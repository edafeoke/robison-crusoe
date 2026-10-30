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
- Sailing uses a different terrain predicate; not implemented.
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
