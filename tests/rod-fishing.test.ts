import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { rodFishingRules as R } from "../src/game/data/rodFishingRules";
import {
  createRodFishingGame,
  stepRodFishing,
  strikeRod,
  rodFishingOutcome,
  validRodShore,
  rodTop,
  rodSide,
  rodBottom,
  type RodFishingGame,
  type RodDirection,
} from "../src/game/systems/RodFishingSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { findPath } from "../src/game/systems/MovementSystem";
import { nearestInteraction } from "../src/game/systems/InteractionSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { SaveSystem } from "../src/game/systems/SaveSystem";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../src/game/assets/assetManifest";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8")),
  map = read("public/assets/maps/island.json"),
  items: RecoveredItem[] = read("public/assets/objects/objects.json"),
  shore = 8 + 151 * 180;
function catchFish(seed: number) {
  let s = createRodFishingGame(shore, seed);
  for (let i = 0; i < 1200 && s.status === "playing"; i++) {
    let dir: RodDirection = null;
    const p = R.fish[s.species];
    if (s.phase === "hooked") dir = "up";
    else {
      if (
        Math.abs(s.fishX) < 40 &&
        Math.abs(s.hookX - s.fishX) < (p.width >> 1) - 2 &&
        Math.abs(s.hookY - s.fishY) < (p.height >> 1) - 2
      )
        s = strikeRod(s);
      const dx = s.fishX + s.travel * 4 - s.floatX,
        dy = s.fishY - s.hookY;
      if (Math.abs(dx) > 2) dir = dx < 0 ? "left" : "right";
      else if (Math.abs(dy) > 1) dir = dy < 0 ? "up" : "down";
    }
    s = stepRodFishing(s, 90, dir);
  }
  return s;
}
test("rod recovery preserves four original species/hitboxes, source tool/bait/terrain and exact packed frames", () => {
  const x = readFileSync("evidence/decompiled/data/x.java", "utf8"),
    v = readFileSync("evidence/bytecode/data.v.txt", "utf8");
  assert.match(x, /this\.a\.m\[n2\] == 90/);
  assert.match(x, /this\.d = new int\[5\]/);
  assert.match(v, /Method data\/x.b:\(\)V/);
  assert.deepEqual(
    R.fish.map((p) => [p.item, p.width, p.height]),
    [
      [40, 16, 10],
      [56, 16, 9],
      [57, 16, 12],
      [58, 20, 14],
    ],
  );
  assert.equal(items[14].name, "Fishing rod");
  assert.equal(items[90].name, "Worms");
  assert.equal(items[14].effects.energy, -5);
  assert.equal(items[14].effects.strength, 1);
  const atlas: AtlasManifest = read("public/assets/ui/atlas.json");
  for (const key of [
    R.floatSprite,
    R.toolSprite,
    ...R.hookFrames,
    ...R.fish.flatMap((p) => [...p.left, ...p.right]),
  ])
    assert.ok(atlas.sheets[key >>> 16]?.frames[key & 65535]);
});
test("terrain 5 opens rod fishing from a walkable original shore, without making deep water walkable or altering map data", () => {
  assert.equal(map.layers.FULL[shore], 5);
  const c = new CollisionSystem(map);
  assert.ok(findPath(c, { x: 136, y: 2376 }, { x: 136, y: 2408 }).length);
  assert.equal(c.walkable(8, 151), false);
  const f = nearestInteraction(map, 136, 2424, new Set(), 13);
  assert.equal(f?.kind, "rod-fishing");
  assert.equal(f?.itemId, 40);
  assert.ok(validRodShore(map.layers.FULL, shore, 136, 2408));
  assert.equal(validRodShore(map.layers.FULL, shore, 136, 2376), false);
  assert.equal(validRodShore(map.layers.FULL, shore - 180, 136, 2408), false);
  for (const i of [-1, 32400, NaN, shore + 0.5])
    assert.equal(validRodShore(map.layers.FULL, i, 136, 2408), false);
  assert.equal(validRodShore(null, shore, 136, 2408), false);
});
test("golden source movement keeps five-slot trailing hook, first idle-sway tick and checked-before-step limits", () => {
  let s = createRodFishingGame(shore, 1);
  const values = [];
  for (let i = 0; i < 5; i++) {
    s = stepRodFishing(s, 90, "left");
    values.push([s.floatX, s.floatY, s.hookX, s.hookY, s.fishX, s.fishY]);
  }
  assert.deepEqual(values, [
    [-3, -20, 0, 0, -118, -1],
    [-6, -20, 0, 0, -117, -2],
    [-9, -20, 0, 0, -116, -3],
    [-11, -19, 0, 1, -115, -4],
    [-14, -19, -3, 1, -114, -5],
  ]);
  assert.equal(rodSide, 97);
  assert.equal(rodTop, -94);
  assert.equal(rodBottom, 94);
  const edge = { ...createRodFishingGame(shore, 1), floatX: 96 };
  assert.equal(
    stepRodFishing(edge, 90, "right").floatX,
    99,
    "preserve source overshoot rather than clamping",
  );
});
test("unhooked fish keep source horizontal turns/vertical bounds and deterministic immutable continuation", () => {
  const initial = createRodFishingGame(shore, 77),
    before = structuredClone(initial);
  let s = initial;
  for (let i = 0; i < 3000; i++) {
    s = stepRodFishing(s, 90);
    assert.ok(s.fishX >= -120 && s.fishX <= 120);
    assert.ok(s.fishY >= -81 && s.fishY <= 81);
  }
  let repeat = createRodFishingGame(shore, 77);
  for (let i = 0; i < 3000; i++) repeat = stepRodFishing(repeat, 90);
  assert.deepEqual(s, repeat);
  assert.deepEqual(initial, before);
  for (const delta of [0, -1, NaN, Infinity])
    assert.equal(stepRodFishing(initial, delta), initial);
  assert.deepEqual(
    stepRodFishing(initial, 10000),
    stepRodFishing(initial, 180),
  );
});
test("fire uses strict original fish bounds after fish motion; missed setting loses and repeated pending strikes cannot multiply bait costs", () => {
  const start = {
    ...createRodFishingGame(shore, 1),
    species: 0,
    fishX: 0,
    fishY: 0,
    hookX: 9,
    hookY: -1,
    wanderTicks: 1,
    wanderRoll: 0,
  };
  const miss = stepRodFishing(strikeRod(start), 90);
  assert.equal(miss.status, "lost");
  assert.equal(miss.reason, "miss");
  const hit = stepRodFishing(strikeRod({ ...start, hookX: 8 }), 90);
  assert.equal(hit.phase, "hooked");
  assert.equal(hit.status, "playing");
  assert.equal(strikeRod(strikeRod(start)).strike, true);
  assert.equal(stepRodFishing(miss, 90), miss);
  assert.equal(strikeRod(miss), miss);
});
test("hooked fish drift from hook sign, escape on source side/bottom thresholds and require crossing above the reel boundary", () => {
  const base = {
    ...createRodFishingGame(shore, 1),
    phase: "hooked" as const,
    fishX: 0,
    fishY: 0,
    hookX: 0,
    hookY: 0,
  };
  const side = stepRodFishing({ ...base, fishX: 96, hookX: 96 }, 90);
  assert.equal(side.status, "lost");
  assert.equal(side.reason, "side");
  const bottom = stepRodFishing({ ...base, fishY: 93, hookY: 93 }, 90);
  assert.equal(bottom.status, "lost");
  assert.equal(bottom.reason, "bottom");
  const win = stepRodFishing(
    { ...base, fishY: -93, hookY: -93, floatY: -113 },
    90,
    "up",
  );
  assert.equal(win.status, "won");
  assert.equal(win.hookY, -95);
  const exact = stepRodFishing(
    { ...base, fishY: -92, hookY: -92, floatY: -112 },
    90,
    "up",
  );
  assert.equal(exact.hookY, -94);
  assert.equal(exact.status, "playing");
});
test("all four species can be caught using only real movement/fire/reeling; completion consumes one worm and keeps the rod", () => {
  const won = new Set<number>();
  for (const seed of [1, 2, 3, 4]) {
    const s = catchFish(seed);
    assert.equal(s.status, "won");
    const item = R.fish[s.species].item;
    won.add(item);
    const bag = rodFishingOutcome({ 14: 1, 90: 2 }, s)!;
    assert.equal(bag[14], 1);
    assert.equal(bag[90], 1);
    assert.equal(bag[item], 1);
  }
  assert.deepEqual(
    [...won].sort((a, b) => a - b),
    [40, 56, 57, 58],
  );
  const lost = stepRodFishing(strikeRod(createRodFishingGame(shore, 1)), 90);
  assert.deepEqual(rodFishingOutcome({ 14: 1, 90: 2 }, lost), { 14: 1, 90: 1 });
  assert.equal(rodFishingOutcome({ 14: 1 }, lost), null);
  assert.equal(rodFishingOutcome({ 90: 1 }, lost), null);
  assert.equal(
    rodFishingOutcome({ 14: 1, 90: 1 }, createRodFishingGame(shore, 1)),
    null,
  );
});
test("rod store enforces equipment, world distance, pause, action/save locks, cancellation and one-time/fatal completion effects", async () => {
  const { useGameStore: store } = await import("../src/store/gameStore");
  store.setState({
    ready: true,
    started: true,
    items,
    worldTerrain: map.layers.FULL,
    worldDecor: map.layers.Decor,
    worldObjects: map.layers.Objects,
    x: 136,
    y: 2408,
    inventory: {},
    survival: initialSurvival(),
    panel: null,
    rodGame: null,
    fishingGame: null,
    coconutGame: null,
    rodMove: null,
  });
  assert.equal(store.getState().startRodFishing(shore), false);
  store.setState({ inventory: { 14: 1 } });
  assert.equal(store.getState().startRodFishing(shore), false);
  store.setState({ inventory: { 14: 1, 90: 2 }, y: 2376 });
  assert.equal(store.getState().startRodFishing(shore), false);
  store.setState({ y: 2408 });
  assert.equal(store.getState().startRodFishing(shore), true);
  assert.equal(store.getState().startNetFishing(12 + 148 * 180), false);
  assert.equal(store.getState().startCoconutGame(28 + 160 * 180), false);
  store.getState().discard(90);
  assert.equal(store.getState().inventory[90], 2);
  assert.equal(store.getState().consume(0), false);
  assert.equal(store.getState().craft(91), false);
  store.getState().beginRest();
  assert.equal(store.getState().survival.restRemainingMs, null);
  store.getState().setPanel("inventory");
  assert.equal(store.getState().panel, null);
  store.getState().setPanel("settings");
  const pause = store.getState().rodGame;
  store.getState().advanceRodFishing(90, "up");
  store.getState().strikeRod();
  assert.equal(store.getState().rodGame, pause);
  const save = SaveSystem.save;
  let writes = 0;
  try {
    SaveSystem.save = async () => {
      writes++;
    };
    await store.getState().save();
    assert.equal(writes, 0);
  } finally {
    SaveSystem.save = save;
  }
  store.getState().leaveRodFishing();
  assert.equal(store.getState().rodGame, null);
  assert.equal(store.getState().inventory[90], 2);
  const terminal = {
    ...createRodFishingGame(shore, 1),
    phase: "hooked" as const,
    fishX: 0,
    fishY: -93,
    hookX: 0,
    hookY: -93,
    floatY: -113,
  };
  store.setState({ rodGame: terminal, panel: null });
  store.getState().advanceRodFishing(90, "up");
  assert.equal(store.getState().rodGame?.status, "won");
  assert.equal(store.getState().inventory[56], 1);
  assert.equal(store.getState().inventory[90], 1);
  assert.equal(store.getState().survival.energy, 95);
  assert.equal(store.getState().survival.strength, 1);
  const settled = store.getState();
  store.getState().advanceRodFishing(90, "up");
  assert.equal(store.getState().inventory, settled.inventory);
  assert.equal(store.getState().survival, settled.survival);
  store.setState({
    rodGame: terminal,
    inventory: { 14: 1, 90: 1 },
    survival: { ...initialSurvival(), energy: 4 },
  });
  store.getState().advanceRodFishing(90, "up");
  assert.equal(store.getState().rodGame, null);
  assert.equal(store.getState().survival.death, "energy");
  assert.equal(store.getState().inventory[56], 1);
  assert.equal(store.getState().inventory[90], 0);
});
