import { create } from "zustand";
import type {
  AtlasManifest,
  RecoveredItem,
} from "../game/assets/assetManifest";
import { canPickup, craftInventory } from "../game/systems/ItemRules";
import { isShelterUpgrade, type Shelter } from "../game/systems/ShelterSystem";
import { SaveSystem, type SaveData } from "../game/systems/SaveSystem";
import type { WildlifeSnapshot } from "../game/entities/WildlifeState";
import {
  initialSurvival,
  activityEffect,
  consumeItem,
  REST_MS,
  type SurvivalState,
  advanceSurvival,
  energyEffect,
} from "../game/systems/SurvivalSystem";
import { isCoconutPalm } from "../game/maps/DecorVariants";
import {
  createCoconutGame,
  aimCoconut,
  throwCoconutStone,
  stepCoconutGame,
  coconutOutcome,
  type CoconutGame,
} from "../game/systems/CoconutSystem";
import {
  createNetFishingGame,
  castNet,
  stepNetFishing,
  netFishingOutcome,
  validNetShore,
  type NetFishingGame,
} from "../game/systems/NetFishingSystem";
import {
  createRodFishingGame,
  strikeRod,
  stepRodFishing,
  rodFishingOutcome,
  validRodShore,
  type RodFishingGame,
  type RodDirection,
} from "../game/systems/RodFishingSystem";
import { fishingCompletionEffect } from "../game/systems/FishingCompletion";
import {
  createHuntingGame,
  aimHunt,
  fireHunt,
  stepHunting,
  huntingOutcome,
  huntingProfile,
  type HuntingGame,
} from "../game/systems/HuntingSystem";
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
  behavior?: string;
  actorId?: number;
  kind?: "coconut-palm" | "net-fishing" | "rod-fishing";
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
  survival: SurvivalState;
  inventory: Record<number, number>;
  shelter: Shelter;
  worldObjects: readonly number[] | null;
  worldDecor: readonly number[] | null;
  worldTerrain: readonly number[] | null;
  fishingGame: NetFishingGame | null;
  rodGame: RodFishingGame | null;
  huntingGame: HuntingGame | null;
  huntingSerial: number;
  rodSerial: number;
  rodMove: RodDirection;
  fishingSerial: number;
  coconutGame: CoconutGame | null;
  coconutUses: number;
  coconutSerial: number;
  wildlife?: WildlifeSnapshot[];
  captureWildlife?: () => WildlifeSnapshot[];
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
  consume: (id: number) => boolean;
  beginRest: () => void;
  cancelRest: () => void;
  setSurvivalEnabled: (enabled: boolean) => void;
  save: () => Promise<void>;
  load: () => Promise<void>;
  startHunting: (index: number, itemId: number, actorId: number) => boolean;
  aimHunt: (angle: number, power: number) => void;
  fireHunt: () => void;
  advanceHunting: (delta: number) => void;
  leaveHunting: () => void;
  startRodFishing: (index: number) => boolean;
  strikeRod: () => void;
  steerRod: (direction: RodDirection) => void;
  advanceRodFishing: (delta: number, direction?: RodDirection) => void;
  leaveRodFishing: () => void;
  startNetFishing: (index: number) => boolean;
  castNet: () => void;
  advanceNetFishing: (delta: number) => void;
  leaveNetFishing: () => void;
  startCoconutGame: (index: number) => boolean;
  aimCoconut: (angle: number, power: number) => void;
  throwCoconut: () => void;
  advanceCoconut: (delta: number) => void;
  leaveCoconut: () => void;
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
  survival: initialSurvival(),
  inventory: {},
  shelter: 17,
  worldObjects: null,
  worldDecor: null,
  worldTerrain: null,
  fishingGame: null,
  rodGame: null,
  huntingGame: null,
  huntingSerial: 0,
  rodSerial: 0,
  rodMove: null,
  fishingSerial: 0,
  coconutGame: null,
  coconutUses: 0,
  coconutSerial: 0,
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
    set((s) =>
      (s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame) &&
      panel !== "settings" &&
      panel !== null
        ? {}
        : { panel: s.panel === panel ? null : panel },
    ),
  notify: (message) => {
    clearTimeout(noticeTimer);
    set({ notification: message });
    noticeTimer = setTimeout(() => set({ notification: null }), 4000);
  },
  inspect: (index, itemId) =>
    set((s) =>
      s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame
        ? {}
        : {
            panel: "dialogue",
            selected: itemId,
            inspected: s.inspected.includes(index)
              ? s.inspected
              : [...s.inspected, index],
          },
    ),
  collect: (index, itemId) => {
    const s = get();
    const item = s.items[itemId];
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame !== null ||
      s.fishingGame !== null ||
      s.rodGame !== null ||
      s.huntingGame !== null ||
      s.survival.death !== null ||
      s.survival.restRemainingMs !== null ||
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
      survival: activityEffect(s.survival, item, "collect"),
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
    if (
      !s.ready ||
      !s.started ||
      !item ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      s.survival.death ||
      s.survival.restRemainingMs !== null
    )
      return false;
    if (isShelterUpgrade(id) && !s.worldObjects?.includes(18)) return false;
    const inventory = craftInventory(item, s.inventory, s.shelter);
    if (!inventory) return false;
    const survival = activityEffect(s.survival, item, "craft");
    set({
      inventory,
      shelter: isShelterUpgrade(id) ? id : s.shelter,
      survival,
      ...(survival.death ? { panel: null, focus: null } : {}),
    });
    get().notify(
      isShelterUpgrade(id)
        ? `Your camp is now a ${item.name.toLowerCase()}`
        : `${item.name} crafted`,
    );
    return true;
  },
  discard: (id) =>
    set((s) =>
      s.coconutGame || s.fishingGame || s.rodGame || s.huntingGame
        ? {}
        : {
            inventory: {
              ...s.inventory,
              [id]: Math.max(0, (s.inventory[id] ?? 0) - 1),
            },
          },
    ),
  consume: (id) => {
    const s = get(),
      item = s.items[id];
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      !item
    )
      return false;
    const result = consumeItem(s.survival, item, s.inventory);
    if (!result) return false;
    set({
      ...result,
      ...(result.survival.death ? { panel: null, focus: null } : {}),
    });
    get().notify(`${item.name} consumed`);
    return true;
  },
  beginRest: () => {
    const s = get();
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame !== null ||
      s.fishingGame !== null ||
      s.rodGame !== null ||
      s.huntingGame !== null ||
      s.survival.death ||
      s.survival.restRemainingMs !== null
    )
      return;
    set({
      panel: null,
      focus: null,
      survival: { ...s.survival, restRemainingMs: REST_MS },
    });
  },
  cancelRest: () =>
    set((s) => ({ survival: { ...s.survival, restRemainingMs: null } })),
  setSurvivalEnabled: (enabled) =>
    set((s) => ({
      survival: {
        ...s.survival,
        enabled,
        ...(enabled
          ? {}
          : {
              hunger: 100,
              thirst: 100,
              energy: 100,
              death: null,
              restRemainingMs: null,
            }),
      },
    })),
  save: async () => {
    if (
      get().coconutGame ||
      get().fishingGame ||
      get().rodGame ||
      get().huntingGame
    ) {
      get().notify("Return to the island before saving this expedition.");
      return;
    }
    if (get().survival.death) {
      get().notify("Load an earlier save or continue in exploration mode.");
      return;
    }
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
        wildlife: s.captureWildlife?.() ?? s.wildlife,
        survival: s.survival,
        coconutUses: s.coconutUses,
        shelter: s.shelter,
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
    if (
      get().coconutGame ||
      get().fishingGame ||
      get().rodGame ||
      get().huntingGame
    ) {
      get().notify("Return to the island before loading an expedition.");
      return;
    }
    try {
      const saved = await SaveSystem.load();
      if (saved) {
        set({
          ...saved,
          wildlife: saved.wildlife,
          coconutUses: saved.coconutUses ?? 0,
          shelter: saved.shelter ?? 17,
          coconutGame: null,
          fishingGame: null,
          rodGame: null,
          huntingGame: null,
          rodMove: null,
          survival:
            saved.survival ?? initialSurvival(false, saved.elapsed * 1000),
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
  startHunting: (index, itemId, actorId) => {
    const s = get(),
      actors = s.captureWildlife?.();
    const near = (a: WildlifeSnapshot) =>
      Math.hypot(
        Math.floor(a.x / 16) * 16 + 8 - s.x,
        Math.floor(a.y / 16) * 16 + 8 - s.y,
      ) <= 44;
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      s.survival.death ||
      s.survival.restRemainingMs !== null ||
      !Number.isInteger(index) ||
      index < 0 ||
      index >= 32400 ||
      !huntingProfile(itemId) ||
      s.items[itemId]?.interaction.huntingTool !== 81 ||
      s.worldObjects?.[index] !== itemId + 1 ||
      (s.inventory[81] ?? 0) < 1 ||
      (s.inventory[45] ?? 0) < 1 ||
      !actors
    )
      return false;
    const selected = actors.find(
      (a) =>
        a.id === actorId &&
        a.mapId === itemId + 1 &&
        a.phase !== "fly" &&
        Math.floor(a.x / 16) + Math.floor(a.y / 16) * 180 === index &&
        near(a),
    );
    if (!selected || s.collected.includes(selected.id)) return false;
    const group = [
      selected,
      ...actors.filter(
        (a) =>
          a.id !== selected.id &&
          a.phase !== "fly" &&
          !s.collected.includes(a.id) &&
          near(a) &&
          huntingProfile(a.mapId - 1),
      ),
    ].slice(0, 5);
    set({
      huntingGame: createHuntingGame(
        group.map((a) => ({ id: a.id, itemId: a.mapId - 1 })),
        ((actorId + 1) * 2654435761 + s.huntingSerial) >>> 0,
      ),
      huntingSerial: s.huntingSerial + 1,
      panel: null,
      focus: null,
    });
    return true;
  },
  aimHunt: (angle, power) =>
    set((s) =>
      s.huntingGame && !s.panel
        ? { huntingGame: aimHunt(s.huntingGame, angle, power) }
        : {},
    ),
  fireHunt: () =>
    set((s) =>
      s.huntingGame && !s.panel ? { huntingGame: fireHunt(s.huntingGame) } : {},
    ),
  advanceHunting: (delta) => {
    const s = get(),
      before = s.huntingGame;
    if (
      !before ||
      before.status !== "playing" ||
      s.panel ||
      !Number.isFinite(delta) ||
      delta <= 0
    )
      return;
    delta = Math.min(delta, 180);
    let survival = advanceSurvival(s.survival, delta);
    if (survival.death) {
      set({
        survival,
        huntingGame: null,
        panel: null,
        focus: null,
        elapsed: s.elapsed + delta / 1000,
      });
      return;
    }
    const game = stepHunting(before, delta);
    if (game.status !== before.status) {
      const result = huntingOutcome(s.inventory, game);
      if (!result) {
        set({ huntingGame: null });
        return;
      }
      survival = energyEffect(survival, -result.attackEnergy);
      survival = fishingCompletionEffect(survival, s.items[81]);
      set({
        survival,
        inventory: result.inventory,
        collected: result.target
          ? [...s.collected, result.target.id]
          : s.collected,
        huntingGame: survival.death ? null : game,
        elapsed: s.elapsed + delta / 1000,
      });
    } else
      set({ huntingGame: game, survival, elapsed: s.elapsed + delta / 1000 });
  },
  leaveHunting: () =>
    set((s) =>
      s.huntingGame?.phase === "attacking" && s.huntingGame.status === "playing"
        ? {}
        : { huntingGame: null, panel: null, focus: null },
    ),
  startRodFishing: (index) => {
    const s = get();
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      s.survival.death ||
      s.survival.restRemainingMs !== null ||
      !validRodShore(s.worldTerrain, index, s.x, s.y) ||
      (s.inventory[14] ?? 0) < 1 ||
      (s.inventory[90] ?? 0) < 1
    )
      return false;
    set({
      rodGame: createRodFishingGame(
        index,
        ((index + 1) * 2654435761 + s.rodSerial) >>> 0,
      ),
      rodSerial: s.rodSerial + 1,
      rodMove: null,
      panel: null,
      focus: null,
    });
    return true;
  },
  strikeRod: () =>
    set((s) =>
      s.rodGame && !s.panel ? { rodGame: strikeRod(s.rodGame) } : {},
    ),
  steerRod: (direction) =>
    set((s) => (s.rodGame && !s.panel ? { rodMove: direction } : {})),
  advanceRodFishing: (delta, direction) => {
    const s = get(),
      before = s.rodGame;
    if (
      !before ||
      before.status !== "playing" ||
      s.panel ||
      !Number.isFinite(delta) ||
      delta <= 0
    )
      return;
    delta = Math.min(delta, 180);
    let survival = advanceSurvival(s.survival, delta);
    if (survival.death) {
      set({
        survival,
        rodGame: null,
        rodMove: null,
        panel: null,
        focus: null,
        elapsed: s.elapsed + delta / 1000,
      });
      return;
    }
    const game = stepRodFishing(before, delta, direction ?? s.rodMove);
    if (game.status !== before.status) {
      const inventory = rodFishingOutcome(s.inventory, game);
      if (!inventory) {
        set({ rodGame: null, rodMove: null });
        return;
      }
      survival = fishingCompletionEffect(survival, s.items[14]);
      set({
        inventory,
        survival,
        rodGame: survival.death ? null : game,
        rodMove: null,
        elapsed: s.elapsed + delta / 1000,
      });
    } else
      set({
        rodGame: game,
        survival,
        rodMove: game.tick === before.tick ? s.rodMove : null,
        elapsed: s.elapsed + delta / 1000,
      });
  },
  leaveRodFishing: () =>
    set({ rodGame: null, rodMove: null, panel: null, focus: null }),
  startNetFishing: (index) => {
    const s = get();
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      s.survival.death ||
      s.survival.restRemainingMs !== null ||
      !validNetShore(s.worldTerrain, index, s.x, s.y) ||
      (s.inventory[83] ?? 0) < 1 ||
      (s.inventory[26] ?? 0) < 1
    )
      return false;
    set({
      fishingGame: createNetFishingGame(
        index,
        ((index + 1) * 2654435761 + s.fishingSerial) >>> 0,
      ),
      fishingSerial: s.fishingSerial + 1,
      panel: null,
      focus: null,
    });
    return true;
  },
  castNet: () =>
    set((s) =>
      s.fishingGame && !s.panel ? { fishingGame: castNet(s.fishingGame) } : {},
    ),
  advanceNetFishing: (delta) => {
    const s = get(),
      before = s.fishingGame;
    if (
      !before ||
      before.status !== "playing" ||
      s.panel ||
      !Number.isFinite(delta) ||
      delta <= 0
    )
      return;
    delta = Math.min(delta, 180);
    let survival = advanceSurvival(s.survival, delta);
    if (survival.death) {
      set({
        survival,
        fishingGame: null,
        panel: null,
        focus: null,
        elapsed: s.elapsed + delta / 1000,
      });
      return;
    }
    const game = stepNetFishing(before, delta);
    if (game.status !== before.status) {
      const inventory = netFishingOutcome(s.inventory, game);
      if (!inventory) {
        set({ fishingGame: null });
        return;
      }
      survival = fishingCompletionEffect(survival, s.items[83]);
      set({
        inventory,
        survival,
        fishingGame: survival.death ? null : game,
        elapsed: s.elapsed + delta / 1000,
      });
    } else
      set({ fishingGame: game, survival, elapsed: s.elapsed + delta / 1000 });
  },
  leaveNetFishing: () => set({ fishingGame: null, panel: null, focus: null }),
  startCoconutGame: (index) => {
    const s = get();
    if (
      !s.ready ||
      !s.started ||
      s.coconutGame ||
      s.fishingGame ||
      s.rodGame ||
      s.huntingGame ||
      s.survival.death ||
      s.survival.restRemainingMs !== null ||
      !s.worldDecor ||
      !isCoconutPalm(s.worldDecor, index) ||
      (s.inventory[3] ?? 0) < 1 ||
      Math.hypot(
        (index % 180) * 16 + 8 - s.x,
        Math.floor(index / 180) * 16 + 8 - s.y,
      ) > 44
    )
      return false;
    set({
      coconutGame: createCoconutGame(
        index,
        ((index + 1) * 2654435761 + s.coconutSerial) >>> 0,
      ),
      coconutSerial: s.coconutSerial + 1,
      panel: null,
      focus: null,
    });
    return true;
  },
  aimCoconut: (angle, power) =>
    set((s) =>
      s.coconutGame && !s.panel
        ? { coconutGame: aimCoconut(s.coconutGame, angle, power) }
        : {},
    ),
  throwCoconut: () =>
    set((s) =>
      s.coconutGame && !s.panel
        ? { coconutGame: throwCoconutStone(s.coconutGame) }
        : {},
    ),
  advanceCoconut: (delta) => {
    const s = get(),
      before = s.coconutGame;
    if (
      !before ||
      before.status !== "playing" ||
      s.panel ||
      !Number.isFinite(delta) ||
      delta <= 0
    )
      return;
    delta = Math.min(delta, 180);
    let survival = advanceSurvival(s.survival, delta);
    if (survival.death) {
      set({
        survival,
        coconutGame: null,
        panel: null,
        focus: null,
        elapsed: s.elapsed + delta / 1000,
      });
      return;
    }
    const game = stepCoconutGame(before, delta);
    if (game.status !== before.status) {
      const result = coconutOutcome(
        s.inventory,
        s.coconutUses,
        game.status === "won",
      );
      if (!result) {
        set({ coconutGame: null });
        return;
      }
      survival = energyEffect(survival, s.items[3].effects.energy);
      set({
        survival,
        coconutGame: survival.death ? null : game,
        inventory: result.inventory,
        coconutUses: result.uses,
        elapsed: s.elapsed + delta / 1000,
      });
    } else
      set({ coconutGame: game, survival, elapsed: s.elapsed + delta / 1000 });
  },
  leaveCoconut: () => set({ coconutGame: null, panel: null, focus: null }),
}));
