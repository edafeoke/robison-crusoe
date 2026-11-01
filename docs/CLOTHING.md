# Recovered clothing and hunting protection

## Verified source structure

The supplied JAR provides **16 garments**, indices 99–114, across four slots. `y.c()` initializes each slot's ownership list with -1; `v.g()` resets all selected indices to 0. New/legacy browser expeditions therefore start with no equipped garment. The underlying Robinson body art is not an equipped item.

| Slot | Source category | IDs | Source ownership/selection |
| --- | --- | --- | --- |
| Shirt | 7 | 99 T-shirt, 100 Pirate's jacket, 101 Wool jacket, 102 Hide jacket | `p.b : int[]`, selected E |
| Pants | 8 | 103 Cotton pants, 104 Loincloth, 105 Fur pants, 106 Wool pants | `p.c : int[]`, selected F |
| Shoes | 9 | 107 Hide shoe, 108 Cotton shoe, 109 Straw shoe, 110 Fur shoe | `p.d : int[]`, selected G |
| Hat | 10 | 111 Hide hat, 112 Hat, 113 Straw hat, 114 Fur hat | `p.a : int[]`, selected D |

`a.G()` dispatches garment creation by category to `ad.a(String)`, using names `hat`, `shirt`, `trousers`, `shoes`. That method adds an unowned garment to its slot list and calls `ad.l()` for creation effects. `ad.o()` subtracts recipe materials; required tools remain. Selected indices are not changed by crafting, so the browser **does not auto-equip** a new garment. `a.p()` cycles selected slot entries; `aa` renders the composed outfit selector. `t` serializes each ownership list plus the four selected indices.

All 16 recipes, materials, reusable tools, camp requirements and morale/energy effects come unchanged from objects.csv. Examples: Loincloth is Dry grass×5 + Vine×1; Straw shoe is Dry grass×4 + Vine×2; Hat is Dry grass×6 + Vine×1. These three require no camp/tools. T-shirt needs Sail×2 + Cotton thread×1, with kept Scissors and Needle, at Cabin tier. Garment labels such as Wool pants and their apparently unusual recipe ingredients are preserved rather than corrected speculatively. Source descriptions for these garments are empty; the wardrobe does not invent Robinson quotations.

CSV row 11 matches codes **0 hat / 1 shirt / 2 shoes / 3 pants** for these garments and is exported as `clothingSlot`. `y.d()` parses this row into private `y.d : int[]`; no runtime consumer of that scratch array was found. Actual slots are independently confirmed by category dispatch and `ab.j()`'s ownership-array switches. These metadata codes are **not** treated as the selector row order (whose pants/shoes rows differ).

## Exact original graphics

`ab.a()` draws the BS3 body, then `ab.j()` draws **shoes → pants → shirt → hat**, sharing one world anchor. Each non-hat garment has four directional sequences of five frames (idle + four walking). Hats have four directional single frames, positioned with direction/frame-dependent x/y offsets. Packed references, D0 dimensions, mirroring and anchors are retained; sheets are never recropped or rearranged.

`tools/recover_clothing.py` exports all references from `ab.j()` and recovers eight five-element hat-offset arrays from the **javap constructor**. CFR omits four of those initializers. For example, idle hats shift +3 x when facing left and -3 x facing right; vertical bob offsets differ by direction. All 16 profiles together cover **every one of the 256 BS5 regions** exactly.

The browser composes the same layers in Phaser, using its existing continuous movement and 125 ms walking-frame cadence. Clothes hide during the source BS9 short-rest pose, matching `ab.a()`'s separate rest branch, then return on ordinary movement. The React preview uses the same recovered layer function and anchors for four static directional views. This preview, explicit garment buttons, labels and protection table are browser UI improvements; source selector cycling is replaced by direct selection. No durability, armour rating, weather resistance or extra survival benefit is inferred.

## Hunting protection

`data.s.a()`'s retaliation block checks **whether the matching slot is non-empty**, not garment material, tier or morale value:

| Animal item | Matching slot | Bare energy loss | Protected energy loss |
| --- | --- | --- | --- |
| Alligator 42 | Pants | 50 | 25 |
| Jaguar 43 | Shirt | 50 | 25 |
| Green snake 48 | Shoes | 10 | 5 |
| Boa 49 | Hat | 20 | 10 |

These losses are additional to Bow's existing completed-attempt cost (10 energy, one Arrow, Bow kept, +1 strength). Nearby dangerous targets can combine their losses. A win retains the already documented success-priority adaptation on the source's overlapping tenth-shot branches. Other species receive no invented clothing effect.

The store captures the equipped outfit at hunt start and prevents wardrobe changes during minigames. Source code reads current selected clothes at attack time; since the browser locks the wardrobe throughout an attempt, the captured outfit produces the same protection for permitted input. World-contact hazard code (`d`) has now been independently audited and ported, including its timing, partial-rest credit and source loss quirks. It uses the same four slot/value checks, extracted separately from the hunting formula. See WORLD_HAZARDS.md.

## Persistence and compatibility

Version-1 saves gain optional `clothing: {owned: number[], equipped: {hat,shirt,pants,shoes}}`. Equipped values are null or owned garment IDs in the matching slot. Duplicate ownership, unknown IDs, missing slots and wrong/unowned selections reject validation without changing the current session. Missing clothing state migrates to empty ownership/slots, including when an older save is loaded over an equipped session. Existing satchel entries are retained; no legacy items are silently removed or promoted into wardrobe ownership.

Crafted clothing belongs to the separate wardrobe rather than the satchel, matching the original four inventory lists. Each garment is unique, tools are kept, and changing/removing a worn item does not consume it or change survival stats. The browser uses stable garment IDs instead of original list indices. Saves, explicit sleep checkpoints and cold offline reload preserve the full wardrobe and worn selections. Unfinished minigames remain unsaveable.

## Validation and limits

Nine new automated checks cover all 16 recipes/slot codes, complete BS5 frame coverage, directional golden compositions and bytecode hat offsets, unique ownership/kept tools, invalid selections, all garments against every predator, combined protected retaliation, creation effects/locks and old/new save restoration. Browser observations and prepared-fixture limits are in VALIDATION.md. All raw source sprites/maps/JAR remain intact; regenerated JSON/TypeScript rule files are reproducible.

No emulator comparison or exhaustive manual 16-garment/all-animation/retaliation playthrough is claimed. Browser QA crafts and wears four representative garments; exact other frame mappings and protected damage branches are checked automatically. Original quest/boat/raft progression, special object actions, weight, tides/weather, non-sleep offline depletion and full device-clock fidelity remain outstanding.
