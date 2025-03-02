package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.ShapedRecipeBuilder
import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent

object IcyStoneRecipesListener {
    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        if (!ConfigScreen.config.icyStone) return

        addShapedRecipe {
            output(Block.FURNACE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
            middle(BetaMod.icyCobblestone, null, BetaMod.icyCobblestone)
            bottom(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
        }

        addShapedRecipe {
            output(Item.STONE_PICKAXE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)

            applySticks()
        }

        addShapedRecipe {
            output(Item.STONE_AXE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, null)

            applySticks(BetaMod.icyCobblestone.asItem())
        }

        addShapedRecipe {
            output(Item.STONE_SHOVEL)

            top(null, BetaMod.icyCobblestone, null)

            applySticks()
        }


        addShapedRecipe {
            output(Item.STONE_SWORD)

            top(null, BetaMod.icyCobblestone, null)

            applySticks(secondItem = BetaMod.icyCobblestone.asItem())
        }


        addShapedRecipe {
            output(Item.STONE_HOE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, null)

            applySticks()
        }
    }


    private fun ShapedRecipeBuilder.applySticks(firstItem: Item? = null, secondItem: Item? = Item.STICK) = apply {
        middle(firstItem, secondItem, null)
        bottom(null, Item.STICK, null)
    }
}