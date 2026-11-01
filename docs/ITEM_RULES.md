# Recovered item rules — second milestone

Evidence is from this workspace's supplied JAR, not a generic survival design. IDs below are zero-based item indices; map IDs are index+1.

## CSV fields now established

`y.d()` starts its parser switch at 3. Follow the receiving field **type** as well as its obfuscated name: `p.b` is both a name string array and a byte requirement array in CFR output.

| CSV row (zero-based) | Parser target | Verified meaning / consumers |
| --- | --- | --- |
| 0 | `p.b : byte[]` | Pickup requirement. `a.c(int)` checks this after the use/hunting branches. 0 means no tool, -1 no pickup branch, positive value is an inventory item index. |
| 1 | `p.c : byte[]` | Use-action requirement. Both `a.C()` and `a.D()` offer TAKE; D additionally offers USE. A missing use tool therefore does not forbid taking food. |
| 2 | `p.d : byte[]` | Specialized tool action, dispatched by `a.a(byte,int)` to fishing/hunting minigames. Not equivalent to generic pickup. |
| 8 | `p.a : byte[][]` | Recipe materials. `r.a(StringBuffer)` checks supplies/quantities; `a.G()` calls `ad.o()`, which subtracts these entries. |
| 9 | `p.b : byte[][]` | Required crafting equipment/quantities. `r.a(StringBuffer)` checks equipment quantities; `ad.o()` does **not** subtract this list. |
| 10 | `p.e : byte[]` | Global camp requirement. `aa.c(StringBuffer)` accepts -1 at Flag, -1/9 at Tent, -1/9/10 at Cabin. Exported as `shelterRequirement`; boats/rafts use separate paths. |
| 12 | `p.f : byte[]` | Inventory category. `ad.a()` and `a.G()` route item storage by category. 0 supplies, 1 equipment; construction, storage, furnishings and clothing use separate branches. |

Other CSV rows retain their original strings in `rawFields`. Status rows 3–7 are now audited and implemented; see SURVIVAL.md. Row 11's matching garment slot metadata is now exported; its scratch-array parser has no confirmed runtime consumer. Actual slots are verified independently by category/renderer switches; see CLOTHING.md.

## Pickup examples

- Knife (76), Stone (3), Branch (8), Sail (12), Mango (0): no tool required.
- Vine (6), Dry grass (7), Clay (20), Tree root (35), Moss (39), Worms (90), Cotton (98): Knife (76).
- Log (11): Axe (5).
- Giant log (93): Saw (78).
- Bamboo (38): Machete (96).
- Pineapple (27) has a knife use action, but TAKE is available before obtaining the knife. Both satchel eating and direct world eating require the original Knife, with their distinct effects retained (WORLD_FOOD.md).
- Animals are not added to inventory merely because the relevant bow/net exists; the original specialized interaction must be played. Supported hunting and both fishing modes are now ported as specialized interactions. Coconut-bearing palms now have their verified throwing action; see COCONUT_CHALLENGE.md.
- Structures and raft/boat/shelter progression have explicit special cases in `a.c(int)` and cannot be gathered as loose items.

`ItemRules.ts` uses typed decoded fields instead of a guessed pickup whitelist. The store independently checks actual map ID, distance, duplicate collection, readiness and tool availability. UI requirements and state-changing actions use the same rules. Original byte layers remain immutable.

## Recipe examples

| Result | Consumed materials | Required, kept equipment |
| --- | --- | --- |
| Axe | Stone×1, Branch×1, Vine×1 | none |
| Rope | Vine×2, Branch×1 | Knife×1 |
| Stick | Log×1 | Knife×1 |
| Plank | Log×1 | Plane×1 |
| Bow | Stick×1, Rope×1 | Knife×1 |
| Net | Rope×4, Stone×4 | Knife×1 |
| Arrow | Branch×5, Stone×1 | Knife×1, Bow×1 |

The UI now lists 21 supplies/equipment recipes, two camp upgrades, four house furnishings and 16 garments. Materials, reusable tools and global shelter-tier prerequisites are enforced. The original Flag upgrades in place to Tent and Cabin; structures never become portable inventory items. Furnishings use a separate house record; garments use separate wardrobe ownership/equipment; Pirate ship repair/ending uses its separate original-world interaction (SHIP_RECONSTRUCTION.md). Large sail (54) is craftable despite its verified sprite −1; no replacement artwork is invented. See SHELTERS.md and HOUSE_AND_SLEEP.md for source call paths, exact recipes and adaptations.

`ad.k()` stores crafted equipment uniquely. The browser prevents crafting an already-owned equipment item instead of consuming materials for no additional result; this is a documented usability fix. Supply results can stack. Output quantity is one, matching `ad.n()`/`ad.k()`.

## Compatibility and limits

Existing version-1 IndexedDB saves still load; previously acquired items are retained. New restrictions apply to subsequent actions. This avoids silently deleting progress from the simplified first milestone.

The browser's interaction radius/pathfinding, labels and recipe filters are adaptations. Exact original facing-only interaction, timed action presentation, remaining special object actions and story gating remain to port. Direct world consumption is now independently ported in WORLD_FOOD.md. Animal contacts and supported minigames are now ported separately. The fourth milestone implements inventory food/poisoning and activity effects from rows 3–7; see SURVIVAL.md. The subsequent wildlife milestone ports the recovered animal behavior families; see WILDLIFE.md. No emulator comparison has been completed.

## Net fishing completion

Terrain-4 inspection dispatches the original Net (83) and Potato bait (26) challenge. Completed rounds use one potato, keep Net, add strength/energy effects and award one original item on a win. This specialized interaction does not remove a loose fish from the Objects map. See NET_FISHING.md, including the original Rug reward branch. Rod fishing is now ported separately; supported bow hunting is now ported separately.

## Rod fishing completion

Terrain-5 inspection now dispatches Fishing rod (14) with Worms (90). Completed attempts use one worm, keep the rod, apply its strength/energy effects, and grant one actual fish species on a win. No loose object is removed from the map. See ROD_FISHING.md. Supported bow hunting is now ported; see HUNTING.md.

## Hunting completion

Animal tool 81 (Bow) requires Arrow 45. One Arrow supplies up to ten minigame shots and is consumed once at completion; Bow is kept. A win awards the hit animal item, adds 1 strength, costs 10 energy, and removes that actual wildlife actor. Failed dangerous hunts add source retaliation energy losses. See HUNTING.md for grouping, protection assumptions and source tenth-shot conflict handling.

## Raft completion

Broken raft84 at original (94,94) now has its dedicated repair interaction. Exact six consumed material groups and kept Saw/Hammer, −30 Energy/+10 Morale, non-portable runtime95, boarding/swamp traversal/landing and transport saves are ported. It remains separate from the43 workbench recipes. See RAFT_TRAVERSAL.md for source art, single-tile transitions and explicit adaptations; dynamic tides and the full tool/material route remain pending.

## Supply renewal and progression

TAKE still awards one item and exact activity effects. Original recipes need more Sails/Nails/Vines than the raw static stock supplies once. Resource histories now allow eligible source cells to return at tide refresh, using exact20/70 buffer capacities and carried-unique IDs. No extra items are granted and no recipe quantities changed. Direct world USE does not record TAKE history. See RESOURCE_RENEWAL.md.
