import { readFileSync, writeFileSync } from "node:fs";
import { auditAcquisition } from "./lib/acquisitionAudit";
const read = (p: string) => JSON.parse(readFileSync(p, "utf8"));
const report = auditAcquisition(
  read("public/assets/maps/island.json"),
  read("public/assets/objects/objects.json"),
);
writeFileSync(
  "evidence/acquisition-access-audit.json",
  JSON.stringify(report, null, 2) + "\n",
);
console.log(
  JSON.stringify(
    {
      beforeRaft: report.beforeRaft,
      afterRaft: report.afterRaft,
      raft: { ...report.raft, landings: report.raft.landings.length },
      tools: report.tools.map((t) => ({
        name: t.name,
        stage: t.stage,
        pathCells: t.path.length,
      })),
    },
    null,
    2,
  ),
);
