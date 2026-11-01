/** Diagnostic upper bound for the current fixed-terrain walking port, not an original-game rule. */
import { readFileSync, writeFileSync } from "node:fs";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import type { IslandMap } from "../src/game/maps/MapDecoder";
const map: IslandMap = JSON.parse(
  readFileSync("public/assets/maps/island.json", "utf8"),
);
const collision = new CollisionSystem(map, new Set(), Array(32400).fill(0));
const seen = new Set([8 + 148 * 180]),
  queue = [8 + 148 * 180];
for (let cursor = 0; cursor < queue.length; cursor++) {
  const i = queue[cursor],
    x = i % 180,
    y = Math.floor(i / 180);
  for (const [dx, dy] of [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1],
  ]) {
    const next = x + dx + (y + dy) * 180;
    if (!seen.has(next) && collision.walkable(x + dx, y + dy)) {
      seen.add(next);
      queue.push(next);
    }
  }
}
const checks = [19, 85, 79, 78, 81, 24, 23, 80].flatMap((mapId) =>
  map.layers.Objects.flatMap((value, index) => {
    if (value !== mapId) return [];
    const x = index % 180,
      y = Math.floor(index / 180);
    let reachable = false;
    for (let dy = -2; dy <= 2; dy++)
      for (let dx = -2; dx <= 2; dx++)
        if (
          Math.hypot(dx * 16, dy * 16) <= 44 &&
          seen.has(x + dx + (y + dy) * 180)
        )
          reachable = true;
    return [{ mapId, index, x, y, reachableIgnoringAllObjects: reachable }];
  }),
);
writeFileSync(
  "evidence/ship-access-audit.json",
  JSON.stringify(
    {
      scope:
        "Current fixed-terrain browser walking collision; all Objects ignored optimistically; no tides, raft or decor-clearing simulation. Not proof of original Java game reachability.",
      walkingComponent: seen.size,
      checks,
    },
    null,
    2,
  ) + "\n",
);
console.log(
  `Recorded ${checks.length} navigation dependencies in the current walking port.`,
);
