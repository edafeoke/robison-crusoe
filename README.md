# Robinson Crusoe — The Island Journal

A local React / TypeScript / Phaser 3 reconstruction of Exkee's **Robinson Crusoe: Shipwrecked**, built from the supplied Java ME JAR. The current milestone adds recovered item rules and offline reload; it is not yet the complete original adventure.

```sh
npm install
npm run dev
```

Open the URL printed by Vite. Choose **Begin expedition**, or **Continue a saved story**. WASD/arrows move, click walks using pathfinding, E examines a nearby object. I opens inventory, C crafting, J journal, M map, Escape pauses. Zoom controls are on the right. Save from the top bar or pause menu; IndexedDB stores your expedition on this device. Original hunger/thirst/energy simulation is paused in this milestone.

Implemented: original 180×180 island, exact packed atlas frames/anchors, reconstructed vegetation, recovered Robinson idle/walking sprites, collision, camera, inspection, loose-resource pickup, inventory categories and 20 original supplies/equipment recipes with reusable tool requirements, chart/journal, saves. Optional ocean ambience and atmospheric clock/lighting are browser enhancements.

```sh
npm run decode     # lossless maps/atlas/objects/text from the JAR + retained CFR sources
npm test           # recovery integrity, collision and pathfinding checks
npm run typecheck
npm run build      # production build + asset precache service worker
npm run preview    # test dist locally; offline support is production-only
```

See [technical findings](docs/REVERSE_ENGINEERING.md), [map format](docs/MAP_FORMAT.md), [asset manifest](docs/ASSET_MANIFEST.md), [scope and roadmap](docs/GAME_DESIGN.md), [recovered item rules](docs/ITEM_RULES.md) and [validation](docs/VALIDATION.md). `evidence/` retains original extracts, complete decompilation/disassembly and reference images. `tools/render_reference.py` optionally rebuilds the contact/world reference with Pillow.

Original JAR, decompiled code and artwork are copyrighted by their owners. This workspace is for personal reconstruction and development. Do not publish or redistribute those materials without permission. No public deployment is configured.

Offline reload: production now uses one classic browser script (including styles), avoiding the earlier offline module-loading failure. After the first complete load and the “Island assets cached” indicator, the game can reload without its local server. Development mode still needs its dev server. This was verified in the embedded browser; other browser families need further QA.

Saves belong to the browser URL’s origin. Return using the same hostname and port to resume the same expedition; `localhost` and `127.0.0.1` have separate saves.
# robison-crusoe
