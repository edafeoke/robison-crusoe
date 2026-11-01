import { useState } from "react";
import { BookOpen, Hammer, LockKeyhole, Check } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import {
  craftAvailability,
  supportedRecipes,
  categoryName,
} from "../game/systems/ItemRules";
import { SpriteIcon } from "./SpriteIcon";
import { CampOverview } from "./ShelterPanel";
import { isShelterUpgrade } from "../game/systems/ShelterSystem";
import { isFurnishing } from "../game/systems/HouseSystem";
import { isClothing } from "../game/systems/ClothingSystem";
export function CraftingPanel() {
  const s = useGameStore(),
    [readyOnly, setReadyOnly] = useState(false);
  const recipes = supportedRecipes(s.items),
    ready = recipes.filter(
      (item) =>
        craftAvailability(
          item,
          s.inventory,
          s.shelter,
          s.furnishings,
          s.clothing,
        ).allowed,
    );
  const shown = readyOnly ? ready : recipes;
  return (
    <>
      <CampOverview />
      <p className="panel-intro">
        Ingenuity is its own kind of survival. Follow the island’s recovered
        recipes, with the right supplies and tools.
      </p>
      <div className="panel-filters" role="group" aria-label="Crafting recipes">
        <button
          className={!readyOnly ? "selected" : ""}
          onClick={() => setReadyOnly(false)}
        >
          All recipes <span>{recipes.length}</span>
        </button>
        <button
          className={readyOnly ? "selected" : ""}
          onClick={() => setReadyOnly(true)}
        >
          Ready to make <span>{ready.length}</span>
        </button>
      </div>
      <div className="recipe-list">
        {shown.map((item) => {
          const status = craftAvailability(
              item,
              s.inventory,
              s.shelter,
              s.furnishings,
              s.clothing,
            ),
            building = isShelterUpgrade(item.id) || isFurnishing(item.id);
          return (
            <article className="recipe" key={item.id}>
              <div className="recipe-illustration">
                <SpriteIcon sprite={item.sprite} size={56} />
              </div>
              <div className="recipe-content">
                <p className="eyebrow">
                  {isShelterUpgrade(item.id)
                    ? "CAMP UPGRADE"
                    : categoryName(item.category).toUpperCase()}{" "}
                  · RECOVERED RECIPE
                </p>
                <h3>{item.name}</h3>
                {isClothing(item.id) && (
                  <p className="craft-energy">
                    Kept in your wardrobe · equip from Change clothes
                  </p>
                )}
                {isShelterUpgrade(item.id) && (
                  <p className="craft-energy">
                    Upgrades your original camp in place · no item to carry
                  </p>
                )}
                {isFurnishing(item.id) && (
                  <p className="craft-energy">
                    Kept in your house · one of each furnishing
                  </p>
                )}
                {item.shelterRequirement >= 0 && (
                  <p
                    className={`craft-tier ${status.shelterReady ? "unlocked" : ""}`}
                  >
                    {status.shelterReady ? (
                      <Check size={12} />
                    ) : (
                      <LockKeyhole size={12} />
                    )}
                    {s.items[item.shelterRequirement]?.name} required ·{" "}
                    {status.shelterReady ? "unlocked" : "build your camp first"}
                  </p>
                )}
                {item.effects.energy < 0 && (
                  <p className="craft-energy">
                    Energy −{Math.abs(item.effects.energy)}
                  </p>
                )}
                <p className="requirement-label">MATERIALS · USED UP</p>
                <div className="ingredients">
                  {item.recipe.map((r) => (
                    <span
                      className={
                        (s.inventory[r.item] ?? 0) >= r.quantity ? "enough" : ""
                      }
                      key={r.item}
                    >
                      <SpriteIcon sprite={s.items[r.item].sprite} size={20} />
                      {s.items[r.item].name}{" "}
                      <b>
                        {s.inventory[r.item] ?? 0}/{r.quantity}
                      </b>
                    </span>
                  ))}
                </div>
                {!!item.craftingTools.length && (
                  <>
                    <p className="requirement-label">TOOLS · KEPT</p>
                    <div className="ingredients">
                      {item.craftingTools.map((r) => (
                        <span
                          className={
                            (s.inventory[r.item] ?? 0) >= r.quantity
                              ? "enough"
                              : ""
                          }
                          key={r.item}
                        >
                          <SpriteIcon
                            sprite={s.items[r.item].sprite}
                            size={20}
                          />
                          {s.items[r.item].name}{" "}
                          <b>
                            {(s.inventory[r.item] ?? 0) >= r.quantity ? (
                              <Check size={11} />
                            ) : (
                              "needed"
                            )}
                          </b>
                        </span>
                      ))}
                    </div>
                  </>
                )}
              </div>
              <button
                className="primary-button"
                aria-label={`${building ? "Build" : "Craft"} ${item.name}`}
                disabled={!status.allowed}
                onClick={() => s.craft(item.id)}
              >
                {status.alreadyOwned ? (
                  <Check size={15} />
                ) : status.allowed ? (
                  <Hammer size={15} />
                ) : (
                  <LockKeyhole size={15} />
                )}{" "}
                {status.alreadyOwned
                  ? building
                    ? "Built"
                    : "Owned"
                  : !status.shelterReady
                    ? `Needs ${s.items[item.shelterRequirement]?.name}`
                    : building
                      ? "Build"
                      : "Craft"}
              </button>
            </article>
          );
        })}
      </div>
      {!shown.length && (
        <div className="empty-state">
          <Hammer size={32} />
          <h3>A few things are still missing.</h3>
          <p>
            Gather supplies and salvage tools from the wreckage. Recipes list
            exactly what you need.
          </p>
        </div>
      )}
      <div className="field-note">
        <BookOpen size={19} />
        <p>
          <b>A tool is worth keeping.</b>
          <br />
          Crafting uses the listed materials. Required tools stay in your
          satchel. Camp upgrades unlock recipes everywhere on the island.
          Furnishings stay in your house; garments stay in your wardrobe. Boat
          repair is still being reconstructed.
        </p>
      </div>
    </>
  );
}
