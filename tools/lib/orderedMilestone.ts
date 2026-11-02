import type { ExpeditionRunner } from "./expeditionRunner";
/** One documented browser-style strategy. Does not claim the original game's mission order. */
export const milestoneGoals = [
  76, 5, 9, 16, 3, 78, 96, 104, 109, 112, 89, 27, 92, 77, 94, 10, 22,
] as const;
export function runSextantMilestone(
  runner: ExpeditionRunner,
  onGoal?: () => void,
) {
  for (const id of milestoneGoals) {
    runner.ensure(id, id === 27 ? 3 : 1);
    if (id === 77) {
      runner.go([94 + 93 * 180]);
      runner.note("returned to raft shore");
    }
    runner.note("goal", { item: id });
    onGoal?.();
  }
  runner.note("milestone complete", {
    endpoint: "Sextant recovered after raft traversal; ship remains unrepaired",
  });
}
