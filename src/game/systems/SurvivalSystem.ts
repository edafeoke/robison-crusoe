import type { RecoveredItem } from "../assets/assetManifest";
import type { Inventory } from "./ItemRules";

export type LossCause = "hunger" | "thirst" | "energy" | "poison" | "animal";
export interface SurvivalState {
  enabled: boolean;
  hunger: number;
  thirst: number;
  energy: number;
  morale: number;
  strength: number;
  minutes: number;
  minuteMs: number;
  restRemainingMs: number | null;
  stoneUses: number;
  death: LossCause | null;
}
export const MINUTE_MS = 60000;
export const REST_MS = 10000;
export const REST_ENERGY = 30;
export function energyEffect(s: SurvivalState, amount: number): SurvivalState {
  return finish({ ...s, energy: percent(Math.floor(s.energy) + amount) });
}
const percent = (n: number) => Math.max(0, Math.min(100, n));
export function initialSurvival(enabled = true, elapsedMs = 0): SurvivalState {
  return {
    enabled,
    hunger: 100,
    thirst: 100,
    energy: 100,
    morale: 0,
    strength: 0,
    minutes: Math.floor(elapsedMs / MINUTE_MS),
    minuteMs: elapsedMs % MINUTE_MS,
    restRemainingMs: null,
    stoneUses: 0,
    death: null,
  };
}
export function validSurvival(value: unknown): value is SurvivalState {
  const s = value as SurvivalState;
  return (
    !!s &&
    typeof s.enabled === "boolean" &&
    [s.hunger, s.thirst, s.energy, s.morale, s.strength].every(
      (v) => Number.isFinite(v) && v >= 0 && v <= 100,
    ) &&
    Number.isSafeInteger(s.minutes) &&
    s.minutes >= 0 &&
    s.minutes < 10000000 &&
    Number.isFinite(s.minuteMs) &&
    s.minuteMs >= 0 &&
    s.minuteMs < MINUTE_MS &&
    (s.restRemainingMs === null ||
      (Number.isFinite(s.restRemainingMs) &&
        s.restRemainingMs > 0 &&
        s.restRemainingMs <= REST_MS)) &&
    Number.isInteger(s.stoneUses) &&
    s.stoneUses >= 0 &&
    s.stoneUses < 6 &&
    [null, "hunger", "thirst", "energy", "poison", "animal"].includes(
      s.death,
    ) &&
    (s.death === null
      ? s.hunger > 0 && s.thirst > 0 && s.energy > 0
      : s.enabled && (s.hunger === 0 || s.thirst === 0 || s.energy === 0)) &&
    (s.enabled || (s.hunger === 100 && s.thirst === 100 && s.energy === 100))
  );
}
function finish(s: SurvivalState, poison = false): SurvivalState {
  if (!s.enabled)
    return { ...s, hunger: 100, thirst: 100, energy: 100, death: null };
  const death =
    s.hunger <= 0
      ? "hunger"
      : s.thirst <= 0
        ? "thirst"
        : s.energy <= 0
          ? poison
            ? "poison"
            : "energy"
          : null;
  return { ...s, death, restRemainingMs: death ? null : s.restRemainingMs };
}
/** ad.f / v.f: percent loss at minute changes; minute 59->0 skips the source decrement. */
export function advanceSurvival(
  state: SurvivalState,
  deltaMs: number,
): SurvivalState {
  if (state.death || !Number.isFinite(deltaMs) || deltaMs <= 0) return state;
  const s = { ...state };
  let remaining = deltaMs;
  while (remaining > 0) {
    const step = Math.min(
      remaining,
      MINUTE_MS - s.minuteMs,
      s.restRemainingMs ?? Infinity,
    );
    s.minuteMs += step;
    if (s.restRemainingMs !== null) s.restRemainingMs -= step;
    remaining -= step;
    if (s.minuteMs >= MINUTE_MS) {
      s.minuteMs = 0;
      s.minutes++;
      if (s.enabled && s.minutes % 60 !== 0) {
        s.hunger = percent(s.hunger - 10);
        s.thirst = percent(s.thirst - 14);
        s.energy = percent(s.energy - 4);
      }
    }
    const depleted = finish(s);
    if (depleted.death) return depleted;
    if (s.restRemainingMs === 0) {
      s.restRemainingMs = null;
      s.energy = percent(Math.floor(s.energy) + REST_ENERGY);
    }
  }
  return finish(s);
}
/** ad.a/ad.l: resource pickup costs and creation effects, once per successful action. */
export function activityEffect(
  s: SurvivalState,
  item: RecoveredItem,
  action: "collect" | "craft",
): SurvivalState {
  if (
    action === "collect" &&
    (item.interaction.pickupTool === -1 || item.interaction.useTool !== -1)
  )
    return s;
  return finish({
    ...s,
    energy: percent(Math.floor(s.energy) + item.effects.energy),
    morale: percent(Math.floor(s.morale) + item.effects.morale),
    strength: percent(Math.floor(s.strength) + item.effects.strength),
  });
}
export function edible(item: RecoveredItem): boolean {
  return (
    item.category === 0 &&
    item.interaction.useTool >= 0 &&
    (item.effects.hunger !== -1 ||
      item.effects.thirst !== -1 ||
      item.effects.energy !== 0)
  );
}
export function useAvailability(
  item: RecoveredItem,
  inventory: Inventory,
): { allowed: boolean; tool: number | null } {
  const tool = item.interaction.useTool > 0 ? item.interaction.useTool : null;
  return {
    allowed:
      edible(item) &&
      (inventory[item.id] ?? 0) > 0 &&
      (tool === null || (inventory[tool] ?? 0) > 0),
    tool,
  };
}
export function foodEffects(item: RecoveredItem) {
  const toolBranch = item.interaction.useTool > 0;
  return {
    hunger: toolBranch || item.effects.hunger !== -1 ? item.effects.hunger : 0,
    thirst: toolBranch || item.effects.thirst !== -1 ? item.effects.thirst : 0,
    energy: item.effects.energy * (toolBranch ? 2 : 1),
    morale: toolBranch ? item.effects.morale : 0,
    strength: toolBranch ? item.effects.strength : 0,
  };
}
/** ad.g/h: preserve the bytecode's double energy application in the positive-tool branch. */
export function consumeItem(
  state: SurvivalState,
  item: RecoveredItem,
  inventory: Inventory,
): { survival: SurvivalState; inventory: Record<number, number> } | null {
  if (
    state.death ||
    state.restRemainingMs !== null ||
    !useAvailability(item, inventory).allowed
  )
    return null;
  const s = { ...state },
    next = { ...inventory },
    e = foodEffects(item);
  next[item.id]--;
  s.hunger = percent(Math.floor(s.hunger) + e.hunger);
  s.thirst = percent(Math.floor(s.thirst) + e.thirst);
  s.energy = percent(Math.floor(s.energy) + item.effects.energy);
  if (item.interaction.useTool > 0)
    s.energy = percent(s.energy + item.effects.energy);
  s.morale = percent(Math.floor(s.morale) + e.morale);
  s.strength = percent(Math.floor(s.strength) + e.strength);
  // h counts six coconut openings before consuming one stone. Equipment (knife/fire) is kept.
  if (item.interaction.useTool === 3) {
    if (++s.stoneUses === 6) {
      next[3]--;
      s.stoneUses = 0;
    }
  }
  return { survival: finish(s, item.effects.energy < 0), inventory: next };
}
