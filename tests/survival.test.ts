import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import {
  initialSurvival,
  advanceSurvival,
  validSurvival,
  consumeItem,
  activityEffect,
  foodEffects,
  useAvailability,
  REST_MS,
} from "../src/game/systems/SurvivalSystem";
import {
  validSave,
  SaveSystem,
  type SaveData,
} from "../src/game/systems/SaveSystem";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
const rows: number[][] = JSON.parse(
  readFileSync("evidence/objects-raw.json", "utf8"),
).map((r: string[]) => r.map(Number));
test("all 120 item effects preserve CSV rows 3–7 and verified field-to-label mapping", () => {
  for (const item of items)
    for (const [key, row] of [
      ["hunger", 3],
      ["thirst", 4],
      ["morale", 5],
      ["energy", 6],
      ["strength", 7],
    ] as const)
      assert.equal(
        item.effects[key],
        rows[row][item.id],
        `${item.name} ${key}`,
      );
  const aa = readFileSync("evidence/decompiled/data/aa.java", "utf8"),
    v = readFileSync("evidence/decompiled/data/v.java", "utf8"),
    ad = readFileSync("evidence/bytecode/data.ad.txt", "utf8");
  for (const id of [70, 71, 72, 73, 74]) assert.ok(aa.includes(`a(${id})`));
  assert.match(v, /this.a.k >= 60000L/);
  assert.match(ad, /putfield\s+#\d+\s+\/\/ Field data\/p.c:F/);
  assert.deepEqual(items[0].effects, {
    hunger: 3,
    thirst: 15,
    morale: 0,
    energy: 8,
    strength: 0,
  });
});
test("reserves drain once per real minute, preserve partial time and the source hour-rollover exception", () => {
  const start = initialSurvival(),
    partial = advanceSurvival(start, 59999);
  assert.equal(partial.thirst, 100);
  assert.equal(partial.minuteMs, 59999);
  const minute = advanceSurvival(partial, 1);
  assert.deepEqual([minute.hunger, minute.thirst, minute.energy], [90, 86, 96]);
  assert.deepEqual(
    [start.hunger, start.thirst, start.energy],
    [100, 100, 100],
    "input must remain unchanged",
  );
  const rollover = advanceSurvival(
    { ...initialSurvival(), minutes: 59 },
    60000,
  );
  assert.equal(rollover.minutes, 60);
  assert.equal(rollover.thirst, 100);
  assert.equal(advanceSurvival(rollover, 60000).thirst, 86);
  assert.deepEqual(advanceSurvival(minute, 0), minute);
  assert.deepEqual(advanceSurvival(minute, NaN), minute);
});
test("exhaustion stops simulation at the first depleted reserve and cannot overwrite an earlier loss", () => {
  const lost = advanceSurvival(initialSurvival(), 60000 * 20);
  assert.equal(lost.death, "thirst");
  assert.equal(lost.minutes, 8);
  assert.deepEqual([lost.hunger, lost.thirst, lost.energy], [20, 0, 68]);
  assert.deepEqual(advanceSurvival(lost, 60000), lost);
  assert.ok(validSurvival(lost));
});
test("rest lasts ten active seconds, restores 30 energy, and does not bypass minute-boundary exhaustion", () => {
  const resting = {
    ...initialSurvival(),
    energy: 40,
    restRemainingMs: REST_MS,
  };
  const partial = advanceSurvival(resting, 9000);
  assert.equal(partial.energy, 40);
  assert.equal(partial.restRemainingMs, 1000);
  const done = advanceSurvival(partial, 1000);
  assert.equal(done.energy, 70);
  assert.equal(done.restRemainingMs, null);
  const capped = advanceSurvival({ ...resting, energy: 90 }, 10000);
  assert.equal(capped.energy, 100);
  const exhausted = advanceSurvival(
    { ...resting, energy: 3, minuteMs: 59500 },
    10000,
  );
  assert.equal(exhausted.death, "energy");
  assert.equal(exhausted.restRemainingMs, null);
});
test("free food restores exact reserves atomically; unavailable, non-food, resting and dead use attempts fail", () => {
  const life = { ...initialSurvival(), hunger: 40, thirst: 30, energy: 50 };
  const inventory = { 0: 2, 3: 1 },
    result = consumeItem(life, items[0], inventory)!;
  assert.deepEqual(
    [result.survival.hunger, result.survival.thirst, result.survival.energy],
    [43, 45, 58],
  );
  assert.equal(result.inventory[0], 1);
  assert.equal(inventory[0], 2);
  assert.equal(consumeItem(life, items[0], {}), null);
  assert.equal(consumeItem(life, items[3], inventory), null);
  assert.equal(
    consumeItem({ ...life, restRemainingMs: 1000 }, items[0], inventory),
    null,
  );
  assert.equal(
    consumeItem({ ...life, energy: 0, death: "energy" }, items[0], inventory),
    null,
  );
  assert.equal(
    consumeItem(
      { ...life, hunger: 99, thirst: 99, energy: 99 },
      items[0],
      inventory,
    )!.survival.energy,
    100,
  );
});
test("tool food preserves double energy effects, reusable equipment and six-use stone wear", () => {
  let life = { ...initialSurvival(), hunger: 20, thirst: 20, energy: 10 },
    inventory: Record<number, number> = { 1: 7, 3: 1 };
  assert.equal(useAvailability(items[1], { 1: 1 }).allowed, false);
  for (let use = 1; use <= 6; use++) {
    const result = consumeItem(life, items[1], inventory)!;
    life = result.survival;
    inventory = result.inventory;
    assert.equal(inventory[3], use === 6 ? 0 : 1);
    assert.equal(life.stoneUses, use % 6);
    if (use === 1) assert.equal(life.energy, 26);
  }
  assert.equal(inventory[1], 1);
  assert.equal(consumeItem(life, items[1], inventory), null);
  const pineapple = consumeItem(
    { ...initialSurvival(), energy: 10 },
    items[27],
    { 27: 1, 76: 1 },
  )!;
  assert.equal(pineapple.survival.energy, 26);
  assert.equal(pineapple.inventory[76], 1);
  assert.equal(useAvailability(items[30], { 30: 1 }).allowed, false);
  const cooked = consumeItem({ ...initialSurvival(), energy: 10 }, items[30], {
    30: 1,
    16: 1,
  })!;
  assert.equal(cooked.survival.energy, 20);
  assert.equal(cooked.inventory[16], 1);
});
test("poisonous red mushroom follows the original -50 twice and no -1 food sentinel is silently reinterpreted in the tool branch", () => {
  const result = consumeItem(initialSurvival(), items[31], { 31: 1, 16: 1 })!;
  assert.equal(result.survival.energy, 0);
  assert.equal(result.survival.death, "poison");
  assert.equal(result.survival.hunger, 99);
  assert.equal(result.survival.thirst, 99);
  assert.equal(result.inventory[31], 0);
  assert.equal(result.inventory[16], 1);
  assert.deepEqual(foodEffects(items[31]), {
    hunger: -1,
    thirst: -1,
    energy: -100,
    morale: 0,
    strength: 0,
  });
});
test("successful pickup and creation charge their source activity effects; food pickup waits until eating", () => {
  const s = initialSurvival();
  const stone = activityEffect(s, items[3], "collect");
  assert.equal(stone.energy, 97);
  assert.equal(stone.strength, 1);
  assert.deepEqual(activityEffect(s, items[0], "collect"), s);
  const axe = activityEffect(s, items[5], "craft");
  assert.equal(axe.energy, 90);
  assert.equal(axe.strength, 1);
  assert.equal(
    activityEffect({ ...s, energy: 2 }, items[3], "collect").death,
    "energy",
  );
});
test("exploration mode suspends losses while preserving food consumption, clock and stone-use progression", () => {
  const s = initialSurvival(false),
    later = advanceSurvival(s, 60000 * 10);
  assert.deepEqual([later.hunger, later.thirst, later.energy], [100, 100, 100]);
  assert.equal(later.minutes, 10);
  assert.equal(later.death, null);
  const result = consumeItem(s, items[31], { 31: 1, 16: 1 })!;
  assert.equal(result.survival.death, null);
  assert.equal(result.survival.energy, 100);
  assert.equal(result.inventory[31], 0);
});
test("survival saves retain partial minute/rest/tool wear; malformed fields fail and old saves still migrate safely", async () => {
  const legacy: SaveData = {
    version: 1,
    x: 136,
    y: 2376,
    inventory: {},
    collected: [],
    inspected: [],
    discovered: [],
    elapsed: 73,
    enhancements: true,
  };
  const life = advanceSurvival(
    { ...initialSurvival(), restRemainingMs: 10000, stoneUses: 5, energy: 20 },
    3500,
  );
  assert.ok(validSave({ ...legacy, survival: life }));
  const roundtrip = JSON.parse(JSON.stringify(life));
  assert.deepEqual(
    advanceSurvival(roundtrip, 90000),
    advanceSurvival(life, 90000),
  );
  for (const invalid of [
    { ...life, minuteMs: 60000 },
    { ...life, energy: NaN },
    { ...life, stoneUses: 6 },
    { ...life, restRemainingMs: 0 },
    { ...life, death: "health" },
    { ...life, energy: 0, death: null },
  ])
    assert.ok(!validSave({ ...legacy, survival: invalid }));
  assert.ok(validSave(legacy));
  const { useGameStore } = await import("../src/store/gameStore");
  const load = SaveSystem.load;
  try {
    SaveSystem.load = async () => legacy;
    await useGameStore.getState().load();
    assert.equal(useGameStore.getState().survival.enabled, false);
    assert.equal(useGameStore.getState().survival.minutes, 1);
    assert.equal(useGameStore.getState().survival.minuteMs, 13000);
    assert.equal(useGameStore.getState().survival.hunger, 100);
  } finally {
    SaveSystem.load = load;
  }
});
test("store actions reject forged eating and resting crafts; death blocks saving and exploration can recover without changing inventory", async () => {
  const { useGameStore } = await import("../src/store/gameStore");
  useGameStore.setState({
    items,
    ready: true,
    started: true,
    inventory: { 0: 1, 3: 1, 8: 1, 6: 1 },
    survival: initialSurvival(),
  });
  assert.equal(useGameStore.getState().consume(999), false);
  assert.equal(useGameStore.getState().consume(1), false);
  useGameStore.getState().beginRest();
  assert.equal(useGameStore.getState().consume(0), false);
  assert.equal(useGameStore.getState().craft(5), false);
  useGameStore.getState().cancelRest();
  assert.equal(useGameStore.getState().consume(0), true);
  assert.equal(useGameStore.getState().consume(0), false);
  useGameStore.setState({
    survival: { ...initialSurvival(), energy: 5 },
    panel: "crafting",
  });
  assert.equal(useGameStore.getState().craft(5), true);
  assert.equal(useGameStore.getState().survival.death, "energy");
  assert.equal(
    useGameStore.getState().panel,
    null,
    "fatal crafting must close the panel before showing the loss dialog",
  );
  assert.equal(
    useGameStore.getState().inventory[5],
    1,
    "completed creation is retained on exhaustion",
  );
  const save = SaveSystem.save;
  let writes = 0;
  try {
    SaveSystem.save = async () => {
      writes++;
    };
    await useGameStore.getState().save();
    assert.equal(writes, 0);
  } finally {
    SaveSystem.save = save;
  }
  const before = { ...useGameStore.getState().inventory };
  useGameStore.getState().setSurvivalEnabled(false);
  assert.equal(useGameStore.getState().survival.death, null);
  assert.equal(useGameStore.getState().survival.energy, 100);
  assert.deepEqual(useGameStore.getState().inventory, before);
});
