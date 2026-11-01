import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { tideRules as R } from "../src/game/data/tideRules";
import {
  tideAt,
  tideChangesIn,
  tidalMap,
  tideRescue,
  sharkTideDestination,
} from "../src/game/systems/TideSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { WildlifeSystem } from "../src/game/systems/WildlifeSystem";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import { cellPoint } from "../src/game/systems/RaftSystem";
import { validSave } from "../src/game/systems/SaveSystem";
import { SHORE_SEQUENCES } from "../src/game/data/shoreSequences";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const source: IslandMap = read("public/assets/maps/island.json"),
  manifest = read("public/assets/animals/wildlife.json");
function blank() {
  const map = structuredClone(source);
  for (const key of ["FULL", "Rocks", "Decor", "Objects"] as const)
    map.layers[key].fill(key === "FULL" ? 5 : 0);
  return map;
}
test("tides alternate on the exact recovered minute bands, including the hour boundary", () => {
  assert.equal(R.bandMinutes, 6);
  assert.equal(R.cycleMinutes, 12);
  for (let minute = 0; minute < 120; minute++)
    assert.equal(
      tideAt(minute * 60),
      Math.floor(minute / 6) % 2 ? "low" : "high",
    );
  assert.equal(tideAt(359.999), "high");
  assert.equal(tideAt(360), "low");
  assert.equal(tideAt(719.999), "low");
  assert.equal(tideAt(720), "high");
  assert.equal(tideChangesIn(359.5), 0.5);
  assert.equal(tideChangesIn(360), 360);
  assert.equal(tideAt(86400), "high");
});
test("all original terrain/border bytes remap exactly once without modifying any source layer; high tide restores them", () => {
  const saved = structuredClone(source),
    low = tidalMap(source, "low"),
    high = tidalMap(source, "high");
  assert.deepEqual(R.lowTerrain, { 4: 0, 5: 4, 6: 5 });
  assert.deepEqual(R.lowBorders, {
    7: 0,
    19: 7,
    22: 19,
    6: 0,
    18: 6,
    21: 18,
    5: 0,
    17: 5,
    20: 17,
  });
  for (let i = 0; i < 32400; i++) {
    assert.equal(
      low.layers.FULL[i],
      ({ 4: 0, 5: 4, 6: 5 } as Record<number, number>)[source.layers.FULL[i]] ??
        source.layers.FULL[i],
    );
    assert.equal(
      low.layers.Borders[i],
      (R.lowBorders as Record<number, number>)[source.layers.Borders[i]] ??
        source.layers.Borders[i],
    );
  }
  for (const key of ["Objects", "Decor", "Rocks"] as const)
    assert.deepEqual(low.layers[key], source.layers[key]);
  assert.deepEqual(high, source);
  assert.deepEqual(source, saved);
  const atlas = read("public/assets/ui/atlas.json");
  for (const key of Object.values(R.lowBorders))
    if (key) {
      if (SHORE_SEQUENCES[key])
        for (const frame of SHORE_SEQUENCES[key])
          assert.ok(atlas.sheets[11].frames[frame]);
      else assert.ok(atlas.borders[key - 1]);
    }
});
test("the newly exposed tide flats become walkable while rocks, vegetation and live objects remain blockers", () => {
  const index = source.layers.FULL.findIndex(
    (v, i) =>
      v === 5 &&
      !source.layers.Rocks[i] &&
      !source.layers.Decor[i] &&
      !source.layers.Objects[i],
  );
  assert.ok(index >= 0);
  const objects = Array(32400).fill(0),
    high = new CollisionSystem(tidalMap(source, "high"), new Set(), objects),
    low = new CollisionSystem(tidalMap(source, "low"), new Set(), objects),
    x = index % 180,
    y = Math.floor(index / 180);
  assert.equal(high.walkable(x, y), false);
  assert.equal(low.walkable(x, y), true);
  objects[index] = 1;
  assert.equal(low.walkable(x, y), false);
  objects[index] = 0;
  low.mode = "raft";
  assert.equal(low.walkable(x, y), false);
});
test("flood rescue keeps source ring order and axial preference and refuses occupied, rocky or decorated shallow shore", () => {
  const map = blank(),
    objects = Array(32400).fill(0),
    at = 100 + 100 * 180,
    p = cellPoint(at);
  map.layers.FULL[at - 181] = 4;
  map.layers.FULL[at - 180] = 4;
  assert.deepEqual(tideRescue(map, objects, p.x, p.y), cellPoint(at - 180));
  objects[at - 180] = 3;
  assert.deepEqual(tideRescue(map, objects, p.x, p.y), cellPoint(at - 181));
  map.layers.Rocks[at - 181] = 10;
  assert.equal(tideRescue(map, objects, p.x, p.y), null); // source requires rock0, not merely passable
  map.layers.Rocks[at - 181] = 0;
  map.layers.Decor[at - 181] = 1;
  assert.equal(tideRescue(map, objects, p.x, p.y), null);
  map.layers.Decor[at - 181] = 0;
  map.layers.FULL[at] = 4;
  assert.equal(tideRescue(map, objects, p.x, p.y), null);
});
test("source flood radius is fourteen tiles with its explicit southeast island exception", () => {
  const map = blank(),
    objects = Array(32400).fill(0),
    at = 100 + 100 * 180,
    p = cellPoint(at);
  map.layers.FULL[at + 15] = 4;
  assert.equal(tideRescue(map, objects, p.x, p.y), null);
  map.layers.FULL[at + 14] = 4;
  assert.deepEqual(tideRescue(map, objects, p.x, p.y), cellPoint(at + 14));
  assert.deepEqual(
    tideRescue(map, objects, 172 * 16 + 8, 108 * 16 + 8),
    cellPoint(166 + 101 * 180),
  );
});
test("shark displacement retains the source nested NW/SE search, exclusions and bounded failure", () => {
  const map = blank(),
    objects = Array(32400).fill(1),
    at = 100 + 100 * 180,
    p = cellPoint(at);
  map.layers.FULL.fill(4);
  const north = at - 180,
    south = at + 180;
  map.layers.FULL[north] = 5;
  map.layers.FULL[south] = 6;
  objects[north] = 0;
  objects[south] = 0;
  assert.deepEqual(
    sharkTideDestination(map, objects, p.x, p.y),
    cellPoint(north),
  );
  assert.deepEqual(
    sharkTideDestination(map, objects, p.x, p.y, cellPoint(north)),
    cellPoint(south),
  );
  objects[north] = 1;
  objects[south] = 1;
  assert.equal(sharkTideDestination(map, objects, p.x, p.y), null);
});
test("tide reconciliation preserves wildlife identities, counts and tombstones while moving a stranded shark into real deep water", () => {
  const w = new WildlifeSystem(source, manifest),
    shark = w.actors.find((a) => a.mapId === 56)!,
    before = w.snapshot(),
    map = tidalMap(source, "high"),
    i = Math.floor(shark.x / 16) + Math.floor(shark.y / 16) * 180;
  map.layers.FULL[i] = 4; // a controlled recession at an actual shark cell
  const dest = sharkTideDestination(map, w.objects, shark.x, shark.y);
  assert.ok(dest);
  w.applyTide(map, { x: 136, y: 2376 });
  assert.deepEqual({ x: shark.x, y: shark.y }, dest);
  assert.equal(w.objects[i], 0);
  assert.deepEqual(
    w.actors.map((a) => a.id),
    before.map((a) => a.id),
  );
  assert.equal(
    w.objects[Math.floor(shark.x / 16) + Math.floor(shark.y / 16) * 180],
    56,
  );
  assert.notEqual(source.layers.FULL[i], 4);
});
test("saved elapsed play determines the tide without a second drifting timer or any legacy schema change", () => {
  const base = {
    version: 1,
    x: 136,
    y: 2376,
    inventory: {},
    collected: [],
    inspected: [],
    discovered: [],
    elapsed: 360,
    enhancements: true,
  };
  assert.ok(validSave(base));
  assert.equal(tideAt(JSON.parse(JSON.stringify(base)).elapsed), "low");
  assert.equal(tideAt(0), "high");
  assert.ok(validSave({ ...base, elapsed: 720 }));
  assert.equal(tideAt(720), "high");
});
test("route audit records the tide-dependent Saw/Hammer access without claiming ship or Sextant access", () => {
  const audit = read("evidence/tide-access-audit.json");
  const [high, low] = audit.phases;
  const route: number[] = audit.rawSawRoute,
    c = new CollisionSystem(tidalMap(source, "low"));
  assert.equal(route.length, 67);
  assert.equal(route[0], 8 + 148 * 180);
  route.forEach((i, n) => {
    assert.ok(c.walkable(i % 180, Math.floor(i / 180)));
    if (n)
      assert.equal(
        Math.abs((i % 180) - (route[n - 1] % 180)) +
          Math.abs(Math.floor(i / 180) - Math.floor(route[n - 1] / 180)),
        1,
      );
  });
  const last = route.at(-1)!;
  assert.ok(
    Math.hypot((last % 180) - 50, Math.floor(last / 180) - 164) * 16 <= 32,
  );
  for (const phase of [high, low]) {
    assert.equal(phase.raftCells, 2274);
    for (const id of [19, 23])
      assert.equal(
        phase.withRaftChecks.find((c: { mapId: number }) => c.mapId === id)
          .reachableIgnoringObjects,
        true,
      );
  }
  assert.equal(high.cells, 13251);
  assert.equal(low.cells, 17813);
  for (const id of [79, 78]) {
    assert.equal(
      high.checks.find((c: { mapId: number }) => c.mapId === id)
        .reachableIgnoringObjects,
      false,
    );
    assert.equal(
      low.checks.find((c: { mapId: number }) => c.mapId === id)
        .reachableIgnoringObjects,
      true,
    );
  }
  for (const id of [19, 23])
    assert.equal(
      low.checks.find((c: { mapId: number }) => c.mapId === id)
        .reachableIgnoringObjects,
      false,
    );
});

