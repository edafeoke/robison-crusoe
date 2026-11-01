import type { RecoveredItem } from "../assets/assetManifest";
import { energyEffect, type SurvivalState } from "./SurvivalSystem";
/** data.x and data.s: fishing/hunting modes apply n[tool] strength before m[tool] energy. */
export function fishingCompletionEffect(
  s: SurvivalState,
  tool: RecoveredItem,
): SurvivalState {
  return energyEffect(
    {
      ...s,
      strength: Math.min(100, s.strength + Math.max(0, tool.effects.strength)),
    },
    tool.effects.energy,
  );
}
