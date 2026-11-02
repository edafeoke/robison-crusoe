/** Spatial/tool closure, NOT a travel-time, renewal, survival or complete-playthrough simulation. */
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import type { RecoveredItem } from "../../src/game/assets/assetManifest";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import { canPickup, craftInventory } from "../../src/game/systems/ItemRules";
import { tidalMap } from "../../src/game/systems/TideSystem";
import { resourceHistoryRules as R } from "../../src/game/data/resourceHistoryRules";
import {
  boardingPoint,
  cellPoint,
  initialRaft,
  landingPoint,
  raftObjects,
  repairRaft,
} from "../../src/game/systems/RaftSystem";
import {
  shelterAllows,
  isShelterUpgrade,
  shelterOwned,
  type Shelter,
} from "../../src/game/systems/ShelterSystem";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
export type AuditEvent =
  | {
      kind: "pickup";
      index: number;
      itemId: number;
      stand: number;
      stage: string;
    }
  | { kind: "craft"; itemId: number }
  | { kind: "repair"; itemId: 94 };
const start = 8 + 148 * 180;
const neighbors = (i: number) =>
  [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1],
  ].flatMap(([dx, dy]) => {
    const x = (i % 180) + dx,
      y = Math.floor(i / 180) + dy;
    return x >= 0 && x < 180 && y >= 0 && y < 180 ? [x + y * 180] : [];
  });
