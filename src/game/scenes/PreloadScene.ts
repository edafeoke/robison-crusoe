import Phaser from "phaser";
import type { AtlasManifest } from "../assets/assetManifest";
import { useGameStore } from "../../store/gameStore";
export class PreloadScene extends Phaser.Scene {
  constructor() {
    super("Preload");
  }
  preload() {
    const atlas = this.cache.json.get("atlas-manifest") as AtlasManifest;
    for (const sheet of atlas.sheets)
      this.load.image(`bank-${sheet.bank}`, sheet.url);
    this.load.on("loaderror", () =>
      useGameStore.setState({
        error: "An original sprite sheet is unavailable. Reload to try again.",
      }),
    );
  }
  create() {
    if (useGameStore.getState().error) return;
    const atlas = this.cache.json.get("atlas-manifest") as AtlasManifest;
    for (const s of atlas.sheets) {
      const t = this.textures.get(`bank-${s.bank}`);
      s.frames.forEach((f, i) => t.add(i, 0, f.x, f.y, f.width, f.height));
    }
    useGameStore.setState({
      atlas,
      items: this.cache.json.get("objects"),
      story: this.cache.json.get("story"),
    });
    this.scene.start("Island");
  }
}
