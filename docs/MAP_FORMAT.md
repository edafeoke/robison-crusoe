# Original island format

Verified against `data.g`, `data.p`, `data.w`, disassembly, and all source bytes.

## Binary layers

Each `BIGisland{FULL,Borders,Rocks,Decor,Objects}.byt` is **exactly 32400 bytes**, with no header or compression. Dimensions are 180 columns ×180 rows. Offset is `x + y*180`; x increases right, y down. Tile size is **16×16**, explicitly initialized in `p.M` and used throughout g. Java reads unsigned stream values 0…255 then casts to signed `byte`. All supplied values are <128, so this archive has no signed/unsigned ambiguity. Web exports preserve the raw unsigned value, never subtract 1 globally.

The world is 2880×2880 pixels. Terrain and rock anchors are top-left. Borders/decorations use cell center `(x*16+8,y*16+8)` followed by the sprite-region anchor subtraction. Character position uses a foot anchor. Border frames may extend beyond a single tile.

| Layer | Meaning and exact sprite mapping |
| --- | --- |
| FULL | Every cell drawn; zero is a real tile. `bank=1, frame=ID` (16 terrain frames). |
| Rocks | Zero empty. IDs 1…48 draw `bank=4, frame=ID-1`; IDs ≥50 are not drawn by g.f and have special semantics elsewhere. |
| Borders | Zero empty. IDs 1…22 use mapping `p.o[ID-1]`, exported in atlas.json. Mix of BS8 and BS11. |
| Decor | Zero empty. ID uses explicit `p.p[ID]` mapping in atlas.json. IDs 1,5,13,18,21,22,49 additionally invoke one of the recovered h.java composite variants. |
| Objects | Zero empty. Nonzero ID maps to item index `ID-1`. Exact icon references from y; dynamic families in g may render differently. Not a terrain tileset. |

## Collision (walking)

- Reject off-grid tiles.
- FULL IDs 5,6,12,13,14 block walking in `g.a(int,int)`.
- Any nonzero Decor or Objects cell blocks in `g.c` / `g.d`.
- Rocks passable: 0,10,11,12,18,20,22,23,24,30,31,32,38,41,42,43,44,45,46,47, or ≥50, from `g.e`.
- Raft terrain predicate `g.b(int,int)` permits FULL 12,13,14 only (the integer terrain identity table, not the byte camp-requirement table). Boarding/travel/disembarking are now ported with this predicate; dynamic tides are now ported separately (TIDES.md).
- Port uses a small foot box and axis-separated movement; pathfinder shares the same grid. Removed pickups clear the runtime object blocker without changing raw layers. The wildlife milestone maintains a separate object/ownership grid for moving animals. Ground animals occupy their current cell; flight releases occupancy and landing reserves a free cell. Player collision, pathfinding and inspection use this runtime layer, including animals that move onto a previously collected resource cell. Original Objects IDs and all source layer bytes remain intact. See WILDLIFE.md.

## JSON export

`python3 tools/decode_original.py` creates `public/assets/maps/island.json`:

```json
{"version":1,"width":180,"height":180,"tileWidth":16,"tileHeight":16,
 "encoding":"unsigned bytes; row-major x + y * 180",
 "layers":{"FULL":[0],"Borders":[0],"Rocks":[0],"Decor":[0],"Objects":[0]},
 "sha256":{"FULL":"original raw SHA-256"}}
```

Arrays here are abbreviated for documentation only; actual arrays each have 32400 entries. Original `.byt` copies accompany JSON. Tests reconstruct `bytes(array)` and compare SHA-256 for all five layers, and compare against JAR originals. Runtime validates dimensions, ranges and lengths. Source checksums are in `evidence/archive-inventory.json`.

## Rendering and constraints

Phaser renders base terrain, rocks and borders into 36 independent 480×480 CanvasTextures (30×30 tiles each), avoiding oversized textures. Decorations and objects are sprite regions sorted by foot depth; recovered vegetation commands preserve their relative drawing order. Camera shows a fraction of the island and follows the character. Full island overview derives from the same source data and sprite metadata.

No guessed IDs or newly generated terrain replace the original map. Tiled export is deferred: plain terrain tileset JSON cannot accurately encode the anchored composite vegetation and special objects without a separate object layer.

## Camp progression overlay

Objects map ID 18 occurs at original grid (24,129), index 23244. Source `a.G` upgrades its item-17 sprite/name in place; it does not rewrite the map. The browser likewise retains map ID 18 and its original collision cell while selecting recovered Tent/Cabin artwork from the persistent camp tier. No terrain, border, decor, rock or object bytes change, and no free-placement grid is invented. See SHELTERS.md.

## Ship reconstruction overlay and access audit

Original Objects ID 19 at (152,30), index 5552, stays unchanged through repair. Only the ship sprite/flag overlay changes from BS6 frame 19 to frame 20, with original anchors. It remains a blocker. The source moves Robinson north two tiles; it does not move the map object. `evidence/ship-access-audit.json` diagnoses the current fixed-terrain walking component with all Objects ignored: the wreck and some required tools remain outside it. This earlier diagnostic does not model original tides/raft/decor updates; raft traversal is now ported separately. No map edits or unrestricted water walking conceal that unfinished traversal. See SHIP_RECONSTRUCTION.md.

## Raft runtime overlay

Original Objects85 at (94,94), index17014, stays immutable in source data. Repair produces runtime95 there; boarding removes it; landing places95 at the prior water cell. The one raft overlay is restored after pickup tombstones and before wildlife occupancy. A raft docked on an earlier removed resource cell remains visible/blocking through future pickups and saves. Raft travel substitutes FULL12/13/14 for the walking terrain rule, while retaining object/decor/rock blockers. Shore priority is left/right/up/down; browser additionally applies the missing source rock check. See RAFT_TRAVERSAL.md.

## Tide overlay

FULL4→0,5→4,6→5 and nine Borders substitutions apply at low tide; high tide restores copies of the original layers. Objects/Decor/Rocks and raw bytes are never rewritten. Live walking/raft collisions and both fishing-shore interactions use the same phase map as Phaser terrain/shore rendering. Static charts keep the original coastline and are labelled accordingly. Source provenance hashes remain associated with the raw map, not the remapped arrays. See TIDES.md and evidence/tide-access-audit.json.

## Static supply renewal

Original non-mobile source indices identify resource histories in the browser. The raw Objects array stays immutable. At refresh, an eligible removed cell leaves the runtime removal set and regains its original map ID, sprite and collision blocker. The20/70 histories and carried-unique filter determine eligibility; occupied cells defer. This differs explicitly from source randomization/short-ID movement. See RESOURCE_RENEWAL.md.
