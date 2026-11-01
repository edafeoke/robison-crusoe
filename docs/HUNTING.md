# Bow hunting recovery

Milestone eight ports the original `data.s` bow-hunting mode. All source JAR/map/sprite files remain intact. World-contact damage, clothing protection and story progression are separate systems and remain deferred.

## Entry, targets and inventory

`data.a.c(int)` dispatches an animal's CSV hunting-tool field through `a.a(byte,int)`. Tool **81 (Bow)** offers action sprite **851977**; selecting it calls `s.b()`, sets `p.m=7` and `p.q=3`. `p` renders `s.a()` and the input dispatcher `v.b()` calls `s.d()`. Hunting scans supplies for **45 (Arrow)**; text 92 is the original no-arrow warning.

The original nearby-animal scan collects at most five species entries. The browser preserves a maximum of five supported **grounded** animals within its established 44-pixel radial interaction, placing the inspected animal first. This adapts the original directional footprint tests; exact original directional grouping is not claimed. Flying wildlife cannot initiate a hunt through ground occupancy.

Browser entry independently verifies current world cell/species, live actor identity, distance, source hunting profile, Bow/Arrow inventory, ready/started state, collapse/rest and other active minigames. The selected animal and nearby group are captured before world simulation pauses. Their original stable IDs accompany the minigame targets; current moving cell numbers are not mistaken for stable identity.

**One Arrow inventory item supplies an attempt of up to ten shots.** Both completed success and failure consume one Arrow, keep Bow, add **1 strength** and apply **−10 energy** from `p.n[81]`/`p.m[81]`. This source behavior is retained; the port does not charge one inventory Arrow per shot. Arrow's crafting energy effect is separate from Bow's hunting completion effect. A win adds one animal item of the hit species, not an invented generic meat drop. Its subsequent food/use-tool requirements remain those in the recovered item data.

## Recovered species and graphics

`tools/recover_hunting.py` extracts all 15 `s.k()` cases: Wild goat (4), Crab (41), Alligator (42), Jaguar (43), Peccary (44), Porcupine (46), Turtle (47), Green snake (48), Boa (49), Green parrot (51), Seagull (52), Pelican (53), Green iguana (61), Toucan (62), Red ibis (63). It writes `evidence/hunting-rules.json` and `src/game/data/huntingRules.ts`; `npm run decode` invokes it.

Every profile retains original left/right/idle region pairs, width/height, axis step and danger flag. All use step 2 except Turtle's step 1. Turtle idle repeats 131080 and iguana idle repeats 131116 exactly as supplied; guessed missing frames are not substituted. Fish/hunting profiles are separate from overworld wildlife sequences.

Shark's item data names Bow, but **there is no `s.k()` shark case** and the original nearby scan skips its species range. Shark hunting is therefore unavailable; no profile is fabricated from its overworld animation.

The constructor's six projectile references are recovered from **javap** push/iastore instructions (CFR mislabels the array in its obfuscated declaration): `[851993,851995,851991,851996,851994,851992]`. Bow display is 851990. Original D0 anchors/mirroring and source angle-dependent offsets are retained. Previous stopped arrows remain visible; a new held arrow appears between shots. The existing original-bank loader supplies BS2 and BS13 without recropping sheets.

## Projectile and collision rules

The browser uses the same explicit **240×320 reference viewport** as the earlier minigames; source dimensions depended on the phone. Target spawn x is width/2 + random %(width/2−24), and y is 40 + random %(height/3−40). At this reference size: **x 120…215, y 40…105**.

Angle is 10…70 in steps of 5; power 40…100 in steps of 2. Both reset to 70/40 after a miss. Arrow arithmetic reuses the verified coefficient arrays on `x` (including the initializer CFR omitted), preserving Java intermediate truncation and the old-x vertical update before the new-x horizontal update.

Let `G=height−50=270`, `radius=ceil(hypot(G,min(width−10,G)))=355`, and coefficient index `trunc((angle+15)/5)−3`. Arrow stop x is `trunc(cos[index]*power*radius/10000)+10`. Flight advances until the prior x has reached that stop value, then the arrow becomes stopped. The reference angle 40/power 84 starts at `(19,270), (28,245), (37,221), (47,200)`.

**Hit testing occurs only for the stopped/apex arrow**, after animal motion and arrow advancement. It does not test every point of the flight path. The arrow rectangle is x…x+8, y−7…y+1; target bounds use strict overlap and Java integer half-width/height. If overlapping animals are hit, the last intersected source-order entry becomes the single rewarded/removed animal. Tests cover this ordering.

