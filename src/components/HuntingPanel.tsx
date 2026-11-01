import { useEffect, useRef } from "react";
import { Crosshair, Pause, ArrowUpRight, X } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import type { RecoveredItem } from "../game/assets/assetManifest";
import { huntingProfile } from "../game/systems/HuntingSystem";
import { huntingRules as R } from "../game/data/huntingRules";
import { huntingDamage } from "../game/systems/ClothingSystem";
export function HuntingControls({ item }: { item: RecoveredItem }) {
  const s = useGameStore(),
    f = s.focus;
  if (item.interaction.huntingTool !== 81) return null;
  if (!huntingProfile(item.id))
    return (
      <p className="muted">
        This animal’s original hunting profile is unavailable.
      </p>
    );
  const equipped = (s.inventory[81] ?? 0) > 0,
    ammo = (s.inventory[45] ?? 0) > 0;
  return (
    <div className="hunting-entry">
      <p>
        Hunt with a bow. Aim with angle and power; the arrow lands at the top of
        its arc.
      </p>
      <div className="fishing-requirements">
        {[81, 45].map((id) => (
          <span key={id}>
            <SpriteIcon sprite={s.items[id].sprite} size={28} />
            <b>{s.items[id].name}</b>
            <small>
              {s.inventory[id] ?? 0} carried ·{" "}
              {id === 81 ? "kept" : "1 per completed hunt"}
            </small>
          </span>
        ))}
      </div>
      {huntingProfile(item.id)!.dangerous && (
        <p className="hunt-warning">
          This animal can attack after ten misses. With your current outfit, its
          retaliation costs {huntingDamage(item.id, s.clothing.equipped)}{" "}
          energy, in addition to the hunt cost. The matching clothing slot
          halves this attack cost.
        </p>
      )}
      <button
        className="primary-button"
        disabled={
          !equipped || !ammo || f?.actorId === undefined || f.itemId !== item.id
        }
        onClick={() => {
          if (
            f?.actorId !== undefined &&
            !s.startHunting(f.index, item.id, f.actorId)
          )
            s.notify(
              "The animal moved out of reach. Return to the island and inspect it again.",
            );
        }}
      >
        <Crosshair size={16} />
        {!equipped
          ? "Requires Bow"
          : !ammo
            ? "Requires Arrow"
            : "Hunt with the bow"}
      </button>
      {!ammo && <small>{s.story[92]}</small>}
      <small>
        Up to ten shots · one Arrow used on completion · Bow kept · +1 strength
        {s.survival.enabled ? " · 10 energy" : ""}
      </small>
    </div>
  );
}
export function HuntingPanel() {
  const s = useGameStore(),
    g = s.huntingGame,
    finish = useRef<HTMLButtonElement>(null);
  useEffect(() => {
    if (g && g.status !== "playing") finish.current?.focus();
  }, [g?.status]);
  if (!g) return null;
  const done = g.status !== "playing",
    locked = g.phase !== "aiming" || !!s.panel,
    target = g.winner === null ? null : g.targets[g.winner],
    item = target ? s.items[target.itemId] : null;
  return (
    <div className="coconut-hud hunting-hud">
      <div className="coconut-title">
        <p className="eyebrow">WITH BOW & ARROW</p>
        <h3>A measured shot.</h3>
      </div>
      <div className="coconut-score">
        <span>
          <Crosshair size={21} />
          <b>{g.targets.length}</b> targets
        </span>
        <span>
          <SpriteIcon sprite={R.arrowFrames[2]} size={25} />
          <b>{g.shots.length}/10</b> shots
        </span>
      </div>
      {!done && (
        <>
          <aside className="coconut-instructions">
            <Crosshair size={24} />
            <h4>Find the top of the arc.</h4>
            <p>
              Choose angle and power. A hit is checked where the arrow stops
              rising.
            </p>
            <p>
              A first miss scatters the animals. Bring down one within ten
              shots.
            </p>
            {g.targets.some((t) => huntingProfile(t.itemId)!.dangerous) && (
              <p className="hunt-warning">
                Dangerous targets attack after ten misses. Your outfit sets each
                attack cost:{" "}
                {g.targets
                  .filter((t) => huntingProfile(t.itemId)!.dangerous)
                  .map(
                    (t) =>
                      `${s.items[t.itemId].name} ${huntingDamage(t.itemId, g.outfit)} energy`,
                  )
                  .join("; ")}
                .
              </p>
            )}
            <small>
              ← → angle · ↑ ↓ power
              <br />
              Space or Enter to shoot
            </small>
          </aside>
          <div className="coconut-controls">
            <label htmlFor="hunt-angle">
              Angle <b>{g.angle}</b>
            </label>
            <input
              id="hunt-angle"
              aria-label="Hunting angle"
              type="range"
              min="10"
              max="70"
              step="5"
              value={g.angle}
              disabled={locked}
              onKeyDown={(e) => e.stopPropagation()}
              onChange={(e) => s.aimHunt(+e.target.value, g.power)}
            />
            <label htmlFor="hunt-power">
              Power <b>{g.power}%</b>
            </label>
            <input
              id="hunt-power"
              aria-label="Hunting power"
              type="range"
              min="40"
              max="100"
              step="2"
              value={g.power}
              disabled={locked}
              onKeyDown={(e) => e.stopPropagation()}
              onChange={(e) => s.aimHunt(g.angle, +e.target.value)}
            />
            <button
              className="primary-button"
              disabled={locked}
              onClick={s.fireHunt}
            >
              <Crosshair size={16} />
              {g.phase === "attacking"
                ? "Animal attacking…"
                : g.phase === "flight"
                  ? "Arrow in flight…"
                  : "Shoot arrow"}
            </button>
            <small>Controls reset after each miss.</small>
            {g.phase === "attacking" && (
              <p className="hunt-warning" role="status">
                Keep your guard. The animal is closing in.
              </p>
            )}
          </div>
        </>
      )}
      <div className="coconut-footer">
        <span>
          Energy {Math.floor(s.survival.energy)} · Hunger{" "}
          {Math.floor(s.survival.hunger)} · Thirst{" "}
          {Math.floor(s.survival.thirst)}
        </span>
        <div>
          <button
            aria-label="Pause hunting"
            onClick={() => s.setPanel("settings")}
          >
            <Pause size={14} />
            Pause
          </button>
          <button
            disabled={!done && g.phase === "attacking"}
            onClick={s.leaveHunting}
          >
            <X size={14} />
            {done ? "Return to island" : "Leave hunt"}
          </button>
        </div>
      </div>
      {done && (
        <section className="coconut-result" role="status">
          <SpriteIcon sprite={item?.sprite ?? R.bowSprite} size={65} />
          <p className="eyebrow">
            {item ? "SOMETHING FOR THE FIRE" : "THE HUNT IS OVER"}
          </p>
          <h3>{item ? `${s.story[93]} ${item.name}` : s.story[83]}</h3>
          <p>
            {item
              ? `${item.name} ×1 added to your satchel.`
              : "Ten shots. The animals got away."}
          </p>
          <small>
            Arrow ×1 used · Bow kept · +1 strength
            <br />
            {s.survival.enabled
              ? `${10 + g.attackEnergy} energy used`
              : "Needs paused"}
            {g.attackEnergy > 0 ? " · includes animal attack" : ""}
          </small>
          <button
            ref={finish}
            className="primary-button"
            onClick={s.leaveHunting}
          >
            Return to the island <ArrowUpRight size={16} />
          </button>
        </section>
      )}
    </div>
  );
}
