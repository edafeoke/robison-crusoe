// Explicit dev-only preconditions. Never moves an animal or changes source layers.
import React, { useEffect, useRef, useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialShip } from "../../src/game/systems/ShipSystem";
import { initialResourceHistory } from "../../src/game/systems/ResourceHistorySystem";
import { initialClothing } from "../../src/game/systems/ClothingSystem";
import "../../src/styles.css";
const actorId = 143 + 65 * 180;
function Harness() {
  const s = useGameStore();
  const observation = useRef<{
    start: number;
    energy: number;
    samples: number;
    near: number;
  } | null>(null);
  const [report, setReport] = useState(
    "Prepare the labelled afloat checkpoint, then observe. World auto-pauses after 2.5 active seconds or the first Energy change.",
  );
  useEffect(
    () =>
      useGameStore.subscribe((next) => {
        const o = observation.current;
        if (!o || next.panel || !next.started) return;
        const actor = next.captureWildlife?.().find((a) => a.id === actorId);
        if (actor) {
          o.samples++;
          if (
            Math.abs(Math.floor(actor.x / 16) - Math.floor(next.x / 16)) <= 1 &&
            Math.abs(Math.floor(actor.y / 16) - Math.floor(next.y / 16)) <= 1
          )
            o.near++;
        }
        const dt = next.elapsed - o.start;
        if (dt < 2.5 && next.survival.energy === o.energy) return;
        observation.current = null;
        setReport(
          `OBSERVED ${next.raft.phase}: ${dt.toFixed(1)} active seconds · Energy ${o.energy} → ${next.survival.energy} · adjacent samples ${o.near}/${o.samples} · Alligator (${actor ? Math.floor(actor.x / 16) : "?"},${actor ? Math.floor(actor.y / 16) : "?"}) exposure ${actor?.contactMs ?? "?"} · loss ${next.survival.death ?? "none"}.`,
        );
        if (!next.survival.death) useGameStore.setState({ panel: "settings" });
      }),
    [],
  );
  function prepare(minuteBoundary = false) {
    observation.current = null;
    const elapsed = minuteBoundary ? 59.8 : 0;
    useGameStore.setState({
      started: true,
      panel: "settings",
      inventory: {},
      collected: [],
      resources: initialResourceHistory(),
      elapsed,
      survival: initialSurvival(true, elapsed * 1000),
      ship: initialShip(),
      raft: { phase: "aboard", index: null, facing: "left" },
      x: 142 * 16 + 8,
      y: 64 * 16 + 8,
      wildlife: undefined,
      clothing: initialClothing(),
      coconutGame: null,
      rodGame: null,
      fishingGame: null,
      huntingGame: null,
      focus: null,
      selected: null,
      notification: null,
      restoreRevision: s.restoreRevision + 1,
    });
    setReport(
      `READY: supplied repaired raft aboard at original clear swamp (142,64); original Alligator ${actorId} at (143,65). Empty inventory, no clothes, active survival, elapsed ${elapsed}s. No animal moved or acquisition journey claimed.`,
    );
  }
  function observe() {
    const current = useGameStore.getState();
    observation.current = {
      start: current.elapsed,
      energy: current.survival.energy,
      samples: 0,
      near: 0,
    };
    setReport(
      "OBSERVING actual scene updates; auto-pauses after 2.5 active seconds or the first Energy change.",
    );
    current.setPanel(null);
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
        <b>ISOLATED RAFT-CONTACT FIXTURE</b>
        <span>
          Supplied repaired raft and repositioned Robinson. Original Alligator
          stays at its original placement. Observation never changes reserves or
          animal behavior.
        </span>
        <button disabled={!s.ready} onClick={() => prepare()}>
          Prepare afloat beside original Alligator
        </button>
        <button disabled={!s.ready} onClick={() => prepare(true)}>
          Prepare afloat at minute boundary
        </button>
        <button disabled={!s.ready || !!s.survival.death} onClick={observe}>
          Observe next live interval
        </button>
        <output role="status">
          {report} Player ({Math.floor(s.x / 16)},{Math.floor(s.y / 16)}) · raft{" "}
          {s.raft.phase} · Energy {s.survival.energy} · time{" "}
          {s.elapsed.toFixed(1)}.
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
