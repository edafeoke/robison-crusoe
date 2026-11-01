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
| `d`, `l`, `q`, `u`, `i` | Predators, passive wildlife, prey, birds, sharks respectively. All 16 constructed species, 326 placements, frame arrays and state families are now traced; see WILDLIFE.md. |
| `s`, `n`, `x` | Hunting, coconut throwing, rod/net fishing respectively; verified through a/p dispatches. Coconut, net, rod and supported bow-hunting modes are ported. |
| `t` | Original RMS save serialization, including the separate coconut-challenge wear counter. |
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

## Verified wildlife

The third milestone recovers all animal constructors in `g.e`, their BS2 frame arrays, source 11 FPS / 90 ms tick, speed tiers, wandering/rest, flee/pursuit, bird flight/landing and shark water restrictions. A pure simulation and original-sprite Phaser renderer now manage 326 placements across 16 species. Runtime occupancy is separate from immutable map data. IndexedDB snapshots retain wildlife state; old saves still load. See WILDLIFE.md for full per-species mapping, hazard arithmetic (paused), save format and explicit browser adaptations. No byte-exact AI/emulator equivalence is claimed.

## Verified objects, inventory and recipes

120 English item names and 120 descriptions were recovered, with all five language versions archived in `evidence/localized-text.json`. Map object ID is item index +1 (`y` initializes `p.i[n]=n+1`, and `a` compares that table to map values). Example indices: Mango 0, Coconut 1, Stone 3, Axe 5, Vine 6, Dry grass 7, Branch 8, Tent 9, Cabin 10, Fishing rod 14, Fire 16, Pirate shipwreck 18, Pirate ship 19, Knife 76, Bow 81, Rope 91, Raft 94. `public/assets/objects/objects.json` preserves every raw field and recovered name, description, icon reference and recipe.

CSV has 13 rows ×120 columns, semicolon delimiters, CRLF, and `@` end marker. The parser begins its switch at 3; row 8 is ingredient lists of zero-based item ID/quantity pairs (`item|quantity#item|quantity`). `-1` means no recipe. Examples **verified in bytes**:

- Axe: Stone×1 + Branch×1 + Vine×1.
- Rope: Vine×2 + Branch×1.
- Bow: Stick×1 + Rope×1.
- Tent: Branch×3 + Sail×1.
- Fire: Dry grass×3 + Branch×2 + Stone×2.

Second-milestone tracing establishes pickup/use/specialized action requirements (rows 0–2), reusable crafting equipment (row 9), and inventory category (row 12). The decoder now exports typed fields, collection uses the recovered requirements, and 20 supplies/equipment recipes check materials and reusable tools. See ITEM_RULES.md for call sites, examples and limits. Other numeric effects and progression fields remain raw until audited. Construction placement, clothing and specialized animal interactions remain deferred. Original examination text and object placement are retained.

## Recovered bow hunting

The eighth milestone traces animal tool 81 → s.b()/q=3. Fifteen original s.k profiles are exported; shark has no hunting profile and remains unsupported. Bow hunting now uses source integer apex-only shots, first-miss animal movement, ten-shot limit, grouped retaliation energy and exact animal rewards/Arrow/Bow effects. A win removes the actual stable overworld animal and persists that removal. HUNTING.md records clothing assumptions, tenth-shot result conflict handling, source quirks and browser adaptations.

## Recovered rod fishing

The seventh milestone traces terrain 5 → c(40) → tool 14 → x.c()/q=0. Rod fishing retains all four fish profiles, trailing hook history, idle sway, strict point-in-fish capture, outward/downward pull, edge escapes and reeling boundary. A completed attempt consumes one worm and applies the rod strength/energy effects; wins grant the actual fish species. Bytecode verifies fish/update and control ordering. See ROD_FISHING.md for source rules and browser adaptations.

## Recovered net fishing

