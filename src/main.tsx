import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./styles.css";
import { useGameStore } from "./store/gameStore";
ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
// Wait for this build's worker to activate: an older active worker is not proof that
// the currently loaded, hashed JavaScript bundle has been cached successfully.
async function prepareOffline() {
  const entry = encodeURIComponent(
    document.querySelector<HTMLScriptElement>("script[src][data-game-entry]")
      ?.src ??
      document.scripts[document.scripts.length - 1]?.src ??
      "game",
  );
  const registration = await navigator.serviceWorker.register(
    `/sw.js?entry=${entry}`,
    { updateViaCache: "none" },
  );
  const worker =
    registration.installing ?? registration.waiting ?? registration.active;
  if (!worker) throw new Error("Offline worker unavailable");
  if (worker.state !== "activated")
    await new Promise<void>((resolve, reject) => {
      worker.addEventListener("statechange", () => {
        if (worker.state === "activated") resolve();
        else if (worker.state === "redundant")
          reject(new Error("Offline cache installation failed"));
      });
    });
  const required = [
    ...Array.from(
      document.querySelectorAll<HTMLScriptElement>("script[src]"),
    ).map((s) => s.src),
    ...Array.from(
      document.querySelectorAll<HTMLLinkElement>('link[rel="stylesheet"]'),
    ).map((s) => s.href),
  ];
  if (
    !(await Promise.all(required.map((url) => caches.match(url)))).every(
      Boolean,
    )
  )
    throw new Error("This build is not fully cached");
  useGameStore.setState({ offlineReady: true });
}
if (import.meta.env.PROD && "serviceWorker" in navigator)
  window.addEventListener("load", () => {
    void prepareOffline().catch(() => {
      /* Online play remains available if browser storage is denied. */
    });
  });
