package me.grian.griansbetamod.itemenhancements

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

data class EnhancementTableRecipe(
    val tool: Item,
    val enhancement: ItemStack,
    val result: ItemStack
)
