import { Anchor, Hammer, Compass } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import { CollisionSystem } from "../game/systems/CollisionSystem";
import {
  raftAvailability,
  raftTarget,
  boardingPoint,
  landingPoint,
} from "../game/systems/RaftSystem";
import { raftRules as R } from "../game/data/raftRules";
export function RaftDialogue() {
  const s = useGameStore(),
    aboard = s.raft.phase === "aboard",
    broken = s.raft.phase === "broken";
  const item = s.items[broken ? R.brokenItem : R.item],
    recipe = s.items[R.item];
  const collision =
    s.worldMap && s.worldObjects
      ? new CollisionSystem(s.worldMap, new Set(s.collected), s.worldObjects)
      : null;
  const target =
    s.focus && raftTarget(s.focus.index, s.x, s.y, s.worldObjects, s.raft);
  const board =
    collision && target && boardingPoint(s.raft, s.x, s.y, collision);
  const land = collision && landingPoint(s.raft, s.x, s.y, collision);
  const locked =
    !s.ready ||
    !s.started ||
    s.saving ||
    !!s.survival.death ||
    s.survival.restRemainingMs !== null ||
    s.ship.phase !== "wreck";
  return (
    <div className="discovery raft-discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={item.sprite} size={110} />
        <span>THE MANGROVE PASSAGE</span>
      </div>
      <h3>{item.name}</h3>
      <blockquote>“{item.description}”</blockquote>
      <p className="observation-credit">— Robinson’s original observation</p>
      {broken ? (
        <>
          <p className="journal-prose">
            Repair the raft to cross the mangrove swamps.
          </p>
          <h4 className="ship-requirements-title">
            Materials · used in repair
          </h4>
          <div className="ship-requirements">
            {recipe.recipe.map((r) => (
              <div key={r.item}>
                <SpriteIcon sprite={s.items[r.item].sprite} size={26} />
                <span>{s.items[r.item].name}</span>
                <strong>
                  {s.inventory[r.item] ?? 0}/{r.quantity}
                </strong>
              </div>
            ))}
          </div>
          <h4 className="ship-requirements-title">
            Equipment · kept after repair
          </h4>
          <div className="ship-requirements">
            {recipe.craftingTools.map((r) => (
              <div key={r.item}>
                <SpriteIcon sprite={s.items[r.item].sprite} size={26} />
                <span>{s.items[r.item].name}</span>
                <strong>
                  {s.inventory[r.item] ?? 0}/{r.quantity}
                </strong>
              </div>
            ))}
          </div>
          <p className="muted">Repair uses 30 Energy and restores 10 Morale.</p>
          <button
            className="primary-button"
            disabled={
              locked ||
              !target ||
              !raftAvailability(s.items, s.inventory, s.raft)
            }
            onClick={() => s.repairRaft(s.focus!.index)}
          >
            <Hammer size={17} />
            Repair the raft
          </button>
        </>
      ) : aboard ? (
        <>
          <p className="journal-prose">
            Paddle with WASD, arrow keys, or click a clear channel. Hunger,
            thirst and wildlife remain active.
          </p>
          <p className="muted">
            {land
              ? "A clear shore is within reach. Your raft will remain at this water’s edge."
              : "Find a clear shore beside the raft to land."}
          </p>
          <button
            className="primary-button"
            disabled={locked || !land}
            onClick={s.landRaft}
          >
            <Anchor size={17} />
            Land on shore
          </button>
        </>
      ) : (
        <>
          <p className="journal-prose">
            The repaired raft is ready. Step directly beside it to board.
          </p>
          <button
            className="primary-button"
            disabled={locked || !board}
            onClick={() => s.boardRaft(s.raft.index!)}
          >
            <Compass size={17} />
            Board the raft
          </button>
          {!board && (
            <p className="muted">
              Approach from the tile immediately above, below, left or right of
              the raft.
            </p>
          )}
        </>
      )}
    </div>
  );
}
export function RaftStatus() {
  const s = useGameStore();
  if (!s.started || s.raft.phase !== "aboard") return null;
  return (
    <div className="raft-status">
      <Anchor size={18} />
      <span>
        <strong>Through the mangroves</strong>
        <small>Paddle · WASD / arrows / click</small>
      </span>
      <button
        disabled={!!s.survival.death || s.survival.restRemainingMs !== null}
        onClick={() =>
          useGameStore.setState({
            panel: "dialogue",
            selected: 94,
            focus: {
              index: Math.floor(s.x / 16) + Math.floor(s.y / 16) * 180,
              itemId: 94,
              x: s.x,
              y: s.y,
              distance: 0,
              kind: "raft",
            },
          })
        }
      >
        Find a landing
      </button>
    </div>
  );
}
