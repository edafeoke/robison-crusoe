import { clothingRules as R } from "../data/clothingRules";
import type { DrawCommand } from "../assets/assetManifest";
export type ClothingSlot = "hat" | "shirt" | "pants" | "shoes";
export type Facing = "down" | "up" | "left" | "right";
export type Outfit = Record<ClothingSlot, number | null>;
export interface ClothingState {
  owned: number[];
  equipped: Outfit;
}
export const clothingSlots: ClothingSlot[] = ["hat", "shirt", "pants", "shoes"];
export const emptyOutfit = (): Outfit => ({
  hat: null,
  shirt: null,
  pants: null,
  shoes: null,
});
export const initialClothing = (): ClothingState => ({
  owned: [],
  equipped: emptyOutfit(),
});
export const clothingProfile = (id: number) =>
  R.profiles.find((p) => p.item === id);
export const isClothing = (id: number): boolean => !!clothingProfile(id);
export function validClothing(value: unknown): value is ClothingState {
  const s = value as ClothingState;
  return (
    !!s &&
    Array.isArray(s.owned) &&
    s.owned.every((id) => Number.isInteger(id) && isClothing(id)) &&
    new Set(s.owned).size === s.owned.length &&
    !!s.equipped &&
    typeof s.equipped === "object" &&
    Object.keys(s.equipped).length === 4 &&
    clothingSlots.every(
      (slot) =>
        s.equipped[slot] === null ||
        (s.owned.includes(s.equipped[slot]!) &&
          clothingProfile(s.equipped[slot]!)?.slot === slot),
    )
  );
}
export function equipClothing(
  s: ClothingState,
  slot: ClothingSlot,
  id: number | null,
): ClothingState | null {
  if (
    !clothingSlots.includes(slot) ||
    s.equipped[slot] === id ||
    (id !== null &&
      (!s.owned.includes(id) || clothingProfile(id)?.slot !== slot))
  )
    return null;
  return { ...s, equipped: { ...s.equipped, [slot]: id } };
}
export function clothingLayers(
  outfit: Outfit,
  facing: Facing,
  frame: number,
): DrawCommand[] {
  if (!Number.isInteger(frame) || frame < 0 || frame > 4) return [];
  return R.layerOrder.flatMap((slot) => {
    const id = outfit[slot],
      profile = id === null ? undefined : clothingProfile(id);
    if (!profile || profile.slot !== slot) return [];
    const offset = R.hatOffsets[facing];
    return [
      {
        sprite: profile.sequences[facing] + (slot === "hat" ? 0 : frame),
        dx: slot === "hat" ? offset.x[frame] : 0,
        dy: slot === "hat" ? offset.y[frame] : 0,
      },
    ];
  });
}
export function huntingDamage(itemId: number, outfit: Outfit): number {
  const rule = R.protection.find((p) => p.item === itemId);
  if (!rule) return 0;
  const id = outfit[rule.slot];
  return id !== null && clothingProfile(id)?.slot === rule.slot
    ? rule.protected
    : rule.unprotected;
}
