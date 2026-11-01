# Deep-water rod fishing

Milestone seven ports the rod mode of the original `data.x`. Net fishing and coconut throwing remain intact. Hunting is now ported separately; see HUNTING.md. Original archive bytes, map layers and sprite sheets are unchanged.

## Entry and source dispatch

`data.a.c/d/e/f()` checks FULL terrain **5** and calls `c(40)`. Item 40 (Brown fish) declares specialized tool **14 (Fishing rod)**. `a.a(byte,int)` maps this tool to action sprite **851976**; selecting it calls `x.c()` and sets `p.m=7`, `p.q=0`. `x.c()` scans supplies for **90 (Worms)**. Original text 85 is the no-worm warning. The rod is retained; one worm is required to begin.

The browser uses the existing radial interaction: 42-pixel prompt, ≤44-pixel start recheck. A player at **(8,150)** can inspect deep water at **(8,151)** near the starting shore. Terrain 5 remains blocked for walking. No fish object is placed in the Objects layer to manufacture a fishing location. Nearby objects/palms retain prompt priority; explicit water clicks can select fishing instead.

Rod's original recipe is **Vine×1 + Bamboo×1 + Nail×1**. Worm collection requires Knife. The existing workbench uses recovered recipes; this milestone does not add equipment to a new production expedition or complete deferred workstation/placement restrictions.

## Update order, coordinates and controls

Bytecode identifies `p`'s rod presentation call to `x.a()` and subsequent `v.b()`, which dispatches `x.b()` for controls. Within `x.a()`, fish motion `g()` precedes hook-setting `h()`. The pure port follows **fish motion → queued hook setting → control/history update**. No Arcade Physics approximation or auto-catch is substituted.

The browser uses a **240×320 reference viewport**, scaled within the game field. This is an explicit browser choice; original formulas depend on device dimensions. All positions below are relative to the reference viewport's center.

| Behavior | Recovered rule |
| --- | --- |
| Loop | Existing source 11 FPS target / integer 90 ms fixed updates |
| Float start | `(0,−20)`; hook starts `(0,0)` |
| Rod control | Left/right/up/down, first matching direction only, step 3 pixels |
| Horizontal limit | Check current float against ±(width/2−23), i.e. ±97, before moving |
| Vertical limit | Unhooked hook checked against ±(height/2−37−height/11), i.e. ±94 |
| Hook offset | Vertical hook = float y+20 |
| Horizontal lag | Five-slot history shifts and appends the new float x; oldest slot supplies hook x (four updates behind the newly appended value) |
| Idle sway | First adjustment on control update 4, then every 3 updates; original counter bounds/hold steps retained |
| Free fish start | `(−width/2+1,0)`, i.e. `(−119,0)` |
| Free horizontal motion | 1 pixel/update, reverses at ±width/2 |
| Free vertical motion | ±1 pixel/update, direction chosen by random %20; segment 5+random %(width/10); bounded at ±81 |
| Hook setting | Strict hook-point containment inside the fish's integer half-width/half-height; an edge equality misses |
| Hooked pull | Moves 1 pixel outward according to hook x's sign; y moves down 1 each update; controls then update the hook and fish follows it |
| Side loss | Reaches or passes x ±97 during the pull |
| Bottom loss | Reaches or passes y +94 during the pull |
| Success | Hook moves **strictly above −94**; being exactly at −94 is not enough |

Source bounds are checked **before** the three-pixel control step. Consequently a float at 96 can move to 99; the port does not clamp away this source behavior. Idle sway can also slightly exceed a checked boundary.

The five-slot history and fish pull make reeling a skill interaction. Holding up produces a net upward movement while hooked, but catches near an edge may require lateral corrections. Setting an empty hook loses the attempt. The browser's direction hint is guidance, not a guarantee of the next tick's collision; the fish moves before hook-setting is evaluated.

## Species and original graphics

`tools/recover_rod_fishing.py` extracts these assignments from `x.c()` and exports `evidence/rod-fishing-rules.json` and `src/game/data/rodFishingRules.ts`. `npm run decode` includes this recovery.

