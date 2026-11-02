import { test } from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import type { IslandMap } from "../src/game/maps/MapDecoder";
import { CollisionSystem } from "../src/game/systems/CollisionSystem";
import {
  findPath,
  moveWithCollision,
  type Point,
} from "../src/game/systems/MovementSystem";
const original: IslandMap = JSON.parse(
  readFileSync("public/assets/maps/island.json", "utf8"),
);

test("click paths recenter an off-centre foot box before a turn beside a moving bird on the original grid", () => {
  const objects = [...original.layers.Objects],
    blocked = 44 + 117 * 180;
  objects[blocked] = 64; // A live Red ibis can occupy this neighboring tile.
  const c = new CollisionSystem(original, new Set(), objects);
  const start = { x: 43 * 16 + 14.4, y: 117 * 16 + 8 },
    center = { x: 43 * 16 + 8, y: start.y },
    goal = { x: center.x, y: 116 * 16 + 8 };
  assert.equal(
    c.canStand(start.x, start.y),
    false,
    "bird has entered under the edge of the foot box",
  );
  assert.deepEqual(
    moveWithCollision(c, start, goal.x - start.x, goal.y - start.y, 0.05),
    start,
    "aiming directly into the next turn cannot clear the edge",
  );
  const path = findPath(c, start, goal);
  assert.deepEqual(path, [center, goal]);
  let p: Point = start;
  for (let n = 0; path.length && n < 50; n++) {
    const target = path[0];
    if (Math.hypot(p.x - target.x, p.y - target.y) < 2) {
      p = target;
      path.shift();
    } else p = moveWithCollision(c, p, target.x - p.x, target.y - p.y, 0.05);
    assert.ok(c.canStand(p.x, p.y));
    assert.notEqual(Math.floor(p.x / 16) + Math.floor(p.y / 16) * 180, blocked);
  }
  assert.deepEqual(p, goal);
  assert.equal(path.length, 0);
  assert.equal(
    objects[blocked],
    64,
    "no actor or blocker is removed to recover",
  );
  assert.equal(
    original.layers.Objects[blocked],
    0,
    "the original map is untouched",
  );
});

test("same-tile clicks can recenter while centred paths retain cardinal neighbors and blocked destinations fail", () => {
  const c = new CollisionSystem(original),
    center = { x: 136, y: 2376 };
  assert.deepEqual(findPath(c, { x: 140, y: 2376 }, center), [center]);
  assert.deepEqual(findPath(c, center, center), []);
  assert.deepEqual(findPath(c, { x: 140, y: 2376 }, { x: -16, y: -16 }), []);
  const path = findPath(c, center, { x: 152, y: 2376 });
  assert.deepEqual(path, [{ x: 152, y: 2376 }]);
});

test("shared movement retains normalized speed, 50ms cap, independent axis collision and raft terrain restrictions", () => {
  const map = structuredClone(original);
  for (const key of ["FULL", "Objects", "Decor", "Rocks", "Borders"] as const)
    map.layers[key].fill(key === "FULL" ? 1 : 0);
  const objects = Array(32400).fill(0),
    c = new CollisionSystem(map, new Set(), objects),
    p = { x: 1608, y: 1608 };
  assert.deepEqual(
    Object.keys(
      moveWithCollision(
        c,
        { ...p, survival: "must stay out of movement results" } as Point,
        0,
        0,
        0.05,
      ),
    ).sort(),
    ["x", "y"],
  );
  const diagonal = moveWithCollision(c, p, 1, 1, 10);
  assert.ok(
    Math.abs(Math.hypot(diagonal.x - p.x, diagonal.y - p.y) - 3.2) < 1e-10,
  );
  objects[101 + 100 * 180] = 4;
  const edge = { x: 1612.9, y: 1608 },
    slide = moveWithCollision(c, edge, 1, 1, 0.05);
  assert.equal(slide.x, edge.x);
  assert.ok(slide.y > edge.y);
  c.mode = "raft";
  assert.deepEqual(moveWithCollision(c, p, 1, 0, 0.05), p);
  map.layers.FULL[100 + 100 * 180] = 14;
  assert.equal(moveWithCollision(c, p, 1, 0, 0.05).x, 1611.2);
  for (const seconds of [0, -1, NaN, Infinity])
    assert.deepEqual(moveWithCollision(c, p, 1, 0, seconds), p);
});
