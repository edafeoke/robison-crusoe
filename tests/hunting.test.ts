import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { huntingRules as R } from "../src/game/data/huntingRules";
import {
  createHuntingGame,
  aimHunt,
  fireHunt,
  stepHunting,
  huntingOutcome,
  huntingProfile,
  type HuntingGame,
} from "../src/game/systems/HuntingSystem";
import { WildlifeSystem } from "../src/game/systems/WildlifeSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import {
  validSave,
  SaveSystem,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../src/game/assets/assetManifest";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8")),
  map = read("public/assets/maps/island.json"),
  items: RecoveredItem[] = read("public/assets/objects/objects.json"),
  manifest = read("public/assets/animals/wildlife.json");
function shot(s: HuntingGame, angle: number, power: number) {
  s = fireHunt(aimHunt(s, angle, power));
  for (
    let i = 0;
    i < 300 && s.phase === "flight" && s.status === "playing";
    i++
  )
    s = stepHunting(s, 90);
  return s;
}
function firstWin(s: HuntingGame) {
  for (let a = 10; a <= 70; a += 5)
    for (let p = 40; p <= 100; p += 2) {
      const out = shot(s, a, p);
      if (out.status === "won") return { a, p, out };
    }
  throw new Error("No supported shot reaches target");
}
function misses(itemId: number) {
  let s = createHuntingGame([{ id: 1, itemId }], 1);
  for (let i = 0; i < 10; i++) {
    s = {
      ...s,
      targets: s.targets.map((t) => ({
        ...t,
        x: 200,
        y: 40,
        resting: true,
        movingX: false,
        movingY: false,
      })),
      initialRest: true,
    };
    s = shot(s, 70, 40);
  }
  return s;
}
test("all 15 hunting profiles preserve source frames, hitboxes, speed and danger quirks; shark remains unsupported", () => {
  assert.equal(R.profiles.length, 15);
  assert.deepEqual(
    R.profiles.filter((p) => p.dangerous).map((p) => p.item),
    [42, 43, 48, 49],
  );
  assert.equal(huntingProfile(55), undefined);
  assert.throws(() => createHuntingGame([{ id: 1, itemId: 55 }], 1));
  assert.throws(() => createHuntingGame([], 1));
  assert.throws(() => createHuntingGame([{ id: NaN, itemId: 4 }], 1));
  const a: AtlasManifest = read("public/assets/ui/atlas.json");
  for (const k of [
    R.bowSprite,
    ...R.arrowFrames,
    ...R.profiles.flatMap((p) => [...p.left, ...p.right, ...p.idle]),
  ])
    assert.ok(a.sheets[k >>> 16]?.frames[k & 65535]);
  assert.deepEqual(huntingProfile(47)!.idle, [131080, 131080]);
  assert.deepEqual(huntingProfile(61)!.idle, [131116, 131116]);
  assert.equal(huntingProfile(47)!.speedX, 1);
  assert.equal(items[45].name, "Arrow");
  assert.equal(items[81].effects.energy, -10);
  assert.equal(items[81].effects.strength, 1);
  const b = readFileSync("evidence/bytecode/data.s.txt", "utf8");
  assert.match(b, /int 851993/);
  assert.match(b, /bipush\s+45/);
});
test("seeded source layouts, initial resting targets and immutable integer arrow motion are stable", () => {
  const s = createHuntingGame([{ id: 1, itemId: 4 }], 1),
    before = structuredClone(s);
  assert.deepEqual([s.targets[0].x, s.targets[0].y], [153, 41]);
  let moving = s;
  for (let i = 0; i < 400; i++) moving = stepHunting(moving, 90);
  assert.deepEqual([moving.targets[0].x, moving.targets[0].y], [153, 41]);
  assert.deepEqual(s, before);
  let g = fireHunt(aimHunt(s, 40, 84));
  const points = [];
  for (let i = 0; i < 4; i++) {
    g = stepHunting(g, 90);
    points.push([g.shots[0].x, g.shots[0].y]);
  }
  assert.deepEqual(points, [
    [19, 270],
    [28, 245],
    [37, 221],
    [47, 200],
  ]);
  assert.equal(aimHunt(g, 10, 100), g);
  assert.equal(fireHunt(g), g);
  for (const d of [0, -1, NaN, Infinity]) assert.equal(stepHunting(s, d), s);
  assert.deepEqual(stepHunting(s, 10000), stepHunting(s, 180));
});
test("apex-only collisions, first-miss roaming, reset controls and ten-shot non-dangerous loss preserve source rules", () => {
  let s = createHuntingGame([{ id: 1, itemId: 4 }], 1);
  s = { ...s, targets: [{ ...s.targets[0], x: 20, y: 245 }] };
  const pass = stepHunting(stepHunting(fireHunt(aimHunt(s, 40, 84)), 90), 90);
  assert.equal(
    pass.status,
    "playing",
    "arrow crosses animal in flight without a hit check",
  );
  const miss = shot(createHuntingGame([{ id: 1, itemId: 4 }], 1), 70, 40);
  assert.equal(miss.phase, "aiming");
  assert.equal(miss.initialRest, false);
  assert.equal(miss.angle, 70);
  assert.equal(miss.power, 40);
  assert.ok(miss.targets[0].movingX && miss.targets[0].movingY);
  assert.equal(miss.shots[0].phase, 3);
  let roam = miss;
  for (let i = 0; i < 2000; i++) roam = stepHunting(roam, 90);
  assert.ok(roam.targets[0].x >= 24 && roam.targets[0].x <= 216);
  assert.ok(roam.targets[0].y >= 45 && roam.targets[0].y <= 106);
  const loss = misses(4);
  assert.equal(loss.status, "lost");
  assert.equal(loss.shots.length, 10);
  assert.equal(loss.attackEnergy, 0);
  assert.equal(fireHunt(loss), loss);
});
test("every supported species is winnable with real angle/power and grants its actual item with only one Arrow charged", () => {
  for (const profile of R.profiles) {
    const s = firstWin(
      createHuntingGame([{ id: 1, itemId: profile.item }], 1),
    ).out;
    const result = huntingOutcome({ 81: 1, 45: 2 }, s)!;
    assert.equal(result.inventory[profile.item], 1);
    assert.equal(result.inventory[45], 1);
    assert.equal(result.inventory[81], 1);
    assert.equal(result.target?.id, 1);
    assert.equal(stepHunting(s, 90), s);
  }
  assert.equal(huntingOutcome({ 81: 1 }, misses(4)), null);
  assert.equal(huntingOutcome({ 45: 1 }, misses(4)), null);
  assert.equal(
    huntingOutcome(
      { 81: 1, 45: 1 },
      createHuntingGame([{ id: 1, itemId: 4 }], 1),
    ),
    null,
  );
});
test("tenth-shot danger attacks interpolate for 1500 ms then apply exact unprotected energy; tenth-shot wins do not retaliate or double-settle", () => {
  for (const [item, cost] of Object.entries(R.attackEnergy)) {
    let s = misses(+item);
    assert.equal(s.phase, "attacking");
    assert.equal(s.status, "playing");
    const start = s.targets[0].x;
    s = stepHunting(s, 90);
    assert.ok(s.targets[0].x < start);
    for (let i = 0; i < 15; i++) s = stepHunting(s, 90);
    assert.equal(s.status, "playing");
    s = stepHunting(s, 90);
    assert.equal(s.status, "lost");
    assert.equal(s.attackEnergy, cost);
  }
  const base = createHuntingGame([{ id: 1, itemId: 42 }], 1),
    win = firstWin(base);
  const nine = {
    ...base,
    shots: Array.from({ length: 9 }, () => ({ ...win.out.shots[0] })),
  };
  const tenth = shot(nine, win.a, win.p);
  assert.equal(tenth.status, "won");
  assert.equal(tenth.attackEnergy, 0);
  assert.equal(tenth.shots.length, 10);
});
test("stable wildlife removal clears current occupancy, preserves other actors/raw maps and restores correctly from saves", () => {
  const before = structuredClone(map),
    w = new WildlifeSystem(map, manifest),
    a = w.actors.find((a) => a.mapId === 5)!,
    id = a.id,
    index = Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180,
    count = w.actors.length;
  assert.equal(w.actorAt(index)?.id, id);
  assert.equal(w.removeActor(id), true);
  assert.equal(w.removeActor(id), false);
  assert.equal(w.objects[index], 0);
  assert.equal(w.actors.length, count - 1);
  assert.ok(!w.snapshot().some((a) => a.id === id));
  assert.deepEqual(map, before);
  const restored = new WildlifeSystem(
    map,
    manifest,
    new Set([id]),
    w.snapshot(),
  );
  assert.equal(restored.actors.length, count - 1);
  assert.ok(!restored.actors.some((a) => a.id === id));
  assert.deepEqual(restored.snapshot(), w.snapshot());
  const save: SaveData = {
    version: 1,
    x: 136,
    y: 2376,
    inventory: { 81: 1, 45: 1, 4: 1 },
    collected: [id],
    inspected: [],
    elapsed: 1,
    discovered: [],
    enhancements: true,
    wildlife: w.snapshot(),
    survival: initialSurvival(),
  };
  assert.ok(validSave(save));
});
test("hunting store verifies live identity/equipment/range, locks actions, charges once and persists a successful removal before fatal energy", async () => {
  const { useGameStore: store } = await import("../src/store/gameStore");
  const w = new WildlifeSystem(map, manifest),
    a = w.actors.find((a) => a.mapId === 5)!,
    index = Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180;
  store.setState({
    ready: true,
    started: true,
    items,
    worldObjects: w.objects,
    captureWildlife: () => w.snapshot(),
    inventory: {},
    x: Math.floor(a.x / 16) * 16 + 24,
    y: Math.floor(a.y / 16) * 16 + 8,
    survival: initialSurvival(),
    panel: null,
    huntingGame: null,
    coconutGame: null,
    fishingGame: null,
    rodGame: null,
    collected: [],
    huntingSerial: 0,
  });
  assert.equal(store.getState().startHunting(index, 4, a.id), false);
  store.setState({ inventory: { 81: 1, 45: 2 } });
  assert.equal(store.getState().startHunting(index, 4, a.id + 1), false);
  store.setState({ x: 136, y: 2376 });
  assert.equal(store.getState().startHunting(index, 4, a.id), false);
  store.setState({
    x: Math.floor(a.x / 16) * 16 + 24,
    y: Math.floor(a.y / 16) * 16 + 8,
  });
  assert.equal(store.getState().startHunting(index, 4, a.id), true);
  store.getState().discard(45);
  assert.equal(store.getState().inventory[45], 2);
  assert.equal(store.getState().craft(91), false);
  assert.equal(store.getState().consume(0), false);
  store.getState().beginRest();
  assert.equal(store.getState().survival.restRemainingMs, null);
  store.getState().setPanel("inventory");
  assert.equal(store.getState().panel, null);
  store.getState().setPanel("settings");
  const paused = store.getState().huntingGame;
  store.getState().advanceHunting(90);
  store.getState().fireHunt();
  assert.equal(store.getState().huntingGame, paused);
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
  store.getState().leaveHunting();
  assert.equal(store.getState().inventory[45], 2);
  const initial = createHuntingGame([{ id: a.id, itemId: 4 }], 1),
    win = firstWin(initial);
  store.setState({ huntingGame: initial, panel: null });
  store.getState().aimHunt(win.a, win.p);
  store.getState().fireHunt();
  for (
    let i = 0;
    i < 250 && store.getState().huntingGame?.status === "playing";
    i++
  )
    store.getState().advanceHunting(90);
  assert.equal(store.getState().inventory[4], 1);
  assert.equal(store.getState().inventory[45], 1);
  assert.ok(store.getState().collected.includes(a.id));
  assert.equal(store.getState().survival.energy, 90);
  assert.equal(store.getState().survival.strength, 1);
  const inventory = store.getState().inventory;
  store.getState().advanceHunting(90);
  assert.equal(store.getState().inventory, inventory);
  store.setState({
    huntingGame: initial,
    inventory: { 81: 1, 45: 1 },
    collected: [],
    survival: { ...initialSurvival(), energy: 9 },
  });
  store.getState().aimHunt(win.a, win.p);
  store.getState().fireHunt();
  for (
    let i = 0;
    i < 250 && store.getState().huntingGame?.status === "playing";
    i++
  )
    store.getState().advanceHunting(90);
  assert.equal(store.getState().huntingGame, null);
  assert.equal(store.getState().survival.death, "energy");
  assert.equal(store.getState().inventory[4], 1);
  assert.ok(store.getState().collected.includes(a.id));
  const attack = misses(42);
  store.setState({ huntingGame: attack, survival: initialSurvival() });
  store.getState().leaveHunting();
  assert.equal(
    store.getState().huntingGame,
    attack,
    "cannot cancel a committed retaliation",
  );
  store.getState().setPanel("settings");
  store.getState().advanceHunting(180);
  assert.equal(store.getState().huntingGame, attack);
});