function flood(c: CollisionSystem, roots: Iterable<number>) {
  const queue = [...roots].filter((i) =>
      c.walkable(i % 180, Math.floor(i / 180)),
    ),
    parents = new Map(queue.map((i) => [i, -1]));
  for (let n = 0; n < queue.length; n++)
    for (const i of neighbors(queue[n]))
      if (!parents.has(i) && c.walkable(i % 180, Math.floor(i / 180))) {
        parents.set(i, queue[n]);
        queue.push(i);
      }
  return parents;
}
function approach(index: number, reachable: Map<number, number>) {
  const p = cellPoint(index);
  for (let dy = -2; dy <= 2; dy++)
    for (let dx = -2; dx <= 2; dx++) {
      const x = (index % 180) + dx,
        y = Math.floor(index / 180) + dy,
        i = x + y * 180;
      if (
        x >= 0 &&
        y >= 0 &&
        x < 180 &&
        y < 180 &&
        reachable.has(i) &&
        Math.hypot(cellPoint(i).x - p.x, cellPoint(i).y - p.y) <= 44
      )
        return i;
    }
  return null;
}
function trace(parents: Map<number, number>, i: number) {
  const path: number[] = [];
  while (i !== -1) {
    path.push(i);
    i = parents.get(i)!;
  }
  return path.reverse();
}
export function auditAcquisition(source: IslandMap, items: RecoveredItem[]) {
  const map = tidalMap(source, "low"),
    objects = [...map.layers.Objects],
    collision = new CollisionSystem(map, new Set(), objects);
  let inventory: Record<number, number> = {},
    reachable = new Map<number, number>();
  let shelter: Shelter = 17;
  const events: AuditEvent[] = [];
  const roots = new Set([start]),
    pickups: { index: number; itemId: number; stand: number; stage: string }[] =
      [],
    crafts: number[] = [];
  const tools: {
    itemId: number;
    name: string;
    index: number;
    stage: string;
    path: number[];
  }[] = [];
  function closure(stage: string) {
    for (let round = 0; round < 32400; round++) {
      reachable = flood(collision, roots);
      let changed = false;
      for (let i = 0; i < objects.length; i++) {
        const v = objects[i];
        if (!v || (R.mobileMapIds as readonly number[]).includes(v)) continue;
        const item = items[v - 1],
          stand = approach(i, reachable);
        if (stand === null || !canPickup(item, inventory)) continue;
        inventory[item.id] = (inventory[item.id] ?? 0) + 1;
        objects[i] = 0;
        pickups.push({ index: i, itemId: item.id, stand, stage });
        events.push({
          kind: "pickup",
          index: i,
          itemId: item.id,
          stand,
          stage,
        });
        if (item.category === 1)
          tools.push({
            itemId: item.id,
            name: item.name,
            index: i,
            stage,
            path: trace(reachable, stand),
          });
        changed = true;
      }
      if (!inventory[5]) {
        const next = craftInventory(items[5], inventory);
        if (next) {
          inventory = next;
          crafts.push(5);
          events.push({ kind: "craft", itemId: 5 });
          changed = true;
        }
      }
      if (!changed) return;
    }
    throw new Error("Acquisition closure did not converge");
  }
  function make(
    id: number,
    quantity: number,
    active = new Set<number>(),
  ): boolean {
    if ((inventory[id] ?? 0) >= quantity || shelterOwned(id, shelter))
      return true;
    const item = items[id];
    if (!item.recipe.length || active.has(id)) return false;
    const chain = new Set(active).add(id);
    if (
      !shelterAllows(item, shelter) &&
      !make(item.shelterRequirement, 1, chain)
    )
      return false;
    if (isShelterUpgrade(id) && !objects.includes(18)) return false;
    while ((inventory[id] ?? 0) < quantity && !shelterOwned(id, shelter)) {
      for (const r of [...item.recipe, ...item.craftingTools])
        if (!make(r.item, r.quantity, chain)) return false;
      const next = craftInventory(item, inventory, shelter);
      if (!next) return false;
      inventory = next;
      crafts.push(id);
      events.push({ kind: "craft", itemId: id });
      if (isShelterUpgrade(id)) shelter = id;
    }
    return true;
  }
  const check = (mapId: number) =>
    source.layers.Objects.flatMap((v, index) =>
      v === mapId
        ? [{ index, reachable: approach(index, reachable) !== null }]
        : [],
    );
  closure("foot");
  const beforeRaft = {
    cells: reachable.size,
    wreck: check(19),
    sextant: check(23),
    tools: tools.map((t) => t.name),
  };
  const raft = initialRaft(),
    raftApproach = approach(raft.index!, reachable);
  let repaired = false,
    boarding: number | null = null;
  const waterParents = new Map<number, number>(),
    shores: { water: number; land: number }[] = [];
  if (
    raftApproach !== null &&
    [...items[94].recipe, ...items[94].craftingTools].every((r) =>
      make(r.item, r.quantity),
    )
  ) {
    const result = repairRaft(items, inventory, initialSurvival(), raft);
    if (result) {
      repaired = true;
      inventory = result.inventory;
      events.push({ kind: "repair", itemId: 94 });
      const moored = raftObjects(objects, result.raft);
      const boardCollision = new CollisionSystem(map, new Set(), moored);
      boarding =
        neighbors(raft.index!).find(
          (i) =>
            reachable.has(i) &&
            boardingPoint(
              result.raft,
              cellPoint(i).x,
              cellPoint(i).y,
              boardCollision,
            ),
        ) ?? null;
      if (boarding !== null) {
        objects[raft.index!] = 0;
        collision.mode = "raft";
        for (const [i, parent] of flood(collision, [raft.index!]))
          waterParents.set(i, parent);
        const aboard = {
          phase: "aboard",
          index: null,
          facing: "left",
        } as const;
        for (const i of waterParents.keys()) {
          const p = cellPoint(i),
            land = landingPoint(aboard, p.x, p.y, collision);
          if (land) {
            const landIndex =
              Math.floor(land.x / 16) + Math.floor(land.y / 16) * 180;
            roots.add(landIndex);
            shores.push({ water: i, land: landIndex });
          }
        }
        collision.mode = "walk";
        closure("after raft");
      }
    }
  }
  return {
    scope:
      "Fixed low tide. Starts empty at original spawn, retains all raw Decor/Rocks/mobile-object blockers; only tool-permitted static pickups clear Objects. Axe and nested raft ingredients use real recipes/kept tools. No renewal, elapsed travel, food use, pursuit, weather or complete escape simulation. After boarding, combines reachable land components through actual priority landing points; not an ordered boat itinerary.",
    beforeRaft,
    afterRaft: { cells: reachable.size, wreck: check(19), sextant: check(23) },
    raft: {
      repaired,
      boarding,
      waterCells: waterParents.size,
      landings: shores,
    },
    tools,
    crafts,
    pickups,
    inventory,
    shelter,
    events,
  };
}
