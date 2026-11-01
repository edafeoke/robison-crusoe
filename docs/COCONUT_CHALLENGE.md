# Coconut-throwing minigame recovery

The fifth milestone ports the original coconut-throwing interaction. It is attached to the actual coconut-bearing palm composite, uses source sprite regions and integer projectile arithmetic, and settles its original inventory reward and stone-wear counter. Hunting and the two fishing games are investigated but remain deferred.

## Execution path and eligibility

`data.a` checks the decoration/variation arrays near Robinson for **decor 18 / variation 8** with `a(18,8)` and its three direction counterparts. `g` initializes `d[18] = 18`; `g`'s special decor renderer sends variation 8 to **`h.i(int,int)`**. That composite draws the tall palm trunk/fronds and two coconuts using the original packed references. This is not a guessed association from a contact sheet.

On that branch, `a.c(-1)` offers action 121, whose menu sprite is 851985. Selecting it calls `n.b()`, sets `p.m = 7` and `p.q = 2`. `p` dispatches this mode to **`data.n`**. `n.b()` scans supplies for zero-based **Stone item 3** and refuses to enter normal play without one. The recovered opening text is bank-0 entry 11; controls entry 12; warning 90; no-stones message 91.

Other minigame dispatches are `p.q = 0` → `x` rod fishing; `q = 1` → `x` net fishing; `q = 3` → `s` hunting. `t` is RMS serialization, not a throwing minigame. Names are obfuscated; these attributions follow actual dispatches and consuming methods.

The browser uses a 44-pixel interaction radius in place of the original directional three-cell tests. Palm inspection is a separate focus kind from a loose Coconut object; it cannot be gathered as a normal pickup. Mouse selection can inspect the palm even when a nearby animal is closer to Robinson. The start action independently verifies actual decor/variant, distance, stone inventory, readiness and player activity.

## Recovered game rules (`n`)

- Generate **5–7 coconuts** on unique eligible leaf anchors, excluding the first three generated leaves. Leaf count is `9 + random % (width / 12)`, bounded by the available grid.
- Leaf positions lie on a 14×5 pixel grid with origin (15,30). Packed leaf regions are **458805, 458806, 458807** (BS7 regions 53–55). Coconut is **851970** (BS13 region 2); the projectile stone is **851985** (BS13 region 17).
- Coconut collision box is ±7 horizontally / ±5 vertically; stone collision box is ±4 on both axes. Tests use strict rectangle overlap.
- First hit puts a coconut into a shaking state. Another hit within the approximately **11-second** window knocks it down. The code tests `shakeElapsed > 11000` before adding another frame interval; the port retains this order.
- One stone cannot supply both hits to the same initially attached coconut. Source per-shot flags reset after landing. A stone may contact several different coconuts on one trajectory; no artificial one-target limit is added.
- A falling coconut advances **5 pixels per update**. Victory is triggered when a falling coconut reaches ground while at least **5** have been knocked loose.
- Maximum **10 throws**. After the tenth stone lands, fewer than five knocked loose loses. With five already loose, falling animations can complete the win.
- Angle control field ranges **10…70**, in steps of **5**. Power ranges **40…100**, in steps of **2**. Initial values are 70/40, and the original resets them after every throw.

`p.b(11)` supplies the original 11 FPS target (integer loop period 90 ms); the port uses fixed 90 ms minigame steps. Shake displacement cycles through 0/−1/0/+1 horizontally. Gameplay rendering and input run in a dedicated Phaser scene; React owns the surrounding controls and readable counters.

## Projectile arithmetic and the CFR omission

The throwing class shares coefficient arrays stored on `x`. The first array initializer is **missing from CFR's `x.java` output** even though its field is declared. The complete constructor initialization is recoverable in `evidence/bytecode/data.x.txt`:

- `a:[I`, constructor offsets 19–114: `[26,34,42,50,57,64,71,77,81,87,91,94,96,98,99,100]`.
- `b:[I`, offsets 117–211: `[97,94,91,87,81,77,71,64,57,50,42,34,26,17,9,0]`.

The value 96 is retained exactly; these are not replaced with runtime sine/cosine estimates. `tools/recover_coconut.py` extracts both arrays from constructor push/iastore instructions and writes `evidence/coconut-rules.json` plus `src/game/data/coconutRules.ts`. `npm run decode` invokes this recovery after the atlas/map/object export.

The browser uses a **240×320 virtual reference viewport**, scaling its display to fit desktop browsers. This is a browser choice, not a claim that all original devices had fixed dimensions. The original formulas depend on device width/height; comparisons on another MIDP resolution may differ.

