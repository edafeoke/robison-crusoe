import { CollisionSystem } from "./CollisionSystem";
export interface Point {
  x: number;
  y: number;
}
// Browser enhancement: bounded A* on the recovered collision grid, four directions, no corner cutting.
export function findPath(
  collision: CollisionSystem,
  start: Point,
  end: Point,
): Point[] {
  const sx = Math.floor(start.x / 16),
    sy = Math.floor(start.y / 16),
    ex = Math.floor(end.x / 16),
    ey = Math.floor(end.y / 16);
  if (!collision.walkable(ex, ey)) return [];
  const key = (x: number, y: number) => x + y * 180,
    goal = key(ex, ey),
    begin = key(sx, sy);
  const open = [begin],
    came = new Map<number, number>(),
    g = new Map([[begin, 0]]),
    closed = new Set<number>();
  const score = (n: number) =>
    (g.get(n) ?? Infinity) +
    Math.abs((n % 180) - ex) +
    Math.abs(Math.floor(n / 180) - ey);
  for (let visited = 0; open.length && visited < 32400; visited++) {
    let best = 0;
    for (let i = 1; i < open.length; i++)
      if (score(open[i]) < score(open[best])) best = i;
    const current = open.splice(best, 1)[0];
    if (current === goal) {
      const path: Point[] = [];
      let n = current;
      while (n !== begin) {
        path.unshift({
          x: (n % 180) * 16 + 8,
          y: Math.floor(n / 180) * 16 + 8,
        });
        n = came.get(n)!;
      }
      return path;
    }
    closed.add(current);
    const x = current % 180,
      y = Math.floor(current / 180);
    for (const [dx, dy] of [
      [1, 0],
      [-1, 0],
      [0, 1],
      [0, -1],
    ]) {
      const nx = x + dx,
        ny = y + dy,
        n = key(nx, ny);
      if (!collision.walkable(nx, ny) || closed.has(n)) continue;
      const cost = g.get(current)! + 1;
      if (cost < (g.get(n) ?? Infinity)) {
        came.set(n, current);
        g.set(n, cost);
        if (!open.includes(n)) open.push(n);
      }
    }
  }
  return [];
}
