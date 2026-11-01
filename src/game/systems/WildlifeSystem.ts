import type { IslandMap } from "../maps/MapDecoder";
import { CollisionSystem } from "./CollisionSystem";
import {
  validWildlife,
  type WildlifeActor,
  type WildlifeManifest,
  type WildlifeSnapshot,
} from "../entities/WildlifeState";

type Point = { x: number; y: number };
type View = Point & { width: number; height: number };
const cell = (p: Point) => Math.floor(p.x / 16) + Math.floor(p.y / 16) * 180;
const clamp = (n: number, min: number, max: number) =>
  Math.max(min, Math.min(max, n));
const divisors = [0, 6, 5, 4, 3, 2];

/** Source behavior port; the source island arrays are never modified. See docs/WILDLIFE.md. */
export class WildlifeSystem {
  readonly actors: WildlifeActor[] = [];
  readonly managed = new Set<number>();
  readonly objects: number[];
  private owners = new Int32Array(32400).fill(-1);
  private accumulator = 0;
  private land: CollisionSystem;
  constructor(
    public map: IslandMap,
    public manifest: WildlifeManifest,
    removed = new Set<number>(),
    saved?: WildlifeSnapshot[],
  ) {
    this.objects = map.layers.Objects.map((v, i) => (removed.has(i) ? 0 : v));
    const profiles = new Map(manifest.species.map((p) => [p.mapId, p]));
    for (let id = 0; id < 32400; id++) {
      const profile = profiles.get(map.layers.Objects[id]);
      if (!profile || removed.has(id)) continue;
      this.managed.add(id);
      this.objects[id] = 0;
      const actor: WildlifeActor = {
        id,
        mapId: profile.mapId,
        profile,
        x: (id % 180) * 16 + 8,
        y: Math.floor(id / 180) * 16 + 8,
        remainingX: 0,
        remainingY: 0,
        phase: "walk",
        facing: "right",
        timer: 0,
        duration: 0,
        frame: 0,
        seed: ((id + 1) * 2654435761) >>> 0 || 1,
        reaction: null,
        contactMs: 0,
      };
      this.wander(actor);
      this.actors.push(actor);
    }
    this.land = new CollisionSystem(map, new Set(), this.objects);
    // Apply a complete snapshot atomically. Old saves have no wildlife member.
    if (saved && validWildlife(saved) && saved.length === this.actors.length) {
      const byId = new Map(saved.map((s) => [s.id, s]));
      const occupied = new Set<number>();
      const valid = this.actors.every((a) => {
        const s = byId.get(a.id);
        if (
          !s ||
          s.mapId !== a.mapId ||
          (s.phase === "fly" && a.profile.family !== "bird") ||
          (a.profile.family === "shark" && s.phase !== "walk") ||
          s.frame >= (a.profile.family === "shark" ? 6 : 4)
        )
          return false;
        const i = cell(s);
        if (s.phase !== "fly") {
          // Some source seabirds spawn on terrain 5/6. They can move within that
          // starting cell before taking flight, even though they cannot walk into it.
          if (
            occupied.has(i) ||
            this.objects[i] !== 0 ||
            (i !== a.id && !this.terrainAllows(a, s.x, s.y))
          )
            return false;
          occupied.add(i);
        }
        return true;
      });
      if (valid) for (const a of this.actors) Object.assign(a, byId.get(a.id));
    }
    for (const a of this.actors) this.occupy(a);
  }
  private random(a: WildlifeActor, limit: number): number {
    // Repeatable browser PRNG, not a claim to reproduce java.util.Random's stream.
    let seed = a.seed;
    seed ^= seed << 13;
    seed ^= seed >>> 17;
    seed ^= seed << 5;
    a.seed = seed >>> 0 || 1;
    return a.seed % limit;
  }
  private wander(a: WildlifeActor) {
    if (a.profile.family === "shark") {
      a.remainingX = 160 - 16 * this.random(a, 20);
      a.remainingY = 160 - 16 * this.random(a, 20);
      return;
    }
    const radius = a.profile.radius!;
    const offset = (home: number, current: number) => {
      const distance = Math.abs(home - current);
      if (distance < radius) {
        const range = 16 * (radius - distance);
        return this.random(a, range * 2) - range;
      }
      return this.random(a, 16 * radius) * (current > home ? -1 : 1);
    };
    a.remainingX = offset(a.id % 180, Math.floor(a.x / 16));
    a.remainingY = offset(Math.floor(a.id / 180), Math.floor(a.y / 16));
  }
  private release(a: WildlifeActor) {
    const i = cell(a);
    if (this.owners[i] === a.id) {
      this.owners[i] = -1;
      this.objects[i] = 0;
    }
  }
  private occupy(a: WildlifeActor) {
    if (a.phase === "fly") return;
    const i = cell(a);
    this.owners[i] = a.id;
    this.objects[i] = a.mapId;
  }
  private terrainAllows(a: WildlifeActor, x: number, y: number): boolean {
    if (x < 0 || y < 0 || x >= 2880 || y >= 2880) return false;
    const i = cell({ x, y }),
      t = this.map.layers.FULL[i],
      rock = this.map.layers.Rocks[i];
    if (this.map.layers.Decor[i] !== 0 || !this.land.rockPassable(rock))
      return false;
    if (a.profile.family === "shark") return t === 5 || t === 6;
    if (a.profile.family === "predator")
      return a.mapId === 43 || ![4, 5, 14].includes(t);
    return ![5, 6, 12, 13, 14].includes(t);
  }
  private canEnter(a: WildlifeActor, x: number, y: number, player: Point) {
    const i = cell({ x, y });
    return (
      this.terrainAllows(a, x, y) &&
      (this.objects[i] === 0 || this.owners[i] === a.id) &&
      i !== cell(player)
    );
  }
  private phase(a: WildlifeActor, phase: WildlifeSnapshot["phase"]) {
    this.release(a);
    a.phase = phase;
    a.timer = 0;
    a.duration =
      ((phase === "fly" ? 70 : a.profile.restBase) + this.random(a, 50)) * 100;
    if (phase !== "rest") this.wander(a);
    this.occupy(a);
  }
  private react(a: WildlifeActor, player: Point): boolean {
    const tx = Math.floor(a.x / 16),
      ty = Math.floor(a.y / 16),
      dx = tx - Math.floor(player.x / 16),
      dy = ty - Math.floor(player.y / 16),
      family = a.profile.family;
    a.reaction = null;
    if (Math.abs(dx) > 2 || Math.abs(dy) > 2 || family === "passive")
      return false;
    if (family === "bird" && a.phase === "fly") return false;
    const chase = family === "predator" || family === "shark";
    if (family === "bird") this.phase(a, "fly");
    else a.phase = "walk";
    const displacement = (diff: number, current: number, home: number) => {
      const move = diff * (chase ? -1 : 1);
      if (family === "shark") return move; // i.a uses tile deltas directly in this branch.
      return (
        16 *
        (clamp(
          current + move,
          home - a.profile.radius!,
          home + a.profile.radius!,
        ) -
          current)
      );
    };
    a.remainingX = displacement(dx, tx, a.id % 180);
    a.remainingY = displacement(dy, ty, Math.floor(a.id / 180));
    a.reaction = chase ? "chase" : "flee";
    return true;
  }
  private move(a: WildlifeActor, step: number, player: Point) {
    for (const axis of ["x", "y"] as const) {
      const remaining = axis === "x" ? "remainingX" : "remainingY",
        amount =
          Math.sign(a[remaining]) * Math.min(step, Math.abs(a[remaining]));
      if (!amount) continue;
      const x = a.x + (axis === "x" ? amount : 0),
        y = a.y + (axis === "y" ? amount : 0);
      if (
        x < 0 ||
        y < 0 ||
        x >= 2880 ||
        y >= 2880 ||
        (cell({ x, y }) !== cell(a) &&
          a.phase !== "fly" &&
          !this.canEnter(a, x, y, player))
      ) {
        a[remaining] = 0;
        continue;
      }
      this.release(a);
      a[axis] += amount;
      a[remaining] -= amount;
      if (axis === "x") a.facing = amount > 0 ? "right" : "left";
      this.occupy(a);
    }
  }
  private tick(a: WildlifeActor, player: Point) {
    const reactive = this.react(a, player);
    const family = a.profile.family;
    a.timer += this.manifest.tickMs;
    if (a.phase === "rest") {
      if (a.timer > a.duration) {
        const choice = this.random(a, 5);
        this.phase(
          a,
          choice === 0
            ? "rest"
            : family === "bird" && choice === 1
              ? "fly"
              : "walk",
        );
      }
    } else {
      const tier =
        a.profile.speedTier +
        (reactive && (family === "prey" || family === "predator") ? 1 : 0);
      this.move(a, Math.floor(8 / divisors[Math.min(5, tier)]), player);
      if (
        a.phase === "fly" &&
        a.timer > a.duration &&
        this.canEnter(a, a.x, a.y, player)
      ) {
        const choice = this.random(a, 5);
        this.phase(a, choice === 0 ? "rest" : choice === 1 ? "fly" : "walk");
      } else if (!a.remainingX && !a.remainingY && !reactive) {
        const choice = this.random(a, 5);
        if (a.phase === "fly" || family === "shark") this.wander(a);
        else if (
          choice === 0 &&
          !(a.mapId === 48 && this.map.layers.FULL[cell(a)] === 4)
        )
          this.phase(a, "rest");
        else if (family === "bird" && choice === 1) this.phase(a, "fly");
        else this.wander(a);
      }
    }
    a.frame = (a.frame + 1) % (family === "shark" ? 6 : 4);
  }
  update(
    delta: number,
    player: Point,
    view?: View,
    afterTick?: (actor: WildlifeActor) => void,
  ) {
    // No background-tab catch-up burst. UI pause callers never advance this clock.
    this.accumulator += Math.max(0, Math.min(delta, 180));
    let advanced = false;
    while (this.accumulator >= this.manifest.tickMs) {
      advanced = true;
      this.accumulator -= this.manifest.tickMs;
      for (const a of this.actors) {
        if (
          view &&
          (a.x < view.x - 32 ||
            a.y < view.y - 32 ||
            a.x > view.x + view.width + 32 ||
            a.y > view.y + view.height + 32)
        )
          continue;
        this.tick(a, player);
        afterTick?.(a);
      }
    }
    return advanced;
  }
  removeActor(id: number) {
    const index = this.actors.findIndex((a) => a.id === id);
    if (index < 0) return false;
    this.release(this.actors[index]);
    this.actors.splice(index, 1);
    return true;
  }
  removeStatic(index: number) {
    if (this.owners[index] === -1) this.objects[index] = 0;
  }
  snapshot(): WildlifeSnapshot[] {
    return this.actors.map(
      ({ profile: _profile, reaction: _reaction, ...state }) => ({ ...state }),
    );
  }
  sprite(a: WildlifeActor) {
    const frames = a.profile.frames;
    const sequence =
      a.phase === "fly"
        ? a.facing === "left"
          ? frames.flyLeft!
          : frames.flyRight!
        : a.phase === "rest"
          ? frames.idle!
          : frames[a.facing];
    return sequence[Math.floor(a.frame / 2) % sequence.length];
  }
  actorAt(index: number) {
    return this.actors.find((a) => a.id === this.owners[index]);
  }
}