The sixth milestone traces terrain 4 → c(59) → tool 83 → x.f()/q=1. Shallow-water net fishing now retains the central lane, fish movement/capture/update order, ten-fish/five-cast objective, potato consumption and exact inventory/activity effects. The original reward table includes item 60 (Rug); this is preserved and explained, not repaired by guessing. See NET_FISHING.md for source paths, rules, randomness/viewport adaptations and validation limits.

## Recovered coconut challenge

The fifth milestone traces decor 18/variant 8 → a menu action 121 → n.b → p.m=7/q=2. The browser now plays the two-hit/eleven-second-window, five-down/ten-throw challenge with original sprite regions and integer projectile arithmetic. The missing x.a:[I constructor initializer in CFR was recovered through javap. Exact reward (+1 Coconut), energy cost and saved wear counter are implemented. See COCONUT_CHALLENGE.md for formulas, source call sites, branch quirks, random-placement safety guard and the documented browser palm-hash change.

## Verified survival, adventure and progression

Recovered English strings explicitly identify Hunger, Thirst, Energy, Strength, Morale, Weight; rest, sleep/save, weather and tides. **A separate health gauge is not verified.** In `ad.f`, three float reserves drain by 10%,14%,4% of a shared maximum when time counter J advances one unit; zero in any triggers a loss state. Fourth-milestone tracing resolves float a/b/c as hunger/thirst/energy and d as their shared maximum, with e strength and f morale. The normal source clock refreshes from device time every 60000 ms; ad.f decrements 10/14/4 percent at consecutive minute changes (with a 59→0 exception). These reserves, exact inventory food effects, collection/creation costs and short rest are now ported. See SURVIVAL.md for call sites, bytecode-confirmed double food energy, stone wear, saved-state migration and browser timing adaptations. Food, poisoning and dangerous animals can kill the player (also explicit strings).

Original text verifies coconut throwing (stone, two hits), net fishing with potato bait in shallow water (ten fish to win), rod fishing with worms in deep water, bow hunting with arrows, changing clothes, constructing and upgrading shelter, and escaping by repairing a boat. Tutorial bank has 117 entries including opening narrative and controls. Boat is named **Amber Queen**. End text confirms survival over days followed by boat repair and escape. Exact quest triggers, unlock order and elapsed-time simulation remain incomplete.

`g.a(boolean)` proves tides remap terrain (4→0,5→4,6→5) and several border IDs; it restores saved layer copies on reversal. A static map without these changes is not a complete original progression model. Tide timing is deferred.

Original save support uses RMS/RecordStore operations in `e` and state serialization in `t`; byte-compatible save import is not implemented. Browser IndexedDB saves versioned position, collected/inspected cells, inventory, explored cells, elapsed play and settings. Browser clock, journal goals, click pathfinding, zoom, ambience and navigation are enhancements. New expeditions enable the recovered needs/food/rest loop; legacy saves keep exploration mode until enabled. No invented thirst collectible or separate health meter is substituted. Explicit offline sleep and world-contact animal hazards are now ported; non-sleep offline arithmetic and emulator comparison remain deferred.

## Audio

No audio files or Java ME media player references were found in this supplied variant. D0's atlas/text boundaries are accounted for; hidden sound data is not claimed. Optional synthesized ocean ambience is a browser enhancement and defaults off.

## Fidelity limitations / next work

- Original MIDlet not yet emulator-tested; static bytecode inspection cannot establish every runtime interaction.
- Exact entity scheduling/emulator comparison, story triggers, weather, tide timing, non-sleep offline behavior and original save formats remain unported. Nutrition-driven Weight is now ported; no mass-based carrying limit was found in the audited inventory paths (BODY_WEIGHT.md). Tent/Cabin now upgrade the existing original flag in place, with persistent global recipe tiers; see SHELTERS.md. No free placement is inferred from those upgrade paths.
- Object icon references are exact; some original world entities use special multipart/animated rendering. The 16 traced animal profiles are animated; other special entities remain static until their specific rendering paths are ported.
- Original map loader filters progress-dependent objects in runtime state. The source export always retains every byte; milestone shows recoverable static placements and does not implement all original filters.
- Decoration variants were randomized at runtime by the original. The port selects a stable variant using cell ID; piece coordinates and variant choices are recovered, random seed is new.
- No public distribution permission is supplied. Keep JAR, decompiled sources and extracted art private. No hosting/publication is part of this iteration.

## Validation record

Decoder validates all layer lengths and all 778 frame bounds against original image dimensions. Further automated and browser checks are recorded in `docs/VALIDATION.md` once run; remaining features are tracked in GAME_DESIGN.md.


## Camp upgrades and crafting tiers

`y.d()` parser case 13 reads CSV row 10 into `p.e : byte[]`. `aa.c(StringBuffer)` gates recipes globally by current Flag/Tent/Cabin state, accepting -1 / -1+9 / -1+9+10 respectively. `a.G()` bytecode offsets 0–177 confirms that Tent (9) and Cabin (10) replace item 17's sprite/name/description in place, call `ad.l()` for effects and `ad.o()` for material subtraction. The original map's lone flag remains map ID 18 at (24,129); terrain/placement/occupancy are unchanged. `t` persists the resulting camp stage. The browser now ports these two upgrades and gates all supported recipes; source equipment prerequisites remain reusable. Furnishings, storage, overnight sleep and boats remain separate. Exact recipes, sources, tests and immediate-completion/UI adaptations are documented in SHELTERS.md.


## House furnishings and real-time sleep

`a.G` routes category 3/5 furnishing creation to `ad.m`: a unique named house entry, creation effects from `ad.l`, materials removed through `ad.o`. Cabin remains an earlier upgrade branch. The four recovered entries are Single bed (15), Barrel (50), Rug (60), Double bed (85); three have sprite -1 and receive no invented mapping. `r.b(StringBuffer)` displays stored names/quantities; the traced house action menu offers inspection, not generic supply transfers. `a.m` Sleep sets the saved int flag and exits to title. Loading through `v.a(boolean)` calls `v.c(true)`, restoring 0/10/20/30/40/50 percent to all three reserves in whole-hour bands, without a house/bed multiplier in that path. Browser ports these explicit sleep bonuses with a transactionally consumed timestamp; ordinary saves keep paused offline reserves. Full sources, recipes, missing-art behavior, preserved fishing Rug loot and timing/claim adaptations: HOUSE_AND_SLEEP.md.


## Direct world-food use

`a.c(int)` prioritizes structures/hunting, then the use-tool byte and owned supplies/equipment to offer `a.D()` world USE (655387). This calls `ad.j()`, not satchel `ad.g/h`. All 22 supported static food types retain their original placements. World Hunger/Thirst skip −1; an always-true tool OR branch causes two capped Energy applications even for free fruit. Tools and the satchel Stone-opening counter are unchanged. Either fatal Energy pass returns before the nutrition call and Objects-cell clear. Successful use applies raw-Hunger nutrition once and removes only that original runtime object, with no inventory addition or pickup cost. Browser normalizes the source's stale fatal reserve/poison-label quirks explicitly; one-use tombstones reuse existing version-1 saves. Full evidence, original food/tool table, implementation guards, adaptations and QA: WORLD_FOOD.md.

## Nutrition-driven Weight and capacity audit

The float `p.g:F` is the sixth original status bar, labelled Weight (text 75); `p.L:I` accumulates raw food Hunger values. `v.a(int)` subtracts 50 once and adds one native bar unit when the threshold is reached, preserving queued nutrition rather than looping over a large meal. `ad.g` calls it once per supported satchel meal, including literal −1 in the positive-tool branch; full Hunger does not prevent growth. Only `v`, `aa` and `t` reference the Weight float; no movement, pickup or crafting capacity gate was found. Source inventory quantity arrays reuse `g` with a different descriptor and are not weight data. The browser preserves raw counter/bar units with an explicitly fixed 240-pixel reference (max 64), displays original Strength/Morale/Weight icons, persists optional weight state and resets legacy saves to zero. No invented carrying restriction is imposed. Detailed arithmetic, original byte-save quirks, capacity findings: BODY_WEIGHT.md. The separate direct-world use branch is now ported in WORLD_FOOD.md.

## World contacts, energy and rest interruption

`d.a()` checks Chebyshev-one tile proximity after movement while not sailing, accumulates per-actor elapsed exposure, and attacks only after more than 1000 ms. Its independent worn-slot helper halves alligator/jaguar/green-snake/boa energy costs from 50/50/10/20 to 25/25/5/10. An attack cancels short rest and credits integer elapsed-rest ×30/10000 after damage. `i.a()` immediately empties energy on one-tile shark proximity, without clothing or sailing exemptions. Fatal animal loss uses source story entry 95. Browser contacts run on actual visible wildlife ticks and preserve optional partial exposure in saves; minigames pause them. Protected negative reserves and the source's exact-zero loss check are normalized explicitly, while the unprotected pre-rest clamp is preserved. No invented health, poison timer or hit-animation mapping is added. Source audit, quirks, rules, validation and remaining limitations: WORLD_HAZARDS.md.

## Clothing, composition and hunting protection

`y.c` and `v.g` initialize four owned lists with -1/selection 0. `a.G` → `ad.a(String)` stores crafted garments uniquely without selecting them; `ad.l/o` apply creation effects/material subtraction. All 16 recipes and slot/category mappings are retained. `ab.j` composes shoes, pants, shirt and hat over the body, with four directional five-frame garment sequences and four static directional hat frames. `javap` recovers all eight hat bob arrays, including four CFR omitted. The resulting profiles cover all 256 original BS5 regions. `s.a` checks whether the appropriate worn slot is non-empty to halve alligator/pants, jaguar/shirt, green-snake/shoes and boa/hat retaliation costs. Browser ports the wardrobe, exact composition and actual protected hunting costs; saves persist owned garment IDs and worn slots. CSV row 11 matches slot metadata but is parsed into an apparently unused scratch array, not assumed to drive the selector. Detailed outfit evidence/adaptations: CLOTHING.md. World contacts have since been independently audited and implemented; see WORLD_HAZARDS.md.

## Pirate ship and escape audit — 2026-11-01

The dedicated `a.c/z/G → r.a → ad.l/o → v.b/d` path is now ported: Large sail crafting, original ship reconstruction and flags, retained map cell, two-tile northward player shift, control lock with world simulation continuing, strict >8000 ms departure and original victory text. The CSV ship requirement is not a camp upgrade. Browser adaptations and exact evidence are in SHIP_RECONSTRUCTION.md; generated facts in `evidence/ship-and-raft-rules.json`. Raft terrain permits only FULL 12/13/14. Boarding/disembarking, tide changes and remaining decor/resource updates are still pending. The current walking reachability audit finds the wreck and several tools outside the spawn component, so no complete original journey is claimed.

## Swamp raft traversal — 2026-11-01

The dedicated `a.B/A/G → ad.u/v → ab.b/k/c` path is now ported, including exact recipe/effects, single adjacent boarding/landing steps, FULL12/13/14 collision, seven-phase seated/paddle layers, shirt/hat composition and persistent moved docks/afloat position. The earlier four-step interpretation of `ab.*(4)` was incorrect: its argument is unused; the source starts one tile movement in half-tile increments. JVM also confirms the landing menu duplicates `g.a:(II)Z` and omits rocks; the browser documents an extra rock check to prevent stuck landings. Generated evidence: `evidence/raft-rules.json`; detailed verification/adaptations: RAFT_TRAVERSAL.md. Tides and a complete legitimate material route remain pending.
