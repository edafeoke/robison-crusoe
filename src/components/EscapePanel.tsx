import { useEffect, useRef } from "react";
import { Anchor, Save, Compass } from "lucide-react";
import { shipRules as R } from "../game/data/shipRules";
import { useGameStore } from "../store/gameStore";
export function DepartureStatus() {
  const s = useGameStore();
  if (!s.started || s.ship.phase !== "departing") return null;
  return (
    <div className="departure-status" role="status">
      <Anchor size={21} />
      <span>
        <strong>The voyage home</strong>
        <small>
          The ship is rebuilt ·{" "}
          {Math.max(
            0,
            Math.ceil((R.strictDepartureMs - s.ship.elapsedMs) / 1000),
          )}
          s
        </small>
      </span>
      <button onClick={() => s.setPanel("settings")}>Pause</button>
    </div>
  );
}
export function EscapeResult() {
  const s = useGameStore(),
    root = useRef<HTMLElement>(null),
    open = s.started && s.ship.phase === "escaped";
  useEffect(() => {
    if (!open) return;
    const dialog = root.current!,
      controls = () => [
        ...dialog.querySelectorAll<HTMLButtonElement>("button:not(:disabled)"),
      ];
    controls()[0]?.focus();
    const key = (e: KeyboardEvent) => {
      if (e.key !== "Tab") return;
      const buttons = controls(),
        first = buttons[0],
        last = buttons.at(-1);
      if (e.shiftKey && document.activeElement === first) {
        e.preventDefault();
        last?.focus();
      } else if (!e.shiftKey && document.activeElement === last) {
        e.preventDefault();
        first?.focus();
      }
    };
    dialog.addEventListener("keydown", key);
    return () => dialog.removeEventListener("keydown", key);
  }, [open]);
  if (!open) return null;
  return (
    <div className="modal-backdrop escape-result">
      <section
        ref={root}
        className="modal"
        role="dialog"
        aria-modal="true"
        aria-labelledby="escape-title"
      >
        <div className="modal-body loss-body">
          <p className="eyebrow">A COURSE FOR HOME</p>
          <Anchor className="escape-emblem" size={44} strokeWidth={1} />
          <h2 id="escape-title">{s.story[R.winText] ?? "You've won!"}</h2>
          <p>
            {s.story[R.endingText] ??
              "The pirate ship is repaired. Robinson can leave the island."}
          </p>
          <button
            className="primary-button"
            onClick={s.save}
            disabled={s.saving}
          >
            <Save size={16} />
            Save completed expedition
          </button>
          <button
            className="secondary-button"
            onClick={s.beginExpedition}
            disabled={s.saving}
          >
            <Compass size={16} />
            Begin another expedition
          </button>
          <button
            className="text-button"
            disabled={s.saving}
            onClick={() =>
              useGameStore.setState({
                started: false,
                panel: null,
                focus: null,
              })
            }
          >
            Return to title
          </button>
          <small>{s.saveMessage}</small>
          <small>Your earlier save stays until you choose to save again.</small>
        </div>
      </section>
    </div>
  );
}
