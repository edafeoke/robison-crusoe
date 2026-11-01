import { Utensils, LockKeyhole } from "lucide-react";
import type { RecoveredItem } from "../game/assets/assetManifest";
import {
  worldFoodAvailability,
  worldFoodEffects,
  worldFoodTarget,
} from "../game/systems/WorldConsumptionSystem";
import { useGameStore } from "../store/gameStore";

export function WorldFoodControls({ item }: { item: RecoveredItem }) {
  const s = useGameStore(),
    availability = worldFoodAvailability(item, s.inventory),
    focus = s.focus;
  if (
    !availability.supported ||
    !focus ||
    focus.kind ||
    focus.itemId !== item.id
  )
    return null;
  const target = worldFoodTarget(item, focus.index, s),
    effects = worldFoodEffects(item),
    tool = availability.tool === null ? null : s.items[availability.tool];
  return (
    <div className="item-use world-food-use">
      <p className="eyebrow">EAT ON THE ISLAND</p>
      <div className="food-effects">
        {Object.entries(effects)
          .filter(([, n]) => n !== 0)
          .map(([name, n]) => (
            <span className={n < 0 ? "negative" : ""} key={name}>
              {name}{" "}
              <b>
                {n > 0 ? "+" : "−"}
                {Math.abs(n)}
              </b>
            </span>
          ))}
      </div>
      {tool && (
        <p className="use-tool">
          <LockKeyhole size={13} />
          {availability.allowed ? "Using" : "Requires"} {tool.name} · kept
        </p>
      )}
      <button
        className={`secondary-button ${effects.energy < 0 ? "danger" : ""}`}
        disabled={
          !s.ready ||
          !s.started ||
          !target ||
          !availability.allowed ||
          !!s.survival.death ||
          s.survival.restRemainingMs !== null ||
          !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame)
        }
        onClick={() => s.consumeWorld(focus.index, item.id)}
      >
        <Utensils size={16} />
        Eat {item.name.toLowerCase()} here
      </button>
      <small>
        Eat directly from this spot. Food contributes to body weight.
      </small>
      {effects.energy < 0 && (
        <small className="negative">
          This food drains energy and can be fatal.
        </small>
      )}
      {!s.survival.enabled && (
        <small>Exploration mode keeps your reserves full.</small>
      )}
      {s.survival.restRemainingMs !== null && (
        <small>Finish or cancel your rest before eating.</small>
      )}
    </div>
  );
}
