import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { coconutRules as R } from "../src/game/data/coconutRules";
import {
  createCoconutGame,
  aimCoconut,
  throwCoconutStone,
  stepCoconutGame,
  coconutOutcome,
  type CoconutGame,
} from "../src/game/systems/CoconutSystem";
import {
  isCoconutPalm,
  decorVariantIndex,
} from "../src/game/maps/DecorVariants";
import { nearestInteraction } from "../src/game/systems/InteractionSystem";
import {
  validSave,
  SaveSystem,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { findPath } from "../src/game/systems/MovementSystem";
import type {
  RecoveredItem,
  AtlasManifest,
} from "../src/game/assets/assetManifest";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const map = read("public/assets/maps/island.json"),
  items: RecoveredItem[] = read("public/assets/objects/objects.json");
function shot(s: CoconutGame, angle = 40, power = 70) {
  let next = throwCoconutStone(aimCoconut(s, angle, power));
  for (let i = 0; i < 400 && next.shot && next.status === "playing"; i++)
    next = stepCoconutGame(next, 90);
  return next;
}
test("coconut constants preserve n rules and the bytecode table omitted by CFR; all packed sprite references resolve", () => {
  const n = readFileSync("evidence/decompiled/data/n.java", "utf8"),
    b = readFileSync("evidence/bytecode/data.x.txt", "utf8");
  assert.match(n, /this.i == 10/);
  assert.match(n, /this.h < 5/);
  assert.match(n, /> 11000L/);
  assert.match(b, /Field a:\[I/);
  assert.deepEqual(
    R.sine,
    [26, 34, 42, 50, 57, 64, 71, 77, 81, 87, 91, 94, 96, 98, 99, 100],
  );
  assert.deepEqual(
    R.cosine,
    [97, 94, 91, 87, 81, 77, 71, 64, 57, 50, 42, 34, 26, 17, 9, 0],
  );
  const atlas: AtlasManifest = read("public/assets/ui/atlas.json");
  for (const key of [R.sprites.coconut, R.sprites.stone, ...R.sprites.leaves])
    assert.ok(atlas.sheets[key >>> 16].frames[key & 65535]);
});
test("coconut interaction belongs only to the displayed decor 18 / variant 8 palms and keeps raw map data intact", () => {
  const source = JSON.stringify(map.layers),
    palms: number[] = [];
  map.layers.Decor.forEach((_v: number, i: number) => {
    if (isCoconutPalm(map.layers.Decor, i)) palms.push(i);
  });
  assert.equal(palms.length, 25);
  for (const i of palms) {
    assert.equal(map.layers.Decor[i], 18);
    assert.equal(decorVariantIndex(i, 18, 13), 8);
  }
  const i = 28 + 160 * 180,
    focus = nearestInteraction(map, 27 * 16 + 8, 160 * 16 + 8, new Set());
  assert.equal(focus?.kind, "coconut-palm");
  assert.equal(focus?.index, i);
  const path = findPath(
    new CollisionSystem(map),
    { x: 22 * 16 + 8, y: 148 * 16 + 8 },
    { x: 27 * 16 + 8, y: 160 * 16 + 8 },
  );
  assert.ok(
    path.length > 0,
    "the starting shore must have a reachable coconut-bearing palm",
  );
  assert.ok(!isCoconutPalm(map.layers.Decor, -1));
  assert.ok(
    !isCoconutPalm(
      map.layers.Decor,
      map.layers.Decor.findIndex(
        (v: number, i: number) =>
          v === 18 && decorVariantIndex(i, 18, 13) !== 8,
      ),
    ),
  );
  assert.equal(JSON.stringify(map.layers), source);
});
test("seeded layouts remain within recovered ranges, use unique eligible anchors and cannot enter the source rejection-loop dead end", () => {
  for (let seed = 1; seed <= 2000; seed++) {
    const s = createCoconutGame(19296, seed);
    assert.ok(s.leaves.length >= 9 && s.leaves.length <= 28);
    assert.ok(
      s.targets.length >= 5 &&
        s.targets.length <= 7 &&
        s.targets.length <= s.leaves.length - 3,
    );
    assert.equal(
      new Set(s.targets.map((t) => `${t.x},${t.y}`)).size,
      s.targets.length,
    );
    for (const t of s.targets) {
      assert.ok(t.x >= 15 && t.x <= 233 && t.y >= 30 && t.y <= 100);
      assert.ok(s.leaves.slice(3).some((l) => l.x === t.x && l.y === t.y));
    }
  }
  assert.deepEqual(createCoconutGame(10, 333), createCoconutGame(10, 333));
});
test("Java integer trajectory, frame order, aim bounds and post-throw reset are preserved", () => {
  const s = aimCoconut(createCoconutGame(1, 1), 40, 70),
    initial = throwCoconutStone(s);
  const first = stepCoconutGame(initial, 90),
    second = stepCoconutGame(first, 90);
  assert.deepEqual([first.shot!.x, first.shot!.y], [19, 270]);
  assert.deepEqual([second.shot!.x, second.shot!.y], [28, 245]);
  assert.equal(
    initial.shot!.elapsed,
    0,
    "pure updates must not mutate the input",
  );
  assert.equal(throwCoconutStone(first), first);
  assert.equal(aimCoconut(first, 10, 100), first);
  const clamped = aimCoconut(s, 999, -1);
  assert.equal(clamped.angle, 70);
  assert.equal(clamped.power, 40);
  const end = shot(s);
  assert.equal(end.shot, null);
  assert.equal(end.angle, 70);
  assert.equal(end.power, 40);
});
test("a coconut needs two separate stones, expires its first hit after eleven seconds, then falls at five pixels per tick", () => {
  const s = createCoconutGame(1, 1);
  s.targets = [{ x: 84, y: 128, phase: 0, shakeMs: 0, hitShot: 0 }];
  const hit = shot(s);
  assert.equal(hit.targets[0].phase, 1);
  assert.equal(hit.knocked, 0);
  let expired = hit;
  for (let i = 0; i < 130; i++) expired = stepCoconutGame(expired, 90);
  assert.equal(expired.targets[0].phase, 0);
  const fallen = shot(hit);
  assert.equal(fallen.knocked, 1);
  assert.ok(fallen.targets[0].phase >= 2);
  const falling = {
    ...s,
    targets: [{ ...s.targets[0], phase: 2 as const }],
    knocked: 1,
  };
  assert.equal(stepCoconutGame(falling, 90).targets[0].y, 133);
});
test("the recovered challenge is winnable through real aim/flight/hits, and ten misses lose without a reward", () => {
  const palm = 28 + 160 * 180,
    seed = ((palm + 1) * 2654435761) >>> 0;
  let s = createCoconutGame(palm, seed);
  for (const [angle, power] of [
    [40, 84],
    [40, 84],
    [70, 62],
    [70, 62],
    [55, 72],
    [55, 72],
  ])
    s = shot(s, angle, power);
  assert.equal(s.knocked, 5);
  for (let i = 0; i < 130 && s.status === "playing"; i++)
    s = stepCoconutGame(s, 90);
  assert.equal(s.status, "won");
  assert.equal(s.throws, 6);
  let lose = createCoconutGame(1, 1);
  lose.targets = lose.targets.map((t) => ({ ...t, x: 230, y: 30 }));
  for (let i = 0; i < 10; i++) lose = shot(lose, 10, 40);
  assert.equal(lose.status, "lost");
  assert.equal(lose.throws, 10);
  assert.equal(lose.knocked, 0);
  assert.equal(throwCoconutStone(lose), lose);
  assert.equal(stepCoconutGame(lose, 90), lose);
});
test("completion rewards one coconut, costs one stone only on the recovered wear boundary, and preserves success/failure reset asymmetry", () => {
  const bag = { 3: 2, 1: 3 };
  const won = coconutOutcome(bag, 4, true)!;
  assert.equal(won.inventory[1], 4);
  assert.equal(won.inventory[3], 2);
  assert.equal(won.uses, 5);
  const sixth = coconutOutcome(bag, 5, true)!;
  assert.equal(sixth.inventory[3], 1);
  assert.equal(sixth.uses, 1);
  const lost = coconutOutcome(bag, 5, false)!;
  assert.equal(lost.inventory[1], 3);
  assert.equal(lost.inventory[3], 1);
  assert.equal(lost.uses, 0);
  assert.equal(coconutOutcome({}, 0, true), null);
  assert.equal(coconutOutcome(bag, 6, true), null);
  assert.deepEqual(bag, { 3: 2, 1: 3 });
});
test("wear counter saves are backward compatible and reject malformed values", () => {
  const old: SaveData = {
    version: 1,
    x: 136,
    y: 2376,
    inventory: { 3: 1 },
    collected: [],
    inspected: [],
    discovered: [],
    elapsed: 0,
    enhancements: true,
  };
  assert.ok(validSave(old));
  assert.ok(validSave({ ...old, coconutUses: 5 }));
  for (const value of [-1, 6, NaN, 1.5, "1", null])
    assert.ok(!validSave({ ...old, coconutUses: value }));
});
test("world start guards, locked world actions, pause, one-time settlement and fatal completion use the shared store safely", async () => {
  const { useGameStore } = await import("../src/store/gameStore");
  const palm = 28 + 160 * 180;
  useGameStore.setState({
    items,
    ready: true,
    started: true,
    worldDecor: map.layers.Decor,
    worldObjects: map.layers.Objects,
    inventory: { 3: 1 },
    coconutGame: null,
    coconutUses: 0,
    coconutSerial: 0,
    survival: initialSurvival(),
    x: 136,
    y: 2376,
    panel: null,
  });
  assert.equal(
    useGameStore.getState().startCoconutGame(palm),
    false,
    "remote tree",
  );
  useGameStore.setState({ x: 27 * 16 + 8, y: 160 * 16 + 8, inventory: {} });
  assert.equal(
    useGameStore.getState().startCoconutGame(palm),
    false,
    "missing stone",
  );
  useGameStore.setState({ inventory: { 3: 1 } });
  assert.equal(useGameStore.getState().startCoconutGame(palm), true);
  const before = useGameStore.getState().coconutGame;
  useGameStore.setState({ panel: "settings" });
  useGameStore.getState().advanceCoconut(90);
  assert.equal(useGameStore.getState().coconutGame, before);
  useGameStore.getState().discard(3);
  assert.equal(useGameStore.getState().inventory[3], 1);
  assert.equal(useGameStore.getState().consume(1), false);
  assert.equal(useGameStore.getState().craft(5), false);
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
  useGameStore.setState({ panel: null });
  for (const [angle, power] of [
    [40, 84],
    [40, 84],
    [70, 62],
    [70, 62],
    [55, 72],
    [55, 72],
  ]) {
    useGameStore.getState().aimCoconut(angle, power);
    useGameStore.getState().throwCoconut();
    for (let i = 0; i < 400 && useGameStore.getState().coconutGame?.shot; i++)
      useGameStore.getState().advanceCoconut(90);
  }
  for (
    let i = 0;
    i < 130 && useGameStore.getState().coconutGame?.status === "playing";
    i++
  )
    useGameStore.getState().advanceCoconut(90);
  assert.equal(useGameStore.getState().coconutGame?.status, "won");
  assert.equal(useGameStore.getState().inventory[1], 1);
  assert.equal(useGameStore.getState().coconutUses, 1);
  const bag = { ...useGameStore.getState().inventory },
    energy = useGameStore.getState().survival.energy;
  assert.equal(energy, 97);
  useGameStore.getState().advanceCoconut(90);
  assert.deepEqual(useGameStore.getState().inventory, bag);
  assert.equal(useGameStore.getState().survival.energy, energy);
  useGameStore.getState().leaveCoconut();
  assert.equal(useGameStore.getState().coconutGame, null);
  // Completing the last pending fall with low energy grants the source reward, then collapses.
  const terminal = {
    ...createCoconutGame(palm, 1),
    knocked: 5,
    targets: [{ x: 100, y: 270, phase: 2 as const, shakeMs: 0, hitShot: 0 }],
  };
  useGameStore.setState({
    coconutGame: terminal,
    survival: { ...initialSurvival(), energy: 2 },
  });
  useGameStore.getState().advanceCoconut(90);
  assert.equal(useGameStore.getState().coconutGame, null);
  assert.equal(useGameStore.getState().survival.death, "energy");
  assert.equal(useGameStore.getState().inventory[1], 2);
});
