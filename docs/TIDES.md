# Original tides, shore recovery and route access

Audited 2026-11-01 against retained CFR, JVM descriptors and original binary layers. No Java ME emulator comparison has been completed. Weather, random object reshuffling and the complete material/escape route are not part of this milestone.

## Verified source rules

`data.ad.p()` reads minute `p.J:J`, alternates boolean `p.r:Z` and calls `data.g.a(boolean)`. High/restored terrain applies during minutes0–5,12–17,24–29,36–41,48–53; low/remapped terrain during6–11,18–23,30–35,42–47,54–59. Changes therefore occur every six clock minutes. These typed fields are distinct from the obfuscated integer menu selections and object-removal arrays sharing their names.

`g.a(true)` backs up both FULL (`p.k:[B`) and Borders (`p.i:[B`), then remaps them. Its checks occur in an order that does not cascade a newly reduced value through a previous rule. `g.a(false)` restores both backup arrays.

| Layer | Low-tide substitutions |
| --- | --- |
| FULL |4→0,5→4,6→5|
| Borders |7→0,19→7,22→19;6→0,18→6,21→18;5→0,17→5,20→17|

All other IDs stay unchanged, including swamp12/13/14. A low tide therefore exposes some original deep-water5 cells as walkable shallow4, but original6 becomes blocked5. It does not allow arbitrary ocean walking or expand raft terrain. Source artwork comes from the same BS1 terrain and BS11 animated shoreline regions already loaded.

`ad.r()` handles a player on terrain5/6 after a change. It searches Chebyshev rings1…14, rows then columns, for **terrain4 with zero Rocks, Decor and Objects**, preserving the source's additional same-column/same-row preference. It does not accept merely passable nonzero rocks. A source exception for tiles170…175 by105…112 sends Robinson to(166,101). If no qualifying shore exists, the source leaves the position unchanged. The method has an initial-call guard on terrain5; the browser consistently checks loaded/changed world state rather than carrying an ambiguous first-device-load flag.

`ad.x()` displaces a shark whose new terrain is below5. It scans dx0…9 outside dy0…9, trying NW then SE offsets, for free terrain5/6 with passable rock and no decor/object. It does not scan all four quadrants. If none qualifies, it leaves that actor stranded until the world changes again. `ad.a(l[],int)` returns flooded members of the three passive `l` arrays (Crab/Turtle/Iguana) to their source home coordinates.

The original tide branch also reloads Objects and invokes unique-object filtering/removal and random nearby placement. That broad object redistribution is **not** silently reproduced by respawning resources. Its complete interactions with unique IDs, prior pickups and source saves remain separate investigation. The port preserves existing stable object/animal identities and collected resources.

## Browser implementation and explicit adaptations

- `tools/recover_tides.py` validates both original methods and exports `tideRules.ts` plus `evidence/tide-rules.json`. `npm run decode` includes it. All five raw maps, packed regions and PNGs remain intact.
- `TideSystem` derives phase from the existing paused expedition clock, which begins08:00 and advances one displayed minute per real minute of active play. The phone instead samples epoch/device minutes through `aa.d()`. Menus, hidden tabs, title, minigames and collapse pause browser play. There is no new accelerated production timer.
- Version-one saves already persist `elapsed`; phase and time until the next change derive from it. New expeditions reset to08:00/high. Legacy checkpoints load their corresponding phase. No second timer or schema migration can drift independently from the displayed clock. Explicit Sleep continues its existing reserve-recovery behavior; it does not advance the browser expedition clock.
- A separate runtime map copies/remaps only FULL/Borders from the immutable validated source. Rocks/Decor/Objects are unchanged references; all runtime occupancy remains separate. Its SHA fields retain source provenance, not a claim that remapped arrays match the archive. Only the raw source map is passed through the archive validator.
- Phaser updates collision, pathfinding, fish-shore selection, wildlife terrain, base rendering and animated shorelines together at each boundary. Any queued mouse path is cleared. Player position follows the source shore-recovery search; no guessed health loss, swimming mode or unrestricted water route is added.
- Terrain is cached in36 chunks per phase, with two phase variants at most:72 × 480px textures, roughly63MiB of RGBA pixels plus browser/GPU overhead. Phase changes replace visible chunk images and shoreline sprites, not the game instance. Shore animation uses its existing400ms cadence and current frame.
- Stable wildlife snapshots are retained through transitions and legacy tide reconciliation. Passive home and shark-water relocations use the recovered checks; the browser avoids overwriting occupied/player cells and clears contact exposure on relocation. Other actor states, random streams and prior hunting removals persist rather than reproducing the source's entire object reshuffle.
- The top clock reports High/Low tide and a next-change tooltip. The large chart explicitly records the original coastline; it is not represented as a live passability map. Notifications and mouse travel are browser presentation adaptations.

## Route evidence and remaining access

`tools/audit_tide_routes.ts` writes `evidence/tide-access-audit.json`. This is an optimistic, fixed-phase walking flood fill: ignore all Objects, retain original Decor/Rocks, foot-only results exclude raft travel/decor clearing/source random placement. It is not a complete acquisition simulation.

| Phase | Spawn-connected walking cells | Important interaction access |
| --- | ---: | --- |
| High |13,251|Broken raft, Compass, Map, Anchor reachable under this optimistic model; Saw, Hammer, shipwreck, Sextant cut off|
| Low |17,813|Saw and Hammer additionally reachable; shipwreck and Sextant still outside this component|

A separate raw-object BFS found a67-cell walking path from spawn to within32px of the original Saw. It crosses from(41,169) through original water5 at(42…46,169) only at low tide. Browser QA positions Robinson at that passage, with no supplied items or relocated objects, then actually crosses, approaches and picks up the source Saw at(50,164). This verifies the crossing and pickup, not a manually played journey from spawn or all tool/material dependencies.

The same audit now also evaluates the original raft’s connected swamp component:2274 raft cells, reaching walking components totalling14633 cells at high tide and19120 at low. Assuming the raft has already been repaired, its adjacent shores connect to interaction range of both shipwreck and Sextant. No raft is carried over land or spawned at another shore. Objects and material acquisition are still ignored; this is access evidence, not a full playthrough.

Next is verification of the complete tide/tool/crafting/raft/escape route with real inventory transactions. Remaining resource/decor interactions and original object filtering/placement need separate fidelity investigation, along with weather, emulator comparison and original RMS compatibility. The complete original expedition is not claimed finished.

## Validation

146 tests pass (10 tide checks), TypeScript/build pass, and the final production checkpoint restores Low tide and the collected Saw entirely offline. Browser checks cover blocked high-water movement, the real falling-tide crossing, original Saw pickup, rising-water rescue and save restoration. Tests and route audit separately verify combined post-repair raft access. Detailed preconditions, observed values and timing limits are in VALIDATION.md. No full start-to-escape playthrough is claimed.

## Subsequent supply-history audit

Tide-time static supply renewal is now ported separately in RESOURCE_RENEWAL.md. This corrects the earlier browser assumption that every resource removal persists forever; exact20/70 buffers allow enough Sail/Nail/Vine for the construction plan. The original random nearby placement and complete global filter remain unported; the browser restores eligible canonical source cells.
