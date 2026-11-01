// Dev-server-only UI fixture. Vite's production entry does not import or ship this page.
// Fixture setup is explicit UI, separate origin, and never changes original world data.
import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore();
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
        <span>Net ×1, Potato ×2 · needs paused · no save imported</span>
        <button
          disabled={!s.ready || !!s.fishingGame || !!s.coconutGame}
          onClick={() =>
            useGameStore.setState({
              started: true,
              x: 200,
              y: 2376,
              inventory: { 83: 1, 26: 2 },
              survival: initialSurvival(false),
              fishingSerial: 0,
              restoreRevision: s.restoreRevision + 1,
              panel: null,
              focus: null,
            })
          }
        >
          Prepare shoreline fixture
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
