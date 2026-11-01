import type { RecoveredItem } from "../assets/assetManifest";
import { bodyWeightRules as R } from "../data/bodyWeightRules";

/** Native bar units, not kilograms or inventory mass. Width is fixed across browser resizes. */
export interface BodyWeightState {
  barUnits: number;
  nutrition: number;
}
export const WEIGHT_MAX =
  Math.trunc(R.browserReferenceWidth / R.barWidthDivisor) - R.barWidthMargin;
export const initialBodyWeight = (): BodyWeightState => ({
  barUnits: 0,
  nutrition: 0,
});
export const weightPercent = (s: BodyWeightState) =>
  (100 * s.barUnits) / WEIGHT_MAX;
export function validBodyWeight(value: unknown): value is BodyWeightState {
  const s = value as BodyWeightState;
  return (
    !!s &&
    Number.isInteger(s.barUnits) &&
    s.barUnits >= 0 &&
    s.barUnits <= WEIGHT_MAX &&
    Number.isInteger(s.nutrition) &&
    s.nutrition >= -2147483648 &&
    s.nutrition <= 2147483647
  );
}
/** ad.g calls v.a once before effects; the tool branch forwards even the -1 sentinel. */
export function foodNutrition(item: RecoveredItem): number | null {
  return item.interaction.useTool > 0 || item.effects.hunger !== -1
    ? item.effects.hunger
    : null;
}
/** v.a uses one IF, not a loop, even for a meal of 100 nutrition or a queued remainder >=50. */
export function gainBodyWeight(
  s: BodyWeightState,
  nutrition: number | null,
): BodyWeightState {
  if (nutrition === null) return s;
  const next = { ...s, nutrition: (s.nutrition + nutrition) | 0 }; // Java signed int addition.
  if (next.nutrition >= R.nutritionThreshold) {
    next.nutrition = (next.nutrition - R.nutritionThreshold) | 0;
    if (next.barUnits < WEIGHT_MAX) next.barUnits += R.barStep;
  }
  return next;
}
