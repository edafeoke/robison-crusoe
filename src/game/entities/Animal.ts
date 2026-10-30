import Phaser from "phaser";
import type { AtlasManifest } from "../assets/assetManifest";
import type { WildlifeActor } from "./WildlifeState";
import type { WildlifeSystem } from "../systems/WildlifeSystem";

/** Original D0 region anchors, mirrored regions and BS9 shadows/wakes. */
export class Animal {
  private body: Phaser.GameObjects.Image;
  private shadow: Phaser.GameObjects.Image;
  private tailShadow: Phaser.GameObjects.Image;
  private wake: Phaser.GameObjects.Image;
  constructor(
    scene: Phaser.Scene,
    private atlas: AtlasManifest,
    private actor: WildlifeActor,
    private system: WildlifeSystem,
  ) {
    this.body = scene.add.image(0, 0, "bank-2", 0).setOrigin(0);
    this.shadow = scene.add.image(0, 0, "bank-9", 3).setOrigin(0);
    this.tailShadow = scene.add.image(0, 0, "bank-9", 3).setOrigin(0);
    this.wake = scene.add.image(0, 0, "bank-9", 2).setOrigin(0);
    this.render();
  }
  private region(
    image: Phaser.GameObjects.Image,
    key: number,
    x: number,
    y: number,
    depth: number,
  ) {
    const bank = key >>> 16,
      frame = key & 65535,
      f = this.atlas.sheets[bank]?.frames[frame];
    if (!f) {
      image.setVisible(false);
      return;
    }
    image
      .setVisible(true)
      .setTexture(`bank-${bank}`, frame)
      .setFlipX(f.flipX)
      .setPosition(x - f.anchorX, y - f.anchorY)
      .setDepth(depth);
  }
  render() {
    const a = this.actor,
      terrain =
        this.system.map.layers.FULL[
          Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180
        ],
      flying = a.phase === "fly",
      shark = a.profile.family === "shark",
      predator = a.profile.family === "predator",
      wet = predator ? [4, 14].includes(terrain) : [4, 5, 6].includes(terrain),
      depth = flying ? 3200 : a.y + 0.5;
    this.region(this.body, this.system.sprite(a), a.x, a.y, depth);
    if (!flying && !shark && !wet)
      this.region(this.shadow, 589827, a.x, a.y, a.y + 0.3);
    else this.shadow.setVisible(false);
    if (!flying && a.mapId === 43 && !wet)
      this.region(
        this.tailShadow,
        589827,
        a.x + (a.facing === "right" ? -16 : 16),
        a.y,
        a.y + 0.3,
      );
    else this.tailShadow.setVisible(false);
    if (!flying && (wet || shark)) {
      const alternate = shark
        ? Math.floor(a.frame / 3)
        : Math.floor(a.frame / 2);
      const key = shark
        ? (a.facing === "left" ? 589831 : 589833) + alternate
        : a.mapId === 43
          ? (a.facing === "left" ? 589835 : 589837) + alternate
          : alternate
            ? 589830
            : 589826;
      this.region(this.wake, key, a.x, a.y, depth + 0.01);
    } else this.wake.setVisible(false);
  }
}
