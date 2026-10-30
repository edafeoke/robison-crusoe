import Phaser from "phaser";
import { assetManifest, type AtlasManifest } from "../assets/assetManifest";
import { useGameStore } from "../../store/gameStore";
export class BootScene extends Phaser.Scene {
  constructor() {
    super("Boot");
  }
  preload() {
    this.load.json("atlas-manifest", assetManifest.atlas);
    this.load.json("island", assetManifest.map);
    this.load.json("objects", assetManifest.objects);
    this.load.json("story", assetManifest.story);
    this.load.json("wildlife", assetManifest.wildlife);
    this.load.on("loaderror", () =>
      useGameStore.setState({
        error: "The island files could not be loaded. Reload to try again.",
      }),
    );
  }
  create() {
    if (useGameStore.getState().error) return;
    const atlas = this.cache.json.get("atlas-manifest") as AtlasManifest;
    if (!atlas?.sheets) {
      useGameStore.setState({
        error: "The original sprite manifest is missing.",
      });
      return;
    }
    this.scene.start("Preload");
  }
}
