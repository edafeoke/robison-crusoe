export const MAP_SIZE = 180;
export const TILE_SIZE = 16;
export const LAYERS = ["FULL", "Borders", "Rocks", "Decor", "Objects"] as const;
export type LayerName = (typeof LAYERS)[number];
export interface IslandMap {
  version: number;
  width: number;
  height: number;
  tileWidth: number;
  tileHeight: number;
  layers: Record<LayerName, number[]>;
  sha256: Record<LayerName, string>;
}
export function decodeLayer(
  buffer: ArrayBuffer,
  width = MAP_SIZE,
  height = MAP_SIZE,
): Uint8Array {
  if (buffer.byteLength !== width * height)
    throw new Error(
      `Invalid map length: expected ${width * height}, received ${buffer.byteLength}`,
    );
  return new Uint8Array(buffer.slice(0));
}
export function validateMap(value: unknown): IslandMap {
  const map = value as IslandMap;
  if (
    !map ||
    map.version !== 1 ||
    map.width !== MAP_SIZE ||
    map.height !== MAP_SIZE ||
    map.tileWidth !== TILE_SIZE ||
    map.tileHeight !== TILE_SIZE
  )
    throw new Error("Unsupported island map format");
  for (const name of LAYERS) {
    const layer = map.layers?.[name];
    if (
      !Array.isArray(layer) ||
      layer.length !== MAP_SIZE * MAP_SIZE ||
      layer.some((v) => !Number.isInteger(v) || v < 0 || v > 255)
    )
      throw new Error(`Invalid ${name} map layer`);
  }
  return map;
}
export const tileIndex = (x: number, y: number) => x + y * MAP_SIZE;
