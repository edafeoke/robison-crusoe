import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { ExpeditionRunner } from "../tools/lib/expeditionRunner";
import {
  runSextantMilestone,
  milestoneGoals,
} from "../tools/lib/orderedMilestone";
import { validSave } from "../src/game/systems/SaveSystem";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
test(
  "ordered fresh-start expedition earns camp tiers, raft, navigation tools and Sextant with active needs and moving wildlife",
  { timeout: 240000 },
  () => {
    const map = read("public/assets/maps/island.json"),
      items = read("public/assets/objects/objects.json"),
      before = JSON.stringify(map);
    const runner = new ExpeditionRunner(
      map,
      items,
      read("public/assets/animals/wildlife.json"),
    );
    assert.deepEqual(runner.state.inventory, {});
    assert.equal(runner.state.x, 136);
    assert.equal(runner.state.y, 2376);
    assert.equal(runner.state.survival.enabled, true);
    runSextantMilestone(runner);
    const saved = runner.checkpoint(),
      log = runner.log;
    assert.ok(validSave(saved));
    assert.equal(saved.inventory[22], 1);
    for (const id of [5, 16, 23, 76, 77, 78, 89, 92, 96])
      assert.equal(saved.inventory[id], 1);
    assert.equal(saved.shelter, 10);
    assert.equal(saved.raft?.phase, "moored");
    assert.notEqual(saved.raft?.index, 17014, "a real landing moved the dock");
    assert.equal(
      saved.ship?.phase,
      "wreck",
      "this milestone does not claim escape",
    );
    assert.equal(saved.survival?.enabled, true);
    assert.equal(saved.survival?.death, null);
    assert.ok(saved.elapsed > 1800 && saved.elapsed < 3600);
    assert.ok(runner.movedPixels > 50000);
    assert.ok(
      runner.movedPixels <= saved.elapsed * 64 + 0.001,
      "no uncharged travel or teleports",
    );
    assert.deepEqual(
      log.filter((e) => e.kind === "goal").map((e) => e.item),
      milestoneGoals,
    );
    for (const e of log)
      assert.ok((e.reserves as number[]).every((n) => n > 0 && n <= 100));
    assert.ok(
      log.some((e) => e.kind === "contact"),
      "hazards have not been disabled",
    );
    assert.ok(log.some((e) => e.kind === "rest"));
    assert.ok(log.some((e) => e.kind === "world meal"));
    assert.ok(log.some((e) => e.kind === "satchel meal"));
    assert.ok(log.filter((e) => e.kind === "tide").length >= 5);
    const board = log.find((e) => e.kind === "board")!,
      land = log.find((e) => e.kind === "land")!;
    assert.ok(board && land && Number(land.at) > Number(board.at));
    assert.ok(
      !log.some(
        (e) =>
          e.kind === "contact" &&
          Number(e.at) > Number(board.at) &&
          Number(e.at) < Number(land.at),
      ),
      "timed predators remain suspended while paddling",
    );
    for (const id of [104, 109, 112])
      assert.ok(saved.clothing?.owned.includes(id));
    assert.equal(JSON.stringify(map), before);
  },
);
