# Validation record

## Current milestone: Pirate ship reconstruction and escape

Date: 2026-11-01. **125 tests passed**, including 11 new ship checks. TypeScript and production build passed. Final classic entry: **index-Dhcqv5Qb.js**, 1,637.28 kB / 458.80 kB gzip. Existing 1,600 kB chunk advisory remains; no dependency added. Full decoding succeeds; all typed rule files and object metadata regenerate byte-for-byte. Map/sprite integrity tests remain green. No development fixture files in dist. Source findings and explicit adaptations: SHIP_RECONSTRUCTION.md.

### Source and automated coverage

- Original single wreck at (152,30), map19/index5552, BS6 wreck/repaired frames19/20; both original flag anchors, frame IDs and cadence checked against D0/source.
- Large sail54 with original sprite−1 is a valid supply recipe. All six ship material groups are consumed, all six tools retained, no portable ship created. Exact activity effects, missing materials/tools, rest/collapse, live cell/distance checks and duplicate settlement covered.
- Northward 32px player shift, independent strict >8000 ms ending timer, action/menu locks and collapse/recovery clock behavior checked. The pure timer has no need effects; the normal Phaser world loop continues needs and wildlife while departure input is locked.
- Optional version-1 phase/timer validation, malformed phases, loss consistency, legacy migration, completed-save load and new-expedition resets checked. Earlier checkpoints are retained until explicitly overwritten.
- Separate raft audit retained without enabling an incomplete mode; terrain12/13/14 verified against the integer identity table and raft predicate, not CSV byte prerequisites.

### Browser observations

Isolated origin **4197** was used; the user's **4173** expedition was untouched. `tests/browser/ship.html` explicitly supplies reconstruction materials/equipment and Large sail ingredients and positions Robinson beside the actual unchanged wreck. It does not claim a manual full-island journey. The final fixture additionally starts active survival 0.5 seconds before a reserve tick.

- Original wreck inspection lists six material groups and six kept tools; repair disabled with Large sail0/1. Actual workbench reports **43 recipes**; Craft Large sail consumes Sail×4/Fabric×1/Thread×1, retains Scissors/Needle and changes Energy100→90. Actual inspection then enables reconstruction.
- Real repair consumes the materials, retains **eight tools total** (six ship tools plus Scissors/Needle), keeps source object19, moves Robinson from **(151,29) to (151,27)** and replaces the wreck with original repaired art and flags.
- Paused departure at **315 ms**, Energy90. Save/Sleep/rest/inventory/crafting controls disabled. Resume plus held Right750ms and pause yielded **1238 ms**, unchanged position **(151,27)**, and **Hunger90 / Thirst86 / Energy86**. This confirms ongoing reserve depletion under the movement lock, rather than granting final-scene immunity.
- Resumed to original victory text97/98 at terminal elapsed **8001 ms**, with the same reserves, source cell and eight tools. `evidence/ship-rebuilt-departure.jpg` shows the recovered ship and real countdown; `evidence/ship-ending-fixture.jpg` records the labelled ending setup. Actual completed-save control reported success.
- Real Begin another expedition reset to spawn **(8,148)**, full reserves, empty satchel and unrepaired state. The saved completed checkpoint survived and was restored in the production root with **90/86/86**, eight tools and the original ending. No fixture controls appear in production.
- After production reported assets cached, stopped the owned preview server and cold-reloaded. Real Continue restored the ending offline with the same **90/86/86**, position and tool count. Screenshot: `evidence/ship-ending-offline-restored.jpg`. Final DOM inspection confirmed **index-Dhcqv5Qb.js**, exactly **one canvas**, captured warnings/errors empty. Retained cached production tab; owned dev/preview servers stopped. No viewport override used.
- During development, a nonexistent “Pause departure” locator timed out; a fresh accessibility read exposed the actual Pause button, which worked. An earlier eight-second locator wait hit the browser provider's short deadline; subsequent observed state confirmed completion. Source edits caused HMR reloads during early runs; the final source-stable transaction above was repeated before saving. No completion is inferred from a timed-out action.

### Remaining limits

The optimistic fixed-terrain walking audit reaches **13,251 cells**, with every Objects blocker ignored. The wreck, Saw, Hammer and Sextant remain outside that component. Raft travel, tides and remaining decor/resource updates are needed to complete the access journey; no full new-game-to-escape playthrough is claimed. The standalone ending is tested under explicit prepared preconditions. Original interstitial/menu timing, emulator comparison, RMS compatibility, weather/tides, non-sleep offline arithmetic and further original special actions remain pending. See SHIP_RECONSTRUCTION.md and evidence/ship-access-audit.json.

## Previous milestone: recovered direct world-food consumption

Date: 2026-11-01. **114 tests passed**, including nine new world-food checks. TypeScript and production build passed. Final classic entry: **index-CuFE_67H.js**, 1,626.47 kB / 456.20 kB gzip. The existing 1,600 kB chunk advisory remains; no dependency added. Full decoding succeeds; world-food/Weight/typed object metadata regenerate byte-for-byte. Original map/sprite integrity checks pass; development fixtures are absent from dist. Source branches and explicit adaptations are documented in WORLD_FOOD.md.

### Source and automated coverage

- All **22** static food profiles derived from original action priority, use-tool gates and typed object data. Original hunting/fishing targets, structures and decor palms do not get a generic eat shortcut.
- Exact world effects for every supported food, doubled free-fruit Energy, source sentinel differences from satchel use, caps and once-only nutrition. Direct Coconut use keeps Stone and does not advance the six-opening satchel counter.
- Both fatal Energy return points preserve the object and omit nutrition. Browser zero-reserve/actual-food poison-label normalization and exploration override are explicit; neither invents a new poison mechanic.
- Matching live cells, integer IDs/bounds, range, removed-cell checks, tools, rest/minigame/start/collapse locks, immutable source maps, runtime occupancy, one-time settlement and prevention of fatal save overwrite.
- Existing version-1 removal/state persistence and load continuation; all prior survival, Weight, food, inventory/crafting, camp/house/clothes, sleep, wildlife/hazards and minigame checks remain green.

### Browser observations

Isolated origin **4196** was used. The user's existing **4173** expedition was untouched.

