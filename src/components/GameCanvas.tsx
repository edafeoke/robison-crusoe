import { useEffect, useRef } from "react";
import Phaser from "phaser";
import { BootScene } from "../game/scenes/BootScene";
import { PreloadScene } from "../game/scenes/PreloadScene";
import { IslandScene } from "../game/scenes/IslandScene";
import { HuntingScene } from "../game/scenes/HuntingScene";
import { RodFishingScene } from "../game/scenes/RodFishingScene";
import { NetFishingScene } from "../game/scenes/NetFishingScene";
import { CoconutScene } from "../game/scenes/CoconutScene";
import { useGameStore } from "../store/gameStore";
export function GameCanvas() {
  const container = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (!container.current) return;
    useGameStore.setState({ ready: false, error: null });
    const game = new Phaser.Game({
      type: Phaser.AUTO,
      parent: container.current,
      backgroundColor: "#258eaa",
      pixelArt: true,
      roundPixels: true,
      render: { antialias: false, powerPreference: "high-performance" },
      scale: {
        mode: Phaser.Scale.RESIZE,
        width: container.current.clientWidth,
        height: container.current.clientHeight,
      },
      scene: [
        BootScene,
        PreloadScene,
        IslandScene,
        CoconutScene,
        NetFishingScene,
        RodFishingScene,
        HuntingScene,
      ],
      audio: { noAudio: true },
      input: { keyboard: { target: window } },
      fps: { target: 60 },
    });
    const resize = new ResizeObserver(() => {
      if (container.current && game.isBooted)
        game.scale.resize(
          container.current.clientWidth,
          container.current.clientHeight,
        );
    });
    resize.observe(container.current);
    return () => {
      resize.disconnect();
      game.destroy(true);
    };
  }, []);
  return (
    <div
      className="game-canvas"
      ref={container}
      aria-label="Robinson's island. Move with WASD or arrow keys, click to walk, E to inspect nearby objects."
      role="application"
    />
  );
}
