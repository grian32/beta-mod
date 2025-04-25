package me.grian.griansbetamod.itemenhancements

import net.minecraft.item.ItemStack

data class EnhancementTableRecipe (
    val toolType: ToolType,
    val ingredients: ItemStack,
    val enhancement: Enhancement
)
