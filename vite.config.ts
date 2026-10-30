import { defineConfig, type Plugin } from "vite";
import react from "@vitejs/plugin-react";
import { readdirSync, readFileSync, writeFileSync } from "node:fs";
import { join } from "node:path";
import { createHash } from "node:crypto";
import { Script } from "node:vm";
function offlineIsland(): Plugin {
  return {
    name: "offline-island",
    apply: "build",
    closeBundle() {
      function files(dir: string, prefix = ""): string[] {
        return readdirSync(dir, { withFileTypes: true }).flatMap((e) =>
          e.isDirectory()
            ? files(join(dir, e.name), `${prefix}${e.name}/`)
            : [`${prefix}${e.name}`],
        );
      }
      // A classic IIFE bundle avoids offline module-graph fetching differences in embedded browsers.
      const htmlPath = join("dist", "index.html");
      const html = readFileSync(htmlPath, "utf8").replace(
        /<script type="module" crossorigin src=/g,
        "<script defer data-game-entry src=",
      );
      const entry = /<script defer data-game-entry src="([^"]+)">/.exec(
        html,
      )?.[1];
      if (!entry)
        throw new Error(
          "Production entry must be a deferred classic script for offline loading",
        );
      // Parse, without executing, to prevent a module import/export from silently reappearing.
      new Script(readFileSync(join("dist", entry), "utf8"), {
        filename: entry,
      });
      writeFileSync(htmlPath, html);
      const assets = files("dist").filter((f) => f !== "sw.js"),
        version = createHash("sha256");
      for (const f of assets) version.update(readFileSync(join("dist", f)));
      const cache = `robinson-${version.digest("hex").slice(0, 12)}`;
      writeFileSync(
        "dist/sw.js",
        `const CACHE=${JSON.stringify(cache)};const FILES=${JSON.stringify(["/", ...assets.map((f) => "/" + f)])};
self.addEventListener('install',event=>{event.waitUntil(caches.open(CACHE).then(cache=>cache.addAll(FILES)).then(()=>self.skipWaiting()));});
self.addEventListener('activate',event=>{event.waitUntil(caches.keys().then(keys=>Promise.all(keys.filter(k=>k.startsWith('robinson-')&&k!==CACHE).map(k=>caches.delete(k)))).then(()=>self.clients.claim()));});
self.addEventListener('fetch',event=>{if(event.request.method!=='GET'||new URL(event.request.url).origin!==self.location.origin)return;event.respondWith(caches.open(CACHE).then(cache=>cache.match(event.request.mode==='navigate'?'/index.html':event.request,{ignoreVary:true}).then(cached=>cached||fetch(event.request))));});\n`,
      );
    },
  };
}
export default defineConfig({
  plugins: [react(), offlineIsland()],
  build: {
    chunkSizeWarningLimit: 1600,
    rollupOptions: {
      output: {
        format: "iife",
        inlineDynamicImports: true,
        name: "RobinsonIsland",
      },
    },
  },
});
