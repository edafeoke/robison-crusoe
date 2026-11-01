# Reconstruction design and milestone boundary

The goal is faithful reconstruction of Exkee's Java ME adventure. The current build extends the **first playable exploration milestone** with original ship reconstruction and its timed escape ending, recovered direct world-food consumption and body weight, clothing and hunting protection, house furnishings and explicit sleep recovery, persistent camp upgrades and recovered recipe tiers, bow hunting, rod fishing, net fishing, coconut throwing, survival/item-use rules, wildlife behavior and offline reload support. The complete survival adventure is a later sequence of audited implementations, not a claimed completed feature.

## Presentation

Preserve original pixel art and world layout. Use crisp nearest-neighbor scaling, original sprite anchors and depth-sorted vegetation. Frame it with a warm expedition-journal interface: ink green, parchment, brass accents, serif headings, restrained motion and useful keyboard hints. Do not replace original map/art with unrelated generated survival scenery. Full hand-painted remaster art is deferred.

## Playable scope

- All five immutable original layers decoded; original terrain/rock/border art, composite foliage, static recoverable objects.
- 326 original animals across 16 source-verified profiles: wandering/rest, prey fleeing, predator pursuit, bird flight/landing, water-constrained sharks, exact directional frames/shadows/wakes. Timed predator contacts, matching-slot clothing protection, interrupted rest and immediate shark exhaustion are playable; supported animals can also be hunted through the recovered bow mode.
- Robinson idle/walk frames, WASD/arrows, click pathfinding, camera follow, zoom, boundaries and recovered walking collision.
- Nearby object inspection using recovered names/descriptions, collection gated by recovered tools, original inventory categories, quantities/discard.
- 21 supplies/equipment recipes, two camp upgrades, four house furnishing recipes and 16 garments, using exact materials, reusable tools and global Flag/Tent/Cabin recipe tiers. Original camp location/collision retained; house records and sleep recovery now supported; wardrobe ownership/equipment and original directional clothing layers supported; Pirate ship repair/ending is now a separate world action, while dynamic tides and a verified full island route remain deferred. See SHELTERS.md, HOUSE_AND_SLEEP.md and CLOTHING.md.
- Original hunger/thirst/energy depletion, separate satchel/direct-world food paths and use-tool effects (22 original static world foods, with independently recovered effects and removal rules), short rest with recovered sprites, collection/creation activity effects, collapse/load/exploration recovery. Strength/morale effects and nutrition-driven body weight are retained; the condition view uses original stat icons and the carrying-capacity audit found no weight-based gate (BODY_WEIGHT.md); world contacts now drain energy and interrupt rest.
- Coconut-bearing palm interaction and original two-hit throwing game, source projectile tables, five-down/ten-throw rules, exact reward and persisted completion wear.
- Shallow-water net fishing from terrain 4: original fish/net frames, timed casts, potato gate, exact rewards/activity effects and shared save support.
- Deep-water rod fishing: four original fish, source hook lag/sway, strict hooking, outward/downward pull and reeling; worm gate, actual fish reward and completion effects.
- Bow hunting with 15 recovered profiles, apex-only arrows, source movement/retaliation, exact ammunition/reward/activity effects, and persistent removal of the actual caught wildlife actor.
- Original swamp raft at (94,94): exact repair recipe/effects, original seated body/outfit/paddle layers, swamp-only keyboard/mouse movement, priority landing and persistent moved docks/afloat saves. Source landing-rock normalization and continuous timing adaptations are documented in RAFT_TRAVERSAL.md. No unrestricted ocean sailing or full expedition completion is claimed.
- Original text reader, island overview with player position, exploration journal and settings.
- IndexedDB save/restore of position, explored/inspected/collected cells, inventory, elapsed play, options, camp tier, furnishings, wardrobe/worn slots, pending sleep checkpoints, ship repair/departure/completed state, raft transport/docks/orientation and complete wildlife snapshots; earlier saves remain supported.
- Local static build, no external fonts/services/backend. A self-contained production script and versioned asset cache support offline reload; verified with the local preview server stopped.

## Explicit browser enhancements

Mouse pathfinding, responsive panels, zoom, exploration checklist, fog/exploration reporting, synthesized ambient surf, clock starting at 08:00 with the recovered real-minute rate and optional visual dusk lighting. These are not claimed recovered mechanics. Journal checklist is an exploration guide, not the original mission trigger graph. Clock begins at 08:00 and advances during unpaused play only; it does not mirror the phone device clock or apply ordinary-save offline losses. Explicit Sleep applies recovered reserve bonuses on continuing.

The ship ending retains original repair requirements, art, northward player shift, running world simulation and strict eight-second departure. Its held victory screen, completed-save option and immediate repair presentation are browser adaptations. Full journey completion is not claimed; SHIP_RECONSTRUCTION.md documents the reachability audit.

Animal randomness, continuous movement, view gating and snapshot format are documented browser adaptations in WILDLIFE.md.

Survival is enabled for new expeditions; earlier saves keep exploration mode with full reserves until enabled. SURVIVAL.md documents source-backed timing, food/tool effects, rest, poison and activity costs. Menus/hidden tabs pause needs and offline depletion is deferred. World-contact animal hazards are active under survival rules; see WORLD_HAZARDS.md for source quirks and explicit browser normalization. Hunting retaliation checks the recovered matching slot; any worn garment in that slot halves its energy loss. No unverified health meter, thirst collectible or arbitrary food recipe is substituted.

## Deferred original systems, in dependency order

1. Run the original MIDlet in a Java ME emulator; compare screenshots, spawn anchors, initial filtered objects, walk speed and tile collision behavior.
2. Compare the ported wildlife state families against the emulator; port exact original runtime object filters, remaining special entity rendering, and compare the recovered hazards against the emulator.
3. Audit CSV row semantics and inventory categories; tool interactions, item consumption, remaining special item actions; compare the ported direct-world consumption branch separately from satchel use; compare the now-ported body-weight arithmetic and carrying-capacity audit against the emulator; compare the ported outfit composition/protection against the emulator.
4. Continue from recovered in-place camp upgrades and global recipe tiers: compare house records and sleep recovery against the emulator; investigate any furnishing-specific uses outside the traced menu. No free-placement building rules are inferred.
5. Compare the ported coconut challenge with an emulator; compare net/rod fishing and bow hunting, including source tenth-shot behavior and outfit protection.
6. Compare the ported reserve depletion, food/poisoning and short rest against an emulator; compare sleep recovery and port non-sleep offline behavior, weather and changing tides; compare the ported nutrition/weight counter.
7. Ship reconstruction and the escape trigger are ported (SHIP_RECONSTRUCTION.md), but the complete access/material journey is blocked in the current walking world. Raft repair/boarding/travel/disembarking are now ported (RAFT_TRAVERSAL.md); implement tide/decor changes, then verify a legitimate start-to-escape route. Compare original save serialization and compatibility where feasible.
8. Profile world updates, compare fidelity, complete accessibility and optional remaster art/audio with licensed sources.

## Acceptance for this delivery

Original grid and sprite data must round-trip losslessly; TypeScript and production build must pass. Browser QA must cover actual player movement by both keyboard and mouse, collisions, camera, object interaction and menus, save/reload and remount cleanup. Honest scope and remaining defects belong in VALIDATION.md.
