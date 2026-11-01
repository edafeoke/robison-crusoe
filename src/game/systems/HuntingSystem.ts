import { huntingRules as R } from "../data/huntingRules";
import { coconutRules as T } from "../data/coconutRules";
import { emptyOutfit, huntingDamage, type Outfit } from "./ClothingSystem";
export interface HuntSource {
  id: number;
  itemId: number;
}
export interface HuntTarget extends HuntSource {
  x: number;
  y: number;
  dx: number;
  dy: number;
  movingX: boolean;
  movingY: boolean;
  resting: boolean;
  enabled: boolean;
  facing: 1 | -1;
  attackX: number;
  attackY: number;
}
export interface HuntShot {
  x: number;
  y: number;
  elapsed: number;
  curve: number;
  denominator: number;
  rise: number;
  cosine: number;
  power: number;
  duration: number;
  apexX: number;
  phase: 1 | 2 | 3;
  frame: number;
  offsetX: number;
  offsetY: number;
}
export interface HuntingGame {
  outfit: Outfit;
  seed: number;
  angle: number;
  power: number;
  shots: HuntShot[];
  targets: HuntTarget[];
  status: "playing" | "won" | "lost";
  phase: "aiming" | "flight" | "attacking";
  winner: number | null;
  attackMs: number;
  attackEnergy: number;
  initialRest: boolean;
  restMs: number;
  restDuration: number;
  frameMs: number;
  accumulator: number;
  tick: number;
}
const div = (a: number, b: number) => Math.trunc(a / b);
export const huntGround = R.height - 50,
  huntRadius = Math.ceil(
    Math.hypot(huntGround, Math.min(R.width - 10, huntGround)),
  );
export const huntingProfile = (id: number) =>
  R.profiles.find((p) => p.item === id);
