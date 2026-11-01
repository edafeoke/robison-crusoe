# Survival and item-use recovery

This milestone implements the verified three-reserve survival loop, inventory consumption, activity costs and short rest. It retains the original food/tool effects and explicitly separates browser timing/mode choices from Java behavior. It does not implement a separate health meter, combat damage, weight, offline sleep, or the complete adventure.

## Verified fields and evidence

CFR produces duplicate names because the original class files overload fields by descriptor. The retained `evidence/bytecode/data.*.txt` disassembly resolves these identities. In `data.p`, the **float** fields are:

| JVM field | Meaning | Evidence |
| --- | --- | --- |
| `a:F` | Hunger reserve | `aa.g()` labels index 0 with text 70 “Hungry”; `aa.d(StringBuffer)` draws `p.a:F` for that index |
| `b:F` | Thirst reserve | Index 1 / text 71 “Thirsty” / `p.b:F` |
| `c:F` | Energy reserve | Index 2 / text 72 “Energy” / `p.c:F` |
| `d:F` | Shared reserve maximum | `p.f()` initializes it from screen width; all percentage arithmetic divides by this value |
| `e:F` | Strength | Index 3 / text 73 “Strength” |
| `f:F` | Morale | Index 4 / text 74 “Morale” |
| `g:F` | Weight bar | Index 5 / text 75 “Weight”; not ported in this milestone |

`p.f()` initializes hunger/thirst/energy to the maximum. `v`'s new-game/reset path initializes strength/morale/weight to zero. The browser stores normalized percentages, matching the original percentage calculations without tying reserves to the physical width of a phone screen.

## Verified timing and depletion

- `p.b(Graphics)` computes frame elapsed milliseconds and increments its long timers. `v.f()` normally waits for `p.k:J >= 60000`, then calls `aa.d()` to read device time using `Date.getTime()` and derive minute `p.J:J`, hour `p.K:J`, and day.
- `v.f()` also has a debug acceleration branch under `p.D:Z`: minute advancement after more than 4000 ms. This is **not** the normal game speed and is not used by the browser port.
- `ad.f()` captures a minute reference after initialization/load (`p.aL:Z`). If the new minute equals that reference plus one, it subtracts **10% hunger, 14% thirst, 4% energy**, clamping each to zero. Zero in any reserve enters the original loss state. It does not reference a separate health reserve.
- The source checks `newMinute == previousMinute + 1`, using minute-of-hour values. Thus the **59 → 0 transition skips that decrement**. The browser retains this verified exception. A delayed source update which skips several device minutes also does not apply each missed minute; browser scheduling is discussed below.
- `p.F:Z` is the debug immortal branch in `ad.f`, refilling the three reserves when redraw is requested. Browser exploration mode is an explicit usability option, not a claim that this was the ordinary original game mode.

## Verified CSV effect rows

`y.d()` starts its parser switch at 3. The generated typed fields retain original values, including sentinels:

| Zero-based CSV row | Target field | Generated effect |
| --- | --- | --- |
| 3 | `p.j:[I` | Hunger |
| 4 | `p.k:[I` | Thirst |
| 5 | `p.l:[I` | Morale |
| 6 | `p.m:[I` | Energy |
| 7 | `p.n:[I` | Strength |

`tools/decode_original.py` writes these as `effects` on all 120 objects. No source CSV, JAR, original map or PNG is modified.

### Inventory food use (`ad.g` / `ad.h`)

`a.d(int)` offers USE for items with `c:[B` (CSV row 1) equal to zero or a tool present in the supplies/equipment lists. A positive row-1 value is the required original item ID, not a durability count. Browser food actions require an actual inventory quantity and the tool before mutating anything.

With **use-tool zero**, `ad.g` adds hunger only if its effect is not `-1`, thirst only if not `-1`, and energy only if nonzero. The item is removed once. The original `ay` flag prevents repeated removals when `h()` is invoked from multiple effect branches; the browser implements one atomic transaction.

With a **positive use-tool**, the source instead adds hunger and thirst unconditionally (including literal `-1` values), then energy once. An OR condition (`tool != 0 || tool != -1`) leads into the next block, which adds morale/strength and **energy a second time**. This unusual result is present in both CFR and bytecode, not corrected speculatively. In `data.ad.txt`, `g()` writes `p.c:F` at offsets 311 and 523; the condition around 318–338 is also visible. Each application caps at 100. Percentage integers are truncated before item arithmetic, as in the Java casts.

Examples:

| Original item | Tool | Hunger | Thirst | Effective energy |
| --- | --- | --- | --- | --- |
| Mango | None | +3 | +15 | +8 |
| Banana | None | +6 | +3 | +5 |
| Coconut | Stone | +3 | +25 | +16 (8 twice) |
| Pineapple | Knife | +5 | +25 | +16 (8 twice) |
| White mushroom | Fire | +3 | −1 literal in tool branch | +10 (5 twice) |
| Red mushroom | Fire | −1 literal | −1 literal | −100 (−50 twice) |

