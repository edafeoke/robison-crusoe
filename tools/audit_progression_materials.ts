/** Material-only lower bound for one construction plan. Does not claim a route or survival playthrough. */
import { readFileSync, writeFileSync } from "node:fs";
import type { RecoveredItem } from "../src/game/assets/assetManifest";
const items: RecoveredItem[] = JSON.parse(
  readFileSync("public/assets/objects/objects.json", "utf8"),
);
const objects: number[] = JSON.parse(
  readFileSync("public/assets/maps/island.json", "utf8"),
).layers.Objects;
const stock = new Map<number, number>();
for (const v of objects) if (v) stock.set(v - 1, (stock.get(v - 1) ?? 0) + 1);
const material = new Map<number, number>(),
  tools = new Map<number, number>(),
  crafts = new Map<number, number>();
function require(id: number, quantity: number, tool = false) {
  const item = items[id];
  if (tool) {
    if ((tools.get(id) ?? 0) >= quantity) return;
    const add = quantity - (tools.get(id) ?? 0);
    tools.set(id, quantity);
    if ((stock.get(id) ?? 0) >= quantity) return;
    quantity = add;
  }
  if (item.recipe.length) {
    crafts.set(id, (crafts.get(id) ?? 0) + quantity);
    for (const r of item.recipe) require(r.item, r.quantity * quantity);
    for (const r of item.craftingTools) require(r.item, r.quantity, true);
  } else if (!tool) {
    material.set(id, (material.get(id) ?? 0) + quantity);
    if (item.interaction.pickupTool > 0)
      require(item.interaction.pickupTool, 1, true);
  }
}
const goals = [9, 10, 94, 19];
for (const id of goals) require(id, 1);
const rows = [...material].map(([id, quantity]) => ({
  id,
  name: items[id].name,
  quantity,
  rawStock: stock.get(id) ?? 0,
  shortfall: Math.max(0, quantity - (stock.get(id) ?? 0)),
}));
const report = {
  scope:
    "One Tent/Cabin/Raft/Ship plan, exact consumed recipes and max reusable tools; crafted tool if absent from raw map. No food costs, travel, hazards, randomized placement or full playthrough simulated.",
  goals,
  materials: rows,
  tools: [...tools].map(([id, quantity]) => ({
    id,
    name: items[id].name,
    quantity,
    rawStock: stock.get(id) ?? 0,
  })),
  crafts: [...crafts].map(([id, quantity]) => ({
    id,
    name: items[id].name,
    quantity,
  })),
};
writeFileSync(
  "evidence/progression-material-audit.json",
  JSON.stringify(report, null, 2) + "\n",
);
console.log(rows.filter((r) => r.shortfall));
