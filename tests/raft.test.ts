import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { raftRules as R } from "../src/game/data/raftRules";
import {
  initialRaft,
  validRaft,
  raftObjects,
  raftAvailability,
  repairRaft,
  raftTarget,
  boardingPoint,
  landingPoint,
  raftLayers,
  cellPoint,
} from "../src/game/systems/RaftSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { findPath } from "../src/game/systems/MovementSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { emptyOutfit } from "../src/game/systems/ClothingSystem";
import { initialShip } from "../src/game/systems/ShipSystem";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const map: IslandMap = read("public/assets/maps/island.json"),
  items: RecoveredItem[] = read("public/assets/objects/objects.json");
const supplies = () =>
  Object.fromEntries(
    [...items[94].recipe, ...items[94].craftingTools].map((r) => [
      r.item,
      r.quantity,
    ]),
  );
const moored = { phase: "moored", index: R.index, facing: "left" } as const;
const aboard = { phase: "aboard", index: null, facing: "left" } as const;
const collision = (
  r = aboard as import("../src/game/systems/RaftSystem").RaftState,
) => new CollisionSystem(map, new Set(), raftObjects(map.layers.Objects, r));
const base: SaveData = {
  version: 1,
  ...cellPoint(R.index),
  inventory: {},
  collected: [],
  inspected: [],
  discovered: [],
  elapsed: 0,
  enhancements: true,
};
function setup() {
  store.setState({
    items,
    ready: true,
    started: true,
    saving: false,
    ship: initialShip(),
    raft: initialRaft(),
    worldMap: map,
    worldObjects: [...map.layers.Objects],
    x: 94 * 16 + 8,
    y: 93 * 16 + 8,
    survival: initialSurvival(),
    inventory: supplies(),
    panel: "dialogue",
    focus: null,
    collected: [],
    coconutGame: null,
    rodGame: null,
    fishingGame: null,
    huntingGame: null,
  });
}

