import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import { shipRules as R } from "../src/game/data/shipRules";
import {
  initialShip,
  validShip,
  advanceShip,
  shipRepairAvailability,
  shipRepairTarget,
  reconstructShip,
} from "../src/game/systems/ShipSystem";
import {
  craftAvailability,
  craftInventory,
  portableRecipes,
  supportedRecipes,
} from "../src/game/systems/ItemRules";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { initialClothing } from "../src/game/systems/ClothingSystem";
import { initialBodyWeight } from "../src/game/systems/BodyWeightSystem";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const items: RecoveredItem[] = read("public/assets/objects/objects.json"),
  map = read("public/assets/maps/island.json");
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
    inventory: materials(19),
    ship: initialShip(),
    survival: initialSurvival(),
    bodyWeight: initialBodyWeight(),
    clothing: initialClothing(),
    shelter: 17,
    furnishings: {},
    collected: [],
    inspected: [],
    discovered: [],
    x: 152 * 16 + 8,
    y: 31 * 16 + 8,
    worldObjects: [...map.layers.Objects],
    panel: "dialogue",
    focus: null,
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
  });
}

test("ship recovery retains the actual wreck, repair sprite, strict ending timer and both original flag anchors", () => {
  assert.equal(R.index, 152 + 30 * 180);
  assert.equal(map.layers.Objects[R.index], 19);
  assert.equal(items[18].sprite, R.wreckSprite);
  assert.equal(items[19].sprite, R.repairedSprite);
  assert.equal(R.strictDepartureMs, 8000);
  assert.deepEqual(R.wreckFlags, [
    { dx: -25, dy: -82 },
    { dx: 15, dy: -74 },
  ]);
  assert.deepEqual(R.repairedFlags, [
    { dx: -25, dy: -83 },
    { dx: 15, dy: -65 },
  ]);
  const atlas = read("public/assets/ui/atlas.json");
  for (const key of [R.wreckSprite, R.repairedSprite, ...R.flagFrames])
    assert.ok(atlas.sheets[key >>> 16].frames[key & 65535]);
  const v = readFileSync("evidence/bytecode/data.v.txt", "utf8"),
    a = readFileSync("evidence/bytecode/data.a.txt", "utf8");
  assert.ok(v.includes("Field data/p.aF:Z") && v.includes("Field data/p.H:J"));
  assert.ok(a.includes("Field data/p.aF:Z"));
});

test("Large sail becomes a genuine missing-art supply recipe with exact materials/tools and existing camp rules", () => {
  const inv = materials(54);
  assert.equal(items[54].sprite, -1);
  assert.deepEqual(items[54].recipe, [
    { item: 12, quantity: 4 },
    { item: 118, quantity: 1 },
    { item: 116, quantity: 1 },
  ]);
  assert.equal(craftAvailability(items[54], inv, 17).allowed, true);
  const next = craftInventory(items[54], inv, 17)!;
  assert.equal(next[54], 1);
  for (const r of items[54].recipe) assert.equal(next[r.item], 0);
  for (const r of items[54].craftingTools)
    assert.equal(next[r.item], r.quantity);
  assert.ok(portableRecipes(items).some((i) => i.id === 54));
  assert.equal(supportedRecipes(items).length, 43);
  assert.equal(craftInventory(items[54], { ...inv, 97: 0 }), null);
  assert.equal(
    craftAvailability(items[19], materials(19), 10).supported,
    false,
    "ship reconstruction is not a global portable recipe",
  );
});

test("reconstruction consumes six original material groups, keeps six tools, applies source effects and cannot repeat", () => {
  const inv = materials(19),
    before = structuredClone(inv),
    life = { ...initialSurvival(), energy: 90 };
  const result = reconstructShip(items, inv, life, initialShip())!;
  assert.deepEqual(result.ship, { phase: "departing", elapsedMs: 0 });
  assert.equal(result.inventory[19], undefined);
  for (const r of items[19].recipe) assert.equal(result.inventory[r.item], 0);
  for (const r of items[19].craftingTools)
    assert.equal(result.inventory[r.item], r.quantity);
  assert.deepEqual(result.survival, life);
  assert.deepEqual(inv, before);
  assert.equal(reconstructShip(items, inv, life, result.ship), null);
  assert.equal(
    reconstructShip(items, inv, life, { phase: "escaped", elapsedMs: 8001 }),
    null,
  );
});

