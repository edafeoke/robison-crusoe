import { useEffect, useRef, useState } from "react";
import {
  X,
  ArrowUpRight,
  Check,
  LockKeyhole,
  ChevronRight,
  Backpack,
  Hammer,
  BookOpen,
  Map,
  Settings2,
  Save,
  RotateCcw,
  Volume2,
  Sun,
  Minus,
  Plus,
  Trash2,
  Leaf,
  Compass,
  Anchor,
} from "lucide-react";
import { useGameStore, type Panel } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import { IslandMap } from "./IslandMap";
import { canPickup, pickupRule, categoryName } from "../game/systems/ItemRules";
import { CraftingPanel } from "./CraftingPanel";
import { ItemUseControls } from "./ItemUseControls";
import { SurvivalSettings } from "./SurvivalPanel";
import { HuntingControls } from "./HuntingPanel";
import { RodFishingDialogue } from "./RodFishingPanel";
import { NetFishingDialogue } from "./NetFishingPanel";
import { CoconutPalmDialogue } from "./CoconutGamePanel";
const titles: Record<
  Exclude<Panel, null>,
  { eyebrow: string; title: string; icon: typeof Backpack }
> = {
  inventory: {
    eyebrow: "EVERYTHING YOU CARRY",
    title: "Your satchel",
    icon: Backpack,
  },
  crafting: {
    eyebrow: "MAKE SOMETHING USEFUL",
    title: "The workbench",
    icon: Hammer,
  },
  journal: {
    eyebrow: "NOTES FROM AN UNKNOWN ISLAND",
    title: "Expedition journal",
    icon: BookOpen,
  },
  map: {
    eyebrow: "A WORLD WAITING TO BE EXPLORED",
    title: "Chart of the island",
    icon: Map,
  },
  settings: {
    eyebrow: "MAKE YOURSELF AT HOME",
    title: "Around the campfire",
    icon: Settings2,
  },
  dialogue: {
    eyebrow: "A CLOSER LOOK",
    title: "An island discovery",
    icon: Leaf,
  },
};
export function PanelOverlay() {
  const s = useGameStore(),
    root = useRef<HTMLDivElement>(null),
    close = () => useGameStore.setState({ panel: null });
  useEffect(() => {
    if (!s.panel) return;
    const previous = document.activeElement as HTMLElement;
    const host = root.current;
    host?.querySelector<HTMLElement>("button")?.focus();
    const handle = (event: KeyboardEvent) => {
      if (event.key !== "Tab") return;
      const focusable = Array.from(
        host?.querySelectorAll<HTMLElement>(
          'button:not(:disabled),input,a[href],[tabindex="0"]',
        ) ?? [],
      );
      const first = focusable[0],
        last = focusable.at(-1);
      if (event.shiftKey && document.activeElement === first) {
        event.preventDefault();
        last?.focus();
      } else if (!event.shiftKey && document.activeElement === last) {
        event.preventDefault();
        first?.focus();
      }
    };
    document.addEventListener("keydown", handle);
    return () => {
      document.removeEventListener("keydown", handle);
      previous?.focus();
    };
  }, [s.panel]);
  if (!s.panel) return null;
  const info = titles[s.panel],
    Icon = info.icon;
  return (
    <div
      className="modal-backdrop"
      onMouseDown={(e) => {
        if (e.target === e.currentTarget) close();
      }}
    >
      <section
        className={`modal modal-${s.panel}`}
        role="dialog"
        aria-modal="true"
        aria-labelledby="panel-title"
        ref={root}
      >
        <header className="modal-header">
          <div className="modal-mark">
            <Icon size={22} />
          </div>
          <div>
            <p className="eyebrow">{info.eyebrow}</p>
            <h2 id="panel-title">{info.title}</h2>
          </div>
          <button
            className="icon-button close-button"
            onClick={close}
            aria-label="Close panel"
          >
            <X size={20} />
          </button>
        </header>
        <div className="modal-body">
          {s.panel === "inventory" ? (
            <InventoryPanel />
          ) : s.panel === "crafting" ? (
            <CraftingPanel />
          ) : s.panel === "journal" ? (
            <QuestJournal />
          ) : s.panel === "map" ? (
            <MapPanel />
          ) : s.panel === "settings" ? (
            <PauseMenu />
          ) : (
            <Dialogue />
          )}
        </div>
        <footer className="modal-footer">
          <span>
            <span className="live-dot" /> Your expedition is paused
          </span>
          <span>
            <kbd>esc</kbd> return to the island
          </span>
        </footer>
      </section>
    </div>
  );
}
export function InventoryPanel() {
  const s = useGameStore(),
    entries = Object.entries(s.inventory).filter(([, n]) => n > 0),
    [selected, setSelected] = useState<number | null>(null),
    [category, setCategory] = useState("All"),
    item = selected === null ? null : s.items[selected];
  return (
    <>
      <p className="panel-intro">
        A few things washed ashore. Others are waiting to be found.
      </p>
      {!!entries.length && (
        <div
          className="panel-filters"
          role="group"
          aria-label="Inventory categories"
        >
          {[
            "All",
            ...new Set(
              entries.map(([id]) => categoryName(s.items[+id].category)),
            ),
          ].map((name) => (
            <button
              key={name}
              className={category === name ? "selected" : ""}
              onClick={() => {
                setCategory(name);
                setSelected(null);
              }}
            >
              {name}
            </button>
          ))}
        </div>
      )}
      {entries.length ? (
        <div className="inventory-layout">
          <div className="inventory-grid">
            {entries
              .filter(
                ([id]) =>
                  category === "All" ||
                  categoryName(s.items[+id].category) === category,
              )
              .map(([id, n]) => {
                const item = s.items[+id];
                return (
                  <button
                    className={`inventory-slot ${selected === +id ? "selected" : ""}`}
                    key={id}
                    onClick={() => setSelected(+id)}
                  >
                    <SpriteIcon sprite={item.sprite} size={46} />
                    <strong>{item.name}</strong>
                    <span className="quantity">×{n}</span>
                  </button>
                );
              })}
            {Array.from(
              { length: Math.max(0, 12 - entries.length) },
              (_, i) => (
                <div className="inventory-slot empty-slot" key={`empty-${i}`}>
                  +
                </div>
              ),
            )}
          </div>
          <aside className="item-detail">
            {item ? (
              <>
                <SpriteIcon sprite={item.sprite} size={64} />
                <p className="eyebrow">FROM THE ISLAND</p>
                <h3>{item.name}</h3>
                <p className="journal-prose">{item.description}</p>
                <ItemUseControls item={item} />
                <button
                  className="text-button danger"
                  onClick={() => s.discard(item.id)}
                  disabled={!s.inventory[item.id]}
                >
                  <Trash2 size={14} /> Discard one
                </button>
              </>
            ) : (
              <>
                <Backpack size={32} />
                <h3>Travel lightly.</h3>
                <p>Select an item to read Robinson’s original observation.</p>
              </>
            )}
          </aside>
        </div>
      ) : (
        <div className="empty-state">
          <Backpack size={40} />
          <h3>Every adventure starts with empty pockets.</h3>
          <p>
            Walk up to an object and press <kbd>E</kbd> to examine it. Gather
            loose resources for your first tools.
          </p>
          <button
            className="primary-button"
            onClick={() => useGameStore.setState({ panel: null })}
          >
            Return to the shore <ArrowUpRight size={16} />
          </button>
        </div>
      )}
    </>
  );
}
export function QuestJournal() {
  const s = useGameStore(),
    goals = [
      {
        title: "Take your first steps",
        text: "Explore the western shore.",
        done: s.discovered.length > 200,
      },
      {
        title: "Look a little closer",
        text: "Examine an object from the island.",
        done: s.inspected.length > 0,
      },
      {
        title: "Something worth keeping",
        text: "Gather three island finds.",
        done: s.collected.length >= 3,
      },
      {
        title: "A little ingenuity",
        text: "Craft an axe or a length of rope.",
        done: (s.inventory[5] ?? 0) + (s.inventory[91] ?? 0) > 0,
      },
    ];
  return (
    <>
      <div className="chapter-line">
        <span>CHAPTER I</span>
        <i />
        THE SHIPWRECK
      </div>
      <h3 className="journal-heading">A strange shore.</h3>
      <p className="journal-prose">{s.story[0]}</p>
      <div className="journal-rule">YOUR EXPLORATION GUIDE</div>
      <div className="journal-goals">
        {goals.map((g, i) => (
          <article className={g.done ? "done" : ""} key={g.title}>
            <span className="goal-number">
              {g.done ? <Check size={16} /> : String(i + 1).padStart(2, "0")}
            </span>
            <div>
              <h4>{g.title}</h4>
              <p>{g.text}</p>
            </div>
            {g.done && <span className="done-label">COMPLETE</span>}
          </article>
        ))}
      </div>
      <div className="field-note">
        <Compass size={20} />
        <p>
          This checklist guides the browser exploration build. The original
          story progression and boat escape are still being reconstructed.
        </p>
      </div>
      <details className="original-notes">
        <summary>
          Read recovered survival notes <ChevronRight size={15} />
        </summary>
        {[5, 8, 11, 14, 17, 20, 23, 26, 29, 30].map((i) => (
          <p key={i}>{s.story[i]}</p>
        ))}
      </details>
    </>
  );
}
export function MapPanel() {
  const s = useGameStore();
  return (
    <>
      <div className="map-caption">
        <span>
          <span className="live-dot" /> Robinson’s position
        </span>
        <span>
          {Math.floor(s.x / 16)}° E · {Math.floor(s.y / 16)}° S{" "}
          <small>(island grid)</small>
        </span>
      </div>
      <IslandMap large />
      <p className="map-legend">
        <Anchor size={15} /> Every shore, river and island follows the original
        map. Walk to discover what lies beyond the beach.
      </p>
    </>
  );
}
export function PauseMenu() {
  const s = useGameStore();
  return (
    <>
      <div className="settings-section">
        <h3>Your expedition</h3>
        <p>Your progress stays on this browser, on this device.</p>
        <div className="save-actions">
          <button
            className="primary-button"
            onClick={s.save}
            disabled={
              s.saving ||
              !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame)
            }
          >
            <Save size={16} />
            {s.saving ? "Saving…" : "Save expedition"}
          </button>
          <button
            className="secondary-button"
            onClick={s.load}
            disabled={
              !!(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame)
            }
          >
            <RotateCcw size={16} />
            Load saved expedition
          </button>
        </div>
        <small>{s.saveMessage}</small>
        {(s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame) && (
          <p className="muted">
            Return to the island before saving or loading.
          </p>
        )}
      </div>
      <div className="settings-section">
        <SurvivalSettings />
      </div>
      <div className="settings-section">
        <div className="setting-row">
          <div>
            <h4>
              <Compass size={17} />
              View distance
            </h4>
            <p>Find your own perspective on the island.</p>
          </div>
          <div className="zoom-buttons">
            <button
              className="icon-button"
              aria-label="Zoom out"
              disabled={s.zoom <= 1.5}
              onClick={() =>
                useGameStore.setState({ zoom: Math.max(1.5, s.zoom - 0.25) })
              }
            >
              <Minus size={16} />
            </button>
            <span>{s.zoom.toFixed(1)}×</span>
            <button
              className="icon-button"
              aria-label="Zoom in"
              disabled={s.zoom >= 4}
              onClick={() =>
                useGameStore.setState({ zoom: Math.min(4, s.zoom + 0.25) })
              }
            >
              <Plus size={16} />
            </button>
          </div>
        </div>
        <div className="setting-row">
          <div>
            <h4>
              <Volume2 size={17} />
              Ocean ambience
            </h4>
            <p>Locally synthesized surf · browser enhancement.</p>
          </div>
          <button
            className={`toggle ${s.ambience ? "on" : ""}`}
            role="switch"
            aria-checked={s.ambience}
            aria-label="Ocean ambience"
            onClick={() => useGameStore.setState({ ambience: !s.ambience })}
          >
            <span />
          </button>
        </div>
        <div className="setting-row">
          <div>
            <h4>
              <Sun size={17} />
              Atmospheric lighting
            </h4>
            <p>A gentle dusk overlay · browser enhancement.</p>
          </div>
          <button
            className={`toggle ${s.enhancements ? "on" : ""}`}
            role="switch"
            aria-checked={s.enhancements}
            aria-label="Atmospheric lighting"
            onClick={() =>
              useGameStore.setState({ enhancements: !s.enhancements })
            }
          >
            <span />
          </button>
        </div>
      </div>
      <div className="field-note">
        <BookOpen size={20} />
        <p>
          <b>A personal preservation project</b>
          <br />
          Original game and art © Exkee. This exploration build preserves the
          original island and sprites. Wildlife roams, flees and takes flight.
          Original reserve depletion, food effects and short rest are playable.
          Coconut throwing, fishing and bow hunting are playable. Clothing,
          construction, offline sleep and story progression are still in
          development. World-contact animal damage remains paused.
        </p>
      </div>
    </>
  );
}
export function Dialogue() {
  const s = useGameStore();
  if (s.focus?.kind === "rod-fishing") return <RodFishingDialogue />;
  if (s.focus?.kind === "net-fishing") return <NetFishingDialogue />;
  if (s.focus?.kind === "coconut-palm") return <CoconutPalmDialogue />;
  if (s.selected === null) return null;
  const item = s.items[s.selected],
    focus = s.focus,
    canCollect =
      focus?.itemId === item.id &&
      canPickup(item, s.inventory) &&
      !s.collected.includes(focus.index) &&
      Math.hypot(focus.x - s.x, focus.y - s.y) <= 44;
  const rule = pickupRule(item);
  return (
    <div className="discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={item.sprite} size={100} />
        <span>FOUND ON THE ISLAND</span>
      </div>
      <h3>{item.name}</h3>
      <blockquote>“{item.description}”</blockquote>
      <p className="observation-credit">— Robinson’s original observation</p>
      {focus?.itemId === item.id && focus.behavior && (
        <p className="muted">
          {focus.behavior} · Observe from a little distance.
        </p>
      )}
      {rule.kind === "collect" && rule.tool !== null && (
        <div className={`pickup-requirement ${canCollect ? "satisfied" : ""}`}>
          <SpriteIcon sprite={s.items[rule.tool].sprite} size={28} />
          <span>
            {canCollect ? "Using" : "Requires"} <b>{s.items[rule.tool].name}</b>
            <small>This tool is kept after gathering.</small>
          </span>
          {canCollect ? <Check size={16} /> : <LockKeyhole size={16} />}
        </div>
      )}
      <HuntingControls item={item} />
      <div className="discovery-actions">
        {canCollect && (
          <button
            className="primary-button"
            onClick={() => s.collect(focus!.index, item.id)}
          >
            <Backpack size={17} />
            Gather {item.name.toLowerCase()}
          </button>
        )}
        <button
          className="secondary-button"
          onClick={() => useGameStore.setState({ panel: null })}
        >
          Leave it here <ArrowUpRight size={16} />
        </button>
      </div>
      {canCollect &&
        item.interaction.useTool === -1 &&
        item.effects.energy < 0 && (
          <p className="muted">
            Gathering uses {Math.abs(item.effects.energy)} energy.
          </p>
        )}
      {!canCollect && (
        <p className="muted">
          {rule.kind === "special" && rule.reason === "hunting"
            ? "Bow hunting is available for supported animals. Use a net in shallow water or a rod in deep water to fish."
            : rule.kind === "collect" && rule.tool !== null
              ? "Return with the right tool to gather this resource."
              : "You’ve recorded this discovery in your journal."}
        </p>
      )}
    </div>
  );
}
