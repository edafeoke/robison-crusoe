import { useGameStore } from "../store/gameStore";
export function IslandMap({ large = false }: { large?: boolean }) {
  const x = useGameStore((s) => s.x),
    y = useGameStore((s) => s.y);
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
      <span className="map-north">
        N<span>↑</span>
      </span>
      {large && (
        <>
          <span className="map-ocean">THE UNKNOWN SEA</span>
          <div className="map-scale">
            ──────
            <br /> 30 tiles
          </div>
        </>
      )}
    </div>
  );
}
