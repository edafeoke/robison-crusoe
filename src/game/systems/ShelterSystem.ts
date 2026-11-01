import type { RecoveredItem } from "../assets/assetManifest";

/** data.a.G upgrades item 17 in place; data.aa.c checks this global camp tier. */
export type Shelter = 17 | 9 | 10;
export const validShelter = (value: unknown): value is Shelter =>
  value === 17 || value === 9 || value === 10;
export const shelterRank = (shelter: Shelter): number =>
  shelter === 10 ? 2 : shelter === 9 ? 1 : 0;
export const isShelterUpgrade = (id: number): id is 9 | 10 =>
  id === 9 || id === 10;
export function shelterAllows(item: RecoveredItem, shelter: Shelter): boolean {
  return (
    item.shelterRequirement === -1 ||
    (item.shelterRequirement === 9 && shelterRank(shelter) >= 1) ||
    (item.shelterRequirement === 10 && shelter === 10)
  );
}
export function shelterOwned(id: number, shelter: Shelter): boolean {
  return isShelterUpgrade(id) && shelterRank(shelter) >= shelterRank(id);
}
/** Preserve the original map IDs and collision cells. Only the camp's appearance changes. */
export const campItem = (itemId: number, shelter: Shelter): number =>
  itemId === 17 ? shelter : itemId;
