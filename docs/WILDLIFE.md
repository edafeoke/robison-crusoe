# Wildlife recovery and browser port

This milestone animates **326 original placements across 16 species**. Source evidence is the retained CFR output and javap disassembly, not an inferred sprite-sheet grid. It is a port of the recovered behavior families, not a byte-exact Java simulation. Supported bow hunting is now implemented separately in HUNTING.md.

## Evidence and reproducible export

`data.g.e()` counts object map IDs, assigns packed sprite references, and constructs `u`, `l`, `q`, `d`, and `i`. `tools/recover_wildlife.py` extracts the actual constructors and array assignments into `public/assets/animals/wildlife.json`; `npm run decode` runs it after exporting objects. Names come from the original localized item bank, indexed by map ID minus one. All atlas references retain their original bank/region values and D0 anchors. The BS2 sheet is unchanged.

`p` initializes `aj = M / 2 = 8` and calls `b(11)`. `k.b(int)` sets the loop minimum to integer `1000 / fps`: **90 ms** at 11 FPS. Ground speeds divide 8 by 6, 5, 4, 3, or 2 according to constructor tier. Integer steps are respectively **1, 1, 2, 2, 4 pixels per update per axis**. Prey/predators use the next tier when reacting. Shark constructor tier 5 uses 4. Two-frame sequences hold each pose for two rendered updates; sharks hold three poses for two updates each.

## Verified species and frames

Frame numbers below are BS2 logical region indices, not pixel coordinates. `L/R` means left/right ground motion; `I` idle; `FL/FR` flight. Encoded reference = `131072 + region`.

| Map ID | Original name | Class / family | Count | Tier | L / R | I | FL / FR |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 5 | Wild goat | q / prey | 21 | 4 | 0,1 / 2,3 | 4,5 | — |
| 42 | Crab | l / passive | 4 | 1 | 40,41 / 40,41 | 40,41 | — |
| 43 | Alligator | d / predator | 20 | 2 | 14,15 / 16,17 | 14,15 | — |
| 44 | Jaguar | d / predator | 6 | 4 | 86,87 / 88,89 | 86,87 | — |
| 45 | Peccary | q / prey | 9 | 4 | 10,11 / 12,13 | 10,11 | — |
| 47 | Porcupine | q / prey | 3 | 2 | 18,19 / 20,21 | 18,19 | — |
| 48 | Turtle | l / passive | 7 | 1 | 6,7 / 8,9 | 6,6 | — |
| 49 | Green snake | d / predator | 41 | 2 | 52,53 / 54,55 | 56,57 | — |
| 50 | Boa | d / predator | 40 | 1 | 46,47 / 48,49 | 50,51 | — |
| 52 | Green parrot | u / bird | 21 | 4 | 102,103 / 104,105 | 104,105 | 106,107 / 108,109 |
| 53 | Seagull | u / bird | 72 | 4 | 70,71 / 72,73 | 72,73 | 74,75 / 76,77 |
| 54 | Pelican | u / bird | 19 | 3 | 78,79 / 80,81 | 80,81 | 82,83 / 84,85 |
| 56 | Shark | i / shark | 9 | 5 | 90,91,92 / 93,94,95 | — | — |
| 62 | Green iguana | l / passive | 7 | 1 | 42,43 / 44,45 | 42,42 | — |
| 63 | Toucan | u / bird | 31 | 3 | 118,119 / 120,121 | 120,121 | 122,123 / 124,125 |
| 64 | Red ibis | u / bird | 16 | 4 | 110,111 / 112,113 | 112,113 | 114,115 / 116,117 |

The counting switch also mentions map IDs without corresponding constructors (including 46 and 51). They are not additional recovered animal species. Fish retain static presentation until their specific interactions are ported.

## Verified state rules

- **Passive (`l`)**: wander near the original home cell, then sometimes rest. Completing a move picks resting with probability 1/5; rests last `(50 + random % 50) * 100` ms, or 5.0–9.9 seconds. At expiry, probability 1/5 extends the rest. Turtles do not enter rest from walking on terrain 4. No player flee branch is present.
- **Prey (`q`)**: same wander/rest family, interrupted by Robinson within two tiles on both axes. It selects an away displacement bounded relative to the home radius 7 and switches to the faster tier. Distance is Chebyshev/tile distance, not a circular pixel radius.
- **Predators (`d`)**: react within two tiles by pursuing rather than fleeing; original sailing suppresses reaction. Their ground tests are distinct: alligators apply no terrain exclusion in this movement branch; other predators reject terrain 4,5,14. This is not replaced with an invented species habitat rule. Rocks, decor, occupied destinations and the player cell still obstruct them.
- **Birds (`u`)**: ground walking, resting, and flight. Within two tiles, a grounded/resting bird takes flight away from Robinson. Flight clears its object occupancy; it can pass over otherwise blocked terrain and objects. Flight lasts 7.0–11.9 seconds before testing for landing. Landing requires a free walkable cell outside Robinson's cell; the random transition may instead continue flight. Ground/rest transitions select rest with probability 1/5, flight with 1/5, otherwise walking. Safe landing is checked again on later updates if blocked. Some original seagull/pelican placements begin on terrain 5/6 even though walking into those cells is forbidden; saving preserves this legitimate starting state.
- **Sharks (`i`)**: move only on terrain 5/6 with free decor/rock/object tests, use three-frame directional motion, and react toward nearby Robinson. Their wander offsets are `160 - 16*(random % 20)` pixels per axis. The proximity branch actually assigns raw tile deltas; the port retains this source detail rather than multiplying by 16 speculatively.

