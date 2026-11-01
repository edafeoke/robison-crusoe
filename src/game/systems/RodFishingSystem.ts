import { rodFishingRules as R } from "../data/rodFishingRules";
export type RodDirection = "left" | "right" | "up" | "down" | null;
export interface RodFishingGame {
  shoreIndex: number;
  seed: number;
  species: number;
  status: "playing" | "won" | "lost";
  phase: "tracking" | "hooked";
  reason: "miss" | "side" | "bottom" | null;
  floatX: number;
  floatY: number;
  hookX: number;
  hookY: number;
  history: number[];
  fishX: number;
  fishY: number;
  facing: 1 | -1;
  travel: 1 | -1;
  fishFrame: number;
  hookFrame: number;
  wanderTicks: number;
  wanderRoll: number;
  jitterTicks: number;
  jitterX: number;
  jitterY: number;
  jitterRight: boolean;
  jitterDown: boolean;
  strike: boolean;
  accumulator: number;
  tick: number;
}
export const rodSide = (R.width >> 1) - 23;
export const rodTop = -((R.height >> 1) - 37 - Math.trunc(R.height / 11));
export const rodBottom = -rodTop;
export const fishVertical = (R.height >> 1) - 50 - Math.trunc(R.height / 11);
function random(s: RodFishingGame, limit: number) {
  let n = s.seed;
  n ^= n << 13;
  n ^= n >>> 17;
  n ^= n << 5;
  s.seed = n >>> 0 || 1;
  return s.seed % limit;
}
export function createRodFishingGame(
  shoreIndex: number,
  seed: number,
): RodFishingGame {
  const s: RodFishingGame = {
    shoreIndex,
    seed: seed >>> 0 || 1,
    species: 0,
    status: "playing",
    phase: "tracking",
    reason: null,
    floatX: 0,
    floatY: -20,
    hookX: 0,
    hookY: 0,
    history: [0, 0, 0, 0, 0],
    fishX: -(R.width >> 1) + 1,
    fishY: 0,
    facing: 1,
    travel: 1,
    fishFrame: 0,
    hookFrame: 0,
    wanderTicks: 0,
    wanderRoll: 0,
    jitterTicks: 0,
    jitterX: 0,
    jitterY: 0,
    jitterRight: true,
    jitterDown: true,
    strike: false,
    accumulator: 0,
    tick: 0,
  };
  s.species = random(s, R.fish.length);
  return s;
}
export function strikeRod(s: RodFishingGame): RodFishingGame {
  return s.status === "playing" && !s.strike ? { ...s, strike: true } : s;
}
function lose(s: RodFishingGame, reason: RodFishingGame["reason"]) {
  s.status = "lost";
  s.reason = reason;
}
function fishMotion(s: RodFishingGame) {
  if (s.phase === "hooked") {
    // x.g chooses the pull direction from hook f's sign, not the fish's former travel direction.
    if (s.hookX >= 0) {
      s.facing = 1;
      s.fishX++;
      s.hookX = s.fishX;
      s.floatX++;
      if (s.fishX >= rodSide) {
        lose(s, "side");
        s.phase = "tracking";
      }
    } else {
      s.facing = -1;
      s.fishX--;
      s.hookX = s.fishX;
      s.floatX--;
      if (s.fishX <= -rodSide) {
        lose(s, "side");
        s.phase = "tracking";
      }
    }
    if (s.hookY >= rodTop) {
      s.fishY++;
      s.hookY = s.fishY;
      s.floatY = s.hookY - R.hookOffset;
      if (s.fishY >= rodBottom) {
        lose(s, "bottom");
        s.phase = "tracking";
      }
    }
  }
  s.fishFrame = (s.fishFrame + 1) % 3;
  if (s.phase === "tracking") {
    if (s.status === "playing") {
      s.facing = s.travel;
      s.fishX += s.travel;
      if (s.fishX === R.width / 2) s.travel = -1;
      else if (s.fishX === -R.width / 2) s.travel = 1;
    } else s.fishX += s.travel * 4;
    if (s.wanderTicks === 0) {
      s.wanderRoll = random(s, 20);
      s.wanderTicks = 5 + random(s, Math.trunc(R.width / 10));
    }
    if (s.wanderRoll < 10) {
      if (s.fishY > -fishVertical) s.fishY--;
    } else if (s.fishY < fishVertical) s.fishY++;
    s.wanderTicks--;
  }
}
function jitter(s: RodFishingGame) {
  if (s.jitterRight) {
    if (s.jitterX < 5) s.floatX++;
    if (s.jitterX === 5) s.jitterRight = false;
    s.jitterX++;
  } else {
    if (s.jitterX > 0) s.floatX--;
    if (s.jitterX === 0) s.jitterRight = true;
    s.jitterX--;
  }
  if (s.jitterDown) {
    if (s.jitterY < 3) s.floatY++;
    if (s.jitterY === 3) s.jitterDown = false;
    s.jitterY++;
  } else {
    if (s.jitterY > 0) s.floatY--;
    if (s.jitterY === 0) s.jitterDown = true;
    s.jitterY--;
  }
}
export function stepRodFishing(
  state: RodFishingGame,
  delta: number,
  direction: RodDirection = null,
): RodFishingGame {
  if (state.status !== "playing" || !Number.isFinite(delta) || delta <= 0)
    return state;
  const s = { ...state, history: [...state.history] };
  s.accumulator += Math.min(delta, 180);
  while (s.accumulator >= R.tickMs && s.status === "playing") {
    s.accumulator -= R.tickMs;
    s.tick++;
    s.hookFrame = (s.hookFrame + 1) % 6;
    // p renders x.a (g then h) before v.b dispatches x.b's control/history update.
    fishMotion(s);
    if (s.strike) {
      s.strike = false;
      const p = R.fish[s.species];
      if (
        s.hookX < s.fishX + (p.width >> 1) &&
        s.hookX > s.fishX - (p.width >> 1) &&
        s.hookY < s.fishY + (p.height >> 1) &&
        s.hookY > s.fishY - (p.height >> 1)
      )
        s.phase = "hooked";
      else lose(s, "miss");
    }
    if (s.status !== "playing") continue;
    if (direction === "left") {
      if (s.floatX > -rodSide) s.floatX -= R.moveStep;
    } else if (direction === "right") {
      if (s.floatX < rodSide) s.floatX += R.moveStep;
    } else if (direction === "up") {
      if (s.phase === "hooked" || s.hookY > rodTop) s.floatY -= R.moveStep;
    } else if (direction === "down" && s.hookY < rodBottom)
      s.floatY += R.moveStep;
    if (s.phase !== "hooked") {
      if (s.jitterTicks === 3) {
        jitter(s);
        s.jitterTicks = 0;
      }
      s.jitterTicks++;
    }
    s.hookY = s.floatY + R.hookOffset;
    s.history.shift();
    s.history.push(s.floatX);
    s.hookX = s.history[0];
    if (s.phase === "hooked") {
      s.fishX = s.hookX;
      s.fishY = s.hookY;
      if (s.hookY < rodTop) s.status = "won";
    }
  }
  return s;
}
export function rodFishingOutcome(
  inventory: Readonly<Record<number, number>>,
  game: RodFishingGame,
) {
  if (
    game.status === "playing" ||
    (inventory[R.tool] ?? 0) < 1 ||
    (inventory[R.bait] ?? 0) < 1
  )
    return null;
  const next: Record<number, number> = {
    ...inventory,
    [R.bait]: inventory[R.bait] - 1,
  };
  if (game.status === "won") {
    const item = R.fish[game.species]?.item;
    if (item === undefined) return null;
    next[item] = (next[item] ?? 0) + 1;
  }
  return next;
}
export function validRodShore(
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