test("raft evidence keeps the original swamp placement, restricted terrain and ordered source artwork", () => {
  assert.equal(R.index, 94 + 94 * 180);
  assert.equal(map.layers.Objects[R.index], 85);
  assert.equal(items[84].sprite, 393232);
  assert.equal(items[94].sprite, 393224);
  assert.deepEqual(R.waterTerrain, [12, 13, 14]);
  assert.equal(R.boardingTiles, 1);
  assert.deepEqual(R.layers.left[0], [393224, 196623, 393226, 393230, 393233]);
  assert.deepEqual(R.layers.right[1], [393225, 196619, 393227, 393229, 393234]);
  const atlas = read("public/assets/ui/atlas.json");
  for (const dir of ["left", "right"] as const)
    for (const pose of [0, 1] as const)
      for (const key of [
        ...R.layers[dir][pose],
        ...Object.values(R.clothing[dir][pose]),
      ])
        assert.ok(atlas.sheets[key >>> 16].frames[key & 65535]);
});
test("repair consumes six material groups, retains both tools and has source Energy/Morale effects exactly once", () => {
  const inv = supplies(),
    saved = structuredClone(inv),
    life = initialSurvival();
  const result = repairRaft(items, inv, life, initialRaft())!;
  assert.equal(result.survival.energy, 70);
  assert.equal(result.survival.morale, 10);
  assert.equal(result.survival.strength, 0);
  for (const r of items[94].recipe) assert.equal(result.inventory[r.item], 0);
  assert.equal(result.inventory[77], 1);
  assert.equal(result.inventory[78], 1);
  assert.equal(result.inventory[94], undefined);
  assert.deepEqual(inv, saved);
  assert.equal(life.energy, 100);
  assert.deepEqual(result.raft, moored);
  assert.equal(repairRaft(items, inv, life, result.raft), null);
});
test("missing each material/tool, rest and collapse reject repair; fatal repair still consumes its materials", () => {
  for (const r of [...items[94].recipe, ...items[94].craftingTools]) {
    const inv = supplies();
    inv[r.item]--;
    assert.equal(raftAvailability(items, inv, initialRaft()), false);
    assert.equal(
      repairRaft(items, inv, initialSurvival(), initialRaft()),
      null,
    );
  }
  for (const life of [
    { ...initialSurvival(), restRemainingMs: 1000 },
    { ...initialSurvival(), energy: 0, death: "energy" as const },
  ])
    assert.equal(repairRaft(items, supplies(), life, initialRaft()), null);
  const fatal = repairRaft(
    items,
    supplies(),
    { ...initialSurvival(), energy: 10 },
    initialRaft(),
  )!;
  assert.equal(fatal.survival.death, "energy");
  assert.equal(fatal.raft.phase, "moored");
  assert.equal(fatal.inventory[36], 0);
});
test("raft overlay relocates only raft occupancy, including a dock on a formerly collected cell; original maps stay immutable", () => {
  const original = [...map.layers.Objects],
    destination = 95 + 94 * 180;
  const moved = raftObjects(original, {
    ...moored,
    index: destination,
    facing: "right",
  });
  assert.equal(moved[R.index], 0);
  assert.equal(moved[destination], 95);
  assert.deepEqual(map.layers.Objects, original);
  assert.ok(
    original.every(
      (v, i) => i === R.index || i === destination || v === moved[i],
    ),
  );
  const sailing = raftObjects(moved, aboard);
  assert.equal(sailing[destination], 0);
  assert.equal(sailing[R.index], 0);
});
test("swamp traversal retains decor, rock and live object blockers; ocean and land remain forbidden aboard", () => {
  const m = structuredClone(map),
    objects = Array(32400).fill(0),
    c = new CollisionSystem(m, new Set(), objects),
    index = 100 + 100 * 180;
  c.mode = "raft";
  m.layers.Decor[index] = 0;
  m.layers.Rocks[index] = 0;
  for (let terrain = 0; terrain < 16; terrain++) {
    m.layers.FULL[index] = terrain;
    assert.equal(c.walkable(100, 100), [12, 13, 14].includes(terrain));
  }
  m.layers.FULL[index] = 14;
  m.layers.Decor[index] = 1;
  assert.equal(c.walkable(100, 100), false);
  m.layers.Decor[index] = 0;
  m.layers.Rocks[index] = 1;
  assert.equal(c.walkable(100, 100), false);
  m.layers.Rocks[index] = 0;
  objects[index] = 1;
  assert.equal(c.walkable(100, 100), false);
  objects[index] = 0;
  assert.equal(c.walkable(100, 100), true);
  assert.equal(c.walkableOnFoot(100, 100), false);
  for (const p of [
    [-1, 100],
    [180, 100],
    [100, -1],
    [100, 180],
  ])
    assert.equal(c.walkable(...(p as [number, number])), false);
});
test("boarding requires a cardinally adjacent clear foot tile and live raft; it reaches the original raft cell", () => {
  const c = collision(moored),
    point = boardingPoint(moored, 94 * 16 + 8, 93 * 16 + 8, c);
  assert.deepEqual(point, cellPoint(R.index));
  assert.equal(boardingPoint(moored, 93 * 16 + 8, 93 * 16 + 8, c), null);
  assert.equal(boardingPoint(moored, 94 * 16 + 8, 92 * 16 + 8, c), null);
  const empty = new CollisionSystem(map, new Set(), Array(32400).fill(0));
  assert.equal(boardingPoint(moored, 94 * 16 + 8, 93 * 16 + 8, empty), null);
  assert.equal(boardingPoint(initialRaft(), 94 * 16 + 8, 93 * 16 + 8, c), null);
});
test("landing preserves left/right/up/down priority and rejects blocked shores without losing the raft", () => {
  const c = collision(),
    point = cellPoint(R.index),
    land = landingPoint(aboard, point.x, point.y, c)!;
  assert.equal(land.direction, "up");
  assert.equal(land.y, 93 * 16 + 8);
  const m = structuredClone(map),
    objects = Array(32400).fill(0),
    center = 100 + 100 * 180;
  for (const i of [
    center,
    center - 1,
    center + 1,
    center - 180,
    center + 180,
  ]) {
    m.layers.FULL[i] = 9;
    m.layers.Decor[i] = 0;
    m.layers.Rocks[i] = 0;
  }
  m.layers.FULL[center] = 14;
  const both = new CollisionSystem(m, new Set(), objects),
    at = cellPoint(center);
  assert.equal(landingPoint(aboard, at.x, at.y, both)!.direction, "left");
  objects[center - 1] = 3;
  assert.equal(landingPoint(aboard, at.x, at.y, both)!.direction, "right");
  m.layers.Rocks[center + 1] = 1;
  m.layers.Decor[center - 180] = 2;
  m.layers.FULL[center + 180] = 14;
  assert.equal(landingPoint(aboard, at.x, at.y, both), null);
  assert.equal(landingPoint(moored, at.x, at.y, both), null);
  objects[center] = 3;
  assert.equal(landingPoint(aboard, at.x, at.y, both), null);
});
test("mouse pathfinding uses the raft predicate and never crosses shore or ocean", () => {
  const c = collision();
  c.mode = "raft";
  const from = cellPoint(R.index),
    to = cellPoint(R.index + 3);
  const path = findPath(c, from, to);
  assert.ok(path.length);
  assert.deepEqual(path.at(-1), to);
  assert.ok(path.every((p) => c.canStand(p.x, p.y)));
  assert.deepEqual(findPath(c, from, { x: 94 * 16 + 8, y: 93 * 16 + 8 }), []);
});
test("source paddle composition uses seven distance phases, opposite-facing body/shirt and original hat offsets, hiding pants/shoes", () => {
  const outfit = { hat: 112, shirt: 99, pants: 103, shoes: 107 };
  const left = raftLayers("left", 0, outfit),
    second = raftLayers("left", 3, outfit);
  assert.deepEqual(
    left.map((l) => l.sprite),
    [393224, 196623, 327725, 327927, 393226, 393230, 393233],
  );
  assert.deepEqual(left[3], { sprite: 327927, dx: -3, dy: -1 });
  assert.equal(second[2].sprite, 327726);
  assert.equal(second[3].dx, 0);
  assert.deepEqual(raftLayers("left", 7, outfit), left);
  assert.equal(raftLayers("right", 0, emptyOutfit()).length, 5);
});
test("store raft actions revalidate tools, live targets, board position, mode locks and survival before settlement", () => {
  setup();
  assert.equal(store.getState().repairRaft(R.index + 1), false);
  assert.equal(store.getState().boardRaft(R.index), false);
  assert.ok(store.getState().repairRaft(R.index));
  assert.equal(store.getState().repairRaft(R.index), false);
  // Model the scene-owned runtime overlay, as with collection tombstones.
  store.setState({
    worldObjects: raftObjects(map.layers.Objects, store.getState().raft),
  });
  assert.ok(store.getState().boardRaft(R.index));
  assert.equal(store.getState().y, 94 * 16 + 8);
  assert.equal(store.getState().raft.phase, "aboard");
  store.setState({
    worldObjects: raftObjects(map.layers.Objects, store.getState().raft),
  });
  assert.equal(store.getState().collect(91 + 94 * 180, 13), false);
  assert.equal(store.getState().repairShip(5552), false);
  store.setState({
    survival: { ...initialSurvival(), death: "energy", energy: 0 },
  });
  assert.equal(store.getState().landRaft(), false);
  store.setState({ survival: initialSurvival() });
  assert.ok(store.getState().landRaft());
  assert.deepEqual(store.getState().raft, moored);
  assert.equal(store.getState().y, 93 * 16 + 8);
  assert.equal(store.getState().landRaft(), false);
});
test("optional version-one raft state validates mode/index/orientation and supports saves afloat, moved docks and legacy loading", async () => {
  for (const raft of [initialRaft(), moored, aboard])
    assert.ok(validSave({ ...base, raft }));
  for (const raft of [
    null,
    {},
    { ...aboard, index: R.index },
    { ...moored, index: -1 },
    { ...moored, index: 32400 },
    { ...moored, index: 1.5 },
    { ...moored, facing: "up" },
    { ...initialRaft(), index: 0 },
    { ...aboard, phase: "fly" },
  ])
    assert.equal(validRaft(raft), false);
  assert.ok(
    !validSave({
      ...base,
      raft: aboard,
      ship: { phase: "escaped", elapsedMs: 8001 },
    }),
  );
  setup();
  const originalLoad = SaveSystem.load,
    originalSave = SaveSystem.save;
  let saved: SaveData | null = null;
  try {
    SaveSystem.save = async (data) => {
      saved = structuredClone(data);
    };
    SaveSystem.load = async () => saved;
    store.setState({ raft: aboard, ...cellPoint(R.index), panel: "settings" });
    await store.getState().save();
    assert.equal(saved!.raft!.phase, "aboard");
    store.setState({ raft: initialRaft() });
    await store.getState().load();
    assert.deepEqual(store.getState().raft, aboard);
    saved = {
      ...base,
      raft: { ...moored, index: R.index + 1, facing: "right" },
    };
    await store.getState().load();
    assert.equal(store.getState().raft.index, R.index + 1);
    assert.equal(store.getState().raft.facing, "right");
    saved = { ...base };
    await store.getState().load();
    assert.deepEqual(store.getState().raft, initialRaft());
  } finally {
    SaveSystem.load = originalLoad;
    SaveSystem.save = originalSave;
  }
});
