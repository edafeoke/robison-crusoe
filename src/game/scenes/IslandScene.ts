import {
  migrateResourceHistory,
  refreshResources,
  crossedResourceReset,
} from "../systems/ResourceHistorySystem";
import {
  tidalMap,
  tideAt,
  tideRescue,
  type TidePhase,
} from "../systems/TideSystem";
import { raftObjects, cellOf, cellPoint } from "../systems/RaftSystem";
import Phaser from "phaser";
import { validateMap, type IslandMap } from "../maps/MapDecoder";
import { MapRenderer } from "../maps/MapRenderer";
import { CollisionSystem } from "../systems/CollisionSystem";
import { findPath } from "../systems/MovementSystem";
import { nearestInteraction } from "../systems/InteractionSystem";
import { AnimationSystem } from "../systems/AnimationSystem";
import { Player } from "../entities/Player";
import { Animal } from "../entities/Animal";
import type { WildlifeManifest } from "../entities/WildlifeState";
import { WildlifeSystem } from "../systems/WildlifeSystem";
import { advanceSurvival } from "../systems/SurvivalSystem";
import {
  advanceWorldContact,
  applyWorldContact,
} from "../systems/WorldHazardSystem";
import { useGameStore } from "../../store/gameStore";
export class IslandScene extends Phaser.Scene {
  player!: Player;
  world!: MapRenderer;
  map!: IslandMap;
  private sourceMap!: IslandMap;
  private tide!: TidePhase;
  private shores!: AnimationSystem;
  collision!: CollisionSystem;
  keys!: Record<string, Phaser.Input.Keyboard.Key>;
  marker!: Phaser.GameObjects.Graphics;
  ring!: Phaser.GameObjects.Ellipse;
  private syncTime = 0;
  private survivalTime = 0;
  private refreshingResources = false;
  private removed = new Set<number>();
  private unsubscribe?: () => void;
  private discovered = new Set<number>();
  private wildlife!: WildlifeSystem;
  private animals: Animal[] = [];
  constructor() {
    super("Island");
  }
  create() {
    try {
      this.sourceMap = validateMap(this.cache.json.get("island"));
    } catch (error) {
      useGameStore.setState({ error: (error as Error).message });
      return;
    }
    const state = useGameStore.getState();
    this.tide = tideAt(state.elapsed);
    this.map = tidalMap(this.sourceMap, this.tide);
    const resources =
      state.resources ??
      migrateResourceHistory(
        this.sourceMap.layers.Objects,
        state.items,
        state.collected,
      );
    this.refreshingResources = false;
    this.removed = new Set(state.collected);
    this.discovered = new Set(state.discovered);
    const manifest = this.cache.json.get("wildlife") as
      | WildlifeManifest
      | undefined;
    if (
      !manifest ||
      manifest.version !== 1 ||
      manifest.tickMs !== 90 ||
      !Array.isArray(manifest.species)
    ) {
      useGameStore.setState({
        error:
          "The recovered wildlife manifest is unavailable. Reload to try again.",
      });
      return;
    }
    this.syncTime = 0;
    this.survivalTime = 0;
    this.wildlife = new WildlifeSystem(
      this.map,
      manifest,
      this.removed,
      state.wildlife,
      raftObjects(
        this.map.layers.Objects.map((v, i) => (this.removed.has(i) ? 0 : v)),
        state.raft,
      ),
      true,
    );
    this.wildlife.applyTide(this.map, { x: state.x, y: state.y });
    this.collision = new CollisionSystem(
      this.map,
      this.removed,
      this.wildlife.objects,
    );
    this.collision.mode = state.raft.phase === "aboard" ? "raft" : "walk";
    this.world = new MapRenderer(
      this,
      this.map,
      state.atlas!,
      state.items,
      this.tide,
    );
    this.world.render(
      this.removed,
      this.wildlife.managed,
      state.shelter,
      state.ship,
      state.raft,
    );
    this.animals = this.wildlife.actors.map(
      (a) => new Animal(this, state.atlas!, a, this.wildlife),
    );
    this.shores = new AnimationSystem(this, this.map, state.atlas!);
    const rescue =
      state.raft.phase !== "aboard" && state.ship.phase === "wreck"
        ? tideRescue(this.map, this.wildlife.objects, state.x, state.y)
        : null;
    const spawn =
      rescue ??
      (state.ship.phase !== "wreck" || this.collision.canStand(state.x, state.y)
        ? { x: state.x, y: state.y }
        : this.collision.nearest(state.x, state.y)!);
    this.player = new Player(
      this,
      state.atlas!,
      this.collision,
      spawn.x,
      spawn.y,
    );
    this.player.setRaft(state.raft);
    this.player.setOutfit(state.clothing.equipped);
    this.player.shadow.setVisible(
      state.ship.phase === "wreck" && state.raft.phase !== "aboard",
    );
    this.cameras.main
      .setBounds(0, 0, 2880, 2880)
      .setZoom(state.zoom)
      .startFollow(this.player.shadow, true, 0.14, 0.14);
    this.cameras.main.setRoundPixels(true);
    this.keys = this.input.keyboard!.addKeys(
      "W,A,S,D,UP,DOWN,LEFT,RIGHT,E",
    ) as typeof this.keys;
    this.input.keyboard!.addCapture([
      "W",
      "A",
      "S",
      "D",
      "UP",
      "DOWN",
      "LEFT",
      "RIGHT",
      "E",
    ]);
    this.input.keyboard!.on("keydown-E", () => {
      const s = useGameStore.getState();
      if (
        !s.panel &&
        !s.coconutGame &&
        !s.fishingGame &&
        !s.rodGame &&
        !s.huntingGame &&
        !s.survival.death &&
        s.survival.restRemainingMs === null &&
        s.focus &&
        s.ship.phase === "wreck"
      )
        s.inspect(s.focus.index, s.focus.itemId);
    });
    this.marker = this.add.graphics().setDepth(4000);
    this.ring = this.add
      .ellipse(0, 0, 20, 10)
      .setStrokeStyle(1, 0xfff0b0, 0.9)
      .setDepth(3500)
      .setVisible(false);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      const s = useGameStore.getState();
      // Phaser also receives window mouse events from React's overlaid controls.
      // Menu/save/zoom clicks must not issue a world movement command.
      if (
        s.panel ||
        s.ship.phase !== "wreck" ||
        s.coconutGame ||
        s.fishingGame ||
        s.rodGame ||
        s.huntingGame ||
        !s.started ||
        s.survival.death ||
        s.survival.restRemainingMs !== null ||
        p.event.target !== this.game.canvas
      )
        return;
      const world = p.positionToCamera(
        this.cameras.main,
      ) as Phaser.Math.Vector2;
      const object =
        s.raft.phase === "aboard"
          ? null
          : nearestInteraction(
              this.map,
              world.x,
              world.y,
              this.removed,
              13,
              this.wildlife.objects,
            );
      if (
        object &&
        Math.hypot(object.x - this.player.x, object.y - this.player.y) < 44
      ) {
        object.actorId = this.wildlife.actorAt(object.index)?.id;
        useGameStore.setState({ focus: object });
        s.inspect(object.index, object.itemId);
        return;
      }
      const dest = this.collision.nearest(world.x, world.y, 3);
      if (!dest) {
        s.notify("The water and rocks block the way.");
        return;
      }
      this.player.path = findPath(this.collision, this.player, dest);
      if (!this.player.path.length) {
        s.notify("No clear path from here.");
        return;
      }
      this.marker
        .clear()
        .lineStyle(1, 0xffecad, 0.85)
        .strokeCircle(dest.x, dest.y, 4);
      this.tweens.add({
        targets: this.marker,
        alpha: 0,
        duration: 1200,
        onComplete: () => this.marker.clear(),
      });
      this.marker.alpha = 1;
    });
    this.unsubscribe = useGameStore.subscribe((s, prev) => {
      if (!prev.coconutGame && s.coconutGame) {
        this.cameras.main.setVisible(false);
        this.scene.launch("Coconut");
        this.scene.bringToTop("Coconut");
        this.player.path = [];
        this.input.keyboard?.resetKeys();
      }
      if (prev.coconutGame && !s.coconutGame) {
        this.cameras.main.setVisible(true);
        this.scene.stop("Coconut");
      }
      if (!prev.fishingGame && s.fishingGame) {
        this.cameras.main.setVisible(false);
        this.scene.launch("NetFishing");
        this.scene.bringToTop("NetFishing");
        this.player.path = [];
        this.input.keyboard?.resetKeys();
      }
      if (prev.fishingGame && !s.fishingGame) {
        this.cameras.main.setVisible(true);
        this.scene.stop("NetFishing");
      }
      if (!prev.rodGame && s.rodGame) {
        this.cameras.main.setVisible(false);
        this.scene.launch("RodFishing");
        this.scene.bringToTop("RodFishing");
        this.player.path = [];
        this.input.keyboard?.resetKeys();
      }
      if (prev.rodGame && !s.rodGame) {
        this.cameras.main.setVisible(true);
        this.scene.stop("RodFishing");
      }
      if (!prev.huntingGame && s.huntingGame) {
        this.cameras.main.setVisible(false);
        this.scene.launch("Hunting");
        this.scene.bringToTop("Hunting");
        this.player.path = [];
        this.input.keyboard?.resetKeys();
      }
      if (prev.huntingGame && !s.huntingGame) {
        this.cameras.main.setVisible(true);
        this.scene.stop("Hunting");
      }
      if (s.raft !== prev.raft) {
        const transitioned =
          s.raft.phase !== prev.raft.phase || s.raft.index !== prev.raft.index;
        if (transitioned) {
          const objects = raftObjects(this.wildlife.objects, s.raft);
          objects.forEach(
            (value, index) => (this.wildlife.objects[index] = value),
          );
          this.world.updateRaft(s.raft, prev.raft);
          if (prev.raft.phase === "aboard" && s.raft.phase === "moored") {
            const dx = Math.floor(s.x / 16) - Math.floor(prev.x / 16),
              dy = Math.floor(s.y / 16) - Math.floor(prev.y / 16);
            this.player.facing =
              dx < 0 ? "left" : dx > 0 ? "right" : dy < 0 ? "up" : "down";
          }
          this.player.x = s.x;
          this.player.y = s.y;
          this.player.path = [];
          this.input.keyboard?.resetKeys();
        }
        this.collision.mode = s.raft.phase === "aboard" ? "raft" : "walk";
        this.player.setRaft(s.raft);
      }
      if (s.zoom !== prev.zoom) this.cameras.main.setZoom(s.zoom);
      if (s.shelter !== prev.shelter) this.world.updateShelter(s.shelter);
      if (s.clothing !== prev.clothing)
        this.player.setOutfit(s.clothing.equipped);
      if (s.collected !== prev.collected) {
        // Loading restores removed objects too; rebuild safely only when the collection shrinks or differs.
        if (
          !this.refreshingResources &&
          prev.collected.some((i) => !s.collected.includes(i))
        ) {
          this.scene.restart();
          return;
        }
        for (const i of s.collected) {
          this.removed.add(i);
          if (s.raft.phase === "moored" && s.raft.index === i) continue;
          this.world.remove(i);
          if (this.wildlife.removeActor(i))
            this.animals = this.animals.filter((a) => {
              if (a.id === i) {
                a.destroy();
                return false;
              }
              return true;
            });
          this.wildlife.removeStatic(i);
        }
      }
      if (s.restoreRevision !== prev.restoreRevision) {
        this.scene.restart();
        return;
      }
      if (prev.ship.phase === "wreck" && s.ship.phase !== "wreck") {
        this.world.updateShip(s.ship);
        this.player.x = s.x;
        this.player.y = s.y;
        this.player.facing = "down";
        this.player.path = [];
        this.player.shadow.setVisible(false);
        this.input.keyboard?.resetKeys();
      }
      if (
        s.panel !== prev.panel ||
        s.survival.restRemainingMs !== prev.survival.restRemainingMs ||
        s.survival.death !== prev.survival.death
      ) {
        this.player.path = [];
        if (s.panel) this.input.keyboard?.resetKeys();
      }
    });
    this.events.once("shutdown", () => {
      this.unsubscribe?.();
      this.input.keyboard?.removeAllListeners();
      useGameStore.setState({ captureWildlife: undefined });
    });
    useGameStore.setState({
      ready: true,
      resources,
      x: spawn.x,
      y: spawn.y,
      worldMap: this.map,
      worldObjects: this.wildlife.objects,
      worldDecor: this.map.layers.Decor,
      worldTerrain: this.map.layers.FULL,
      captureWildlife: () => this.wildlife.snapshot(),
    });
    if (state.huntingGame) {
      this.cameras.main.setVisible(false);
      this.scene.launch("Hunting");
      this.scene.bringToTop("Hunting");
    }
    if (state.rodGame) {
      this.cameras.main.setVisible(false);
      this.scene.launch("RodFishing");
      this.scene.bringToTop("RodFishing");
    }
    if (state.fishingGame) {
      this.cameras.main.setVisible(false);
      this.scene.launch("NetFishing");
      this.scene.bringToTop("NetFishing");
    }
    if (state.coconutGame) {
      this.cameras.main.setVisible(false);
      this.scene.launch("Coconut");
      this.scene.bringToTop("Coconut");
    }
  }
  update(_time: number, delta: number) {
    if (!this.player) return;
    const s = useGameStore.getState();
    if (s.ship.phase === "escaped") {
      this.player.path = [];
      this.player.facing = "down";
      this.player.move(0, 0, 0);
      this.player.shadow.setVisible(false);
      return;
    }
    if (
      s.panel ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      !s.started ||
      s.survival.death ||
      document.hidden
    ) {
      if (s.survival.restRemainingMs === null) this.player.move(0, 0, 0);
      return;
    }
    delta = Math.max(0, Math.min(delta, 250));
    const dx =
        Number(this.keys.D.isDown || this.keys.RIGHT.isDown) -
        Number(this.keys.A.isDown || this.keys.LEFT.isDown),
      dy =
        Number(this.keys.S.isDown || this.keys.DOWN.isDown) -
        Number(this.keys.W.isDown || this.keys.UP.isDown);
    if (s.ship.phase === "departing") {
      this.player.path = [];
      this.player.facing = "down";
      this.player.move(0, 0, 0);
      this.player.shadow.setVisible(false);
    } else if (s.survival.restRemainingMs !== null) {
      this.player.path = [];
      this.player.rest(10000 - s.survival.restRemainingMs);
    } else if (dx || dy) {
      this.player.path = [];
      this.player.move(dx, dy, delta / 1000);
    } else this.player.updatePath(delta / 1000);
    this.syncTime += delta;
    this.survivalTime += delta;
    let survival = s.survival;
    let rested = false;
    const flushSurvival = () => {
      const before = survival.restRemainingMs;
      survival = advanceSurvival(survival, this.survivalTime);
      this.survivalTime = 0;
      rested ||=
        before !== null && survival.restRemainingMs === null && !survival.death;
    };
    const attacks: string[] = [];
    if (
      this.wildlife.update(
        delta,
        this.player,
        s.raft.phase === "aboard",
        this.cameras.main.worldView,
        (actor) => {
          if (!survival.enabled) {
            actor.contactMs = 0;
            return;
          }
          if (survival.death) return;
          const contact = advanceWorldContact(
            actor,
            this.player,
            this.wildlife.manifest.tickMs,
            s.raft.phase === "aboard",
          );
          if (!contact) return;
          flushSurvival();
          if (survival.death) return;
          const result = applyWorldContact(
            survival,
            contact,
            s.clothing.equipped,
          );
          survival = result.survival;
          attacks.push(
            `${actor.profile.name} · ${contact.kind === "instant" ? "energy depleted" : `−${result.cost} energy`}${result.interrupted && contact.kind === "timed" ? ` · Rest interrupted (+${result.restCredit} energy)` : ""}`,
          );
        },
      )
    )
      for (const animal of this.animals) animal.render();
    if (attacks.length) s.notify(attacks.join(" · "));
    if (this.syncTime > 100) {
      const elapsed = s.elapsed + this.syncTime / 1000;
      let collected = s.collected,
        resources =
          s.resources ??
          migrateResourceHistory(
            this.sourceMap.layers.Objects,
            s.items,
            collected,
          );
      const phase = tideAt(elapsed);
      if (phase !== this.tide) {
        this.tide = phase;
        this.map = tidalMap(this.sourceMap, phase);
        this.collision.map = this.map;
        this.wildlife.applyTide(this.map, this.player);
        this.world.updateTide(this.map, phase);
        this.shores.setMap(this.map);
        this.player.path = [];
        const playerCells = new Set(
          [
            [-3, -2],
            [3, -2],
            [-3, 2],
            [3, 2],
          ].map(([dx, dy]) => cellOf(this.player.x + dx, this.player.y + dy)),
        );
        const refreshed = refreshResources(
          this.sourceMap.layers.Objects,
          s.items,
          collected,
          resources,
          s.inventory,
          (index) =>
            playerCells.has(index) || this.wildlife.objects[index] !== 0,
          crossedResourceReset(s.elapsed, elapsed),
        );
        collected = [...refreshed.collected];
        resources = refreshed.resources;
        for (const index of refreshed.revived) {
          if (
            this.wildlife.restoreStatic(
              index,
              this.sourceMap.layers.Objects[index],
            )
          ) {
            this.removed.delete(index);
            this.world.restoreResource(index);
          }
        }
        const rescued =
          s.raft.phase !== "aboard" && s.ship.phase === "wreck"
            ? tideRescue(
                this.map,
                this.wildlife.objects,
                this.player.x,
                this.player.y,
              )
            : null;
        if (rescued) {
          this.player.x = rescued.x;
          this.player.y = rescued.y;
          this.player.move(0, 0, 0);
        }
        for (const animal of this.animals) animal.render();
        s.notify(
          phase === "low"
            ? "Low tide · the shore has receded"
            : rescued
              ? "High tide · returned to the shallow shore"
              : "High tide · water covers the tidal flats",
        );
      }
      flushSurvival();
      if (rested && !attacks.length)
        s.notify("Rested · Energy restored by up to 30 points");
      this.syncTime = 0;
      const tx = Math.floor(this.player.x / 16),
        ty = Math.floor(this.player.y / 16);
      for (let y = -5; y <= 5; y++)
        for (let x = -5; x <= 5; x++) {
          const ax = tx + x,
            ay = ty + y;
          if (ax >= 0 && ay >= 0 && ax < 180 && ay < 180)
            this.discovered.add(ax + ay * 180);
        }
      const focus =
        survival.restRemainingMs !== null ||
        survival.death ||
        s.ship.phase !== "wreck"
          ? null
          : s.raft.phase === "aboard"
            ? {
                index: cellOf(this.player.x, this.player.y),
                itemId: 94,
                ...cellPoint(cellOf(this.player.x, this.player.y)),
                distance: 0,
                kind: "raft" as const,
              }
            : nearestInteraction(
                this.map,
                this.player.x,
                this.player.y,
                this.removed,
                42,
                this.wildlife.objects,
              );
      if (focus) {
        const animal = this.wildlife.actorAt(focus.index);
        if (animal) focus.actorId = animal.id;
        if (animal)
          focus.behavior =
            animal.reaction === "flee"
              ? "Moving away"
              : animal.reaction === "chase"
                ? "Following Robinson"
                : animal.phase === "rest"
                  ? "Resting"
                  : "Wandering";
      }
      this.ring.setVisible(!!focus);
      if (focus) this.ring.setPosition(focus.x, focus.y + 2);
      this.refreshingResources = true;
      useGameStore.setState({
        collected,
        resources,
        x: this.player.x,
        y: this.player.y,
        elapsed,
        worldMap: this.map,
        worldTerrain: this.map.layers.FULL,
        survival,
        ...(s.raft.phase === "aboard" &&
        this.player.getRaftFacing() !== s.raft.facing
          ? { raft: { ...s.raft, facing: this.player.getRaftFacing()! } }
          : {}),
        focus,
        discovered: [...this.discovered],
      });
      this.refreshingResources = false;
    } else if (attacks.length)
      useGameStore.setState({
        survival,
        ...(survival.death ? { focus: null } : {}),
      });
    if (s.ship.phase === "departing")
      useGameStore.getState().advanceShip(delta);
  }
}