- `tests/browser/world-food.html` explicitly labels its preconditions: Robinson positioned near actual unchanged original food cells; reserves 40/40/40 (poison Energy 100), nutrition accumulator 49, and specifically supplied tools. Original wildlife snapshots reset between prepared cases. No manual journey, tool creation, gathering or food relocation is claimed for this fixture.
- **Mango at (70,60)**, Robinson **(71,61)**: discovery offered separate **Eat mango here** and **Gather mango**. Direct eating produced **Hunger 43 / Thirst 55 / Energy 56**, body bar **1/64** (1.6%) / nutrition **2**, runtime object **0**, empty satchel. Screenshot `evidence/world-food-mango-dialogue.jpg` shows the labelled fixture's real action.
- **Coconut at (108,15)**, Robinson **(107,15)**: supplied Stone×1 and opening counter **5/6**. Direct use produced **43 / 65 / 56**, body bar **1/64**, nutrition **2**. Runtime object became **0**, Stone remained **1**, counter stayed **5/6**. Screenshot: `evidence/world-food-coconut-kept-stone.jpg`.
- **Pineapple at (24,32)**, Robinson **(23,32)**: direct Eat was disabled without Knife while Gather remained available. Supplied the labelled original Knife, then used the actual Eat control. Result **45 / 65 / 56**, nutrition **4**, object **0**, Knife×1 kept and no pineapple added to inventory.
- **Red mushroom at (34,21)**, Robinson **(35,22)**: Fire×1 supplied, Energy 100. Warning displayed **−100 Energy**. Real use caused poison collapse, Energy **0**, Hunger/Thirst still **40/40**, object still **32**, nutrition still **49**, Fire×1 kept and save disabled. Screenshot: `evidence/world-food-poison-loss.jpg`.
- Used the real exploration recovery control and re-inspected that still-present mushroom. Exploration use cleared it, kept reserves at 100, retained Fire and changed nutrition **49 → 48**, with no duplicate inventory effect. This is the documented browser loss override.
- Prepared the original Mango again and saved **before** eating via normal IndexedDB UI. Production root restored **(71,61)**, **40/40/40**, empty satchel, zero Weight bar and the still-available Mango. No fixture controls in production.
- Stopped the preview server and cold-reloaded production. Continued the checkpoint offline, resumed, and the actual nearest prompt was **E Examine Mango**. Pressed **E** and used **Eat mango here**: exact **43/55/56**, Weight **1.6%**, empty satchel, Mango gone and next prompt **Red berry**. The Weight step also confirms the saved nutrition remainder 49 was restored. Screenshot: `evidence/world-food-offline-dialogue.jpg`.
- During the first offline observation, the world was left running and nearby wildlife eventually caused animal collapse. The earlier checkpoint was preserved. Reloaded it through the normal loss dialog, repeated the real offline meal, saved immediately and paused. Screenshot `evidence/world-food-offline-eaten.jpg` records that successful production world transaction. No poisoned/animal-loss state overwrote the save.
- Cold-reloaded the new save with the server still stopped: **43/55/56**, Weight **1.6%** and empty satchel restored. The next actual interaction remained **Red berry**, with the eaten Mango absent. `evidence/world-food-offline-restored.jpg` shows the restored world. Final tab is paused; the saved checkpoint contains the completed meal.
- One cold-load Continue click timed out while the page materialized; a fresh accessibility read showed the title screen, and its actual Continue control recovered the flow. Final production entry confirmed **index-CuFE_67H.js**, exactly one canvas and captured warnings/errors empty. Retained the cached production tab; owned dev/preview processes stopped. No viewport override used.

### Remaining limits

Exact source facing/menu presentation, Java ME emulator comparison, remaining special-object actions, boat/raft/story escape progression, weather/tides, non-sleep offline arithmetic and RMS compatibility remain outstanding. Radial mouse interaction, loss normalization, actual-food poison labelling, exploration recovery and guide wording are documented browser adaptations. No complete original adventure or exhaustive manual check of every food placement is claimed.

## Previous milestone: nutrition-driven Weight and carrying-capacity audit

Date: 2026-11-01. **105 tests passed**, including eight new Weight checks. TypeScript and production build passed. Final classic entry: **index-CxM8jFtp.js**, 1,622.75 kB / 455.46 kB gzip. The existing 1,600 kB chunk advisory remains; no dependency added. Full decoding succeeds, and Weight/hazard/typed object metadata regenerate byte-for-byte. Original map/sprite integrity checks pass. Development fixtures are absent from dist. Detailed source findings and explicit browser choices: BODY_WEIGHT.md.

### Source and automated assessment

- The float Weight field `p.g:F` is read/written only by the recovered stat display, nutrition/reset and save classes. No weight-based movement or carrying-capacity gate was found in the inspected inventory/action paths. Quantity arrays sharing the name `g` are distinguished by their descriptors. No guessed mass or slot limit was imposed.
- Exact single-if threshold 50, one native bar-unit step, queued large-meal remainder, maximum-bar behavior, negative and signed-int nutrition, full-reserve consumption and once-only tool nutrition. A fixed 240-wide browser reference yields max 64 native units; that width is an explicit presentation choice.
- The extraction regression check caught an initial match against unrelated loading-screen width arithmetic; the final decoder anchors the exact reserve-initialization assignment. Descriptor provenance and all three original BS10 stat icons are checked.
- Successful, unavailable, missing-tool, resting, pre-start, full-Hunger and fatal-poison transactions; exploration growth; crafting/discard/rest independence. Normal save and Sleep retain the accumulator; loading a legacy save resets it to zero rather than inferring past meals. Invalid/out-of-range bars and malformed counters rejected.
- All previous survival, original assets/maps, movement, inventory/crafting, camp/house/clothing, sleep, wildlife, world contacts and minigame checks remain green.

### Browser observations

Isolated origin **4195** was used. The user's existing **4173** expedition was untouched.

- Development-only `tests/browser/body-weight.html` labels its supplied **Mango×17, Wild goat×1, Pineapple×1, Coconut×1 and Stone×1**, starting Weight/nutrition zero and active survival. A separate visible button supplies a Knife. No manual gathering, hunting or tool creation is claimed for these supplies.
- Selected Mango in the real satchel and used the actual **Eat mango** button 17 times. Hunger stayed **100**, all 17 items were consumed, Weight advanced **0 → 1/64** (**1.6%**), with nutrition remainder **1**. Last Mango use then became disabled.
- Ate Wild goat through its normal button: raw nutrition **100** produced only **one** additional Weight unit, **2/64** (**3.1%**), with remainder **51**. Screenshot `evidence/body-weight-large-meal-fixture.jpg` retains the labelled setup and observed accumulator; no loop/modulo substitute is used.
- Pineapple initially required Knife and its Eat button was disabled; the queued remainder stayed 51. Supplied the labelled Knife, then ate through the real control. Weight became **3/64** (**4.7%**), remainder **6**, and Knife remained owned. Its existing duplicated Energy calculation does not duplicate nutrition.
- Ate Coconut using the kept Stone: Weight stayed **3/64**, remainder became **9**, stone-opening counter **1/6**, Stone retained. All food disappeared from the satchel; Knife and Stone remained one each.
- Opened **View Robinson’s condition** from the HUD. Original Strength/Morale/Weight icons, accessible meters **0% / 0% / 4.7%**, and the body-weight/carrying distinction were visible. Saved through the normal IndexedDB button. Screenshot: `evidence/body-weight-condition-fixture.jpg`.
- Loaded the production root on the same origin: Weight **4.7%**, full reserves, position **(8,148)** and the two kept tools restored, with no fixture controls. Stopped the preview server, cold-reloaded, chose Continue and reopened the condition view. The same Weight and inventory count restored offline. Complete raw-counter persistence/continued arithmetic is covered automatically; the production UI intentionally displays the bar rather than the raw accumulator.
- Final production screenshot `evidence/body-weight-offline-restored.jpg` shows the restored condition view. Entry confirmed **index-CxM8jFtp.js**, exactly one canvas, captured warnings/errors empty. Retained the cached production tab, paused; owned dev/preview processes stopped. No viewport override or browser recovery was needed in this milestone.

### Remaining limits

Direct world consumption (`ad.j`) remains distinct from implemented satchel eating. Remaining special object actions, original boat/raft/story escape progression, weather/tides, non-sleep offline arithmetic, emulator comparison and RMS compatibility remain outstanding. Body-weight bar width and percentage UI are browser adaptations; original byte-save truncation is deliberately not reproduced. No unverified carrying restriction or weight-driven hazard is claimed.

## Previous milestone: recovered world-contact hazards

Date: 2026-11-01. **97 tests passed**, including eight new world-hazard checks. TypeScript and production build passed. Final classic entry: **index-B5pno_Wk.js**, 1,619.79 kB / 454.66 kB gzip. The existing 1,600 kB chunk advisory remains; no dependency added. Full decoding succeeds; hazard/clothing JSON/TypeScript and typed object metadata regenerate byte-for-byte. Original map and sprite integrity tests pass. QA fixtures are absent from dist. See WORLD_HAZARDS.md for verified source facts and explicit adaptations.

### Automated coverage

- All four independently extracted `d` slot/cost rules, irrelevant/wrong-slot clothing and `i` shark loss. No invented health meter or generic drops.
- Chebyshev-one tile range including diagonals, strict >1000 ms, leaving-range reset, source sailing timer exemption and immediate shark behavior without that exemption.
- Actual wildlife tick integration, per-animal 1080 ms attack cycles at the fixed browser cadence, view freezing, removal, partial-exposure save continuation and legacy zero migration. Invalid contact timers rejected.
- Partial short-rest credit after damage, one-time interruption, preserved unprotected clamp quirks, normalized protected fatal loss, valid collapsed saves and exploration safety.
- Existing map, sprites, movement, resources, inventory, all crafting/camp/house/clothing, food, sleep and four minigame regression checks remain green.

