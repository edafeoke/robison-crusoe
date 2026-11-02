import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import {
  advanceWorldContact,
  applyWorldContact,
  worldContactCost,
  worldHazardRule,
} from "../src/game/systems/WorldHazardSystem";
import {
  initialSurvival,
  validSurvival,
} from "../src/game/systems/SurvivalSystem";
import { emptyOutfit } from "../src/game/systems/ClothingSystem";
import {
  validWildlife,
  type WildlifeManifest,
} from "../src/game/entities/WildlifeState";
import { WildlifeSystem } from "../src/game/systems/WildlifeSystem";
import type { IslandMap } from "../src/game/maps/MapDecoder";

const bare = emptyOutfit();
const clothed = { hat: 112, shirt: 99, pants: 104, shoes: 109 };
const read = (path: string) => JSON.parse(readFileSync(path, "utf8"));
const original: IslandMap = read("public/assets/maps/island.json");
const manifest: WildlifeManifest = read("public/assets/animals/wildlife.json");
const rule = (id: number) => worldHazardRule(id)!;
function world(id = 50) {
  const map = structuredClone(original);
  for (const key of ["FULL", "Objects", "Decor", "Rocks", "Borders"] as const)
    map.layers[key].fill(key === "FULL" ? (id === 56 ? 5 : 1) : 0);
  map.layers.Objects[100 + 100 * 180] = id;
  const system = new WildlifeSystem(map, manifest);
  const actor = system.actors[0];
  return { system, actor, player: { x: actor.x + 16, y: actor.y } };
}

test("world contact rules independently retain all four d protection checks and i's immediate loss", () => {
  assert.deepEqual(
    [43, 44, 49, 50].map((id) => [
      worldContactCost(rule(id), bare),
      worldContactCost(rule(id), clothed),
    ]),
    [
      [50, 25],
      [50, 25],
      [10, 5],
      [20, 10],
    ],
  );
  assert.equal(worldContactCost(rule(43), { ...bare, shirt: 99 }), 50);
  assert.equal(worldContactCost(rule(43), { ...bare, pants: 99 }), 50);
  assert.equal(worldContactCost(rule(56), clothed), 100);
  for (const id of [5, 42, 48, 53, 55])
    assert.equal(worldHazardRule(id), undefined);
  assert.deepEqual(
    read("evidence/world-hazard-rules.json").contacts.map(
      (r: { mapId: number }) => r.mapId,
    ),
    [43, 44, 49, 50, 56],
  );
});

test("Chebyshev tile proximity includes diagonals, rejects a second tile, and requires strictly more than 1000 ms", () => {
  const actor = { mapId: 49, x: 1608, y: 1608, contactMs: 0 };
  const diagonal = { x: 1624, y: 1624 };
  assert.equal(advanceWorldContact(actor, diagonal, 1000, false), null);
  assert.equal(actor.contactMs, 1000);
  assert.equal(advanceWorldContact(actor, diagonal, 1, false)?.mapId, 49);
  assert.equal(actor.contactMs, 0);
  advanceWorldContact(actor, diagonal, 900, false);
  assert.equal(
    advanceWorldContact(actor, { x: 1640, y: 1608 }, 90, false),
    null,
  );
  assert.equal(actor.contactMs, 0, "leaving range discards exposure");
  assert.equal(advanceWorldContact(actor, diagonal, 100, false), null);
  advanceWorldContact(actor, diagonal, 900, true);
  assert.equal(
    actor.contactMs,
    100,
    "source sailing branch retains but does not advance d's timer",
  );
});

test("contacts run after active wildlife ticks, freeze outside the camera and persist partial exposure across saves", () => {
  const { system, actor, player } = world();
  let hits = 0;
  const afterTick = (a: typeof actor) => {
    if (advanceWorldContact(a, player, 90, false)) hits++;
  };
  system.update(89, player, false, undefined, afterTick);
  assert.equal(actor.contactMs, 0);
  system.update(1, player, false, undefined, afterTick);
  for (let n = 0; n < 9; n++)
    system.update(90, player, false, undefined, afterTick);
  assert.equal(actor.contactMs, 900);
  system.update(
    180,
    player,
    false,
    { x: 0, y: 0, width: 16, height: 16 },
    afterTick,
  );
  assert.equal(actor.contactMs, 900);
  assert.equal(hits, 0);
  const snapshot = system.snapshot();
  const restored = new WildlifeSystem(
    system.map,
    manifest,
    new Set(),
    snapshot,
  );
  restored.update(180, player, false, undefined, afterTick);
  assert.equal(hits, 1);
  assert.equal(restored.actors[0].contactMs, 0);
  assert.equal(actor.contactMs, 900, "restored state is independent");
  assert.equal(
    system.map.layers.Objects[actor.id],
    50,
    "original map is intact",
  );
  assert.ok(restored.removeActor(actor.id));
  restored.update(180, player, false, undefined, afterTick);
  assert.equal(hits, 1, "hunted animals cannot attack later");
});

