import Phaser from "phaser";
import { useGameStore } from "../../store/gameStore";
import { coconutRules as R } from "../data/coconutRules";
import { ground } from "../systems/CoconutSystem";
import type { AtlasManifest } from "../assets/assetManifest";

export class CoconutScene extends Phaser.Scene {
  private backdrop!: Phaser.GameObjects.Graphics;
  private aim!: Phaser.GameObjects.Graphics;
  private leaves: Phaser.GameObjects.Image[] = [];
  private targets: Phaser.GameObjects.Image[] = [];
  private stone!: Phaser.GameObjects.Image;
  private atlas!: AtlasManifest;
  private keys!: Record<string, Phaser.Input.Keyboard.Key>;
  private elapsed = 0;
  constructor() {
    super("Coconut");
  }
  create() {
    const s = useGameStore.getState(),
      game = s.coconutGame;
    if (!game || !s.atlas) {
      this.scene.stop();
      return;
    }
    this.atlas = s.atlas;
    this.elapsed = 0;
    this.backdrop = this.add.graphics();
    this.aim = this.add.graphics();
    this.leaves = game.leaves.map(() =>
      this.add.image(0, 0, "bank-7", 53).setOrigin(0),
    );
    this.targets = game.targets.map(() =>
      this.add.image(0, 0, "bank-13", 2).setOrigin(0),
    );
    this.stone = this.add.image(0, 0, "bank-13", 17).setOrigin(0);
    this.keys = this.input.keyboard!.addKeys(
      "UP,DOWN,LEFT,RIGHT",
    ) as typeof this.keys;
    this.input.keyboard!.addCapture(["UP", "DOWN", "LEFT", "RIGHT"]);
    const fire = (e: KeyboardEvent) => {
      const state = useGameStore.getState();
      if (
        !e.repeat &&
        !state.panel &&
        state.coconutGame?.status === "playing"
      ) {
        e.preventDefault();
        state.throwCoconut();
      }
    };
    this.input.keyboard!.on("keydown-SPACE", fire);
    this.input.keyboard!.on("keydown-ENTER", fire);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      if (p.event.target === this.game.canvas)
        useGameStore.getState().throwCoconut();
    });
    this.events.once("shutdown", () =>
      this.input.keyboard?.removeAllListeners(),
    );
    this.renderBoard();
  }
  private region(
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
  private renderBoard() {
    const game = useGameStore.getState().coconutGame;
    if (!game) return;
    const w = this.scale.width,
      h = this.scale.height,
      scale = Math.max(
        0.5,
        Math.min((w - 32) / R.width, (h - 160) / R.height, 2),
      ),
      x = (w - R.width * scale) / 2,
      y = 66;
    this.backdrop
      .clear()
      .fillStyle(0x183d35)
      .fillRect(0, 0, w, h)
      .fillStyle(0x003366)
      .fillRect(x, y, R.width * scale, R.height * scale)
      .lineStyle(1, 0xbca66b, 0.65)
      .strokeRect(x - 4, y - 4, R.width * scale + 8, R.height * scale + 8);
    this.aim.clear();
    if (!game.shot && game.status === "playing")
      this.aim
        .fillStyle(0x006699, 0.8)
        .slice(
          x + 10 * scale,
          y + ground * scale,
          Math.trunc((game.power * (ground >> 1)) / 100) * scale,
          Phaser.Math.DegToRad(-game.angle - 30),
          Phaser.Math.DegToRad(-game.angle),
          false,
        )
        .fillPath();
    game.leaves.forEach((leaf, i) =>
      this.region(this.leaves[i], leaf.sprite, leaf.x, leaf.y, x, y, scale),
    );
    game.targets.forEach((target, i) =>
      this.region(
        this.targets[i],
        R.sprites.coconut,
        target.x + (target.phase === 1 ? [0, -1, 0, 1][game.tick % 4] : 0),
        target.y,
        x,
        y,
        scale,
      ),
    );
    this.region(
      this.stone,
      R.sprites.stone,
      game.shot?.x ?? 10,
      game.shot?.y ?? ground,
      x,
      y,
      scale,
    );
  }
  update(_time: number, delta: number) {
    const s = useGameStore.getState();
    if (!s.coconutGame) {
      this.scene.stop();
      return;
    }
    if (s.panel || document.hidden) return;
    this.elapsed += Math.max(0, Math.min(delta, 180));
    if (this.elapsed >= R.tickMs) {
      const g = s.coconutGame;
      if (!g.shot && g.status === "playing") {
        if (this.keys.LEFT.isDown) s.aimCoconut(g.angle + 5, g.power);
        else if (this.keys.RIGHT.isDown) s.aimCoconut(g.angle - 5, g.power);
        else if (this.keys.UP.isDown) s.aimCoconut(g.angle, g.power + 2);
        else if (this.keys.DOWN.isDown) s.aimCoconut(g.angle, g.power - 2);
      }
      useGameStore.getState().advanceCoconut(this.elapsed);
      this.elapsed = 0;
    }
    this.renderBoard();
  }
}