### Browser observations

The user's existing **4173** expedition was untouched. QA used isolated origin **4194**.

- `tests/browser/world-hazards.html` explicitly labels its setup, supplied Hat and automatic pause after the first energy change. It moves Robinson to actual original actors without moving animals or modifying map layers. No manual travel/crafting supply chain is claimed for this fixture.
- Beside original **Boa actor 1091**, at walkable player grid **(11,5)**: bare **100 → 80** energy; with the supplied worn Hat, **100 → 90**. Each real world attack produced the corresponding named notice. Between tests, settings kept the world paused.
- Prepared energy 60 and Hat, then used the actual **Rest a moment** game control. The Boa interrupted rest: **60 → 53**, with notice **−10 energy / +3 partial-rest energy**, rest cleared, no collapse. Screenshot: `evidence/world-hazards-interrupted-rest.jpg`.
- Prepared original **Shark actor 1111**, leaving it at its actual source cell **(31,6)** and placing Robinson on a walkable original shore at **(31,8)**. The shark approached through water and immediately exhausted energy: **100 → 0**, animal-loss state, original dangerous-animal message and disabled save. Continued through the real exploration recovery button; reserves returned to 100. Screenshot: `evidence/world-hazards-shark-loss.jpg`.
- Returned to the protected Boa case, observed **100 → 90**, then saved through normal IndexedDB UI. Loaded the production root on the same origin: position **(11,5)**, energy **90**, one owned/equipped Hat and its **10-energy** Boa protection restored. No fixture controls in production.
- Stopped the preview server, cold-reloaded production and continued the save: the same position/energy/outfit restored offline. Resumed the actual world offline and observed another protected attack **90 → 80**, then paused in settings. `evidence/world-hazards-offline-world.jpg` captures the actual production world, original Boa, Robinson's Hat, energy 80 and the **Boa · −10 energy** notice. The saved checkpoint remains at 90; the final paused in-memory state is 80.
- Production entry confirmed as **index-B5pno_Wk.js**, exactly one canvas and captured warnings/errors empty, including after offline reload. Retained the cached production tab, paused; owned dev/preview processes stopped.
- One development HMR accessibility snapshot timed out, recovered on a fresh read. One full-page screenshot failed; a native screenshot then succeeded. These did not prevent the final production/offline verification. No viewport override used. Screenshots `world-hazards-bare.jpg` and `world-hazards-protected.jpg` retain the labelled fixture comparisons.

### Remaining limits

Java ME execution/emulator comparison, original boat/raft and story/escape progression, remaining special object actions, inventory weight/capacity, weather/tides, non-sleep offline arithmetic and original RMS save compatibility remain outstanding. The browser's fixed wildlife ticks, menu/hidden-tab pause, optional exploration mode, new warnings and normalized negative-reserve loss are documented adaptations. No exhaustive manual tour of all predator placements or full source timing parity is claimed.

## Previous milestone: recovered clothing and hunting protection

Date: 2026-11-01. **89 tests passed**, including nine clothing checks. TypeScript and production build passed. Final classic entry: **index-BwCtSQDL.js**, 1,617.21 kB / 453.71 kB gzip. The existing 1,600 kB chunk advisory remains; the single classic bundle preserves the verified offline behavior. Original map/sprite integrity checks are green. Clothing JSON/TypeScript and typed object metadata regenerate byte-for-byte. QA fixtures are absent from dist; `git diff --check` passed. No dependency added.

### Automated coverage and source assessment

- All 16 source garments, slot/category mappings, exact recipes, reusable tools, global camp gates and unique wardrobe ownership. Crafting does not auto-equip or add garments to the satchel.
- All **256 BS5 regions**, source layer order, four directional five-frame sequences, static directional hats and all eight bytecode hat-offset arrays (four omitted by CFR).
- Ownership and correct-slot checks, immutable equip/remove operations, invalid/duplicate/wrong-slot save rejection and legacy unequipped migration.
- All garments tested against all four predators: pants/alligator 25 vs 50, shirt/jaguar 25 vs 50, shoes/green snake 5 vs 10, hat/boa 10 vs 20. No effect for unrelated slots/species.
- Hunting captures the worn outfit; grouped protected retaliation totals **65**, bare total **130**. Existing ammunition, one-time settlement, source timing and actual animal removal tests remain green. Wardrobe changes are blocked during challenges/rest/collapse.
- Creation energy/morale, tool retention and persistence of ownership/worn IDs. CSV row 11 matches garment slot metadata but has no confirmed runtime consumer; actual slots are independently verified by category and renderer switches. See CLOTHING.md.

### Browser observations

Isolated origin **4193** was used; the user's existing **4173** save was untouched.

- Dev-only `tests/browser/clothing.html` labels its supplied Cabin, garment materials/tools and active survival. Through the real workbench buttons crafted **T-shirt, Loincloth, Straw shoe and Hat**. Energy changed **100 → 55**; Morale became **4**, Strength **0**; Needle and Scissors remained in the satchel. The four new recipes became Owned. No manual gathering or complete textile/camp supply chain is claimed.
- Opened the wardrobe, which initially showed no garment equipped. Equipped one in each slot through visible controls; the protection table changed **50/50/10/20 → 25/25/5/10**. Garments remained owned when worn. Four directional preview controls were exercised; down/left screenshots were visually inspected, and representative original layers aligned correctly.
- Returned to the actual starting shore, moved using Right/Down/Up, and reached grid **(10,148)**. Original clothes rendered on Robinson in the world; screenshot `evidence/clothing-world-fixture.jpg` is visibly labelled as a prepared fixture. Exact walking-frame mappings are checked automatically, not claimed to have been individually captured manually.
- The visible fixture control then positioned Robinson beside an **actual original Boa actor** near **(12,6)** and supplied Bow×1/Arrow×1, without replacing the actor or map. Boa dialogue showed its original observation and **10 energy** retaliation cost with the worn Hat. Entered a real three-target hunt; the HUD showed **Boa 10 energy** from the captured outfit. Left before shooting, preserving Arrow and reserves. No manual protected-retaliation loss is claimed; those calculations are checked automatically.
- Saved through normal settings UI. Navigated the same isolated tab to the production root, which has no fixture controls, and loaded the save. Restored four owned/worn garments, original preview, table **25/25/5/10**, energy **55**, and **Arrow×1 / Bow×1 / Needle×1 / Scissors×1** at **(12,6)**.
- Stopped preview, cold-reloaded the cached production root, and chose Continue. The same position, energy, equipment, wardrobe ownership and four worn slots returned. Opened Change clothes from the satchel and verified the protected table again.
- Final DOM check: exactly **one canvas**, script **index-BwCtSQDL.js**, captured production warnings/errors empty. Final screenshot: `evidence/clothing-offline-restored.jpg`. Other screenshots: `evidence/clothing-wardrobe-fixture.jpg`, `evidence/clothing-world-fixture.jpg`, `evidence/clothing-hunt-protection.jpg`. The saved expedition used explicit QA preparation; it is not a claimed new-game progression playthrough.
- No viewport override or browser-control failure occurred. Owned dev/preview servers stopped; the cached production tab is retained with wardrobe open, pausing survival. Restart `npm run dev`, or `npm run preview -- --port 4193` after building to serve the same QA origin.

### Remaining limits

World-contact animal hazards remain disabled pending their separate collision/attack consequence audit. No emulator comparison, complete 16-garment manual animation/retaliation playthrough or broad browser-family comparison is claimed. Outfit preview and direct selection are browser UI adaptations; wardrobe saves use stable item IDs rather than source list indices. Rest uses the source separate unclothed pose; raft-specific clothing rendering is deferred with travel. Weight, special object actions, tides/weather, non-sleep offline behavior, source save import and original story/boat/raft progression remain outstanding. See CLOTHING.md for complete evidence and scope.

