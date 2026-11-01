import Phaser from "phaser";
import { useGameStore } from "../../store/gameStore";
import { huntingRules as R } from "../data/huntingRules";
import { huntingProfile, huntGround } from "../systems/HuntingSystem";
import type { AtlasManifest } from "../assets/assetManifest";
export class HuntingScene extends Phaser.Scene {
  private board!: Phaser.GameObjects.Graphics;
  private aim!: Phaser.GameObjects.Graphics;
  private bow!: Phaser.GameObjects.Image;
  private arrows: Phaser.GameObjects.Image[] = [];
  private targets: Phaser.GameObjects.Image[] = [];
  private atlas!: AtlasManifest;
  private keys!: Record<string, Phaser.Input.Keyboard.Key>;
  private elapsed = 0;
  constructor() {
    super("Hunting");
  }
  create() {
    const s = useGameStore.getState();
    if (!s.huntingGame || !s.atlas) {
      this.scene.stop();
      return;
    }
    this.atlas = s.atlas;
    this.elapsed = 0;
    this.board = this.add.graphics();
    this.aim = this.add.graphics();
    this.bow = this.add.image(0, 0, "bank-13", 22).setOrigin(0);
    this.arrows = Array.from({ length: 10 }, () =>
      this.add.image(0, 0, "bank-13", 24).setOrigin(0),
    );
    this.targets = s.huntingGame.targets.map(() =>
      this.add.image(0, 0, "bank-2", 0).setOrigin(0),
    );
    this.keys = this.input.keyboard!.addKeys(
      "UP,DOWN,LEFT,RIGHT",
    ) as typeof this.keys;
    this.input.keyboard!.addCapture(["UP", "DOWN", "LEFT", "RIGHT"]);
    const fire = (e: KeyboardEvent) => {
      if (
        e.target instanceof Element &&
        e.target.closest("button,input,select,textarea")
      )
        return;
      const s = useGameStore.getState();
      if (!e.repeat && !s.panel && s.huntingGame?.status === "playing") {
        e.preventDefault();
        s.fireHunt();
      }
    };
    this.input.keyboard!.on("keydown-SPACE", fire);
    this.input.keyboard!.on("keydown-ENTER", fire);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      if (p.event.target === this.game.canvas)
        useGameStore.getState().fireHunt();
    });
    this.events.once("shutdown", () =>
      this.input.keyboard?.removeAllListeners(),
    );
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
    const g = useGameStore.getState().huntingGame;
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
      .lineStyle(1, 0xbca66b, 0.65)
      .strokeRect(x - 4, y - 4, R.width * scale + 8, R.height * scale + 8);
    this.aim.clear();
    if (g.phase === "aiming" && g.status === "playing")
      this.aim
        .fillStyle(0x006699)
        .slice(
          x + 10 * scale,
          y + huntGround * scale,
          Math.trunc((g.power * (huntGround >> 1)) / 100) * scale,
          Phaser.Math.DegToRad(-g.angle - 30),
          Phaser.Math.DegToRad(-g.angle),
          false,
        )
        .fillPath();
    this.sprite(this.bow, R.bowSprite, 10, huntGround, x, y, scale);
    g.targets.forEach((t, i) => {
      const p = huntingProfile(t.itemId)!,
        frames =
          t.resting && g.phase !== "attacking"
            ? p.idle
            : t.facing === 1
              ? p.right
              : p.left;
      this.sprite(
        this.targets[i],
        frames[g.frameMs < 600 ? 0 : 1],
        t.x,
        t.y,
        x,
        y,
        scale,
      );
    });
    this.arrows.forEach((image, i) => {
      const p = g.shots[i];
      if (p)
        this.sprite(
          image,
          p.phase === 1 ? R.arrowFrames[p.frame] : R.arrowFrames[5],
          p.x + (p.phase === 1 ? p.offsetX : 0),
          p.y + (p.phase === 1 ? p.offsetY : -7),
          x,
          y,
          scale,
        );
      else image.setVisible(false);
    });
    if (g.phase === "aiming" && g.status === "playing" && g.shots.length < 10)
      this.sprite(
        this.arrows[g.shots.length],
        R.arrowFrames[
          g.angle < 25 ? 3 : g.angle < 35 ? 2 : g.angle < 65 ? 1 : 0
        ],
        10 + (g.angle >= 65 ? -4 : g.angle >= 35 ? -3 : 0),
        huntGround + (g.angle < 25 ? 2 : 0),
        x,
        y,
        scale,
      );
  }
  update(_time: number, delta: number) {
    const s = useGameStore.getState();
    if (!s.huntingGame) {
      this.scene.stop();
      return;
    }
    if (s.panel || document.hidden) {
      this.input.keyboard?.resetKeys();
      return;
    }
    this.elapsed += Math.max(0, Math.min(delta, 180));
    if (this.elapsed >= R.tickMs) {
      const g = s.huntingGame;
      if (g.phase === "aiming" && g.status === "playing") {
        if (this.keys.UP.isDown) s.aimHunt(g.angle, g.power + 2);
        else if (this.keys.DOWN.isDown) s.aimHunt(g.angle, g.power - 2);
        else if (this.keys.LEFT.isDown) s.aimHunt(g.angle + 5, g.power);
        else if (this.keys.RIGHT.isDown) s.aimHunt(g.angle - 5, g.power);
      }
      useGameStore.getState().advanceHunting(this.elapsed);
      this.elapsed = 0;
    }
    this.draw();
  }
}
