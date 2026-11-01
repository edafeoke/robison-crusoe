import type { SaveData } from "./SaveSystem";
export const HOUR_MS = 3600000;
/** data.v.c(boolean), positive/sleep branch. Browser elapsed hours avoid device timezone quirks. */
export function sleepRecovery(hours: number): number {
  return !Number.isFinite(hours) || hours < 1
    ? 0
    : hours <= 15
      ? 10
      : hours <= 35
        ? 20
        : hours <= 72
          ? 30
          : hours <= 119
            ? 40
            : 50;
}
export function claimSleep(save: SaveData, now: number): SaveData {
  const { sleepStartedAt, ...next } = save;
  if (sleepStartedAt === undefined || !next.survival) return next;
  const hours = Math.floor(Math.max(0, now - sleepStartedAt) / HOUR_MS),
    bonus = sleepRecovery(hours),
    s = next.survival;
  if (!s.death && s.enabled)
    next.survival = {
      ...s,
      hunger: Math.min(100, s.hunger + bonus),
      thirst: Math.min(100, s.thirst + bonus),
      energy: Math.min(100, s.energy + bonus),
    };
  return next;
}