## Tenth milestone: house furnishings and explicit sleep recovery (historical)

Date: 2026-11-01. **80 tests passed**, including eight house/sleep checks. TypeScript and production build passed. Final classic entry: **index-Bg1SzBZ3.js**, 1,608.17 kB / 451.21 kB gzip. The existing 1,600 kB chunk advisory remains; the intentional single classic bundle preserves verified offline loading. Original sprite/map integrity checks remain green. `git diff --check` passed. No dependency was added.

### Source assessment and automated coverage

- Four source furnishing recipes (Single bed, Barrel, Rug, Double bed), kept tools, Tent/Cabin requirements, creation costs, unique house entries and no portable furnishing output. Three have original sprite -1; no replacement mapping is invented.
- Satchel Rug loot from the original net-fishing reward quirk stays in the satchel, separate from a crafted house Rug. Old saves default to empty house records; malformed furnishing/timestamp fields reject validation.
- Sleep's positive `v.c(true)` branch restores all three reserves using 0/10/20/30/40/50 whole-hour bands. Tests cover exact boundaries, partial hours, caps, clock rollback, legacy/exploration/dead states and immutable inputs. No bed multiplier is introduced.
- Sleep saves successfully before returning to title; failed persistence leaves the active expedition intact. Readiness/rest/loss/save locks are covered, with shared activity save exclusions covered by the existing minigame checks.
- `SaveSystem.load` reads, claims a pending sleep interval, writes the recovered snapshot and removes its marker in one read/write IndexedDB transaction. Promise resolution waits for commit. Repeated loads cannot award that interval twice. Ordinary saves retain paused offline reserves.

### Browser observations

QA used separate origin **4192**; the existing user **4173** save was untouched.

- Dev-only `tests/browser/house.html` explicitly supplies a Cabin and exact four-recipe materials/tools, sets active reserves to **40 hunger / 50 thirst / 90 energy**, and labels its setup. This does not claim the gathering/crafting chain was played from a new expedition.
- Built all four furnishings through their real workbench buttons. All four became disabled as Built; materials were consumed, four reusable tools remained in the satchel, energy became **50**, Morale **20**, Strength **0**. House storage displayed one of each furnishing with original descriptions; only Barrel used recovered artwork.
- Chose the real Sleep button in settings. The successful IndexedDB write returned to the title screen with unchanged reserves. The labelled fixture then **simulated two hours away** by changing only that pending checkpoint's timestamp through its visible QA control. No two-hour wall-clock wait is claimed.
- Continue restored **50 hunger / 60 thirst / 60 energy**. Loaded the same save again through settings; those values stayed unchanged, verifying the real IndexedDB one-claim write. Screenshot `evidence/sleep-fixture-recovery.jpg` retains the simulated-time label.
- Navigated the same isolated tab to the production root, which contains no fixture controls. Continued the saved state, observed the same reserves and Cabin, then chose production Sleep and continued immediately. No bonus appeared before one hour elapsed; values stayed **50/60/60**.
- Stopped the preview server and cold-reloaded the cached production root. Continue restored position **(23,128)**, Cabin, all four house furnishings, four kept tools and **50/60/60** reserves. The saved sleep bonus did not repeat. House storage remained accessible from the restored workbench.
- Final DOM check: exactly **one canvas**, final entry **index-Bg1SzBZ3.js**; captured production warnings/errors empty. Final screenshot `evidence/house-offline-restored.jpg` shows the four restored entries. Other evidence: `evidence/house-fixture.jpg`, `evidence/sleep-fixture-recovery.jpg`.
- Initial test-page creation reported a disconnected target even though the page had opened; rebinding that existing tab recovered browser control. No game error was observed. No viewport override was used. Dev and preview processes are stopped; the cached production tab is retained with House storage open, pausing survival. Restart `npm run dev`, or `npm run preview -- --port 4192` after building to serve the QA origin again.

### Remaining limits

No original MIDlet emulator comparison, comprehensive browser-family testing or real multi-hour manual wait is claimed. Other sleep hour bands and failure paths are checked automatically. Source house artwork omissions remain intentional. Arbitrary supply containers, water capacity/refills, furnishing placement, bed bonuses, non-sleep offline depletion, device clock parity, outfits/protection, world-contact hazards, weight, changing tides/weather and original story/boat/raft progression remain unported or unverified. See HOUSE_AND_SLEEP.md for evidence and the absolute-timestamp, one-claim, ordinary-save and presentation adaptations.

## Ninth milestone: camp upgrades and recipe tiers (historical)

Date: 2026-11-01. **72 tests passed**, including eight shelter checks. TypeScript and production build passed. Final classic entry: **index-PvVUqI1m.js**, 1,603.98 kB / 450.06 kB gzip. Vite reports its existing 1,600 kB chunk advisory; the intentional self-contained classic bundle retains the verified offline-loading behavior. A loading guard keeps early workbench shortcuts safe while recovered items load. Original map/sprite integrity checks remain green. Objects/maps/atlas regenerate byte-for-byte; dev fixtures are absent from dist. `git diff --check` passed.

### Source assessment and automated coverage

- CSV row 10 is a global camp-tier gate: Flag allows -1, Tent allows -1/9, Cabin allows -1/9/10. Reusable crafting equipment remains independently required. No proximity-only workstation requirement is invented.
- Bytecode confirms that Tent/Cabin replace item 17's sprite/name/description without relocating the original camp or changing Objects map ID 18. Source placement stays at (24,129), index 23244; original single-cell collision is retained.
- Exact Tent/Cabin materials, kept tools, activity effects and no portable building output; rejection of missing tools/materials, duplicates, downgrades and unsupported furnishings/boat paths.
- Store transactions respect activity/rest/loss/readiness locks and original camp presence; a committed fatal construction settles once. All 120 exported tier requirements are checked against source raw data.
- Version-1 saves accept optional valid camp tiers, reject malformed tiers, persist the new tier and reset correctly to Flag when an old save is loaded over a Cabin session.

### Browser observations

The user's existing **4173** expedition was untouched. QA used separate origin **4191**.

- `tests/browser/shelter.html` explicitly labels its setup: original camp position, supplied Tent/Cabin/Rope ingredients/tools, survival active. No end-to-end manual supply collection is claimed.
- Camp dialogue showed the original Flag observation and construction action. Workbench displayed **22 recipes**, one initially ready; Cabin/Rope/Bow/Net remained tier-locked.
- Built Tent through its real workbench button. Energy changed **100 → 80**, source Tent artwork appeared at the original camp, previous Tent build became disabled, and Tent-tier recipes unlocked.
- Ready filter exposed actual available recipes. Crafted Rope through the UI: energy **80 → 77**, supplies consumed and Knife kept. Upgraded to Cabin: energy **77 → 47**, camp artwork changed in place, Cabin recipes unlocked and both upgrade buttons became disabled. Settings showed **Morale 15 / Strength 0**.
- Saved through normal IndexedDB UI. Production root loaded that save with **Cabin**, position **(23,128)**, energy **47**, and four remaining items (Knife, Axe, Hammer, Rope). Source camp remains at (24,129); player stands alongside it. Workbench confirmed restored Cabin tier and kept tools.
- With the preview process stopped, cold-reloaded the production root from its asset cache and chose Continue. Restored the Cabin artwork/marker, position, **energy 47**, four-item inventory and unlocked Cabin recipe tier. Exactly one canvas; captured production warnings/errors empty; final script confirmed as index-PvVUqI1m.js.
- Screenshots: `evidence/shelter-tent-fixture.jpg`, `evidence/shelter-cabin-fixture.jpg`, `evidence/shelter-workbench-restored.jpg`, `evidence/shelter-offline-restored.jpg`. Fixture screenshots are explicitly labelled; the production root has no fixture controls. The nearby generic prompt can target the original adjacent “dummi” object rather than the camp; camp inspection remains available by clicking its own cell.
- One cache-refresh browser call timed out; a fresh accessibility read recovered immediately and the final script was confirmed before its successful offline reload. No viewport override was used. Owned dev/preview processes stopped, fixture tab closed, cached production tab retained with inventory open to pause survival. Restart with `npm run dev`, or `npm run preview -- --port 4191` after building to serve the same QA origin.

