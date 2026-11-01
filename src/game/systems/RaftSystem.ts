import { raftRules as R } from "../data/raftRules";
import type { RecoveredItem, DrawCommand } from "../assets/assetManifest";
import type { Inventory } from "./ItemRules";
import { activityEffect, type SurvivalState } from "./SurvivalSystem";
import { clothingRules } from "../data/clothingRules";
import type { Outfit, Facing } from "./ClothingSystem";
import { CollisionSystem } from "./CollisionSystem";
export interface RaftState {
  phase: "broken" | "moored" | "aboard";
  index: number | null;
  facing: "left" | "right";
}
export const initialRaft = (): RaftState => ({
  phase: "broken",
  index: R.index,
  facing: "left",
});
export function validRaft(value: unknown): value is RaftState {
  const r = value as RaftState;
  return (
    !!r &&
    (r.facing === "left" || r.facing === "right") &&
    (r.phase === "broken"
      ? r.index === R.index
      : r.phase === "aboard"
        ? r.index === null
        : r.phase === "moored" &&
          Number.isInteger(r.index) &&
          r.index! >= 0 &&
          r.index! < 32400)
  );
}
export const cellOf = (x: number, y: number) =>
  Math.floor(x / 16) + Math.floor(y / 16) * 180;
export const cellPoint = (index: number) => ({
  x: (index % 180) * 16 + 8,
  y: Math.floor(index / 180) * 16 + 8,
});
export const directions: Record<Facing, readonly [number, number]> = {
  left: [-1, 0],
  right: [1, 0],
  up: [0, -1],
  down: [0, 1],
};
/** Runtime overlay only. Does not clear unrelated resources or source bytes. Apply after pickup tombstones. */
export function raftObjects(
  objects: readonly number[],
  raft: RaftState,
): number[] {
  const result = objects.map((v) =>
    v === R.brokenMapId || v === R.mapId ? 0 : v,
  );
  if (raft.phase === "broken") result[R.index] = R.brokenMapId;
  else if (raft.phase === "moored") result[raft.index!] = R.mapId;
  return result;
}
export function raftAvailability(
  items: RecoveredItem[],
  inventory: Inventory,
  raft: RaftState,
) {
  const item = items[R.item];
  return (
    !!item &&
    raft.phase === "broken" &&
    item.recipe.length > 0 &&
    [...item.recipe, ...item.craftingTools].every(
      (r) => (inventory[r.item] ?? 0) >= r.quantity,
    )
  );
}
export function repairRaft(
  items: RecoveredItem[],
  inventory: Inventory,
  life: SurvivalState,
  raft: RaftState,
) {
  if (
    life.death ||
    life.restRemainingMs !== null ||
    !raftAvailability(items, inventory, raft)
  )
    return null;
  const next = { ...inventory };
  for (const r of items[R.item].recipe) next[r.item] -= r.quantity;
  return {
    inventory: next,
    survival: activityEffect(life, items[R.item], "craft"),
    raft: { ...raft, phase: "moored" as const },
  };
}
export function raftTarget(
  index: number,
  x: number,
  y: number,
  objects: readonly number[] | null,
  raft: RaftState,
) {
  const point = cellPoint(index);
  return (
    raft.phase !== "aboard" &&
    index === raft.index &&
    objects?.[index] === (raft.phase === "broken" ? R.brokenMapId : R.mapId) &&
    Math.hypot(point.x - x, point.y - y) <= 44
  );
}
export function boardingPoint(
  raft: RaftState,
  x: number,
  y: number,
  collision: CollisionSystem,
) {
  if (
    raft.phase !== "moored" ||
    raft.index === null ||
    !collision.canStand(x, y)
  )
    return null;
  const from = cellOf(x, y),
    dx = (raft.index % 180) - (from % 180),
    dy = Math.floor(raft.index / 180) - Math.floor(from / 180);
  if (
    Math.abs(dx) + Math.abs(dy) !== 1 ||
    collision.objects?.[raft.index] !== R.mapId
  )
    return null;
  const point = cellPoint(raft.index),
    i = raft.index,
    l = collision.map.layers;
  if (
    !(R.waterTerrain as readonly number[]).includes(l.FULL[i]) ||
    l.Decor[i] ||
    !collision.rockPassable(l.Rocks[i])
  )
    return null;
  return point;
}
/** Source priority left/right/up/down; browser also checks rocks to avoid the source's duplicate-terrain landing bug. */
export function landingPoint(
  raft: RaftState,
  x: number,
  y: number,
  collision: CollisionSystem,
) {
  if (raft.phase !== "aboard") return null;
  const tx = Math.floor(x / 16),
    ty = Math.floor(y / 16),
    index = cellOf(x, y);
  if (collision.objects?.[index] !== 0) return null;
  for (const direction of R.landPriority) {
    const [dx, dy] = directions[direction];
    if (collision.walkableOnFoot(tx + dx, ty + dy))
      return { ...cellPoint(tx + dx + (ty + dy) * 180), direction };
  }
  return null;
}
/** Source draw order: raft, body, shirt/hat, then three foreground pieces. Pants/shoes are hidden aboard. */
export function raftLayers(
  facing: RaftState["facing"],
  stroke: number,
  outfit: Outfit,
): DrawCommand[] {
  const pose =
    ((Math.floor(stroke) % R.strokePhases) + R.strokePhases) % R.strokePhases <
    R.firstPosePhases
      ? 0
      : 1;
  const layers = R.layers[facing][pose];
  const keys = R.clothing[facing][pose] as Record<string, number>;
  const clothes: DrawCommand[] = [];
  for (const slot of ["shirt", "hat"] as const) {
    const id = outfit[slot];
    if (id === null || keys[id] === undefined) continue;
    const offsets =
      clothingRules.hatOffsets[facing === "left" ? "right" : "left"];
    clothes.push({
      sprite: keys[id],
      dx: slot === "hat" ? offsets.x[pose] : 0,
      dy: slot === "hat" ? offsets.y[pose] : 0,
    });
  }
  return [
    ...layers.slice(0, 2).map((sprite) => ({ sprite, dx: 0, dy: 0 })),
    ...clothes,
    ...layers.slice(2).map((sprite) => ({ sprite, dx: 0, dy: 0 })),
  ];
}
