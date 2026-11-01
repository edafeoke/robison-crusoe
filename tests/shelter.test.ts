import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import {
  campItem,
  shelterAllows,
  shelterOwned,
} from "../src/game/systems/ShelterSystem";
import {
  craftAvailability,
  craftInventory,
  supportedRecipes,
} from "../src/game/systems/ItemRules";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import {
  validSave,
  SaveSystem,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { useGameStore as store } from "../src/store/gameStore";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
const map = JSON.parse(readFileSync("public/assets/maps/island.json", "utf8"));
const materials = (id: number): Record<number, number> =>
  Object.fromEntries(
    [...items[id].recipe, ...items[id].craftingTools].map((r) => [
      r.item,
      r.quantity,
    ]),
  );
const save: SaveData = {
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

test("CSV shelter gates follow global flag/tent/cabin tiers, preserving boat repair separation", () => {
  for (const item of items) {
    assert.equal(shelterAllows(item, 17), item.shelterRequirement === -1);
    assert.equal(
      shelterAllows(item, 9),
      [-1, 9].includes(item.shelterRequirement),
    );
    assert.equal(
      shelterAllows(item, 10),
      [-1, 9, 10].includes(item.shelterRequirement),
    );
  }
  assert.equal(shelterAllows(items[94], 10), false);
  for (const id of [91, 45, 81, 83]) {
    assert.equal(
      craftAvailability(items[id], materials(id), 17).allowed,
      false,
    );
    assert.equal(craftAvailability(items[id], materials(id), 9).allowed, true);
  }
  assert.equal(craftAvailability(items[117], materials(117), 9).allowed, false);
  assert.equal(craftAvailability(items[117], materials(117), 10).allowed, true);
});
test("tent and cabin consume exact materials, retain tools, never add a building to the satchel", () => {
  assert.deepEqual(items[9].recipe, [
    { item: 8, quantity: 3 },
    { item: 12, quantity: 1 },
  ]);
  const input = { ...materials(9), 8: 5 };
  const next = craftInventory(items[9], input, 17)!;
  assert.equal(next[8], 2);
  assert.equal(next[12], 0);
  assert.equal(next[76], 1);
  assert.equal(next[9], undefined);
  assert.equal(input[8], 5);
  assert.equal(craftInventory(items[9], { 8: 3, 12: 1 }, 17), null);
  assert.equal(craftInventory(items[10], materials(10), 17), null);
  const cabin = craftInventory(items[10], materials(10), 9)!;
  for (const r of items[10].recipe) assert.equal(cabin[r.item], 0);
  for (const r of items[10].craftingTools)
    assert.equal(cabin[r.item], r.quantity);
  assert.equal(cabin[10], undefined);
});
test("camp progression cannot repeat or downgrade; boat paths stay separate", () => {
  assert.equal(supportedRecipes(items).length, 43);
  for (const stage of [9, 10] as const) {
    assert.equal(shelterOwned(9, stage), true);
    assert.equal(craftInventory(items[9], materials(9), stage), null);
  }
  assert.equal(craftInventory(items[10], materials(10), 10), null);
  for (const id of [19, 94])
    assert.equal(
      craftAvailability(items[id], materials(id), 10).supported,
      false,
    );
});
test("original camp placement, terrain and collision remain intact at every shelter tier", () => {
  const original = JSON.stringify(map.layers);
  const flags = map.layers.Objects.flatMap((v: number, i: number) =>
    v === 18 ? [i] : [],
  );
  assert.deepEqual(flags, [23244]);
  const collision = new CollisionSystem(map);
  for (const stage of [17, 9, 10] as const) {
    assert.equal(campItem(17, stage), stage);
    assert.equal(campItem(18, stage), 18);
    assert.equal(collision.walkable(24, 129), false);
  }
  assert.equal(JSON.stringify(map.layers), original);
});
test("store upgrades globally once and applies recovered construction effects atomically", () => {
  store.setState({
    ready: true,
    started: true,
    items,
    shelter: 17,
    inventory: materials(9),
    survival: initialSurvival(),
    worldObjects: map.layers.Objects,
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
    x: 136,
    y: 2376,
  });
  assert.equal(store.getState().craft(9), true);
  assert.equal(store.getState().shelter, 9);
  assert.equal(store.getState().survival.energy, 80);
  assert.equal(store.getState().survival.morale, 5);
  const after = store.getState().inventory;
  assert.equal(store.getState().craft(9), false);
  assert.equal(store.getState().inventory, after);
  store.setState({ inventory: materials(10) });
  assert.equal(store.getState().craft(10), true);
  assert.equal(store.getState().shelter, 10);
  assert.equal(store.getState().survival.energy, 50);
  assert.equal(store.getState().survival.morale, 15);
  assert.equal(map.layers.Objects[23244], 18);
});
test("failed, resting, unavailable-site and fatal construction cannot double settle", () => {
  store.setState({
    shelter: 17,
    inventory: materials(9),
    worldObjects: null,
    survival: initialSurvival(),
  });
  assert.equal(store.getState().craft(9), false);
  store.setState({
    worldObjects: map.layers.Objects,
    survival: { ...initialSurvival(), restRemainingMs: 10000 },
  });
  assert.equal(store.getState().craft(9), false);
  store.setState({ survival: { ...initialSurvival(), energy: 20 } });
  assert.equal(store.getState().craft(9), true);
  assert.equal(store.getState().shelter, 9);
  assert.equal(store.getState().survival.death, "energy");
  assert.equal(store.getState().craft(10), false);
});
test("version-1 saves accept old camps and reject invalid shelter states", () => {
  assert.equal(validSave(save), true);
  for (const shelter of [17, 9, 10])
    assert.equal(validSave({ ...save, shelter }), true);
  for (const shelter of [0, 18, 11, 9.5, "9", null])
    assert.equal(validSave({ ...save, shelter }), false);
});
test("load resets a cabin to flag for legacy saves, while new saves persist and restore the tier", async () => {
  const oldLoad = SaveSystem.load,
    oldSave = SaveSystem.save;
  let recorded: SaveData | undefined;
  try {
    SaveSystem.load = async () => save;
    store.setState({ shelter: 10 });
    await store.getState().load();
    assert.equal(store.getState().shelter, 17);
    store.setState({ shelter: 9 });
    SaveSystem.save = async (data) => {
      recorded = data;
    };
    await store.getState().save();
    assert.equal(recorded?.shelter, 9);
    SaveSystem.load = async () => recorded!;
    store.setState({ shelter: 17 });
    await store.getState().load();
    assert.equal(store.getState().shelter, 9);
  } finally {
    SaveSystem.load = oldLoad;
    SaveSystem.save = oldSave;
  }
});
