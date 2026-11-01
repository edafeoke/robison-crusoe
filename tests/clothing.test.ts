import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type {
  RecoveredItem,
  AtlasManifest,
} from "../src/game/assets/assetManifest";
import { clothingRules as R } from "../src/game/data/clothingRules";
import {
  initialClothing,
  emptyOutfit,
  clothingProfile,
  clothingLayers,
  equipClothing,
  validClothing,
  huntingDamage,
  type ClothingState,
  type ClothingSlot,
  type Facing,
} from "../src/game/systems/ClothingSystem";
import {
  craftAvailability,
  craftInventory,
} from "../src/game/systems/ItemRules";
import {
  SaveSystem,
  validSave,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import {
  createHuntingGame,
  stepHunting,
  type HuntingGame,
} from "../src/game/systems/HuntingSystem";
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
  inventory: {},
  collected: [],
  inspected: [],
  discovered: [],
  elapsed: 0,
  enhancements: true,
};
test("16 recovered garment profiles cover all 256 exact BS5 regions with source slots", () => {
  const atlas: AtlasManifest = JSON.parse(
      readFileSync("public/assets/ui/atlas.json", "utf8"),
    ),
    frames = new Set<number>();
  assert.equal(R.profiles.length, 16);
  for (const p of R.profiles) {
    assert.equal(items[p.item].sprite, p.sequences.down);
    assert.equal(
      ["hat", "shirt", "shoes", "pants"][items[p.item].clothingSlot],
      p.slot,
    );
    for (const key of Object.values(p.sequences))
      for (let i = 0; i < p.frames; i++) {
        assert.ok(atlas.sheets[5].frames[(key & 65535) + i]);
        frames.add((key & 65535) + i);
      }
  }
  assert.deepEqual(
    [...frames].sort((a, b) => a - b),
    Array.from({ length: 256 }, (_, i) => i),
  );
  assert.deepEqual(R.hatOffsets.up, {
    x: [0, 0, 0, 0, 0],
    y: [0, -1, 0, -1, 0],
  });
  assert.deepEqual(R.hatOffsets.left, {
    x: [3, 0, 0, 0, 0],
    y: [-1, 0, -1, 0, -1],
  });
  assert.deepEqual(R.hatOffsets.right, {
    x: [-3, 0, 0, 0, 0],
    y: [-1, 0, -1, 0, -1],
  });
});
test("outfit layers preserve source order, all directional frames and hat bob anchors", () => {
  const outfit = { hat: 112, shirt: 99, pants: 104, shoes: 109 };
  assert.deepEqual(clothingLayers(outfit, "down", 0), [
    { sprite: 327865, dx: 0, dy: 0 },
    { sprite: 327750, dx: 0, dy: 0 },
    { sprite: 327680, dx: 0, dy: 0 },
    { sprite: 327924, dx: 0, dy: 0 },
  ]);
  assert.deepEqual(
    clothingLayers(outfit, "left", 0).map((c) => [c.sprite, c.dx, c.dy]),
    [
      [327895, 0, 0],
      [327780, 0, 0],
      [327710, 0, 0],
      [327926, 3, -1],
    ],
  );
  for (const dir of ["down", "up", "left", "right"] as Facing[])
    for (let frame = 0; frame < 5; frame++) {
      const layers = clothingLayers(outfit, dir, frame);
      assert.equal(layers.length, 4);
      assert.equal(layers[3].sprite, clothingProfile(112)!.sequences[dir]);
      assert.equal(layers[3].dy, R.hatOffsets[dir].y[frame]);
    }
  assert.deepEqual(clothingLayers(emptyOutfit(), "down", 0), []);
  assert.deepEqual(clothingLayers(outfit, "down", 5), []);
});
test("all garment recipes enforce materials, reusable tools, camp tier and unique ownership", () => {
  for (const p of R.profiles) {
    const item = items[p.item],
      inv = materials(p.item),
      tier =
        item.shelterRequirement === -1
          ? 17
          : item.shelterRequirement === 9
            ? 9
            : 10;
    assert.equal(craftAvailability(item, inv, tier).allowed, true, item.name);
    const next = craftInventory(item, inv, tier)!;
    assert.equal(next[item.id], undefined);
    for (const r of item.recipe) assert.equal(next[r.item], 0);
    for (const r of item.craftingTools) assert.equal(next[r.item], r.quantity);
    if (tier !== 17)
      assert.equal(craftAvailability(item, inv, 17).allowed, false);
    assert.equal(
      craftAvailability(
        item,
        inv,
        tier,
        {},
        { owned: [item.id], equipped: emptyOutfit() },
      ).allowed,
      false,
    );
  }
});
test("wearing requires ownership in the correct slot; changing does not consume or mutate clothes", () => {
  const s: ClothingState = {
      owned: [99, 104, 109, 112],
      equipped: emptyOutfit(),
    },
    original = structuredClone(s);
  assert.equal(equipClothing(s, "hat", 99), null);
  assert.equal(equipClothing(s, "hat", 114), null);
  const next = equipClothing(s, "hat", 112)!;
  assert.equal(next.equipped.hat, 112);
  assert.deepEqual(next.owned, s.owned);
  assert.deepEqual(s, original);
  assert.equal(equipClothing(next, "hat", 112), null);
  assert.equal(equipClothing(next, "hat", null)!.equipped.hat, null);
});
test("each predator checks its exact source slot and accepts every garment in that slot", () => {
  for (const rule of R.protection) {
    assert.equal(huntingDamage(rule.item, emptyOutfit()), rule.unprotected);
    for (const profile of R.profiles) {
      const outfit = { ...emptyOutfit(), [profile.slot]: profile.item };
      assert.equal(
        huntingDamage(rule.item, outfit),
        profile.slot === rule.slot ? rule.protected : rule.unprotected,
      );
    }
  }
  assert.equal(huntingDamage(4, { ...emptyOutfit(), hat: 112 }), 0);
});
test("hunting captures equipped protection and combines protected retaliation without changing ammunition rules", () => {
  const outfit = { hat: 112, shirt: 99, pants: 104, shoes: 109 };
  let game = createHuntingGame(
    R.protection.map((p, i) => ({ id: i, itemId: p.item })),
    1,
    outfit,
  );
  outfit.pants = 105;
  assert.equal(game.outfit.pants, 104);
  game = { ...game, phase: "attacking" };
  for (let i = 0; i < 17; i++) game = stepHunting(game, 90);
  assert.equal(game.status, "lost");
  assert.equal(game.attackEnergy, 65);
  let bare: HuntingGame = {
    ...createHuntingGame(
      R.protection.map((p, i) => ({ id: i, itemId: p.item })),
      1,
    ),
    phase: "attacking" as const,
  };
  for (let i = 0; i < 17; i++) bare = stepHunting(bare, 90);
  assert.equal(bare.attackEnergy, 130);
});
test("clothing save validation rejects wrong-slot, unowned, duplicate and malformed equipment", () => {
  const valid = { owned: [112], equipped: { ...emptyOutfit(), hat: 112 } };
  assert.equal(validClothing(initialClothing()), true);
  assert.equal(validSave({ ...base, clothing: valid }), true);
  assert.equal(validSave(base), true);
  for (const clothing of [
    null,
    { owned: [112, 112], equipped: emptyOutfit() },
    { owned: [112], equipped: { ...emptyOutfit(), shirt: 112 } },
    { owned: [], equipped: valid.equipped },
    { owned: [1], equipped: emptyOutfit() },
    { owned: [112], equipped: { hat: 112 } },
  ])
    assert.equal(validSave({ ...base, clothing }), false);
});
test("store creation charges exact effects, does not auto-equip, and refuses unsafe outfit changes", () => {
  store.setState({
    ready: true,
    started: true,
    items,
    clothing: initialClothing(),
    inventory: materials(112),
    survival: initialSurvival(),
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    huntingGame: null,
    saving: false,
  });
  assert.equal(store.getState().craft(112), true);
  assert.deepEqual(store.getState().clothing.owned, [112]);
  assert.equal(store.getState().clothing.equipped.hat, null);
  assert.equal(store.getState().survival.energy, 90);
  assert.equal(store.getState().survival.morale, 1);
  assert.equal(store.getState().inventory[112], undefined);
  assert.equal(store.getState().equip("hat", 112), true);
  assert.equal(store.getState().equip("shoes", 112), false);
  store.setState({
    huntingGame: createHuntingGame(
      [{ id: 1, itemId: 49 }],
      1,
      store.getState().clothing.equipped,
    ),
  });
  assert.equal(store.getState().equip("hat", null), false);
  assert.equal(store.getState().craft(109), false);
  store.setState({
    huntingGame: null,
    survival: { ...initialSurvival(), restRemainingMs: 10000 },
  });
  assert.equal(store.getState().equip("hat", null), false);
  store.setState({
    survival: { ...initialSurvival(), energy: 0, death: "energy" },
  });
  assert.equal(store.getState().equip("hat", null), false);
});
test("saves retain wardrobe and worn IDs while legacy loads reset to the source unequipped state", async () => {
  const load = SaveSystem.load,
    save = SaveSystem.save;
  let recorded: SaveData | undefined;
  try {
    store.setState({
      survival: initialSurvival(),
      started: true,
      saving: false,
      clothing: { owned: [112], equipped: { ...emptyOutfit(), hat: 112 } },
    });
    SaveSystem.save = async (d) => {
      recorded = d;
    };
    await store.getState().save();
    assert.equal(recorded!.clothing!.equipped.hat, 112);
    SaveSystem.load = async () => recorded!;
    store.setState({ clothing: initialClothing() });
    await store.getState().load();
    assert.equal(store.getState().clothing.equipped.hat, 112);
    SaveSystem.load = async () => base;
    await store.getState().load();
    assert.deepEqual(store.getState().clothing, initialClothing());
  } finally {
    SaveSystem.load = load;
    SaveSystem.save = save;
  }
});
