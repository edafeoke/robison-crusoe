import { useState } from "react";
import { BookOpen, Hammer, LockKeyhole, Check } from "lucide-react";
import { useGameStore } from "../store/gameStore";
import {
  craftAvailability,
  portableRecipes,
  categoryName,
} from "../game/systems/ItemRules";
import { SpriteIcon } from "./SpriteIcon";
export function CraftingPanel() {
  const s = useGameStore(),
    [readyOnly, setReadyOnly] = useState(false);
  const recipes = portableRecipes(s.items),
    ready = recipes.filter(
      (item) => craftAvailability(item, s.inventory).allowed,
    );
  const shown = readyOnly ? ready : recipes;
  return (
    <>
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
          const status = craftAvailability(item, s.inventory);
          return (
            <article className="recipe" key={item.id}>
              <div className="recipe-illustration">
                <SpriteIcon sprite={item.sprite} size={56} />
              </div>
              <div className="recipe-content">
                <p className="eyebrow">
                  {categoryName(item.category).toUpperCase()} · RECOVERED RECIPE
                </p>
                <h3>{item.name}</h3>
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
                aria-label={`Craft ${item.name}`}
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
                {status.alreadyOwned ? "Owned" : "Craft"}
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
          satchel. Buildings, clothing and the original minigames are still
          being reconstructed.
        </p>
      </div>
    </>
  );
}