### Remaining limits

Furnishings/storage, overnight shelter sleep and saved-time recovery, outfits/protection, world-contact animal hazards, original story/boat/raft progression, weight, weather/tides and emulator comparison remain outstanding. Camp upgrades are immediate rather than the source skippable completion interstitial; locked-recipe visibility/map camp markers are browser usability additions. Source animated flag overlays remain unported. No arbitrary placement, relocated shelter, larger collision footprint, storage capacity or shelter rest bonus is claimed. See SHELTERS.md for evidence and adaptations.

## Eighth milestone: bow hunting (historical)

Date: 2026-11-01. **64 tests passed**, including eight hunting checks. TypeScript/production build passed. Final classic entry is **index-DJdfJBni.js**, 1,598.94 kB / 448.66 kB gzip. Hunting rule exports regenerated byte-for-byte, all copied sprite/map integrity checks remain green, and `git diff --check` passed. The explicit hunting fixture is excluded from production dist.

### Automated coverage added

- All 15 source hunting profiles, packed frames, hitboxes, speeds/danger flags, repeated idle-frame quirks and unsupported shark case.
- Reproducible initial layouts/resting animals, exact integer trajectory points, immutable stepping and fixed active timing.
- Apex-only collision, first-miss movement, reset controls, ten-shot loss and real angle/power wins for every supported species.
- Source retaliation timing, all four unprotected costs, combined predator costs and a tenth-shot success without double settlement.
- Overlapping targets award/remove only the last intersected stable actor; one Arrow is charged for the completed attempt.
- Live actor/cell/equipment/distance checks, mutual minigame exclusion, paused/action/save restrictions, cancellation policy and fatal one-time settlement.
- Actual original-world actor removal releases current occupancy, keeps other actors/raw map intact, and restores exact remaining snapshots from a version-1-compatible save.

### Browser observations

The user's active 4173 expedition was preserved. QA used a separate **4190** origin.

- `tests/browser/hunting.html` visibly labelled its equipment/needs/seed setup. It prepared an actual Wild goat, stable original cell **1383**, with Bow×1/Arrow×2 and a deterministic hunt seed. This does not claim the full crafting chain was manually played.
- Source world dialogue, original goat description and dedicated Bow action appeared. Entering captured two nearby supported animals and opened a dedicated Phaser scene in the existing game instance.
- Through visible sliders set **angle 45 / power 70**, then Shoot arrow. The arrow flew, a first-shot win displayed **“You have hunted: Wild goat”**, one goat entered inventory, Arrow count dropped by one, Bow was kept, and Strength became 1. This run used exploration mode; active energy/retaliation costs are covered by automated tests.
- Enter on the result returned to the same northern-shore position **(124,7)**. The previous animal prompt was replaced by a nearby Stone prompt. Exact stable-ID removal/remaining population continuation is established by automated tests, not inferred solely from this prompt change.
- Saved successfully through settings at clock **08:02**, then loaded the save in the production root. Satchel showed **Wild goat×1, Arrow×1, Bow×1**. No fixture controls are present in the production root.
- Stopped the preview process, cold-reloaded the production app from cache, and restored **(124,7), clock 08:02, Wild goat×1, Arrow×1, Bow×1 and Strength 1**. Exactly one canvas was observed, and the production tab returned no captured errors/warnings. Browser evidence establishes the trophy/equipment/state restoration; exact remaining wildlife snapshots are checked automatically.
- Screenshots: `evidence/hunting-fixture-win.jpg` and `evidence/hunting-offline-restored.jpg`. The fixture screenshot is labelled as such; it does not misrepresent equipment acquisition.
- One preview accessibility snapshot timed out during reload. Closing the no-longer-needed dev fixture recovered normal browser reads; the final script was then confirmed and cold-loaded again. No viewport override was applied. Owned dev/preview processes are stopped after the final cache update. Cached production QA tab retained; run `npm run dev` for development or `npm run preview -- --port 4190` after building to serve that origin again.

### Remaining limits

No original MIDlet emulator or comprehensive browser-family comparison is claimed. Dangerous hunts and every species/reward path were not all played manually; automated simulations cover them. Clothing protection is unported and hunting explicitly uses the unprotected branch. World-contact damage, construction/workstations, original story/escape, outfits, weight, tides/weather and offline sleep remain deferred. HUNTING.md records grouping, PRNG/viewport/timer, cancellation and tenth-shot result adaptations.

## Seventh milestone: deep-water rod fishing (historical)

Date: 2026-10-31. **56 tests passed**, including eight rod-fishing tests. TypeScript and production build passed; `git diff --check` passed. Original map/sheet integrity tests remain green. Rod JSON/TypeScript recovery regenerated byte-for-byte. The labelled browser fixtures are excluded from production dist. Final classic entry is **index-DFk2dU3u.js**, 1,580.07 kB / 444.37 kB gzip.

### Automated coverage added

- Source tool/bait/terrain IDs, all four species/frame arrays and integer hitboxes; bytecode confirms the rod control dispatcher.
- Original deep-water access from reachable shore (8,150), without making terrain 5 walkable or modifying map bytes.
- Exact initial float/hook/fish coordinates, five-slot horizontal history, idle-sway timing and checked-before-step overshoot.
- Three thousand deterministic wandering updates, bounds, immutability, pause/invalid delta and capped catch-up.
- Strict hook edges and fish-before-fire/control update order; missed setting, side escape, bottom escape and strict reel-above boundary.
- Genuine movement/fire/reeling wins for all four species; exact fish rewards, one worm consumed and rod retained.
- Equipment/range checks, mutual minigame exclusion, paused actions, world/inventory/rest/save locks, free cancellation, one-time settlement and a fish awarded before fatal completion cost.
- Existing net completion tests still pass after extracting the shared source strength/energy ordering into FishingCompletion.ts.

### Browser observations

QA used a separate **4188** origin. Earlier expeditions and saved progress were preserved.

- Dev-only `tests/browser/rod-fishing.html` visibly labels its preparation action: Fishing rod×1, Worms×2, needs paused. It uses the actual App, original island/assets, scenes and store. It does not imply a manually played full crafting chain and is not shipped in the production bundle.
- The fixture entered deep-water rod fishing at the original shoreline. Original Brown fish, float/bait animation, segmented white line, trailing hook, idle sway and up/side movement rendered. Settings paused the attempt; save/load/rest were disabled. A missed hook produced original loss text, consumed one worm, retained the rod and added Strength 1.
- The production build's fresh Begin started with no equipment. Down movement reached (8,150); E opened Deep water with Fishing rod 0 / Worms 0, original no-worm warning and disabled Requires Fishing rod action.
- Loaded the explicitly prepared QA save through normal IndexedDB UI, entered rod fishing and exercised both lateral directions plus upward steering. Checks followed the visible direction guide; hook-setting missed and produced the expected one-worm loss. These browser runs **do not claim a manually completed catch**. Control-only successful catches and all four rewards are verified by automated simulation/store tests.
- Enter on the focused result button returned to the same (8,150) world position. Settings showed Strength 1; the completed attempt saved successfully. The browser run used exploration mode; −5 energy and fatal completion are covered automatically.
- Dev hot reload during generated-rule edits/reinitialization produced historical Vite reload errors; the final production entry loaded and ran. No production error is inferred from those earlier dev-only log entries. Final filtered production logs and offline results are recorded below.
- Final filtered production log check returned no captured errors/warnings containing production asset paths. Exactly one canvas was observed after return/reload.
- Stopped the owned preview server, cold-reloaded **index-DFk2dU3u.js** from cache and chose Continue. Restored **(8,150), clock 08:02, Fishing rod×1, Worms×1 and Strength 1**. Re-entered the rod scene and observed original rendering while the server remained stopped. The final shortened title fits beside the field at the normal desktop width.
- Screenshots: `evidence/rod-fishing-miss.jpg`, `evidence/rod-fishing-requirements.jpg`, `evidence/rod-fishing-result.jpg`, `evidence/rod-fishing-production.jpg`, `evidence/rod-fishing-offline-restored.jpg`.
- Cached production QA tab retained. Both owned dev and preview processes are stopped. No viewport override was applied. Run `npm run dev` for development or `npm run preview -- --port 4188` after building to serve this origin again. Earlier user expeditions were not overwritten.

