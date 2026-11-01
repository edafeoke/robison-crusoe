// Explicitly labelled dev-only setup. No fixture is imported by the production entry.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
  async function prepare() {
    const map = await fetch("/assets/maps/island.json").then((r) => r.json());
    if (!s.worldObjects) return;
    const index = map.layers.Objects.indexOf(18),
      cx = (index % 180) * 16 + 8,
      cy = Math.floor(index / 180) * 16 + 8;
    const point = new CollisionSystem(
      map,
      new Set(s.collected),
      s.worldObjects,
    ).nearest(cx, cy, 2);
    if (!point) return;
    const inventory: Record<number, number> = {};
    for (const id of [9, 10, 91]) {
      for (const r of s.items[id].recipe)
        inventory[r.item] = (inventory[r.item] ?? 0) + r.quantity;
      for (const r of s.items[id].craftingTools)
        inventory[r.item] = Math.max(inventory[r.item] ?? 0, r.quantity);
    }
    useGameStore.setState({
      ...point,
      inventory,
      shelter: 17,
      survival: initialSurvival(),
      started: true,
      wildlife: s.captureWildlife?.(),
      restoreRevision: s.restoreRevision + 1,
      panel: "dialogue",
      selected: 17,
      focus: {
        index,
        itemId: 17,
        x: cx,
        y: cy,
        distance: Math.hypot(point.x - cx, point.y - cy),
      },
    });
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
        <span>
          Original camp · Tent/Cabin/Rope ingredients & tools supplied ·
          survival active
        </span>
        <button
          disabled={
            !s.ready ||
            !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame)
          }
          onClick={prepare}
        >
          Prepare original camp
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
