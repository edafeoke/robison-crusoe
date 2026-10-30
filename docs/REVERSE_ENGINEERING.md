# Robinson Crusoe: Shipwrecked — technical assessment

Investigation date: 2026-10-30. Scope of this iteration: recover the original archive and deliver the first playable exploration milestone. This is not yet a complete port.

## Evidence and reproducibility

The initial workspace contained **only** `robinsoncr_ok5vhpp3.jar` (365,449 bytes). No JAD, extracted assets, JSON maps, source code, or existing React/Phaser project was supplied. The JAR contains 59 files: 35 classes, 16 PNGs (15 BS banks plus the application icon), five 32,400-byte maps, `data/D0`, `objects.csv`, and the manifest. `evidence/archive-inventory.json` records every entry, size and SHA-256. Original files remain unmodified in the JAR and `evidence/original/`.

CFR 0.152 was downloaded from its author's distribution to `/private/tmp/robinson-cfr.jar`. All 35 classes were decompiled into `evidence/decompiled/`; `javap -c -p -constants` output is in `evidence/bytecode/`. The CFR summary flags partially structured methods in `aa`, `m`, and `w`. Obfuscated overloaded field names are not valid recompilable Java; decompiled expressions can have wrong inferred types. Treat disassembly and binary validation as the authority. Java ME APIs are absent from the host JDK, so the original MIDlet has not been executed in an emulator.

Reproduce:

```sh
java -jar /private/tmp/robinson-cfr.jar robinsoncr_ok5vhpp3.jar --outputdir evidence/decompiled --silent true
python3 tools/decode_original.py
# Optional, with Pillow installed:
python3 tools/render_reference.py
npm install
npm test
npm run build
npm run dev
```

The decoder uses the Python standard library. The optional reference renderer requires Pillow. CFR is needed only when regenerating decompiled sources. The browser uses generated JSON/PNG files, not Java or Python.

## Verified execution path and class responsibilities

Names below are roles inferred from actual call sites and referenced APIs, not the original developers' names.

| Class | Verified evidence / role |
| --- | --- |
| `Robinson.MyMidlet` | Manifest entry. Constructor creates `data.p`, starts a Java Thread with it; `startApp` displays it. |
| `p` | Central game state and loading/state dispatch; extends `k`. Owns map arrays, tile size `M=16`, coordinates, inventory, timers and scene flags. |
| `k` | Canvas platform wrapper, Runnable loop, keyboard/pointer processing, graphics, current-time delta and sleeps; host screen dimensions. |
| `v` | Menu/game state dispatch and loading stages; at stage 100 sets original start tile `(8,148)`. Its world draw path calls terrain, rock, border, decoration/entity and HUD renderers. |
| `w` | Sprite-region engine. Reads 15 bank counts and six little-endian shorts per region from D0; handles anchors and Java ME mirror transform. Sprite integer packs bank in high 16 bits and region in low 16. |
| `g` | Five map loaders, terrain/rock/border/decor mapping tables, collision predicates, entity initialization, depth ordering, tide remapping. |
| `ab` | Character movement/camera offsets, facing, basic walking and composite clothing rendering; checks all four collision predicates before advancing. |
| `h` | 54 straight-line vegetation drawing routines combining branches/trunks/leaves with explicit offsets. |
| `a` | Context actions, object examination, tool selection, nearby object checks and interaction dispatch. |
| `y` | Initializes 120 object IDs, maps object index to sprite, loads names/descriptions and parses 13 metadata rows including recipes. |
| `ad` | Inventory additions, quantities, categories, item effects, crafting-related state and need depletion. |
| `ac` | Localized text decoder and text layout. Four banks, five locales, little-endian pointers and variable-length code points. |
| `aa` | Time/date, statistics, weather/tide UI and debug overlays. |
| `r` | HUD/menu/inventory graphics and text rendering. |
| `d`, `l`, `q`, `u`, `i` | Entity families instantiated by g for specific object map IDs; movement/animation state. Their complete animal-to-behavior mapping remains to audit. |
| `s`, `n`, `x`, `t` | Action/minigame state/rendering code. Exact one-to-one attribution of every minigame needs a deeper call-graph pass. |
| `e`, `z` | RecordStore wrapper and storage exception. Wrapper increments caller record index before access. |
| `m` | Resource loader / palette support. |
| `o` | Random helper. |
| `j` | Touch/pointer UI hit testing and directional input. |
| `c` | Environmental animation controller: shoreline border cycles every 400 ms, fire swaps every 100 ms, selected boat frames every 300 ms. Shoreline cycles are ported exactly. |
| `b`, `f` | Exception support classes. |
| `com.*` classes | Celsius MIDlet and Motorola full-screen compatibility wrappers. |

## Verified world, movement and sprites

See MAP_FORMAT.md and ASSET_MANIFEST.md for exact formats. Grid is 180×180 at 16×16 world pixels, or 2880×2880. Index is `x+y*180`. FULL is not a collision mask: it is a terrain sprite index. Nonzero decoration and object cells block walking in the recovered predicates. Original shoreline animation in c cycles nine BS11 border entries through four steps every 400 ms; the port preserves this cycle. Walking terrain rejects 5,6,12,13,14. Sailing has another predicate. Rocks have a nontrivial passable allowlist. Border sprites do not independently determine collision.

