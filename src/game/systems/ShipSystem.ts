import { shipRules as R } from "../data/shipRules";
import type { RecoveredItem } from "../assets/assetManifest";
import type { Inventory } from "./ItemRules";
import { activityEffect, type SurvivalState } from "./SurvivalSystem";
export interface ShipState {
  phase: "wreck" | "departing" | "escaped";
  elapsedMs: number;
}
export const initialShip = (): ShipState => ({ phase: "wreck", elapsedMs: 0 });
export const isLargeSail = (id: number) => id === R.sailItem;
export const SHIP_SHIFT_Y = R.northShiftTiles * 16;
export const expeditionEnded = (ship: ShipState) => ship.phase !== "wreck";
export function validShip(value: unknown): value is ShipState {
  const s = value as ShipState;
  return (
    !!s &&
    Number.isFinite(s.elapsedMs) &&
    (s.phase === "wreck"
      ? s.elapsedMs === 0
      : s.phase === "departing"
        ? s.elapsedMs >= 0 && s.elapsedMs <= R.strictDepartureMs
        : s.phase === "escaped" && s.elapsedMs === R.strictDepartureMs + 1)
  );
}
export function advanceShip(ship: ShipState, delta: number): ShipState {
  if (ship.phase !== "departing" || !Number.isFinite(delta) || delta <= 0)
    return ship;
  const elapsedMs = Math.min(R.strictDepartureMs + 1, ship.elapsedMs + delta);
  return {
    phase: elapsedMs > R.strictDepartureMs ? "escaped" : "departing",
    elapsedMs,
  };
}
export function shipRepairAvailability(
  items: RecoveredItem[],
  inventory: Inventory,
  ship: ShipState,
) {
  const item = items[R.repairItem];
  const missingMaterials =
    item?.recipe.filter((r) => (inventory[r.item] ?? 0) < r.quantity) ?? [];
  const missingTools =
    item?.craftingTools.filter((r) => (inventory[r.item] ?? 0) < r.quantity) ??
    [];
  return {
    allowed:
      !!item &&
      item.recipe.length > 0 &&
      ship.phase === "wreck" &&
      !missingMaterials.length &&
      !missingTools.length,
    missingMaterials,
    missingTools,
  };
}
export function shipRepairTarget(
  index: number,
  world: {
    x: number;
    y: number;
    worldObjects: readonly number[] | null;
    collected: readonly number[];
  },
) {
  return (
    index === R.index &&
    world.worldObjects?.[index] === R.mapId &&
    !world.collected.includes(index) &&
    Math.hypot(
      (index % 180) * 16 + 8 - world.x,
      Math.floor(index / 180) * 16 + 8 - world.y,
    ) <= 44
  );
}
/** a.G result 19 -> ad.l/o; equipment is required but retained. No portable boat is created. */
export function reconstructShip(
  items: RecoveredItem[],
  inventory: Inventory,
  survival: SurvivalState,
  ship: ShipState,
) {
  if (
    survival.death ||
    survival.restRemainingMs !== null ||
    !shipRepairAvailability(items, inventory, ship).allowed
  )
    return null;
  const next = { ...inventory };
  for (const r of items[R.repairItem].recipe) next[r.item] -= r.quantity;
  return {
    inventory: next,
    survival: activityEffect(survival, items[R.repairItem], "craft"),
    ship: { phase: "departing", elapsedMs: 0 } as ShipState,
  };
}
