# Reconstruction design and milestone boundary

The goal is faithful reconstruction of Exkee's Java ME adventure. The current build extends the **first playable exploration milestone** with recovered item rules and offline reload support. The complete survival adventure is a later sequence of audited implementations, not a claimed completed feature.

## Presentation

Preserve original pixel art and world layout. Use crisp nearest-neighbor scaling, original sprite anchors and depth-sorted vegetation. Frame it with a warm expedition-journal interface: ink green, parchment, brass accents, serif headings, restrained motion and useful keyboard hints. Do not replace original map/art with unrelated generated survival scenery. Full hand-painted remaster art is deferred.

## Playable scope

- All five immutable original layers decoded; original terrain/rock/border art, composite foliage, static recoverable objects.
- Robinson idle/walk frames, WASD/arrows, click pathfinding, camera follow, zoom, boundaries and recovered walking collision.
- Nearby object inspection using recovered names/descriptions, collection gated by recovered tools, original inventory categories, quantities/discard.
- 20 supplies/equipment recipes using exact material and reusable-tool requirements; construction placement and clothing paths deferred.
- Original text reader, island overview with player position, exploration journal and settings.
- IndexedDB save/restore of position, explored/inspected/collected cells, inventory, elapsed play and options.
- Local static build, no external fonts/services/backend. A self-contained production script and versioned asset cache support offline reload; verified with the local preview server stopped.

## Explicit browser enhancements

Mouse pathfinding, responsive panels, zoom, exploration checklist, fog/exploration reporting, synthesized ambient surf, session-based clock and optional visual dusk lighting. These are not claimed recovered mechanics. Journal checklist is an exploration guide, not the original mission trigger graph. Clock begins at 08:00 and advances during unpaused play only; it is not the original real-time survival calculation.

Needs remain visibly paused during this milestone. Hunger, thirst and energy are recovered concepts; exact depletion mapping/timing and item effects must be audited before enabling. No unverified health meter, thirst collectible or arbitrary food recipe is substituted.

## Deferred original systems, in dependency order

1. Run the original MIDlet in a Java ME emulator; compare screenshots, spawn anchors, initial filtered objects, walk speed and tile collision behavior.
2. Port exact original runtime object filters, entity animation/state, animal AI and hazards.
3. Audit CSV row semantics and inventory categories; tool interactions, item consumption, outfit composition and capacity/weight rules.
4. Port construction placement and workstation restrictions from actual call paths; shelter/storage and rest.
5. Recreate coconut throwing, shallow/deep fishing and hunting minigames with original parameters.
6. Port original time/date reserve depletion and food/poisoning, sleep/save behavior, weather and changing tides.
7. Recover and implement exact story/repair/escape triggers, original save serialization and compatibility where feasible.
8. Profile world updates, compare fidelity, complete accessibility and optional remaster art/audio with licensed sources.

## Acceptance for this delivery

Original grid and sprite data must round-trip losslessly; TypeScript and production build must pass. Browser QA must cover actual player movement by both keyboard and mouse, collisions, camera, object interaction and menus, save/reload and remount cleanup. Honest scope and remaining defects belong in VALIDATION.md.
