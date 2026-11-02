# Ordered survival expedition and click-path recovery

Audited 2026-11-02. This milestone adds an ordered, timed integration test from a new expedition through **Sextant recovery**, and fixes a mouse-navigation edge exposed during that work. It does not certify the final ship/escape acquisition route.

## Mouse recovery: verified defect and browser fix

A moving animal can enter the neighboring tile under Robinson’s off-centre foot box without entering his own integer tile. A new click path previously aimed straight at the next cardinal tile centre. In a narrow turn, that diagonal direction could fail both axis checks and clear the path, although moving back to the current tile centre first was legal.

The test reproduces the actual grid case at tile(43,117), foot position(702.4,1880), a live Red ibis occupying(44,117), and a goal at(43,116). The old first aim cannot advance; the new path first returns to(696,1880), then reaches(696,1864), with the blocker retained. Same-tile clicks can also recenter. Already-centred paths keep their prior cardinal nodes, and blocked destinations remain rejected.

`MovementSystem.moveWithCollision` now contains the existing browser movement arithmetic used by both Phaser `Player` and the headless driver:64px/s, normalized direction,50ms step cap and independent axis collision. It returns coordinates only. That prevents structurally compatible player/store objects from leaking unrelated fields into a movement result. No source tile, object placement, collision mask or sprite changes. This recentering is a **browser click-navigation improvement**, not a recovered Java ME action or phone-speed claim.

## Ordered integration strategy

`tools/lib/expeditionRunner.ts` advances production store transactions and the production collision/movement, survival, wildlife/contact, tide/recovery and supply-renewal systems. `expeditionNavigation.ts` holds its test-only navigation policy; `orderedMilestone.ts` declares the documented strategy. None is imported into the production application.

The run calls normal Begin expedition at(8,148), empty inventory, unmodified original layers, default moving wildlife and enabled survival. Supplies and equipment enter inventory only through actual collection/crafting transactions. It acquires Knife, Axe, Tent, Fire and a retained Stone for coconut use, then Saw at low tide, Machete, three protective garments, Plane, a travelling food reserve, Gunpowder and Hammer. It returns toward the raft’s shore before committing to further construction, makes the exact nested materials, repairs the actual raft, builds Cabin, boards, paddles, lands and gathers the original Sextant.

Ordinary blockers along planned paths are gathered only with the actual tool and interaction distance. Dynamic actors remain actual collision occupants; blocked movement still advances the world. The strategy replans, retreats, forages, consumes carried food and rests rather than disabling hazards or replenishing reserves directly. Ingredients needed by the current recipe are reserved against the driver’s own automatic meal choices. These are test-driver decisions, not original quests or new gameplay mechanics.

Reproduce with `node --import tsx tools/play_expedition.ts`. The successful report is `evidence/ordered-expedition-audit.json`, including ordered events and a validated version-one save. The script exits nonzero on a failed leg and reports that failure; it does not create an escape victory as a fallback.

## Recorded result

| Milestone | Active time | Hunger / Thirst / Energy |
| --- | --- | --- |
| Knife |00:04.25|100 /100 /98|
| Axe |00:14.00|100 /100 /79|
| Tent |00:24.50|100 /100 /47|
| Fire |00:50.00|100 /100 /49|
| Saw |06:22.25|62 /86 /91|
| Machete |07:11.75|87 /100 /95|
| Hammer acquired and return to raft shore |18:49.30|70 /100 /93|
| Raft repair |22:42.80|99 /100 /61|
| Cabin |26:04.05|90 /86 /62|
| Board original raft |31:59.45|100 /100 /86|
| Land on the eastern shore |32:19.95|90 /86 /82|
| Sextant recovered |32:20.70|95 /91 /100|

The final checkpoint is beside original Sextant(165,76), Robinson at(163,77), source Sextant removed and Sextant×1 carried. The raft is moored at(163,81), rather than its original(94,94). The ship remains a wreck. The run covers approximately63,364px of movement and38,814 simulation ticks,168 pickups,32 crafting actions,93 direct world meals,18 satchel meals, five tide changes, five rests and53 actual timed predator contacts. Contact consequences and garment protection remain active; no timed predator hit occurs during the recorded paddling interval. Original layers remain unchanged.

## Verification boundary

This is **headless integration**, not a manual full-island browser playthrough. The driver samples movement and world updates in50ms increments, uses a fixed500×300px wildlife view and makes instantaneous menu transactions. Its planning/food choices and conservative final movement samples are not a claim of exact Phaser frame cadence or original Java randomness. Production Phaser owns rendering, camera, actual input and frame scheduling; those still need broader combined playthrough coverage.

Earlier experimental full-escape strategies collapsed near wildlife or ran out of reserves after poor tide/food decisions. Other driver errors included stale movement state, treating structure prerequisites as camp tiers, and inconsistent land/raft safety planning. Those attempts are not completion evidence; the bounded Sextant run is the regression target. Original damage, depletion and recipes were not weakened. A longer attempt reached the five total Sail pickups needed by a Tent/Large sail plan but later died near a Shark; another failed on the return route. Neither proves final ship construction or escape.

The final Large sail/Cabin-tier tool and material itinerary, repeated raft return journeys and complete ship departure still need a successful combined run. Source phone/emulator comparison, remaining object filtering/random placement, weather, non-sleep offline losses and RMS compatibility remain separate limitations.

## Browser verification

`tests/browser/movement.html` prepares Robinson off-centre beside an actual original bird without moving the bird or changing source layers. It runs the real Phaser path follower and records arrival, followed by native arrow-key and canvas-click checks. `tests/browser/expedition.html` only imports the earned, validated headless save into isolated IndexedDB and tests normal UI/Phaser restoration. It does not claim that the browser manually earned those items. Detailed observations and production/offline checks belong in VALIDATION.md.
