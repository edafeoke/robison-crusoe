# Direct world-food consumption

Robinson can now **Eat … here** from a nearby original food object's discovery panel. This ports the verified `data.ad.j()` world-use path separately from satchel food use (`ad.g/h`). Gathering remains available through its existing source rule. No food is relocated, respawned, replaced with a generic resource or automatically added to the satchel.

## Eligibility and recovered action flow

`a.c(int)` first handles special structures and the hunting-tool branch. Only then does it examine the object's use-tool byte (`p.c:[B`, CSV row 1). Zero offers world USE immediately; a positive tool ID must be found in supplies or equipment. Missing the use tool still permits TAKE through `a.C()`; `a.D()` offers USE, TAKE and EXAMINE. USE icon **655387** dispatches to `ad.j()`, whereas satchel USE dispatches to `ad.g()`. Original facing determines the source cell; the browser retains its already-established **44-pixel interaction distance** and mouse selection as an explicit adaptation.

`tools/recover_world_food.py` checks this branch order, the always-true OR expression, both energy assignments and fatal returns against CFR/javap, and exports `evidence/world-food-rules.json` / `src/game/data/worldFoodRules.ts`. Combining the recovered menu gates with the typed original object table identifies **22 static food types**, all with actual original Objects-layer placements. Hunting/fishing targets and special structures do not gain an eat-in-place shortcut. Coconut-bearing decor palms keep their throwing challenge; loose Coconut objects use the new food action.

| Owned use tool | Original item IDs and foods |
| --- | --- |
| None | Mango 0, Banana 24, Papaya 25, Potato 26, Red berry 28, Mussel 71, Egg 75 |
| Stone 3 | Coconut 1 |
| Knife 76 | Pineapple 27, Oyster 66 |
| Fire 16 | White mushroom 30, Red mushroom 31, Cream mushroom 32, Orange mushroom 33, Spiral 64, Whelk 65, Clam 67, Conch 68, Sea snail 70, Scallop 72, Starfish 73, Sea urchin 74 |

The gate checks an owned item; no nearby workstation or new cooked-food recipe is inferred. Fire does not negate Red mushroom's negative Energy effect.

## Effect order and source differences

`ad.j()` converts current reserves to integer percentages, then:

1. Applies Hunger if its raw effect is not −1, capped at 100.
2. Applies Thirst if its raw effect is not −1, capped at 100.
3. Applies Energy once; nonpositive energy invokes loss and **returns immediately**.
4. Enters `useTool != 0 || useTool != -1`, an always-true source condition. Applies Morale/Strength, then Energy again. The second nonpositive-energy result also returns immediately.
5. After passing both energy checks, calls the nutrition routine once with **raw Hunger**, clears only the addressed Objects cell, exits the action menu and redraws.

This is not the same as calling the satchel food function:

| Case | Satchel path | World path |
| --- | --- | --- |
| Mango Energy | +8 | +16, two capped applications |
| Positive-tool Hunger/Thirst of −1 | Applied literally | Skipped |
| Nutrition | Once, according to satchel sentinel/tool branch | Raw Hunger once after successful energy checks |
| Tool stacks | Mostly retained; Stone wear every six coconut openings | Retained, no tool-stack update |
| Coconut-opening counter | Advances and may spend a Stone | Unchanged |
| Food inventory | Uses one owned item | Unchanged |
| Fatal food | Satchel item settlement is retained | World object remains; no nutrition call |

No extra gathering energy, creation effect or food-item inventory reward is charged on direct use. Source world food has no verified separate eating animation or new sprite mapping; no fabricated one is attached.

## Fatal-state and exploration adaptations

Source `ad.j()` calls loss **before assigning the failing energy value**. A fatal first pass can therefore leave the old reserve visible, and a fatal second pass can leave its first-pass value. The browser explicitly normalizes a fatal reserve to **zero**, preserving the earlier effects, early return, unremoved object, unchanged tools and absent nutrition. This keeps collapse/save validation consistent.

The source world dispatcher also checks the currently selected **inventory** ID (`p.m:[B` / `p.z:I`) to set the poison flag before using the world object. That stale selection is not reliable evidence of which object caused the loss. The browser deliberately uses the actual consumed food's negative Energy effect to display the existing source poison text (story 110). The only negative-Energy static food in the recovered set is Red mushroom. No new poison-duration mechanic is added.

Exploration mode remains an explicit browser option: it suppresses fatal food loss, keeps reserves full and permits successful object removal/nutrition settlement. This overrides the source fatal-return branch rather than claiming original debug/friend-mode equivalence. A player recovering from collapse can inspect the still-present mushroom again.

## Object identity, collision and saves

The store independently verifies readiness, started/alive/non-resting play, absence of active minigames, integer IDs/cell bounds, actual runtime map ID, distance, supported food/tool and absence from removed cells. Forged, remote, stale or duplicate requests cannot award reserves. UI eligibility uses the same target/tool helpers.

Successful use appends the exact original static cell to the existing version-1 `collected` removal list, without changing inventory. That list already represents runtime removal for gathering and hunting; it is not restricted to carried supplies. `IslandScene` removes the corresponding rendered object and runtime occupancy through the existing path. Original binary/JSON layers remain immutable, decor/rocks/terrain are preserved, and the cell becomes walkable only if its other source layers allow it. A second use fails even before rendering catches up. Fatal use does not append a removal.

Normal IndexedDB saves already persist removed cells, needs and the optional Weight/nutrition state, so no new save version or migration is required. Loading reconstructs the same removal and cannot grant the meal twice. Collapse still prevents overwriting an earlier save. The browser exploration-guide wording now says gather/hunt/eat, reflecting removal-list semantics; it is not claimed to be the original story quest graph.

## Validation and remaining work

Nine automated checks cover all 22 source profiles and exact effects, satchel/world distinctions, tool/wear separation, both fatal passes, exploration, live-cell/range/type guards, atomic store settlement, original-map/runtime collision, activity locks and normal save/reload. Browser QA uses a visibly labelled development fixture to position Robinson near real unchanged original foods and supply specific tools/needs/nutrition. No manual tool creation or cross-island journey is claimed. See VALIDATION.md for measured outcomes and production/offline verification.

Exact facing footprints, source menu/icon presentation, emulator comparison, remaining special-object actions, boat/raft/story escape, weather/tides, non-sleep offline arithmetic and RMS save compatibility remain outstanding. No complete original adventure or exhaustive manual tour of all food placements is claimed.