test("missing materials/tools, rest and collapse reject reconstruction before any mutation", () => {
  const inv = materials(19);
  for (const r of [...items[19].recipe, ...items[19].craftingTools]) {
    const lacking = { ...inv, [r.item]: r.quantity - 1 };
    assert.equal(
      shipRepairAvailability(items, lacking, initialShip()).allowed,
      false,
    );
    assert.equal(
      reconstructShip(items, lacking, initialSurvival(), initialShip()),
      null,
    );
  }
  assert.equal(
    reconstructShip(
      items,
      inv,
      { ...initialSurvival(), restRemainingMs: 10000 },
      initialShip(),
    ),
    null,
  );
  assert.equal(
    reconstructShip(
      items,
      inv,
      { ...initialSurvival(), energy: 0, death: "energy" },
      initialShip(),
    ),
    null,
  );
});

test("only the real original ship cell within interaction distance accepts repair", () => {
  setup();
  const s = store.getState();
  assert.ok(shipRepairTarget(R.index, s));
  assert.ok(!shipRepairTarget(R.index + 1, s));
  assert.ok(!shipRepairTarget(R.index, { ...s, x: 136, y: 2376 }));
  assert.ok(!shipRepairTarget(R.index, { ...s, worldObjects: null }));
  assert.ok(!shipRepairTarget(R.index, { ...s, collected: [R.index] }));
  const other = [...map.layers.Objects];
  other[R.index] = 85;
  assert.ok(!shipRepairTarget(R.index, { ...s, worldObjects: other }));
  assert.equal(s.repairShip(R.index + 1), false);
  store.setState({ saving: true });
  assert.equal(store.getState().repairShip(R.index), false);
  store.setState({ saving: false, started: false });
  assert.equal(store.getState().repairShip(R.index), false);
});

test("departure follows the strict eight-second boundary and safely ignores invalid or repeated updates", () => {
  const start = { phase: "departing", elapsedMs: 0 } as const;
  assert.deepEqual(advanceShip(start, 8000), {
    phase: "departing",
    elapsedMs: 8000,
  });
  const win = advanceShip(advanceShip(start, 8000), 1);
  assert.deepEqual(win, { phase: "escaped", elapsedMs: 8001 });
  assert.equal(advanceShip(win, 1000), win);
  for (const delta of [NaN, Infinity, -1, 0])
    assert.equal(advanceShip(start, delta), start);
  assert.deepEqual(initialShip(), advanceShip(initialShip(), 9000));
});

test("store repair shifts north two tiles, locks actions and pauses the ending in settings; its timer has no reserve effect", () => {
  setup();
  store.setState({
    inventory: { ...materials(19), ...materials(54), 0: 1 },
    clothing: {
      owned: [112],
      equipped: { hat: null, shirt: null, pants: null, shoes: null },
    },
  });
  const y = store.getState().y;
  assert.equal(store.getState().repairShip(R.index), true);
  assert.equal(store.getState().y, y - 32);
  assert.equal(store.getState().worldObjects![R.index], 19);
  assert.deepEqual(store.getState().collected, []);
  assert.equal(store.getState().repairShip(R.index), false);
  assert.equal(store.getState().craft(54), false);
  assert.equal(store.getState().consume(0), false);
  assert.equal(store.getState().equip("hat", 112), false);
  store.getState().discard(0);
  assert.equal(store.getState().inventory[0], 1);
  store.getState().beginRest();
  assert.equal(store.getState().survival.restRemainingMs, null);
  store.getState().setPanel("crafting");
  assert.equal(store.getState().panel, null);
  store.getState().setPanel("settings");
  store.getState().advanceShip(250);
  assert.equal(store.getState().ship.elapsedMs, 0);
  store.getState().setPanel(null);
  const life = structuredClone(store.getState().survival);
  for (let i = 0; i < 32; i++) store.getState().advanceShip(250);
  assert.equal(store.getState().ship.phase, "departing");
  store.getState().advanceShip(1);
  assert.equal(store.getState().ship.phase, "escaped");
  assert.deepEqual(store.getState().survival, life);
});

