import type { RecoveredItem } from "../assets/assetManifest";
import { isFurnishing } from "./HouseSystem";
import { isLargeSail } from "./ShipSystem";
import {
  isClothing,
  initialClothing,
  type ClothingState,
} from "./ClothingSystem";
import {
  isShelterUpgrade,
  shelterAllows,
  shelterOwned,
  type Shelter,
} from "./ShelterSystem";
export type Inventory = Readonly<Record<number, number>>;
export interface Requirement {
  item: number;
  quantity: number;
}
export type PickupRule =
  | { kind: "collect"; tool: number | null }
  | { kind: "special"; reason: "hunting" | "construction" | "unavailable" };
// data.a.c(int) checks special structures, hunting tools, use actions, then pickup.
const SPECIAL_OBJECTS = new Set([9, 10, 17, 18, 84, 94]);
export function pickupRule(item: RecoveredItem): PickupRule {
  if (SPECIAL_OBJECTS.has(item.id))
    return { kind: "special", reason: "construction" };
  if (item.interaction.huntingTool !== -1)
    return { kind: "special", reason: "hunting" };
  // Use-tool availability changes whether USE is offered; TAKE remains available (a.C/a.D).
  if (item.interaction.useTool !== -1) return { kind: "collect", tool: null };
  const tool = item.interaction.pickupTool;
  return tool === -1
    ? { kind: "special", reason: "unavailable" }
    : { kind: "collect", tool: tool === 0 ? null : tool };
}
export function canPickup(item: RecoveredItem, inventory: Inventory): boolean {
  const rule = pickupRule(item);
  return (
    rule.kind === "collect" &&
    (rule.tool === null || (inventory[rule.tool] ?? 0) > 0)
  );
}
export const categoryName = (category: number): string =>
  ({
    0: "Supplies",
    1: "Equipment",
    2: "Construction",
    3: "Furnishings",
    5: "Storage",
    7: "Clothes",
    8: "Clothes",
    9: "Clothes",
    10: "Clothes",
  })[category] ?? "Other";
export function portableRecipes(items: RecoveredItem[]): RecoveredItem[] {
  return items.filter(
    (item) =>
      item.recipe.length > 0 &&
      (item.sprite >= 0 || isLargeSail(item.id)) &&
      (item.category === 0 || item.category === 1),
  );
}
export function supportedRecipes(items: RecoveredItem[]): RecoveredItem[] {
  return items.filter(
    (item) =>
      item.recipe.length > 0 &&
      (((item.sprite >= 0 || isLargeSail(item.id)) &&
        (item.category === 0 ||
          item.category === 1 ||
          isShelterUpgrade(item.id) ||
          isClothing(item.id))) ||
        isFurnishing(item.id)),
  );
}
export interface CraftAvailability {
  allowed: boolean;
  missingMaterials: Requirement[];
  missingTools: Requirement[];
  alreadyOwned: boolean;
  supported: boolean;
  shelterReady: boolean;
}
export function craftAvailability(
  item: RecoveredItem,
  inventory: Inventory,
  shelter: Shelter = 17,
  furnishings: Inventory = {},
  clothing: ClothingState = initialClothing(),
): CraftAvailability {
  const supported =
    !!item.recipe.length &&
    (((item.sprite >= 0 || isLargeSail(item.id)) &&
      (item.category === 0 ||
        item.category === 1 ||
        isShelterUpgrade(item.id) ||
        isClothing(item.id))) ||
      isFurnishing(item.id));
  const missing = (rs: Requirement[]) =>
    rs.filter((r) => (inventory[r.item] ?? 0) < r.quantity);
  const missingMaterials = missing(item.recipe),
    missingTools = missing(item.craftingTools);
  // The original equipment list is unique (ad.k). Prevent wasting ingredients on a duplicate.
  const alreadyOwned =
    (item.category === 1 && (inventory[item.id] ?? 0) > 0) ||
    shelterOwned(item.id, shelter) ||
    (isFurnishing(item.id) && (furnishings[item.id] ?? 0) > 0) ||
    clothing.owned.includes(item.id);
  const shelterReady = shelterAllows(item, shelter);
  return {
    allowed:
      supported &&
      shelterReady &&
      !alreadyOwned &&
      !missingMaterials.length &&
      !missingTools.length,
    missingMaterials,
    missingTools,
    alreadyOwned,
    supported,
    shelterReady,
  };
}
export function craftInventory(
  item: RecoveredItem,
  inventory: Inventory,
  shelter: Shelter = 17,
  furnishings: Inventory = {},
  clothing: ClothingState = initialClothing(),
): Record<number, number> | null {
  if (
    !craftAvailability(item, inventory, shelter, furnishings, clothing).allowed
  )
    return null;
  const next = { ...inventory };
  for (const r of item.recipe) next[r.item] -= r.quantity;
  // a.G changes the camp's sprite/name, rather than adding a portable building.
  if (
    !isShelterUpgrade(item.id) &&
    !isFurnishing(item.id) &&
    !isClothing(item.id)
  )
    next[item.id] = (next[item.id] ?? 0) + 1;
  return next;
}
