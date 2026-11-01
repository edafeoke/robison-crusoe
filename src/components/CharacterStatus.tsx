import { useGameStore } from "../store/gameStore";
import { weightPercent } from "../game/systems/BodyWeightSystem";
import { SpriteIcon } from "./SpriteIcon";

export function CharacterStatus() {
  const s = useGameStore();
  const attributes = [
    { name: "Strength", value: s.survival.strength, sprite: 655396 },
    { name: "Morale", value: s.survival.morale, sprite: 655397 },
    { name: "Weight", value: weightPercent(s.bodyWeight), sprite: 655398 },
  ];
  return (
    <section className="character-status" aria-labelledby="attributes-title">
      <h3 id="attributes-title">Robinson’s condition</h3>
      <div className="attribute-list">
        {attributes.map((a) => (
          <div className="attribute-line" key={a.name}>
            <SpriteIcon sprite={a.sprite} size={22} />
            <span>{a.name}</span>
            <div
              className="attribute-track"
              role="meter"
              aria-label={a.name}
              aria-valuenow={Number(a.value.toFixed(1))}
              aria-valuemin={0}
              aria-valuemax={100}
            >
              <i style={{ width: `${a.value}%` }} />
            </div>
            <strong>{Number(a.value.toFixed(1))}%</strong>
          </div>
        ))}
      </div>
      <p>
        Meals gradually build body weight, even when your hunger is full. Weight
        doesn’t limit what you can carry.
      </p>
    </section>
  );
}
