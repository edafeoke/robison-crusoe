import { Utensils, LockKeyhole } from "lucide-react";
import type { RecoveredItem } from "../game/assets/assetManifest";
import {
  edible,
  foodEffects,
  useAvailability,
} from "../game/systems/SurvivalSystem";
import { useGameStore } from "../store/gameStore";
export function ItemUseControls({ item }: { item: RecoveredItem }) {
  const s = useGameStore();
  if (!edible(item)) return null;
  const available = useAvailability(item, s.inventory),
    effects = foodEffects(item),
    tool = available.tool === null ? null : s.items[available.tool];
  return (
    <div className="item-use">
      <p className="eyebrow">FOOD & RECOVERY</p>
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
          {(s.inventory[tool.id] ?? 0) > 0 ? "Using" : "Requires"} {tool.name}
          {tool.id === 3
            ? ` · one stone per six uses (${s.survival.stoneUses}/6)`
            : " · tool kept"}
        </p>
      )}
      {!s.survival.enabled && (
        <small>Survival is paused. Eating still uses one item.</small>
      )}
      <small>
        Food also contributes to body weight, even with full reserves.
      </small>
      {!(s.inventory[item.id] > 0) && <small>None left in your satchel.</small>}
      <button
        className={
          effects.energy < 0 ? "secondary-button danger" : "primary-button"
        }
        disabled={
          !available.allowed ||
          !s.started ||
          !!s.survival.death ||
          s.survival.restRemainingMs !== null
        }
        onClick={() => s.consume(item.id)}
      >
        <Utensils size={15} />
        Eat {item.name.toLowerCase()}
      </button>
      {effects.energy < 0 && (
        <small className="negative">
          This food drains energy and can be fatal.
        </small>
      )}
      {s.survival.restRemainingMs !== null && (
        <small>Finish or cancel your rest before eating.</small>
      )}
    </div>
  );
}
