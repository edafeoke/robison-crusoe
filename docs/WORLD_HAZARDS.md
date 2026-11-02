# Recovered world-contact animal hazards

World hazards now run separately from the `data.s` bow-hunting minigame. All original map bytes, animal placements and sprite banks remain intact. `tools/recover_hazards.py` extracts and checks rules from the retained CFR sources into `evidence/world-hazard-rules.json` and typed `src/game/data/worldHazardRules.ts`. This recovery does not infer damage from hunting profiles.

## Verified source behavior

`data.g` constructs map IDs 43/44/49/50 using `data.d`, and map ID 56 using `data.i`. Object/item IDs are one lower than map IDs.

| Map ID | Animal | Energy loss | Worn slot | Protected loss |
| --- | --- | --- | --- | --- |
| 43 | Alligator | 50 | Pants | 25 |
| 44 | Jaguar | 50 | Shirt | 25 |
| 49 | Green snake | 10 | Shoes | 5 |
| 50 | Boa | 20 | Hat | 10 |
| 56 | Shark | Sets energy to zero | None | No protection |

`d.a()` checks proximity **after moving**. Both integer tile differences must be at most one, including diagonals. This is not sprite-overlap collision or a circular pixel radius. While not sailing (`!p.T`), each predator accumulates its own `long c` timer using the elapsed game-loop interval. An attack happens only when that timer is **strictly greater than 1000 ms**, and resets it to zero. Leaving the tile range resets exposure to zero; sailing skips the branch and retains the existing timer. Repeated proximity permits repeated attacks. Resting predators can attack too. The four clothing checks only require a nonempty selected slot, not a particular material or garment quality. Clothes are not consumed or worn down by these branches.

The deducted reserve is `p.c`, independently traced to **Energy**, relative to the common maximum `p.d`. No separate health meter, venom timer, knockback, inventory theft or arbitrary meat drop is introduced. The inspected passive/prey/bird update families `l/q` have no corresponding contact-damage branch.

If Robinson is short-resting (`p.U`), `d` cancels rest **after the attack**, then calls `ad.b(floor(p.q * 30 / 10000))` and clears elapsed rest `q`. This partial recovery is separate from the ordinary completed-rest +30. Multiple animals in one update only interrupt/credit the same rest once.

`i.a()` checks the same one-tile range after shark movement. It immediately sets energy to zero and `p.aO = true`, without a timer, clothing test, sailing exemption or partial-rest credit. Sharks retain their existing water-only movement and cannot enter the player's own tile; adjacency is sufficient. They can pursue a nearby player from water toward a walkable shore. This does not add swimming or boat travel.

`v.c()` uses `aO` to choose source text 95: “You've been killed by a dangerous wild animal!” Browser loss now displays that recovered text for fatal animal contact. The source `aN` animation branch is not assumed to be a damage reaction: this contact code does not set it. No fabricated hit animation is attached to attacks.

## Source quirks and explicit browser settlement

The unprotected `d` branch clamps depleted energy to zero and sets `aO` **before** partial-rest recovery. Its protected helper skips that clamp/loss flag. `ad.b` converts the current percentage to an integer, adds the partial-rest credit, caps at 100 and invokes loss when the result is nonpositive. The broader `ad.f` check compares reserves to **exactly zero**; a negative protected reserve can therefore miss that check when not resting. This is an inconsistent source loss path, not evidence of immunity from clothing.

The browser preserves subtraction, the unprotected pre-credit clamp and interrupted-rest integer arithmetic, then consistently clamps the final result to 0–100 and collapses at zero for either branch. For example, at energy 5 with 1080 ms of rest, a bare Boa attack clamps to 0 then credits 3 (survives at 3); a protected attack computes 5−10+3 and collapses at 0. It does not preserve negative-reserve survival or a stale `aO` flag after recovery. This normalization is an explicit safety adaptation, covered by tests, pending emulator comparison.

## Raft integration correction (2026-11-02)

The raft milestone initially left the source sailing exemption disconnected: the scene omitted the hazard helper’s sailing argument, and predators still retargeted passengers. Both now receive the actual aboard state. Timed exposure is retained without accumulation/reset aboard; existing movement/speed continues, and landing resumes targeting and attacks. Sharks remain immediate hazards. New optional snapshot reaction state preserves that last speed through afloat saves. Full source checks, route audit and verification: PROGRESSION_AUDIT.md.

## Scheduling, UI and persistence

- Contacts run once after **each actually updated wildlife actor**, under the existing view bounds, with no separate wall-clock timer. At the browser's fixed 90 ms wildlife cadence, the first uninterrupted predator hit is on tick 12 (**1080 ms**). Java used measured loop intervals with a 90 ms minimum, so exact real-time parity is not claimed.
- Menus, title screen, minigames, hidden tabs and collapse pause world contacts together with the world. Removed/hunted actors cannot attack. Minigame retaliation remains its own system and is not charged twice.
- The scene flushes pending survival/rest time before settling contact, then keeps the resulting energy through the normal HUD synchronization. Partial-rest credit uses actual active elapsed rest, not a stale HUD snapshot.
- A small warning accompanies a focused dangerous animal; attack notices name the animal and energy cost, and report interrupted-rest credit. These are browser usability additions, not recovered original UI. Another closer object can own the focus prompt; it is not a complete radar or guarantee of warning before every attack.
- Exploration mode keeps reserves full, disables these contact consequences and resets exposure as actors update. This deliberate browser option does not reproduce the original sailing state.
- Optional `WildlifeSnapshot.contactMs` preserves each animal's partial exposure through normal saves. Legacy snapshots start at zero. Save validation rejects noninteger, negative or above-1000 timers; existing version-1 saves stay supported. The original RMS dynamic-timer serialization has not been matched.

## Validation and remaining work

Automated tests cover independent source rules, correct/wrong clothing slots, diagonal/two-tile bounds, strict timing, repeated attacks, view freezing, actual actor removal, save continuation/migration, invalid timers, interrupted rest, source clamp quirks, protected fatal damage, immediate sharks and exploration safety. Browser QA uses a visibly labelled development fixture to move Robinson beside an actual source Boa or onto an original shore near an actual source Shark; it supplies a Hat only when labelled. No animal is relocated and no map layer is altered. See VALIDATION.md for observed results.

Raft travel, tides and the timed escape are now ported separately; a complete survival expedition, remaining special entity behavior, weather, non-sleep offline depletion and emulator fidelity comparison remain outstanding. No exhaustive manual tour of every predator placement or Java ME execution is claimed.
