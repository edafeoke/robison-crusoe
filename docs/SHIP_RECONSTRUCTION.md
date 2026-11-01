# Pirate ship reconstruction and escape

Audited 2026-11-01 against the supplied JAR, CFR output and JVM disassembly. This milestone ports the original ship repair and ending, not the full journey needed to reach them. No Java ME emulator comparison has been completed.

## Verified original behavior

`data.a.c(int)` dispatches Pirate shipwreck (item **18**) to `z()`. Its `p.aC:Z` flag selects a dedicated reconstruction menu; `data.r.a(StringBuffer)` forces result item **19**. The CSV camp-requirement value 18 therefore does not make this an ordinary camp upgrade. The sole original wreck is Objects **19** at **(152,30)**, index **5552**.

| Result | Consumed materials | Required equipment, kept |
| --- | --- | --- |
| Large sail (54) | Sail (12) ×4, Fabric (118) ×1, Cotton thread (116) ×1 | Scissors (86) ×1, Needle (97) ×1 |
| Pirate ship (19) | Large sail (54) ×1, Giant log (93) ×3, Nail (13) ×8, Rope (91) ×5, Stick (36) ×6, Moss (39) ×5 | Saw (78), Hammer (77), Compass (80), Map (23), Sextant (22), Anchor (79), one each |

Large sail is a supply recipe with no camp prerequisite, −10 Energy and no original sprite (`−1`). The browser now permits this otherwise valid recipe and uses the existing neutral missing-art marker. Fabric/thread retain their own recovered recipe prerequisites. Nothing is fabricated as sail artwork. Ship repair has zero Energy/Morale/Strength effects and does not put a portable ship in inventory. `ad.o()` consumes materials and retains equipment.

In `a.G()`, repair copies sprite `p.f[19]` to `p.f[18]`, subtracts **two tiles** from player Y, updates the camera coordinate, selects down-facing idle and clears movement. It sets `p.aF:Z`, resets `p.H:J` and enables the source creation interstitial. Original map ID 19 remains unchanged. `ab` omits the body shadow after this sprite change.

The source has a skippable creation presentation, then the world control path skips player input under `aF`. When `H > 8000L`, `v.b()` selects game state 9. The main world loop still calls `ad.f()` and updates world actors during this control lock: departure does not grant immunity to need depletion or wildlife. `v.d()` displays original story text **97** and **98** (victory and escape after repairing the boat), then returns automatically to its menu.

### Exact art and flags

Wreck: encoded sprite **393235**, BS6 frame **19**. Repaired ship: **393236**, BS6 frame **20**. Both keep the original D0 regions, anchors and mirror flags. `c.c()` alternates flag sprites **393222 / 393223** (BS6 frames 6/7) after a strict 300 ms source check. `y` supplies these offsets relative to the map tile centre:

| State | First flag | Second flag |
| --- | --- | --- |
| Wreck | (−25, −82) | (15, −74) |
| Repaired | (−25, −83) | (15, −65) |

The port uses a 300 ms Phaser timer. It does not invent a sailing tween or move the ship off its original cell.

## Browser implementation and explicit adaptations

- `tools/recover_ship.py` validates source branches and exports `shipRules.ts` plus `evidence/ship-and-raft-rules.json`. `npm run decode` includes it. Raw map/PNG data remain intact.
- The workbench now contains **43 recipes**: 21 supplies/equipment, two camp upgrades, four furnishings, 16 garments. Ship repair is a separate interaction on the actual original wreck.
- `ShipSystem` validates the live cell, distance, materials/equipment and phase. Repair updates the render overlay in place, moves Robinson north 32 pixels, locks world actions and starts a strict **greater-than-eight-second** ending clock. Needs and actors continue; collapse pauses the ending until the browser's existing recovery flow is used. Menus and hidden tabs pause play, following existing browser behavior.
- The source's separate skippable creation interstitial is folded into the immediate repair transaction, as with existing camp upgrades. The subsequent departure countdown remains. There is no claimed reproduction of the phone's exact menu timing.
- A persistent victory panel holds the original ending text until an explicit choice. The original auto-return is replaced with Save completed expedition, Begin another expedition and Return to title. New expedition resets gameplay in memory and leaves the previous IndexedDB checkpoint until explicitly saved over.
- Optional version-1 `ship` state supports legacy saves (default wreck), active departure and completed expeditions. Normal save and Sleep are disabled during departure; completed saves are allowed. The terminal result freezes world play. IndexedDB remains local; no backend or new dependency.

## Access limitation: the full expedition is not complete

`tools/audit_ship_routes.ts` records an optimistic reachability audit in `evidence/ship-access-audit.json`: current fixed-terrain walking, every Objects blocker ignored, original Decor/Rocks still respected. The starting component contains **13,251 cells**. The actual wreck, Saw, Hammer and Sextant are outside its interaction reach. The broken raft, Compass, Map and Anchor are reachable in that optimistic model. This is a diagnostic of this browser build, not proof about the original game's dynamic world.

Raft traversal is now ported separately (RAFT_TRAVERSAL.md); changing tides and remaining decor/resource updates must still be implemented and audited before claiming a complete new-game-to-escape route. No terrain is made walkable, no tool or wreck is relocated, and no invented shortcut conceals this gap. Browser QA explicitly supplies reconstruction materials and positions Robinson beside the unchanged original wreck. It verifies the actual crafting/repair/ending UI under those preconditions, not legitimate island completion.

## Preliminary raft audit (superseded by RAFT_TRAVERSAL.md)

`a.G()` repairs Broken raft **84 / map 85**, at **(94,94)**, index **17014**, to item **94 / map 95**, replacing matching runtime object cells and setting `p.ap=1`. Materials: Stick×6, Rope×5, Plank×5, Nail×5, Tree root×3, Moss×4; kept Saw×1 and Hammer×1. Effects: −30 Energy, +10 Morale.

`ad.u()` removes the raft cell, sets `p.T` and initiates one adjacent tile step in the facing direction. `ad.v()` places the raft back at the player cell and steps toward shore. `g.b(int,int)` permits raft terrain **FULL 12,13,14 only**. Its `p.e:[I` terrain identity array is distinct from CSV `p.e:[B` camp prerequisites. General ocean travel cannot be inferred from this predicate. `ab.b/k` uses original raft/body/outfit layers (BS6 frames 8–18); `t` retains raft state and includes a special cell 14925→14926 correction. The subsequent raft milestone ports repair, boarding, swamp paddling, landing and saves. RAFT_TRAVERSAL.md corrects the preliminary interpretation of the movement-method argument and documents the now-ported art, source landing-check quirk and browser adaptations. Dynamic tides and the full acquisition/access route remain pending.
