import { Anchor, Check, LockKeyhole, Hammer } from "lucide-react";
import { shipRules as R } from "../game/data/shipRules";
import {
  shipRepairAvailability,
  shipRepairTarget,
} from "../game/systems/ShipSystem";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
export function ShipDialogue() {
  const s = useGameStore(),
    item = s.items[R.wreckItem],
    repair = s.items[R.repairItem],
    status = shipRepairAvailability(s.items, s.inventory, s.ship);
  const target = !!s.focus && shipRepairTarget(s.focus.index, s);
  return (
    <div className="discovery ship-discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={item.sprite} size={120} />
        <span>THE PIRATE SHIPWRECK</span>
      </div>
      <h3>{item.name}</h3>
      <blockquote>“{item.description}”</blockquote>
      <p className="observation-credit">— Robinson’s original observation</p>
      <p className="journal-prose">
        Rebuild this vessel for the voyage home. Reconstruction begins the final
        departure.
      </p>
      <h4 className="ship-requirements-title">Materials for reconstruction</h4>
      <div className="ship-requirements">
        {repair.recipe.map((r) => (
          <div key={r.item}>
            <SpriteIcon sprite={s.items[r.item].sprite} size={26} />
            <span>{s.items[r.item].name}</span>
            <strong>
              {s.inventory[r.item] ?? 0}/{r.quantity}
            </strong>
            {(s.inventory[r.item] ?? 0) >= r.quantity ? (
              <Check size={14} />
            ) : (
              <LockKeyhole size={14} />
            )}
          </div>
        ))}
      </div>
      <h4 className="ship-requirements-title">Equipment · kept after repair</h4>
      <div className="ship-requirements">
        {repair.craftingTools.map((r) => (
          <div key={r.item}>
            <SpriteIcon sprite={s.items[r.item].sprite} size={26} />
            <span>{s.items[r.item].name}</span>
            <strong>
              {s.inventory[r.item] ?? 0}/{r.quantity}
            </strong>
            {(s.inventory[r.item] ?? 0) >= r.quantity ? (
              <Check size={14} />
            ) : (
              <LockKeyhole size={14} />
            )}
          </div>
        ))}
      </div>
      <p className="muted">
        Make the Large sail in the workbench with Sail×4, Fabric×1 and Cotton
        thread×1, using Scissors and Needle.
      </p>
      <div className="discovery-actions">
        <button
          className="primary-button"
          disabled={
            !s.ready ||
            !s.started ||
            s.saving ||
            !target ||
            !status.allowed ||
            !!s.survival.death ||
            s.survival.restRemainingMs !== null
          }
          onClick={() => s.repairShip(s.focus!.index)}
        >
          <Anchor size={16} />
          Reconstruct the pirate ship
        </button>
        <button
          className="secondary-button"
          onClick={() => s.setPanel("crafting")}
        >
          <Hammer size={16} />
          Open workbench
        </button>
      </div>
    </div>
  );
}
