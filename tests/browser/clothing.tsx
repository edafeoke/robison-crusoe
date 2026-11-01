// Explicit source-world QA setup; not imported by the production entry.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialClothing } from "../../src/game/systems/ClothingSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
  function prepare() {
    const inventory: Record<number, number> = {};
    for (const id of [99, 104, 109, 112]) {
      for (const r of s.items[id].recipe)
        inventory[r.item] = (inventory[r.item] ?? 0) + r.quantity;
      for (const r of s.items[id].craftingTools)
        inventory[r.item] = Math.max(inventory[r.item] ?? 0, r.quantity);
    }
    useGameStore.setState({
      started: true,
      inventory,
      clothing: initialClothing(),
      shelter: 10,
      survival: initialSurvival(),
      x: 136,
      y: 2376,
      panel: "crafting",
      wildlife: s.captureWildlife?.(),
      restoreRevision: s.restoreRevision + 1,
    });
  }
  async function boa() {
    const map = await fetch("/assets/maps/island.json").then((r) => r.json()),
      actors = s.captureWildlife?.();
    if (!actors || !s.worldObjects) return;
    const collision = new CollisionSystem(
      map,
      new Set(s.collected),
      s.worldObjects,
    );
    for (const a of actors) {
      if (a.mapId !== 50) continue;
      for (const [dx, dy] of [
        [16, 0],
        [-16, 0],
        [0, 16],
        [0, -16],
        [16, 16],
        [-16, -16],
      ]) {
        const ax = Math.floor(a.x / 16) * 16 + 8,
          ay = Math.floor(a.y / 16) * 16 + 8,
          x = ax + dx,
          y = ay + dy;
        if (!collision.canStand(x, y)) continue;
        const index = Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180;
        useGameStore.setState({
          x,
          y,
          inventory: { ...s.inventory, 81: 1, 45: 1 },
          wildlife: actors,
          restoreRevision: s.restoreRevision + 1,
          panel: "dialogue",
          selected: 49,
          focus: {
            index,
            itemId: 49,
            actorId: a.id,
            x: ax,
            y: ay,
            distance: Math.hypot(dx, dy),
          },
        });
        return;
      }
    }
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
          gap: 16,
          alignItems: "center",
          flexWrap: "wrap",
        }}
      >
        <b>ISOLATED UI TEST FIXTURE</b>
        <span>
          Cabin + T-shirt/Loincloth/Straw shoe/Hat ingredients & tools supplied
          · survival active
        </span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare garment ingredients
        </button>
        <button disabled={!s.ready || !!s.huntingGame} onClick={boa}>
          Prepare original boa encounter · Bow & Arrow supplied
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