test("flooded passive wildlife returns to its original home without discarding the saved population", () => {
  const original = new WildlifeSystem(source, manifest),
    actor = original.actors.find(
      (a) => a.profile.sourceClass === "l" && source.layers.Decor[a.id] === 0,
    )!,
    snapshot = original.snapshot();
  const target = source.layers.FULL.findIndex(
    (v, i) =>
      v === 5 &&
      !source.layers.Decor[i] &&
      !source.layers.Rocks[i] &&
      !source.layers.Objects[i],
  );
  assert.ok(actor && target >= 0);
  const saved = snapshot.find((a) => a.id === actor.id)!;
  Object.assign(saved, cellPoint(target));
  const restored = new WildlifeSystem(
    source,
    manifest,
    new Set(),
    snapshot,
    undefined,
    true,
  );
  assert.deepEqual(
    {
      x: restored.actors.find((a) => a.id === actor.id)!.x,
      y: restored.actors.find((a) => a.id === actor.id)!.y,
    },
    cellPoint(target),
  );
  restored.applyTide(source, { x: 136, y: 2376 });
  const home = restored.actors.find((a) => a.id === actor.id)!;
  assert.deepEqual({ x: home.x, y: home.y }, cellPoint(actor.id));
  assert.equal(restored.objects[target], 0);
  assert.equal(restored.actors.length, 326);
});
