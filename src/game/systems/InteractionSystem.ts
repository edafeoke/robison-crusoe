import type { IslandMap } from "../maps/MapDecoder";
import type { FocusObject } from "../../store/gameStore";
export function nearestObject(
  map: IslandMap,
  x: number,
  y: number,
  removed: Set<number>,
  radius = 42,
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
        id = map.layers.Objects[index];
      if (!id || removed.has(index)) continue;
      const ox = ax * 16 + 8,
        oy = ay * 16 + 8,
        distance = Math.hypot(ox - x, oy - y);
      if (distance <= radius && (!best || distance < best.distance))
        best = { index, itemId: id - 1, x: ox, y: oy, distance };
    }
  return best;
}
