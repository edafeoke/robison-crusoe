import { useState } from "react";
import { Hammer, Check } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import {
  clothingSlots,
  clothingLayers,
  clothingProfile,
  huntingDamage,
  type Facing,
} from "../game/systems/ClothingSystem";
import { clothingRules as R } from "../game/data/clothingRules";
import { assetManifest } from "../game/assets/assetManifest";
import { SpriteIcon } from "./SpriteIcon";
export function OutfitPreview() {
  const s = useGameStore(),
    [facing, setFacing] = useState<Facing>("down");
  const commands = [
    {
      sprite: 196608 + assetManifest.player.sequences[facing][0],
      dx: 0,
      dy: 0,
    },
    ...clothingLayers(s.clothing.equipped, facing, 0),
  ];
  return (
    <div className="outfit-preview">
      <div
        className="outfit-portrait"
        role="img"
        aria-label={`Robinson wearing the selected outfit, facing ${facing}`}
      >
        {commands.map((c, i) => {
          const sheet = s.atlas?.sheets[c.sprite >>> 16],
            f = sheet?.frames[c.sprite & 65535];
          if (!sheet || !f) return null;
          return (
            <span
              key={i}
              style={{
                position: "absolute",
                left: 72 + (c.dx - f.anchorX) * 3,
                top: 136 + (c.dy - f.anchorY) * 3,
                width: f.width * 3,
                height: f.height * 3,
                backgroundImage: `url(${sheet.url})`,
                backgroundSize: `${sheet.width * 3}px ${sheet.height * 3}px`,
                backgroundPosition: `-${f.x * 3}px -${f.y * 3}px`,
                transform: f.flipX ? "scaleX(-1)" : undefined,
                imageRendering: "pixelated",
              }}
            />
          );
        })}
      </div>
      <div
        className="outfit-directions"
        role="group"
        aria-label="Outfit preview direction"
      >
        {(["down", "left", "up", "right"] as Facing[]).map((d) => (
          <button
            key={d}
            className={facing === d ? "selected" : ""}
            aria-pressed={facing === d}
            onClick={() => setFacing(d)}
          >
            {d}
          </button>
        ))}
      </div>
    </div>
  );
}
export function WardrobePanel() {
  const s = useGameStore();
  return (
    <>
      <p className="panel-intro">
        A little comfort for the journey. Choose what Robinson wears; your other
        clothes stay in the wardrobe.
      </p>
      <OutfitPreview />
      <div className="wardrobe-slots">
        {clothingSlots.map((slot) => (
          <section className="wardrobe-slot" key={slot}>
            <h3>
              {slot === "pants"
                ? "Pants"
                : slot[0].toUpperCase() + slot.slice(1)}
            </h3>
            <div className="wardrobe-choices">
              <button
                className={s.clothing.equipped[slot] === null ? "selected" : ""}
                aria-pressed={s.clothing.equipped[slot] === null}
                onClick={() => s.equip(slot, null)}
              >
                No {slot}
              </button>
              {s.clothing.owned
                .filter((id) => clothingProfile(id)?.slot === slot)
                .map((id) => (
                  <button
                    key={id}
                    className={
                      s.clothing.equipped[slot] === id ? "selected" : ""
                    }
                    aria-pressed={s.clothing.equipped[slot] === id}
                    onClick={() => s.equip(slot, id)}
                  >
                    <SpriteIcon sprite={s.items[id].sprite} size={32} />
                    {s.items[id].name}
                    {s.clothing.equipped[slot] === id && <Check size={12} />}
                  </button>
                ))}
            </div>
          </section>
        ))}
      </div>
      <button
        className="secondary-button"
        onClick={() => s.setPanel("crafting")}
      >
        <Hammer size={15} /> Make clothes
      </button>
      <h3 className="wardrobe-protection-title">Hunting protection</h3>
      <p className="muted">
        The matching clothing slot halves retaliation after a failed hunt. These
        attacks still cost energy.
      </p>
      <table className="protection-table">
        <thead>
          <tr>
            <th>Animal</th>
            <th>Protective slot</th>
            <th>Current attack cost</th>
          </tr>
        </thead>
        <tbody>
          {R.protection.map((p) => (
            <tr key={p.item}>
              <td>{s.items[p.item]?.name}</td>
              <td>{p.slot}</td>
              <td>{huntingDamage(p.item, s.clothing.equipped)} energy</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p className="muted">
        The same protection applies to nearby predators on the island. Sharks
        exhaust energy instantly, regardless of clothing.
      </p>
    </>
  );
}
