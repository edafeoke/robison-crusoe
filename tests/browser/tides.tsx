import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialRaft } from "../../src/game/systems/RaftSystem";
import { initialShip } from "../../src/game/systems/ShipSystem";
import { tideAt } from "../../src/game/systems/TideSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
  function prepare(rising: boolean) {
    const elapsed = rising ? 718 : 358;
    useGameStore.setState({
      started: true,
      elapsed,
      x: (rising ? 44 : 41) * 16 + 8,
      y: 169 * 16 + 8,
      inventory: {},
      collected: [],
      wildlife: undefined,
      raft: initialRaft(),
      ship: initialShip(),
      survival: initialSurvival(true, elapsed * 1000),
      panel: "settings",
      focus: null,
      restoreRevision: s.restoreRevision + 1,
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
        <b>ISOLATED ORIGINAL TIDE CROSSING</b>
        <span>
          Robinson positioned at the original tidal passage near (42,169); clock
          two seconds before a tide change. No items supplied or objects moved.
          No full spawn journey claimed.
        </span>
        <button disabled={!s.ready} onClick={() => prepare(false)}>
          Prepare falling tide at passage
        </button>
        <button disabled={!s.ready} onClick={() => prepare(true)}>
          Prepare rising tide on flats
        </button>
        <output role="status">
          Tide {tideAt(s.elapsed)} · clock {s.elapsed.toFixed(1)}s · player (
          {Math.floor(s.x / 16)},{Math.floor(s.y / 16)}) · gate source5 / active
          {s.worldTerrain?.[42 + 169 * 180]} · border source17 / active
          {s.worldMap?.layers.Borders[41 + 169 * 180]} · Saw{" "}
          {s.inventory[78] ?? 0} · Energy {s.survival.energy}
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
