package me.grian.griansbetamod.itemenhancements

import net.minecraft.item.ItemStack

data class EnhancementRecipe (
    val toolType: ToolType,
    val ingredients: ItemStack,
    val enhancement: Enhancement
)
