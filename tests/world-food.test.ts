import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import {
  worldFoodProfile,
  worldFoodAvailability,
  worldFoodEffects,
  worldFoodTarget,
  consumeWorldFood,
} from "../src/game/systems/WorldConsumptionSystem";
import {
  initialSurvival,
  consumeItem,
  validSurvival,
} from "../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { createCoconutGame } from "../src/game/systems/CoconutSystem";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const items: RecoveredItem[] = read("public/assets/objects/objects.json");
const map: IslandMap = read("public/assets/maps/island.json");
const meal = () => ({
  ...initialSurvival(),
  hunger: 40,
  thirst: 40,
  energy: 40,
});
function prepare(id = 0) {
  const index = map.layers.Objects.indexOf(id + 1),
    x = (index % 180) * 16 + 8,
    y = Math.floor(index / 180) * 16 + 8;
  store.setState({
    items,
    ready: true,
    started: true,
    saving: false,
    inventory: {},
    survival: meal(),
    bodyWeight: { barUnits: 0, nutrition: 49 },
    worldObjects: [...map.layers.Objects],
    collected: [],
    inspected: [],
    discovered: [],
    x: x - 16,
    y,
    panel: "dialogue",
    selected: id,
    focus: { index, itemId: id, x, y, distance: 16 },
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
  });
  return index;
}

test("22 original static foods follow the world USE branch, excluding hunting targets and special structures", () => {
  const rules = read("evidence/world-food-rules.json");
  assert.equal(rules.profiles.length, 22);
  assert.equal(rules.energyPasses, 2);
  for (const i of items) {
    const expected =
      i.category === 0 &&
      i.interaction.useTool >= 0 &&
      i.interaction.huntingTool === -1;
    assert.equal(!!worldFoodProfile(i.id), expected, i.name);
    if (expected) assert.ok(map.layers.Objects.includes(i.mapId));
  }
  for (const id of [2, 4, 17, 18, 42, 43, 55, 84, 94])
    assert.equal(
      worldFoodAvailability(items[id], { 81: 1, 45: 1, 83: 1 }).supported,
      false,
    );
});

test("all supported source foods apply exact world effects, keep inputs intact and only commit nutrition on survival", () => {
  for (const item of items.filter((i) => worldFoodProfile(i.id))) {
    const inventory =
        item.interaction.useTool > 0 ? { [item.interaction.useTool]: 1 } : {},
      before = structuredClone(inventory);
    const s = meal(),
      result = consumeWorldFood(s, item, inventory)!;
    assert.equal(
      result.survival.hunger,
      Math.min(
        100,
        40 + (item.effects.hunger === -1 ? 0 : item.effects.hunger),
      ),
      item.name,
    );
    assert.equal(
      result.survival.thirst,
      Math.min(
        100,
        40 + (item.effects.thirst === -1 ? 0 : item.effects.thirst),
      ),
      item.name,
    );
    assert.equal(
      result.survival.energy,
      Math.max(0, Math.min(100, 40 + 2 * item.effects.energy)),
      item.name,
    );
    assert.equal(result.removed, item.id !== 31);
    assert.equal(result.nutrition, item.id === 31 ? null : item.effects.hunger);
    assert.ok(validSurvival(result.survival));
    assert.deepEqual(inventory, before);
    assert.deepEqual(s, meal());
  }
});

test("free fruit doubles energy only in world use; tool sentinel food differs from its satchel path", () => {
  const world = consumeWorldFood(meal(), items[0], {})!;
  assert.equal(world.survival.energy, 56);
  assert.equal(consumeItem(meal(), items[0], { 0: 1 })!.survival.energy, 48);
  assert.deepEqual(worldFoodEffects(items[30]), {
    hunger: 3,
    thirst: 0,
    energy: 10,
    morale: 0,
    strength: 0,
  });
  assert.equal(
    consumeWorldFood(meal(), items[30], { 16: 1 })!.survival.thirst,
    40,
  );
  assert.equal(
    consumeItem(meal(), items[30], { 16: 1, 30: 1 })!.survival.thirst,
    39,
  );
  assert.equal(
    consumeWorldFood({ ...meal(), energy: 95 }, items[0], {})!.survival.energy,
    100,
  );
});

test("world tool checks do not spend supplies or advance the separate six-use coconut-opening counter", () => {
  const s = { ...meal(), stoneUses: 5 },
    inventory = { 3: 1, 1: 2 };
  assert.equal(consumeWorldFood(s, items[1], {}), null);
  const world = consumeWorldFood(s, items[1], inventory)!;
  assert.equal(world.survival.stoneUses, 5);
  assert.deepEqual(inventory, { 3: 1, 1: 2 });
  const satchel = consumeItem(s, items[1], inventory)!;
  assert.equal(satchel.survival.stoneUses, 0);
  assert.equal(satchel.inventory[3], 0);
  assert.equal(consumeWorldFood(s, items[27], {}), null);
  assert.ok(consumeWorldFood(s, items[27], { 76: 1 }));
});

