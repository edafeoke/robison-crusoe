export type WildlifeFamily = "bird" | "passive" | "prey" | "predator" | "shark";
export interface WildlifeProfile {
  mapId: number;
  name: string;
  sourceClass: string;
  family: WildlifeFamily;
  radius: number | null;
  speedTier: number;
  restBase: number;
  frames: {
    left: number[];
    right: number[];
    idle?: number[];
    flyLeft?: number[];
    flyRight?: number[];
  };
}
export interface WildlifeManifest {
  version: 1;
  tickMs: number;
  species: WildlifeProfile[];
}
export interface WildlifeSnapshot {
  /** Stable original object-cell index, never the current moving cell. */
  id: number;
  mapId: number;
  x: number;
  y: number;
  remainingX: number;
  remainingY: number;
  phase: "walk" | "rest" | "fly";
  facing: "left" | "right";
  timer: number;
  duration: number;
  frame: number;
  seed: number;
  /** Optional in older browser saves; elapsed nearby time in data.d, at most 1000 ms. */
  contactMs?: number;
  /** Browser continuation of the last source reaction/speed; absent in legacy saves. */
  reaction?: "flee" | "chase" | null;
}
export interface WildlifeActor extends WildlifeSnapshot {
  profile: WildlifeProfile;
  reaction: "flee" | "chase" | null;
}

export function validWildlife(value: unknown): value is WildlifeSnapshot[] {
  if (!Array.isArray(value) || value.length > 32400) return false;
  const ids = new Set<number>();
  return value.every((s: WildlifeSnapshot) => {
    if (
      !s ||
      !Number.isInteger(s.id) ||
      s.id < 0 ||
      s.id >= 32400 ||
      ids.has(s.id)
    )
      return false;
    ids.add(s.id);
    return (
      Number.isInteger(s.mapId) &&
      s.mapId > 0 &&
      s.mapId <= 120 &&
      [s.x, s.y].every((v) => Number.isFinite(v) && v >= 0 && v < 2880) &&
      [s.remainingX, s.remainingY].every(
        (v) => Number.isFinite(v) && Math.abs(v) <= 2880,
      ) &&
      ["walk", "rest", "fly"].includes(s.phase) &&
      ["left", "right"].includes(s.facing) &&
      [s.timer, s.duration].every((v) => Number.isSafeInteger(v) && v >= 0) &&
      Number.isInteger(s.frame) &&
      s.frame >= 0 &&
      s.frame < 6 &&
      Number.isInteger(s.seed) &&
      s.seed > 0 &&
      s.seed <= 0xffffffff &&
      (s.reaction === undefined ||
        s.reaction === null ||
        s.reaction === "flee" ||
        s.reaction === "chase") &&
      (s.contactMs === undefined ||
        (Number.isSafeInteger(s.contactMs) &&
          s.contactMs >= 0 &&
          s.contactMs <= 1000))
    );
  });
}
