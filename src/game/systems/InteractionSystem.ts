import type { IslandMap } from "../maps/MapDecoder";
import type { FocusObject } from "../../store/gameStore";
import { isCoconutPalm } from "../maps/DecorVariants";
export function nearestObject(
  map: IslandMap,
  x: number,
  y: number,
  removed: Set<number>,
  radius = 42,
  objects?: readonly number[],
): FocusObject | null {
  let best: FocusObject | null = null;
  const tx = Math.floor(x / 16),
    ty = Math.floor(y / 16);
  for (let dy = -3; dy <= 3; dy++)
    for (let dx = -3; dx <= 3; dx++) {
      const ax = tx + dx,
        ay = ty + dy;
      if (ax < 0 || ay < 0 || ax >= 180 || ay >= 180) continue;
      const index = ax + ay * 180,
        id = (objects ?? map.layers.Objects)[index];
      if (!id || (!objects && removed.has(index))) continue;
      const ox = ax * 16 + 8,
        oy = ay * 16 + 8,
        distance = Math.hypot(ox - x, oy - y);
      if (distance <= radius && (!best || distance < best.distance))
        best = { index, itemId: id - 1, x: ox, y: oy, distance };
    }
  return best;
}
export function nearestInteraction(
  map: IslandMap,
  x: number,
  y: number,
  removed: Set<number>,
  radius = 42,
  objects?: readonly number[],
): FocusObject | null {
  let best = nearestObject(map, x, y, removed, radius, objects);
  let shore: FocusObject | null = null;
  const tx = Math.floor(x / 16),
    ty = Math.floor(y / 16);
  for (let dy = -3; dy <= 3; dy++)
    for (let dx = -3; dx <= 3; dx++) {
      const ax = tx + dx,
        ay = ty + dy,
        index = ax + ay * 180;
      if (
        ax < 0 ||
        ay < 0 ||
        ax >= 180 ||
        ay >= 180 ||
        (!isCoconutPalm(map.layers.Decor, index) &&
          map.layers.FULL[index] !== 4 &&
          map.layers.FULL[index] !== 5)
      )
        continue;
      const px = ax * 16 + 8,
        py = ay * 16 + 8,
        distance = Math.hypot(px - x, py - y);
      const palm = isCoconutPalm(map.layers.Decor, index);
      const target = palm ? best : shore;
      if (distance <= radius && (!target || distance < target.distance)) {
        const candidate: FocusObject = {
          index,
          itemId: palm ? 1 : map.layers.FULL[index] === 5 ? 40 : 59,
          x: px,
          y: py,
          distance,
          kind: palm
            ? "coconut-palm"
            : map.layers.FULL[index] === 5
              ? "rod-fishing"
              : "net-fishing",
        };
        if (palm) best = candidate;
        else shore = candidate;
      }
    }
  return best ?? shore;
}
