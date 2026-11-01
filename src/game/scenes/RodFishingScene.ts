import Phaser from "phaser";
import { useGameStore } from "../../store/gameStore";
import { rodFishingRules as R } from "../data/rodFishingRules";
import type { AtlasManifest } from "../assets/assetManifest";
import type { RodDirection } from "../systems/RodFishingSystem";
export class RodFishingScene extends Phaser.Scene {
  private board!: Phaser.GameObjects.Graphics;
  private line!: Phaser.GameObjects.Graphics;
  private clip!: Phaser.GameObjects.Graphics;
  private rod!: Phaser.GameObjects.Image;
  private hook!: Phaser.GameObjects.Image;
  private fish!: Phaser.GameObjects.Image;
  private atlas!: AtlasManifest;
  private keys!: Record<string, Phaser.Input.Keyboard.Key>;
  private elapsed = 0;
  constructor() {
    super("RodFishing");
  }
  create() {
    const s = useGameStore.getState();
    if (!s.rodGame || !s.atlas) {
      this.scene.stop();
      return;
    }
    this.atlas = s.atlas;
    this.elapsed = 0;
    this.board = this.add.graphics();
    this.line = this.add.graphics();
    this.rod = this.add.image(0, 0, "bank-13", 11).setOrigin(0);
    this.hook = this.add.image(0, 0, "bank-13", 12).setOrigin(0);
    this.fish = this.add.image(0, 0, "bank-2", 28).setOrigin(0);
    this.clip = this.make.graphics({ x: 0, y: 0 });
    const mask = this.clip.createGeometryMask();
    this.fish.setMask(mask);
    this.keys = this.input.keyboard!.addKeys(
      "UP,DOWN,LEFT,RIGHT,W,A,S,D",
    ) as typeof this.keys;
    this.input.keyboard!.addCapture([
      "UP",
      "DOWN",
      "LEFT",
      "RIGHT",
      "W",
      "A",
      "S",
      "D",
    ]);
    const strike = (e: KeyboardEvent) => {
      const s = useGameStore.getState();
      if (
        e.target instanceof Element &&
        e.target.closest("button,input,select,textarea")
      )
        return;
      if (!e.repeat && !s.panel && s.rodGame?.status === "playing") {
        e.preventDefault();
        s.strikeRod();
      }
    };
    this.input.keyboard!.on("keydown-SPACE", strike);
    this.input.keyboard!.on("keydown-ENTER", strike);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      if (p.event.target === this.game.canvas)
        useGameStore.getState().strikeRod();
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
    const s = useGameStore.getState(),
      g = s.rodGame;
    if (!g) return;
    const w = this.scale.width,
      h = this.scale.height,
      side = window.innerWidth > 1100 ? 480 : 32,
      scale = Math.max(
        0.5,
        Math.min((w - side) / R.width, (h - 160) / R.height, 2),
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
        x + 20 * scale,
        y + (Math.trunc(R.height / 11) + 30) * scale,
        (R.width - 40) * scale,
        (R.height - 60 - Math.trunc(R.height / 11) * 2) * scale,
      )
      .lineStyle(1, 0xbca66b, 0.65)
      .strokeRect(x - 4, y - 4, R.width * scale + 8, R.height * scale + 8);
    this.clip.clear().fillRect(x, y, R.width * scale, R.height * scale);
    const cx = R.width >> 1,
      cy = R.height >> 1,
      ix = Math.trunc(g.floatX / 3),
      iy = Math.trunc(g.floatY / 3),
      points = [
        [cx + g.floatX, cy - 3 + g.floatY],
        [cx + 4 + ix * 2, cy - 3 + iy * 2 - 20],
        [cx + 7 + ix, cy - 3 - 60 + iy],
        [cx + 9, cy - 3 - 140],
      ];
    this.line
      .clear()
      .lineStyle(scale, 0xffffff)
      .beginPath()
      .moveTo(x + points[0][0] * scale, y + points[0][1] * scale);
    for (const [px, py] of points.slice(1))
      this.line.lineTo(x + px * scale, y + py * scale);
    this.line
      .strokePath()
      .beginPath()
      .moveTo(x + (cx + g.floatX) * scale, y + (cy + 3 + g.floatY) * scale)
      .lineTo(x + (cx + g.hookX + 1) * scale, y + (cy + g.hookY - 7) * scale)
      .strokePath();
    if (s.enhancements && g.status === "playing")
      this.line
        .lineStyle(1, 0xf0dda1, 0.6)
        .strokeCircle(
          x + (cx + g.hookX) * scale,
          y + (cy + g.hookY) * scale,
          7 * scale,
        );
    this.sprite(
      this.rod,
      R.floatSprite,
      cx + g.floatX,
      cy + g.floatY,
      x,
      y,
      scale,
    );
    this.sprite(
      this.hook,
      R.hookFrames[g.hookFrame < 3 ? 0 : 1],
      cx + g.hookX,
      cy + g.hookY,
      x,
      y,
      scale,
    );
    const p = R.fish[g.species];
    this.sprite(
      this.fish,
      (g.facing === 1 ? p.right : p.left)[g.fishFrame],
      cx + g.fishX,
      cy + g.fishY,
      x,
      y,
      scale,
    );
  }
  update(_time: number, delta: number) {
    const s = useGameStore.getState();
    if (!s.rodGame) {
      this.scene.stop();
      return;
    }
    if (s.panel || document.hidden) {
      this.input.keyboard?.resetKeys();
      return;
    }
    this.elapsed += Math.max(0, Math.min(delta, 180));
    if (this.elapsed >= R.tickMs) {
      const k = this.keys;
      const direction: RodDirection =
        k.LEFT.isDown || k.A.isDown
          ? "left"
          : k.RIGHT.isDown || k.D.isDown
            ? "right"
            : k.UP.isDown || k.W.isDown
              ? "up"
              : k.DOWN.isDown || k.S.isDown
                ? "down"
                : null;
      s.advanceRodFishing(this.elapsed, direction);
      this.elapsed = 0;
    }
    this.draw();
  }
}
