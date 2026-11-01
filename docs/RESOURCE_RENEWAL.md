# Pickup histories and supply renewal

Audited 2026-11-01 against the supplied JAR's retained CFR, JVM field descriptors and raw Objects map. This work arose from progression verification: the previous browser's permanent removal of every pickup made the full construction plan impossible. It does not claim a completed new-game-to-escape playthrough.

## Concrete progression blocker

`tools/audit_progression_materials.ts` expands one Tent → Cabin, Raft and Pirate ship construction plan using exact recipes, reusable tools, and a crafted Axe because no loose Axe exists in the raw map. This includes Plank, Stick, Rope, Cotton thread, Weaving machine, Fabric and Large sail dependencies.

| Loose material | Required by this plan | Raw map stock | Additional stock needed |
| --- | ---: | ---: | ---: |
| Sail | 5 | 1 | 4 |
| Nail | 17 | 13 | 4 |
| Vine | 39 | 32 | 7 |

The Tent consumes one Sail; Large sail consumes four. Raft/Cabin/Ship consume five/four/eight Nails. Nineteen Ropes need 38 Vines, and the Axe needs one more. These are source recipe requirements, not reasons to increase pickup quantities or place extra items. The material audit excludes food costs, travel, hazards and source random placement; it is not an optimized complete route solver. Full output: `evidence/progression-material-audit.json`.

## Verified original behavior

- `data.ad.a()` adds exactly one item per successful TAKE. For category0 supplies, it records the source object's short identifier from `p.a:[S` in one of two int arrays. The method does not increase Sail/Nail yield to cover the construction requirements.
- `ad.c(int)` writes `p.s:[I` using integer cursor `p.aD:I`, wrapping at **20**. Ordinary supplies, including Sail, Nail and Vine, use this recent pickup buffer.
- Giant log93, Rock21 and Bamboo38 instead call `ad.a(int)`, writing `p.r:[I` using `p.aC:I`, wrapping at **70**. This longer buffer is bounded, not an infinite permanent-removal set.
- Equipment/category1 TAKE does not enter either supply buffer. `g.l()` separately filters **49 map IDs** against currently carried inventory/owned records. Sail map13 and Nail map14 are not in that unique list. Knife map77, Saw map79 and other original tools are.
- `g.k()` assigns sequential short IDs to original non-mobile source placements. `y.a(int)` identifies 22 map IDs excluded from these IDs, including animals/fish. `g.m()` filters the longer removal buffer. `g.d()` filters the recent buffer and randomizes eligible placements near source cells after reloading Objects. Tide changes call this path through `g.a(boolean)`; source load/resume and `ad.s()` also reload objects.
- `ad.s()` clears only the recent buffer once while the source clock is at04:00, then reloads/reprocesses Objects. It does not clear the longer removal buffer.
- Direct world food USE in `ad.j()` clears its cell without calling the recent-history method. A successful direct meal therefore differs from gathering food into the satchel. Fatal food branches still return before removal.

Descriptor checks matter: `p.aC`/`p.aD` also name boolean menu flags, while `p.r` also names the boolean tide state. Those are not the int cursors/arrays used here. `tools/recover_resource_history.py` checks the source branches and exports `resourceHistoryRules.ts` plus `evidence/resource-history-rules.json`.

## Browser implementation and explicit adaptations

`ResourceHistorySystem` keeps chronological buffers of20 and70 **original source cell indices**. The browser uses these stable indices in place of Java's short source IDs because its world has not yet ported random placement/identifier relocation. No raw map ID, PNG or source recipe is changed.

A successful category0 collection updates the matching buffer. At a tide boundary, a previously collected static supply cell can return if its index has fallen out of both buffers. Carried unique equipment remains hidden; discarded unique equipment can return at a later boundary. Direct world meals do not add recent history and can return at the next boundary. The 04:00 crossing clears recent history once, using the existing paused08:00 browser clock. It does not apply a fresh daily reset on every load during that hour.

Renewal restores the item **at its unchanged original cell**, not at a guessed random coordinate. Already occupied cells, moored rafts and cells under Robinson's foot box defer renewal to another boundary. This documented normalization avoids overwriting actors or trapping the player. Existing wildlife identities, hunting removals, collected special objects, repaired ships and rafts remain separate; animals do not respawn through the static supply path. No invented per-resource timer or replenishment quantity is used.

The current implementation filters unique ownership when considering previously removed source cells. It does not claim full parity with the original global unique/clothing filter, nearby randomization, source-ID movement, object reload-on-resume, or all object/decor mutations. Source load/resume reprocessing is deliberately not used as a browser reload exploit: new saves retain active removals and buffers until an actual in-game refresh boundary.

`collected` now represents the active removed-cell set. Expired renewable cells leave it only during refresh; collision, object rendering and interaction availability update together. Surviving wildlife and camera state remain in place: renewal does not restart Phaser. The existing guide is an exploration checklist, not a recovered mission graph. Its new note explains gathering elsewhere between tides.

## Save compatibility

Optional version-one `resources` holds recent/permanent index arrays, validated for bounds and capacities. New expeditions start empty. Ordinary save/load preserves current absence and history; loading cannot award an extra item or force a tide refresh.

Legacy browser saves have only `collected`. On scene creation, their recorded order is conservatively classified into the last20 ordinary supply pickups and last70 quarried pickups. Old saves cannot distinguish TAKE from direct USE, so legacy food removals are treated as pickups during migration. No inventory is granted or deleted. Unrelated animal/special removals remain unchanged.

## Verification boundary

Automated coverage includes the material shortfalls, exact buffer rollover, unique-tool behavior, mined materials, direct-world consumption, daily reset, occupied-cell deferral, saves/migration, wildlife preservation and repeated acquisition of five Sails from the one raw placement through four twenty-pickup cycles. That last case verifies a quantity/renewal model, not traversal or survival.

Browser QA uses a clearly labelled test page that moves Robinson to original cells and prepares a clock boundary; it grants no items, completed pickups or history. Real Gather controls populate the buffers. The complete survival/crafting/raft/escape route still requires a full playthrough after this necessary progression fix. See VALIDATION.md for actual observations.

## Completed validation

157 tests pass (11 resource-history checks), TypeScript/build pass, and production saves restore offline. Real UI gathering verified the nineteen-versus-twentieth-pickup boundary, delayed tide refresh, the original Sail returning at its one source cell, actual second collection and retained Sail×2 after offline load. The browser fixture supplies no items or history; it repositions Robinson and prepares clock boundaries explicitly. Wildlife interrupted an initial unpaused attempt; the earlier checkpoint was retained and the final active-survival sequence repeated with pauses. Detailed values and limitations: VALIDATION.md.
