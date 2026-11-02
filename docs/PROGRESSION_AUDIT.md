# Acquisition routes and raft-contact integration

Audited 2026-11-02 against the retained original JAR, CFR sources and JVM disassembly. This milestone fixes an integration error found while connecting previously tested systems. It does **not** certify a complete survival playthrough.

## Recovered raft/predator distinction

`ad.u()` sets `p.T:Z` on boarding; `ad.v()` clears it on landing. In `d.a()` that same flag guards two separate branches: predator retargeting within two tiles, and timed contact damage within one tile. JVM disassembly confirms `getfield data/p.T:Z` at offsets4 and2290. It is the swamp-raft aboard flag, not the separate Pirate ship departure state.

While aboard, predators still wander, rest, animate and finish existing movement. They do not choose a new pursuit target or advance/reset their contact exposure. Their previous movement/speed is retained. On landing, retargeting and contact accumulation resume. A partially accumulated timer can therefore cause an early hit after landing. `i.a()` contains no equivalent flag check: sharks still cause immediate Energy exhaustion. Prey/bird reactions also have no such gate.

The browser hazard helper already represented the timed exemption, but `IslandScene` omitted its sailing argument. Wildlife retargeting also omitted that state. Both now receive the actual `raft.phase === "aboard"`. The mode argument is required by TypeScript, including at all test callers. Ship departure is not mistaken for raft boarding. No damage amount, source placement, world clock, recipe or map layer changes.

Afloat saves now retain the last wildlife reaction so an existing pursuit speed continues after loading. Optional `WildlifeSnapshot.reaction` accepts only null/flee/chase; older saves default to null and refresh normally after landing. Partial `contactMs` remains independently preserved. This is browser state continuation, not original RMS compatibility. Source assertions were added to `tools/recover_hazards.py`; generated damage rules remain unchanged.

## Stronger spatial acquisition audit

Run `node --import tsx tools/audit_acquisition_routes.ts` to regenerate `evidence/acquisition-access-audit.json`. The pure implementation is `tools/lib/acquisitionAudit.ts`.

Earlier tide/raft diagnostics ignored all Objects, which could conceal a circular tool dependency. The new model starts at the original (8,148) with **empty inventory**, fixed low tide, and the actual original Objects/Decor/Rocks. It repeatedly finds reachable foot cells and gathers only static objects whose actual pickup rule/tool is satisfied within44px. Each cleared cell corresponds to one documented pickup. Raw mobile animals remain blockers at their source cells; none are silently erased or hunted. The Axe is crafted from its exact acquired materials. Construction dependencies use the recovered materials, kept tools and camp prerequisites.

Before rafting, this closure reaches **17,546 foot cells** and obtains these loose tools: Saw, Knife, Machete, Scissors, Plane, Needle, Gunpowder, Anchor, Map, Hammer and Compass. Tool witnesses retain cardinal cell paths valid at their acquisition step. The order reflects the model’s scan, not a recommended player itinerary. Saw can be gathered before Knife at low tide; no invented Knife requirement is added. Giant log requires Saw, whereas ordinary Log requires Axe.

The raft ingredients require **Tent first** because Rope and Plank require that tier. The model consumes the one raw Sail plus Branch×3 using the kept Knife for Tent, then makes Stick×6, Rope×5 and Plank×5 from actual stock. It repairs the original raft with those supplies, Nail×5, Tree root×3, Moss×4 and retained Saw/Hammer. No item is granted. Initial boarding is validated at **(94,93)** into the source raft **(94,94)**.

Retaining raw mobile blockers yields **2,250 connected swamp cells**. The actual landing-priority predicate finds **467 water-to-shore edges**. Combining those reachable land components and further legal pickups expands access to **18,798 foot cells**, including interaction reach of the wreck at(152,30) and collection of the Sextant at(165,76). Both are outside the pre-raft foot closure. The lower cell count relative to the previous optimistic raft audit is expected: this audit retains actual object blockers.

The report records ordered pickup/crafting/repair transactions, tool path witnesses, boarding and landing edges, and remaining inventory. Tests independently replay the item/tool/tier transactions and verify tool paths, cardinal adjacency, source immutability, boarding and priority landing. A negative case removes Knife from a test-only map and confirms that Vine/Log gathering, Tent and raft do not receive free prerequisites.

## Limits of that result

This is a **spatial/tool feasibility model**, not a timed expedition. It fixes the map to low tide, aggregates connected components and does not order a complete water/land itinerary, charge travel/activity survival, simulate pursuit, consume food, wait through changing tides, renew resources or construct the final ship. In particular, the one raw Sail is spent on Tent; Large sail still needs four renewed Sails. The earlier resource-history tests and material audit cover that separate quantity issue. The fixed-phase model does not prove its many actions fit inside one low-tide band.

Raw animals are static blockers in this diagnostic; actual gameplay moves them and applies view-gated hazards. Leaving and returning via a moved raft, resource renewal while traveling, survival planning and final ship ingredients still need a combined start-to-escape test. The browser UI remains the existing exploration journal rather than presenting these model paths as recovered quests.

## Validation

164 automated tests pass, including five new raft/predator continuation tests and two acquisition-audit tests. TypeScript and production build pass. Real-browser observations, prepared-fixture preconditions and offline restoration are recorded in VALIDATION.md. The fixture explicitly supplies a repaired raft and repositions Robinson; it does not claim that browser QA acquired its ingredients or traversed the acquisition model.