Ground families use a seven-tile home radius parameter. The original random displacement function reduces each axis range as distance from home increases; it is not pathfinding. Both axes may advance in one update, with no diagonal normalization. Blocked axes stop until another displacement is selected. Source movement is therefore capable of getting caught among vegetation; the port does not invent avoidance/pathfinding AI.

BS9 provides the recovered ground shadow (region 3), generic shallow-water overlays (2/6), shark wakes (7–10), and alligator wakes (11–14). An alligator has an additional offset ground shadow. Flight uses the bird's flight frames, without a guessed animation grid or added altitude offset.

## Hazards recovered but paused

`d.a()` accumulates contact time within one tile while Robinson is not sailing. After more than 1000 ms it subtracts a percentage of maximum reserve `p.d` from current reserve `p.c`: alligator/jaguar 50%, green snake 10%, boa 20%. Equipment checks reduce those to 25%,25%,5%,10% respectively (trousers, shirt, shoes, hat slots). The shark contact branch sets the reserve to zero and the loss flag. These are verified numeric operations; this document does **not** relabel the reserve as a separate health meter. These contacts now integrate with energy, worn slots, interrupted short rest and collapse; see WORLD_HAZARDS.md for the verified call path and source loss quirks.

**World-contact animal hazards are now playable; generic meat drops are not invented.** Bow hunting now removes the actual caught actor and grants its original animal item; see HUNTING.md. Net and rod fishing are now implemented separately; see NET_FISHING.md and ROD_FISHING.md. Animals cannot be gathered through generic resource pickup; a bow uses the dedicated hunting mode. These deliberate milestone boundaries appear in settings. No new food loop is substituted.

## Browser implementation and adaptations

`WildlifeSystem` is a pure TypeScript simulation; `Animal` draws the recovered regions in Phaser. `IslandScene` advances visible actors at fixed 90 ms ticks only during active play. The source updates entities from its visible rendering path; the browser uses its larger camera rectangle plus a 32-pixel edge margin. Display size therefore affects the set of active actors. Hidden-tab delays are capped at 180 ms per update; no large catch-up occurs. Sprite counters use simulation ticks rather than the Java renderer's exact draw scheduling.

The browser uses a deterministic per-animal xorshift stream instead of `java.util.Random`. It preserves probabilities and timing ranges, not the original random sequence. Animal update order is original spawn-cell order rather than Java's family-array order. Continuous pixel positions replace separate tile/subtile fields; movement collision is tested when entering a different tile, with explicit world bounds. Facing follows actual horizontal movement; flight draws above vegetation. Small original asymmetries/edge arithmetic are not emulated. These choices are implementation adaptations, not newly recovered facts.

The raw five map layers remain unchanged. A separate object array and ownership grid track current ground occupancy. Flight releases a cell; landing and motion cannot overwrite static objects or another ground animal. Player collision, click paths and inspection read this runtime grid. A collected-resource cell can later be occupied by wildlife and must block movement again. Static rendering skips only the original cells managed by these 16 profiles.

Saves retain stable original spawn ID, map ID, current pixel position, state, remaining displacement, facing, timer, duration, animation counter and random seed. Snapshot arrays are detached copies. Saves lacking wildlife remain compatible and instantiate original placements. Structural validation rejects invalid values and duplicate IDs; scene restoration validates a complete matching population and ground occupancy before applying it atomically. Incompatible populations fall back to original wildlife placements while preserving expedition progress. Fractional time below one 90 ms tick is not serialized. This is browser IndexedDB data, not Java RMS compatibility.

## Validation and remaining comparisons

Automated tests check source-frame references and counts, flee/pursuit/passive response, bird takeoff and safe landing, water restrictions, moving occupancy and inspection, view/tick gating, 1500 updates without map mutation or overlapping ground animals, deterministic save continuation, and old/malformed saves. See VALIDATION.md for browser observations. No emulator comparison is claimed; exact runtime filtering, family update order, draw scheduling and original save serialization remain to compare.
