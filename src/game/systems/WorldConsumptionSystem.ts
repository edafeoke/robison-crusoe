import type { RecoveredItem } from "../assets/assetManifest";
import { worldFoodRules as R } from "../data/worldFoodRules";
import type { Inventory } from "./ItemRules";
import { energyEffect, type SurvivalState } from "./SurvivalSystem";

export const worldFoodProfile = (itemId: number) =>
  R.profiles.find((p) => p.item === itemId);
export function worldFoodAvailability(
  item: RecoveredItem,
  inventory: Inventory,
) {
  const profile = worldFoodProfile(item.id),
    tool = profile?.tool ?? null;
  return {
    supported: !!profile,
    allowed: !!profile && (tool === null || (inventory[tool] ?? 0) > 0),
    tool,
  };
}
export function worldFoodTarget(
  item: RecoveredItem,
  index: number,
  world: {
    x: number;
    y: number;
    worldObjects: readonly number[] | null;
    collected: readonly number[];
  },
) {
  return (
    !!worldFoodProfile(item.id) &&
    Number.isInteger(index) &&
    index >= 0 &&
    index < 32400 &&
    world.worldObjects?.[index] === item.mapId &&
    !world.collected.includes(index) &&
    Math.hypot(
      (index % 180) * 16 + 8 - world.x,
      Math.floor(index / 180) * 16 + 8 - world.y,
    ) <= 44
  );
}
export function worldFoodEffects(item: RecoveredItem) {
  return {
    hunger: item.effects.hunger === -1 ? 0 : item.effects.hunger,
    thirst: item.effects.thirst === -1 ? 0 : item.effects.thirst,
    energy: item.effects.energy * R.energyPasses,
    morale: item.effects.morale,
    strength: item.effects.strength,
  };
}
const cap = (v: number) => Math.max(0, Math.min(100, v));
/** data.ad.j, including early return before removal/nutrition on either fatal energy pass. */
export function consumeWorldFood(
  state: SurvivalState,
  item: RecoveredItem,
  inventory: Inventory,
) {
  if (
    state.death ||
    state.restRemainingMs !== null ||
    !worldFoodAvailability(item, inventory).allowed
  )
    return null;
  const s = { ...state },
    effect = worldFoodEffects(item);
  s.hunger = cap(Math.trunc(s.hunger) + effect.hunger);
  s.thirst = cap(Math.trunc(s.thirst) + effect.thirst);
  const settle = () => {
    const result = energyEffect(s, 0);
    if (result.death === "energy" && item.effects.energy < 0)
      result.death = "poison";
    return result;
  };
  for (let pass = 0; pass < R.energyPasses; pass++) {
    if (pass === 1) {
      s.morale = cap(Math.trunc(s.morale) + effect.morale);
      s.strength = cap(Math.trunc(s.strength) + effect.strength);
    }
    s.energy = cap(Math.trunc(s.energy) + item.effects.energy);
    if (s.enabled && s.energy <= 0) {
      // Source calls loss before assigning the failing energy value. Normalize to a valid collapsed state.
      return { survival: settle(), removed: false, nutrition: null };
    }
  }
  return { survival: settle(), removed: true, nutrition: item.effects.hunger };
}
