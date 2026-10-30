# Validation record

## Current milestone: recovered item rules and offline reload

Automated checks: **13 passed**. TypeScript and production build pass. The six new checks cover typed CSV requirements, tool-gated collection, specialized animal/structure actions, reusable crafting tools, unique equipment and recipe scope, and forged/remote/duplicate pickup rejection.

Production now emits a self-contained classic IIFE script, including styles, rather than a split ES module graph. Preliminary embedded-browser QA successfully reloaded the cached game with the preview server stopped, rendered the island, and moved Robinson from tile (8,148) to (11,148). Final UI/save/reload results are recorded in the second-milestone browser evidence below.

## First playable milestone (historical)

Date: 2026-10-30. Local development and production builds; personal assets only. No public deployment.

## Automated checks

`npm test`: **7 passed**, covering:

1. All five JSON map layers reconstruct the exact original JAR bytes and SHA-256; copied raw .byt files also match.
2. Invalid map dimensions, lengths and byte values are rejected; original coordinate indexing is preserved.
3. Every one of 778 atlas regions fits its source sheet; all 15 copied bank PNGs are byte-identical; composite sprite references resolve.
4. Zero-based item recipe IDs and one-based object map IDs match recovered examples.
5. Original terrain/rock/decor/object walking rules, boundaries and runtime removal without modifying original maps.
6. A path through the actual island grid stays on passable cardinal neighbors and reaches its destination.
7. Crafting consumes exact materials atomically, refuses missing materials/unsupported construction, and duplicate pickup of one world cell yields only once.

`npm run typecheck` and `npm run build`: passed during implementation. Final checks were repeated after source formatting and the last save-position fix. Build has separate React/application and Phaser bundles; Phaser is approximately 1.21 MB uncompressed / 332 KB gzip. Assets and dependencies are local; no CDN, web fonts or backend calls are required.

## Browser checks performed

Tested through the in-app browser's UI at desktop widths (1280 and 1440) and its initial narrow panel width (~570). This is not a claim of comprehensive cross-browser/mobile QA.

- Original world/vegetation displayed and original Robinson sprite visible.
- Arrow right moved from original tile (8,148) along the shore; WASD changed facing and advanced world coordinates in the production build.
- Click-to-walk crossed the original shoreline to tile (22,148); camera followed onto the main beach.
- E opened Stone examination at original map location (23,147), with recovered description: “Just a flat stone. This might come in handy.”
- Gather added Stone×1 to the satchel; the item detail displayed the original sprite/description. The world object's runtime removal persisted.
- Save produced confirmation; a full page reload followed by Continue restored position, inventory and journal progress.
- Craft panel showed exact Axe/Rope ingredients and disabled buttons for missing materials. Material consumption is checked by the automated transaction test; full manual world collection/crafting progression was not exhaustively played.
- Journal showed original opening narrative and clearly identified the new exploration checklist.
- Map showed the complete original island overview and player marker.
- Pause/settings controls displayed; Escape returned to play. Zoom controls were present; full-screen and audible ambience were not comprehensively verified.
- After all local servers were stopped, the already-loaded production game still accepted arrow input and advanced from tile (8,148) to (10,148). This verifies continued loaded-session play, not cold offline reload.
- Exactly one canvas was observed with React StrictMode enabled. No captured console errors/warnings during normal play.

## First milestone offline failure (subsequently resolved)

A versioned production service worker precaches the shell, generated bundles, original sprites/maps and data. Readiness now waits for worker activation and checks cached entry assets. Cache reads ignore `Vary` for these same-origin static files; local response inspection confirmed Vite emits `Vary: Origin` with different headers for module requests.

**At the first milestone, reload after stopping the local server produced an HTML shell with an empty React root in the embedded browser.** This was subsequently resolved by the self-contained classic-script production build described above. The loaded game has no network gameplay dependency, and the second milestone now verifies cold offline reload as well. Native Chrome verification could not be completed because computer automation timed out. The exact underlying module-replay cause was not established; the single-script packaging is a verified workaround, not a claim about a browser defect.

The footer therefore says **Island assets cached**, not that offline reload is guaranteed. Development mode does not install a worker and needs its server. Production can reload offline after its first complete cached load. Browser families other than the in-app browser remain unverified.

## Other limitations

This milestone does not implement the full original survival timing, animal AI, clothing composition, fishing/hunting/throwing minigames, changing tide timing, construction placement, story triggers or boat escape. Needs remain paused. Hand-painted replacement artwork is deferred in favor of exact original pixel art. No original MIDlet emulator comparison has been performed. See REVERSE_ENGINEERING.md and GAME_DESIGN.md for verified facts, adaptations and the implementation sequence.

First-milestone visual evidence: `evidence/playable-milestone.jpg` (1440 px desktop, full page). Temporary preview servers were stopped after QA; restart with `npm run dev` to open/reload the game.


## Second milestone browser evidence

- Final production entry `index-Blmz7Y18.js` is a single classic script with inline styles. Build-time parsing with Node's Script parser rejects any accidental top-level module import/export regression.
- Workbench UI displayed **All recipes 20**, separated “MATERIALS · USED UP” and “TOOLS · KEPT”, and disabled unavailable crafts. “Ready to make 0” showed an honest empty state.
- Walked through the original island to the vine at the jungle edge near tile (38,149). Inspection from (37,150) showed **Requires Knife**, explained that the tool is kept, and offered **no Gather vine button** with an empty inventory. Evidence: `evidence/tool-requirement.jpg`.
- Positive tool-gated pickup, ingredient consumption, retained crafting tools and equipment uniqueness are also covered by automated tests; no manual bypass or debug inventory injection was used.

- The live expedition subsequently showed **Using Knife** and an enabled **Gather vine** action, confirming the positive side of the tool rule. Its state was preserved while the final cold-reload test used a separate origin.
- Final isolated QA used `http://localhost:4181` (separate saves from the active `127.0.0.1` expedition). Moved Robinson to (11,148), saved, stopped the preview process, reloaded the complete app and original island from cache, and continued the saved story. Position (11,148) was restored while the server remained stopped.
- Opened the **20-recipe workbench after offline restoration**; expected reusable-tool sections and disabled missing-material actions were present, with no captured errors/warnings.
- Current production bundle: approximately 1.51 MB / 427 KB gzip, including React, Phaser and styles. No module graph or external stylesheet request is required to start the cached app.
- Visual evidence: `evidence/workbench-milestone.jpg` and `evidence/tool-requirement.jpg`. The active expedition's save was not overwritten by isolated QA.
- All 13 automated tests and the final TypeScript/production build passed. Preview processes were stopped after testing. Offline support is verified in the in-app browser; other browser families remain to test.
