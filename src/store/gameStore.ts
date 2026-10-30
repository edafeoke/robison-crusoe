import { create } from "zustand";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../game/assets/assetManifest";
import { canPickup, craftInventory } from "../game/systems/ItemRules";
import { SaveSystem, type SaveData } from "../game/systems/SaveSystem";
export type Panel =
  | "inventory"
  | "crafting"
  | "journal"
  | "map"
  | "settings"
  | "dialogue"
  | null;
export interface FocusObject {
  index: number;
  itemId: number;
  x: number;
  y: number;
  distance: number;
}
interface GameState {
  offlineReady: boolean;
  restoreRevision: number;
  ready: boolean;
  error: string | null;
  atlas: AtlasManifest | null;
  items: RecoveredItem[];
  story: string[];
  x: number;
  y: number;
  elapsed: number;
  inventory: Record<number, number>;
  worldObjects: readonly number[] | null;
  collected: number[];
  inspected: number[];
  discovered: number[];
  panel: Panel;
  selected: number | null;
  focus: FocusObject | null;
  notification: string | null;
  saving: boolean;
  saveMessage: string;
  zoom: number;
  ambience: boolean;
  enhancements: boolean;
  started: boolean;
  help: boolean;
  setPanel: (panel: Panel) => void;
  notify: (message: string) => void;
  collect: (index: number, itemId: number) => boolean;
  inspect: (index: number, itemId: number) => void;
  craft: (id: number) => boolean;
  discard: (id: number) => void;
  save: () => Promise<void>;
  load: () => Promise<void>;
}
let noticeTimer: ReturnType<typeof setTimeout>;
export const useGameStore = create<GameState>((set, get) => ({
  offlineReady: false,
  restoreRevision: 0,
  ready: false,
  error: null,
  atlas: null,
  items: [],
  story: [],
  x: 136,
  y: 2376,
  elapsed: 0,
  inventory: {},
  worldObjects: null,
  collected: [],
  inspected: [],
  discovered: [],
  panel: null,
  selected: null,
  focus: null,
  notification: null,
  saving: false,
  saveMessage: "Not saved yet",
  zoom: 2.6,
  ambience: false,
  enhancements: true,
  started: false,
  help: true,
  setPanel: (panel) =>
    set((s) => ({ panel: s.panel === panel ? null : panel })),
  notify: (message) => {
    clearTimeout(noticeTimer);
    set({ notification: message });
    noticeTimer = setTimeout(() => set({ notification: null }), 4000);
  },
  inspect: (index, itemId) =>
    set((s) => ({
      panel: "dialogue",
      selected: itemId,
      inspected: s.inspected.includes(index)
        ? s.inspected
        : [...s.inspected, index],
    })),
  collect: (index, itemId) => {
    const s = get();
    const item = s.items[itemId];
    if (
      !s.ready ||
      !s.started ||
      !Number.isInteger(index) ||
      index < 0 ||
      index >= 32400 ||
      !item ||
      s.worldObjects?.[index] !== itemId + 1 ||
      s.collected.includes(index) ||
      Math.hypot(
        (index % 180) * 16 + 8 - s.x,
        Math.floor(index / 180) * 16 + 8 - s.y,
      ) > 44 ||
      !canPickup(item, s.inventory)
    )
      return false;
    set({
      inventory: { ...s.inventory, [itemId]: (s.inventory[itemId] ?? 0) + 1 },
      collected: [...s.collected, index],
      panel: null,
      focus: null,
    });
    get().notify(`${s.items[itemId].name} added to your satchel`);
    return true;
  },
  craft: (id) => {
    const s = get(),
      item = s.items[id];
    if (!item) return false;
    const inventory = craftInventory(item, s.inventory);
    if (!inventory) return false;
    set({ inventory });
    get().notify(`${item.name} crafted`);
    return true;
  },
  discard: (id) =>
    set((s) => ({
      inventory: {
        ...s.inventory,
        [id]: Math.max(0, (s.inventory[id] ?? 0) - 1),
      },
    })),
  save: async () => {
    set({ saving: true });
    try {
      const s = get();
      const data: SaveData = {
        version: 1,
        x: s.x,
        y: s.y,
        elapsed: s.elapsed,
        inventory: s.inventory,
        collected: s.collected,
        inspected: s.inspected,
        discovered: s.discovered,
        enhancements: s.enhancements,
      };
      await SaveSystem.save(data);
      set({ saveMessage: "Expedition saved on this device" });
      get().notify("Your expedition has been saved");
    } catch {
      set({
        saveMessage: "Storage unavailable. Please allow browser storage.",
      });
      get().notify("Could not save. Browser storage is unavailable.");
    } finally {
      set({ saving: false });
    }
  },
  load: async () => {
    try {
      const saved = await SaveSystem.load();
      if (saved) {
        set({
          ...saved,
          panel: null,
          focus: null,
          started: true,
          restoreRevision: get().restoreRevision + 1,
          saveMessage: "Saved expedition restored",
        });
        get().notify("Welcome back to the island");
      } else get().notify("No saved expedition on this device yet");
    } catch {
      get().notify(
        "Save unavailable or unsupported. Your current expedition is unchanged.",
      );
    }
  },
}));
