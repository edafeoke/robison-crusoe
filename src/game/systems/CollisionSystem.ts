import {
  MAP_SIZE,
  TILE_SIZE,
  tileIndex,
  type IslandMap,
} from "../maps/MapDecoder";
// Recovered from data.g.a(int,int), c, d and e. Walking mode only; sailing is deferred.
const BLOCKED_TERRAIN = new Set([5, 6, 12, 13, 14]);
const PASSABLE_ROCKS = new Set([
  0, 10, 11, 12, 18, 20, 22, 23, 24, 30, 31, 32, 38, 41, 42, 43, 44, 45, 46, 47,
]);
export class CollisionSystem {
  constructor(
    public map: IslandMap,
    public removed: Set<number> = new Set(),
  ) {}
  walkable(tx: number, ty: number): boolean {
    if (tx < 0 || ty < 0 || tx >= MAP_SIZE || ty >= MAP_SIZE) return false;
    const i = tileIndex(tx, ty),
      l = this.map.layers;
    return (
      !BLOCKED_TERRAIN.has(l.FULL[i]) &&
      (PASSABLE_ROCKS.has(l.Rocks[i]) || l.Rocks[i] >= 50) &&
      l.Decor[i] === 0 &&
      (l.Objects[i] === 0 || this.removed.has(i))
    );
  }
  canStand(x: number, y: number): boolean {
    return [
      [-3, -2],
      [3, -2],
      [-3, 2],
      [3, 2],
    ].every(([dx, dy]) =>
      this.walkable(
        Math.floor((x + dx) / TILE_SIZE),
        Math.floor((y + dy) / TILE_SIZE),
      ),
    );
  }
  nearest(x: number, y: number, radius = 12): { x: number; y: number } | null {
    const cx = Math.max(0, Math.min(179, Math.floor(x / 16))),
      cy = Math.max(0, Math.min(179, Math.floor(y / 16)));
    for (let r = 0; r <= radius; r++)
      for (let dy = -r; dy <= r; dy++)
        for (let dx = -r; dx <= r; dx++) {
          if (Math.max(Math.abs(dx), Math.abs(dy)) !== r) continue;
          if (this.walkable(cx + dx, cy + dy))
            return { x: (cx + dx) * 16 + 8, y: (cy + dy) * 16 + 8 };
        }
    return null;
  }
}
