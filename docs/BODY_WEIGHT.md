# Body weight, nutrition and carrying-capacity audit

The original **Weight** stat is now playable through inventory food consumption, displayed alongside Strength and Morale, and saved independently from reserves. It is not item mass. No encumbrance mechanic or guessed inventory-slot limit is added.

The original label is simply **Weight**. Interpreting this food-driven character bar as body weight is an inference from its call graph; the source does not provide kilograms or another physical unit. The nutrition arithmetic and lack of a carrying gate in the audited paths are verified separately below.

## Verified fields and call graph

Obfuscation reuses names with different JVM descriptors. The authoritative bytecode identifies:

| Field | Meaning | Verified consumers |
| --- | --- | --- |
| `p.g:F` | Weight status-bar value | `v.a(int)` growth, `v.b(boolean)` reset, `aa` stat drawing, `t` serialization |
| `p.L:I` | Accumulated food/nutrition counter | `v.a(int)` growth, reset and `t` serialization |
| `p.d:F` | Common status-bar maximum | Initialized by `p.c()` to `floor(screenWidth/3)−16` |
| `p.g:[I` | Equipment stack quantities | Separate field; not the float Weight stat |

Source text index **75** labels the sixth status row “Weight.” `aa` draws `p.g:F` using the original **BS10 frame 38** icon (packed key 655398). Adjacent Strength/Morale icons are frames 36/37. Across the retained bytecode, references to `Field data/p.g:F` occur only in `aa`, `v` and `t`: no movement-speed, attack, pickup or crafting consumer of this field was found. This is evidence for a nutrition/body stat, not carrying weight or a body-size animation.

`tools/recover_weight.py` extracts the threshold, step and exact reserve-initialization width formula into `evidence/body-weight-rules.json` and `src/game/data/bodyWeightRules.ts`. The extractor anchors the reserve assignment specifically; unrelated loading-screen centering calculations are not used. Tests also verify descriptor users and the original stat-icon regions.

## Exact nutrition arithmetic

`v.a(int nutrition)` performs these operations:

1. Add the argument to integer `p.L`.
2. If the result is at least **50**, subtract **50 once**.
3. If `p.g` is below `p.d`, add **one native bar unit**.

This is a single **if**, not a loop or modulo operation. A 100-nutrition meal at zero produces Weight 1 and remainder 50, rather than two weight steps. A subsequent +3 meal produces Weight 2 and remainder 3. Remainders may exceed 49, even after the bar has reached its maximum. The counter can be negative; Java's signed 32-bit addition is retained. No time-based weight loss, kilogram conversion, food-protein taxonomy or starving/overweight penalty was found in these consumers.

`ad.g()` calls this routine once for successful satchel consumption: the positive use-tool branch passes the raw hunger effect even when it is **−1**; the free-food branch skips the call only when hunger equals the −1 sentinel. Thus a tool meal's duplicated Energy effect does **not** double its nutrition. Eating at already-full Hunger still counts the original raw nutrition, rather than the capped amount actually restored. Weight is updated in the same successful transaction as item use, including fatal poison, and does not change on unavailable food, missing tools, rest/activity locks, rejected input, crafting, gathering, discarding or ordinary rest.

`ad.j()` is a separate direct-world consumption path with its own effect and fatal-return order; it also has one nutrition call. Direct world use is now independently ported in WORLD_FOOD.md; it does not route through satchel consumption. Its successful nutrition call and fatal non-commit behavior are preserved separately.

## Browser representation and saves

The original one-unit step is a physical status-bar pixel, so its normalized size depends on phone screen width. The browser explicitly chooses the same **240-pixel reference width** used by its minigame reference canvases: maximum native bar value **64**. One step displays as **1.6%** rounded to one decimal. This is a browser presentation choice, not a claim that every original device used width 240. Browser resizing, world zoom and desktop canvas size do not alter progression.

`BodyWeightSystem.ts` retains `{ barUnits, nutrition }`. `CharacterStatus.tsx` displays the three recovered attributes with their original icons. A compact HUD shortcut opens these condition details in settings. Weight is distinguished from inventory space in the interface; no new low/high-weight hazard is implied.

Version-1 IndexedDB saves accept optional `bodyWeight`. New expeditions and legacy saves without it start at zero; past meals cannot be inferred from remaining inventory. Validation checks integer bar units in 0–64 and a signed 32-bit nutrition counter, including queued values above 49. Sleep, exploration-mode changes and reserve recovery preserve this separate state. Exploration still permits food-driven growth while reserves remain full.

The original `t` writes Weight and the nutrition accumulator as **bytes**, then reads signed bytes back. The browser preserves the complete accumulator rather than reproducing that lossy RMS serialization. Original save import and device-specific bar-width migration are not implemented. Source reset/food behavior is verified by bytecode; no Java ME execution comparison is claimed.

## Carrying-capacity findings

`p` allocates source catalogue/inventory arrays with **120 entries**, including supply IDs/quantities (`m:[B`, `q:[I`) and equipment IDs/quantities (`a:[B`, `g:[I`). `ad.a()` stacks matching IDs or appends a new type; `ad.k/n` handle unique crafted equipment or supply stacks. Their inspected branches do not compare the float Weight stat, item mass, total quantity or a player carrying-capacity statistic before accepting an item. The decoded 13-row object table contains interactions, five stat effects, recipes/tools, camp gates, clothing metadata and categories; none is an independently verified item-mass field.

These array lengths are storage implementation details covering a 120-item catalogue, not evidence of a 120-unit backpack or a 12-slot limit. The browser satchel's empty grid cells are visual spacing, not a gameplay capacity. Original byte serialization of quantities is likewise not evidence that the pickup code imposes a 255-unit stack cap. The browser retains its existing validated positive-integer stacks and 100000-per-stack save-sanity bound, explicitly not claimed as an original carrying rule. No arbitrary restriction is substituted for an unverified mechanic.

## Validation and remaining work

Eight automated checks cover provenance/icons/reference scaling; threshold/queued large meals; maximum/signed arithmetic; tool/sentinel nutrition; successful/full/fatal/blocked food transactions; independence from crafting/discard/rest/exploration; save/sleep/legacy migration; and malformed saves. Browser checks exercise the real food buttons with labelled supplied food/tools, then normal IndexedDB and production offline loading. Exact observations are in VALIDATION.md.

Remaining special object actions, boat/raft/story escape progression, weather/tides, non-sleep offline arithmetic and original RMS compatibility remain unported. No original emulator comparison or exhaustive manual menu/action playthrough is claimed.
