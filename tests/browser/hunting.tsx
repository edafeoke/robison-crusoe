// Dev-only, explicitly prepared original-world encounter; never imported by the production entry.
import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import { huntingProfile } from "../../src/game/systems/HuntingSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore(),
    [message, setMessage] = useState(
      "Bow ×1 · Arrow ×2 · needs paused · deterministic hunt seed 1",
    );
  async function prepare() {
    const map = await fetch("/assets/maps/island.json").then((r) => r.json()),
      actors = s.captureWildlife?.();
    if (!actors || !s.worldObjects) return;
    const c = new CollisionSystem(map, new Set(s.collected), s.worldObjects);
    for (const a of [...actors].sort(
      (a, b) => Number(b.mapId === 5) - Number(a.mapId === 5),
    )) {
      if (a.phase === "fly" || !huntingProfile(a.mapId - 1)) continue;
      for (const [dx, dy] of [
        [16, 0],
        [-16, 0],
        [0, 16],
        [0, -16],
        [16, 16],
        [-16, -16],
      ]) {
        const x = Math.floor(a.x / 16) * 16 + 8 + dx,
          y = Math.floor(a.y / 16) * 16 + 8 + dy;
        if (!c.canStand(x, y)) continue;
        const index = Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180,
          itemId = a.mapId - 1;
        useGameStore.setState({
          started: true,
          x,
          y,
          inventory: { 81: 1, 45: 2 },
          survival: initialSurvival(false),
          wildlife: actors,
          huntingSerial: (1 - (a.id + 1) * 2654435761) >>> 0,
          restoreRevision: s.restoreRevision + 1,
          panel: "dialogue",
          selected: itemId,
          focus: {
            index,
            itemId,
            actorId: a.id,
            x: Math.floor(a.x / 16) * 16 + 8,
            y: Math.floor(a.y / 16) * 16 + 8,
            distance: Math.hypot(dx, dy),
          },
        });
        setMessage(
          `${s.items[itemId].name} · original cell ${a.id} · Bow ×1 / Arrow ×2 · hunt seed 1`,
        );
        return;
      }
    }
    setMessage("No nearby supported encounter with walkable footing.");
  }
  return (
    <>
      <div
        style={{
          padding: "10px 20px",
          background: "#f4e5aa",
          color: "#253f34",
          font: "13px Georgia",
          display: "flex",
          gap: 20,
          alignItems: "center",
        }}
      >
        <b>ISOLATED UI TEST FIXTURE</b>
        <span>{message}</span>
        <button
          disabled={
            !s.ready ||
            !!(s.huntingGame || s.coconutGame || s.fishingGame || s.rodGame)
          }
          onClick={prepare}
        >
          Prepare original animal encounter
        </button>
      </div>
      <App />
    </>
  );
}
const root = createRoot(document.getElementById("root")!);
import.meta.hot?.dispose(() => root.unmount());
root.render(
  <React.StrictMode>
    <Harness />
  </React.StrictMode>,
);
