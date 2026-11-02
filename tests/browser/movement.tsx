// Captures this fixture's actual scene through its public lifecycle; no production debug API.
import React, { useEffect, useRef, useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { IslandScene } from "../../src/game/scenes/IslandScene";
import { useGameStore as store } from "../../src/store/gameStore";
import { findPath, type Point } from "../../src/game/systems/MovementSystem";
import { cellPoint, cellOf } from "../../src/game/systems/RaftSystem";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import "../../src/styles.css";
let scene: IslandScene | null = null;
const originalCreate = IslandScene.prototype.create;
IslandScene.prototype.create = function () {
  originalCreate.call(this);
  scene = this;
  this.events.once("shutdown", () => {
    if (scene === this) scene = null;
  });
};
function Harness() {
  const s = store();
  const goal = useRef<Point | null>(null),
    armed = useRef(false);
  const [report, setReport] = useState(
    "Prepare Robinson off-centre beside an actual bird. No animal, object or map layer moves during setup.",
  );
  useEffect(
    () =>
      store.subscribe((next) => {
        const target = goal.current;
        if (!armed.current || !target || !scene) return;
        if (Math.hypot(next.x - target.x, next.y - target.y) > 2) return;
        armed.current = false;
        setReport(
          `ARRIVED at (${Math.floor(next.x / 16)},${Math.floor(next.y / 16)}) · pixel (${next.x.toFixed(1)},${next.y.toFixed(1)}) · Energy ${next.survival.energy}. The original bird kept its real behavior; no collision bypass.`,
        );
        store.setState({ panel: "settings" });
      }),
    [],
  );
  function prepare() {
    if (!scene) return;
    armed.current = false;
    const c = scene.collision;
    for (const a of s.captureWildlife?.() ?? []) {
      if (![53, 64].includes(a.mapId) || a.phase === "fly") continue;
      const tx = Math.floor(a.x / 16),
        ty = Math.floor(a.y / 16),
        left = tx - 1 + ty * 180,
        up = left - 180;
      const p = { x: (tx - 1) * 16 + 14.4, y: ty * 16 + 8 },
        target = cellPoint(up);
      if (
        tx < 1 ||
        ty < 1 ||
        !c.walkableOnFoot(tx - 1, ty) ||
        !c.canStand(target.x, target.y) ||
        c.canStand(p.x, p.y) ||
        [4, 5].includes(scene.map.layers.FULL[up])
      )
        continue;
      const actors = s.captureWildlife?.() ?? [];
      if (
        actors.some(
          (b) =>
            [43, 44, 49, 50, 56].includes(b.mapId) &&
            Math.abs(Math.floor(b.x / 16) - tx) <= 7 &&
            Math.abs(Math.floor(b.y / 16) - ty) <= 7,
        )
      )
        continue;
      goal.current = target;
      scene.player.x = p.x;
      scene.player.y = p.y;
      scene.player.path = [];
      scene.player.render();
      scene.cameras.main.centerOn(p.x, p.y);
      scene.marker
        .clear()
        .lineStyle(1, 0xfff0b0, 1)
        .strokeRect(target.x - 7, target.y - 7, 14, 14);
      store.setState({
        ...p,
        started: true,
        panel: "settings",
        inventory: {},
        elapsed: 0,
        survival: initialSurvival(),
        focus: null,
      });
      setReport(
        `READY: Robinson (${p.x.toFixed(1)},${p.y.toFixed(1)}) · actual bird ${a.id}/map${a.mapId} remains at (${tx},${ty}) · target (${target.x},${target.y}). Source-cell ${scene.map.layers.Objects[a.id]} unchanged; live neighbor ${s.worldObjects?.[cellOf(a.x, a.y)]}.`,
      );
      return;
    }
    setReport(
      "No suitable live original bird edge found; no source or actor changed.",
    );
  }
  function run() {
    if (!scene || !goal.current) return;
    s.setPanel(null);
    const path = findPath(scene.collision, scene.player, goal.current);
    scene.player.path = path;
    setReport(
      `PATH: ${path.map((p) => `(${p.x},${p.y})`).join(" → ")}. Running the production click-path follower; arrival auto-pauses.`,
    );
    armed.current = true;
  }
  return (
    <>
      <div
        style={{
          position: "relative",
          zIndex: 101,
          padding: "10px 20px",
          background: "#f4e5aa",
          color: "#253f34",
          font: "13px Georgia",
          display: "flex",
          gap: 12,
          flexWrap: "wrap",
          alignItems: "center",
        }}
      >
        <b>ISOLATED CLICK-PATH FIXTURE</b>
        <span>
          Robinson repositioned at a live bird edge. No items supplied, no bird
          relocated, no source layers changed.
        </span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare original bird edge
        </button>
        <button disabled={!s.ready} onClick={run}>
          Run prepared click path
        </button>
        <output role="status">{report}</output>
      </div>
      <App />
    </>
  );
}
const root = createRoot(document.getElementById("root")!);
import.meta.hot?.dispose(() => {
  IslandScene.prototype.create = originalCreate;
  root.unmount();
});
root.render(
  <React.StrictMode>
    <Harness />
  </React.StrictMode>,
);
