import {
  Droplets,
  Utensils,
  Zap,
  Moon,
  RotateCcw,
  Compass,
} from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { useEffect, useRef } from "react";
import {
  worldHazardRule,
  worldContactCost,
} from "../game/systems/WorldHazardSystem";
export function ConditionPanel() {
  const s = useGameStore(),
    life = s.survival,
    hazard = s.focus ? worldHazardRule(s.focus.itemId + 1) : undefined;
  return (
    <div className="condition-plaque">
      <p>
        YOUR CONDITION{" "}
        <span>
          {life.death
            ? "COLLAPSED"
            : life.enabled
              ? "SURVIVAL"
              : "NEEDS PAUSED"}
        </span>
      </p>
      {(
        [
          { icon: Utensils, name: "Hunger", key: "hunger", color: "#d9b66e" },
          { icon: Droplets, name: "Thirst", key: "thirst", color: "#83bdc2" },
          { icon: Zap, name: "Energy", key: "energy", color: "#aac094" },
        ] as const
      ).map(({ icon: Icon, name, key, color }) => (
        <div className="condition-line" key={key}>
          <Icon size={14} style={{ color }} />
          <span>{name}</span>
          <div
            className="condition-track"
            role="meter"
            aria-label={name}
            aria-valuenow={Math.floor(life[key])}
            aria-valuemin={0}
            aria-valuemax={100}
          >
            <i
              style={{
                background: life[key] <= 15 ? "#d88069" : color,
                width: `${life[key]}%`,
              }}
            />
          </div>
          <small>{Math.floor(life[key])}</small>
        </div>
      ))}
      {life.restRemainingMs !== null && (
        <div className="rest-status" role="status">
          <Moon size={13} />
          <span>Resting · {Math.ceil(life.restRemainingMs / 1000)}s</span>
          <button onClick={s.cancelRest}>Cancel</button>
        </div>
      )}
      {life.enabled && !life.death && hazard && (
        <small className="condition-warning" role="status">
          Keep your distance · {s.items[hazard.mapId - 1]?.name}{" "}
          {hazard.kind === "instant"
            ? "can exhaust your energy instantly."
            : `drains ${worldContactCost(hazard, s.clothing.equipped)} energy per attack.`}
        </small>
      )}
      {life.enabled &&
        !life.death &&
        Math.min(life.hunger, life.thirst, life.energy) <= 20 && (
          <small className="condition-warning">
            {life.thirst <= 20
              ? "Find fruit to restore your thirst."
              : life.hunger <= 20
                ? "Eat from your satchel."
                : "Find a safe place to rest."}
          </small>
        )}
    </div>
  );
}
export function SurvivalSettings() {
  const s = useGameStore(),
    life = s.survival;
  return (
    <>
      <div className="setting-row">
        <div>
          <h4>
            <Utensils size={17} />
            Original survival rules
          </h4>
          <p>
            Hunger −10, thirst −14, energy −4 per real minute.
            <br />
            Menus and hidden tabs pause the expedition.
          </p>
        </div>
        <button
          className={`toggle ${life.enabled ? "on" : ""}`}
          role="switch"
          aria-label="Original survival rules"
          aria-checked={life.enabled}
          onClick={() => s.setSurvivalEnabled(!life.enabled)}
        >
          <span />
        </button>
      </div>
      <div className="rest-setting">
        <div>
          <h4>
            <Moon size={17} />A moment to recover
          </h4>
          <p>
            Rest for 10 seconds to restore up to 30 energy. Hunger and thirst
            continue to drain.
          </p>
        </div>
        <button
          className="secondary-button"
          disabled={
            !s.started ||
            !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame) ||
            !!life.death ||
            life.restRemainingMs !== null
          }
          onClick={s.beginRest}
        >
          <Moon size={16} />
          Rest a moment <kbd>R</kbd>
        </button>
        {life.restRemainingMs !== null && (
          <button className="text-button" onClick={s.cancelRest}>
            Cancel rest
          </button>
        )}
      </div>
      <p className="muted survival-note">
        Strength {Math.floor(life.strength)} · Morale {Math.floor(life.morale)}.
        Nearby predators drain energy and interrupt rest. Clothing reduces their
        attacks; sharks exhaust energy instantly. Turning survival off restores
        the three reserves for exploration.
      </p>
      <div className="rest-setting">
        <div>
          <h4>
            <Moon size={17} /> Sleep while away
          </h4>
          <p>
            Save and return to the title screen. After one real hour away,
            recover 10 hunger, thirst and energy; longer sleep restores up to
            50. Ordinary saves pause your reserves.
          </p>
        </div>
        <button
          className="secondary-button"
          onClick={s.sleep}
          disabled={
            !s.ready ||
            !s.started ||
            s.saving ||
            !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame) ||
            !!life.death ||
            life.restRemainingMs !== null
          }
        >
          <Moon size={16} /> Sleep · save & return to title
        </button>
      </div>
    </>
  );
}
export function SurvivalLoss() {
  const s = useGameStore();
  const root = useRef<HTMLElement>(null);
  useEffect(() => {
    if (!s.survival.death) return;
    const previous = document.activeElement as HTMLElement;
    const buttons = root.current?.querySelectorAll<HTMLButtonElement>("button");
    buttons?.[0]?.focus();
    const trap = (event: KeyboardEvent) => {
      if (event.key !== "Tab" || !buttons?.length) return;
      if (event.shiftKey && document.activeElement === buttons[0]) {
        event.preventDefault();
        buttons[buttons.length - 1].focus();
      } else if (
        !event.shiftKey &&
        document.activeElement === buttons[buttons.length - 1]
      ) {
        event.preventDefault();
        buttons[0].focus();
      }
    };
    document.addEventListener("keydown", trap);
    return () => {
      document.removeEventListener("keydown", trap);
      previous?.focus();
    };
  }, [s.survival.death]);
  if (!s.survival.death) return null;
  const cause = s.survival.death;
  return (
    <div className="modal-backdrop survival-loss">
      <section
        ref={root}
        className="modal"
        role="dialog"
        aria-modal="true"
        aria-labelledby="loss-title"
      >
        <div className="modal-body loss-body">
          <p className="eyebrow">THE ISLAND TAKES ITS TOLL</p>
          <h2 id="loss-title">Robinson has collapsed.</h2>
          <p>
            {cause === "animal"
              ? s.story[95]
              : cause === "poison"
                ? s.story[110]
                : cause === "energy"
                  ? s.story[96]
                  : cause === "hunger"
                    ? "His hunger reserve is empty."
                    : "His thirst reserve is empty."}
          </p>
          <button className="primary-button" onClick={s.load}>
            <RotateCcw size={16} />
            Load saved expedition
          </button>
          <button
            className="secondary-button"
            onClick={() => s.setSurvivalEnabled(false)}
          >
            <Compass size={16} />
            Continue in exploration mode
          </button>
          <small>
            Your earlier save is kept. Exploration mode restores your reserves
            and keeps your discoveries.
          </small>
        </div>
      </section>
    </div>
  );
}
