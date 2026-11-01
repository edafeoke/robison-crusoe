// Stable selection was introduced in the first browser milestone; all consumers share it.
export const decorVariantIndex = (
  index: number,
  decor: number,
  count: number,
) => {
  if (decor !== 18) return (index * 17 + decor) % count;
  // A mixed hash avoids the old linear selector's inaccessible starting-region palms.
  // Source g.b chooses palm variants randomly; this deterministic choice is a browser adaptation.
  let hash =
    Math.imul(index + 1, 0x9e3779b1) ^ Math.imul(decor + 1, 0x85ebca6b);
  hash ^= hash >>> 16;
  hash = Math.imul(hash, 0x7feb352d);
  hash ^= hash >>> 15;
  hash = Math.imul(hash, 0x846ca68b);
  hash ^= hash >>> 16;
  return (hash >>> 0) % count;
};
/** g's identity decor table + a.a(18,8) + h.i, which draws the coconut-bearing palm. */
export const isCoconutPalm = (decor: readonly number[], index: number) =>
  Number.isInteger(index) &&
  index >= 0 &&
  index < 32400 &&
  decor[index] === 18 &&
  decorVariantIndex(index, 18, 13) === 8;
