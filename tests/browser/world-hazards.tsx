// Visible, dev-only setup. Uses actual original actor IDs and unmodified island layers.
import React, { useEffect, useRef, useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialClothing } from "../../src/game/systems/ClothingSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import type { WildlifeSnapshot } from "../../src/game/entities/WildlifeState";
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import "../../src/styles.css";

function Harness() {
  const s = useGameStore();
  const initial = useRef<WildlifeSnapshot[] | null>(null);
  const armed = useRef(false);
  const [report, setReport] = useState(
    "Prepare an encounter; settings pause the world. Auto-pauses after the first energy change.",
  );
  useEffect(
    () =>
      useGameStore.subscribe((next, prev) => {
        if (!armed.current || next.survival.energy === prev.survival.energy)
          return;
        armed.current = false;
        setReport(
          `OBSERVED: ${prev.survival.energy} → ${next.survival.energy} energy · rest ${next.survival.restRemainingMs === null ? "off" : "active"} · loss ${next.survival.death ?? "none"} · ${next.notification ?? ""}`,
        );
        if (!next.survival.death) useGameStore.setState({ panel: "settings" });
      }),
    [],
  );
  async function prepare(
    mapId: 50 | 56,
    protectedOutfit: boolean,
    energy = 100,
  ) {
    const map: IslandMap = await fetch("/assets/maps/island.json").then((r) =>
      r.json(),
    );
    initial.current ??= s.captureWildlife?.() ?? null;
    if (!initial.current) return;
    const actors = structuredClone(initial.current);
    const objects = [...map.layers.Objects];
    for (const a of actors) objects[a.id] = 0;
    for (const a of actors)
      objects[Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180] = a.mapId;
    const collision = new CollisionSystem(map, new Set(), objects);
    const radius = mapId === 56 ? 2 : 1;
    for (const actor of actors.filter((a) => a.mapId === mapId)) {
      const tx = Math.floor(actor.x / 16),
        ty = Math.floor(actor.y / 16);
      for (let dy = -radius; dy <= radius; dy++)
        for (let dx = -radius; dx <= radius; dx++) {
          const x = (tx + dx) * 16 + 8,
            y = (ty + dy) * 16 + 8;
          if (!collision.canStand(x, y)) continue;
          if (
            actors.some(
              (a) =>
                a.id !== actor.id &&
                [43, 44, 49, 50, 56].includes(a.mapId) &&
                Math.abs(Math.floor(a.x / 16) - tx - dx) <= 2 &&
                Math.abs(Math.floor(a.y / 16) - ty - dy) <= 2,
            )
          )
            continue;
          const clothing = initialClothing();
          if (protectedOutfit) {
            clothing.owned = [112];
            clothing.equipped.hat = 112;
          }
          armed.current = false;
          useGameStore.setState({
            started: true,
            panel: "settings",
            inventory: {},
            collected: [],
            inspected: [],
            focus: null,
            clothing,
            survival: { ...initialSurvival(), energy },
            x,
            y,
            wildlife: actors,
            restoreRevision: s.restoreRevision + 1,
            notification: null,
          });
          armed.current = true;
          setReport(
            `READY: ${mapId === 50 ? "Boa" : "Shark"} original actor ${actor.id} · player (${tx + dx},${ty + dy}) · ${protectedOutfit ? "Hat supplied and worn" : "no clothes"} · energy ${energy}. Close settings or choose Rest a moment; first contact auto-pauses.`,
          );
          return;
        }
    }
    setReport("No safe original encounter found; no map or actor was moved.");
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
        <b>ISOLATED WORLD-CONTACT FIXTURE</b>
        <span>
          Robinson moved to actual original actors. Hat supplied only when
          labelled.
        </span>
        <button disabled={!s.ready} onClick={() => prepare(50, false)}>
          Prepare bare Boa encounter
        </button>
        <button disabled={!s.ready} onClick={() => prepare(50, true)}>
          Prepare protected Boa encounter
        </button>
        <button disabled={!s.ready} onClick={() => prepare(50, true, 60)}>
          Prepare protected rest · energy 60
        </button>
        <button disabled={!s.ready} onClick={() => prepare(56, true)}>
          Prepare original Shark approach
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
