# Mangrove raft reconstruction and traversal

Audited 2026-11-01 against retained CFR and JVM disassembly from the supplied JAR. No emulator comparison has been completed. This is swamp traversal, not unrestricted ocean sailing. Dynamic tides and a verified full expedition remain separate work.

## Verified source

- `a.c(int)` sends Broken raft item84 to `B()` and Raft item94 to `A()`. The former sets the dedicated `aD` reconstruction flag; the latter exposes the embark/disembark action. The only original map85 cell is **(94,94), index17014**.
- `a.G()` result94 sets `p.ap:I=1` and replaces runtime map85 with95. `ad.o()` consumes Stick36×6, Rope91×5, Plank37×5, Nail13×5, Tree root35×3 and Moss39×4. Required Saw78×1 and Hammer77×1 are retained. `ad.l()` applies **−30 Energy, +10 Morale**, zero Strength; no portable raft inventory item is produced.
- `ad.u()` removes the adjacent raft cell, initiates a movement toward it and sets `p.T:Z`. Horizontal visual orientation comes from `p.f[94]` (393224 left /393225 right). The initial sprite is393224. Vertical paddling retains horizontal orientation.
- **Correction to the preliminary ship audit:** `ab.a/b/c/d(4)` does not mean four tiles or four grid steps. Each parameter is unused in the decompiled method and bytecode; movement uses `p.aj:I = M/2 =8` pixels per ≥25ms tick and completes a **single16px tile step**. The browser snaps the transition to that adjacent cell centre rather than reproducing phone half-step timing.
- While `T` is true, `ab.c()` substitutes `g.b(int,int)` for the terrain predicate, while continuing the Rock/Decor/Objects checks. Raft terrain permits **FULL12,13,14 only**. Ground walking continues to reject5,6,12,13,14. Collision does not become generic water walking.
- `a.a()` while aboard offers only landing interaction. `a.b()` tests adjacent shore in priority **left, right, up, down**. It calls `g.a:(II)Z` twice, then `g.d` (Objects) and `g.c` (Decor); JVM confirms the duplicated terrain call and omitted `g.e` rock test. `ad.v()` clears `T`, chooses the static raft orientation, leaves map95 at the previous player cell and initiates one tile toward the first eligible shore.
- `t` persists repaired status, raft location and aboard flag; when afloat, location derives from the player cell. It contains a special14925→14926 correction. The browser uses its own validated IndexedDB schema, not RMS serialization, and does not apply that source-specific correction to arbitrary web saves.
- World needs and wildlife keep updating while paddling. The raft does not grant hazard immunity. Inventory menus, food use and short rest remain separate actions; raft rendering retains the seated composition while resting.

## Original graphics and animation

Broken raft: **393232 / BS6 frame16**. Static repaired orientation: **393224/393225 / frames8/9**. `ab.b()` supplies four ordered drawing branches:

| Facing/pose | Ordered fixed layers, with shirt/hat inserted after body |
| --- | --- |
| Left0 |393224,196623,393226,393230,393233|
| Left1 |393224,196624,393226,393228,393233|
| Right0 |393225,196618,393227,393231,393234|
| Right1 |393225,196619,393227,393229,393234|

The source stroke counter cycles0…6; values0…2 use pose0 and3…6 pose1. `ab.k()` renders **shirt then hat only**; pants/shoes are hidden by the raft. Seated Robinson/shirt faces opposite the raft's horizontal direction. Original hat offsets use the first two opposite-direction constructor entries. `tools/recover_raft.py` exports exact source frame IDs and both clothing branches in `raftRules.ts` and `evidence/raft-rules.json`. Every reference resolves through unchanged D0 metadata and original PNGs. No uniform crop grid or guessed sprite mapping is used.

## Browser implementation and explicit adaptations

- Optional version-one `raft` state holds broken/moored/aboard mode, moored cell and left/right orientation. Earlier saves default to the original broken raft. Afloat saves retain water position/orientation; moved docks restore at their actual cells. A completed ship expedition cannot also be aboard a raft.
- The runtime object overlay clears original85 after repair, displays95 at the current dock, and removes the static object while aboard. Pickup tombstones are applied before this overlay, so a raft can occupy a previously collected resource cell. Subsequent collection does not erase that dock. Original map layers remain immutable.
- Repair is a dedicated nearby-world action with exact materials/equipment and effects. It uses the existing immediate browser crafting transaction rather than the phone creation interstitial. The existing workbench remains43 recipes; the raft is not a global camp or portable recipe.
- Board requires a cardinally adjacent clear foot tile and the actual live raft. The interface chooses the direction toward it, rather than requiring the phone's facing-only action menu. Boarding/landing centre the player in the single adjacent destination tile immediately; no invented multi-tile jump or teleport across terrain.
- Keyboard movement and mouse A* share the active raft collision predicate. Raft movement chooses one cardinal axis at a time; vertical movement keeps left/right artwork. Continuous64px/s movement is the existing browser speed adaptation. Paddling poses advance per8 travelled pixels, retain their idle pose and use the source's seven-phase pattern, rather than asserting exact Java loop timing.
- Landing preserves original direction priority but **also requires passable rocks**. This documented normalization avoids placing Robinson on a rock tile from which normal browser walking cannot continue. It does not change the raw map or allow land movement while aboard.
- Needs, wildlife/hazards, menus, short rest, satchel use and saves continue under the existing browser rules. World gathering/food/minigame/ship actions are unavailable aboard, matching the original raft-only contextual branch. Menus and hidden tabs pause play as before.
- The unobtrusive mangrove status strip opens the actual landing interaction. Repeated shore/raft transitions and camera follow retain one Phaser instance; owned render layers and listeners are scene-cleaned.

## Remaining route dependencies

This does not implement weather/tide remapping, remaining resource/decor mutations, original half-step control timing, RMS compatibility or emulator comparison. The earlier fixed-terrain walking audit excluded raft travel and is retained as historical evidence. Saw is required to repair the raft yet lies outside that walking component, so implementing the transport alone does not establish a legitimate material acquisition route. No required tool, raft or ship is relocated, no unrestricted ocean travel is added, and no complete new-game-to-escape route is claimed. Browser QA supplies repair materials/tools and positions Robinson beside the unchanged original raft, with those preconditions labelled.

## Validation record

136 automated checks pass (11 new raft tests), TypeScript and final production build pass, and the final version was restored offline from an afloat IndexedDB checkpoint. Browser QA covers actual repair, wardrobe equipment, keyboard/WASD paddling, mouse travel, blocked landing, moved-dock reload/reboarding, final landing facing and afloat offline continuation. Original user port4173 was untouched; isolated4198 was used. See VALIDATION.md and `evidence/raft-offline-restored.jpg` for detailed preconditions and observed values.
