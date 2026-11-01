# Shallow-water net fishing

Milestone six ports **net fishing only**, from `data.x`. Rod fishing is now ported separately (ROD_FISHING.md); supported bow hunting is now ported separately (HUNTING.md). The original JAR/extracted assets remain intact. No fish objects, terrain tiles or recipes were invented to create fishing locations.

## Entry path and equipment

`data.a.g/h/i/j()` checks original FULL terrain **4**, then calls `c(59)`. Item 59 is Goldfish; its CSV hunting-tool row is **83 (Net)**. `a.a(byte,int)` maps tool 83 to action sprite **851986**; selecting that action invokes `x.f()`, sets `p.m=7`, `p.q=1`. `p` dispatches presentation to `x.d()` and active fire input to `x.e()`.

`x.f()` scans supplies for item **26 (Potato)**. No potato displays original text 88: “You can't go fishing, because you haven't got any potatoes for bait”. The equipment requirement is checked by the world menu; the browser also validates it at the store boundary. Net and bait are independently required.

Net's existing original recipe is **Rope×4 + Stone×4**, with a reusable **Knife**. Rope is **Vine×2 + Branch×1**, also requiring Knife. Existing crafting/workstation limitations are documented in ITEM_RULES.md; this milestone does not claim to complete original workstation gating. Potato collection requires Knife. Nothing in this delivery grants equipment to a new production expedition.

The browser finds nearby terrain-4 cells within 42 pixels; entry rechecks terrain, index, distance ≤44, inventory, ready/started state, collapse, rest and other active minigames. Directional MIDP adjacency is adapted to the browser's existing radial interaction. Nearby objects/palms take precedence over a water prompt, so shore resources remain inspectable with E; clicking the water explicitly also works. Terrain (12,148) is reachable from the starting shore. Source map bytes and collision rules are unchanged.

## Recovered simulation

The source depends on screen dimensions. **240×320** is a browser reference viewport, not a recovered universal device resolution. Frames scale with original D0 anchors and mirroring; no sprite sheet is rearranged.

| Rule | Source behavior at the browser reference size |
| --- | --- |
| Target / casts | At least 10 fish within 5 casts |
| Input | Fire releases a net in a fixed central lane; no horizontal net steering |
| Net | 32×16 hit region; packed display region 851984 (BS13 region 16) |
| Start | Relative y = −height/2 +35 −netHeight/2 = −133; absolute center y=27 |
| Descent | +5 pixels each active update; bottom threshold y+8 ≥ height/2−45 |
| School | 3…7 fish, refreshed after each completed nonterminal cast |
| Spawn x | From left −(random %60), or right width+(random %60) |
| Spawn y | 42 + random %(height−80−14), i.e. 42…267 |
| Speed | 2…4 pixels per update toward the opposite edge |
| Respawn | Beyond width+70 or −70, with ±8 fish padding |
| Capture | Strict center containment inside net bounds; equality on an edge misses |
| Captured fish | Stop horizontal travel; descend +5 per update with the casting net |
| Haul | Count captured flags once, remove those fish, hold for >2000 ms; uncaught fish flee at 10 pixels per update |
| Frame cycle | Three directional original frames; one advance per active update |
| Timing | Existing source 11 FPS target, integer 90 ms fixed updates |

`x.d()` calls `l()` (fish movement), `k()` (net/haul), then `m()` (capture). The port preserves this order, including fish movement before net advancement and counting before the final collision pass. Large browser deltas are capped at 180 ms; source wall-clock scheduling is adapted to fixed active ticks. A held Space is not treated as repeated casts; casts cannot overlap.

`x.c(int)` selects five frame families by a roll below **45 /65 /75 /85 /100**. `tools/recover_fishing.py` extracts the assignments, verifies the mirrored directional branches, and exports `evidence/net-fishing-rules.json` and `src/game/data/netFishingRules.ts`. It runs as part of `npm run decode`. Fish frames use BS2; net/tool frames use BS13. All regions resolve through the existing original asset loader.

The browser uses a deterministic xorshift stream seeded from shore index and a transient session serial. It preserves ranges and thresholds, not `java.util.Random` sequences. Source screen-size changes and PRNG differences can change individual schools.

## Inventory effects and the reward quirk

Both completed outcomes consume exactly **one Potato**, keep the Net, add **1 strength**, and apply **−10 energy** from the original Net CSV (`p.n[83]` and `p.m[83]`). There is no bait cost per cast and no coconut-style wear counter. A win adds **one item**, not ten fish to inventory.

The original victory branch randomly selects item IDs **59, 60, or 2**. In the supplied English data these are **Goldfish, Rug, Gray fish**. `javap` confirms the literal 60; it is not silently changed to a guessed fish ID. The browser retains this branch and explains a Rug reward in the result. Rug has sprite −1, so the generic missing-icon handling remains available in the satchel. The displayed fish families and final inventory reward do not correspond one-to-one in the source.

Settlement happens exactly once when the game resolves. Inventory effects precede the activity energy cost; a reward survives a fatal cost. Survival's normal minute depletion continues during active fishing; exploration mode keeps reserves full. Strength still changes in exploration mode, matching other existing item effects.

Completed inventory/survival changes use the existing version-1 IndexedDB save. No new persistent field is required. In-progress schools/casts are transient and save/load are locked until returning to the island. Settings and hidden tabs pause simulation; world movement, wildlife, rest, collection, crafting and other minigames are locked. Returning restores the same world position in the single existing Phaser instance.

## Explicit browser adaptations

- React instructions, counters, click/Space/Enter cast controls, readable result and responsive journal frame replace device-specific interstitials and touch/keypad controls.
- The source four-second target warning is persistent instruction text. Completion effects are settled on resolution rather than after a skippable result wait. The two-second haul is retained.
- Source increments the exhausted cast counter to six in its loss branch. Browser keeps the truthful **5/5** display after five casts; this does not grant an extra cast.
- Leaving an unfinished round is an explicit cancellation enhancement: no reward, completion energy/strength effect or bait consumption. Earlier saves are not overwritten. This matches the established coconut challenge cancellation policy.
- Fish are clipped to the original field; the surrounding journal HUD is new. Original sprite anchors, colors and lane dimensions are retained.

## Validation boundary

Seven new tests cover source/packed frames, terrain access and reachability, 1000 deterministic schools, strict capture/update order/haul, real timed four-cast win (12 fish), five-miss loss, exact inventory outcomes including Rug, pauses/action locks and one-time/fatal settlement. Existing map/asset, coconut, survival and wildlife tests remain required.

`tests/browser/net-fishing.html` is an explicit **dev-only UI fixture** with a labelled setup button supplying Net×1 and Potato×2 on a separate QA origin. It uses the actual App, scenes, store guards and original map/assets. It is not imported by the production entry or included in dist. This fixture avoids changing users' saves or pretending equipment was obtained through a manually played full crafting chain. Browser observations, production/offline checks and any limits belong in VALIDATION.md. No original MIDlet emulator equivalence is claimed.
