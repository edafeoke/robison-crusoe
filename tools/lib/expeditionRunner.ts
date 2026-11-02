/** Ordered, headless integration driver. Uses production rules/store; no renderer or phone timing claim. */
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import type { RecoveredItem } from "../../src/game/assets/assetManifest";
import type { WildlifeManifest } from "../../src/game/entities/WildlifeState";
import { useGameStore as store } from "../../src/store/gameStore";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import { WildlifeSystem } from "../../src/game/systems/WildlifeSystem";
import { moveWithCollision } from "../../src/game/systems/MovementSystem";
import { canPickup } from "../../src/game/systems/ItemRules";
import {
  advanceSurvival,
  foodEffects,
  useAvailability,
} from "../../src/game/systems/SurvivalSystem";
import {
  worldFoodAvailability,
  worldFoodEffects,
} from "../../src/game/systems/WorldConsumptionSystem";
import {
  advanceWorldContact,
  applyWorldContact,
} from "../../src/game/systems/WorldHazardSystem";
import {
  tideAt,
  tidalMap,
  tideRescue,
} from "../../src/game/systems/TideSystem";
import {
  initialResourceHistory,
  refreshResources,
  crossedResourceReset,
} from "../../src/game/systems/ResourceHistorySystem";
import {
  cellOf,
  cellPoint,
  raftObjects,
  landingPoint,
} from "../../src/game/systems/RaftSystem";
import { clothingProfile } from "../../src/game/systems/ClothingSystem";
import {
  shelterOwned,
  shelterAllows,
} from "../../src/game/systems/ShelterSystem";
import { resourceHistoryRules as R } from "../../src/game/data/resourceHistoryRules";
import { validSave, type SaveData } from "../../src/game/systems/SaveSystem";
type Point = { x: number; y: number };
import { ExpeditionNavigator, adjacent } from "./expeditionNavigation";
export class ExpeditionRunner {
  private navigation: ExpeditionNavigator;
  map: IslandMap;
  wildlife: WildlifeSystem;
  collision: CollisionSystem;
  log: Record<string, unknown>[] = [];
  movedPixels = 0;
  ticks = 0;
  private provisioning = false;
  private fleeing = false;
  private actions = 0;
  private foodHolds = new Map<number, number>();
  constructor(
    public source: IslandMap,
    public items: RecoveredItem[],
    manifest: WildlifeManifest,
  ) {
    store.setState({
      items,
      ready: true,
      started: false,
      saving: false,
      panel: null,
      coconutGame: null,
      fishingGame: null,
      rodGame: null,
      huntingGame: null,
      notify: () => {},
    });
    if (!store.getState().beginExpedition())
      throw new Error("Cannot begin expedition");
    this.map = tidalMap(source, "high");
    this.wildlife = new WildlifeSystem(
      this.map,
      manifest,
      new Set(),
      undefined,
      raftObjects(this.map.layers.Objects, store.getState().raft),
      true,
    );
    this.collision = new CollisionSystem(
      this.map,
      new Set(),
      this.wildlife.objects,
    );
    this.navigation = new ExpeditionNavigator(items, () => this);
    this.publish();
    this.note("begin");
  }
  get state() {
    return store.getState();
  }
  private publish() {
    store.setState({
      worldMap: this.map,
      worldObjects: this.wildlife.objects,
      worldDecor: this.map.layers.Decor,
      worldTerrain: this.map.layers.FULL,
    });
    this.collision.mode = this.state.raft.phase === "aboard" ? "raft" : "walk";
  }
  note(kind: string, detail: Record<string, unknown> = {}) {
    const s = this.state;
    this.log.push({
      kind,
      at: +s.elapsed.toFixed(2),
      cell: cellOf(s.x, s.y),
      reserves: [s.survival.hunger, s.survival.thirst, s.survival.energy],
      ...detail,
    });
  }
  private alive() {
    if (this.state.survival.death)
      throw new Error(
        `Collapse: ${this.state.survival.death} at ${this.state.elapsed.toFixed(1)}s`,
      );
    if (this.state.elapsed > 14400 || ++this.actions > 500000)
      throw new Error("Expedition budget reached");
  }
  tick(ms = 50) {
    this.alive();
    this.ticks++;
    const s = this.state,
      previous = s.elapsed,
      next = previous + ms / 1000;
    let survival = advanceSurvival(s.survival, ms);
    this.wildlife.update(
      ms,
      s,
      s.raft.phase === "aboard",
      { x: s.x - 250, y: s.y - 150, width: 500, height: 300 },
      (actor) => {
        if (survival.death) return;
        const contact = advanceWorldContact(
          actor,
          s,
          this.wildlife.manifest.tickMs,
          s.raft.phase === "aboard",
        );
        if (contact) {
          survival = applyWorldContact(
            survival,
            contact,
            s.clothing.equipped,
          ).survival;
          this.note("contact", {
            animal: actor.mapId,
            energy: survival.energy,
          });
        }
      },
    );
    store.setState({ elapsed: next, survival });
    if (tideAt(previous) !== tideAt(next)) {
      this.map = tidalMap(this.source, tideAt(next));
      this.collision.map = this.map;
      this.wildlife.applyTide(this.map, this.state);
      const occupied = new Set(
        [
          [-3, -2],
          [3, -2],
          [-3, 2],
          [3, 2],
        ].map(([dx, dy]) => cellOf(this.state.x + dx, this.state.y + dy)),
      );
      const renewal = refreshResources(
        this.source.layers.Objects,
        this.items,
        s.collected,
        s.resources ?? initialResourceHistory(),
        s.inventory,
        (i) => occupied.has(i) || this.wildlife.objects[i] !== 0,
        crossedResourceReset(previous, next),
      );
      for (const i of renewal.revived)
        this.wildlife.restoreStatic(i, this.source.layers.Objects[i]);
      store.setState({
        collected: [...renewal.collected],
        resources: renewal.resources,
      });
      if (s.raft.phase !== "aboard" && s.ship.phase === "wreck") {
        const p = tideRescue(
          this.map,
          this.wildlife.objects,
          this.state.x,
          this.state.y,
        );
        if (p) {
          store.setState(p);
          this.note("tide rescue");
        }
      }
      this.publish();
      this.note("tide", {
        phase: tideAt(next),
        renewed: renewal.revived.length,
      });
    }
    if (this.state.ship.phase === "departing") this.state.advanceShip(ms);
    this.alive();
  }
  private removed() {
    const s = this.state;
    for (const i of s.collected) this.wildlife.removeStatic(i);
  }
  private raftChanged() {
    const next = raftObjects(this.wildlife.objects, this.state.raft);
    next.forEach((v, i) => (this.wildlife.objects[i] = v));
    this.publish();
  }
  private goodFood(id: number, world: boolean) {
    const item = this.items[id];
    if (!item) return false;
    const held = this.foodHolds.get(id) ?? 0;
    if (held > 0 && (world || (this.state.inventory[id] ?? 0) <= held))
      return false;
    const effects = world ? worldFoodEffects(item) : foodEffects(item);
    const allowed = world
      ? worldFoodAvailability(item, this.state.inventory).allowed
      : useAvailability(item, this.state.inventory).allowed;
    return (
      allowed && effects.hunger > 0 && effects.energy > 0 && effects.thirst >= 0
    );
  }
  eatNearby() {
    if (this.state.survival.restRemainingMs !== null) return;
    const s = this.state,
      tx = Math.floor(s.x / 16),
      ty = Math.floor(s.y / 16);
    if (s.raft.phase !== "aboard")
      for (let dy = -3; dy <= 3; dy++)
        for (let dx = -3; dx <= 3; dx++) {
          const i = tx + dx + (ty + dy) * 180,
            id = this.wildlife.objects[i] - 1;
          if (!this.goodFood(id, true)) continue;
          const p = cellPoint(i),
            e = worldFoodEffects(this.items[id]),
            life = this.state.survival;
          if (Math.hypot(p.x - this.state.x, p.y - this.state.y) > 44) continue;
          if (
            life.hunger > 100 - e.hunger &&
            life.thirst > 100 - e.thirst &&
            life.energy > 100 - e.energy
          )
            continue;
          if (this.state.consumeWorld(i, id)) {
            this.removed();
            this.note("world meal", { item: id, index: i });
          }
        }
    for (const [id, quantity] of Object.entries(this.state.inventory)) {
      if (!quantity || !this.goodFood(+id, false)) continue;
      const life = this.state.survival,
        e = foodEffects(this.items[+id]);
      if (life.hunger < 60 || life.thirst < 50 || life.energy < 35)
        if (this.state.consume(+id)) this.note("satchel meal", { item: +id });
    }
  }
  private collect(index: number) {
    const id = this.wildlife.objects[index] - 1;
    if (!this.state.collect(index, id))
      throw new Error(`Illegal pickup ${id}@${index}`);
    this.removed();
    this.note("gather", { item: id, index });
    this.alive();
  }
  private walkCell(index: number) {
    const p = cellPoint(index);
    for (let n = 0; n < 30; n++) {
      const s = this.state,
        dx = p.x - s.x,
        dy = p.y - s.y,
        d = Math.hypot(dx, dy);
      if (d < 0.01) return true;
      if (
        !this.fleeing &&
        this.wildlife.actors.some(
          (a) =>
            a.profile.family === "shark" &&
            Math.abs(Math.floor(a.x / 16) - Math.floor(s.x / 16)) <= 3 &&
            Math.abs(Math.floor(a.y / 16) - Math.floor(s.y / 16)) <= 3,
        ) &&
        this.flee()
      )
        return false;
      if (s.raft.phase !== "aboard") {
        this.eatNearby();
        if (!this.provisioning && !this.fleeing) this.vitals();
      }
      const value = this.wildlife.objects[index];
      if (
        value &&
        s.raft.phase !== "aboard" &&
        !(R.mobileMapIds as readonly number[]).includes(value) &&
        canPickup(this.items[value - 1], this.state.inventory)
      ) {
        if (
          this.items[value - 1].effects.energy < 0 &&
          !this.provisioning &&
          !this.fleeing
        )
          this.vitals(30 - this.items[value - 1].effects.energy);
        if (
          this.state.x !== s.x ||
          this.state.y !== s.y ||
          this.wildlife.objects[index] !== value
        )
          return false;
        if (this.navigation.nearTargets([index]).length) this.collect(index);
      }
      if (this.state.x !== s.x || this.state.y !== s.y) return false;
      const next = moveWithCollision(
        this.collision,
        s,
        dx,
        dy,
        Math.min(0.05, d / 64),
      );
      const amount = Math.hypot(next.x - s.x, next.y - s.y);
      if (amount < 0.001) {
        this.tick();
        return false;
      }
      this.movedPixels += amount;
      store.setState(next);
      const oldPhase = tideAt(s.elapsed);
      this.tick();
      if (tideAt(this.state.elapsed) !== oldPhase) return false;
    }
    return false;
  }
  go(indices: readonly number[]) {
    for (let attempt = 0; attempt < 1200; attempt++) {
      if (
        this.state.raft.phase !== "aboard" &&
        this.navigation.nearTargets(indices).length
      )
        return;
      const center = cellPoint(cellOf(this.state.x, this.state.y));
      if (Math.hypot(this.state.x - center.x, this.state.y - center.y) > 2) {
        this.walkCell(cellOf(this.state.x, this.state.y));
        continue;
      }
      const goals = this.navigation.approaches(indices),
        path =
          this.navigation.path(goals, "walk", 2) ??
          this.navigation.path(goals, "walk", 1) ??
          this.navigation.path(goals, "walk", 0);
      if (this.state.raft.phase !== "aboard" && path) {
        let blocked = false;
        for (const index of path.slice(0, 4))
          if (!this.walkCell(index)) {
            blocked = true;
            break;
          }
        if (!blocked && path.length <= 4) return;
        if (blocked) this.flee();
        continue;
      }
      if (this.state.raft.phase !== "broken" && this.transport(goals)) continue;
      if (this.flee()) continue;
      this.eatNearby();
      if (!this.provisioning && !this.fleeing) this.vitals();
      for (let n = 0; n < 20; n++) this.tick();
    }
    throw new Error(
      `No ordered route to ${indices.slice(0, 4)} at ${this.state.elapsed.toFixed(1)}s`,
    );
  }
  private transport(goals: Set<number>) {
    const s = this.state;
    if (s.raft.phase === "moored") {
      const dock = s.raft.index!,
        candidates = new Set(adjacent(dock));
      const path =
        this.navigation.path(candidates, "walk", 2) ??
        this.navigation.path(candidates, "walk", 1);
      if (path === null) return false;
      for (const i of path.slice(0, 4)) if (!this.walkCell(i)) return true;
      if (path.length > 4) return true;
      if (!this.state.boardRaft(dock)) return false;
      this.raftChanged();
      this.note("board");
      return true;
    }
    if (s.raft.phase !== "aboard") return false;
    // Work backwards from every legal interaction stand to its connected land component.
    const blockedShore = new Set<number>();
    for (const a of this.wildlife.actors.filter(
      (a) => a.profile.family === "shark",
    )) {
      const x = Math.floor(a.x / 16),
        y = Math.floor(a.y / 16);
      for (let dy = -2; dy <= 2; dy++)
        for (let dx = -2; dx <= 2; dx++)
          blockedShore.add(x + dx + (y + dy) * 180);
    }
    const landCollision = new CollisionSystem(
      this.map,
      new Set(),
      this.navigation.planned("walk"),
    );
    const q = [...goals].filter(
        (i) =>
          !blockedShore.has(i) &&
          landCollision.walkableOnFoot(i % 180, Math.floor(i / 180)),
      ),
      seen = new Set(q);
    for (let n = 0; n < q.length; n++)
      for (const j of adjacent(q[n]))
        if (
          !seen.has(j) &&
          !blockedShore.has(j) &&
          landCollision.walkableOnFoot(j % 180, Math.floor(j / 180))
        ) {
          seen.add(j);
          q.push(j);
        }

    const waterGoals = new Set<number>();
    for (let i = 0; i < 32400; i++) {
      if (!this.collision.walkable(i % 180, Math.floor(i / 180))) continue;
      const p = cellPoint(i),
        land = landingPoint(s.raft, p.x, p.y, this.collision);
      if (land && seen.has(cellOf(land.x, land.y))) waterGoals.add(i);
    }
    const path = this.navigation.path(waterGoals, "raft");
    if (path === null) return false;
    for (const i of path.slice(0, 4)) if (!this.walkCell(i)) return true;
    if (path.length > 4) return true;
    if (!this.state.landRaft()) return false;
    this.raftChanged();
    this.note("land");
    return true;
  }
  private flee() {
    if (this.fleeing || this.state.raft.phase === "aboard") return false;
    const s = this.state,
      predators = this.wildlife.actors.filter(
        (a) => a.profile.family === "predator" || a.profile.family === "shark",
      );
    const danger = (p: Point) =>
      predators.some(
        (a) =>
          Math.abs(Math.floor(a.x / 16) - Math.floor(p.x / 16)) <= 5 &&
          Math.abs(Math.floor(a.y / 16) - Math.floor(p.y / 16)) <= 5,
      );
    if (!danger(s)) return false;
    const goals = new Set<number>(),
      tx = Math.floor(s.x / 16),
      ty = Math.floor(s.y / 16);
    for (let dy = -12; dy <= 12; dy++)
      for (let dx = -12; dx <= 12; dx++) {
        const x = tx + dx,
          y = ty + dy,
          i = x + y * 180;
        if (
          x >= 0 &&
          x < 180 &&
          y >= 0 &&
          y < 180 &&
          !danger(cellPoint(i)) &&
          this.collision.walkableOnFoot(x, y)
        )
          goals.add(i);
      }
    const path = this.navigation.path(goals, "walk", 2);
    if (!path?.length) return false;
    this.fleeing = true;
    this.note("retreat");
    try {
      for (const i of path) if (!this.walkCell(i)) break;
    } finally {
      this.fleeing = false;
    }
    return true;
  }
  private renew(id: number) {
    const sourceIndices = this.source.layers.Objects.flatMap((v, i) =>
      v === id + 1 ? [i] : [],
    );
    if (!sourceIndices.length) throw new Error(`No original placement: ${id}`);
    for (let n = 0; n < 75; n++) {
      const h = this.state.resources!;
      if (
        !sourceIndices.some(
          (i) => h.recent.includes(i) || h.permanent.includes(i),
        )
      )
        break;
      const candidates = this.wildlife.objects.flatMap((v, i) => {
        const item = this.items[v - 1];
        return item &&
          item.id !== id &&
          item.category === 0 &&
          !(R.mobileMapIds as readonly number[]).includes(v) &&
          !(R.minedItems as readonly number[]).includes(item.id) &&
          canPickup(item, this.state.inventory)
          ? [i]
          : [];
      });
      this.vitals();
      this.go(candidates);
      const index = this.navigation
        .nearTargets(candidates)
        .find((i) => this.wildlife.objects[i] !== 0);
      if (index !== undefined) this.collect(index);
    }
    this.note("await renewal", { item: id });
    const phase = tideAt(this.state.elapsed);
    while (tideAt(this.state.elapsed) === phase) {
      this.vitals();
      this.eatNearby();
      this.tick();
    }
  }
  vitals(minEnergy = 35) {
    if (this.provisioning) return;
    this.provisioning = true;
    try {
      this.eatNearby();
      for (
        let n = 0;
        n < 30 &&
        (this.state.survival.hunger < 55 || this.state.survival.thirst < 45);
        n++
      ) {
        const food = this.wildlife.objects.flatMap((v, i) =>
          this.goodFood(v - 1, true) ? [i] : [],
        );
        if (!food.length) throw new Error("No available food");
        this.go(food);
        this.eatNearby();
      }
      let restAttempts = 0;
      while (this.state.survival.energy < minEnergy) {
        if (++restAttempts > 100)
          throw new Error("No safe recovery route found");
        for (let n = 0; n < 8 && this.flee(); n++) {}
        this.state.beginRest();
        this.note("rest");
        while (this.state.survival.restRemainingMs !== null) {
          const p = this.state;
          if (
            this.wildlife.actors.some(
              (a) =>
                ["predator", "shark"].includes(a.profile.family) &&
                Math.abs(Math.floor(a.x / 16) - Math.floor(p.x / 16)) <= 3 &&
                Math.abs(Math.floor(a.y / 16) - Math.floor(p.y / 16)) <= 3,
            )
          ) {
            this.state.cancelRest();
            this.flee();
            break;
          }
          this.tick();
        }
        this.eatNearby();
      }
    } finally {
      this.provisioning = false;
    }
  }
  ensure(id: number, quantity = 1, chain = new Set<number>()) {
    const held = new Map(this.foodHolds);
    for (const r of [{ item: id, quantity }, ...this.items[id].recipe])
      this.foodHolds.set(
        r.item,
        Math.max(this.foodHolds.get(r.item) ?? 0, r.quantity),
      );
    try {
      this.acquire(id, quantity, chain);
    } finally {
      this.foodHolds = held;
    }
  }
  private acquire(id: number, quantity: number, chain: Set<number>) {
    if (
      (this.state.inventory[id] ?? 0) >= quantity ||
      shelterOwned(id, this.state.shelter) ||
      this.state.clothing.owned.includes(id) ||
      (id === 94 && this.state.raft.phase !== "broken")
    )
      return;
    if (chain.has(id)) throw new Error(`Recipe cycle at ${id}`);
    const active = new Set(chain).add(id),
      item = this.items[id];
    if (item.recipe.length) {
      if (id !== 94 && id !== 19 && !shelterAllows(item, this.state.shelter))
        this.ensure(item.shelterRequirement, 1, active);
      while (
        (this.state.inventory[id] ?? 0) < quantity &&
        !shelterOwned(id, this.state.shelter) &&
        !this.state.clothing.owned.includes(id)
      ) {
        for (const r of [...item.recipe, ...item.craftingTools])
          this.ensure(r.item, r.quantity, active);
        this.vitals(Math.max(35, 10 - item.effects.energy));
        if (id === 94 || id === 19) {
          const target = id === 94 ? 17014 : 5552;
          this.go([target]);
          this.vitals(id === 94 ? 45 : 35);
          if (
            !(id === 94
              ? this.state.repairRaft(target)
              : this.state.repairShip(target))
          )
            throw new Error(`Repair rejected: ${item.name}`);
          if (id === 94) this.raftChanged();
          this.note("repair", { item: id });
          this.alive();
          return;
        }
        if (!this.state.craft(id))
          throw new Error(`Craft rejected: ${item.name}`);
        const garment = clothingProfile(id);
        if (garment && !this.state.equip(garment.slot, id))
          throw new Error("Cannot equip crafted garment");
        this.note("craft", { item: id });
        this.alive();
      }
    } else {
      if (item.interaction.pickupTool > 0)
        this.ensure(item.interaction.pickupTool, 1, active);
      while ((this.state.inventory[id] ?? 0) < quantity) {
        this.vitals(Math.max(35, 10 - item.effects.energy));
        const indices = this.wildlife.objects.flatMap((v, i) =>
          v === item.mapId ? [i] : [],
        );
        if (!indices.length) {
          this.renew(id);
          continue;
        }
        this.go(indices);
        if ((this.state.inventory[id] ?? 0) >= quantity) break;
        const index = this.navigation
          .nearTargets(indices)
          .find((i) => this.wildlife.objects[i] === item.mapId);
        if (index === undefined) continue;
        this.collect(index);
      }
    }
  }
  checkpoint(): SaveData {
    const s = this.state;
    const result: SaveData = {
      version: 1,
      x: s.x,
      y: s.y,
      elapsed: s.elapsed,
      inventory: s.inventory,
      collected: s.collected,
      resources: s.resources,
      inspected: s.inspected,
      discovered: s.discovered,
      enhancements: s.enhancements,
      survival: s.survival,
      wildlife: this.wildlife.snapshot(),
      shelter: s.shelter,
      furnishings: s.furnishings,
      clothing: s.clothing,
      bodyWeight: s.bodyWeight,
      ship: s.ship,
      raft: s.raft,
    };
    if (!validSave(result))
      throw new Error("Runner produced invalid checkpoint");
    return structuredClone(result);
  }
}
