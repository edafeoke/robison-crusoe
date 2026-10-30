import Phaser from "phaser";
import { validateMap, type IslandMap } from "../maps/MapDecoder";
import { MapRenderer } from "../maps/MapRenderer";
import { CollisionSystem } from "../systems/CollisionSystem";
import { findPath } from "../systems/MovementSystem";
import { nearestObject } from "../systems/InteractionSystem";
import { AnimationSystem } from "../systems/AnimationSystem";
import { Player } from "../entities/Player";
import { useGameStore } from "../../store/gameStore";
export class IslandScene extends Phaser.Scene {
  player!: Player;
  world!: MapRenderer;
  map!: IslandMap;
  collision!: CollisionSystem;
  keys!: Record<string, Phaser.Input.Keyboard.Key>;
  marker!: Phaser.GameObjects.Graphics;
  ring!: Phaser.GameObjects.Ellipse;
  private syncTime = 0;
  private removed = new Set<number>();
  private unsubscribe?: () => void;
  private discovered = new Set<number>();
  constructor() {
    super("Island");
  }
  create() {
    try {
      this.map = validateMap(this.cache.json.get("island"));
    } catch (error) {
      useGameStore.setState({ error: (error as Error).message });
      return;
    }
    const state = useGameStore.getState();
    this.removed = new Set(state.collected);
    this.discovered = new Set(state.discovered);
    this.collision = new CollisionSystem(this.map, this.removed);
    this.world = new MapRenderer(this, this.map, state.atlas!, state.items);
    this.world.render(this.removed);
    new AnimationSystem(this, this.map, state.atlas!);
    const spawn = this.collision.canStand(state.x, state.y)
      ? { x: state.x, y: state.y }
      : this.collision.nearest(state.x, state.y)!;
    this.player = new Player(
      this,
      state.atlas!,
      this.collision,
      spawn.x,
      spawn.y,
    );
    this.cameras.main
      .setBounds(0, 0, 2880, 2880)
      .setZoom(state.zoom)
      .startFollow(this.player.shadow, true, 0.14, 0.14);
    this.cameras.main.setRoundPixels(true);
    this.keys = this.input.keyboard!.addKeys(
      "W,A,S,D,UP,DOWN,LEFT,RIGHT,E",
    ) as typeof this.keys;
    this.input.keyboard!.addCapture([
      "W",
      "A",
      "S",
      "D",
      "UP",
      "DOWN",
      "LEFT",
      "RIGHT",
      "E",
    ]);
    this.input.keyboard!.on("keydown-E", () => {
      const s = useGameStore.getState();
      if (!s.panel && s.focus) s.inspect(s.focus.index, s.focus.itemId);
    });
    this.marker = this.add.graphics().setDepth(4000);
    this.ring = this.add
      .ellipse(0, 0, 20, 10)
      .setStrokeStyle(1, 0xfff0b0, 0.9)
      .setDepth(3500)
      .setVisible(false);
    this.input.on("pointerdown", (p: Phaser.Input.Pointer) => {
      const s = useGameStore.getState();
      if (s.panel || !s.started) return;
      const world = p.positionToCamera(
        this.cameras.main,
      ) as Phaser.Math.Vector2;
      const object = nearestObject(
        this.map,
        world.x,
        world.y,
        this.removed,
        13,
      );
      if (
        object &&
        Math.hypot(object.x - this.player.x, object.y - this.player.y) < 44
      ) {
        useGameStore.setState({ focus: object });
        s.inspect(object.index, object.itemId);
        return;
      }
      const dest = this.collision.nearest(world.x, world.y, 3);
      if (!dest) {
        s.notify("The water and rocks block the way.");
        return;
      }
      this.player.path = findPath(this.collision, this.player, dest);
      if (!this.player.path.length) {
        s.notify("No clear path from here.");
        return;
      }
      this.marker
        .clear()
        .lineStyle(1, 0xffecad, 0.85)
        .strokeCircle(dest.x, dest.y, 4);
      this.tweens.add({
        targets: this.marker,
        alpha: 0,
        duration: 1200,
        onComplete: () => this.marker.clear(),
      });
      this.marker.alpha = 1;
    });
    this.unsubscribe = useGameStore.subscribe((s, prev) => {
      if (s.zoom !== prev.zoom) this.cameras.main.setZoom(s.zoom);
      if (s.collected !== prev.collected) {
        // Loading restores removed objects too; rebuild safely only when the collection shrinks or differs.
        if (prev.collected.some((i) => !s.collected.includes(i))) {
          this.scene.restart();
          return;
        }
        for (const i of s.collected) {
          this.removed.add(i);
          this.world.remove(i);
        }
      }
      if (s.restoreRevision !== prev.restoreRevision) {
        this.scene.restart();
        return;
      }
      if (s.panel !== prev.panel) {
        this.player.path = [];
        if (s.panel) this.input.keyboard?.resetKeys();
      }
    });
    this.events.once("shutdown", () => {
      this.unsubscribe?.();
      this.input.keyboard?.removeAllListeners();
    });
    useGameStore.setState({
      ready: true,
      x: spawn.x,
      y: spawn.y,
      worldObjects: this.map.layers.Objects,
    });
  }
  update(_time: number, delta: number) {
    if (!this.player) return;
    const s = useGameStore.getState();
    if (s.panel || !s.started) {
      this.player.move(0, 0, 0);
      return;
    }
    const dx =
        Number(this.keys.D.isDown || this.keys.RIGHT.isDown) -
        Number(this.keys.A.isDown || this.keys.LEFT.isDown),
      dy =
        Number(this.keys.S.isDown || this.keys.DOWN.isDown) -
        Number(this.keys.W.isDown || this.keys.UP.isDown);
    if (dx || dy) {
      this.player.path = [];
      this.player.move(dx, dy, delta / 1000);
    } else this.player.updatePath(delta / 1000);
    this.syncTime += delta;
    if (this.syncTime > 100) {
      const elapsed = s.elapsed + this.syncTime / 1000;
      this.syncTime = 0;
      const tx = Math.floor(this.player.x / 16),
        ty = Math.floor(this.player.y / 16);
      for (let y = -5; y <= 5; y++)
        for (let x = -5; x <= 5; x++) {
          const ax = tx + x,
            ay = ty + y;
          if (ax >= 0 && ay >= 0 && ax < 180 && ay < 180)
            this.discovered.add(ax + ay * 180);
        }
      const focus = nearestObject(
        this.map,
        this.player.x,
        this.player.y,
        this.removed,
      );
      this.ring.setVisible(!!focus);
      if (focus) this.ring.setPosition(focus.x, focus.y + 2);
      useGameStore.setState({
        x: this.player.x,
        y: this.player.y,
        elapsed,
        focus,
        discovered: [...this.discovered],
      });
    }
  }
}
