// Explicit dev-only materials/position setup. Crafting, repair, departure and saves use the real game UI.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { shipRules as R } from "../../src/game/data/shipRules";
import { initialShip } from "../../src/game/systems/ShipSystem";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
  async function prepare() {
    const map: IslandMap = await fetch("/assets/maps/island.json").then((r) =>
      r.json(),
    );
    const inventory: Record<number, number> = {};
    for (const r of s.items[19].recipe)
      if (r.item !== 54) inventory[r.item] = r.quantity;
    for (const r of [
      ...s.items[19].craftingTools,
      ...s.items[54].craftingTools,
    ])
      inventory[r.item] = Math.max(inventory[r.item] ?? 0, r.quantity);
    for (const r of s.items[54].recipe)
      inventory[r.item] = (inventory[r.item] ?? 0) + r.quantity;
    const ox = (R.index % 180) * 16 + 8,
      oy = Math.floor(R.index / 180) * 16 + 8;
    const collision = new CollisionSystem(
      map,
      new Set(s.collected),
      s.worldObjects ?? undefined,
    );
    const point = collision.nearest(ox, oy, 2);
    if (!point || Math.hypot(point.x - ox, point.y - oy) > 44) return;
    useGameStore.setState({
      started: true,
      inventory,
      ship: initialShip(),
      survival: { ...initialSurvival(), minuteMs: 59500 },
      x: point.x,
      y: point.y,
      panel: "dialogue",
      selected: 18,
      focus: {
        index: R.index,
        itemId: 18,
        x: ox,
        y: oy,
        distance: Math.hypot(point.x - ox, point.y - oy),
      },
      inspected: s.inspected.includes(R.index)
        ? s.inspected
        : [...s.inspected, R.index],
      wildlife: s.captureWildlife?.(),
      restoreRevision: s.restoreRevision + 1,
    });
  }
  function inspect() {
    const x = (R.index % 180) * 16 + 8,
      y = Math.floor(R.index / 180) * 16 + 8;
    useGameStore.setState({
      panel: "dialogue",
      selected: 18,
      focus: {
        index: R.index,
        itemId: 18,
        x,
        y,
        distance: Math.hypot(s.x - x, s.y - y),
      },
    });
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
        <b>ISOLATED SHIP RECONSTRUCTION FIXTURE</b>
        <span>
          Robinson moved beside the original wreck. Reconstruction materials,
          Large sail ingredients and all tools supplied; survival active, next
          reserve tick in 0.5s. No original island journey claimed.
        </span>
        <button
          disabled={!s.ready || s.ship.phase === "departing"}
          onClick={prepare}
        >
          Prepare original wreck & supplied materials
        </button>
        <button
          disabled={!s.ready || s.ship.phase !== "wreck"}
          onClick={inspect}
        >
          Inspect original shipwreck
        </button>
        <output role="status">
          Ship {s.ship.phase} · departure {Math.round(s.ship.elapsedMs)}ms ·
          energy {s.survival.energy} · Large sail {s.inventory[54] ?? 0} ·
          source object {s.worldObjects?.[R.index]} · player (
          {Math.floor(s.x / 16)},{Math.floor(s.y / 16)})
        </output>
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
