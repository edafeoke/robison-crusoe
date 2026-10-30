import Phaser from "phaser";
import type { AtlasManifest } from "../assets/assetManifest";
import type { IslandMap } from "../maps/MapDecoder";
import { makeRegion } from "../maps/MapRenderer";
// data.c.a(): original shoreline cycle, advanced by a 400 ms timer.
export const SHORE_SEQUENCES: Record<number, number[]> = {
  5: [0, 1, 2, 1],
  6: [1, 2, 1, 0],
  7: [2, 1, 0, 1],
  17: [3, 4, 5, 4],
  18: [4, 5, 4, 3],
  19: [5, 4, 3, 4],
  20: [6, 7, 8, 7],
  21: [7, 8, 7, 6],
  22: [8, 7, 6, 7],
};
export class AnimationSystem {
  constructor(scene: Phaser.Scene, map: IslandMap, atlas: AtlasManifest) {
    const shores: { image: Phaser.GameObjects.Image; sequence: number[] }[] =
      [];
    for (let i = 0; i < map.layers.Borders.length; i++) {
      const sequence = SHORE_SEQUENCES[map.layers.Borders[i]];
      if (!sequence) continue;
      const image = makeRegion(
        scene,
        atlas,
        720896 + sequence[0],
        (i % 180) * 16 + 8,
        Math.floor(i / 180) * 16 + 8,
        -90,
      );
      if (image) shores.push({ image, sequence });
    }
    let frame = 0;
    scene.time.addEvent({
      delay: 400,
      loop: true,
      callback: () => {
        frame = (frame + 1) % 4;
        for (const { image, sequence } of shores)
          image.setFrame(sequence[frame]);
      },
    });
  }
}