function random(s: HuntingGame, limit: number) {
  let n = s.seed;
  n ^= n << 13;
  n ^= n >>> 17;
  n ^= n << 5;
  s.seed = n >>> 0 || 1;
  return s.seed % limit;
}
export function createHuntingGame(
  sources: readonly HuntSource[],
  seed: number,
  outfit: Outfit = emptyOutfit(),
): HuntingGame {
  if (
    !sources.length ||
    sources.length > 5 ||
    sources.some(
      (p) =>
        !Number.isInteger(p.id) ||
        p.id < 0 ||
        p.id >= 32400 ||
        !huntingProfile(p.itemId),
    ) ||
    new Set(sources.map((p) => p.id)).size !== sources.length
  )
    throw new Error("Unsupported hunting targets");
  const s: HuntingGame = {
    outfit: { ...outfit },
    seed: seed >>> 0 || 1,
    angle: 70,
    power: 40,
    shots: [],
    targets: [],
    status: "playing",
    phase: "aiming",
    winner: null,
    attackMs: 0,
    attackEnergy: 0,
    initialRest: true,
    restMs: 0,
    restDuration: 0,
    frameMs: 0,
    accumulator: 0,
    tick: 0,
  };
  s.targets = sources.map((p) => ({
    ...p,
    x: (R.width >> 1) + random(s, (R.width >> 1) - 24),
    y: 40 + random(s, div(R.height, 3) - 40),
    dx: 0,
    dy: 0,
    movingX: false,
    movingY: false,
    resting: true,
    enabled: true,
    facing: 1,
    attackX: 0,
    attackY: 0,
  }));
  return s;
}
export function aimHunt(
  s: HuntingGame,
  angle: number,
  power: number,
): HuntingGame {
  if (
    s.status !== "playing" ||
    s.phase !== "aiming" ||
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
export function fireHunt(s: HuntingGame): HuntingGame {
  if (
    s.status !== "playing" ||
    s.phase !== "aiming" ||
    s.shots.length >= R.maxShots
  )
    return s;
  const i = div(s.angle + 15, 5) - 3,
    sin = T.sine[i],
    cos = T.cosine[i],
    duration =
      div(div(div(3000, R.width) * cos, 100) * s.power * huntRadius, 100) +
      div(div(div(2000, huntGround) * sin, 100) * s.power * huntRadius, 100);
  const shot: HuntShot = {
    x: 10,
    y: huntGround,
    elapsed: 0,
    curve: div(sin * 10000, cos * cos),
    denominator: s.power * huntRadius,
    rise: 2 * sin,
    cosine: cos,
    power: s.power,
    duration,
    apexX: div(cos * s.power * huntRadius, 10000) + 10,
    phase: 1,
    offsetX: 0,
    offsetY: 0,
    frame: s.angle < 25 ? 3 : s.angle < 35 ? 2 : s.angle < 65 ? 1 : 0,
  };
  return { ...s, phase: "flight", shots: [...s.shots, shot] };
}
function wander(s: HuntingGame, t: HuntTarget, first = false) {
  const vertical = div(R.height, 3) - 30;
  t.dy = random(s, 2 * vertical) - vertical;
  t.dx = first
    ? random(s, div(4 * R.width, 3)) - div(2 * R.width, 3)
    : random(s, div(4 * R.width, 3) - 40) - (div(2 * R.width, 3) - 20);
  t.movingX = t.movingY = true;
}
function moveTargets(s: HuntingGame) {
  for (const t of s.targets) {
    const p = huntingProfile(t.itemId)!;
    if (s.phase === "attacking" && p.dangerous) {
      t.x = t.attackX + div(t.dx * s.attackMs, R.attackMs);
      t.y = t.attackY + div(t.dy * s.attackMs, R.attackMs);
      t.facing = t.dx > 0 ? 1 : -1;
      continue;
    }
    if (t.enabled && t.movingY) {
      if (t.dy >= 0) {
        t.y += p.speedY;
        t.dy -= p.speedY;
        if (t.dy < 0 || t.y > div(R.height, 3)) t.movingY = false;
        if (t.y > div(R.height, 3)) t.y = div(R.height, 3);
      } else {
        t.y -= p.speedY;
        t.dy += p.speedY;
        if (t.dy >= 0 || t.y <= 45) t.movingY = false;
        if (t.y <= 45) t.y = 45;
      }
    }
    if (!t.movingX && !t.movingY && !t.resting) {
      if (random(s, s.shots.length * 4) === 0) {
        t.resting = true;
        s.restDuration = (20 + random(s, 30)) * 100;
        s.restMs = 0;
      } else wander(s, t);
    }
    if (t.resting) {
      if (!s.initialRest && s.restMs > s.restDuration) t.resting = false;
    } else if (t.dx >= 0) {
      if (t.enabled && t.movingX) {
        t.x += p.speedX;
        t.dx -= p.speedX;
        if (t.dx < 0 || t.x >= R.width - 24) t.movingX = false;
        if (t.x >= R.width - 24) t.x = R.width - 24;
      }
      t.facing = 1;
    } else {
      if (t.enabled && t.movingX) {
        t.x -= p.speedX;
        t.dx += p.speedX;
        if (t.dx >= 0 || t.x < 24) t.movingX = false;
        if (t.x < 24) t.x = 24;
      }
      t.facing = -1;
    }
  }
}
export function stepHunting(state: HuntingGame, delta: number): HuntingGame {
  if (state.status !== "playing" || !Number.isFinite(delta) || delta <= 0)
    return state;
  const s = {
    ...state,
    targets: state.targets.map((t) => ({ ...t })),
    shots: state.shots.map((p) => ({ ...p })),
  };
  s.accumulator += Math.min(delta, 180);
  while (s.accumulator >= R.tickMs && s.status === "playing") {
    s.accumulator -= R.tickMs;
    s.tick++;
    s.frameMs = (s.frameMs + R.tickMs) % 1200;
    s.restMs += R.tickMs;
    if (s.phase === "attacking")
      s.attackMs = Math.min(R.attackMs, s.attackMs + R.tickMs);
    moveTargets(s);
    if (s.phase === "attacking") {
      if (s.attackMs >= R.attackMs) {
        s.attackEnergy = s.targets.reduce(
          (sum, t) => sum + huntingDamage(t.itemId, s.outfit),
          0,
        );
        s.status = "lost";
      }
      continue;
    }
    const p = s.shots.at(-1);
    if (s.phase !== "flight" || !p) continue;
    p.offsetX = p.offsetY = 0;
    if (p.x < p.apexX) {
      p.elapsed += R.tickMs;
      const dx = p.x - 10;
      p.y =
        huntGround -
        (-div(p.curve * dx * dx, p.denominator) + div(p.rise * dx, p.cosine));
      p.x =
        10 +
        div(
          div(div(p.elapsed * 2 * p.cosine, 100) * p.power * huntRadius, 100),
          p.duration,
        );
      const unit = s.angle > 60 ? 1 : 2,
        distance = p.apexX - p.x;
      if (distance <= unit && p.frame < 4) {
        p.frame = 4;
        p.offsetX = -4;
      } else if (distance <= 2 * unit && p.frame < 3) {
        p.frame = 3;
        p.offsetX = -3;
      } else if (distance <= 3 * unit && p.frame < 2) {
        p.frame = 2;
        p.offsetY = -4;
      } else if (distance <= 4 * unit && p.frame < 1) {
        p.frame = 1;
        p.offsetY = -4;
      }
    } else p.phase = 2;
    if (p.phase !== 2) continue;
    // s.j only tests the stopped/apex arrow rectangle, after target movement and projectile update.
    for (let i = 0; i < s.targets.length; i++) {
      const t = s.targets[i],
        profile = huntingProfile(t.itemId)!;
      if (
        t.x - (profile.width >> 1) < p.x + 8 &&
        t.x + (profile.width >> 1) > p.x &&
        t.y - (profile.height >> 1) < p.y + 1 &&
        t.y + (profile.height >> 1) > p.y - 7
      ) {
        s.status = "won";
        s.winner = i;
        t.enabled = false;
      } else if (s.shots.length === 1) {
        wander(s, t, true);
        s.initialRest = false;
      }
    }
    p.phase = 3;
    if (s.status === "won") continue; // Prevent the source's overlapping tenth-shot success/retaliation result paths from settling twice.
    if (s.shots.length >= R.maxShots) {
      if (s.targets.some((t) => huntingProfile(t.itemId)!.dangerous)) {
        s.phase = "attacking";
        s.attackMs = 0;
        for (const t of s.targets)
          if (huntingProfile(t.itemId)!.dangerous) {
            t.attackX = t.x;
            t.attackY = t.y;
            t.dx = 10 - t.x;
            t.dy = huntGround - t.y;
          }
      } else s.status = "lost";
    } else {
      s.phase = "aiming";
      s.angle = 70;
      s.power = 40;
    }
  }
  return s;
}
export function huntingOutcome(
  inventory: Readonly<Record<number, number>>,
  game: HuntingGame,
) {
  if (
    game.status === "playing" ||
    (inventory[R.tool] ?? 0) < 1 ||
    (inventory[R.ammunition] ?? 0) < 1
  )
    return null;
  const next: Record<number, number> = {
    ...inventory,
    [R.ammunition]: inventory[R.ammunition] - 1,
  };
  const target =
    game.status === "won" && game.winner !== null
      ? game.targets[game.winner]
      : null;
  if (game.status === "won" && !target) return null;
  if (target) next[target.itemId] = (next[target.itemId] ?? 0) + 1;
  return { inventory: next, target, attackEnergy: game.attackEnergy };
}
