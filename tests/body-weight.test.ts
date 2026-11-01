import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync, readdirSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import {
  initialBodyWeight,
  foodNutrition,
  gainBodyWeight,
  validBodyWeight,
  weightPercent,
  WEIGHT_MAX,
} from "../src/game/systems/BodyWeightSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
const base: SaveData = {
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
const setup = () =>
  store.setState({
    items,
    ready: true,
    started: true,
    survival: initialSurvival(),
    bodyWeight: initialBodyWeight(),
    inventory: {},
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
    panel: null,
  });

test("weight is a food-driven status bar; descriptor references do not connect it to movement or carrying", () => {
  const uses = readdirSync("evidence/bytecode").filter(
    (file) =>
      file.endsWith(".txt") &&
      readFileSync(`evidence/bytecode/${file}`, "utf8").includes(
        "Field data/p.g:F",
      ),
  );
  assert.deepEqual(uses.sort(), ["data.aa.txt", "data.t.txt", "data.v.txt"]);
  const rule = JSON.parse(
    readFileSync("evidence/body-weight-rules.json", "utf8"),
  );
  assert.equal(rule.nutritionThreshold, 50);
  assert.equal(rule.barStep, 1);
  assert.equal(
    WEIGHT_MAX,
    64,
    "explicit 240-wide browser reference uses the original width/3−16 formula",
  );
  const atlas = JSON.parse(readFileSync("public/assets/ui/atlas.json", "utf8"));
  for (const key of [655396, 655397, 655398])
    assert.ok(atlas.sheets[key >>> 16].frames[key & 65535]);
});

test("50 food points add one native bar unit, without looping through a large meal or clamping queued nutrition", () => {
  const start = initialBodyWeight();
  assert.deepEqual(gainBodyWeight(start, 49), { barUnits: 0, nutrition: 49 });
  assert.deepEqual(gainBodyWeight({ barUnits: 0, nutrition: 49 }, 1), {
    barUnits: 1,
    nutrition: 0,
  });
  const large = gainBodyWeight(start, 100);
  assert.deepEqual(large, { barUnits: 1, nutrition: 50 });
  assert.equal(weightPercent(large), 1.5625);
  assert.deepEqual(
    gainBodyWeight(large, null),
    large,
    "no hunger call does not flush the queue",
  );
  assert.deepEqual(gainBodyWeight(large, 3), { barUnits: 2, nutrition: 3 });
  assert.deepEqual(start, initialBodyWeight(), "no input mutation");
});

test("body-weight cap still consumes one threshold per call; signed source nutrition is retained", () => {
  const capped = gainBodyWeight({ barUnits: WEIGHT_MAX, nutrition: 49 }, 100);
  assert.deepEqual(capped, { barUnits: WEIGHT_MAX, nutrition: 99 });
  assert.deepEqual(gainBodyWeight(capped, 0), {
    barUnits: WEIGHT_MAX,
    nutrition: 49,
  });
  assert.equal(weightPercent(capped), 100);
  assert.deepEqual(gainBodyWeight(initialBodyWeight(), -1), {
    barUnits: 0,
    nutrition: -1,
  });
  assert.deepEqual(gainBodyWeight({ barUnits: 2, nutrition: 2147483647 }, 1), {
    barUnits: 2,
    nutrition: -2147483648,
  });
});

test("nutrition calls preserve ad.g's sentinel/tool branches and do not double with food energy", () => {
  assert.equal(foodNutrition(items[0]), 3);
  assert.equal(foodNutrition(items[27]), 5);
  assert.equal(
    foodNutrition(items[31]),
    -1,
    "positive-tool poison forwards the literal -1",
  );
  assert.equal(
    foodNutrition({
      ...items[0],
      effects: { ...items[0].effects, hunger: -1 },
    }),
    null,
  );
  setup();
  store.setState({
    inventory: { 27: 1, 76: 1 },
    survival: { ...initialSurvival(), energy: 10 },
  });
  assert.equal(store.getState().consume(27), true);
  assert.equal(
    store.getState().survival.energy,
    26,
    "existing double-energy quirk stays intact",
  );
  assert.equal(
    store.getState().bodyWeight.nutrition,
    5,
    "nutrition counts once",
  );
  assert.equal(store.getState().inventory[76], 1);
});

test("only successful meals change body weight, including full reserves and fatal food; failed/resting/locked use cannot award it", () => {
  setup();
  store.setState({ inventory: { 4: 1, 27: 1, 31: 1, 16: 1 } });
  assert.equal(store.getState().consume(999), false);
  assert.equal(store.getState().consume(27), false, "missing knife");
  store.getState().beginRest();
  assert.equal(store.getState().consume(4), false);
  store.getState().cancelRest();
  store.setState({ started: false });
  assert.equal(store.getState().consume(4), false);
  assert.deepEqual(store.getState().bodyWeight, initialBodyWeight());
  store.setState({ started: true });
  assert.equal(store.getState().consume(4), true);
  assert.equal(store.getState().survival.hunger, 100);
  assert.deepEqual(store.getState().bodyWeight, { barUnits: 1, nutrition: 50 });
  assert.equal(store.getState().consume(4), false);
  store.setState({
    bodyWeight: initialBodyWeight(),
    survival: initialSurvival(),
  });
  assert.equal(store.getState().consume(31), true);
  assert.equal(store.getState().survival.death, "poison");
  assert.deepEqual(store.getState().bodyWeight, { barUnits: 0, nutrition: -1 });
});

test("exploration preserves nutrition progression; crafting, discarding and rest do not reduce weight or create encumbrance", () => {
  setup();
  store.setState({
    bodyWeight: { barUnits: WEIGHT_MAX, nutrition: 49 },
    inventory: { 0: 2, 3: 1, 8: 1, 6: 1 },
  });
  store.getState().setSurvivalEnabled(false);
  assert.equal(store.getState().consume(0), true);
  assert.deepEqual(store.getState().bodyWeight, {
    barUnits: WEIGHT_MAX,
    nutrition: 2,
  });
  const before = structuredClone(store.getState().bodyWeight);
  assert.equal(
    store.getState().craft(5),
    true,
    "full weight does not prevent crafting or storing equipment",
  );
  store.getState().discard(0);
  store.getState().beginRest();
  store.getState().cancelRest();
  store.getState().setSurvivalEnabled(true);
  assert.deepEqual(store.getState().bodyWeight, before);
  assert.equal(store.getState().inventory[5], 1);
});

test("saves retain weight and queued nutrition, sleep preserves it, and legacy loads reset rather than infer past meals", async () => {
  setup();
  const weight = { barUnits: 7, nutrition: 100 };
  store.setState({ bodyWeight: weight });
  const save = SaveSystem.save,
    load = SaveSystem.load;
  let captured: SaveData | undefined;
  try {
    SaveSystem.save = async (data) => {
      captured = structuredClone(data);
    };
    await store.getState().save();
    assert.deepEqual(captured!.bodyWeight, weight);
    assert.ok(validSave(captured));
    SaveSystem.load = async () => captured!;
    store.setState({ bodyWeight: initialBodyWeight() });
    await store.getState().load();
    assert.deepEqual(store.getState().bodyWeight, weight);
    await store.getState().sleep();
    assert.deepEqual(captured!.bodyWeight, weight);
    SaveSystem.load = async () => base;
    await store.getState().load();
    assert.deepEqual(store.getState().bodyWeight, initialBodyWeight());
  } finally {
    SaveSystem.save = save;
    SaveSystem.load = load;
  }
});

test("body-weight validation accepts signed/queued nutrition and rejects malformed or out-of-reference bars", () => {
  assert.ok(validSave(base));
  for (const nutrition of [-2147483648, -1, 0, 50, 1000, 2147483647])
    assert.ok(validSave({ ...base, bodyWeight: { barUnits: 64, nutrition } }));
  for (const bad of [
    null,
    {},
    { barUnits: -1, nutrition: 0 },
    { barUnits: 65, nutrition: 0 },
    { barUnits: 0.5, nutrition: 0 },
    { barUnits: 0, nutrition: NaN },
    { barUnits: 0, nutrition: 2147483648 },
    { barUnits: 0, nutrition: "10" },
  ]) {
    assert.ok(!validBodyWeight(bad));
    assert.ok(!validSave({ ...base, bodyWeight: bad }));
  }
});