| Item | Species | Hitbox | Left frames | Right frames |
| --- | --- | --- | --- | --- |
| 40 | Brown fish | 16×10 | 131100…131102 | 131103…131105 |
| 56 | Blue fish | 16×9 | 131106…131108 | 131109…131111 |
| 57 | Yellow fish | 16×12 | 131094…131096 | 131097…131099 |
| 58 | Puffer fish | 20×14 | 131168…131170 | 131171…131173 |

Height 9 uses Java integer half-height **4**, not 4.5. A uniform source random %4 selects the species. Original fish animation cycles three directional frames. The source draws packed **851979** at the float/rod-tip position and alternates bait/hook regions **851980/851981** in three-update groups. Tool icon is **851976**. The white segmented rod/line geometry uses source coordinates and Java-style truncation for its thirds. Original D0 anchors, mirroring and atlas regions are retained; no sheet cropping/repacking is performed.

The existing atlas loader already loads these BS2 and BS13 banks. Missing-region handling remains in place. A thin optional hook marker and readable relative-direction hint improve visibility on desktop; these are browser additions.

## Completion, inventory and persistence

Both completed outcomes:

- Consume **Worms×1**.
- Keep **Fishing rod**.
- Add **1 strength**, then apply **−5 energy**, from `p.n[14]` and `p.m[14]`.

A successful attempt grants **one fish of the selected species**. It does not use the net game's random reward table or its Rug branch. Source text 84 reports success; text 83 reports loss. Inventory changes are applied before the activity cost, so a fish remains awarded if the final energy cost is fatal. Settlement is guarded by the transition out of playing; later updates cannot charge another worm or duplicate a reward.

The shared `FishingCompletion.ts` contains the source strength/energy ordering for both rod and net modes. Existing version-1 IndexedDB saves already persist inventory and strength/energy; no new persistent field or format bump is necessary. In-progress rod state is transient. Save/load, rest, inventory mutations, crafting, world motion, wildlife and other minigames are locked until returning to the island. Settings and hidden tabs pause active time. Reserves otherwise continue through the existing survival model; exploration mode keeps the three reserves full but still records strength changes.

## Browser adaptations and limits

- WASD supplements the original arrows. Clickable buttons nudge the rod one control update; holding a movement key gives continuous movement. Space/Enter or a water click sets the hook. Native button keyboard activation is preserved when a UI button has focus.
- A new Phaser scene runs within the existing game instance. React owns instructions/results, pause and controls. Field scaling reserves room for both side panels at desktop widths.
- Source's skippable result delay/exit lift is replaced by immediate settlement and an explicit Return button, as in the earlier minigames. The successful gameplay boundary is unchanged.
- Leaving an unfinished attempt abandons it without reward, worm consumption or completion strength/energy cost. This is the existing browser cancellation policy, not a recovered original action.
- Fixed active ticks (large deltas capped at 180 ms), deterministic xorshift randomness and reference resolution differ from original device scheduling/`java.util.Random`.
- Each browser attempt initializes transient animation/sway/wander state freshly. Original `x` is shared across modes and retains some private counters between entries; exact cross-attempt/cross-mode carry is not claimed reproduced.
- No original MIDlet emulator comparison is claimed. World-contact animal damage, equipment/clothing protections, construction, original story progression and boat escape remain deferred.

## Validation

Eight new automated tests cover source assignments, original terrain access/reachability, exact initial movement/history/sway points, immutable deterministic wandering, strict hook bounds and ordering, side/bottom/strict-top outcomes, genuine control-only wins for all four fish, and store/equipment/save/pause/cancellation/fatal-cost restrictions. Existing net, coconut, survival and asset/map checks remain required.

The dev-only `tests/browser/rod-fishing.html` fixture explicitly supplies a rod and two worms on an isolated QA origin; it is not part of the production build. Manual full recipe acquisition is not implied by fixture preparation. Actual browser observations, screenshots and remaining verification limits are in VALIDATION.md.
