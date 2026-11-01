import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import {
  initialResourceHistory,
  rememberPickup,
  migrateResourceHistory,
  refreshResources,
  validResourceHistory,
  crossedResourceReset,
} from "../src/game/systems/ResourceHistorySystem";
import { resourceHistoryRules as R } from "../src/game/data/resourceHistoryRules";
import {
  validSave,
  SaveSystem,
  type SaveData,
} from "../src/game/systems/SaveSystem";
import { useGameStore as store } from "../src/store/gameStore";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
import { initialRaft } from "../src/game/systems/RaftSystem";
import { initialShip } from "../src/game/systems/ShipSystem";
import { WildlifeSystem } from "../src/game/systems/WildlifeSystem";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8")),
  items = read("public/assets/objects/objects.json"),
  map = read("public/assets/maps/island.json");
const sail = map.layers.Objects.indexOf(13),
  knife = map.layers.Objects.indexOf(77),
  giant = map.layers.Objects.indexOf(94);
const branches: number[] = map.layers.Objects.flatMap((v: number, i: number) =>
  v === 9 ? [i] : [],
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
test("source histories preserve twenty recent supplies, seventy longer removals, unique filters and mobile exclusions", () => {
  assert.equal(R.recentCapacity, 20);
  assert.equal(R.permanentCapacity, 70);
  assert.deepEqual(R.minedItems, [93, 21, 38]);
  assert.equal(R.uniqueMapIds.length, 49);
  assert.ok(!R.uniqueMapIds.includes(13 as never));
  assert.ok(R.uniqueMapIds.includes(77));
  assert.equal(R.mobileMapIds.length, 22);
  const audit = read("evidence/progression-material-audit.json");
  for (const [id, quantity, raw] of [
    [12, 5, 1],
    [13, 17, 13],
    [6, 39, 32],
  ]) {
    const row = audit.materials.find((r: { id: number }) => r.id === id);
    assert.equal(row.quantity, quantity);
    assert.equal(row.rawStock, raw);
    assert.ok(row.shortfall > 0);
  }
});
test("a gathered Sail stays hidden through nineteen further pickups, then returns only on a refresh after the twentieth", () => {
  let history = rememberPickup(initialResourceHistory(), items[12], sail);
  const before = structuredClone(history);
  for (const i of branches.slice(0, 19))
    history = rememberPickup(history, items[8], i);
  assert.deepEqual(
    refreshResources(map.layers.Objects, items, [sail], history, { 12: 1 })
      .revived,
    [],
  );
  history = rememberPickup(history, items[8], branches[19]);
  assert.equal(history.recent.length, 20);
  const fresh = refreshResources(map.layers.Objects, items, [sail], history, {
    12: 1,
  });
  assert.deepEqual(fresh.revived, [sail]);
  assert.deepEqual(fresh.collected, []);
  assert.deepEqual(before, { recent: [sail], permanent: [] });
});
test("quarried materials use the seventy-slot history; the source ring overwrites instead of growing indefinitely", () => {
  let history = rememberPickup(initialResourceHistory(), items[93], giant);
  for (let n = 0; n < 69; n++) history = rememberPickup(history, items[21], n);
  assert.ok(history.permanent.includes(giant));
  assert.equal(
    refreshResources(map.layers.Objects, items, [giant], history, {}).revived
      .length,
    0,
  );
  history = rememberPickup(history, items[38], 100);
  assert.equal(history.permanent.length, 70);
  assert.ok(!history.permanent.includes(giant));
  assert.deepEqual(
    refreshResources(map.layers.Objects, items, [giant], history, {}).revived,
    [giant],
  );
});
test("carried unique equipment remains removed, while discarded tools can return at the next refresh", () => {
  const h = rememberPickup(initialResourceHistory(), items[76], knife);
  assert.deepEqual(h, initialResourceHistory());
  assert.deepEqual(
    refreshResources(map.layers.Objects, items, [knife], h, { 76: 1 }).revived,
    [],
  );
  assert.deepEqual(
    refreshResources(map.layers.Objects, items, [knife], h, {}).revived,
    [knife],
  );
});
test("direct world meals without a TAKE entry can return next tide; animals and structures never respawn through this static supply path", () => {
  const mango = map.layers.Objects.indexOf(1),
    goat = map.layers.Objects.indexOf(5),
    camp = map.layers.Objects.indexOf(18),
    h = initialResourceHistory();
  assert.deepEqual(
    refreshResources(map.layers.Objects, items, [mango, goat, camp], h, {})
      .revived,
    [mango],
  );
  assert.deepEqual(
    refreshResources(
      map.layers.Objects,
      items,
      [mango],
      rememberPickup(h, items[0], mango),
      {},
    ).revived,
    [],
  );
});
test("occupied source cells defer renewal until a later refresh without trapping the player or overwriting an animal or raft", () => {
  const blocked = refreshResources(
    map.layers.Objects,
    items,
    [sail],
    initialResourceHistory(),
    {},
    (i) => i === sail,
  );
  assert.deepEqual(blocked.collected, [sail]);
  assert.deepEqual(blocked.revived, []);
  assert.deepEqual(
    refreshResources(
      map.layers.Objects,
      items,
      blocked.collected,
      blocked.resources,
      {},
    ).revived,
    [sail],
  );
});
test("04:00 clears only the recent history, once per crossed browser day; ordinary tides do not clear it", () => {
  assert.equal(crossedResourceReset(71999.9, 72000), true);
  assert.equal(crossedResourceReset(72000, 72000.1), false);
  assert.equal(crossedResourceReset(360, 720), false);
  assert.equal(crossedResourceReset(158399, 158400), true);
  const h = { recent: [sail], permanent: [giant] },
    result = refreshResources(
      map.layers.Objects,
      items,
      [sail, giant],
      h,
      {},
      () => false,
      true,
    );
  assert.deepEqual(result.revived, [sail]);
  assert.deepEqual(result.resources, { recent: [], permanent: [giant] });
  assert.deepEqual(h, { recent: [sail], permanent: [giant] });
});
test("history saves are optional, bounded, index-validated and migrate legacy pickup order conservatively", () => {
  assert.ok(validSave(base));
  assert.ok(validSave({ ...base, resources: initialResourceHistory() }));
  for (const resources of [
    null,
    {},
    { recent: [-1], permanent: [] },
    { recent: [], permanent: [32400] },
    { recent: Array(21).fill(0), permanent: [] },
    { recent: [], permanent: Array(71).fill(0) },
    { recent: [1.5], permanent: [] },
  ]) {
    assert.equal(validResourceHistory(resources), false);
    assert.equal(validSave({ ...base, resources }), false);
  }
  const order = [sail, ...branches.slice(0, 20), giant, knife],
    legacy = migrateResourceHistory(map.layers.Objects, items, order);
  assert.deepEqual(legacy.recent, branches.slice(0, 20));
  assert.deepEqual(legacy.permanent, [giant]);
});
test("actual store pickups record source history; crafting, consumption, saves, legacy loads and new expeditions do not invent grants", async () => {
  store.setState({
    items,
    ready: true,
    started: true,
    saving: false,
    worldMap: map,
    worldObjects: [...map.layers.Objects],
    resources: initialResourceHistory(),
    collected: [],
    inventory: {},
    survival: initialSurvival(false),
    raft: initialRaft(),
    ship: initialShip(),
    panel: null,
    coconutGame: null,
    rodGame: null,
    fishingGame: null,
    huntingGame: null,
  });
  for (const index of [sail, ...branches.slice(0, 20)]) {
    store.setState({
      x: (index % 180) * 16 + 8,
      y: Math.floor(index / 180) * 16 + 8,
    });
    assert.ok(store.getState().collect(index, map.layers.Objects[index] - 1));
    const objects = [...store.getState().worldObjects!];
    objects[index] = 0;
    store.setState({ worldObjects: objects });
  }
  assert.equal(store.getState().inventory[12], 1);
  assert.equal(store.getState().inventory[8], 20);
  assert.ok(!store.getState().resources!.recent.includes(sail));
  const originalSave = SaveSystem.save,
    originalLoad = SaveSystem.load;
  let saved: SaveData | undefined;
  try {
    SaveSystem.save = async (d) => {
      saved = structuredClone(d);
    };
    SaveSystem.load = async () => saved ?? null;
    await store.getState().save();
    assert.deepEqual(saved!.resources, store.getState().resources);
    store.setState({ resources: initialResourceHistory() });
    await store.getState().load();
    assert.deepEqual(store.getState().resources, saved!.resources);
    saved = { ...base, collected: [sail] };
    await store.getState().load();
    assert.equal(store.getState().resources, undefined);
    store.setState({ started: false });
    store.getState().beginExpedition();
    assert.deepEqual(store.getState().resources, initialResourceHistory());
    assert.deepEqual(store.getState().inventory, {});
  } finally {
    SaveSystem.save = originalSave;
    SaveSystem.load = originalLoad;
  }
});
test("static resource restoration preserves moving wildlife and refuses occupied cells", () => {
  const w = new WildlifeSystem(
      map,
      read("public/assets/animals/wildlife.json"),
      new Set([sail]),
    ),
    before = w.snapshot();
  assert.equal(w.objects[sail], 0);
  assert.equal(w.restoreStatic(sail, 13), true);
  assert.equal(w.restoreStatic(sail, 13), false);
  const occupied = before.find((a) => a.phase !== "fly")!;
  assert.equal(
    w.restoreStatic(
      Math.floor(occupied.x / 16) + Math.floor(occupied.y / 16) * 180,
      13,
    ),
    false,
  );
  assert.deepEqual(w.snapshot(), before);
});

test("the one original Sail can supply the five-sail construction plan through four genuine twenty-pickup renewal cycles", () => {
  let resources = initialResourceHistory(),
    collected: number[] = [],
    inventory: Record<number, number> = {},
    objects = [...map.layers.Objects];
  const gather = (index: number) => {
    const item = items[objects[index] - 1];
    assert.ok(item);
    inventory[item.id] = (inventory[item.id] ?? 0) + 1;
    resources = rememberPickup(resources, item, index);
    collected.push(index);
    objects[index] = 0;
  };
  gather(sail);
  for (let cycle = 0; cycle < 4; cycle++) {
    for (const index of branches.slice(cycle * 20, (cycle + 1) * 20))
      gather(index);
    const result = refreshResources(
      map.layers.Objects,
      items,
      collected,
      resources,
      inventory,
    );
    assert.ok(result.revived.includes(sail));
    collected = [...result.collected];
    resources = result.resources;
    for (const index of result.revived)
      objects[index] = map.layers.Objects[index];
    gather(sail);
  }
  assert.equal(inventory[12], 5);
  assert.equal(map.layers.Objects.filter((v: number) => v === 13).length, 1);
  assert.equal(objects[sail], 0);
  assert.equal(resources.recent.at(-1), sail);
});
