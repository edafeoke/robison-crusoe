import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { createHash } from "node:crypto";
import {
  decodeLayer,
  LAYERS,
  validateMap,
  tileIndex,
} from "../src/game/maps/MapDecoder";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { findPath } from "../src/game/systems/MovementSystem";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../src/game/assets/assetManifest";
const map = validateMap(
  JSON.parse(readFileSync("public/assets/maps/island.json", "utf8")),
);
const atlas: AtlasManifest = JSON.parse(
  readFileSync("public/assets/ui/atlas.json", "utf8"),
);
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
test("all five JSON layers round-trip to the original bytes and SHA-256", () => {
  for (const name of LAYERS) {
    const source = readFileSync(`evidence/original/BIGisland${name}.byt`);
    assert.equal(source.length, 32400);
    const recovered = Buffer.from(map.layers[name]);
    assert.deepEqual(recovered, source);
    assert.equal(
      createHash("sha256").update(recovered).digest("hex"),
      map.sha256[name],
    );
    const raw = readFileSync(`public/assets/maps/BIGisland${name}.byt`);
    assert.deepEqual(raw, source);
    assert.deepEqual(
      decodeLayer(
        raw.buffer.slice(raw.byteOffset, raw.byteOffset + raw.byteLength),
      ),
      new Uint8Array(source),
    );
  }
});
test("rejects malformed map bytes and JSON", () => {
  assert.throws(() => decodeLayer(new ArrayBuffer(32399)));
  assert.throws(() => validateMap({ ...map, width: 181 }));
  assert.throws(() =>
    validateMap({ ...map, layers: { ...map.layers, FULL: [-1] } }),
  );
  assert.equal(tileIndex(8, 148), 26648);
});
test("all 778 recovered atlas regions fit and all copied sheets retain original hashes", () => {
  assert.equal(
    atlas.sheets.reduce((n, s) => n + s.frames.length, 0),
    778,
  );
  for (const s of atlas.sheets) {
    const source = readFileSync(`evidence/original/data/BS${s.bank}.png`);
    assert.deepEqual(readFileSync(`public${s.url}`), source);
    for (const f of s.frames) {
      assert.ok(f.x >= 0 && f.y >= 0 && f.width > 0 && f.height > 0);
      assert.ok(f.x + f.width <= s.width && f.y + f.height <= s.height);
    }
  }
  for (const c of Object.values(atlas.composites).flat()) {
    assert.ok(atlas.sheets[c.sprite >>> 16].frames[c.sprite & 65535]);
  }
});
test("recipes preserve zero-based original IDs and quantities", () => {
  assert.equal(items.length, 120);
  assert.equal(items[3].name, "Stone");
  assert.deepEqual(items[5].recipe, [
    { item: 3, quantity: 1 },
    { item: 8, quantity: 1 },
    { item: 6, quantity: 1 },
  ]);
  assert.deepEqual(items[91].recipe, [
    { item: 6, quantity: 2 },
    { item: 8, quantity: 1 },
  ]);
  assert.equal(items[3].mapId, 4);
  assert.equal(items[18].name, "Pirate shipwreck");
});
test("collision uses original terrain, rocks and occupancy; removed objects open the runtime grid only", () => {
  const c = new CollisionSystem(map);
  assert.ok(c.walkable(8, 148));
  assert.ok(!c.walkable(-1, 0));
  assert.ok(!c.walkable(180, 20));
  for (let i = 0; i < 32400; i++) {
    if ([5, 6, 12, 13, 14].includes(map.layers.FULL[i]))
      assert.ok(!c.walkable(i % 180, Math.floor(i / 180)));
  }
  const i = map.layers.Objects.findIndex(
    (v, i) =>
      v !== 0 &&
      map.layers.Decor[i] === 0 &&
      ![5, 6, 12, 13, 14].includes(map.layers.FULL[i]) &&
      map.layers.Rocks[i] === 0,
  );
  assert.ok(i >= 0);
  assert.ok(!c.walkable(i % 180, Math.floor(i / 180)));
  c.removed.add(i);
  assert.ok(c.walkable(i % 180, Math.floor(i / 180)));
  assert.notEqual(map.layers.Objects[i], 0);
});
test("pathfinding reaches an actual nearby original object without entering blocked cells", () => {
  const c = new CollisionSystem(map),
    start = { x: 136, y: 2376 };
  let destination: { x: number; y: number } | null = null,
    path: { x: number; y: number }[] = [];
  for (let y = 143; y < 154 && !path.length; y++)
    for (let x = 8; x < 27 && !path.length; x++) {
      if (!c.walkable(x, y)) continue;
      destination = { x: x * 16 + 8, y: y * 16 + 8 };
      path = findPath(c, start, destination);
    }
  assert.ok(path.length > 0);
  assert.deepEqual(path.at(-1), destination);
  let previous = start;
  for (const p of path) {
    assert.ok(c.walkable(Math.floor(p.x / 16), Math.floor(p.y / 16)));
    assert.equal(Math.abs(p.x - previous.x) + Math.abs(p.y - previous.y), 16);
    previous = p;
  }
  assert.deepEqual(findPath(c, start, { x: -16, y: -16 }), []);
});
test("crafting consumes exact ingredients atomically and rejects unavailable and unsupported recipes", async () => {
  const { useGameStore } = await import("../src/store/gameStore");
  useGameStore.setState({
    items,
    inventory: { 3: 1, 8: 1, 6: 1 },
    ready: true,
    started: true,
    worldObjects: map.layers.Objects,
    x: 23 * 16 + 8,
    y: 148 * 16 + 8,
    collected: [],
  });
  assert.equal(useGameStore.getState().craft(5), true);
  assert.equal(useGameStore.getState().inventory[5], 1);
  assert.equal(useGameStore.getState().inventory[3], 0);
  const before = { ...useGameStore.getState().inventory };
  assert.equal(useGameStore.getState().craft(5), false);
  assert.deepEqual(useGameStore.getState().inventory, before);
  assert.equal(useGameStore.getState().craft(9), false);
  assert.deepEqual(useGameStore.getState().inventory, before);
  assert.equal(useGameStore.getState().collect(23 + 147 * 180, 3), true);
  assert.equal(useGameStore.getState().collect(23 + 147 * 180, 3), false);
  assert.equal(
    useGameStore.getState().inventory[3],
    1,
    "the same world cell must never yield twice",
  );
});
