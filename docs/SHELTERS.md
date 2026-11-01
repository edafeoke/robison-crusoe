# Camp upgrades and recipe tiers

Recovered from the supplied JAR, its CFR output and retained `javap` disassembly. Item indices are zero-based; map values are item index + 1. This milestone implements camp upgrades and global crafting tiers. It does not implement arbitrary building placement, furnishings, storage or overnight sleep.

## Verified source behavior

- `data.y.d()` parses zero-based CSV row 10 into `data.p.e : byte[]` (parser case 13). `data.aa.c(StringBuffer)` uses that array to filter recipes according to the current name of item 17. Flag allows requirement -1; Tent allows -1 or 9; Cabin allows -1, 9 or 10. Requirements 18 and 84 belong to boat/raft paths, not the shelter tiers. Row 11 is separate and is not treated as a workstation requirement.
- This check is **global camp progression**, not a distance check against a placed crafting table. Equipment listed in row 9 is an additional, reusable prerequisite. A Tent therefore unlocks Rope, Plank, Arrow, Bow, Fire, Net and other source recipes everywhere; Cabin unlocks Rug machine, Weaving machine and Fabric among the supported recipes.
- `data.a.G()` special-cases results 9 and 10. It replaces **item 17's sprite, name and description** with Tent or Cabin, calls `data.ad.l()` for effects, then `data.ad.o()` for consumed materials. It does not add the structure to an inventory or move the map object. Confirmed in `evidence/bytecode/data.a.txt`, method G, bytecode offsets 0–177.
- `data.aa.c` excludes previous/current shelter upgrades from the construction list. `data.t` saves 17, 9 or 10 based on item 17's current sprite. This corroborates a single persistent camp tier rather than loose portable buildings.
- The original Objects bytes contain one camp flag: map ID **18**, grid **(24,129)**, row-major index **23244**. The JAR map is retained unchanged. There is no evidence in these upgrade call paths for free placement of Tent/Cabin, terrain-clearing, relocated camp sites or multiple shelters.
- `data.y.e()` draws exact packed sprites: Flag **393219** (BS6 frame 3), Tent **393216** (BS6 frame 0), Cabin **393217** (BS6 frame 1). Original D0 dimensions, anchors and transparency are preserved. Source animated flag overlays are not added by this milestone.

## Recovered transactions

| Upgrade | Consumed materials | Required tools, kept | Camp requirement | Effects |
| --- | --- | --- | --- | --- |
| Tent (9) | Branch (8) ×3, Sail (12) ×1 | Knife (76) ×1 | Flag | Energy −20, Morale +5 |
| Cabin (10) | Stick (36) ×4, Rope (91) ×5, Giant log (93) ×2, Nail (13) ×4, Moss (39) ×3, Rock (21) ×2 | Axe (5) ×1, Hammer (77) ×1 | Tent | Energy −30, Morale +10 |

Both add zero Strength. They create no satchel item. Duplicate builds and downgrades are rejected before consuming anything. As in the source completion flow, a committed construction can exhaust energy; its camp/material changes settle once before the resulting loss state. Exploration mode retains its existing reserve-loss suspension.

## Browser implementation and adaptations

- `ShelterSystem.ts` owns typed tiers, source gate interpretation and camp display mapping. The decoder exports a typed `shelterRequirement` for all 120 items while retaining every raw CSV field.
- The workbench displays **22 supported recipes**: the previous 20 supplies/equipment recipes plus Tent and Cabin. All are checked against camp tier, materials, tools and existing equipment. Unsupported furnishings, clothes, boat and raft recipes stay unavailable. Showing all locked recipes with explanations is a browser usability adaptation; the original builds a candidate list from carried recipe ingredients.
- The store validates readiness, active activity/rest/loss locks and the presence of the original camp. A successful upgrade consumes materials atomically, keeps tools, applies recovered activity effects, and updates the tier. No proximity-only workstation rule is invented. Old saves retain already acquired items; subsequent crafts follow the recovered gates.
- The Phaser renderer swaps the camp sprite in place without restarting the game. Runtime occupancy and source map ID 18 remain unchanged, so original single-cell collision and nearby-object interaction continue. A larger artwork footprint does not invent larger collision geometry.
- Camp inspection uses its current recovered name/description. The map's camp marker and workbench overview are browser additions. The existing ten-second breather can be started from camp; it gives **no invented shelter bonus**. Overnight sleep and furnishings/storage remain separate future work.
- Version-1 IndexedDB saves gain optional `shelter: 17 | 9 | 10`. Missing fields migrate to Flag, including when loading an old save over a Cabin session. Invalid tiers reject the save without changing the current expedition. Original Java RMS saves are not imported.
- Upgrade presentation is immediate rather than the source's skippable roughly eight-second completion interstitial. No emulator comparison or claim of complete original shelter functionality is made.

## Validation boundary

Eight new automated checks cover every item gate, exact upgrade transactions, tool retention, duplicate/downgrade prevention, supported recipe boundaries, immutable original placement/collision, active construction effects including exhaustion, and legacy/new saves. Browser setup and observations are recorded in VALIDATION.md; the dev-only fixture explicitly supplies ingredients/tools and positions Robinson near the original camp. It does not claim manual collection of the complete building supply chain.

Next: trace the actual furnishings/storage menu and sleep/save-time arithmetic, then implement those benefits without adding generic survival mechanics. Clothing protection, world-contact hazards, story triggers, boat/raft progression, weight and tide/weather scheduling remain outstanding.
