import { worldHazardRules as R } from "../data/worldHazardRules";
import type { WildlifeSnapshot } from "../entities/WildlifeState";
import { clothingProfile, type Outfit } from "./ClothingSystem";
import {
  energyEffect,
  REST_ENERGY,
  REST_MS,
  type SurvivalState,
} from "./SurvivalSystem";

export const worldHazardRule = (mapId: number) =>
  R.contacts.find((rule) => rule.mapId === mapId);
export type WorldContact = NonNullable<ReturnType<typeof worldHazardRule>>;
type ContactActor = Pick<WildlifeSnapshot, "mapId" | "x" | "y" | "contactMs">;

/** Called after each visible actor's source tick, never from an independent wall clock. */
export function advanceWorldContact(
  actor: ContactActor,
  player: { x: number; y: number },
  tickMs: number,
  sailing = false,
): WorldContact | null {
  const rule = worldHazardRule(actor.mapId);
  if (!rule) return null;
  // d skips this entire branch while sailing; i has no such exemption.
  if (sailing && rule.kind === "timed") return null;
  const near =
    Math.abs(Math.floor(actor.x / 16) - Math.floor(player.x / 16)) <=
      R.radiusTiles &&
    Math.abs(Math.floor(actor.y / 16) - Math.floor(player.y / 16)) <=
      R.radiusTiles;
  if (!near) {
    actor.contactMs = 0;
    return null;
  }
  if (rule.kind === "instant") return rule;
  actor.contactMs = (actor.contactMs ?? 0) + tickMs;
  if (actor.contactMs <= R.strictDelayMs) return null;
  actor.contactMs = 0;
  return rule;
}

export function worldContactCost(rule: WorldContact, outfit: Outfit): number {
  if (rule.slot === null) return rule.energy;
  const id = outfit[rule.slot];
  return id !== null && clothingProfile(id)?.slot === rule.slot
    ? rule.protectedEnergy
    : rule.energy;
}

/** Preserve d's damage -> interrupted-rest credit ordering. See WORLD_HAZARDS.md for source quirks. */
export function applyWorldContact(
  state: SurvivalState,
  rule: WorldContact,
  outfit: Outfit,
) {
  if (!state.enabled || state.death)
    return { survival: state, cost: 0, restCredit: 0, interrupted: false };
  const cost = worldContactCost(rule, outfit);
  const interrupted = state.restRemainingMs !== null;
  const restCredit =
    interrupted && rule.kind === "timed"
      ? Math.floor(((REST_MS - state.restRemainingMs!) * REST_ENERGY) / REST_MS)
      : 0;
  let energy = rule.kind === "instant" ? 0 : state.energy - cost;
  // In d, only the unprotected branch clamps before ad.b awards partial rest.
  if (cost === rule.energy) energy = Math.max(0, energy);
  if (interrupted && rule.kind === "timed")
    energy = Math.trunc(energy) + restCredit;
  const survival = energyEffect({ ...state, energy, restRemainingMs: null }, 0);
  if (survival.death === "energy") survival.death = "animal";
  return { survival, cost, restCredit, interrupted };
}
