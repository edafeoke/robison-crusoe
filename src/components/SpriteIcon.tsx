import { useGameStore } from "../store/gameStore";
export function SpriteIcon({
  sprite,
  size = 36,
}: {
  sprite: number;
  size?: number;
}) {
  const atlas = useGameStore((s) => s.atlas);
  if (!atlas || sprite < 0)
    return (
      <span className="empty-sprite" style={{ width: size, height: size }}>
        ◇
      </span>
    );
  const bank = sprite >>> 16,
    sheet = atlas.sheets[bank],
    f = sheet?.frames[sprite & 65535];
  if (!f) return null;
  const scale = Math.min(size / f.width, size / f.height, 3);
  return (
    <span
      className="sprite-icon"
      aria-hidden="true"
      style={{
        width: f.width * scale,
        height: f.height * scale,
        backgroundImage: `url(${sheet.url})`,
        backgroundPosition: `-${f.x * scale}px -${f.y * scale}px`,
        backgroundSize: `${sheet.width * scale}px ${sheet.height * scale}px`,
        transform: f.flipX ? "scaleX(-1)" : undefined,
      }}
    />
  );
}
