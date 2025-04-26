package me.grian.griansbetamod.itemenhancements.recipe

import me.grian.griansbetamod.itemenhancements.setEnhancement
import me.grian.griansbetamod.itemenhancements.setEnhancementTier
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack

object EnhancementRecipeManager {
    val recipes: MutableList<EnhancementRecipe> = mutableListOf()

    fun craft(input: CraftingInventory): ItemStack? {
        val first = input.getStack(0)
        val second = input.getStack(1)

        if (first == null || second == null) return null

        val recipe = findRecipe(input) ?: return null

        val newStack = ItemStack(first.item, 1, first.damage).copy()

        newStack.setEnhancement(recipe.enhancement)
        newStack.setEnhancementTier(recipe.enhancementTier)

        return newStack
    }


    fun findRecipe(input: CraftingInventory) = recipes.find {
        input.getStack(0).item::class.java == it.toolType.clazz && input.getStack(1).containsOther(it.ingredients)
    }

    private fun ItemStack.containsOther(other: ItemStack) = this.item.id == other.item.id && this.count >= other.count && this.damage == other.damage
}