### Remaining limits

Original emulator equivalence, comprehensive browser-family QA and a manual successful rod catch remain unverified. Hunting/ammunition, animal damage, clothing protections, construction/workstation placement, original story/boat escape, weight, tide/weather and offline sleep remain deferred. ROD_FISHING.md separates source rules from reference resolution, PRNG, transient counter reset, input/presentation and cancellation adaptations.

## Sixth milestone: shallow-water net fishing (historical)

Date: 2026-10-31. **48 tests passed**, including seven net-fishing tests. TypeScript and production build passed. Final classic production entry: **index-BHQiH8Fc.js**, 1,564.64 kB / 440.98 kB gzip. `git diff --check` passed. The fishing JSON/TypeScript exports regenerate byte-for-byte; original map and copied sprite integrity checks remain green. The browser fixture is absent from production dist.

### Automated coverage

- Source-verified tool, bait, terrain and reward IDs, all original fish/net frame references and the source item-60/Rug reward quirk.
- Reachable terrain-4 interaction from the starting shore; forged indices, wrong terrain and remote entry rejected.
- One thousand deterministic schools retain source spawning/speed ranges without mutating their input.
- Exact fish-before-net/capture ordering, strict containment, caught-fish descent and the >2000 ms haul.
- A real four-cast win with **12 fish**, five-miss loss, bounded input, exact inventory rewards including Rug, and one-time/fatal settlement with energy/strength effects.
- Shared world/minigame action locks, paused updates, equipment/bait gates and blocked in-progress saving. Existing save structure already persists all new completion effects; no version bump is needed.

### Browser observations

Existing expeditions on ports 4180/4181/4183/4184/4185 were left intact. A separate **4187** origin was used for QA.

- Dev-only `tests/browser/net-fishing.html` explicitly labels its setup button and grants Net×1/Potato×2 for testing. Actual App, world, store validation and scenes are used. The manual crafting chain was not played end to end; original recipe transactions remain tested separately.
- The fixture displayed original moving fish, directional frames, central lane and descending net. A five-cast round caught **3 fish**, lost, consumed one potato and retained Net. Enter on the focused result button returned to the world. Exactly one canvas and no captured errors/warnings were observed during that initial playthrough.
- A later dev hot reload reset the fixture and produced React duplicate-root / transient WebGL framebuffer resize logs. The fixture now unmounts its root on HMR disposal; source typecheck passes. This fixture-only HMR fix was not manually retested. The earlier intermittent embedded-browser WebGL resize behavior remains unverified. Neither error appeared in the final production entry's playthrough/cold reload.
- Saved a fresh, explicitly prepared fixture via the normal IndexedDB UI, then stopped Vite dev and served the production build on the same QA origin.
- Before loading that QA save, chose Begin, moved right from (8,148) to **(12,148)**, and pressed E. Shallow-water dialogue showed **Net 0 / Potato 0**, original no-bait text and a disabled Requires Net button. Production does not grant fixture equipment by default.
- Loaded the prepared save through settings, inspected the same source terrain, and entered net fishing. Space cast and held-key suppression worked. Completed another production round: **3/10 fish after 5/5 casts**. Original loss text, one-potato cost, retained net and **Strength 1** appeared. This browser run used exploration mode; survival-mode −10 energy and fatal cost ordering are tested automatically.
- Saved the completed round, stopped the preview process, cold-reloaded the cached production build and chose Continue. Restored **(12,148), clock 08:01, Net×1, Potato×1 and Strength 1**. Exactly one canvas remained. Historic captured log entries were from the dev fixture; no entries referenced the final production script.
- Screenshots: `evidence/net-fishing-fixture.jpg`, `evidence/net-fishing-requirements.jpg`, `evidence/net-fishing-production.jpg`, `evidence/net-fishing-result.jpg`, `evidence/net-fishing-offline-restored.jpg`.
- Final screenshot review found the scaled field overlapping the left instructions at the normal desktop width. Net display scaling now reserves space for the side instructions/controls; original virtual coordinates and hit regions remain unchanged. TypeScript/production build passed after this presentation fix. Cold-loaded that final entry with the preview stopped, restored the save, re-entered fishing offline and visually confirmed clear instructions alongside the scaled field. Left the unfinished verification round without changing saved inventory.
- Cached production QA tab retained; owned dev/preview processes stopped. Restart with `npm run dev`, or `npm run preview -- --port 4187` after building. No viewport override was applied during this milestone.

### Remaining limitations

Browser wins/reward random branches were not exhaustively played; the real simulated win and all three reward IDs are checked automatically. Rod fishing, hunting, animal damage, clothing protection, construction/workstation placement, story triggers and boat escape remain deferred. Original emulator and other browser-family comparisons remain outstanding. See NET_FISHING.md for viewport, random stream, pause/cancellation, interstitial and exhausted-counter adaptations.

## Fifth milestone: recovered coconut throwing (historical)

Date: 2026-10-31. **41 automated tests passed**, including nine coconut checks. TypeScript and production build passed. Final classic entry is **index-eyP03t3g.js**, 1,551.72 kB / 437.86 kB gzip. Coconut JSON and TypeScript exports regenerated byte-for-byte; original raw-map and copied-sprite integrity checks remain green. `git diff --check` passed.

### Automated coverage added

- Original packed leaf/coconut/stone regions, source coefficient tables (including the initializer CFR omitted), aim controls and exact integer trajectory reference points.
- Shared palm appearance/interaction mapping and a walkable route from the starting component to the palm at (28,160), without changing source map bytes.
- Two thousand generated layouts, unique anchors and a guard against the original impossible uniqueness-rejection case.
- Separate-shot double hits, eleven-second expiry, falling, a real six-throw win and ten-miss loss.
- One-coconut reward, distinct completed-round stone wear and original success/failure reset asymmetry.
- Start restrictions, paused simulation, locked world actions, backward-compatible saves, one-time reward settlement and a reward followed by fatal energy cost.

### Production browser observations

Isolated QA used `http://127.0.0.1:4185/`; existing expeditions on other ports were preserved.

- Collected an original Stone, walked the starting shore to (26,160), and inspected the displayed coconut palm at (28,160). Original description, stone requirement and challenge rules appeared.
- Original palm leaf clusters, coconut and stone frames rendered in a dedicated Phaser scene within the existing game canvas. Sliders updated aim/power; input was disabled during a shot; controls reset to 70/40 afterward. Settings paused play and disabled saving/loading during an unfinished challenge.
- Completed the challenge through UI controls: angle/power pairs 40/84 twice, 70/62 twice, 55/72 twice. Readable first-hit feedback showed 2, 2 and 1 shaking targets. The round reached **5/5 in 6/10 throws**, original victory text appeared, satchel total rose from one to two, and completion wear became **1/6**. This successful round used exploration mode; active survival energy effects are covered by automated tests.
- Earlier long automation calls exceeded the hit window and one browser-control call timed out. A fresh checkpoint run with prompt follow-up throws won successfully. No hidden game-state injection or debug inventory was used.
- Returned to the same (26,160) world position, opened settings, and saved successfully. Completed rewards are persistent; unfinished challenge state is intentionally transient and leaving it grants no reward.
- Stopped the owned preview process, reloaded the final production build from cache and chose Continue. Position **(26,160)**, clock **08:10**, **Coconut×1** and **Stone×1** restored. Played a second complete six-throw win while the server remained stopped; completion wear advanced to **2/6**, confirming the first completion counter restored as well. Exploration-mode result text correctly says “Survival paused”.
- Exactly one canvas remained after scene changes and reload. A transient WebGL “Incomplete Attachment” resize error was captured during an earlier build reload; it did not recur in the final entry's cold reload/playthrough. Its precise embedded-browser resize trigger remains unverified.
- Screenshots: `evidence/coconut-palm.jpg`, `evidence/coconut-minigame.jpg`, `evidence/coconut-victory.jpg`, `evidence/coconut-offline-restored.jpg`. Preview process is stopped; cached QA tab is retained. Run `npm run dev` for development or `npm run preview -- --port 4185` after building to serve that origin again.

