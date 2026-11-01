import Phaser from "phaser";
import { useGameStore } from "../../store/gameStore";
import { netFishingRules as R } from "../data/netFishingRules";
import type { AtlasManifest } from "../assets/assetManifest";
/** Recovered x.d display/update order; React owns controls and results. */
export class NetFishingScene extends Phaser.Scene {
  private board!: Phaser.GameObjects.Graphics;
  private clip!: Phaser.GameObjects.Graphics;
  private net!: Phaser.GameObjects.Image;
  private fish: Phaser.GameObjects.Image[] = [];
  private atlas!: AtlasManifest;
  constructor() {
    super("NetFishing");
  }
  create() {
    const s = useGameStore.getState();
    if (!s.fishingGame || !s.atlas) {
      this.scene.stop();
      return;
    }
    this.atlas = s.atlas;
    this.board = this.add.graphics();
    this.fish = Array.from({ length: 7 }, () =>
      this.add.image(0, 0, "bank-2", 58).setOrigin(0),
    );
    this.net = this.add.image(0, 0, "bank-13", 16).setOrigin(0);
    this.clip = this.make.graphics({ x: 0, y: 0 });
    const mask = this.clip.createGeometryMask();
    for (const image of this.fish) image.setMask(mask);
    const fire = (e: KeyboardEvent) => {
      const s = useGameStore.getState();
      if (!e.repeat && !s.panel && s.fishingGame?.status === "playing") {
        e.preventDefault();
        s.castNet();
      }
    };
    this.input.keyboard!.on("keydown-SPACE", fire);
    this.input.keyboard!.on("keydown-ENTER", fire);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      if (p.event.target === this.game.canvas)
        useGameStore.getState().castNet();
    });
    this.events.once("shutdown", () => {
      this.input.keyboard?.removeAllListeners();
      mask.destroy();
      this.clip.destroy();
    });
    this.draw();
  }
  private sprite(
    image: Phaser.GameObjects.Image,
    key: number,
    x: number,
    y: number,
    ox: number,
    oy: number,
    scale: number,
  ) {
    const bank = key >>> 16,
      index = key & 65535,
      f = this.atlas.sheets[bank]?.frames[index];
    if (!f) {
      image.setVisible(false);
      return;
    }
    image
      .setVisible(true)
      .setTexture(`bank-${bank}`, index)
      .setFlipX(f.flipX)
      .setScale(scale)
      .setPosition(ox + (x - f.anchorX) * scale, oy + (y - f.anchorY) * scale);
  }
  private draw() {
    const g = useGameStore.getState().fishingGame;
    if (!g) return;
    const w = this.scale.width,
      h = this.scale.height,
      sideSpace = window.innerWidth > 1100 ? 480 : 32,
      scale = Math.max(
        0.5,
        Math.min((w - sideSpace) / R.width, (h - 160) / R.height, 2),
      ),
      x = (w - R.width * scale) / 2,
      y = 66;
    this.board
      .clear()
      .fillStyle(0x183d35)
      .fillRect(0, 0, w, h)
      .fillStyle(0x003366)
      .fillRect(x, y, R.width * scale, R.height * scale)
      .fillStyle(0x006699)
      .fillRect(
        x + (R.width / 2 - R.netWidth / 2) * scale,
        y + 35 * scale,
        R.netWidth * scale,
        (R.height - 80) * scale,
      )
      .lineStyle(1, 0xbca66b, 0.65)
      .strokeRect(x - 4, y - 4, R.width * scale + 8, R.height * scale + 8);
    // Clip passing fish at original screen edges rather than drawing outside the field.
    this.clip.clear().fillRect(x, y, R.width * scale, R.height * scale);
    this.fish.forEach((image, i) => {
      const f = g.fish[i];
      if (!f || f.phase === 2) {
        image.setVisible(false);
        return;
      }
      const profile = R.fish[f.profile],
        frames = f.direction === 1 ? profile.right : profile.left;
      this.sprite(image, frames[g.frame], f.x, f.y, x, y, scale);
    });
    this.sprite(
      this.net,
      R.netSprite,
      R.width / 2 - 1,
      R.height / 2 + g.netY - 1,
      x,
      y,
      scale,
    );
  }
  update(_time: number, delta: number) {
    const s = useGameStore.getState();
    if (!s.fishingGame) {
      this.scene.stop();
      return;
    }
    if (s.panel || document.hidden) return;
    s.advanceNetFishing(delta);
    this.draw();
  }
}
