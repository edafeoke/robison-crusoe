// Explicit dev-only preconditions; all food stays at its original map cell.
import React, { useEffect, useRef, useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore, type FocusObject } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import type { IslandMap } from "../../src/game/maps/MapDecoder";
import type { WildlifeSnapshot } from "../../src/game/entities/WildlifeState";
import "../../src/styles.css";

function Harness() {
  const s = useGameStore();
  const initial = useRef<WildlifeSnapshot[] | null>(null),
    target = useRef<FocusObject | null>(null),
    armed = useRef(false);
  const [report, setReport] = useState(
    "Prepare a real original food object. Robinson is moved nearby; food is not relocated. Auto-pauses after eating.",
  );
  useEffect(
    () =>
      useGameStore.subscribe((next, prev) => {
        const focus = target.current;
        if (!armed.current || !focus) return;
        if (
          !prev.collected.includes(focus.index) &&
          next.collected.includes(focus.index)
        ) {
          armed.current = false;
          setReport(
            `OBSERVED: food used · hunger ${next.survival.hunger}, thirst ${next.survival.thirst}, energy ${next.survival.energy} · nutrition ${next.bodyWeight.nutrition} · no food added to satchel`,
          );
          useGameStore.setState({ panel: "settings" });
        } else if (!prev.survival.death && next.survival.death) {
          armed.current = false;
          setReport(
            `OBSERVED: ${next.survival.death} collapse · object kept · nutrition ${next.bodyWeight.nutrition}`,
          );
        }
      }),
    [],
  );
  async function prepare(id: 0 | 1 | 27 | 31) {
    const map: IslandMap = await fetch("/assets/maps/island.json").then((r) =>
      r.json(),
    );
    initial.current ??= s.captureWildlife?.() ?? null;
    if (!initial.current) return;
    const collision = new CollisionSystem(map);
    for (let index = 0; index < 32400; index++) {
      if (map.layers.Objects[index] !== id + 1 || map.layers.Decor[index] !== 0)
        continue;
      const ox = (index % 180) * 16 + 8,
        oy = Math.floor(index / 180) * 16 + 8;
      for (const [dx, dy] of [
        [-16, 0],
        [16, 0],
        [0, -16],
        [0, 16],
        [-16, -16],
        [16, 16],
      ]) {
        const x = ox + dx,
          y = oy + dy;
        if (
          !collision.canStand(x, y) ||
          initial.current.some(
            (a) =>
              [43, 44, 49, 50, 56].includes(a.mapId) &&
              Math.abs(Math.floor(a.x / 16) - Math.floor(x / 16)) <= 4 &&
              Math.abs(Math.floor(a.y / 16) - Math.floor(y / 16)) <= 4,
          )
        )
          continue;
        const inventory: Record<number, number> =
          id === 1 ? { 3: 1 } : id === 31 ? { 16: 1 } : {};
        target.current = {
          index,
          itemId: id,
          x: ox,
          y: oy,
          distance: Math.hypot(dx, dy),
        };
        armed.current = false;
        useGameStore.setState({
          started: true,
          panel: "dialogue",
          selected: id,
          focus: target.current,
          x,
          y,
          inventory,
          survival: {
            ...initialSurvival(),
            hunger: 40,
            thirst: 40,
            energy: id === 31 ? 100 : 40,
            stoneUses: id === 1 ? 5 : 0,
          },
          bodyWeight: { barUnits: 0, nutrition: 49 },
          collected: [],
          inspected: [index],
          wildlife: structuredClone(initial.current),
          restoreRevision: s.restoreRevision + 1,
        });
        armed.current = true;
        setReport(
          `READY: original ${s.items[id].name} at (${index % 180},${Math.floor(index / 180)}) · ${id === 1 ? "Stone×1 supplied, opening counter 5/6" : id === 31 ? "Fire×1 supplied, energy 100" : id === 27 ? "no Knife supplied" : "no tools needed"} · nutrition 49 supplied · hunger/thirst 40`,
        );
        return;
      }
    }
    setReport("No safe original target found; nothing was moved.");
  }
  function inspect() {
    if (!target.current) return;
    useGameStore.setState({
      focus: target.current,
      selected: target.current.itemId,
      panel: "dialogue",
    });
    armed.current = true;
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
        <b>ISOLATED ORIGINAL WORLD-FOOD FIXTURE</b>
        <button disabled={!s.ready} onClick={() => prepare(0)}>
          Prepare original Mango
        </button>
        <button disabled={!s.ready} onClick={() => prepare(1)}>
          Prepare original Coconut · Stone supplied
        </button>
        <button disabled={!s.ready} onClick={() => prepare(27)}>
          Prepare original Pineapple · no Knife
        </button>
        <button
          disabled={!s.ready}
          onClick={() =>
            useGameStore.setState({ inventory: { ...s.inventory, 76: 1 } })
          }
        >
          Supply original Knife
        </button>
        <button disabled={!s.ready} onClick={() => prepare(31)}>
          Prepare original Red mushroom · Fire supplied
        </button>
        <button
          disabled={
            !target.current ||
            s.survival.death !== null ||
            s.collected.includes(target.current.index)
          }
          onClick={inspect}
        >
          Inspect prepared object
        </button>
        <span>{report}</span>
        <output role="status">
          Runtime object{" "}
          {target.current ? s.worldObjects?.[target.current.index] : "—"} · body
          bar {s.bodyWeight.barUnits}/64 · nutrition {s.bodyWeight.nutrition} ·
          stone counter {s.survival.stoneUses}/6 · satchel{" "}
          {JSON.stringify(s.inventory)}
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
