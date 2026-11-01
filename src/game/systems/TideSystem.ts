import type { IslandMap } from "../maps/MapDecoder";
import { tideRules as R } from "../data/tideRules";
import { cellPoint } from "./RaftSystem";
import { CollisionSystem } from "./CollisionSystem";
export type TidePhase = "high" | "low";
/** Uses the existing paused browser clock, starting 08:00; the phone uses device minutes. */
export const tideAt = (elapsed: number): TidePhase =>
  Math.floor(Math.max(0, elapsed) / 60) % R.cycleMinutes >= R.bandMinutes
    ? "low"
    : "high";
export const tideChangesIn = (elapsed: number) =>
  R.bandMinutes * 60 - (Math.max(0, elapsed) % (R.bandMinutes * 60));
export function tidalMap(source: IslandMap, phase: TidePhase): IslandMap {
  const remap = (values: number[], table: Record<string, number>) =>
    values.map((v) => (phase === "low" ? (table[v] ?? v) : v));
  return {
    ...source,
    layers: {
      ...source.layers,
      FULL: remap(source.layers.FULL, R.lowTerrain),
      Borders: remap(source.layers.Borders, R.lowBorders),
    },
  };
}
/** ad.r ring ordering and axial preference. Only empty, rock/decor-free terrain4 qualifies. */
export function tideRescue(
  map: IslandMap,
  objects: readonly number[],
  x: number,
  y: number,
): { x: number; y: number } | null {
  const tx = Math.floor(x / 16),
    ty = Math.floor(y / 16),
    terrain = map.layers.FULL[tx + ty * 180];
  if (terrain !== 5 && terrain !== 6) return null;
  const s = R.specialRescue;
  if (tx >= s.minX && tx <= s.maxX && ty >= s.minY && ty <= s.maxY)
    return cellPoint(s.x + s.y * 180);
  const shore = (ax: number, ay: number) => {
    if (ax < 0 || ay < 0 || ax >= 180 || ay >= 180) return false;
    const i = ax + ay * 180,
      l = map.layers;
    return (
      l.FULL[i] === R.rescueTerrain &&
      l.Rocks[i] === 0 &&
      l.Decor[i] === 0 &&
      objects[i] === 0
    );
  };
  for (let r = 1; r <= R.rescueRadius; r++)
    for (let ay = ty - r; ay <= ty + r; ay++)
      for (let ax = tx - r; ax <= tx + r; ax++) {
        if (
          Math.max(Math.abs(ax - tx), Math.abs(ay - ty)) !== r ||
          !shore(ax, ay)
        )
          continue;
        if ((ay === ty - r || ay === ty + r) && ax < tx && shore(tx, ay))
          return cellPoint(tx + ay * 180);
        if ((ax === tx - r || ax === tx + r) && ay < ty && shore(ax, ty))
          return cellPoint(ax + ty * 180);
        return cellPoint(ax + ay * 180);
      }
  return null;
}
/** ad.x checks NW then SE offsets in this exact nested order; no invented ocean movement. */
export function sharkTideDestination(
  map: IslandMap,
  objects: readonly number[],
  x: number,
  y: number,
  player?: { x: number; y: number },
) {
  const tx = Math.floor(x / 16),
    ty = Math.floor(y / 16),
    c = new CollisionSystem(map, new Set(), objects);
  for (let dx = 0; dx < R.sharkSearch; dx++)
    for (let dy = 0; dy < R.sharkSearch; dy++)
      for (const sign of [-1, 1]) {
        const ax = tx + dx * sign,
          ay = ty + dy * sign,
          i = ax + ay * 180;
        if (ax <= 0 || ay <= 0 || ax >= 180 || ay >= 180) continue;
        if (
          player &&
          Math.floor(player.x / 16) === ax &&
          Math.floor(player.y / 16) === ay
        )
          continue;
        const l = map.layers;
        if (
          l.FULL[i] > 4 &&
          l.FULL[i] < 7 &&
          c.rockPassable(l.Rocks[i]) &&
          l.Decor[i] === 0 &&
          objects[i] === 0
        )
          return cellPoint(i);
      }
  return null;
}
