import { readFileSync, writeFileSync } from "node:fs";
import { ExpeditionRunner } from "./lib/expeditionRunner";
import { runSextantMilestone } from "./lib/orderedMilestone";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const runner = new ExpeditionRunner(
  read("public/assets/maps/island.json"),
  read("public/assets/objects/objects.json"),
  read("public/assets/animals/wildlife.json"),
);
let error: string | null = null;
try {
  runSextantMilestone(runner, () => console.log(runner.log.at(-1)));
} catch (e) {
  error = e instanceof Error ? (e.stack ?? String(e)) : String(e);
  console.error(error);
}
writeFileSync(
  "evidence/ordered-expedition-audit.json",
  JSON.stringify(
    {
      scope:
        "Headless ordered production-system integration, 50ms movement steps at64px/s,500x300px wildlife view, no renderer/frame timing claim. No inventory or removed object grants; active survival and original wildlife.",
      error,
      movedPixels: runner.movedPixels,
      ticks: runner.ticks,
      log: runner.log,
      checkpoint: runner.checkpoint(),
    },
    null,
    2,
  ) + "\n",
);
if (error) process.exitCode = 1;
