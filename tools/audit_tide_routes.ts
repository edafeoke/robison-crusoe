/** Optimistic route diagnosis: ignore Objects, retain original Decor/Rocks, no random source object reshuffle. */
import { readFileSync, writeFileSync } from "node:fs";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import { tidalMap, type TidePhase } from "../src/game/systems/TideSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
const source: IslandMap = JSON.parse(
  readFileSync("public/assets/maps/island.json", "utf8"),
);
const moves = [
  [-1, 0],
  [1, 0],
  [0, -1],
  [0, 1],
];
function flood(c: CollisionSystem, starts: Set<number>) {
  const queue = [...starts],
    seen = new Set(starts);
  for (let n = 0; n < queue.length; n++) {
    const i = queue[n],
      x = i % 180,
      y = Math.floor(i / 180);
    for (const [dx, dy] of moves) {
      const k = x + dx + (y + dy) * 180;
      if (!seen.has(k) && c.walkable(x + dx, y + dy)) {
        seen.add(k);
        queue.push(k);
      }
    }
  }
  return seen;
}
function checks(seen: Set<number>) {
  return [19, 85, 79, 78, 81, 24, 23, 80].flatMap((id) =>
    source.layers.Objects.flatMap((v, index) => {
      if (v !== id) return [];
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
      return [{ mapId: id, index, x, y, reachableIgnoringObjects: reachable }];
    }),
  );
}
const phases = (["high", "low"] as TidePhase[]).map((phase) => {
  const c = new CollisionSystem(
    tidalMap(source, phase),
    new Set(),
    Array(32400).fill(0),
  );
  const foot = flood(c, new Set([8 + 148 * 180]));
  c.mode = "raft";
  const water = flood(c, new Set([94 + 94 * 180]));
  c.mode = "walk";
  const shores = new Set([8 + 148 * 180]);
  for (const i of water)
    for (const [dx, dy] of moves) {
      const x = (i % 180) + dx,
        y = Math.floor(i / 180) + dy;
      if (c.walkable(x, y)) shores.add(x + y * 180);
    }
  const withRaft = flood(c, shores);
  return {
    phase,
    cells: foot.size,
    checks: checks(foot),
    raftCells: water.size,
    withRaftFootCells: withRaft.size,
    withRaftChecks: checks(withRaft),
  };
});
const lowCollision = new CollisionSystem(tidalMap(source, "low")),
  queue = [8 + 148 * 180],
  parents = new Map([[queue[0], -1]]);
let rawSawRoute: number[] = [];
for (let n = 0; n < queue.length; n++) {
  const i = queue[n],
    x = i % 180,
    y = Math.floor(i / 180);
  if (Math.hypot(x - 50, y - 164) * 16 <= 32) {
    let cell = i;
    while (cell !== -1) {
      rawSawRoute.unshift(cell);
      cell = parents.get(cell)!;
    }
    break;
  }
  for (const [dx, dy] of [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1],
  ]) {
    const next = x + dx + (y + dy) * 180;
    if (!parents.has(next) && lowCollision.walkable(x + dx, y + dy)) {
      parents.set(next, i);
      queue.push(next);
    }
  }
}
writeFileSync(
  "evidence/tide-access-audit.json",
  JSON.stringify(
    {
      scope:
        "Fixed phases, Objects ignored, original Decor/Rocks retained. Foot-only checks start at spawn. withRaft assumes the original raft has already been repaired and uses only its connected swamp component and adjacent shores; no boat carrying over land. No decor clearing, source object reshuffle or material acquisition simulation.",
      phases,
      rawSawRoute,
    },
    null,
    2,
  ) + "\n",
);
console.log(
  phases.map((p) => ({
    phase: p.phase,
    cells: p.cells,
    unreachable: p.checks
      .filter((c) => !c.reachableIgnoringObjects)
      .map((c) => c.mapId),
  })),
);
