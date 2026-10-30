import Phaser from "phaser";
import type { AtlasManifest, RecoveredItem } from "../assets/assetManifest";
import type { IslandMap } from "./MapDecoder";
// Animated BS11 shoreline borders are drawn separately using the recovered data.c cycle.
const ANIMATED_BORDERS = new Set([5, 6, 7, 17, 18, 19, 20, 21, 22]);
export function drawRegion(
  ctx: CanvasRenderingContext2D,
  scene: Phaser.Scene,
  atlas: AtlasManifest,
  key: number,
  x: number,
  y: number,
) {
  if (key < 0) return;
  const bank = key >>> 16,
    frame = atlas.sheets[bank]?.frames[key & 65535];
  if (!frame) return;
  const image = scene.textures
    .get(`bank-${bank}`)
    .getSourceImage() as HTMLImageElement;
  const dx = x - frame.anchorX,
    dy = y - frame.anchorY;
  ctx.save();
  if (frame.flipX) {
    ctx.translate(dx + frame.width, dy);
    ctx.scale(-1, 1);
    ctx.drawImage(
      image,
      frame.x,
      frame.y,
      frame.width,
      frame.height,
      0,
      0,
      frame.width,
      frame.height,
    );
  } else
    ctx.drawImage(
      image,
      frame.x,
      frame.y,
      frame.width,
      frame.height,
      dx,
      dy,
      frame.width,
      frame.height,
    );
  ctx.restore();
}
export function makeRegion(
  scene: Phaser.Scene,
  atlas: AtlasManifest,
  key: number,
  x: number,
  y: number,
  depth: number,
): Phaser.GameObjects.Image | null {
  if (key < 0) return null;
  const bank = key >>> 16,
    f = atlas.sheets[bank]?.frames[key & 65535];
  if (!f) return null;
  return scene.add
    .image(x - f.anchorX, y - f.anchorY, `bank-${bank}`, key & 65535)
    .setOrigin(0)
    .setFlipX(f.flipX)
    .setDepth(depth);
}
export class MapRenderer {
  objectSprites = new Map<number, Phaser.GameObjects.Image>();
  constructor(
    private scene: Phaser.Scene,
    private map: IslandMap,
    private atlas: AtlasManifest,
    private items: RecoveredItem[],
  ) {}
  render(removed: Set<number>, managed = new Set<number>()) {
    const { scene, map, atlas } = this;
    for (let cy = 0; cy < 6; cy++)
      for (let cx = 0; cx < 6; cx++) {
        const key = `ground-${cx}-${cy}`;
        if (scene.textures.exists(key)) {
          scene.add
            .image(cx * 480, cy * 480, key)
            .setOrigin(0)
            .setDepth(-100);
          continue;
        }
        const texture = scene.textures.createCanvas(key, 480, 480);
        if (!texture) throw new Error("Unable to allocate terrain texture");
        const ctx = texture.context;
        ctx.imageSmoothingEnabled = false;
        for (let y = 0; y < 30; y++)
          for (let x = 0; x < 30; x++) {
            const i = cx * 30 + x + (cy * 30 + y) * 180;
            drawRegion(
              ctx,
              scene,
              atlas,
              65536 + map.layers.FULL[i],
              x * 16,
              y * 16,
            );
          }
        // Border circles extend outside a tile: include neighbors so chunk boundaries are seamless.
        for (let y = -2; y < 32; y++)
          for (let x = -2; x < 32; x++) {
            const tx = cx * 30 + x,
              ty = cy * 30 + y;
            if (tx < 0 || ty < 0 || tx >= 180 || ty >= 180) continue;
            const i = tx + ty * 180,
              r = map.layers.Rocks[i];
            if (r > 0 && r < 50)
              drawRegion(ctx, scene, atlas, 262144 + r - 1, x * 16, y * 16);
          }
        for (let y = -2; y < 32; y++)
          for (let x = -2; x < 32; x++) {
            const tx = cx * 30 + x,
              ty = cy * 30 + y;
            if (tx < 0 || ty < 0 || tx >= 180 || ty >= 180) continue;
            const b = map.layers.Borders[tx + ty * 180];
            if (b && !ANIMATED_BORDERS.has(b))
              drawRegion(
                ctx,
                scene,
                atlas,
                atlas.borders[b - 1],
                x * 16 + 8,
                y * 16 + 8,
              );
          }
        texture.refresh();
        scene.add
          .image(cx * 480, cy * 480, texture.key)
          .setOrigin(0)
          .setDepth(-100);
      }
    for (let i = 0; i < 32400; i++) {
      const x = (i % 180) * 16 + 8,
        y = Math.floor(i / 180) * 16 + 8,
        d = map.layers.Decor[i];
      if (d) {
        makeRegion(scene, atlas, atlas.decor[d], x, y, y);
        const variants = atlas.variants[d];
        if (variants) {
          const variant = variants[(i * 17 + d) % variants.length];
          for (const [n, c] of atlas.composites[variant].entries())
            makeRegion(
              scene,
              atlas,
              c.sprite,
              x + c.dx,
              y + c.dy,
              y + 0.001 * (n + 1),
            );
        }
      }
      const obj = map.layers.Objects[i];
      if (obj && !removed.has(i) && !managed.has(i)) {
        const sprite = makeRegion(
          scene,
          atlas,
          this.items[obj - 1].sprite,
          x,
          y,
          y + 0.5,
        );
        if (sprite) this.objectSprites.set(i, sprite);
      }
    }
  }
  remove(index: number) {
    this.objectSprites.get(index)?.destroy();
    this.objectSprites.delete(index);
  }
}
