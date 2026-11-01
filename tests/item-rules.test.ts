import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import {
  canPickup,
  pickupRule,
  craftAvailability,
  craftInventory,
  portableRecipes,
  categoryName,
} from "../src/game/systems/ItemRules";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
test("typed object rules preserve all CSV requirement fields and reusable-tool lists", () => {
  const rows = JSON.parse(readFileSync("evidence/objects-raw.json", "utf8"));
  const decode = (field: string) =>
    field === "-1"
      ? []
      : field.split("#").map((pair) => {
          const [item, quantity] = pair.split("|").map(Number);
          return { item, quantity };
        });
  for (const item of items) {
    assert.equal(item.interaction.pickupTool, +rows[0][item.id]);
    assert.equal(item.interaction.useTool, +rows[1][item.id]);
    assert.equal(item.interaction.huntingTool, +rows[2][item.id]);
    assert.equal(item.category, +rows[12][item.id]);
    assert.equal(item.shelterRequirement, +rows[10][item.id]);
    assert.deepEqual(item.craftingTools, decode(rows[9][item.id]));
  }
  assert.deepEqual(items[91].craftingTools, [{ item: 76, quantity: 1 }]);
});
test("resources require their recovered tools and a food use-tool does not block taking the food", () => {
  for (const id of [6, 7, 20, 35, 39, 90, 98]) {
    assert.equal(canPickup(items[id], {}), false, items[id].name);
    assert.equal(canPickup(items[id], { 76: 1 }), true, items[id].name);
  }
  assert.equal(canPickup(items[11], { 76: 1 }), false);
  assert.equal(canPickup(items[11], { 5: 1 }), true);
  assert.equal(canPickup(items[93], { 5: 1 }), false);
  assert.equal(canPickup(items[93], { 78: 1 }), true);
  assert.equal(canPickup(items[38], { 76: 1 }), false);
  assert.equal(canPickup(items[38], { 96: 1 }), true);
  assert.equal(canPickup(items[3], {}), true);
  assert.equal(canPickup(items[76], {}), true);
  assert.equal(
    canPickup(items[27], {}),
    true,
    "pineapple can be taken before obtaining its use tool",
  );
});
test("wildlife and special structures require their own interactions rather than generic pickup", () => {
  assert.deepEqual(pickupRule(items[4]), {
    kind: "special",
    reason: "hunting",
  });
  assert.equal(canPickup(items[4], { 81: 1, 45: 20 }), false);
  for (const id of [9, 10, 17, 18, 84, 94])
    assert.equal(canPickup(items[id], { 76: 1, 5: 1 }), false);
});
test("rope requires both materials and the knife; crafting keeps tools and rejects atomic failures", () => {
  const materialOnly = { 6: 2, 8: 1 };
  assert.equal(craftAvailability(items[91], materialOnly, 9).allowed, false);
  assert.equal(craftInventory(items[91], materialOnly, 9), null);
  const inventory = { ...materialOnly, 76: 1 };
  const next = craftInventory(items[91], inventory, 9)!;
  assert.equal(next[6], 0);
  assert.equal(next[8], 0);
  assert.equal(next[91], 1);
  assert.equal(next[76], 1);
  assert.deepEqual(inventory, { 6: 2, 8: 1, 76: 1 });
  const stick = craftInventory(items[36], { 11: 2, 76: 1 })!;
  assert.equal(stick[36], 1);
  assert.equal(stick[11], 1);
  assert.equal(stick[76], 1);
});
test("equipment stays unique and unimplemented building/clothing recipes are excluded", () => {
  const owned = { 5: 1, 3: 1, 8: 1, 6: 1 };
  assert.equal(craftAvailability(items[5], owned).alreadyOwned, true);
  assert.equal(craftInventory(items[5], owned), null);
  const recipes = portableRecipes(items);
  assert.ok(recipes.length > 2);
  assert.ok(recipes.some((i) => i.id === 83));
  assert.ok(recipes.some((i) => i.id === 36));
  assert.ok(recipes.every((i) => [0, 1].includes(i.category)));
  assert.ok(!recipes.some((i) => [9, 10, 19, 94, 99].includes(i.id)));
  assert.equal(categoryName(1), "Equipment");
});
test("world actions reject remote pickups and mismatched cells before any inventory mutation", async () => {
  const { useGameStore } = await import("../src/store/gameStore");
  const map = JSON.parse(
    readFileSync("public/assets/maps/island.json", "utf8"),
  );
  const index = 23 + 147 * 180;
  useGameStore.setState({
    items,
    ready: true,
    started: true,
    inventory: {},
    collected: [],
    worldObjects: map.layers.Objects,
    x: 8 * 16 + 8,
    y: 148 * 16 + 8,
  });
  assert.equal(useGameStore.getState().collect(index, 3), false);
  useGameStore.setState({ x: 23 * 16 + 8 });
  assert.equal(useGameStore.getState().collect(index, 76), false);
  assert.deepEqual(useGameStore.getState().inventory, {});
  assert.deepEqual(useGameStore.getState().collected, []);
  assert.equal(useGameStore.getState().collect(index, 3), true);
  assert.equal(useGameStore.getState().collect(index, 3), false);
  assert.equal(useGameStore.getState().inventory[3], 1);
});