test("each predator gets its own repeating 1080 ms contact cycle at the source 90 ms cadence", () => {
  for (const id of [43, 44, 49, 50]) {
    const { system, actor, player } = world(id);
    let survival = initialSurvival(),
      hits = 0;
    for (let n = 0; n < 24; n++)
      system.update(90, player, false, undefined, (a) => {
        const contact = advanceWorldContact(a, player, 90, false);
        if (contact) {
          hits++;
          survival = applyWorldContact(survival, contact, clothed).survival;
        }
        if (n === 10) assert.equal(hits, 0);
      });
    assert.equal(hits, 2);
    assert.equal(
      survival.energy,
      100 - 2 * worldContactCost(rule(id), clothed),
    );
    assert.equal(actor.contactMs, 0);
  }
});

test("attacks interrupt rest once, with the source proportional whole-point credit after damage", () => {
  const life = { ...initialSurvival(), energy: 60, restRemainingMs: 8920 };
  const hit = applyWorldContact(life, rule(50), bare);
  assert.equal(hit.restCredit, 3);
  assert.equal(hit.survival.energy, 43);
  assert.equal(hit.survival.restRemainingMs, null);
  assert.equal(hit.interrupted, true);
  const next = applyWorldContact(hit.survival, rule(50), bare);
  assert.equal(next.restCredit, 0);
  assert.equal(next.survival.energy, 23);
  assert.equal(life.energy, 60, "no input state mutation");
  const protectedHit = applyWorldContact(life, rule(50), clothed);
  assert.equal(protectedHit.survival.energy, 53);
});

test("source unprotected pre-rest clamp is preserved; protected negative reserve is safely settled", () => {
  const life = { ...initialSurvival(), energy: 5, restRemainingMs: 8920 };
  assert.equal(applyWorldContact(life, rule(50), bare).survival.energy, 3);
  const protectedHit = applyWorldContact(life, rule(50), clothed).survival;
  assert.equal(protectedHit.energy, 0);
  assert.equal(protectedHit.death, "animal");
  assert.equal(protectedHit.restRemainingMs, null);
  assert.ok(validSurvival(protectedHit));
  assert.deepEqual(
    applyWorldContact(protectedHit, rule(50), bare).survival,
    protectedHit,
  );
});

test("sharks cause immediate exhaustion, even while sailing/resting or wearing all clothing", () => {
  const { system, player } = world(56);
  let survival = {
    ...initialSurvival(),
    restRemainingMs: 5000 as number | null,
  };
  system.update(90, player, true, undefined, (actor) => {
    const contact = advanceWorldContact(actor, player, 90, true);
    assert.equal(contact?.kind, "instant");
    const result = applyWorldContact(survival, contact!, clothed);
    assert.equal(result.restCredit, 0);
    survival = result.survival;
  });
  assert.equal(survival.energy, 0);
  assert.equal(survival.death, "animal");
  assert.equal(survival.restRemainingMs, null);
  assert.ok(validSurvival(survival));
  const exploration = initialSurvival(false);
  assert.deepEqual(
    applyWorldContact(exploration, rule(56), clothed).survival,
    exploration,
  );
});

