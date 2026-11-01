import { useEffect, useRef } from "react";
import {
  Fish,
  Pause,
  ArrowUpRight,
  X,
  ArrowUp,
  ArrowDown,
  ArrowLeft,
  ArrowRight,
} from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import { rodFishingRules as R } from "../game/data/rodFishingRules";
export function RodFishingDialogue() {
  const s = useGameStore(),
    index = s.focus?.index,
    rod = (s.inventory[14] ?? 0) > 0,
    bait = (s.inventory[90] ?? 0) > 0;
  return (
    <div className="discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={R.toolSprite} size={90} />
        <span>BEYOND THE SHALLOWS</span>
      </div>
      <h3>A line into deeper water.</h3>
      <p>
        Guide the trailing hook onto a fish, set it, then reel upward before the
        fish pulls free.
      </p>
      <div className="fishing-requirements">
        {[14, 90].map((id) => (
          <span key={id}>
            <SpriteIcon sprite={s.items[id].sprite} size={30} />
            <b>{s.items[id].name}</b>
            <small>
              {s.inventory[id] ?? 0} carried ·{" "}
              {id === 14 ? "kept" : "1 per completed attempt"}
            </small>
          </span>
        ))}
      </div>
      <p className="muted">
        {s.survival.enabled
          ? "Completed attempts use 5 energy and add 1 strength."
          : "Needs are paused. Completed attempts still add 1 strength."}{" "}
        A catch adds one fish to your satchel.
      </p>
      {!bait && <blockquote>“{s.story[85]}”</blockquote>}
      <div className="discovery-actions">
        <button
          className="primary-button"
          disabled={!rod || !bait || index === undefined}
          onClick={() => index !== undefined && s.startRodFishing(index)}
        >
          <Fish size={16} />
          {!rod
            ? "Requires Fishing rod"
            : !bait
              ? "Requires Worms"
              : "Fish with the rod"}
        </button>
        <button className="secondary-button" onClick={() => s.setPanel(null)}>
          Return to the island <ArrowUpRight size={16} />
        </button>
      </div>
      {!rod && (
        <p className="muted">
          Craft a fishing rod in the workbench. Worms require a knife to gather.
        </p>
      )}
    </div>
  );
}
export function RodFishingPanel() {
  const s = useGameStore(),
    g = s.rodGame,
    finish = useRef<HTMLButtonElement>(null);
  useEffect(() => {
    if (g && g.status !== "playing") finish.current?.focus();
  }, [g?.status]);
  if (!g) return null;
  const done = g.status !== "playing",
    profile = R.fish[g.species],
    item = s.items[profile.item],
    dx = g.fishX - g.hookX,
    dy = g.fishY - g.hookY,
    hint =
      Math.abs(dx) >= profile.width / 2
        ? `Fish ${dx < 0 ? "left" : "right"} of the hook`
        : Math.abs(dy) >= Math.trunc(profile.height / 2)
          ? `Fish ${dy < 0 ? "above" : "below"} the hook`
          : "Within reach · set the hook";
  return (
    <div className="coconut-hud fishing-hud rod-hud">
      <div className="coconut-title">
        <p className="eyebrow">A LINE INTO THE DEEP</p>
        <h3>A steady hand.</h3>
      </div>
      <div className="coconut-score">
        <span>
          <SpriteIcon sprite={item.sprite} size={28} />
          <b>{item.name}</b>
        </span>
        <span>
          <SpriteIcon sprite={s.items[90].sprite} size={25} />
          <b>{s.inventory[90] ?? 0}</b> bait
        </span>
      </div>
      {!done && (
        <>
          <aside className="coconut-instructions">
            <Fish size={24} />
            <h4>The hook follows. The fish pulls.</h4>
            <p>
              Move the rod with WASD or arrows. The hook trails behind your
              hand.
            </p>
            <p>
              Set the hook when it meets the fish. Once hooked, hold ↑ to reel
              upward. Keep clear of the sides and bottom.
            </p>
            <small>
              Space or Enter to set the hook
              <br />
              Click the water to set it
            </small>
          </aside>
          <div className="coconut-controls fishing-controls rod-controls">
            <p role="status">
              {g.phase === "hooked" ? "Hooked · reel upward!" : hint}
            </p>
            <div
              className="rod-steering"
              role="group"
              aria-label="Nudge the fishing rod"
            >
              <button
                aria-label="Nudge rod up"
                disabled={!!s.panel}
                onClick={() => s.steerRod("up")}
              >
                <ArrowUp size={17} />
              </button>
              <button
                aria-label="Nudge rod left"
                disabled={!!s.panel}
                onClick={() => s.steerRod("left")}
              >
                <ArrowLeft size={17} />
              </button>
              <button
                aria-label="Nudge rod down"
                disabled={!!s.panel}
                onClick={() => s.steerRod("down")}
              >
                <ArrowDown size={17} />
              </button>
              <button
                aria-label="Nudge rod right"
                disabled={!!s.panel}
                onClick={() => s.steerRod("right")}
              >
                <ArrowRight size={17} />
              </button>
            </div>
            <button
              className="primary-button"
              disabled={!!s.panel || g.strike || g.phase === "hooked"}
              onClick={s.strikeRod}
            >
              <Fish size={16} />
              {g.phase === "hooked"
                ? "Reel with ↑"
                : g.strike
                  ? "Setting the hook…"
                  : "Set hook"}
            </button>
            <small>
              Buttons nudge · hold keys to move
              <br />
              One worm per completed attempt
            </small>
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
            aria-label="Pause rod fishing"
            onClick={() => s.setPanel("settings")}
          >
            <Pause size={14} />
            Pause
          </button>
          <button onClick={s.leaveRodFishing}>
            <X size={14} />
            {done ? "Return to island" : "Leave fishing"}
          </button>
        </div>
      </div>
      {done && (
        <section className="coconut-result" role="status">
          <SpriteIcon
            sprite={g.status === "won" ? item.sprite : R.toolSprite}
            size={65}
          />
          <p className="eyebrow">
            {g.status === "won"
              ? "SOMETHING FOR THE SUPPER POT"
              : "THE LINE COMES BACK LIGHT"}
          </p>
          <h3>{s.story[g.status === "won" ? 84 : 83]}</h3>
          <p>
            {g.status === "won"
              ? `${item.name} ×1 added to your satchel.`
              : g.reason === "miss"
                ? "The hook missed the fish."
                : g.reason === "side"
                  ? "The fish pulled free at the side."
                  : "The fish pulled the hook too low."}
          </p>
          <small>
            Worms ×1 used · Fishing rod kept
            <br />
            {s.survival.enabled
              ? "5 energy used · +1 strength"
              : "Needs paused · +1 strength"}
          </small>
          <button
            ref={finish}
            className="primary-button"
            onClick={s.leaveRodFishing}
          >
            Return to the island <ArrowUpRight size={16} />
          </button>
        </section>
      )}
    </div>
  );
}
