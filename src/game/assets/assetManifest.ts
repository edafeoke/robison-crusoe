// Original banks are preserved byte-for-byte. Region metadata is decoded from D0 by tools/decode_original.py.
export interface SpriteFrame {
  x: number;
  y: number;
  width: number;
  height: number;
  anchorX: number;
  anchorY: number;
  flipX: boolean;
}
export interface SpriteSheet {
  bank: number;
  url: string;
  width: number;
  height: number;
  frames: SpriteFrame[];
}
export interface DrawCommand {
  sprite: number;
  dx: number;
  dy: number;
}
export interface AtlasManifest {
  sheets: SpriteSheet[];
  decor: Record<number, number>;
  borders: Record<number, number>;
  icons: Record<number, number>;
  composites: Record<string, DrawCommand[]>;
  variants: Record<number, string[]>;
}
export const assetManifest = {
  atlas: "/assets/ui/atlas.json",
  map: "/assets/maps/island.json",
  objects: "/assets/objects/objects.json",
  story: "/assets/ui/story.json",
  wildlife: "/assets/animals/wildlife.json",
  overview: "/assets/maps/island-overview.png",
  player: {
    bank: 3,
    sequences: {
      down: [0, 1, 2, 3, 4],
      up: [5, 6, 7, 8, 9],
      left: [10, 11, 12, 13, 14],
      right: [15, 16, 17, 18, 19],
    },
    frameMs: 125,
  },
} as const;
export interface RecoveredItem {
  id: number;
  mapId: number;
  name: string;
  description: string;
  sprite: number;
  rawFields: string[];
  recipe: { item: number; quantity: number }[];
  craftingTools: { item: number; quantity: number }[];
  category: number;
  interaction: { pickupTool: number; useTool: number; huntingTool: number };
  effects: {
    hunger: number;
    thirst: number;
    morale: number;
    energy: number;
    strength: number;
  };
}
