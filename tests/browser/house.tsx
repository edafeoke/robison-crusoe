import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";
import { useGameStore } from "../../src/store/gameStore";
import { initialSurvival } from "../../src/game/systems/SurvivalSystem";
import { FURNISHING_IDS } from "../../src/game/systems/HouseSystem";
import { CollisionSystem } from "../../src/game/systems/CollisionSystem";
import { validSave } from "../../src/game/systems/SaveSystem";
import "../../src/styles.css";
function Harness() {
  const s = useGameStore(),
    [message, setMessage] = useState(
      "Cabin + furnishings ingredients/tools supplied · reserves 40/50/90",
    );
  async function prepare() {
    const map = await fetch("/assets/maps/island.json").then((r) => r.json());
    if (!s.worldObjects) return;
    const i = map.layers.Objects.indexOf(18),
      point = new CollisionSystem(
        map,
        new Set(s.collected),
        s.worldObjects,
      ).nearest((i % 180) * 16 + 8, Math.floor(i / 180) * 16 + 8, 2);
    if (!point) return;
    const inventory: Record<number, number> = {};
    for (const id of FURNISHING_IDS) {
      for (const r of s.items[id].recipe)
        inventory[r.item] = (inventory[r.item] ?? 0) + r.quantity;
      for (const r of s.items[id].craftingTools)
        inventory[r.item] = Math.max(inventory[r.item] ?? 0, r.quantity);
    }
    useGameStore.setState({
      ...point,
      inventory,
      furnishings: {},
      shelter: 10,
      started: true,
      panel: "crafting",
      survival: { ...initialSurvival(), hunger: 40, thirst: 50, energy: 90 },
      wildlife: s.captureWildlife?.(),
      restoreRevision: s.restoreRevision + 1,
    });
  }
  async function age() {
    try {
      await new Promise<void>((resolve, reject) => {
        const request = indexedDB.open("robinson-island", 1);
        request.onerror = () => reject(request.error);
        request.onsuccess = () => {
          const db = request.result,
            tx = db.transaction("saves", "readwrite"),
            st = tx.objectStore("saves"),
            read = st.get("expedition");
          read.onsuccess = () => {
            const d = read.result;
            if (!validSave(d) || d.sleepStartedAt === undefined) {
              tx.abort();
              return;
            }
            st.put(
              { ...d, sleepStartedAt: Date.now() - 2 * 3600000 },
              "expedition",
            );
          };
          tx.oncomplete = () => {
            db.close();
            resolve();
          };
          tx.onabort = () => {
            db.close();
            reject(new Error("Choose Sleep before aging the checkpoint."));
          };
          tx.onerror = () => reject(tx.error);
        };
      });
      setMessage(
        "SIMULATED 2 real hours away · Continue should restore +10 to each reserve once",
      );
    } catch {
      setMessage("Choose Sleep before aging the checkpoint.");
    }
  }
  return (
    <>
      <div
        style={{
          padding: "10px 20px",
          background: "#f4e5aa",
          color: "#253f34",
          font: "13px Georgia",
          display: "flex",
          gap: 16,
          alignItems: "center",
          flexWrap: "wrap",
        }}
      >
        <b>ISOLATED UI TEST FIXTURE</b>
        <span>{message}</span>
        <button disabled={!s.ready} onClick={prepare}>
          Prepare cabin furnishings
        </button>
        <button onClick={age}>Simulate 2 hours away</button>
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