test("boarding preserves predator exposure and rest while wildlife ticks; landing resumes pursuit and damage", () => {
  for (const id of [43, 44, 49, 50]) {
    const { system, actor, player } = world(id);
    actor.phase = "rest";
    actor.duration = 5000;
    actor.contactMs = 900;
    const before = { x: actor.x, y: actor.y, frame: actor.frame };
    let survival = initialSurvival();
    const tick = (sailing: boolean) =>
      system.update(90, player, sailing, undefined, (a) => {
        const contact = advanceWorldContact(a, player, 90, sailing);
        if (contact)
          survival = applyWorldContact(survival, contact, bare).survival;
      });
    for (let n = 0; n < 19; n++) tick(true);
    assert.equal(survival.energy, 100);
    assert.equal(
      actor.contactMs,
      900,
      "boarding neither resets nor advances exposure",
    );
    assert.equal(
      actor.phase,
      "rest",
      "a passenger does not wake a resting predator",
    );
    assert.equal(actor.reaction, null);
    assert.equal(actor.x, before.x);
    assert.equal(actor.y, before.y);
    assert.equal(actor.timer, 1710, "wildlife has not been paused");
    assert.notEqual(actor.frame, before.frame);
    tick(false);
    assert.equal(actor.reaction, "chase");
    assert.equal(actor.phase, "walk");
    assert.equal(actor.contactMs, 990);
    assert.equal(survival.energy, 100);
    tick(false);
    assert.equal(actor.contactMs, 0);
    assert.equal(survival.energy, 100 - rule(id).energy);
  }
});

test("aboard predators keep their existing displacement and pursuit speed without retargeting the passenger", () => {
  const { system, actor } = world(43);
  system.update(90, { x: actor.x - 32, y: actor.y }, false);
  assert.equal(actor.reaction, "chase");
  const before = { x: actor.x, remaining: actor.remainingX };
  // Moving the passenger to the opposite side must not reverse an existing chase.
  system.update(90, { x: actor.x + 32, y: actor.y }, true);
  assert.equal(actor.x, before.x - 2);
  assert.equal(actor.remainingX, before.remaining + 2);
  assert.equal(actor.reaction, "chase");
  system.update(90, { x: actor.x + 32, y: actor.y }, false);
  assert.ok(actor.remainingX > 0, "landing permits a new target");
});

test("sailing does not suppress the distinct prey and bird reaction families", () => {
  for (const id of [5, 53]) {
    const { system, actor } = world(id);
    actor.phase = "rest";
    actor.duration = 5000;
    system.update(90, { x: actor.x - 32, y: actor.y }, true);
    assert.equal(actor.reaction, "flee");
    assert.equal(actor.phase, id === 53 ? "fly" : "walk");
    assert.ok(actor.x > 1608);
  }
});

test("an afloat save retains partial predator exposure without granting immunity after landing", () => {
  const { system, actor, player } = world();
  actor.contactMs = 990;
  const restored = new WildlifeSystem(
    system.map,
    manifest,
    new Set(),
    system.snapshot(),
  );
  const saved = restored.actors[0];
  restored.update(90, player, true, undefined, (a) => {
    assert.equal(advanceWorldContact(a, player, 90, true), null);
  });
  assert.equal(saved.contactMs, 990);
  restored.update(90, player, false, undefined, (a) => {
    assert.equal(advanceWorldContact(a, player, 90, false)?.mapId, 50);
  });
  assert.equal(saved.contactMs, 0);
  assert.equal(actor.contactMs, 990, "original snapshot remains independent");
});

test("afloat saves continue the last predator speed; legacy reactions default safely and invalid reactions are rejected", () => {
  const { system, actor } = world(43);
  system.update(90, { x: actor.x - 32, y: actor.y }, false);
  const snapshot = system.snapshot();
  assert.equal(snapshot[0].reaction, "chase");
  const restored = new WildlifeSystem(
    system.map,
    manifest,
    new Set(),
    snapshot,
  );
  const player = { x: actor.x + 32, y: actor.y };
  for (let n = 0; n < 10; n++) {
    system.update(90, player, true);
    restored.update(90, player, true);
  }
  assert.deepEqual(restored.snapshot(), system.snapshot());
  const legacy = snapshot.map(({ reaction: _reaction, ...s }) => s);
  assert.ok(validWildlife(legacy));
  assert.equal(
    new WildlifeSystem(system.map, manifest, new Set(), legacy).actors[0]
      .reaction,
    null,
  );
  for (const reaction of [true, 1, "attack", {}])
    assert.equal(validWildlife([{ ...snapshot[0], reaction }]), false);
});

test("old wildlife snapshots migrate exposure to zero, while invalid contact timers cannot enter a save", () => {
  const { system } = world();
  const old = system.snapshot().map(({ contactMs: _contactMs, ...a }) => a);
  assert.ok(validWildlife(old));
  const restored = new WildlifeSystem(system.map, manifest, new Set(), old);
  assert.equal(restored.actors[0].contactMs, 0);
  for (const contactMs of [-1, 1001, NaN, 0.1, "90"])
    assert.ok(!validWildlife([{ ...old[0], contactMs }]));
});
