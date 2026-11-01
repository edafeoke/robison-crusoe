import React from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import { initialResourceHistory } from "../../src/game/systems/ResourceHistorySystem";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { initialRaft } from "../../src/game/systems/RaftSystem";
import { initialShip } from "../../src/game/systems/ShipSystem";
import { worldFoodProfile } from "../../src/game/systems/WorldConsumptionSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore(),
    sail = s.worldMap?.layers.Objects.indexOf(13) ?? -1;
  function visit(index: number) {
    const current = useGameStore.getState();
    if (!current.worldMap || !current.worldObjects?.[index]) return;
    const c = new CollisionSystem(
      current.worldMap,
      new Set(current.collected),
      current.worldObjects,
    );
    const x = (index % 180) * 16 + 8,
      y = Math.floor(index / 180) * 16 + 8,
      point = c.nearest(x, y, 2);
    if (!point || Math.hypot(point.x - x, point.y - y) > 44) return;
    useGameStore.setState({
      ...point,
      panel: "dialogue",
      selected: current.worldObjects[index] - 1,
      focus: {
        index,
        itemId: current.worldObjects[index] - 1,
        x,
        y,
        distance: Math.hypot(point.x - x, point.y - y),
      },
      wildlife: current.captureWildlife?.(),
      restoreRevision: current.restoreRevision + 1,
    });
  }
  function prepare() {
    useGameStore.setState({
      started: true,
      inventory: {},
      collected: [],
      resources: initialResourceHistory(),
      survival: initialSurvival(),
      raft: initialRaft(),
      ship: initialShip(),
      elapsed: 0,
      wildlife: undefined,
      panel: "settings",
      focus: null,
      x: 8 * 16 + 8,
      y: 148 * 16 + 8,
      restoreRevision: s.restoreRevision + 1,
    });
  }
  function fruit() {
    const current = useGameStore.getState();
    if (!current.worldMap || !current.worldObjects) return;
    const c = new CollisionSystem(
      current.worldMap,
      new Set(current.collected),
      current.worldObjects,
    );
    for (let index = 0; index < 32400; index++) {
      const id = current.worldObjects[index] - 1,
        item = current.items[id];
      if (
        !item ||
        item.effects.hunger <= 0 ||
        item.effects.energy <= 0 ||
        !worldFoodProfile(id)
      )
        continue;
      const x = (index % 180) * 16 + 8,
        y = Math.floor(index / 180) * 16 + 8,
        p = c.nearest(x, y, 2);
      if (!p || Math.hypot(p.x - x, p.y - y) > 44) continue;
      const tx = Math.floor(p.x / 16),
        ty = Math.floor(p.y / 16);
      let danger = false;
      for (let dy = -6; dy <= 6; dy++)
        for (let dx = -6; dx <= 6; dx++)
          if (
            [43, 44, 49, 50, 56].includes(
              current.worldObjects[tx + dx + (ty + dy) * 180],
            ) ||
            [43, 44, 49, 50, 56].includes(
              current.worldMap.layers.Objects[tx + dx + (ty + dy) * 180],
            )
          )
            danger = true;
      if (!danger) {
        visit(index);
        return;
      }
    }
  }
  function tide() {
    const elapsed = Math.floor(s.elapsed / 360) * 360 + 358;
    useGameStore.setState({
      elapsed,
      x: 136,
      y: 2376,
      panel: "settings",
      focus: null,
      survival: {
        ...s.survival,
        minutes: Math.floor(elapsed / 60),
        minuteMs: 58000,
      },
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
        <b>ISOLATED SOURCE SUPPLY RENEWAL</b>
        <span>
          Moves Robinson to original source cells and prepares a clock boundary.
          No items, pickups or history supplied; use actual Gather controls.
          Active survival. No full island journey claimed.
        </span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare empty expedition
        </button>
        <button
          disabled={!s.ready || s.worldObjects?.[sail] !== 13}
          onClick={() => visit(sail)}
        >
          Visit original Sail
        </button>
        <button disabled={!s.ready} onClick={fruit}>
          Visit next original food
        </button>
        <button disabled={!s.ready} onClick={tide}>
          Prepare next tide boundary
        </button>
        <output role="status">
          Sail {s.inventory[12] ?? 0} · recent {s.resources?.recent.length ?? 0}
          /20 · long history {s.resources?.permanent.length ?? 0}/70 · Sail
          recent {s.resources?.recent.includes(sail) ? "yes" : "no"} · Sail cell{" "}
          {s.worldObjects?.[sail]} · removed {s.collected.length} · Energy{" "}
          {s.survival.energy}
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