Source `s.a()` calls aim/bow, prior-arrow drawing, target motion `g()`, current arrow `i()`, then hit/miss processing `j()`. The port preserves the gameplay update order. Input uses the existing fixed 90 ms minigame steps with large browser deltas capped at 180 ms; wall-clock/device and cross-mode timer equivalence is not claimed.

## Animal behavior during the hunt

Animals initially rest until the first miss. That miss releases them and assigns source signed random vertical/horizontal displacement. Later segments either rest or wander; rest chance denominator is four times shots fired. Source's shared rest timer/duration is retained. Rest durations are 2000…4900 ms. Movement clamps at x 24…width−24, y 45…height/3, with checked step completion/overshoot behavior. Two-pose animation follows the source 600/1200 ms clock through the browser fixed-step approximation.

After ten unsuccessful shots, non-dangerous groups lose immediately. A group containing Alligator, Jaguar, Green snake or Boa enters the source **1500 ms retaliation**. Dangerous animals interpolate from their current positions toward the bow. Unprotected energy losses add for each dangerous group member:

| Animal | Unprotected | Source protected branch |
| --- | --- | --- |
| Alligator | 50 | 25 |
| Jaguar | 50 | 25 |
| Green snake | 10 | 5 |
| Boa | 20 | 10 |

These are **energy** effects, not a new health stat. The browser currently has no worn-clothing state, so it explicitly applies the unprotected branch. This is an implementation assumption, not proof of the original starting outfit. Protected values are documented/exported for the future clothing port. Overworld contact damage remains paused. Exploration mode keeps reserves full, including during hunting; strength and completion inventory still change.

Retaliation is a committed outcome: settings/hidden tabs can pause it, but Leave cannot cancel it to avoid its cost. A hit on shot ten is prioritized as success. Original `j()` can also set retaliation flags on that shot while the success branch is active; those overlapping source result paths are not allowed to double-charge or both win and lose in the browser.

## World removal and saves

A win appends exactly the caught actor's stable original ID to the existing `collected` set. `WildlifeSystem.removeActor` releases its **current** occupied cell and removes it from simulation; `Animal.destroy` removes body, shadow and wake objects. Other actors, resources and original map bytes remain unchanged. Source itself clears a nearby matching species cell through `s.m/n/o/p`; stable identity is a necessary adaptation to the browser's continuous moving actors and avoids removing the wrong same-species neighbor.

The next snapshot omits the removed actor. On restore, the original map plus collected stable IDs construct the remaining population before applying matching snapshots. Old saves remain supported. Existing version-1 inventory/collected/wildlife/survival fields cover the whole change; no new persistent field is needed. Inventory reward/removal is retained even if completion energy is fatal. Terminal state transitions settle once.

In-progress hunting is transient and cannot be saved. World motion, wildlife, inventory mutations, crafting, rest, and other minigames are locked while hunting. Returning restores the same world position and camera inside the existing Phaser instance.

## Explicit adaptations and limits

- React sliders, keyboard/click shooting, persistent instructions, readable counters and immediate result settlement replace device-specific controls and skippable interstitials. The original first-fire warning acknowledgement is not an extra consumed shot.
- Leaving before retaliation abandons an unfinished hunt without reward or completion charge, following the established browser minigame cancellation policy. The save is not overwritten.
- Browser PRNG, fresh per-attempt transient timers, fixed ticks, reference viewport and radial grouping differ from original Java runtime/device scheduling. No emulator equivalence is claimed.
- Clothing protection, overworld contact hazards, construction/workstations, weight, weather/tides, sleep and original quest/escape progression remain incomplete.

## Verification

Eight hunting tests cover all recovered regions/profiles and source quirks; stable layouts/initial rests and golden arrow coordinates; apex-only collision, roaming and ten-shot loss; genuine angle/power wins for all 15 profiles; retaliation timing/costs and tenth-shot success; live world/equipment/action/save guards and fatal one-time settlement; stable actor removal and save restoration; overlapping targets and combined predator damage.

The dev-only `tests/browser/hunting.html` fixture openly prepares Bow×1/Arrow×2 with needs paused at an actual original animal. It labels its deterministic seed for repeatable UI shots and does not ship in the production bundle. It does not imply a manually completed equipment-crafting chain. Browser observations and offline verification limits are recorded in VALIDATION.md.
