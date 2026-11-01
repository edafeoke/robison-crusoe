import { useEffect, useRef } from "react";
import { Fish, Pause, ArrowUpRight, X } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
export function NetFishingDialogue() {
  const s = useGameStore(),
    index = s.focus?.index,
    net = (s.inventory[83] ?? 0) > 0,
    bait = (s.inventory[26] ?? 0) > 0;
  return (
    <div className="discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={851986} size={90} />
        <span>IN THE SHALLOWS</span>
      </div>
      <h3>A patient kind of fishing.</h3>
      <p>
        Small fish pass through these shallow waters. Time your net to catch ten
        in five casts.
      </p>
      <div className="fishing-requirements">
        {[83, 26].map((id) => (
          <span key={id}>
            <SpriteIcon sprite={s.items[id].sprite} size={30} />
            <b>{s.items[id].name}</b>
            <small>
              {s.inventory[id] ?? 0} carried ·{" "}
              {id === 83 ? "kept" : "1 per completed round"}
            </small>
          </span>
        ))}
      </div>
      <p className="muted">
        {s.survival.enabled
          ? "Completed rounds use 10 energy and add 1 strength."
          : "Survival is paused."}{" "}
        A win awards one original reward item.
      </p>
      {!bait && <blockquote>“{s.story[88]}”</blockquote>}
      <div className="discovery-actions">
        <button
          className="primary-button"
          disabled={!net || !bait || index === undefined}
          onClick={() => index !== undefined && s.startNetFishing(index)}
        >
          <Fish size={16} />
          {!net
            ? "Requires Net"
            : !bait
              ? "Requires Potato"
              : "Fish with the net"}
        </button>
        <button className="secondary-button" onClick={() => s.setPanel(null)}>
          Return to the island <ArrowUpRight size={16} />
        </button>
      </div>
      {!net && (
        <p className="muted">
          Craft the original Net with four ropes, four stones and a reusable
          knife.
        </p>
      )}
    </div>
  );
}
export function NetFishingPanel() {
  const s = useGameStore(),
    g = s.fishingGame,
    finish = useRef<HTMLButtonElement>(null);
  useEffect(() => {
    if (g && g.status !== "playing") finish.current?.focus();
  }, [g?.status]);
  if (!g) return null;
  const done = g.status !== "playing",
    reward = g.reward === null ? null : s.items[g.reward];
  return (
    <div className="coconut-hud fishing-hud">
      <div className="coconut-title">
        <p className="eyebrow">THE SHALLOW-WATER FISHERY</p>
        <h3>Wait for the shoal.</h3>
      </div>
      <div className="coconut-score">
        <span>
          <Fish size={25} />
          <b>{g.caught}/10</b> fish
        </span>
        <span>
          <SpriteIcon sprite={851986} size={27} />
          <b>{g.casts}/5</b> casts
        </span>
      </div>
      {!done && (
        <>
          <aside className="coconut-instructions">
            <Fish size={24} />
            <h4>A small net. The right moment.</h4>
            <p>
              Watch the fish cross the blue channel. Release the net when their
              paths line up.
            </p>
            <p>
              Catch ten fish within five casts. Each cast brings a fresh shoal.
            </p>
            <small>
              Space or Enter to cast
              <br />
              Or click the water
            </small>
          </aside>
          <div className="coconut-controls fishing-controls">
            <SpriteIcon sprite={851986} size={52} />
            <p>
              {g.phase === "ready"
                ? "Wait as long as you need."
                : g.phase === "casting"
                  ? "The net is sweeping down…"
                  : `${g.lastCatch} caught this cast.`}
            </p>
            <button
              className="primary-button"
              disabled={g.phase !== "ready" || !!s.panel}
              onClick={s.castNet}
            >
              <Fish size={16} />
              {g.phase === "ready"
                ? "Cast net"
                : g.phase === "casting"
                  ? "Net in the water…"
                  : "Hauling the catch…"}
            </button>
            <small>Potato bait ×1 · net kept</small>
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
            aria-label="Pause net fishing"
            onClick={() => s.setPanel("settings")}
          >
            <Pause size={14} />
            Pause
          </button>
          <button onClick={s.leaveNetFishing}>
            <X size={14} />
            {done ? "Return to island" : "Leave fishing"}
          </button>
        </div>
      </div>
      {done && (
        <section className="coconut-result" role="status">
          <SpriteIcon
            sprite={reward && reward.sprite >= 0 ? reward.sprite : 851986}
            size={65}
          />
          <p className="eyebrow">
            {g.status === "won"
              ? "A PATIENT HAND IS REWARDED"
              : "THE NEXT SHOAL WILL COME"}
          </p>
          <h3>{s.story[g.status === "won" ? 86 : 83]}</h3>
          <p>
            {reward
              ? `${reward.name} ×1 added to your satchel.`
              : `${g.caught} fish caught. You needed ten.`}
          </p>
          {g.reward === 60 && (
            <small>
              The supplied game's reward table grants Rug here. Its original
              icon is unavailable.
            </small>
          )}
          <small>
            Potato ×1 used · Net kept
            <br />
            {s.survival.enabled
              ? "10 energy used · +1 strength"
              : "Survival paused"}
          </small>
          <button
            ref={finish}
            className="primary-button"
            onClick={s.leaveNetFishing}
          >
            Return to the island <ArrowUpRight size={16} />
          </button>
        </section>
      )}
    </div>
  );
}