test("overlapping targets award only the last intersected animal, while grouped predators combine their source retaliation costs", () => {
  const base = createHuntingGame(
    [
      { id: 1, itemId: 4 },
      { id: 2, itemId: 43 },
    ],
    1,
  );
  base.targets[1] = {
    ...base.targets[1],
    x: base.targets[0].x,
    y: base.targets[0].y,
  };
  const win = shot(base, 45, 70),
    result = huntingOutcome({ 81: 1, 45: 2 }, win)!;
  assert.equal(win.status, "won");
  assert.equal(win.winner, 1);
  assert.equal(result.target?.id, 2);
  assert.equal(result.inventory[43], 1);
  assert.equal(result.inventory[4], undefined);
  assert.equal(result.inventory[45], 1);
  let attack = createHuntingGame(
    [
      { id: 1, itemId: 42 },
      { id: 2, itemId: 43 },
    ],
    1,
  );
  for (let i = 0; i < 10; i++) {
    attack = {
      ...attack,
      initialRest: true,
      targets: attack.targets.map((t) => ({
        ...t,
        x: 200,
        y: 40,
        resting: true,
        movingX: false,
        movingY: false,
      })),
    };
    attack = shot(attack, 70, 40);
  }
  assert.equal(attack.phase, "attacking");
  for (let i = 0; i < 17; i++) attack = stepHunting(attack, 90);
  assert.equal(attack.status, "lost");
  assert.equal(attack.attackEnergy, 100);
  assert.deepEqual(huntingOutcome({ 81: 1, 45: 2 }, attack)?.inventory, {
    81: 1,
    45: 1,
  });
});
