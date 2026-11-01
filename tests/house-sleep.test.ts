import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import {
  FURNISHING_IDS,
  validFurnishings,
} from "../src/game/systems/HouseSystem";
import {
  craftAvailability,
  craftInventory,
} from "../src/game/systems/ItemRules";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import {
  claimSleep,
  sleepRecovery,
  HOUR_MS,
} from "../src/game/systems/SleepSystem";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
const materials = (id: number) =>
  Object.fromEntries(
    [...items[id].recipe, ...items[id].craftingTools].map((r) => [
      r.item,
      r.quantity,
    ]),
  );
const base: SaveData = {
  version: 1,
  x: 136,
  y: 2376,
  elapsed: 17,
  inventory: { 60: 1 },
  collected: [],
  inspected: [],
  discovered: [],
  enhancements: true,
  shelter: 10,
  survival: {
    ...initialSurvival(),
    hunger: 40,
    thirst: 50,
    energy: 60,
    morale: 20,
    strength: 5,
  },
};
test("all four source furnishings support their original recipes, including unmapped sprites", () => {
  assert.deepEqual(FURNISHING_IDS, [15, 50, 60, 85]);
  assert.equal(items[15].sprite, -1);
  assert.equal(items[60].sprite, -1);
  assert.equal(items[85].sprite, -1);
  for (const id of FURNISHING_IDS) {
    const tier = id === 15 ? 9 : 10,
      inv = materials(id);
    assert.equal(craftAvailability(items[id], inv, 17).allowed, false);
    if (id !== 15)
      assert.equal(craftAvailability(items[id], inv, 9).allowed, false);
    assert.equal(craftAvailability(items[id], inv, tier).allowed, true);
    const next = craftInventory(items[id], inv, tier)!;
    assert.equal(next[id], undefined);
    for (const r of items[id].recipe) assert.equal(next[r.item], 0);
    for (const r of items[id].craftingTools)
      assert.equal(next[r.item], r.quantity);
    assert.equal(craftInventory(items[id], inv, tier, { [id]: 1 }), null);
  }
  assert.equal(
    craftAvailability(items[50], { ...materials(50), 76: 0 }, 10).allowed,
    false,
  );
});
test("house crafting records one furnishing, applies source effects and retains satchel Rug rewards", () => {
  store.setState({
    items,
    ready: true,
    started: true,
    shelter: 10,
    furnishings: {},
    survival: initialSurvival(),
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
  });
  for (const id of FURNISHING_IDS) {
    store.setState({ inventory: { ...materials(id), 60: 2 } });
    assert.equal(store.getState().craft(id), true);
    assert.equal(store.getState().furnishings[id], 1);
    assert.equal(store.getState().inventory[60], 2);
    assert.equal(store.getState().craft(id), false);
  }
  assert.equal(store.getState().survival.energy, 60);
  assert.equal(store.getState().survival.morale, 20);
  assert.deepEqual(store.getState().furnishings, {
    15: 1,
    50: 1,
    60: 1,
    85: 1,
  });
});
test("furnishing and sleep save fields are optional, validated and do not reinterpret legacy loot", () => {
  assert.equal(validSave(base), true);
  assert.equal(
    validSave({ ...base, furnishings: { 15: 1, 60: 1 }, sleepStartedAt: 1 }),
    true,
  );
  for (const furnishings of [
    null,
    [],
    { 10: 1 },
    { 15: -1 },
    { 15: 0.5 },
    { "015": 1 },
  ])
    assert.equal(validSave({ ...base, furnishings }), false);
  for (const sleepStartedAt of [null, -1, 1.5, Infinity, "1", 8640000000000001])
    assert.equal(validSave({ ...base, sleepStartedAt }), false);
  assert.equal(validFurnishings({}), true);
  assert.equal(claimSleep(base, Date.now()).inventory[60], 1);
});
test("recovered sleep hour bands and minute borrowing produce the original bonuses", () => {
  for (const [hours, bonus] of [
    [0, 0],
    [1, 10],
    [15, 10],
    [16, 20],
    [35, 20],
    [36, 30],
    [72, 30],
    [73, 40],
    [119, 40],
    [120, 50],
    [1000, 50],
  ])
    assert.equal(sleepRecovery(hours), bonus);
  const start = 2000000000000;
  for (const [ms, bonus] of [
    [HOUR_MS - 1, 0],
    [HOUR_MS, 10],
    [16 * HOUR_MS - 1, 10],
    [16 * HOUR_MS, 20],
  ]) {
    const next = claimSleep({ ...base, sleepStartedAt: start }, start + ms);
    assert.equal(next.survival!.hunger, 40 + bonus);
    assert.equal(next.survival!.thirst, 50 + bonus);
    assert.equal(next.survival!.energy, 60 + bonus);
    assert.equal(next.survival!.morale, 20);
    assert.equal(next.survival!.strength, 5);
    assert.equal(next.elapsed, 17);
    assert.equal(next.sleepStartedAt, undefined);
  }
});
test("sleep caps each reserve, claims once, preserves input and guards clock rollback, old/exploration/dead saves", () => {
  const checkpoint = { ...base, sleepStartedAt: 1000 },
    original = JSON.stringify(checkpoint),
    now = 1000 + 120 * HOUR_MS;
  const next = claimSleep(checkpoint, now);
  assert.deepEqual(
    [next.survival!.hunger, next.survival!.thirst, next.survival!.energy],
    [90, 100, 100],
  );
  assert.deepEqual(claimSleep(next, now + 120 * HOUR_MS), next);
  assert.equal(JSON.stringify(checkpoint), original);
  assert.deepEqual(claimSleep(checkpoint, 0).survival, base.survival);
  assert.deepEqual(
    claimSleep(
      { ...base, sleepStartedAt: 1, survival: initialSurvival(false) },
      now,
    ).survival,
    initialSurvival(false),
  );
  const dead = { ...initialSurvival(), energy: 0, death: "energy" as const };
  assert.deepEqual(
    claimSleep({ ...base, sleepStartedAt: 1, survival: dead }, now).survival,
    dead,
  );
});
test("sleep commits a checkpoint before returning to title; ordinary saves remain ordinary", async () => {
  const old = SaveSystem.save;
  let recorded: SaveData | undefined;
  try {
    SaveSystem.save = async (d) => {
      recorded = d;
    };
    store.setState({
      ...base,
      items,
      ready: true,
      started: true,
      saving: false,
      furnishings: { 15: 1 },
      survival: base.survival!,
    });
    await store.getState().save();
    assert.equal(recorded!.sleepStartedAt, undefined);
    assert.equal(store.getState().started, true);
    await store.getState().sleep();
    assert.equal(typeof recorded!.sleepStartedAt, "number");
    assert.equal(store.getState().started, false);
    assert.deepEqual(recorded!.furnishings, { 15: 1 });
    assert.equal(recorded!.survival!.energy, 60);
    assert.equal(validSave(recorded), true);
    store.setState({ started: true });
    SaveSystem.save = async () => {
      throw new Error("storage blocked");
    };
    await store.getState().sleep();
    assert.equal(store.getState().started, true);
    assert.equal(store.getState().saving, false);
  } finally {
    SaveSystem.save = old;
  }
});
test("sleep refuses unavailable play, rest, exhaustion and concurrent saves", async () => {
  const old = SaveSystem.save;
  let writes = 0;
  SaveSystem.save = async () => {
    writes++;
  };
  try {
    for (const patch of [
      { ready: false },
      { started: false },
      { saving: true },
      { survival: { ...initialSurvival(), restRemainingMs: 10000 } },
      {
        survival: { ...initialSurvival(), energy: 0, death: "energy" as const },
      },
    ]) {
      store.setState({
        ready: true,
        started: true,
        saving: false,
        survival: initialSurvival(),
        ...patch,
      });
      await store.getState().sleep();
    }
    assert.equal(writes, 0);
  } finally {
    SaveSystem.save = old;
    store.setState({ saving: false, survival: initialSurvival() });
  }
});
test("loading legacy saves clears previous house state without moving old fishing Rug loot", async () => {
  const old = SaveSystem.load;
  try {
    SaveSystem.load = async () => base;
    store.setState({ furnishings: { 60: 1 }, saving: false });
    await store.getState().load();
    assert.deepEqual(store.getState().furnishings, {});
    assert.equal(store.getState().inventory[60], 1);
  } finally {
    SaveSystem.load = old;
  }
});
