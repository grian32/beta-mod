package me.grian.griansbetamod.itemenhancements

import net.minecraft.block.Block
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object EnhancementRecipeManager {
    val recipes = listOf(
        EnhancementTableRecipe(
            Item.DIAMOND_AXE,
            ItemStack(Block.LOG, 48),
            ItemStack(Block.WOOL, 1, 2)
        )
    )

    fun craft(input: CraftingInventory): ItemStack? {
        val first = input.getStack(0)
        val second = input.getStack(1)

        if (first == null || second == null) return null

        return findRecipe(input)?.result
    }


    fun findRecipe(input: CraftingInventory) = recipes.find { it.tool.id == input.getStack(0).item.id && input.getStack(1).containsOther(it.enhancement) }

    private fun ItemStack.containsOther(other: ItemStack) = this.item.id == other.item.id && this.count >= other.count && this.damage == other.damage
}