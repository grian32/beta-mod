package me.grian.griansbetamod.itemenhancements.recipe

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.ToolType
import net.minecraft.item.ItemStack

data class EnhancementRecipe (
    val toolType: ToolType,
    val ingredients: ItemStack,
    val enhancement: Enhancement,
    val enhancementTier: Int,
)
