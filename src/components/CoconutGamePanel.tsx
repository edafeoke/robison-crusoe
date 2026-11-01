import { useEffect, useRef } from "react";
import { ArrowUpRight, CircleDot, Pause, Target, X } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import { coconutRules as R } from "../game/data/coconutRules";
export function CoconutPalmDialogue() {
  const s = useGameStore(),
    index = s.focus?.index;
  return (
    <div className="discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={851970} size={80} />
        <span>ABOVE THE CANOPY</span>
      </div>
      <h3>Coconut palm</h3>
      <blockquote>“{s.story[11]}”</blockquote>
      <p className="observation-credit">— Robinson’s original observation</p>
      <div className="palm-challenge-note">
        <Target size={21} />
        <p>
          Knock down five coconuts in ten throws. Hit each twice before it stops
          shaking.
        </p>
      </div>
      <p className="muted">
        Requires a stone · completed round uses 3 energy · a win adds one
        coconut.
      </p>
      <div className="discovery-actions">
        <button
          className="primary-button"
          disabled={index === undefined || (s.inventory[3] ?? 0) < 1}
          onClick={() => index !== undefined && s.startCoconutGame(index)}
        >
          <CircleDot size={16} />
          {(s.inventory[3] ?? 0) > 0
            ? "Throw stones at the coconuts"
            : "Requires Stone"}
        </button>
        <button className="secondary-button" onClick={() => s.setPanel(null)}>
          Return to the island <ArrowUpRight size={16} />
        </button>
      </div>
      {(s.inventory[3] ?? 0) < 1 && <p className="muted">{s.story[91]}</p>}
    </div>
  );
}
export function CoconutGamePanel() {
  const s = useGameStore(),
    game = s.coconutGame,
    finish = useRef<HTMLButtonElement>(null);
  useEffect(() => {
    if (game?.status && game.status !== "playing") finish.current?.focus();
  }, [game?.status]);
  if (!game) return null;
  const flight = !!game.shot,
    done = game.status !== "playing";
  return (
    <div className="coconut-hud">
      <div className="coconut-title">
        <p className="eyebrow">UNDER THE COCONUT PALMS</p>
        <h3>A well-aimed stone.</h3>
      </div>
      <div className="coconut-score">
        <span>
          <SpriteIcon sprite={R.sprites.coconut} size={29} />
          <b>{game.knocked}/5</b> down
        </span>
        <span>
          <SpriteIcon sprite={R.sprites.stone} size={25} />
          <b>{game.throws}/10</b> throws
        </span>
      </div>
      {!done && (
        <>
          <aside className="coconut-instructions">
            <Target size={23} />
            <h4>Two hits. One chance to follow through.</h4>
            <p>
              A first hit makes a coconut shake for eleven seconds. Hit it again
              to knock it down.
            </p>
            <p>Bring five down within ten throws to earn one coconut.</p>
            <small>
              ← → adjust angle
              <br />↑ ↓ adjust power
              <br />
              Space or Enter to throw
            </small>
          </aside>
          <div className="coconut-controls">
            <label htmlFor="coconut-angle">
              Angle <b>{game.angle}</b>
            </label>
            <input
              id="coconut-angle"
              type="range"
              min="10"
              max="70"
              step="5"
              value={game.angle}
              disabled={flight || !!s.panel}
              onKeyDown={(e) => e.stopPropagation()}
              onChange={(e) => s.aimCoconut(+e.target.value, game.power)}
            />
            <label htmlFor="coconut-power">
              Power <b>{game.power}%</b>
            </label>
            <input
              id="coconut-power"
              type="range"
              min="40"
              max="100"
              step="2"
              value={game.power}
              disabled={flight || !!s.panel}
              onKeyDown={(e) => e.stopPropagation()}
              onChange={(e) => s.aimCoconut(game.angle, +e.target.value)}
            />
            <button
              className="primary-button"
              disabled={flight || !!s.panel || game.throws >= 10}
              onClick={s.throwCoconut}
            >
              <CircleDot size={16} />
              {flight ? "Stone in flight…" : "Throw stone"}
            </button>
            <small>Controls reset after each throw.</small>
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
            aria-label="Pause coconut challenge"
            onClick={() => s.setPanel("settings")}
          >
            <Pause size={14} />
            Pause
          </button>
          <button onClick={s.leaveCoconut}>
            <X size={14} />
            {done ? "Return to island" : "Leave challenge"}
          </button>
        </div>
      </div>
      {!done && (
        <p className="coconut-hit-feedback" aria-live="polite">
          {game.targets.filter((t) => t.phase === 1).length
            ? `${game.targets.filter((t) => t.phase === 1).length} shaking · hit again`
            : "Hit each coconut twice"}
        </p>
      )}
      {done && (
        <section className="coconut-result" role="status">
          <SpriteIcon
            sprite={game.status === "won" ? R.sprites.coconut : R.sprites.stone}
            size={60}
          />
          <p className="eyebrow">
            {game.status === "won"
              ? "A LITTLE PERSISTENCE PAYS OFF"
              : "ANOTHER DAY, ANOTHER TRY"}
          </p>
          <h3>{game.status === "won" ? s.story[89] : s.story[83]}</h3>
          <p>
            {game.status === "won"
              ? "One coconut has been added to your satchel."
              : `${game.knocked} coconuts down. You needed five.`}
          </p>
          <small>
            {s.survival.enabled ? "3 energy used" : "Survival paused"} · stone
            wear {s.coconutUses}/6 completed rounds
          </small>
          <button
            ref={finish}
            className="primary-button"
            onClick={s.leaveCoconut}
          >
            Return to the island <ArrowUpRight size={16} />
          </button>
        </section>
      )}
    </div>
  );
}
