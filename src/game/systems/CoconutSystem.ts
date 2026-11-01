import { coconutRules as R } from "../data/coconutRules";
export interface CoconutTarget {
  x: number;
  y: number;
  phase: 0 | 1 | 2 | 3;
  shakeMs: number;
  hitShot: number;
}
export interface CoconutLeaf {
  x: number;
  y: number;
  sprite: number;
}
export interface CoconutShot {
  x: number;
  y: number;
  elapsed: number;
  curve: number;
  denominator: number;
  rise: number;
  cosine: number;
  power: number;
  duration: number;
}
export interface CoconutGame {
  palmIndex: number;
  seed: number;
  angle: number;
  power: number;
  throws: number;
  knocked: number;
  status: "playing" | "won" | "lost";
  targets: CoconutTarget[];
  leaves: CoconutLeaf[];
  shot: CoconutShot | null;
  accumulator: number;
  tick: number;
}
const div = (a: number, b: number) => Math.trunc(a / b);
export const ground = R.height - 50;
export const throwRadius = Math.ceil(
  Math.hypot(ground, Math.min(R.width - 10, ground)),
);
function random(state: { seed: number }, limit: number) {
  let n = state.seed;
  n ^= n << 13;
  n ^= n >>> 17;
  n ^= n << 5;
  state.seed = n >>> 0 || 1;
  return state.seed % limit;
}
export function createCoconutGame(
  palmIndex: number,
  seed: number,
): CoconutGame {
  const s: CoconutGame = {
    palmIndex,
    seed: seed >>> 0 || 1,
    angle: 70,
    power: 40,
    throws: 0,
    knocked: 0,
    status: "playing",
    targets: [],
    leaves: [],
    shot: null,
    accumulator: 0,
    tick: 0,
  };
  const cols = div(R.width - 15, 14),
    rows = div(div(R.height, 3) - 30, 5),
    capacity = cols * rows,
    count = Math.min(capacity, 9 + random(s, div(R.width, 12))),
    occupied = new Set<number>();
  for (let i = 0; i < count; i++) {
    let cell = random(s, capacity);
    while (occupied.has(cell)) cell = random(s, capacity);
    occupied.add(cell);
    s.leaves.push({
      x: 15 + 14 * (cell % cols),
      y: 30 + 5 * div(cell, cols),
      sprite: R.sprites.leaves[random(s, 3)],
    });
  }
  // Source can request 7 unique targets from only 6 eligible leaves: bound this rejection loop.
  const targetCount = Math.min(5 + random(s, 3), count - 3),
    eligible = new Set<number>();
  for (let i = 0; i < targetCount; i++) {
    let index = 3 + random(s, count - 3);
    while (eligible.has(index)) index = 3 + random(s, count - 3);
    eligible.add(index);
    const leaf = s.leaves[index];
    s.targets.push({ x: leaf.x, y: leaf.y, phase: 0, shakeMs: 0, hitShot: 0 });
  }
  return s;
}
export function aimCoconut(
  s: CoconutGame,
  angle: number,
  power: number,
): CoconutGame {
  if (
    s.shot ||
    s.status !== "playing" ||
    !Number.isFinite(angle) ||
    !Number.isFinite(power)
  )
    return s;
  return {
    ...s,
    angle: Math.max(10, Math.min(70, Math.round(angle / 5) * 5)),
    power: Math.max(40, Math.min(100, Math.round(power / 2) * 2)),
  };
}
export function throwCoconutStone(s: CoconutGame): CoconutGame {
  if (s.status !== "playing" || s.shot || s.throws >= R.maxThrows) return s;
  const index = div(s.angle + 15, 5) - 3,
    sin = R.sine[index],
    cosine = R.cosine[index];
  // Preserve Java's intermediate integer divisions, not a substituted physics formula.
  const duration =
    div(div(div(3000, R.width) * cosine, 100) * s.power * throwRadius, 100) +
    div(div(div(2000, ground) * sin, 100) * s.power * throwRadius, 100);
  return {
    ...s,
    throws: s.throws + 1,
    shot: {
      x: 10,
      y: ground,
      elapsed: 0,
      curve: div(sin * 10000, cosine * cosine),
      denominator: s.power * throwRadius,
      rise: 2 * sin,
      cosine,
      power: s.power,
      duration,
    },
  };
}
export function stepCoconutGame(
  state: CoconutGame,
  delta: number,
): CoconutGame {
  if (state.status !== "playing" || !Number.isFinite(delta) || delta <= 0)
    return state;
  const s = {
    ...state,
    targets: state.targets.map((t) => ({ ...t })),
    shot: state.shot ? { ...state.shot } : null,
  };
  s.accumulator += Math.min(delta, 180);
  while (s.accumulator >= R.tickMs && s.status === "playing") {
    s.accumulator -= R.tickMs;
    s.tick++;
    for (const t of s.targets) {
      if (t.phase === 1) {
        if (t.shakeMs > R.shakeMs) {
          t.phase = 0;
          t.shakeMs = 0;
        } else t.shakeMs += R.tickMs;
      } else if (t.phase === 2) {
        if (t.y < ground) t.y += R.fallStep;
        else {
          t.y = ground;
          t.phase = 3;
          if (s.knocked >= R.target) s.status = "won";
        }
      }
    }
    if (s.status !== "playing" || !s.shot) continue;
    const p = s.shot,
      dx = p.x - 10;
    p.elapsed += R.tickMs;
    p.y =
      ground -
      (-div(p.curve * dx * dx, p.denominator) + div(p.rise * dx, p.cosine));
    p.x =
      10 +
      div(
        div(div(p.elapsed * 2 * p.cosine, 100) * p.power * throwRadius, 100),
        p.duration,
      );
    if (p.y > ground) {
      s.shot = null;
      // n.h resets both controls after a throw.
      s.angle = 70;
      s.power = 40;
      if (s.throws === R.maxThrows && s.knocked < R.target) s.status = "lost";
      continue;
    }
    for (const t of s.targets) {
      if (t.hitShot === s.throws || t.phase >= 2) continue;
      if (
        t.x - 7 < p.x + 4 &&
        t.x + 7 > p.x - 4 &&
        t.y - 5 < p.y + 4 &&
        t.y + 5 > p.y - 4
      ) {
        t.hitShot = s.throws;
        if (t.phase === 0) {
          t.phase = 1;
          t.shakeMs = 0;
        } else {
          t.phase = 2;
          s.knocked++;
        }
      }
    }
  }
  return s;
}
/** n.a has separate success/failure reset values after consuming a stone; t serializes this counter. */
export function coconutOutcome(
  inventory: Readonly<Record<number, number>>,
  uses: number,
  won: boolean,
) {
  if (
    !Number.isInteger(uses) ||
    uses < 0 ||
    uses > 5 ||
    (inventory[3] ?? 0) < 1
  )
    return null;
  const next = { ...inventory },
    stoneSpent = uses >= 5;
  if (won) next[1] = (next[1] ?? 0) + 1;
  if (stoneSpent) next[3]--;
  return {
    inventory: next,
    uses: stoneSpent ? (won ? 1 : 0) : uses + 1,
    stoneSpent,
  };
}
