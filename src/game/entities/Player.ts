import { raftLayers, type RaftState } from "../systems/RaftSystem";
import { raftRules } from "../data/raftRules";
import Phaser from "phaser";
import type { AtlasManifest } from "../assets/assetManifest";
import { assetManifest } from "../assets/assetManifest";
import { CollisionSystem } from "../systems/CollisionSystem";
import type { Point } from "../systems/MovementSystem";
import {
  clothingLayers,
  emptyOutfit,
  type Outfit,
} from "../systems/ClothingSystem";
export class Player {
  sprite: Phaser.GameObjects.Image;
  shadow: Phaser.GameObjects.Ellipse;
  facing: keyof typeof assetManifest.player.sequences = "down";
  walking = false;
  frame = 0;
  animationTime = 0;
  path: Point[] = [];
  private clothes: Phaser.GameObjects.Image[];
  private outfit: Outfit = emptyOutfit();
  private raft: RaftState | null = null;
  private raftImages: Phaser.GameObjects.Image[];
  private strokeDistance = 0;
  constructor(
    private scene: Phaser.Scene,
    public atlas: AtlasManifest,
    public collision: CollisionSystem,
    public x: number,
    public y: number,
  ) {
    this.shadow = scene.add.ellipse(x, y, 13, 6, 0x112f29, 0.24);
    this.sprite = scene.add.image(x, y, "bank-3", 0).setOrigin(0);
    this.clothes = Array.from({ length: 4 }, () =>
      scene.add.image(x, y, "bank-5", 0).setOrigin(0).setVisible(false),
    );
    this.raftImages = Array.from({ length: 7 }, () =>
      scene.add.image(x, y, "bank-6", 8).setOrigin(0).setVisible(false),
    );
    this.render();
  }
  setRaft(raft: RaftState) {
    this.raft = raft.phase === "aboard" ? { ...raft } : null;
    this.render();
  }
  getRaftFacing() {
    return this.raft?.facing;
  }
  move(dx: number, dy: number, dt: number) {
    this.walking = false;
    if (!dx && !dy) {
      this.frame = 0;
      this.render();
      return;
    }
    if (this.raft) {
      if (Math.abs(dx) > Math.abs(dy)) dy = 0;
      else dx = 0;
      if (dx) this.raft.facing = dx > 0 ? "right" : "left";
    }
    const len = Math.hypot(dx, dy);
    dx /= len;
    dy /= len;
    this.facing =
      Math.abs(dx) > Math.abs(dy)
        ? dx > 0
          ? "right"
          : "left"
        : dy > 0
          ? "down"
          : "up";
    const step = 64 * Math.min(dt, 0.05),
      nx = this.x + dx * step,
      ny = this.y + dy * step;
    const oldX = this.x,
      oldY = this.y;
    if (this.collision.canStand(nx, this.y)) {
      this.walking ||= Math.abs(nx - this.x) > 0.001;
      this.x = nx;
    }
    if (this.collision.canStand(this.x, ny)) {
      this.walking ||= Math.abs(ny - this.y) > 0.001;
      this.y = ny;
    }
    if (this.raft)
      this.strokeDistance += Math.hypot(this.x - oldX, this.y - oldY);
    if (this.walking) {
      this.animationTime += dt * 1000;
      this.frame =
        1 + (Math.floor(this.animationTime / assetManifest.player.frameMs) % 4);
    } else this.frame = 0;
    this.render();
  }
  updatePath(dt: number) {
    const target = this.path[0];
    if (!target) {
      this.move(0, 0, dt);
      return;
    }
    const dx = target.x - this.x,
      dy = target.y - this.y;
    if (Math.hypot(dx, dy) < 2) {
      if (this.raft) this.strokeDistance += Math.hypot(dx, dy);
      this.x = target.x;
      this.y = target.y;
      this.path.shift();
      this.render();
      return;
    }
    this.move(dx, dy, dt);
    if (!this.walking) this.path = [];
  }
  rest(elapsedMs: number) {
    if (this.raft) {
      this.walking = false;
      this.render();
      return;
    }
    this.clothes.forEach((image) => image.setVisible(false));
    this.walking = false;
    const frame = Math.floor(elapsedMs / 700) % 2,
      f = this.atlas.sheets[9].frames[frame];
    this.sprite
      .setTexture("bank-9", frame)
      .setPosition(this.x - f.anchorX, this.y + 8 - f.anchorY)
      .setFlipX(f.flipX)
      .setDepth(this.y + 0.4);
    this.shadow.setPosition(this.x, this.y + 5).setDepth(this.y - 0.1);
  }
  render() {
    if (this.raft) {
      this.sprite.setVisible(false);
      this.clothes.forEach((image) => image.setVisible(false));
      this.shadow
        .setVisible(false)
        .setPosition(this.x, this.y + 5)
        .setDepth(this.y - 0.1);
      const layers = raftLayers(
        this.raft.facing,
        Math.floor(this.strokeDistance / raftRules.sourcePixelsPerStroke),
        this.outfit,
      );
      this.raftImages.forEach((image, i) => {
        const layer = layers[i],
          f =
            layer &&
            this.atlas.sheets[layer.sprite >>> 16]?.frames[
              layer.sprite & 65535
            ];
        image.setVisible(!!f);
        if (f)
          image
            .setTexture(`bank-${layer.sprite >>> 16}`, layer.sprite & 65535)
            .setPosition(
              this.x + layer.dx - f.anchorX,
              this.y + 8 + layer.dy - f.anchorY,
            )
            .setFlipX(f.flipX)
            .setDepth(this.y + 0.4 + i * 0.001);
      });
      return;
    }
    this.raftImages.forEach((image) => image.setVisible(false));
    this.sprite.setVisible(true);
    this.shadow.setVisible(true);
    const frame = assetManifest.player.sequences[this.facing][this.frame],
      f = this.atlas.sheets[3].frames[frame];
    this.sprite
      .setTexture("bank-3", frame)
      .setPosition(this.x - f.anchorX, this.y + 8 - f.anchorY)
      .setFlipX(f.flipX)
      .setDepth(this.y + 0.4);
    this.shadow.setPosition(this.x, this.y + 5).setDepth(this.y - 0.1);
    const layers = clothingLayers(this.outfit, this.facing, this.frame);
    this.clothes.forEach((image, i) => {
      const layer = layers[i],
        frame =
          layer &&
          this.atlas.sheets[layer.sprite >>> 16]?.frames[layer.sprite & 65535];
      image.setVisible(!!frame);
      if (frame)
        image
          .setTexture(`bank-${layer.sprite >>> 16}`, layer.sprite & 65535)
          .setPosition(
            this.x + layer.dx - frame.anchorX,
            this.y + 8 + layer.dy - frame.anchorY,
          )
          .setFlipX(frame.flipX)
          .setDepth(this.y + 0.41 + i * 0.01);
    });
  }
  setOutfit(outfit: Outfit) {
    this.outfit = { ...outfit };
    this.render();
  }
}
