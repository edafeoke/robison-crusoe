import { resourceHistoryRules as R } from "../data/resourceHistoryRules";
import type { RecoveredItem } from "../assets/assetManifest";
import type { Inventory } from "./ItemRules";
export interface ResourceHistory {
  recent: number[];
  permanent: number[];
}
export const initialResourceHistory = (): ResourceHistory => ({
  recent: [],
  permanent: [],
});
export function validResourceHistory(value: unknown): value is ResourceHistory {
  const h = value as ResourceHistory;
  const list = (a: unknown, cap: number) =>
    Array.isArray(a) &&
    a.length <= cap &&
    a.every((i) => Number.isInteger(i) && i >= 0 && i < 32400);
  return (
    !!h &&
    list(h.recent, R.recentCapacity) &&
    list(h.permanent, R.permanentCapacity)
  );
}
export const renewableStatic = (item: RecoveredItem | undefined) =>
  !!item &&
  (item.category === 0 || item.category === 1) &&
  !(R.mobileMapIds as readonly number[]).includes(item.mapId);
/** ad.a records category0 pickup, but direct world consumption ad.j does not record recent history. */
export function rememberPickup(
  history: ResourceHistory,
  item: RecoveredItem,
  index: number,
): ResourceHistory {
  if (item.category !== 0 || !renewableStatic(item)) return history;
  const field = (R.minedItems as readonly number[]).includes(item.id)
    ? "permanent"
    : "recent";
  const cap = field === "permanent" ? R.permanentCapacity : R.recentCapacity;
  return { ...history, [field]: [...history[field], index].slice(-cap) };
}
/** Legacy browser removals did not distinguish TAKE from world USE; migrate conservatively in recorded order. */
export function migrateResourceHistory(
  objects: readonly number[] | undefined,
  items: RecoveredItem[],
  collected: readonly number[],
) {
  let history = initialResourceHistory();
  for (const index of collected) {
    const item = items[(objects?.[index] ?? 0) - 1];
    if (item) history = rememberPickup(history, item, index);
  }
  return history;
}
export function crossedResourceReset(previous: number, next: number) {
  const first = ((R.dailyResetHour - 8 + 24) % 24) * 3600,
    day = 86400;
  return (
    next >= first &&
    Math.floor((next - first) / day) > Math.floor((previous - first) / day)
  );
}
export function refreshResources(
  objects: readonly number[],
  items: RecoveredItem[],
  collected: readonly number[],
  history: ResourceHistory,
  inventory: Inventory,
  occupied: (index: number) => boolean = () => false,
  dailyReset = false,
) {
  const nextHistory = dailyReset ? { ...history, recent: [] } : history;
  const recent = new Set(nextHistory.recent),
    permanent = new Set(nextHistory.permanent),
    revived: number[] = [];
  const kept = collected.filter((index) => {
    const item = items[objects[index] - 1];
    if (
      !renewableStatic(item) ||
      recent.has(index) ||
      permanent.has(index) ||
      occupied(index)
    )
      return true;
    if (
      (R.uniqueMapIds as readonly number[]).includes(item.mapId) &&
      (inventory[item.id] ?? 0) > 0
    )
      return true;
    revived.push(index);
    return false;
  });
  return {
    collected: revived.length ? kept : collected,
    resources: nextHistory,
    revived,
  };
}
