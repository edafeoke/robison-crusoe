import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { SaveSystem, validSave } from "../../src/game/systems/SaveSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore(),
    [report, setReport] = useState(
      "Import only the completed, valid headless milestone. This replaces this isolated QA origin’s checkpoint; no manual browser journey is claimed.",
    );
  async function restore() {
    try {
      const result = await fetch(
        "/evidence/ordered-expedition-audit.json",
      ).then((r) => {
        if (!r.ok) throw new Error("Generate the milestone report first");
        return r.json();
      });
      if (
        result.error !== null ||
        !validSave(result.checkpoint) ||
        result.checkpoint.inventory[22] !== 1 ||
        result.checkpoint.shelter !== 10 ||
        result.checkpoint.ship.phase !== "wreck"
      )
        throw new Error("The earned Sextant checkpoint is incomplete");
      await SaveSystem.save(result.checkpoint);
      await useGameStore.getState().load();
      useGameStore.getState().setPanel("settings");
      setReport(
        `RESTORED: earned headless checkpoint at ${result.checkpoint.elapsed.toFixed(1)}s, reserves ${result.checkpoint.survival.hunger}/${result.checkpoint.survival.thirst}/${result.checkpoint.survival.energy}, Cabin and Sextant. This fixture did not grant repair materials or synthesize progress.`,
      );
    } catch (error) {
      setReport(String(error));
    }
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
        <b>EARNED EXPEDITION CHECKPOINT · DEV ONLY</b>
        <span>
          Items and progression were earned through the ordered headless run.
          This page checks normal IndexedDB/Phaser/UI restoration.
        </span>
        <button disabled={!s.ready || s.saving} onClick={restore}>
          Restore earned Sextant checkpoint
        </button>
        <output role="status">{report}</output>
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
