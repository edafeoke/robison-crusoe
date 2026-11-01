# House furnishings and sleep recovery

## Verified discoveries

Evidence is the supplied JAR, complete CFR output and retained javap disassembly. IDs are zero-based.

`data.a.G()` sends category 3/5 results through `data.ad.m()`, except Cabin's earlier special branch. `ad.m()` stores the item's name once in the house list (`p.a : String[]`, count `p.aw : int`, quantities `p.h : int[]`), calls `ad.l()` for effects, and `a.G()` calls `ad.o()` for material subtraction. `data.a.y()` offers house access from Flag/Tent/Cabin; the world action opens mode 8, and `v.b()` mode 8 and `r.b(StringBuffer)` display it. `a.F()` → `a.e(int)` offers inspection; no arbitrary satchel-to-container transfer or capacity rule is established in this path. `ad.i()` contains removal bookkeeping, but this milestone does not add a remove/return-materials button without a verified reachable house action.

The four furnishing recipes are:

| Item | Consumed materials | Kept tools | Tier |
| --- | --- | --- | --- |
| Single bed (15) | Dry grass (7) ×8, Branch (8) ×4, Stone (3) ×8 | none | Tent |
| Barrel (50) | Plank (37) ×4, Rope (91) ×2, Clay (20) ×2, Tree root (35) ×2 | Axe (5), Knife (76) | Cabin |
| Rug (60) | Wool (119) ×3, Spiral (64) ×4 | Rug machine (34) | Cabin |
| Double bed (85) | Plank (37) ×4, Bamboo (38) ×1, Tree root (35) ×3, Moss (39) ×6, Rock (21) ×1 | Saw (78) | Cabin |

Each successful creation costs **10 energy**, adds **5 morale**, adds zero strength and creates one house entry. Duplicate crafted furnishings are refused before materials are consumed, matching `ad.m`'s unique list. The browser workbench now has **26 recipes**: 20 portable recipes, two camp upgrades and these four furnishings.

Only Barrel has a mapped source illustration (852041, BS13 frame 41). Original `y` maps Single bed, Rug and Double bed to -1. They use labelled text entries and the existing neutral missing-art marker; no unrelated sprite or invented bed artwork is assigned. No furniture is placed onto the island map or given guessed collision.

## Sleep path

`data.a.m()` distinguishes short rest from Sleep. Sleep sets `p.c : int = 1`, serializes through `t.a()`, records the day and returns to the title menu. `data.v.a(boolean)` at loading progress 70 selects `c(true)` for that flag; `v.c(boolean)` calculates elapsed whole hours from saved day/hour/minute fields. Its positive branch restores all three reserves, capped at full. Its recovery path has no bed, Rug, Barrel, camp-tier or house-inventory multiplier.

| Whole real hours away | Hunger, thirst and energy restored |
| --- | --- |
| Less than 1 | 0 |
| 1–15 | +10 each |
| 16–35 | +20 each |
| 36–72 | +30 each |
| 73–119 | +40 each |
| 120 or more | +50 each |

Original time comes from `aa.d()`'s `Date` arithmetic; `t` stores day/hour/minute and the sleep flag. The negative/non-sleep branch has separate raw-bar-unit clamps and asymmetries. It is **not ported** as offline depletion. Barrel's description mentions water, but no refill/drinking or capacity benefit is established by these menu/sleep paths. No such action is invented.

## Browser behavior and explicit adaptations

- `HouseSystem.ts` identifies the four source furnishings; `ItemRules.ts` handles original tier/material/tool checks, including recipes whose source sprite is -1. The store records furnishing output separately from satchel inventory and applies source creation effects once.
- House storage opens from camp inspection or the workbench overview. Its entries display original observations and quantities. There is no generic supply deposit/withdraw system, extra carrying capacity, automatic drinking, free-placement furniture or bed rest multiplier.
- Optional version-1 `furnishings` maps preserve the house list. Old saves default to an empty house. Existing satchel Rug loot from the recovered net-fishing reward bug is deliberately **not** reclassified or silently removed. Crafting a house Rug is a separate transaction.
- Settings offers **Sleep · save & return to title**. The game must be ready/started, outside minigames, rest and collapse. Saving must succeed before returning to title. Failure preserves the active expedition. Sleep writes `sleepStartedAt` alongside complete ordinary save state; no instant reserve bonus is given.
- Continuing a sleeping checkpoint computes `floor(max(0, now - sleepStartedAt) / 3600000)` and applies the source bands. Absolute timestamps replace the phone's fixed timezone/day arithmetic; backward clock changes give zero recovery. Elapsed browser game time, wildlife, morale, strength and tool wear do not advance while away. This retains the existing browser clock choice rather than claiming device-wall-clock fidelity.
- The pending sleep marker is consumed and the recovered save written in **one IndexedDB read/write transaction**. Resolving waits for commit. Repeated/concurrent loads cannot claim the same interval twice; a failed transaction leaves the in-memory expedition unchanged. Loading before one hour ends that sleep with zero recovery. Start another sleep to recover from another absence. This one-claim persistence is a browser safeguard, not a claim that the original RMS implementation prevented save-reload exploits.
- Ordinary Save retains the existing offline pause behavior. Legacy saves without a timestamp receive no recovery or offline losses. Exploration mode keeps full reserves; a malformed/dead checkpoint is not revived by sleep.

## Validation and remaining work

Eight new automated tests cover all four exact transactions, sprite omissions, tier/tool/duplicate rules, separate house/satchel records, source effects, malformed/legacy save fields, all hour boundaries and partial-hour handling, caps, one-time recovery, failed writes and load migration. Browser QA uses an explicitly labelled fixture with supplied Cabin/materials/tools and **simulated** two-hour elapsed time. It does not claim a two-hour manual wait or an end-to-end supply-gathering playthrough. See VALIDATION.md for the observed production/offline results.

Outstanding: original non-sleep offline depletion, device timezone/daylight changes and clock parity, source save import, possible furnishing-specific uses outside the traced menu, clothing/protection, world-contact hazards, weight, tides/weather and original story/boat/raft escape progression. No Java ME emulator comparison is claimed.
