import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import type { AtlasManifest } from "../src/game/assets/assetManifest";
import type { WildlifeManifest } from "../src/game/entities/WildlifeState";
import { validWildlife } from "../src/game/entities/WildlifeState";
import { WildlifeSystem } from "../src/game/systems/WildlifeSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { nearestObject } from "../src/game/systems/InteractionSystem";
import { validSave, type SaveData } from "../src/game/systems/SaveSystem";
const read = (path: string) => JSON.parse(readFileSync(path, "utf8"));
const manifest: WildlifeManifest = read("public/assets/animals/wildlife.json");
const original: IslandMap = read("public/assets/maps/island.json");
const atlas: AtlasManifest = read("public/assets/ui/atlas.json");
const far = { x: 8, y: 8 };
function world(mapId: number, terrain = 1) {
  const map = structuredClone(original);
  for (const key of ["FULL", "Objects", "Decor", "Rocks", "Borders"] as const)
    map.layers[key].fill(key === "FULL" ? terrain : 0);
  map.layers.Objects[100 + 100 * 180] = mapId;
  const system = new WildlifeSystem(map, manifest);
  return { map, system, actor: system.actors[0] };
}
test("16 wildlife profiles preserve constructor IDs, tiers, arrays and original packed regions", () => {
  const source = readFileSync("evidence/decompiled/data/g.java", "utf8");
  assert.equal(manifest.species.length, 16);
  assert.equal(manifest.tickMs, 90);
  assert.match(
    readFileSync("evidence/decompiled/data/p.java", "utf8"),
    /this.b\(11\)/,
  );
  for (const profile of manifest.species) {
    const start = source.indexOf(
      `case ${profile.mapId}: {`,
      source.indexOf("int n19 = 0;"),
    );
    const block = source.slice(
      start,
      source.indexOf("\n                }", start),
    );
    assert.match(block, new RegExp(`new ${profile.sourceClass}\\(`));
    assert.ok(block.includes(`, ${profile.speedTier}, `));
    for (const key of Object.values(profile.frames).flat()) {
      assert.ok(block.includes(String(key)));
      assert.ok(atlas.sheets[key >>> 16].frames[key & 65535]);
    }
  }
  assert.deepEqual(
    manifest.species.find((p) => p.mapId === 53)!.frames.flyLeft,
    [131146, 131147],
  );
  assert.deepEqual(
    manifest.species.find((p) => p.mapId === 56)!.frames.right,
    [131165, 131166, 131167],
  );
  assert.equal(new WildlifeSystem(original, manifest).actors.length, 326);
});
test("nearby prey flees, predators pursue and passive wildlife continues resting", () => {
  for (const [mapId, direction] of [
    [5, 1],
    [44, -1],
    [42, 0],
  ] as const) {
    const { system, actor } = world(mapId);
    actor.phase = "rest";
    actor.duration = 5000;
    system.update(90, { x: actor.x - 32, y: actor.y });
    assert.equal(Math.sign(actor.x - 1608), direction);
    assert.equal(
      actor.reaction,
      mapId === 5 ? "flee" : mapId === 44 ? "chase" : null,
    );
  }
});
test("birds release ground occupancy in flight and cannot land on blocked cells or Robinson", () => {
  const { system, actor, map } = world(53);
  const home = actor.id;
  system.update(90, { x: actor.x - 32, y: actor.y });
  assert.equal(actor.phase, "fly");
  assert.equal(system.objects[home], 0);
  assert.equal(map.layers.Objects[home], 53);
  assert.ok([131148, 131149].includes(system.sprite(actor)));
  actor.duration = 0;
  actor.remainingX = actor.remainingY = 0;
  system.update(90, { x: actor.x, y: actor.y });
  assert.equal(
    actor.phase,
    "fly",
    "cannot land on Robinson even when the ground cell is empty",
  );
  system.objects.fill(99);
  for (let i = 0; i < 180; i++) system.update(90, far);
  assert.equal(actor.phase, "fly");
  system.objects.fill(0);
  for (let i = 0; i < 180 && actor.phase === "fly"; i++) system.update(90, far);
  assert.notEqual(actor.phase, "fly");
  assert.equal(
    system.objects[Math.floor(actor.x / 16) + Math.floor(actor.y / 16) * 180],
    53,
  );
});
test("runtime occupancy follows wildlife without ghost blocks, overwriting static objects or ignoring previously collected cells", () => {
  const { system, actor, map } = world(42);
  const old = actor.id;
  actor.remainingX = 40;
  actor.remainingY = 0;
  system.objects[old + 2] = 4;
  const collision = new CollisionSystem(
    map,
    new Set([old + 1]),
    system.objects,
  );
  for (let i = 0; i < 20; i++) system.update(90, far);
  assert.equal(system.objects[old], 0);
  assert.equal(system.objects[old + 1], 42);
  assert.equal(system.objects[old + 2], 4);
  assert.ok(collision.walkable(100, 100));
  assert.ok(
    !collision.walkable(101, 100),
    "a removed resource cell can be occupied by a moving animal",
  );
  assert.equal(
    nearestObject(map, actor.x, actor.y, new Set([old + 1]), 13, system.objects)
      ?.itemId,
    41,
  );
});
test("sharks stay in deep water, ground animals reject water, and alligators retain their distinct terrain rule", () => {
  for (const [mapId, terrain, canCross] of [
    [56, 5, true],
    [56, 1, false],
    [42, 5, false],
    [43, 5, true],
    [44, 5, false],
    [44, 6, true],
  ] as const) {
    const { system, actor } = world(mapId, terrain);
    actor.remainingX = 40;
    actor.remainingY = 0;
    for (let i = 0; i < 12; i++) system.update(90, far);
    assert.equal(
      Math.floor(actor.x / 16) > 100,
      canCross,
      `${mapId} on terrain ${terrain}`,
    );
  }
});
test("wildlife uses fixed source ticks, freezes outside the view, and leaves original map layers unchanged", () => {
  const source = JSON.stringify(original.layers);
  const system = new WildlifeSystem(original, manifest),
    before = system.snapshot();
  system.update(89, far);
  assert.deepEqual(system.snapshot(), before);
  system.update(0, far);
  assert.deepEqual(system.snapshot(), before);
  system.update(1, far, { x: 4000, y: 4000, width: 100, height: 100 });
  assert.deepEqual(system.snapshot(), before);
  for (let i = 0; i < 1500; i++) system.update(90, { x: 136, y: 2376 });
  assert.equal(JSON.stringify(original.layers), source);
  const cells = new Set<number>();
  for (const a of system.actors) {
    assert.ok(a.x >= 0 && a.y >= 0 && a.x < 2880 && a.y < 2880);
    if (a.phase === "fly") continue;
    const i = Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180;
    assert.ok(!cells.has(i), "no two ground animals occupy one tile");
    cells.add(i);
    assert.equal(system.objects[i], a.mapId);
  }
});
test("wildlife saves restore position, animation, behavior and deterministic continuation", () => {
  const system = new WildlifeSystem(original, manifest);
  for (let i = 0; i < 100; i++) system.update(90, { x: 136, y: 2376 });
  const snapshot = system.snapshot();
  assert.ok(validWildlife(snapshot));
  const restored = new WildlifeSystem(original, manifest, new Set(), snapshot);
  assert.deepEqual(restored.snapshot(), snapshot);
  for (let i = 0; i < 100; i++) {
    system.update(90, far);
    restored.update(90, far);
  }
  assert.deepEqual(restored.snapshot(), system.snapshot());
});
test("old saves remain valid; malformed, duplicate and incompatible wildlife data is rejected safely", () => {
  const saved: SaveData = {
    version: 1,
    x: 136,
    y: 2376,
    inventory: {},
    collected: [],
    inspected: [],
    discovered: [],
    elapsed: 0,
    enhancements: true,
  };
  assert.ok(validSave(saved));
  const { system, actor } = world(53),
    snapshot = system.snapshot();
  assert.ok(validSave({ ...saved, wildlife: snapshot }));
  for (const invalid of [
    [{ ...snapshot[0], x: NaN }],
    [{ ...snapshot[0], phase: "hunt" }],
    [{ ...snapshot[0], seed: 0 }],
    [...snapshot, ...snapshot],
  ])
    assert.ok(!validSave({ ...saved, wildlife: invalid }));
  const bad = structuredClone(snapshot);
  bad[0].mapId = 44;
  const restored = new WildlifeSystem(system.map, manifest, new Set(), bad);
  assert.equal(restored.actors[0].mapId, actor.mapId);
  const prey = world(5),
    flight = prey.system.snapshot();
  flight[0].phase = "fly";
  assert.equal(
    new WildlifeSystem(prey.map, manifest, new Set(), flight).actors[0].phase,
    "walk",
  );
});