### Remaining limitations

Hunting, rod/net fishing, animal damage, clothing protections, construction placement, original story triggers and boat escape remain deferred. Palm placement hashing, browser reference resolution/randomness, cancellation and source-loop safety fixes are documented in COCONUT_CHALLENGE.md. No Java ME emulator or comprehensive cross-browser comparison is claimed.

## Fourth milestone: survival, food and short rest (historical)

Date: 2026-10-30. **32 automated tests passed** (11 new survival checks). Final TypeScript check and production build passed; `git diff --check` passed. `npm run decode` regenerated all exports successfully, and original layer/sprite integrity checks remain green. Final classic production entry is **index-CwmaxgIA.js**, approximately 1.534 MB / 433 KB gzip.

### Automated coverage added

- All 120 typed effect records match original CSV rows 3–7; field names trace through original UI labels and bytecode.
- Exact 60-second boundaries, partial-minute carry, 59→0 exception, pause/invalid-delta behavior and first depleted reserve.
- Ten-second rest and capped +30 energy, with minute-boundary exhaustion taking precedence over recovery.
- Atomic food consumption, absence/tool/rest/death guards, original sentinel handling, two energy applications for positive-tool food, reusable equipment and six-use stone wear.
- Poisonous red mushroom effects, successful pickup/creation costs, exploration mode and explicit recovery without inventory changes.
- Version-1 legacy migration, partial minute/rest/tool-count save roundtrip, malformed saved states, fatal crafting closing its panel, and refusal to overwrite an earlier save after collapse.

### Production browser observations

Used isolated `http://127.0.0.1:4184/`; existing expeditions at other ports were not reloaded, saved over or reset. No publication/deployment occurred.

1. Began a new survival expedition. Keyboard movement reached tile (22,148); E examined the original Stone. Gathering retained Stone×1 and changed energy **100 → 97**, matching the source activity cost.
2. Reached the Coconut at original cell (27,140), inspecting from (26,141). Gathered it and opened the satchel. Its original observation, **Hunger +3, Thirst +25, Energy +16**, required Stone, and **one stone per six uses (0/6)** were displayed. Screenshot: `evidence/survival-food.jpg`.
3. Ate the coconut: its quantity disappeared, energy **97 → 100** (capped), Stone×1 remained, use count displayed **1/6**, and the Eat button became disabled. Browser QA found and fixed a label which had called the tool missing when only the food was depleted. The final label checks the tool's own quantity and says when no food remains.
4. At presentation time **08:01**, reserves were **90 hunger / 86 thirst / 96 energy**, matching the original minute drain after eating. At **08:02**, hunger/thirst reached **80 / 72**. No timing override, debug inventory or browser-state injection was used.
5. Rest showed a countdown, disabled movement/rest re-entry, rendered Robinson's source BS9 resting pose, and restored energy **96 → 100** on completion. A second rest was captured while active: `evidence/survival-rest.jpg`. Exact 30-point recovery, event ordering and cancellation guards are covered by automated tests; browser recovery was capped by the current near-full energy.
6. Settings paused the expedition and displayed strength **1**, morale **0**, source depletion/rest values and explicit remaining limits. Exploration mode switched reserves to 100 with NEEDS PAUSED; re-enabling restored the SURVIVAL label. The prior save was not overwritten by this mode test.
7. Saved at **(26,141), 08:02, hunger 80, thirst 72, energy 100, Stone×1**, after coconut consumption. Updated to the final cached production entry and observed exactly one canvas.
8. Stopped the preview server, reloaded the page entirely from cache, and chose Continue a saved story. It restored **(26,141), 08:02, hunger 80, thirst 72, energy 100, Stone×1**, exploration/inspection progress and survival enabled. Screenshot: `evidence/survival-offline-restored.jpg`. The tool-use count and partial rest serialization are verified by model/save tests; UI confirmation of that count after this reload is not claimed.
9. Opened the **20-recipe workbench offline**. Recovered energy costs and reusable-tool sections rendered correctly. No captured console errors/warnings during final QA. A short embedded-browser load inspection timed out once; a fresh state showed the final bundle loaded successfully.

The current preview is left paused so reserves do not drain while the user reads the result. Temporary viewport override was reset; owned preview process is stopped. To serve it again, use `npm run preview -- --port 4184` after building. Production reload is verified in the in-app browser; other browser families remain to test.

### Limits

Poison/exhaustion loss transactions, focus-flow guards and no-overwrite behavior are checked in automated tests; a complete fatal browser playthrough was not performed. Animal damage, clothing protection, weight, direct world consumption, offline sleep, original device-clock/save behavior, weather/tides and story progression remain incomplete. Browser pause, clock epoch, loss recovery and legacy migration are documented adaptations in SURVIVAL.md. No Java ME emulator comparison is claimed.

## Third milestone: recovered wildlife (historical)

Date: 2026-10-30. **21 tests passed**, including eight new wildlife checks. TypeScript/production build passed. The new wildlife export was regenerated and compared byte-for-byte. Original map and copied sprite integrity checks still pass. `git diff --check` passed.

Automated wildlife coverage:

1. All 16 profiles resolve to original constructors/frame values and valid D0 regions; source start map contains 326 managed animals; loop target is 11 FPS / integer 90 ms.
2. Prey flees, predators pursue, and passive animals keep resting when Robinson approaches.
3. Bird takeoff releases ground occupancy; flight uses original frames; blocked cells and Robinson prevent landing.
4. Moving occupancy vacates old cells, blocks current cells (including formerly collected resource cells), preserves static resources and updates inspection.
5. Sharks cannot leave terrain 5/6; other families retain their distinct source terrain restrictions, including alligator and predator differences.
6. Fixed ticks/view gating and zero-delta pause; 1500 whole-population updates stay within world bounds without double-booked ground cells or source-map mutation.
7. Snapshots restore positions, animation, behavior, timers and seeds, then produce identical continuation for another 100 ticks.
8. Earlier saves remain structurally valid; malformed values, duplicate stable IDs and incompatible species/state combinations are rejected or safely reset before scene restoration.

### Production browser observations

Used a separate origin, `http://127.0.0.1:4183/`, leaving existing expeditions at ports 4180/4181 untouched. No public deployment or remote-site modification occurred.

- Loaded original world and original Robinson/animal frames. Seagull ground motion, ground shadow, flight wing poses, movement over water and flight above vegetation were visible.
- Arrow Right moved Robinson from tile (8,148) to (15,148). D advanced to (19,148), then (22,148), triggering nearby seagull flight. These observations cover one bird species in-browser; other family responses are covered by the pure simulation tests, not claimed manually played across the whole island.
- E opened the original Stone description; Gather added Stone×1 to inventory and removed its runtime obstacle. Mouse input subsequently moved along a generated path to (19,146).
- Browser testing found React menu/zoom clicks issuing Phaser movement commands through window mouse events. The world handler now accepts canvas-target events only. Repeating Begin and Zoom out left Robinson at the original (8,148) position; deliberate canvas clicks and keyboard movement still worked.
- Panels paused wildlife simulation. Saved at (19,146), with one stone, explored cells, examination progress and elapsed time 09:18 on the presentation clock. Save confirmed successfully.
- Final production entry is **index-C0QT_FH9.js**, approximately 1.52 MB / 430 KB gzip. Exactly one canvas was observed. No captured console errors/warnings during final QA.
- After the final build's “Island assets cached” indicator, stopped the preview process, reloaded the page, and chose Continue a saved story. The game restored **(19,146), Stone×1, examination/exploration progress and clock 09:18**, with original wildlife rendering and moving while the server remained stopped. Exact per-animal continuation is separately established by the automated snapshot test; UI observation alone is not a byte-level snapshot comparison.
- Screenshots: `evidence/wildlife-milestone.jpg`, `evidence/wildlife-flight.jpg`, `evidence/wildlife-offline-restored.jpg`. The updated cached preview tab remains available; temporary desktop viewport override was reset. Preview process is stopped. Run `npm run preview -- --port 4183` after building to serve that origin again, or `npm run dev` for development.

