package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.api.craftingrecipes.addEnhancementRecipe
import me.grian.griansbetamod.config.ConfigScreen
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.item.ItemStack

object EnhancementRecipeListener : ModInitializer {
    override fun onInitialize() {
        if (ConfigScreen.config.enhancementSystem) {
            fun extraLogs(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.AXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.EXTRA_LOGS
                    enhancementTier = tier
                }

            extraLogs(
                ItemStack(Block.LOG, 48, 0),
                1
            )

            extraLogs(
                ItemStack(BetaMod.pileOfLogs, 16),
                2
            )

            extraLogs(
                ItemStack(BetaMod.pileOfLogs, 32),
                3
            )

            extraLogs(
                ItemStack(BetaMod.pileOfLogs, 64),
            4
            )

            fun resin(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.AXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.RESIN
                    enhancementTier = tier
                }

            resin(
                ItemStack(Block.LOG, 64, 1),
                1
            )

            resin(
                ItemStack(BetaMod.resin, 48),
                2
            )

            resin(
                ItemStack(BetaMod.resinBlock, 32),
                3
            )
        }
    }
}