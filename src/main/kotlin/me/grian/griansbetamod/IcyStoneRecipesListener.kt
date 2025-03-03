package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.ShapedRecipeBuilder
import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.util.isEventTypeShapeless
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent

object IcyStoneRecipesListener {
    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        if (!ConfigScreen.config.icyStone) return
        if (!isEventTypeShapeless(event.recipeId)) return

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

        // TODO: maybe figure out a way to get this to wokr properly without an extra recipe
        addShapedRecipe {
            output(Item.REPEATER)

            top(Block.LIT_REDSTONE_TORCH.asItem(), Item.REDSTONE, Block.LIT_REDSTONE_TORCH.asItem())
            middle(BetaMod.icyStone, BetaMod.icyStone, BetaMod.icyStone)
            bottom(null as Item?, null, null) // needs the cast for overload lol, so dumb
        }

        addShapedRecipe {
            output(Item.REPEATER)

            // have to do this as a separate recipe due to limitations of the dsl, won't be encountered often
            // so i cbf doing it in the dsl

            top(null as Item?, null, null) // needs the cast for overload lol, so dumb
            middle(Block.LIT_REDSTONE_TORCH.asItem(), Item.REDSTONE, Block.LIT_REDSTONE_TORCH.asItem())
            bottom(BetaMod.icyStone, BetaMod.icyStone, BetaMod.icyStone)
        }
    }


    private fun ShapedRecipeBuilder.applySticks(firstItem: Item? = null, secondItem: Item? = Item.STICK) = apply {
        middle(firstItem, secondItem, null)
        bottom(null, Item.STICK, null)
    }
}