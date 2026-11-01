// Explicit dev-only preconditions. Repair, boarding, paddling, landing and saves use the real UI.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialRaft } from "../../src/game/systems/RaftSystem";
import { initialShip } from "../../src/game/systems/ShipSystem";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialClothing } from "../../src/game/systems/ClothingSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
  function prepare() {
    const inventory: Record<number, number> = {};
    for (const r of [...s.items[94].recipe, ...s.items[94].craftingTools])
      inventory[r.item] = r.quantity;
    useGameStore.setState({
      started: true,
      inventory,
      raft: initialRaft(),
      ship: initialShip(),
      survival: initialSurvival(),
      x: 94 * 16 + 8,
      y: 93 * 16 + 8,
      panel: "dialogue",
      selected: 84,
      focus: {
        index: 17014,
        itemId: 84,
        x: 94 * 16 + 8,
        y: 94 * 16 + 8,
        distance: 16,
      },
      collected: [],
      wildlife: undefined,
      clothing: { ...initialClothing(), owned: [99, 112] },
      restoreRevision: s.restoreRevision + 1,
    });
  }
  function inspect() {
    const index =
      s.raft.index ?? Math.floor(s.x / 16) + Math.floor(s.y / 16) * 180;
    useGameStore.setState({
      panel: "dialogue",
      selected: s.raft.phase === "broken" ? 84 : 94,
      focus: {
        index,
        itemId: s.raft.phase === "broken" ? 84 : 94,
        x: (index % 180) * 16 + 8,
        y: Math.floor(index / 180) * 16 + 8,
        distance: Math.hypot(
          (index % 180) * 16 + 8 - s.x,
          Math.floor(index / 180) * 16 + 8 - s.y,
        ),
        ...(s.raft.phase === "aboard" ? { kind: "raft" as const } : {}),
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
        <b>ISOLATED ORIGINAL RAFT FIXTURE</b>
        <span>
          Robinson positioned beside the original broken raft at (94,94). Repair
          materials, Saw and Hammer supplied; T-shirt and Hat in wardrobe.
          Active survival; no full island journey claimed.
        </span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare original raft & supplied materials
        </button>
        <button disabled={!s.ready} onClick={inspect}>
          Inspect the raft
        </button>
        <output role="status">
          Raft {s.raft.phase} · dock {s.raft.index ?? "aboard"} · facing{" "}
          {s.raft.facing} · player ({Math.floor(s.x / 16)},
          {Math.floor(s.y / 16)}) · pixel ({Math.round(s.x)},{Math.round(s.y)})
          · Energy {s.survival.energy} · Morale {s.survival.morale} · original
          cell {s.worldObjects?.[17014]} · current cell{" "}
          {s.worldObjects?.[Math.floor(s.x / 16) + Math.floor(s.y / 16) * 180]}
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