`ad.b()` handles a fatal item energy effect. The original recovered text includes “That food was poisonous - you're dead!” The browser uses that text for poisonous food loss and distinguishes it from ordinary reserve exhaustion. It warns about a negative food energy effect before the player chooses to eat it.

`ad.h()` increments a stone-use counter and consumes a stone on every sixth coconut opening, then resets the counter. The stone must still be present for every use. Other tool-removal paths search the supplies list; the Knife and Fire are category-1 equipment and remain available. Browser quantities are keyed by item ID, avoiding reliance on the original mutable array-slot indexing. The tool-use counter is saved.

### Pickup and creation (`ad.a` / `ad.l`)

- Successful collection applies energy/morale/strength effects when pickup-tool is not `-1` and use-tool **is** `-1`. A fruit's food benefits therefore wait until consumption; merely taking it does not feed Robinson.
- Successful creation applies its energy/morale/strength effect once. For example, collecting a stone costs 3 energy and adds 1 strength; making an axe costs 10 energy and adds 1 strength. Failed/missing-tool/duplicate actions have no effect.
- Activities may exhaust Robinson after their original successful item transaction. The acquired or made item is retained, but movement stops on loss.
- Direct USE on an object in the world follows a separate `ad.j()` path, with its own duplicate-effect behavior. This milestone uses **Gather → Satchel → Eat**. Direct world consumption is not silently treated as equivalent to inventory use.

## Verified short rest

`ad.c()` sets `p.U:Z`, clears the action state and resets `p.q:J`. `ad.d()` completes after `q > 10000`, clears rest, and calls `b(30)` to restore 30 energy percentage points, capped at 100. Movement stops during rest while world/need updates continue. `ab.a()` draws **BS9 regions 0 and 1**, swapping on a 700 ms timer. These are the port's resting frames, with recovered anchors.

The browser uses a ten-second active countdown, restores energy at its completion, and keeps hunger/thirst ticking. A minute-boundary loss takes precedence over later rest recovery. Cancelling from the browser UI grants no recovery; this explicit cancel control is a usability adaptation. Rest can be paused by opening a panel.

**Sleep/save-and-exit is a different original operation.** It serializes RMS state and exits to a menu; the later `v.c(boolean)` path calculates recovery or depletion from elapsed hours. Neither offline recovery nor offline death is implemented here. Browser Save retains the current state without simulating a night's sleep.

## Browser integration and explicit adaptations

- `SurvivalSystem.ts` is a pure, deterministic model. Phaser `IslandScene` advances it with active elapsed time, while Zustand exposes reserves, inventory use and rest to React.
- New expeditions start with survival enabled and full hunger/thirst/energy, zero strength/morale. Settings can switch to exploration mode. Turning survival off restores the three reserves and cancels loss/rest without removing discoveries or inventory. This mode control is a browser enhancement.
- Earlier saves lacking `survival` migrate to **exploration mode with full reserves**. Their elapsed play seconds seed the new minute clock; the old 3-seconds-per-display-minute presentation is replaced by real-minute timing. Enabling survival is then explicit.
- The browser clock begins at 08:00 and advances by one minute per 60 active seconds. This preserves the recovered rate and minute transitions but does not mirror the phone's absolute device time. Clock/date are still browser presentation, not recovered story triggers.
- Panels, hidden documents, pre-start state and loss pause simulation. Each scene frame contributes at most 250 ms, preventing catch-up damage after browser suspension. Wildlife keeps moving during active rest. This is intentionally different from device wall-clock/offline behavior; no offline depletion is claimed.
- Food effects and tool requirements appear beside the original description. Exploration mode keeps reserves full while still consuming supplies, and the UI says so.
- On loss, the browser stops world advancement, focuses a loss dialog, offers loading the saved expedition or continuing in exploration mode, and **refuses to overwrite the earlier save with the collapsed state**. The exploration recovery option is new; original story loss/restart flow is not claimed complete.
- IndexedDB's optional `survival` member stores all five implemented percentage attributes, mode, total minutes, partial minute milliseconds, remaining rest milliseconds, stone-use count, and loss cause. Old version-1 saves remain accepted. Validation rejects nonfinite/out-of-range values and malformed states.

## Still deferred

Animal contact damage remains paused until clothing protections and loss interactions are integrated. Weight's nutrition counter (`v.a(int)`, a bar increment after 50 nutrition units), clothing, direct world consumption, shelter sleep benefits, offline saved-time arithmetic, workstation placement restrictions, weather/tide scheduling and original save compatibility need further implementation/comparison. All are distinct from the now-working reserve/food/rest loop. No Java ME emulator fidelity comparison is claimed.

Automated and browser observations are recorded in VALIDATION.md. The tests cover exact CSV values, minute boundaries and rollover, rest/death ordering, food/tool consumption, poison, activity effects, save migration/validation, and loss recovery without inventory or save mutation.

## Hunting energy

Hunting completion now applies Bow strength/energy effects. Source retaliation after ten misses drains energy for dangerous species; clothing protection is not implemented, so the documented unprotected branch applies. World-contact damage remains paused. HUNTING.md distinguishes verified source values from this protection assumption.
