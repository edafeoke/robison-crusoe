import {
  validWildlife,
  type WildlifeSnapshot,
} from "../entities/WildlifeState";
import { validSurvival, type SurvivalState } from "./SurvivalSystem";
import { validShelter, type Shelter } from "./ShelterSystem";
export interface SaveData {
  version: 1;
  x: number;
  y: number;
  inventory: Record<number, number>;
  collected: number[];
  inspected: number[];
  elapsed: number;
  discovered: number[];
  enhancements: boolean;
  wildlife?: WildlifeSnapshot[];
  survival?: SurvivalState;
  coconutUses?: number;
  shelter?: Shelter;
}
export function validSave(value: unknown): value is SaveData {
  const d = value as SaveData;
  return (
    !!d &&
    d.version === 1 &&
    Number.isFinite(d.x) &&
    Number.isFinite(d.y) &&
    d.x >= 0 &&
    d.x < 2880 &&
    d.y >= 0 &&
    d.y < 2880 &&
    Number.isFinite(d.elapsed) &&
    d.elapsed >= 0 &&
    typeof d.enhancements === "boolean" &&
    (d.wildlife === undefined || validWildlife(d.wildlife)) &&
    (d.survival === undefined || validSurvival(d.survival)) &&
    (d.shelter === undefined || validShelter(d.shelter)) &&
    (d.coconutUses === undefined ||
      (Number.isInteger(d.coconutUses) &&
        d.coconutUses >= 0 &&
        d.coconutUses <= 5)) &&
    !!d.inventory &&
    typeof d.inventory === "object" &&
    Object.entries(d.inventory).every(
      ([k, v]) =>
        Number.isInteger(+k) &&
        +k >= 0 &&
        +k < 120 &&
        Number.isInteger(v) &&
        v >= 0 &&
        v <= 100000,
    ) &&
    ["collected", "inspected", "discovered"].every(
      (k) =>
        Array.isArray(d[k as keyof SaveData]) &&
        (d[k as keyof SaveData] as unknown[]).every(
          (v) => Number.isInteger(v) && Number(v) >= 0 && Number(v) < 32400,
        ),
    )
  );
}
export class SaveSystem {
  private static db(): Promise<IDBDatabase> {
    return new Promise((resolve, reject) => {
      const request = indexedDB.open("robinson-island", 1);
      request.onupgradeneeded = () => request.result.createObjectStore("saves");
      request.onsuccess = () => resolve(request.result);
      request.onerror = () => reject(request.error);
    });
  }
  static async save(data: SaveData): Promise<void> {
    const db = await this.db();
    try {
      await new Promise<void>((resolve, reject) => {
        const tx = db.transaction("saves", "readwrite");
        tx.objectStore("saves").put(data, "expedition");
        tx.oncomplete = () => resolve();
        tx.onerror = () => reject(tx.error);
        tx.onabort = () => reject(tx.error);
      });
    } finally {
      db.close();
    }
  }
  static async load(): Promise<SaveData | null> {
    const db = await this.db();
    try {
      return await new Promise((resolve, reject) => {
        const req = db
          .transaction("saves")
          .objectStore("saves")
          .get("expedition");
        req.onsuccess = () => {
          if (req.result !== undefined && !validSave(req.result)) {
            reject(
              new Error("The saved expedition is invalid or unsupported."),
            );
            return;
          }
          resolve(req.result ?? null);
        };
        req.onerror = () => reject(req.error);
      });
    } finally {
      db.close();
    }
  }
}
