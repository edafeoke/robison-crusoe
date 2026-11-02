/** Test-driver navigation policy; this is not a recovered quest or player autopilot. */
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import type { RecoveredItem } from "../../src/game/assets/assetManifest";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import type { WildlifeSystem } from "../../src/game/systems/WildlifeSystem";
import { canPickup, type Inventory } from "../../src/game/systems/ItemRules";
import { resourceHistoryRules as R } from "../../src/game/data/resourceHistoryRules";
import { cellOf, cellPoint } from "../../src/game/systems/RaftSystem";
interface NavigationWorld {
  map: IslandMap;
  wildlife: WildlifeSystem;
  collision: CollisionSystem;
  state: { x: number; y: number; inventory: Inventory };
}
export const adjacent = (i: number) =>
  [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1],
  ].flatMap(([dx, dy]) => {
    const x = (i % 180) + dx,
      y = Math.floor(i / 180) + dy;
    return x >= 0 && x < 180 && y >= 0 && y < 180 ? [x + y * 180] : [];
  });
export class ExpeditionNavigator {
  constructor(
    private items: RecoveredItem[],
    private read: () => NavigationWorld,
  ) {}
  private get state() {
    return this.read().state;
  }
  private get map() {
    return this.read().map;
  }
  private get wildlife() {
    return this.read().wildlife;
  }
  private get collision() {
    return this.read().collision;
  }
  planned(mode: "walk" | "raft") {
    return this.wildlife.objects.map((v) => {
      const item = this.items[v - 1];
      return mode === "walk" &&
        item &&
        !(R.mobileMapIds as readonly number[]).includes(v) &&
        canPickup(item, this.state.inventory)
        ? 0
        : v;
    });
  }
  path(
    goals: Set<number>,
    mode: "walk" | "raft" = this.collision.mode,
    avoid = 2,
  ) {
    const s = this.state,
      planned = this.planned(mode);
    const c = new CollisionSystem(this.map, new Set(), planned);
    c.mode = mode;
    const dangerous = new Set<number>();
    for (const a of this.wildlife.actors) {
      const radius =
        a.profile.family === "shark"
          ? 2
          : mode === "walk" && a.profile.family === "predator"
            ? avoid
            : -1;
      const x = Math.floor(a.x / 16),
        y = Math.floor(a.y / 16);
      for (let dy = -radius; dy <= radius; dy++)
        for (let dx = -radius; dx <= radius; dx++)
          dangerous.add(x + dx + (y + dy) * 180);
    }
    const begin = cellOf(s.x, s.y),
      q = [begin],
      parent = new Map([[begin, -1]]);
    for (let n = 0; n < q.length; n++) {
      const i = q[n];
      if (goals.has(i)) {
        const result: number[] = [];
        let k = i;
        while (k !== begin) {
          result.push(k);
          k = parent.get(k)!;
        }
        return result.reverse();
      }
      for (const j of adjacent(i))
        if (
          !parent.has(j) &&
          (!dangerous.has(j) || dangerous.has(i)) &&
          c.walkable(j % 180, Math.floor(j / 180))
        ) {
          parent.set(j, i);
          q.push(j);
        }
    }
    return null;
  }
  approaches(indices: readonly number[]) {
    const goals = new Set<number>();
    for (const i of indices) {
      const tx = i % 180,
        ty = Math.floor(i / 180);
      for (let dy = -2; dy <= 2; dy++)
        for (let dx = -2; dx <= 2; dx++) {
          const x = tx + dx,
            y = ty + dy;
          if (
            x >= 0 &&
            x < 180 &&
            y >= 0 &&
            y < 180 &&
            Math.hypot(dx, dy) * 16 <= 44
          )
            goals.add(x + y * 180);
        }
    }
    return goals;
  }
  nearTargets(indices: readonly number[]) {
    return indices
      .filter((i) => {
        const p = cellPoint(i);
        return Math.hypot(p.x - this.state.x, p.y - this.state.y) <= 44;
      })
      .sort(
        (a, b) =>
          Math.hypot(
            cellPoint(a).x - this.state.x,
            cellPoint(a).y - this.state.y,
          ) -
          Math.hypot(
            cellPoint(b).x - this.state.x,
            cellPoint(b).y - this.state.y,
          ),
      );
  }
}
