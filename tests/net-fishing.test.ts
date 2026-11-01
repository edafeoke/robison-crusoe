import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { netFishingRules as R } from "../src/game/data/netFishingRules";
import {
  createNetFishingGame,
  castNet,
  stepNetFishing,
  netFishingOutcome,
  validNetShore,
  netStart,
  type NetFishingGame,
} from "../src/game/systems/NetFishingSystem";
import { nearestInteraction } from "../src/game/systems/InteractionSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { findPath } from "../src/game/systems/MovementSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { SaveSystem } from "../src/game/systems/SaveSystem";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../src/game/assets/assetManifest";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const map = read("public/assets/maps/island.json"),
  items: RecoveredItem[] = read("public/assets/objects/objects.json");
const shore = 12 + 148 * 180;
function idle(s: NetFishingGame, ticks: number) {
  for (let i = 0; i < ticks; i++) s = stepNetFishing(s, 90);
  return s;
}
function sweep(s: NetFishingGame) {
  s = castNet(s);
  for (let i = 0; i < 100 && s.status === "playing" && s.phase !== "ready"; i++)
    s = stepNetFishing(s, 90);
  return s;
}
function win() {
  let s = createNetFishingGame(shore, 1);
  for (const delay of [127, 21, 13, 389]) s = sweep(idle(s, delay));
  return s;
}
test("net rules and all fish/net frames resolve to the original x class and packed atlas; reward ID 60 remains the source Rug quirk", () => {
  const x = readFileSync("evidence/decompiled/data/x.java", "utf8"),
    a = readFileSync("evidence/decompiled/data/a.java", "utf8"),
    b = readFileSync("evidence/bytecode/data.x.txt", "utf8");
  for (const text of [
    "this.A = 32",
    "this.z = 16",
    "this.D >= 10",
    "this.B == 5",
    "this.x += 5",
    "by = 60;",
  ])
    assert.ok(x.includes(text));
  assert.match(a, /this\.a\.k\[this\.a\.P \+ 180\] == 4/);
  assert.match(b, /bipush\s+60/);
  assert.equal(items[83].name, "Net");
  assert.equal(items[26].name, "Potato");
  assert.equal(items[83].effects.energy, -10);
  assert.equal(items[83].effects.strength, 1);
  assert.equal(items[60].name, "Rug");
  const atlas: AtlasManifest = read("public/assets/ui/atlas.json");
  for (const sprite of [
    R.netSprite,
    R.toolSprite,
    ...R.fish.flatMap((p) => [...p.left, ...p.right]),
  ])
    assert.ok(atlas.sheets[sprite >>> 16]?.frames[sprite & 65535]);
  assert.deepEqual(
    R.fish.map((p) => p.below),
    [45, 65, 75, 85, 100],
  );
});
test("shallow-water interaction is backed by original terrain 4, reachable from spawn and rejects remote/deep/forged locations", () => {
  assert.equal(map.layers.FULL[shore], 4);
  const c = new CollisionSystem(map);
  assert.ok(
    findPath(c, { x: 136, y: 2376 }, { x: 12 * 16 + 8, y: 148 * 16 + 8 })
      .length,
  );
  const f = nearestInteraction(map, 12 * 16 + 8, 148 * 16 + 8, new Set(), 13);
  assert.equal(f?.kind, "net-fishing");
  assert.equal(f?.itemId, 59);
  assert.ok(validNetShore(map.layers.FULL, shore, 11 * 16 + 8, 148 * 16 + 8));
  for (const i of [-1, 32400, NaN, shore + 0.5])
    assert.equal(validNetShore(map.layers.FULL, i, 200, 2376), false);
  assert.equal(validNetShore(map.layers.FULL, shore, 136, 2376), false);
  assert.equal(validNetShore(null, shore, 200, 2376), false);
  assert.equal(
    validNetShore(
      [
        ...map.layers.FULL.slice(0, shore),
        5,
        ...map.layers.FULL.slice(shore + 1),
      ],
      shore,
      200,
      2376,
    ),
    false,
  );
});
test("net schools preserve source spawn ranges, speeds, frame distributions and deterministic continuation", () => {
  for (let seed = 1; seed <= 1000; seed++) {
    const s = createNetFishingGame(shore, seed);
    assert.ok(s.fish.length >= 3 && s.fish.length <= 7);
    assert.equal(s.netY, -133);
    for (const f of s.fish) {
      assert.ok(f.x <= 0 || f.x >= 240);
      assert.ok(f.y >= 42 && f.y <= 267);
      assert.ok(Math.abs(f.speed) >= 2 && Math.abs(f.speed) <= 4);
      assert.equal(Math.sign(f.speed), f.direction);
    }
    assert.deepEqual(idle(s, 40), idle(createNetFishingGame(shore, seed), 40));
  }
  const s = createNetFishingGame(shore, 4),
    snapshot = structuredClone(s);
  idle(s, 20);
  assert.deepEqual(s, snapshot);
});
test("strict center containment, fish-before-net update order, caught-fish descent and two-second haul follow x.l/k/m", () => {
  const s = {
    ...createNetFishingGame(shore, 1),
    fish: [
      {
        x: 99,
        y: 40,
        speed: 4,
        profile: 0,
        direction: 1 as const,
        phase: 0 as const,
      },
    ],
  };
  let next = stepNetFishing(castNet(s), 90);
  assert.equal(next.netY, netStart + 5);
  assert.equal(next.fish[0].x, 103);
  assert.equal(next.fish[0].phase, 0, "x outside narrow lane");
  next = stepNetFishing(next, 90);
  assert.equal(next.fish[0].x, 107);
  assert.equal(next.fish[0].phase, 1, "center inside net");
  const y = next.fish[0].y;
  next = stepNetFishing(next, 90);
  assert.equal(next.fish[0].y, y + 5);
  for (let i = 0; i < 60 && next.phase !== "haul"; i++)
    next = stepNetFishing(next, 90);
  assert.equal(next.caught, 1);
  assert.equal(next.lastCatch, 1);
  assert.equal(next.fish[0].phase, 2);
  assert.equal(castNet(next), next, "no recast while hauling");
  next = idle(next, 22);
  assert.equal(next.phase, "haul");
  next = idle(next, 1);
  assert.equal(next.phase, "ready");
  assert.equal(next.netY, netStart);
});
test("real waiting and casting wins with original movement; five misses lose, bounded input cannot spend extra casts", () => {
  const s = win();
  assert.equal(s.casts, 4);
  assert.equal(s.caught, 12);
  assert.equal(s.status, "won");
  assert.equal(s.reward, 59);
  assert.equal(castNet(s), s);
  assert.equal(stepNetFishing(s, 90), s);
  let miss = createNetFishingGame(shore, 3);
  for (let i = 0; i < 5; i++) {
    miss = {
      ...miss,
      fish: [{ x: 0, y: 50, speed: -2, profile: 0, direction: -1, phase: 0 }],
    };
    miss = sweep(miss);
  }
  assert.equal(miss.status, "lost");
  assert.equal(miss.casts, 5);
  assert.equal(miss.reward, null);
  for (const delta of [0, -1, NaN, Infinity])
    assert.equal(
      stepNetFishing(createNetFishingGame(shore, 2), delta).accumulator,
      0,
    );
  const g = createNetFishingGame(shore, 1);
  assert.deepEqual(stepNetFishing(g, 10000), stepNetFishing(g, 180));
});
test("completed rounds consume one potato, keep net, reward one original item only on a win, and reject invalid settlement", () => {
  const bag = { 83: 1, 26: 2 };
  assert.equal(netFishingOutcome(bag, createNetFishingGame(shore, 1)), null);
  for (const reward of R.rewardItems) {
    const result = netFishingOutcome(bag, { ...win(), reward })!;
    assert.equal(result[26], 1);
    assert.equal(result[83], 1);
    assert.equal(result[reward], 1);
  }
  const lost = { ...createNetFishingGame(shore, 1), status: "lost" as const };
  assert.deepEqual(netFishingOutcome(bag, lost), { 83: 1, 26: 1 });
  assert.equal(netFishingOutcome({ 83: 1 }, win()), null);
  assert.equal(netFishingOutcome({ 26: 1 }, win()), null);
  assert.equal(netFishingOutcome(bag, { ...win(), reward: 91 }), null);
  assert.deepEqual(bag, { 83: 1, 26: 2 });
});
test("store gates actual shoreline, net and bait; locks world/save actions, pauses and settles reward/cost once including fatal energy", async () => {
  const { useGameStore: store } = await import("../src/store/gameStore");
  store.setState({
    ready: true,
    started: true,
    items,
    worldTerrain: map.layers.FULL,
    worldDecor: map.layers.Decor,
    worldObjects: map.layers.Objects,
    x: 200,
    y: 2376,
    inventory: {},
    survival: initialSurvival(),
    panel: null,
    fishingGame: null,
    coconutGame: null,
  });
  assert.equal(store.getState().startNetFishing(shore), false);
  store.setState({ inventory: { 83: 1 } });
  assert.equal(store.getState().startNetFishing(shore), false);
  store.setState({ inventory: { 83: 1, 26: 2 } });
  assert.equal(store.getState().startNetFishing(shore), true);
  assert.equal(store.getState().startCoconutGame(28 + 160 * 180), false);
  store.getState().setPanel("inventory");
  assert.equal(store.getState().panel, null);
  store.getState().discard(26);
  assert.equal(store.getState().inventory[26], 2);
  assert.equal(store.getState().consume(26), false);
  assert.equal(store.getState().craft(91), false);
  store.getState().setPanel("settings");
  const paused = store.getState().fishingGame;
  store.getState().castNet();
  store.getState().advanceNetFishing(90);
  assert.equal(store.getState().fishingGame, paused);
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
  const terminal = {
    ...win(),
    status: "playing" as const,
    reward: null,
    phase: "haul" as const,
    haulMs: 2000,
  };
  store.setState({ fishingGame: terminal, panel: null });
  store.getState().advanceNetFishing(90);
  assert.equal(store.getState().fishingGame?.status, "won");
  assert.equal(store.getState().inventory[26], 1);
  assert.equal(store.getState().survival.energy, 90);
  assert.equal(store.getState().survival.strength, 1);
  const settled = store.getState();
  store.getState().advanceNetFishing(90);
  assert.equal(store.getState().inventory, settled.inventory);
  assert.equal(store.getState().survival, settled.survival);
  store.getState().leaveNetFishing();
  assert.equal(store.getState().fishingGame, null);
  store.setState({
    fishingGame: terminal,
    inventory: { 83: 1, 26: 1 },
    survival: { ...initialSurvival(), energy: 9 },
  });
  store.getState().advanceNetFishing(90);
  assert.equal(store.getState().survival.death, "energy");
  assert.equal(store.getState().fishingGame, null);
  assert.equal(store.getState().inventory[26], 0);
  assert.equal(
    Object.values(store.getState().inventory).reduce((a, b) => a + b, 0),
    2,
    "net and one reward survive fatal cost",
  );
});
