import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { auditAcquisition } from "../tools/lib/acquisitionAudit";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
import { tidalMap } from "../src/game/systems/TideSystem";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import { canPickup, craftInventory } from "../src/game/systems/ItemRules";
import {
  isShelterUpgrade,
  type Shelter,
} from "../src/game/systems/ShelterSystem";
import {
  initialRaft,
  repairRaft,
  cellPoint,
  boardingPoint,
  raftObjects,
  landingPoint,
} from "../src/game/systems/RaftSystem";
import { initialSurvival } from "../src/game/systems/SurvivalSystem";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const source: IslandMap = read("public/assets/maps/island.json");
const items: RecoveredItem[] = read("public/assets/objects/objects.json");

test("empty-inventory acquisition audit replays exact gated pickups, shelter prerequisites and raft costs without deleting other blockers", () => {
  const before = JSON.stringify(source),
    report = auditAcquisition(source, items),
    map = tidalMap(source, "low"),
    objects = [...map.layers.Objects];
  const collision = new CollisionSystem(map, new Set(), objects);
  let inventory: Record<number, number> = {},
    shelter: Shelter = 17;
  const toolWitnesses = new Map(report.tools.map((t) => [t.index, t]));
  for (const e of report.events) {
    const item = items[e.itemId];
    if (e.kind === "pickup") {
      assert.equal(objects[e.index], item.mapId);
      assert.ok(
        canPickup(item, inventory),
        `${item.name} must have its actual tool`,
      );
      const at = cellPoint(e.stand),
        target = cellPoint(e.index);
      assert.ok(collision.canStand(at.x, at.y));
      assert.ok(Math.hypot(target.x - at.x, target.y - at.y) <= 44);
      const witness = toolWitnesses.get(e.index);
      if (witness) {
        assert.equal(witness.path.at(-1), e.stand);
        if (witness.stage === "foot")
          assert.equal(witness.path[0], 8 + 148 * 180);
        for (let n = 0; n < witness.path.length; n++) {
          const p = cellPoint(witness.path[n]);
          assert.ok(collision.canStand(p.x, p.y));
          if (n) {
            const previous = cellPoint(witness.path[n - 1]);
            assert.equal(
              Math.abs(p.x - previous.x) + Math.abs(p.y - previous.y),
              16,
            );
          }
        }
      }
      inventory[item.id] = (inventory[item.id] ?? 0) + 1;
      objects[e.index] = 0;
    } else if (e.kind === "craft") {
      const result = craftInventory(item, inventory, shelter);
      assert.ok(result, `${item.name} cannot bypass its recipe or camp tier`);
      inventory = result;
      if (isShelterUpgrade(item.id)) shelter = item.id;
    } else {
      const result = repairRaft(
        items,
        inventory,
        initialSurvival(),
        initialRaft(),
      );
      assert.ok(result);
      inventory = result.inventory;
      const moored = new CollisionSystem(
        map,
        new Set(),
        raftObjects(objects, result.raft),
      );
      const point = cellPoint(report.raft.boarding!);
      assert.ok(boardingPoint(result.raft, point.x, point.y, moored));
      objects[17014] = 0;
      for (const shore of report.raft.landings) {
        const p = cellPoint(shore.water);
        collision.mode = "raft";
        assert.ok(collision.canStand(p.x, p.y));
        const land = landingPoint(
          { phase: "aboard", index: null, facing: "left" },
          p.x,
          p.y,
          collision,
        )!;
        assert.deepEqual({ x: land.x, y: land.y }, cellPoint(shore.land));
      }
      collision.mode = "walk";
    }
  }
  assert.equal(shelter, 9);
  assert.deepEqual(inventory, report.inventory);
  assert.equal(report.raft.repaired, true);
  assert.ok(report.raft.waterCells > 2000);
  assert.equal(report.beforeRaft.sextant[0].reachable, false);
  assert.equal(report.beforeRaft.wreck[0].reachable, false);
  assert.equal(report.afterRaft.sextant[0].reachable, true);
  assert.equal(report.afterRaft.wreck[0].reachable, true);
  assert.equal(
    report.tools.find((t) => t.name === "Sextant")?.stage,
    "after raft",
  );
  assert.equal(JSON.stringify(source), before);
});

test("removing the source Knife prevents tool-dependent collection, tent and raft rather than granting missing equipment", () => {
  const map = structuredClone(source);
  map.layers.Objects[11 + 165 * 180] = 0;
  const report = auditAcquisition(map, items);
  assert.equal(report.inventory[76], undefined);
  assert.equal(report.inventory[5], undefined);
  assert.equal(report.shelter, 17);
  assert.equal(report.raft.repaired, false);
  assert.equal(report.afterRaft.sextant[0].reachable, false);
  assert.ok(report.pickups.every((p) => ![6, 11].includes(p.itemId)));
  assert.ok(!report.crafts.includes(9));
});
