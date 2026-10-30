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
| 12 | `p.f : byte[]` | Inventory category. `ad.a()` and `a.G()` route item storage by category. 0 supplies, 1 equipment; construction, storage, furnishings and clothing use separate branches. |

Other CSV rows retain their original strings in `rawFields`. Their numeric status/consumption semantics are still being audited and are not enabled as a survival model.

## Pickup examples

- Knife (76), Stone (3), Branch (8), Sail (12), Mango (0): no tool required.
- Vine (6), Dry grass (7), Clay (20), Tree root (35), Moss (39), Worms (90), Cotton (98): Knife (76).
- Log (11): Axe (5).
- Giant log (93): Saw (78).
- Bamboo (38): Machete (96).
- Pineapple (27) has a knife use action, but TAKE is available before obtaining the knife. Using/eating it is not implemented by this milestone.
- Animals are not added to inventory merely because the relevant bow/net exists; the original specialized interaction must be played. That remains deferred.
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

The UI now lists 20 recovered recipes whose result belongs to the original supplies/equipment categories and has a supported sprite. This includes equipment such as the original crafting machines; their equipment-list prerequisites are enforced. Construction placement, clothes, furnishings/storage and boat repair have separate original paths and are not automatically unlocked by this generic recipe interface.

`ad.k()` stores crafted equipment uniquely. The browser prevents crafting an already-owned equipment item instead of consuming materials for no additional result; this is a documented usability fix. Supply results can stack. Output quantity is one, matching `ad.n()`/`ad.k()`.

## Compatibility and limits

Existing version-1 IndexedDB saves still load; previously acquired items are retained. New restrictions apply to subsequent actions. This avoids silently deleting progress from the simplified first milestone.

The browser's interaction radius/pathfinding, labels and recipe filters are adaptations. Exact original facing-only interaction, timed action presentation, food/poisoning, animal damage, minigames and story gating remain to port. The subsequent wildlife milestone ports the recovered animal behavior families; see WILDLIFE.md. No emulator comparison has been completed.
