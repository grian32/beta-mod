package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.api.craftingrecipes.addEnhancementRecipe
import me.grian.griansbetamod.config.ConfigScreen
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.item.ItemStack

object EnhancementRecipeListener : ModInitializer {
    override fun onInitialize() {
        if (ConfigScreen.config.enhancementSystem) {
            addEnhancementRecipe {
                toolType = ToolType.AXE
                ingredients = ItemStack(Block.LOG, 48, 0)
                enhancement = Enhancement.EXTRA_LOGS
                enhancementTier = 1
            }
        }
    }
}