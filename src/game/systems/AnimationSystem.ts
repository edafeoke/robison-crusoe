import Phaser from "phaser";
import type { AtlasManifest } from "../assets/assetManifest";
import type { IslandMap } from "../maps/MapDecoder";
import { makeRegion } from "../maps/MapRenderer";
import { SHORE_SEQUENCES } from "../data/shoreSequences";
export class AnimationSystem {
  private shores: { image: Phaser.GameObjects.Image; sequence: number[] }[] =
    [];
  private frame = 0;
  constructor(
    private scene: Phaser.Scene,
    map: IslandMap,
    private atlas: AtlasManifest,
  ) {
    this.setMap(map);
    scene.time.addEvent({
      delay: 400,
      loop: true,
      callback: () => {
        this.frame = (this.frame + 1) % 4;
        for (const { image, sequence } of this.shores)
          image.setFrame(sequence[this.frame]);
      },
    });
  }
  setMap(map: IslandMap) {
    this.shores.forEach((s) => s.image.destroy());
    this.shores = [];
    map.layers.Borders.forEach((id, i) => {
      const sequence = SHORE_SEQUENCES[id];
      if (!sequence) return;
      const image = makeRegion(
        this.scene,
        this.atlas,
        720896 + sequence[this.frame],
        (i % 180) * 16 + 8,
        Math.floor(i / 180) * 16 + 8,
        -90,
      );
      if (image) this.shores.push({ image, sequence });
    });
  }
}