Let `W=240`, `H=320`, `G=H−50`, `R=ceil(sqrt(G²+min(W−10,G)²))`; here R=355. Array index is `trunc((angle+15)/5)−3`. The original's offset is retained rather than silently converting the displayed angle into an idealized physical degree value.

`CoconutSystem` preserves Java's intermediate integer divisions and update order: vertical position uses the previous horizontal position, then horizontal position advances from elapsed time. A reference throw at angle 40 / power 70 starts with `(19,270), (28,245), (37,222), …` at successive updates. The browser does not substitute Arcade Physics gravity or a guessed parabola. The aim-sector color/geometry follows the original fillArc reference; the surrounding expedition interface is new.

## Rewards, costs and saved wear

The victory branch of `n.a()` increments **Coconut item 1 by one**, regardless of the five-or-more targets knocked down. Text 89 says “You've won a coconut!” Failure uses text 83. Both completed outcomes apply Stone's CSV energy effect (`p.m[3]`, **−3 energy** in this supplied JAR).

The original has a **separate completed-round stone-wear counter** `n.a:I`; it is not the coconut-opening counter used when eating. `t` serializes/restores it (javap `getfield/putfield data/n.a:I`). Before completion, a counter below 5 increments without spending a stone. When the previous counter is at least 5, one stone is consumed: success resets the counter to **1**, failure to **0**. This branch asymmetry is retained, not replaced with one stone per throw or a generic durability rule.

The store settles a transition from playing to won/lost exactly once. It retains a successful reward before applying a fatal energy cost, matching source ordering. Repeated updates of a result cannot add more rewards. Version-1 browser saves gain optional `coconutUses` (integer 0…5); earlier saves default to zero. Food-opening wear remains in `survival.stoneUses`.

World movement, inventory mutations, crafting, rest and save/load are locked during a challenge. The UI explains that saving requires returning to the island. In-progress shot state is transient and is not misrepresented as saved; completed inventory changes and wear are saved normally afterward. Returning from a result restores the same world position and camera in the existing single Phaser game instance.

## Browser adaptations and safety fixes

1. **Palm variation selection:** the previous browser linear variant formula left all 18 displayed coconut palms outside the starting shore's 1203-cell walkable component. Original `g.b()` chooses palm variants randomly, so those exact variant placements are not fixed source map data. Decor-18 variation now uses a stable integer hash; other decorative families keep their earlier selection. The same 13 recovered composites remain available. The current island displays 25 coconut palms, including a reachable one at **(28,160)**. Collision and all original byte layers remain unchanged. Renderer and interaction checks share `DecorVariants.ts`, so a tree's appearance and action agree. A regression check verifies a path from the starting shore to this palm.
2. **Target-generation guard:** source can choose 7 targets with only 6 eligible leaves when total leaf count is 9. Its uniqueness-rejection loop can then run forever. Browser target count is capped by eligible anchors; five targets remain available. Two thousand deterministic layouts are checked for termination and bounds.
3. **Randomness:** a deterministic per-session browser stream replaces `java.util.Random`; initial seed combines palm cell ID and a transient session serial. Exact random sequences and placements are not claimed recovered. This makes the first layout at a palm repeatable after reload.
4. **Presentation/controls:** React sliders and click/Space/Enter throwing complement the original arrow controls. A readable shaking count supplements the recovered ±1-pixel jiggle. The original warning/controls appear as a persistent instruction panel rather than an eight-second skippable interstitial. Result settlement happens when the game resolves rather than after another skippable eight-second message.
5. **Pause and cancellation:** settings/hidden tabs pause active game time; survival otherwise continues through the existing browser reserve model. Exploration mode keeps needs full. Leaving an unfinished challenge abandons it without reward or completion wear/energy charge; this explicit cancellation control is a browser usability choice. It does not delete or overwrite an earlier save.

## Verification boundary

Automated tests verify source constants and all referenced packed regions, palm appearance/action identity and reachability, target generation, exact projectile points, hit-window/falling states, a complete six-throw win, ten-miss loss, source wear/reward arithmetic, one-time store settlement, fatal energy completion, active-action locks, pause and save compatibility. Browser observations and any automation limitations are recorded in VALIDATION.md. No MIDlet emulator equivalence is claimed.

Net and rod fishing are now ported; see NET_FISHING.md and ROD_FISHING.md. Supported bow hunting is now ported in HUNTING.md. Clothing protections, and original story gating still need complete ports. No generic animal pickup, fishing loot roll or invented hunting recipe replaces them.