BS3 has 20 logical frames, divided into four groups of five. The renderer uses down `196608+af`, up `196613+ag`, left `196618+ah`, right `196623+ai`. Frame zero is a standing pose; counters cycle 1…4 for walking. Mirrored logical frames reuse packed pixels. Original movement waits for a 25 ms gate and advances in `aj` increments; exact speed depends on initialization and state. The port uses normalized continuous movement at 64 pixels/s and a 125 ms walk frame interval: these timing choices are **browser adaptations**, not exact recovered constants. Four directions and exact frames are verified.

The original art is pixel art. This milestone preserves it with nearest-neighbor rendering. Replacing it with invented hand-painted artwork would weaken visual fidelity; the surrounding parchment/ink interface is new. Additional painted art is deferred.

## Verified objects, inventory and recipes

120 English item names and 120 descriptions were recovered, with all five language versions archived in `evidence/localized-text.json`. Map object ID is item index +1 (`y` initializes `p.i[n]=n+1`, and `a` compares that table to map values). Example indices: Mango 0, Coconut 1, Stone 3, Axe 5, Vine 6, Dry grass 7, Branch 8, Tent 9, Cabin 10, Fishing rod 14, Fire 16, Pirate shipwreck 18, Pirate ship 19, Knife 76, Bow 81, Rope 91, Raft 94. `public/assets/objects/objects.json` preserves every raw field and recovered name, description, icon reference and recipe.

CSV has 13 rows ×120 columns, semicolon delimiters, CRLF, and `@` end marker. The parser begins its switch at 3; row 8 is ingredient lists of zero-based item ID/quantity pairs (`item|quantity#item|quantity`). `-1` means no recipe. Examples **verified in bytes**:

- Axe: Stone×1 + Branch×1 + Vine×1.
- Rope: Vine×2 + Branch×1.
- Bow: Stick×1 + Rope×1.
- Tent: Branch×3 + Sail×1.
- Fire: Dry grass×3 + Branch×2 + Stone×2.

Second-milestone tracing establishes pickup/use/specialized action requirements (rows 0–2), reusable crafting equipment (row 9), and inventory category (row 12). The decoder now exports typed fields, collection uses the recovered requirements, and 20 supplies/equipment recipes check materials and reusable tools. See ITEM_RULES.md for call sites, examples and limits. Other numeric effects and progression fields remain raw until audited. Construction placement, clothing and specialized animal interactions remain deferred. Original examination text and object placement are retained.

## Verified survival, adventure and progression

Recovered English strings explicitly identify Hunger, Thirst, Energy, Strength, Morale, Weight; rest, sleep/save, weather and tides. **A separate health gauge is not verified.** In `ad.f`, three float reserves drain by 10%,14%,4% of a shared maximum when time counter J advances one unit; zero in any triggers a loss state. Exact temporal unit and mapping of all float fields must be traced before porting. Food, poisoning and dangerous animals can kill the player (also explicit strings).

Original text verifies coconut throwing (stone, two hits), net fishing with potato bait in shallow water (ten fish to win), rod fishing with worms in deep water, bow hunting with arrows, changing clothes, constructing and upgrading shelter, and escaping by repairing a boat. Tutorial bank has 117 entries including opening narrative and controls. Boat is named **Amber Queen**. End text confirms survival over days followed by boat repair and escape. Exact quest triggers, unlock order and elapsed-time simulation remain incomplete.

`g.a(boolean)` proves tides remap terrain (4→0,5→4,6→5) and several border IDs; it restores saved layer copies on reversal. A static map without these changes is not a complete original progression model. Tide timing is deferred.

Original save support uses RMS (`e`), state serialization and RecordStore calls in `p`; byte-compatible save import is not implemented. Browser IndexedDB saves versioned position, collected/inspected cells, inventory, explored cells, elapsed play and settings. Browser clock, journal goals, click pathfinding, zoom, ambience and navigation are enhancements. Needs are explicitly paused in this exploration milestone; no invented thirst/food loop is substituted.

## Audio

No audio files or Java ME media player references were found in this supplied variant. D0's atlas/text boundaries are accounted for; hidden sound data is not claimed. Optional synthesized ocean ambience is a browser enhancement and defaults off.

## Fidelity limitations / next work

- Original MIDlet not yet emulator-tested; static bytecode inspection cannot establish every runtime interaction.
- Complete entity AI, hunting, fishing and throwing minigames, clothing composition, construction placement, story triggers, weather, tide timing and original survival/save formats remain unported.
- Object icon references are exact; some original world entities use special multipart/animated rendering. Their static representations in this milestone do not claim exact AI or animation behavior.
- Original map loader filters progress-dependent objects in runtime state. The source export always retains every byte; milestone shows recoverable static placements and does not implement all original filters.
- Decoration variants were randomized at runtime by the original. The port selects a stable variant using cell ID; piece coordinates and variant choices are recovered, random seed is new.
- No public distribution permission is supplied. Keep JAR, decompiled sources and extracted art private. No hosting/publication is part of this iteration.

## Validation record

Decoder validates all layer lengths and all 778 frame bounds against original image dimensions. Further automated and browser checks are recorded in `docs/VALIDATION.md` once run; remaining features are tracked in GAME_DESIGN.md.