test("either fatal energy pass leaves the object and nutrition uncommitted; exploration overrides loss safely", () => {
  for (const energy of [40, 50, 100]) {
    const result = consumeWorldFood({ ...meal(), energy }, items[31], {
      16: 1,
    })!;
    assert.equal(result.removed, false);
    assert.equal(result.nutrition, null);
    assert.equal(result.survival.death, "poison");
    assert.equal(result.survival.energy, 0);
    assert.deepEqual(
      [result.survival.hunger, result.survival.thirst],
      [40, 40],
    );
    assert.ok(validSurvival(result.survival));
  }
  const safe = consumeWorldFood(initialSurvival(false), items[31], { 16: 1 })!;
  assert.equal(safe.removed, true);
  assert.equal(safe.nutrition, -1);
  assert.deepEqual(
    [safe.survival.hunger, safe.survival.thirst, safe.survival.energy],
    [100, 100, 100],
  );
  assert.equal(safe.survival.death, null);
});

test("real source targets require a matching live map cell and nearby player; bad IDs/cells and removed objects fail", () => {
  const index = prepare(),
    s = store.getState();
  assert.ok(worldFoodTarget(items[0], index, s));
  for (const bad of [-1, 32400, 0.1, NaN])
    assert.ok(!worldFoodTarget(items[0], bad, s));
  assert.ok(!worldFoodTarget(items[1], index, s));
  assert.ok(!worldFoodTarget(items[0], index, { ...s, x: s.x - 100 }));
  assert.ok(!worldFoodTarget(items[0], index, { ...s, worldObjects: null }));
  assert.ok(!worldFoodTarget(items[0], index, { ...s, collected: [index] }));
  assert.equal(store.getState().consumeWorld(index, 1), false);
  assert.equal(store.getState().consumeWorld(index, NaN), false);
  assert.equal(store.getState().consumeWorld(32400, 0), false);
  assert.deepEqual(store.getState().inventory, {});
});

test("store commits world food once without gathering costs or satchel changes, and removal opens the runtime grid only", () => {
  const index = prepare();
  const s = store.getState(),
    original = JSON.stringify(map.layers);
  store.setState({ inventory: { 0: 7 } });
  assert.equal(store.getState().consumeWorld(index, 0), true);
  const after = store.getState();
  assert.deepEqual(after.inventory, { 0: 7 });
  assert.deepEqual(
    [after.survival.hunger, after.survival.thirst, after.survival.energy],
    [43, 55, 56],
  );
  assert.deepEqual(after.bodyWeight, { barUnits: 1, nutrition: 2 });
  assert.deepEqual(after.collected, [index]);
  assert.equal(after.panel, null);
  assert.equal(after.focus, null);
  const life = structuredClone(after.survival);
  assert.equal(after.consumeWorld(index, 0), false);
  assert.deepEqual(store.getState().survival, life);
  const runtime = s.worldObjects!.map((v, i) =>
    after.collected.includes(i) ? 0 : v,
  );
  const collision = new CollisionSystem(map, new Set(after.collected), runtime);
  assert.equal(runtime[index], 0);
  assert.equal(JSON.stringify(map.layers), original);
  assert.equal(
    new CollisionSystem(map).walkable(index % 180, Math.floor(index / 180)),
    false,
  );
  assert.equal(
    collision.walkable(index % 180, Math.floor(index / 180)),
    true,
    "the actual first original Mango cell becomes walkable",
  );
});

test("rest, minigames, pre-start, missing tools and collapse cannot trigger world use; fatal use cannot overwrite a save", async () => {
  let index = prepare();
  store.getState().beginRest();
  assert.equal(store.getState().consumeWorld(index, 0), false);
  store.getState().cancelRest();
  store.setState({ started: false });
  assert.equal(store.getState().consumeWorld(index, 0), false);
  store.setState({ started: true, coconutGame: createCoconutGame(0, 1) });
  assert.equal(store.getState().consumeWorld(index, 0), false);
  assert.deepEqual(store.getState().bodyWeight, { barUnits: 0, nutrition: 49 });
  index = prepare(31);
  assert.equal(store.getState().consumeWorld(index, 31), false);
  store.setState({
    inventory: { 16: 1 },
    survival: { ...meal(), energy: 100 },
  });
  assert.equal(store.getState().consumeWorld(index, 31), true);
  const after = store.getState();
  assert.equal(after.survival.death, "poison");
  assert.deepEqual(after.collected, []);
  assert.deepEqual(after.bodyWeight, { barUnits: 0, nutrition: 49 });
  assert.equal(after.worldObjects![index], 32);
  assert.deepEqual(after.inventory, { 16: 1 });
  assert.equal(after.consumeWorld(index, 31), false);
  const save = SaveSystem.save;
  let writes = 0;
  try {
    SaveSystem.save = async () => {
      writes++;
    };
    await after.save();
    assert.equal(writes, 0);
  } finally {
    SaveSystem.save = save;
  }
});

test("existing version-1 saves retain used-cell tombstones and body nutrition; reloading cannot award the same meal twice", async () => {
  const index = prepare();
  assert.ok(store.getState().consumeWorld(index, 0));
  const save = SaveSystem.save,
    load = SaveSystem.load;
  let captured: SaveData | undefined;
  try {
    SaveSystem.save = async (data) => {
      captured = structuredClone(data);
    };
    await store.getState().save();
    assert.ok(validSave(captured));
    assert.ok(captured!.collected.includes(index));
    assert.deepEqual(captured!.bodyWeight, { barUnits: 1, nutrition: 2 });
    prepare();
    SaveSystem.load = async () => captured!;
    await store.getState().load();
    assert.equal(store.getState().consumeWorld(index, 0), false);
    assert.deepEqual(store.getState().inventory, {});
  } finally {
    SaveSystem.save = save;
    SaveSystem.load = load;
  }
});
