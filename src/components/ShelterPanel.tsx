import { ArrowUpRight, Hammer, MapPin, Moon } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import { SpriteIcon } from "./SpriteIcon";
import { FURNISHING_IDS } from "../game/systems/HouseSystem";

export function CampOverview() {
  const s = useGameStore(),
    camp = s.items[s.shelter];
  if (!camp) return null;
  const index = s.worldObjects?.indexOf(18) ?? -1;
  return (
    <aside className="camp-overview">
      <SpriteIcon sprite={camp.sprite} size={68} />
      <div>
        <p className="eyebrow">YOUR ISLAND CAMP</p>
        <h3>{camp.name}</h3>
        <p>
          {s.shelter === 17
            ? "Build a tent to unlock rope, arrows, bows and nets."
            : s.shelter === 9
              ? "Tent recipes unlocked. A cabin opens the next set of recipes."
              : "Cabin recipes unlocked, including weaving equipment."}
        </p>
        {index >= 0 && (
          <button className="camp-map-link" onClick={() => s.setPanel("map")}>
            <MapPin size={13} /> Camp at {index % 180},{" "}
            {Math.floor(index / 180)} · view map
          </button>
        )}
        <button
          className="camp-map-link house-link"
          onClick={() => s.setPanel("house")}
        >
          House storage ·{" "}
          {FURNISHING_IDS.filter((id) => (s.furnishings[id] ?? 0) > 0).length}{" "}
          furnishings
        </button>
      </div>
    </aside>
  );
}

export function ShelterDialogue() {
  const s = useGameStore(),
    item = s.items[s.shelter];
  if (!item) return null;
  return (
    <div className="discovery">
      <div className="discovery-art">
        <SpriteIcon sprite={item.sprite} size={100} />
        <span>YOUR ISLAND CAMP</span>
      </div>
      <h3>{item.name}</h3>
      <blockquote>“{item.description}”</blockquote>
      <p className="observation-credit">— Robinson’s original observation</p>
      <p className="panel-intro">
        {s.shelter === 17
          ? "Turn this camp into a home. The tent needs three branches, a sail and your knife."
          : s.shelter === 9
            ? "Your tent opens more recipes. Upgrade this same camp to a cabin when you have the materials and tools."
            : "Your cabin opens the full camp recipe tier."}
      </p>
      <div className="discovery-actions">
        <button
          className="primary-button"
          onClick={() => s.setPanel("crafting")}
        >
          <Hammer size={17} />
          {s.shelter === 10 ? "Open workbench" : "Build & upgrade"}
        </button>
        <button className="secondary-button" onClick={s.beginRest}>
          <Moon size={16} /> Take a breather
        </button>
        <button
          className="secondary-button"
          onClick={() => s.setPanel("house")}
        >
          House storage
        </button>
        <button className="secondary-button" onClick={() => s.setPanel(null)}>
          Return to the island <ArrowUpRight size={16} />
        </button>
      </div>
      <p className="muted">
        A breather restores energy after ten seconds. For recovery while away,
        choose Sleep from settings.
      </p>
    </div>
  );
}
