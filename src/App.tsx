import { useEffect } from "react";
import {
  Anchor,
  Compass,
  Sun,
  CloudSun,
  Backpack,
  Hammer,
  BookOpen,
  Map,
  Settings2,
  Save,
  ArrowUpRight,
  Check,
  Droplets,
  Utensils,
  Zap,
  Minus,
  Plus,
  Maximize2,
  ChevronRight,
  Volume2,
  VolumeX,
  MoveUpRight,
  X,
} from "lucide-react";
import { GameCanvas } from "./components/GameCanvas";
import { PanelOverlay } from "./components/Panels";
import { IslandMap } from "./components/IslandMap";
import { useGameStore, type Panel } from "./store/gameStore";
import { useAmbience } from "./hooks/useAmbience";
import { SpriteIcon } from "./components/SpriteIcon";
const toolbar: {
  panel: Panel;
  name: string;
  key: string;
  icon: typeof Backpack;
}[] = [
  { panel: "inventory", name: "Satchel", key: "I", icon: Backpack },
  { panel: "crafting", name: "Craft", key: "C", icon: Hammer },
  { panel: "journal", name: "Journal", key: "J", icon: BookOpen },
  { panel: "map", name: "Island map", key: "M", icon: Map },
];
export default function App() {
  const s = useGameStore();
  useAmbience(s.ambience && s.started);
  useEffect(() => {
    const keydown = (e: KeyboardEvent) => {
      if (e.repeat || e.ctrlKey || e.metaKey || e.altKey) return;
      const key = e.key.toLowerCase();
      if (key === "escape") {
        e.preventDefault();
        useGameStore.setState((t) => ({ panel: t.panel ? null : "settings" }));
        return;
      }
      const mapping: Record<string, Panel> = {
        i: "inventory",
        c: "crafting",
        j: "journal",
        m: "map",
      };
      if (key in mapping) {
        e.preventDefault();
        useGameStore.getState().setPanel(mapping[key]);
      }
    };
    window.addEventListener("keydown", keydown);
    return () => window.removeEventListener("keydown", keydown);
  }, []);
  const minutes = 8 * 60 + Math.floor(s.elapsed / 3),
    day = 1 + Math.floor(minutes / 1440),
    clock = `${String(Math.floor(minutes / 60) % 24).padStart(2, "0")}:${String(minutes % 60).padStart(2, "0")}`;
  const goals = [
    { text: "Explore the shore", done: s.discovered.length > 200 },
    { text: "Examine an island object", done: s.inspected.length > 0 },
    { text: "Gather your first supplies", done: s.collected.length >= 3 },
  ];
  const begin = () => useGameStore.setState({ started: true });
  const fullscreen = () => {
    const host = document.querySelector(".game-shell");
    if (!document.fullscreenElement)
      void host
        ?.requestFullscreen()
        .catch(() => s.notify("Fullscreen is unavailable in this browser."));
    else void document.exitFullscreen();
  };
  return (
    <div className="app">
      <header className="topbar">
        <a
          className="brand"
          href="#"
          onClick={(e) => e.preventDefault()}
          aria-label="Robinson Crusoe"
        >
          <span className="brand-emblem">
            <Compass size={31} strokeWidth={1.1} />
          </span>
          <span>
            <strong>ROBINSON CRUSOE</strong>
            <small>THE ISLAND JOURNAL</small>
          </span>
        </a>
        <div className="top-chapter">
          <span className="chapter-diamond">◆</span>
          <span>
            CHAPTER I <i /> THE SHIPWRECK
          </span>
        </div>
        <div className="top-actions">
          <span className="exploration-label">
            <span className="live-dot" /> EXPLORATION BUILD
          </span>
          <button
            className="icon-button"
            aria-label="Save expedition"
            disabled={!s.ready || s.saving}
            onClick={s.save}
          >
            <Save size={19} />
          </button>
          <button
            className="icon-button"
            aria-label="Settings and pause"
            onClick={() => s.setPanel("settings")}
          >
            <Settings2 size={20} />
          </button>
        </div>
      </header>
      <main className="main-layout">
        <section className="adventure-column">
          <div className="world-heading">
            <div>
              <span className="eyebrow">AN UNCHARTED TROPICAL ISLAND</span>
              <h1>A world beyond the wreck.</h1>
            </div>
            <div className="world-time">
              <Sun size={22} />
              <div>
                <strong>Day {String(day).padStart(2, "0")}</strong>
                <small>
                  {clock} <i /> Clear skies
                </small>
              </div>
            </div>
          </div>
          <div className="game-shell">
            <GameCanvas />
            <div className="canvas-vignette" />
            {s.enhancements && (
              <div
                className="dusk-overlay"
                style={{
                  opacity:
                    Math.max(
                      0,
                      Math.sin(
                        (((minutes % 1440) - 1020) / 1440) * Math.PI * 2,
                      ),
                    ) * 0.3,
                }}
              />
            )}
            <div className="location-plaque">
              <span className="location-pin">
                <Anchor size={17} />
              </span>
              <div>
                <strong>
                  {s.x < 1000 && s.y > 1800
                    ? "The western shore"
                    : s.y < 900
                      ? "The northern wilds"
                      : "The island interior"}
                </strong>
                <span>
                  {s.x < 1000 && s.y > 1800
                    ? "WRECKAGE & SECOND CHANCES"
                    : "A LITTLE FURTHER FROM HOME"}
                </span>
              </div>
            </div>
            <div className="world-compass" aria-hidden="true">
              <span>N</span>
              <Compass size={45} strokeWidth={0.8} />
            </div>
            <div className="condition-plaque">
              <p>
                YOUR CONDITION <span>NEEDS PAUSED</span>
              </p>
              {[
                { icon: Utensils, name: "Hunger", color: "#d9b66e" },
                { icon: Droplets, name: "Thirst", color: "#83bdc2" },
                { icon: Zap, name: "Energy", color: "#aac094" },
              ].map(({ icon: Icon, name, color }) => (
                <div className="condition-line" key={name}>
                  <Icon size={14} style={{ color }} />
                  <span>{name}</span>
                  <div className="condition-track">
                    <i style={{ background: color }} />
                  </div>
                  <small>100</small>
                </div>
              ))}
            </div>
            <div className="zoom-control">
              <button
                aria-label="Zoom in"
                disabled={s.zoom >= 4}
                onClick={() =>
                  useGameStore.setState({ zoom: Math.min(4, s.zoom + 0.25) })
                }
              >
                <Plus size={17} />
              </button>
              <i />
              <button
                aria-label="Zoom out"
                disabled={s.zoom <= 1.5}
                onClick={() =>
                  useGameStore.setState({ zoom: Math.max(1.5, s.zoom - 0.25) })
                }
              >
                <Minus size={17} />
              </button>
              <i />
              <button aria-label="Fullscreen game" onClick={fullscreen}>
                <Maximize2 size={15} />
              </button>
            </div>
            {!s.ready && !s.error && (
              <div className="loading-screen">
                <Compass size={42} strokeWidth={1} />
                <h2>Charting the island…</h2>
                <p>Recovering a world, one shore at a time.</p>
              </div>
            )}
            {s.error && (
              <div className="loading-screen">
                <h2>The island is out of reach.</h2>
                <p>{s.error}</p>
                <button
                  className="primary-button"
                  onClick={() => window.location.reload()}
                >
                  Try again
                </button>
              </div>
            )}
            {s.ready && !s.started && (
              <div className="welcome-overlay">
                <div className="welcome-card">
                  <Anchor size={28} strokeWidth={1} />
                  <p className="eyebrow">THE ADVENTURE BEGINS AGAIN</p>
                  <h2>
                    Washed ashore.
                    <br />
                    Far from ordinary.
                  </h2>
                  <p>
                    The sea took everything.
                    <br />
                    The island has a story to tell.
                  </p>
                  <button className="primary-button" onClick={begin}>
                    Begin expedition <ArrowUpRight size={17} />
                  </button>
                  <button className="continue-button" onClick={s.load}>
                    Continue a saved story
                  </button>
                </div>
              </div>
            )}
            {s.focus && s.started && !s.panel && (
              <button
                className="interaction-prompt"
                onClick={() => s.inspect(s.focus!.index, s.focus!.itemId)}
              >
                <kbd>E</kbd>
                <span>
                  Examine <b>{s.items[s.focus.itemId]?.name}</b>
                </span>
                <ChevronRight size={17} />
              </button>
            )}
            {s.help && s.started && (
              <div className="control-note">
                <span>
                  <kbd>W</kbd>
                  <span>
                    <kbd>A</kbd>
                    <kbd>S</kbd>
                    <kbd>D</kbd>
                  </span>
                </span>
                <p>
                  Make your own way.
                  <small>WASD / arrows to move · click to walk</small>
                </p>
                <button
                  aria-label="Dismiss movement help"
                  onClick={() => useGameStore.setState({ help: false })}
                >
                  <X size={14} />
                </button>
              </div>
            )}
            {s.notification && (
              <div className="toast" role="status">
                <Check size={17} />
                {s.notification}
              </div>
            )}
            <div className="game-bottom-line">
              <span>
                <span className="live-dot" />{" "}
                {s.panel
                  ? "EXPEDITION PAUSED"
                  : s.started
                    ? "EXPLORING THE ISLAND"
                    : "A NEW CHAPTER AWAITS"}
              </span>
              <span>
                {Math.floor(s.x / 16)} E <i /> {Math.floor(s.y / 16)} S
              </span>
            </div>
          </div>
          <nav className="game-toolbar" aria-label="Game tools">
            <span className="toolbar-flourish">✦</span>
            <div className="toolbar-items">
              {toolbar.map(({ panel, name, key, icon: Icon }) => (
                <button
                  key={name}
                  disabled={!s.ready}
                  className={s.panel === panel ? "active" : ""}
                  onClick={() => s.setPanel(panel)}
                >
                  <Icon size={21} strokeWidth={1.5} />
                  <span>{name}</span>
                  <kbd>{key}</kbd>
                </button>
              ))}
            </div>
            <div className="toolbar-right">
              <button
                className="icon-button"
                aria-label={s.ambience ? "Mute ambience" : "Enable ambience"}
                onClick={() => useGameStore.setState({ ambience: !s.ambience })}
              >
                {s.ambience ? <Volume2 size={19} /> : <VolumeX size={19} />}
              </button>
              <button
                className="pause-button"
                onClick={() => s.setPanel("settings")}
              >
                Pause <kbd>esc</kbd>
              </button>
            </div>
          </nav>
        </section>
        <aside className="journal-sidebar">
          <div className="journal-tab">
            <BookOpen size={16} />
            <span>THE EXPEDITION</span>
            <span>01</span>
          </div>
          <div className="sidebar-story">
            <div className="ink-anchor">
              <Anchor size={29} strokeWidth={1} />
            </div>
            <p className="eyebrow">THE FIRST DAY</p>
            <h2>
              A strange
              <br />
              shore.
            </h2>
            <div className="tiny-rule" />
            <p className="story-excerpt">
              “Giving up is not an option.
              <br />I must survive!”
            </p>
            <p className="story-context">
              The Amber Queen is gone. Before you lies an unfamiliar island, and
              the beginning of another story.
            </p>
            <button
              className="text-button"
              onClick={() => s.setPanel("journal")}
            >
              Open your journal <ArrowUpRight size={15} />
            </button>
          </div>
          <div className="sidebar-goals">
            <p className="eyebrow">
              FIRST THINGS FIRST{" "}
              <span>{goals.filter((g) => g.done).length}/3</span>
            </p>
            {goals.map((g, i) => (
              <div
                className={`sidebar-goal ${g.done ? "done" : ""}`}
                key={g.text}
              >
                <span>
                  {g.done ? (
                    <Check size={13} />
                  ) : (
                    String(i + 1).padStart(2, "0")
                  )}
                </span>
                <p>{g.text}</p>
              </div>
            ))}
          </div>
          <button
            className="mini-map-button"
            onClick={() => s.setPanel("map")}
            aria-label="Open island map"
          >
            <div className="mini-map-heading">
              <span>YOUR LITTLE CORNER</span>
              <MoveUpRight size={15} />
            </div>
            <IslandMap />
            <div className="mini-map-footer">
              <span>The island is bigger than you think.</span>
              <small>
                <kbd>M</kbd> EXPLORE MAP
              </small>
            </div>
          </button>
          <div className="satchel-summary">
            <div>
              <Backpack size={21} strokeWidth={1.4} />
              <span>
                Your satchel
                <small>
                  {Object.values(s.inventory).reduce((a, b) => a + b, 0)} items
                  gathered
                </small>
              </span>
            </div>
            <div className="satchel-preview">
              {Object.entries(s.inventory)
                .filter(([, n]) => n > 0)
                .slice(0, 3)
                .map(([id]) => (
                  <SpriteIcon
                    key={id}
                    sprite={s.items[+id]?.sprite ?? -1}
                    size={21}
                  />
                ))}
              <button
                aria-label="Open satchel"
                onClick={() => s.setPanel("inventory")}
              >
                <ChevronRight size={17} />
              </button>
            </div>
          </div>
        </aside>
      </main>
      <footer className="page-footer">
        <span>
          <Compass size={12} /> NOT ALL WHO ARE LOST REMAIN SO.
        </span>
        <span>
          {s.offlineReady
            ? "Island assets cached"
            : "A personal reconstruction"}{" "}
          <i /> Original game & artwork by Exkee
        </span>
        <button onClick={() => s.setPanel("settings")}>
          Field notes <ArrowUpRight size={11} />
        </button>
      </footer>
      <PanelOverlay />
    </div>
  );
}
