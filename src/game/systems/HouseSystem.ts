import type { Inventory } from "./ItemRules";
/** CSV categories 3/5, excluding Cabin's separate a.G upgrade branch. */
export const FURNISHING_IDS = [15, 50, 60, 85] as const;
export const isFurnishing = (id: number): boolean =>
  (FURNISHING_IDS as readonly number[]).includes(id);
export function validFurnishings(
  value: unknown,
): value is Record<number, number> {
  return (
    !!value &&
    typeof value === "object" &&
    !Array.isArray(value) &&
    Object.entries(value).every(
      ([id, n]) =>
        isFurnishing(+id) &&
        String(+id) === id &&
        Number.isInteger(n) &&
        n >= 0 &&
        n <= 100000,
    )
  );
}
/** ad.m stores each crafted furnishing once; tools are checked by the shared recipe rules. */
export function furnish(house: Inventory, id: number): Record<number, number> {
  return { ...house, [id]: 1 };
}
