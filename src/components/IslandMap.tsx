import { tideAt } from "../game/systems/TideSystem";
import { useGameStore } from "../store/gameStore";
export function IslandMap({ large = false }: { large?: boolean }) {
  const x = useGameStore((s) => s.x),
    y = useGameStore((s) => s.y),
    elapsed = useGameStore((s) => s.elapsed),
    shelter = useGameStore((s) => s.shelter),
    items = useGameStore((s) => s.items),
    camp = useGameStore((s) => s.worldObjects?.indexOf(18) ?? -1);
  return (
    <div className={`island-map ${large ? "large-map" : ""}`}>
      <img
        src="/assets/maps/island-overview.png"
        alt="The complete recovered island, with beaches, jungle, rivers and offshore islands"
      />
      <span
        className="map-marker"
        style={{ left: `${(x / 2880) * 100}%`, top: `${(y / 2880) * 100}%` }}
        aria-label="Your position"
      />
      {camp >= 0 && (
        <span
          className="map-camp"
          style={{
            left: `${(((camp % 180) + 0.5) / 180) * 100}%`,
            top: `${((Math.floor(camp / 180) + 0.5) / 180) * 100}%`,
          }}
          title={`Your camp: ${items[shelter]?.name}`}
          aria-label={`Your camp: ${items[shelter]?.name}`}
        >
          ⌂
        </span>
      )}
      <span className="map-north">
        N<span>↑</span>
      </span>
      {large && (
        <>
          <span className="map-ocean">THE UNKNOWN SEA</span>
          <span className="map-tide-note">
            {tideAt(elapsed) === "low" ? "Low tide" : "High tide"} · Chart
            records the original coastline
          </span>
          <div className="map-scale">
            ──────
            <br /> 30 tiles
          </div>
        </>
      )}
    </div>
  );
}
