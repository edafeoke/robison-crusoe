// Explicit, dev-only supplies. The fixture does not consume food on the player's behalf.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import {
  initialBodyWeight,
  WEIGHT_MAX,
} from "../../src/game/systems/BodyWeightSystem";
import "../../src/styles.css";

function Harness() {
  const s = useGameStore();
  function prepare() {
    useGameStore.setState({
      started: true,
      x: 136,
      y: 2376,
      panel: "inventory",
      focus: null,
      inventory: { 0: 17, 4: 1, 27: 1, 1: 1, 3: 1 },
      bodyWeight: initialBodyWeight(),
      survival: initialSurvival(),
      wildlife: s.captureWildlife?.(),
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
        <b>ISOLATED NUTRITION FIXTURE</b>
        <span>
          Mango×17, Wild goat×1, Pineapple×1, Coconut×1, Stone×1 supplied ·
          survival active.
        </span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare source food & stone
        </button>
        <button
          disabled={!s.ready}
          onClick={() =>
            useGameStore.setState({ inventory: { ...s.inventory, 76: 1 } })
          }
        >
          Supply original Knife
        </button>
        <output role="status">
          Weight {s.bodyWeight.barUnits}/{WEIGHT_MAX} native bar units ·
          nutrition carried forward {s.bodyWeight.nutrition} · hunger{" "}
          {s.survival.hunger} · stone openings {s.survival.stoneUses}/6
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