test("collapse stops the departure clock until recovery", () => {
  setup();
  assert.ok(store.getState().repairShip(R.index));
  store.setState({
    panel: null,
    survival: { ...initialSurvival(), energy: 0, death: "energy" },
  });
  store.getState().advanceShip(250);
  assert.equal(store.getState().ship.elapsedMs, 0);
  store.setState({ survival: initialSurvival() });
  store.getState().advanceShip(250);
  assert.equal(store.getState().ship.elapsedMs, 250);
});

test("ship save state remains optional and validates phase, timing and loss consistency", () => {
  assert.ok(validSave(base));
  for (const ship of [
    initialShip(),
    { phase: "departing", elapsedMs: 499.5 },
    { phase: "escaped", elapsedMs: 8001 },
  ])
    assert.ok(validSave({ ...base, ship }));
  for (const ship of [
    null,
    { phase: "boat", elapsedMs: 0 },
    { phase: "wreck", elapsedMs: 1 },
    { phase: "departing", elapsedMs: 8001 },
    { phase: "escaped", elapsedMs: 8000 },
    { phase: "departing", elapsedMs: NaN },
    { phase: "departing", elapsedMs: -1 },
  ]) {
    assert.ok(!validShip(ship));
    assert.ok(!validSave({ ...base, ship }));
  }
  assert.ok(
    !validSave({
      ...base,
      ship: { phase: "escaped", elapsedMs: 8001 },
      survival: { ...initialSurvival(), energy: 0, death: "energy" },
    }),
  );
});

test("unfinished departures refuse saves/sleep; completed expeditions save and restore, and legacy loading restores the wreck", async () => {
  setup();
  store.getState().repairShip(R.index);
  const save = SaveSystem.save,
    load = SaveSystem.load;
  let writes = 0,
    captured: SaveData | undefined;
  try {
    SaveSystem.save = async (data) => {
      writes++;
      captured = structuredClone(data);
    };
    await store.getState().save();
    await store.getState().sleep();
    assert.equal(writes, 0);
    for (let i = 0; i < 33; i++) store.getState().advanceShip(250);
    await store.getState().save();
    assert.equal(writes, 1);
    assert.equal(captured!.ship!.phase, "escaped");
    assert.ok(validSave(captured));
    SaveSystem.load = async () => captured!;
    store.setState({ ship: initialShip() });
    await store.getState().load();
    assert.equal(store.getState().ship.phase, "escaped");
    SaveSystem.load = async () => base;
    await store.getState().load();
    assert.deepEqual(store.getState().ship, initialShip());
  } finally {
    SaveSystem.save = save;
    SaveSystem.load = load;
  }
});

test("another expedition resets the live world and ending but preserves the earlier stored expedition until an explicit save", async () => {
  setup();
  store.setState({
    ship: { phase: "escaped", elapsedMs: 8001 },
    shelter: 10,
    inventory: { 76: 1 },
    bodyWeight: { barUnits: 4, nutrition: 48 },
    collected: [123],
    inspected: [123],
    discovered: [123],
    enhancements: false,
    ambience: true,
  });
  const revision = store.getState().restoreRevision,
    save = SaveSystem.save;
  let writes = 0;
  try {
    SaveSystem.save = async () => {
      writes++;
    };
    assert.equal(store.getState().beginExpedition(), true);
    const s = store.getState();
    assert.equal(s.started, true);
    assert.deepEqual(s.ship, initialShip());
    assert.deepEqual(s.inventory, {});
    assert.deepEqual(s.collected, []);
    assert.equal(s.shelter, 17);
    assert.deepEqual(s.bodyWeight, initialBodyWeight());
    assert.deepEqual(s.clothing, initialClothing());
    assert.deepEqual([s.x, s.y], [136, 2376]);
    assert.equal(s.restoreRevision, revision + 1);
    assert.equal(s.enhancements, false);
    assert.equal(s.ambience, true);
    assert.equal(writes, 0);
    assert.equal(
      s.beginExpedition(),
      false,
      "active unfinished play cannot accidentally reset",
    );
  } finally {
    SaveSystem.save = save;
  }
});