### Remaining limitations

Animal damage, hunting/fishing, food drops, reserve depletion, outfits, construction, tide scheduling and original story/escape progression remain deferred. No Java ME emulator or comprehensive cross-browser comparison was performed. Offscreen activation, PRNG, fixed-step scheduling and continuous-coordinate adaptations are detailed in WILDLIFE.md. This milestone continues to use the original pixel art; it is not a completed hand-painted remaster.

## Second milestone: recovered item rules and offline reload (historical)

Automated checks: **13 passed**. TypeScript and production build pass. The six new checks cover typed CSV requirements, tool-gated collection, specialized animal/structure actions, reusable crafting tools, unique equipment and recipe scope, and forged/remote/duplicate pickup rejection.

Production now emits a self-contained classic IIFE script, including styles, rather than a split ES module graph. Preliminary embedded-browser QA successfully reloaded the cached game with the preview server stopped, rendered the island, and moved Robinson from tile (8,148) to (11,148). Final UI/save/reload results are recorded in the second-milestone browser evidence below.

## First playable milestone (historical)

Date: 2026-10-30. Local development and production builds; personal assets only. No public deployment.

## Automated checks

`npm test`: **7 passed**, covering:

1. All five JSON map layers reconstruct the exact original JAR bytes and SHA-256; copied raw .byt files also match.
2. Invalid map dimensions, lengths and byte values are rejected; original coordinate indexing is preserved.
3. Every one of 778 atlas regions fits its source sheet; all 15 copied bank PNGs are byte-identical; composite sprite references resolve.
4. Zero-based item recipe IDs and one-based object map IDs match recovered examples.
5. Original terrain/rock/decor/object walking rules, boundaries and runtime removal without modifying original maps.
6. A path through the actual island grid stays on passable cardinal neighbors and reaches its destination.
7. Crafting consumes exact materials atomically, refuses missing materials/unsupported construction, and duplicate pickup of one world cell yields only once.

`npm run typecheck` and `npm run build`: passed during implementation. Final checks were repeated after source formatting and the last save-position fix. Build has separate React/application and Phaser bundles; Phaser is approximately 1.21 MB uncompressed / 332 KB gzip. Assets and dependencies are local; no CDN, web fonts or backend calls are required.

## Browser checks performed

Tested through the in-app browser's UI at desktop widths (1280 and 1440) and its initial narrow panel width (~570). This is not a claim of comprehensive cross-browser/mobile QA.

- Original world/vegetation displayed and original Robinson sprite visible.
- Arrow right moved from original tile (8,148) along the shore; WASD changed facing and advanced world coordinates in the production build.
- Click-to-walk crossed the original shoreline to tile (22,148); camera followed onto the main beach.
- E opened Stone examination at original map location (23,147), with recovered description: “Just a flat stone. This might come in handy.”
- Gather added Stone×1 to the satchel; the item detail displayed the original sprite/description. The world object's runtime removal persisted.
- Save produced confirmation; a full page reload followed by Continue restored position, inventory and journal progress.
- Craft panel showed exact Axe/Rope ingredients and disabled buttons for missing materials. Material consumption is checked by the automated transaction test; full manual world collection/crafting progression was not exhaustively played.
- Journal showed original opening narrative and clearly identified the new exploration checklist.
- Map showed the complete original island overview and player marker.
- Pause/settings controls displayed; Escape returned to play. Zoom controls were present; full-screen and audible ambience were not comprehensively verified.
- After all local servers were stopped, the already-loaded production game still accepted arrow input and advanced from tile (8,148) to (10,148). This verifies continued loaded-session play, not cold offline reload.
- Exactly one canvas was observed with React StrictMode enabled. No captured console errors/warnings during normal play.

## First milestone offline failure (subsequently resolved)

A versioned production service worker precaches the shell, generated bundles, original sprites/maps and data. Readiness now waits for worker activation and checks cached entry assets. Cache reads ignore `Vary` for these same-origin static files; local response inspection confirmed Vite emits `Vary: Origin` with different headers for module requests.

**At the first milestone, reload after stopping the local server produced an HTML shell with an empty React root in the embedded browser.** This was subsequently resolved by the self-contained classic-script production build described above. The loaded game has no network gameplay dependency, and the second milestone now verifies cold offline reload as well. Native Chrome verification could not be completed because computer automation timed out. The exact underlying module-replay cause was not established; the single-script packaging is a verified workaround, not a claim about a browser defect.

The footer therefore says **Island assets cached**, not that offline reload is guaranteed. Development mode does not install a worker and needs its server. Production can reload offline after its first complete cached load. Browser families other than the in-app browser remain unverified.

## Other limitations

The first two milestones did not implement the full original survival timing, animal AI, clothing composition, fishing/hunting/throwing minigames, changing tide timing, construction placement, story triggers or boat escape. Needs remain paused. Hand-painted replacement artwork is deferred in favor of exact original pixel art. No original MIDlet emulator comparison has been performed. See REVERSE_ENGINEERING.md and GAME_DESIGN.md for verified facts, adaptations and the implementation sequence.

First-milestone visual evidence: `evidence/playable-milestone.jpg` (1440 px desktop, full page). Temporary preview servers were stopped after QA; restart with `npm run dev` to open/reload the game.


## Second milestone browser evidence

- Final production entry `index-Blmz7Y18.js` is a single classic script with inline styles. Build-time parsing with Node's Script parser rejects any accidental top-level module import/export regression.
- Workbench UI displayed **All recipes 20**, separated “MATERIALS · USED UP” and “TOOLS · KEPT”, and disabled unavailable crafts. “Ready to make 0” showed an honest empty state.
- Walked through the original island to the vine at the jungle edge near tile (38,149). Inspection from (37,150) showed **Requires Knife**, explained that the tool is kept, and offered **no Gather vine button** with an empty inventory. Evidence: `evidence/tool-requirement.jpg`.
- Positive tool-gated pickup, ingredient consumption, retained crafting tools and equipment uniqueness are also covered by automated tests; no manual bypass or debug inventory injection was used.

- The live expedition subsequently showed **Using Knife** and an enabled **Gather vine** action, confirming the positive side of the tool rule. Its state was preserved while the final cold-reload test used a separate origin.
- Final isolated QA used `http://localhost:4181` (separate saves from the active `127.0.0.1` expedition). Moved Robinson to (11,148), saved, stopped the preview process, reloaded the complete app and original island from cache, and continued the saved story. Position (11,148) was restored while the server remained stopped.
- Opened the **20-recipe workbench after offline restoration**; expected reusable-tool sections and disabled missing-material actions were present, with no captured errors/warnings.
- Current production bundle: approximately 1.51 MB / 427 KB gzip, including React, Phaser and styles. No module graph or external stylesheet request is required to start the cached app.
- Visual evidence: `evidence/workbench-milestone.jpg` and `evidence/tool-requirement.jpg`. The active expedition's save was not overwritten by isolated QA.
- All 13 automated tests and the final TypeScript/production build passed. Preview processes were stopped after testing. Offline support is verified in the in-app browser; other browser families remain to test.
