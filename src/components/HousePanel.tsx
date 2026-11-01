import { useGameStore } from "../store/gameStore";
import { FURNISHING_IDS } from "../game/systems/HouseSystem";
import { SpriteIcon } from "./SpriteIcon";
import { Hammer } from "lucide-react";
export function HousePanel() {
  const s = useGameStore();
  const owned = FURNISHING_IDS.filter((id) => (s.furnishings[id] ?? 0) > 0);
  return (
    <>
      <p className="panel-intro">
        The comforts you’ve made for your island home. These stay in your house
        when you explore.
      </p>
      {owned.length ? (
        <div className="house-list">
          {owned.map((id) => {
            const item = s.items[id];
            return (
              <article className="house-entry" key={id}>
                <SpriteIcon sprite={item.sprite} size={56} />
                <div>
                  <p className="eyebrow">AT HOME · ×{s.furnishings[id]}</p>
                  <h3>{item.name}</h3>
                  <blockquote>“{item.description}”</blockquote>
                </div>
              </article>
            );
          })}
        </div>
      ) : (
        <div className="empty-state">
          <Hammer size={32} />
          <h3>Make yourself at home.</h3>
          <p>
            Build a tent, then make a single bed. A cabin opens the remaining
            furnishings.
          </p>
        </div>
      )}
      <button className="primary-button" onClick={() => s.setPanel("crafting")}>
        <Hammer size={16} /> Make furnishings
      </button>
      <p className="muted">
        House storage lists your furnishings. Supplies and equipment stay in
        your satchel.
      </p>
    </>
  );
}
