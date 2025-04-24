package me.grian.griansbetamod.icystone

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.api.craftingrecipes.ShapedRecipeBuilder
import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addSmeltingRecipe
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

        // has to be in listener body so the blocks are initialized when called to be part of the map
        val smeltRecipes = mapOf(
            BetaMod.icyDiamondOre to Item.DIAMOND,
            BetaMod.icyIronOre to Item.IRON_INGOT,
            BetaMod.icyGoldOre to Item.GOLD_INGOT
        )

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

        addShapedRecipe {
            output(Item.REPEATER)

            top(Block.LIT_REDSTONE_TORCH.asItem(), Item.REDSTONE, Block.LIT_REDSTONE_TORCH.asItem())
            middle(BetaMod.icyStone, BetaMod.icyStone, BetaMod.icyStone)
        }

        addSmeltingRecipe {
            input(BetaMod.icyCobblestone)
            output(BetaMod.icyStone)
        }

        for ((input, output) in smeltRecipes) {
            addSmeltingRecipe {
                input(input)
                output(output)
            }
        }
    }


    private fun ShapedRecipeBuilder.applySticks(firstItem: Item? = null, secondItem: Item? = Item.STICK) = apply {
        middle(firstItem, secondItem, null)
        bottom(null, Item.STICK, null)
    }
}