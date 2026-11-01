import { netFishingRules as R } from "../data/netFishingRules";
export interface NetFish {
  x: number;
  y: number;
  speed: number;
  profile: number;
  direction: 1 | -1;
  phase: 0 | 1 | 2;
}
export interface NetFishingGame {
  shoreIndex: number;
  seed: number;
  casts: number;
  caught: number;
  lastCatch: number;
  phase: "ready" | "casting" | "haul";
  status: "playing" | "won" | "lost";
  netY: number;
  haulMs: number;
  accumulator: number;
  frame: number;
  fish: NetFish[];
  reward: number | null;
}
const half = R.height >> 1;
export const netStart = -half + 35 - (R.netHeight >> 1);
export const netBottom = half - 45;
function random(s: NetFishingGame, limit: number) {
  let n = s.seed;
  n ^= n << 13;
  n ^= n >>> 17;
  n ^= n << 5;
  s.seed = n >>> 0 || 1;
  return s.seed % limit;
}
function spawn(s: NetFishingGame): NetFish {
  const side = random(s, 2),
    x = side === 0 ? -random(s, 60) : R.width + random(s, 60),
    y = 42 + random(s, R.height - 80 - 14),
    speed = 2 + random(s, 3),
    roll = random(s, 100);
  return {
    x,
    y,
    speed: side === 0 ? speed : -speed,
    profile: R.fish.findIndex((p) => roll < p.below),
    direction: side === 0 ? 1 : -1,
    phase: 0,
  };
}
function wave(s: NetFishingGame) {
  s.fish = Array.from({ length: 3 + random(s, 5) }, () => spawn(s));
}
export function createNetFishingGame(
  shoreIndex: number,
  seed: number,
): NetFishingGame {
  const s: NetFishingGame = {
    shoreIndex,
    seed: seed >>> 0 || 1,
    casts: 0,
    caught: 0,
    lastCatch: 0,
    phase: "ready",
    status: "playing",
    netY: netStart,
    haulMs: 0,
    accumulator: 0,
    frame: 0,
    fish: [],
    reward: null,
  };
  wave(s);
  return s;
}
export function castNet(s: NetFishingGame): NetFishingGame {
  return s.status === "playing" && s.phase === "ready" && s.casts < R.maxCasts
    ? { ...s, casts: s.casts + 1, phase: "casting" }
    : s;
}
export function stepNetFishing(
  state: NetFishingGame,
  delta: number,
): NetFishingGame {
  if (state.status !== "playing" || !Number.isFinite(delta) || delta <= 0)
    return state;
  const s = { ...state, fish: state.fish.map((f) => ({ ...f })) };
  s.accumulator += Math.min(delta, 180);
  while (s.accumulator >= R.tickMs && s.status === "playing") {
    s.accumulator -= R.tickMs;
    s.frame = (s.frame + 1) % 3;
    // x.d calls l (fish movement), k (net/haul), then m (strict center containment).
    for (let i = 0; i < s.fish.length; i++) {
      const f = s.fish[i];
      if (f.phase === 2) continue;
      if (s.phase === "haul") {
        if (f.phase === 0) {
          f.direction = f.x > R.width / 2 ? 1 : -1;
          f.speed = 10 * f.direction;
          f.x += f.speed;
        }
      } else if (f.phase === 0) {
        if (
          f.speed > 0
            ? f.x + 8 + f.speed >= R.width + 70
            : f.x - 8 + f.speed <= -70
        )
          s.fish[i] = spawn(s);
        else f.x += f.speed;
      } else if (s.phase === "casting") f.y += R.netStep;
    }
    if (s.phase === "casting") {
      if (s.netY + (R.netHeight >> 1) >= netBottom) {
        s.lastCatch = 0;
        for (const f of s.fish)
          if (f.phase === 1) {
            f.phase = 2;
            s.lastCatch++;
          }
        s.caught += s.lastCatch;
        s.phase = "haul";
        s.haulMs = 0;
      } else s.netY += R.netStep;
    } else if (s.phase === "haul") {
      s.haulMs += R.tickMs;
      if (s.haulMs > R.haulMs) {
        s.netY = netStart;
        s.phase = "ready";
        if (s.caught >= R.target) {
          s.status = "won";
          s.reward = R.rewardItems[random(s, 3)];
        } else if (s.casts >= R.maxCasts) s.status = "lost";
        else wave(s);
      }
    }
    for (const f of s.fish)
      if (
        f.phase === 0 &&
        f.x > R.width / 2 - R.netWidth / 2 &&
        f.x < R.width / 2 + R.netWidth / 2 &&
        f.y > half + s.netY - R.netHeight / 2 &&
        f.y < half + s.netY + R.netHeight / 2
      )
        f.phase = 1;
  }
  return s;
}
export function netFishingOutcome(
  inventory: Readonly<Record<number, number>>,
  game: NetFishingGame,
) {
  if (
    game.status === "playing" ||
    (inventory[R.tool] ?? 0) < 1 ||
    (inventory[R.bait] ?? 0) < 1 ||
    (game.status === "won" &&
      !(R.rewardItems as readonly number[]).includes(game.reward ?? -1))
  )
    return null;
  const next: Record<number, number> = {
    ...inventory,
    [R.bait]: inventory[R.bait] - 1,
  };
  if (game.status === "won") next[game.reward!] = (next[game.reward!] ?? 0) + 1;
  return next;
}
/** data.a.g-j: FULL terrain 4 opens c(59), whose hunting tool is Net item 83. */
export function validNetShore(
  terrain: readonly number[] | null,
  index: number,
  x: number,
  y: number,
) {
  return (
    !!terrain &&
    Number.isInteger(index) &&
    index >= 0 &&
    index < 32400 &&
    terrain[index] === R.terrain &&
    Math.hypot(
      (index % 180) * 16 + 8 - x,
      Math.floor(index / 180) * 16 + 8 - y,
    ) <= 44
  );
}
