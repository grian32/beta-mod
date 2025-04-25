package me.grian.griansbetamod.itemenhancements.recipe

import me.grian.griansbetamod.mixininterfaces.IItemStackMixin
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack

object EnhancementRecipeManager {
    val recipes: MutableList<EnhancementRecipe> = mutableListOf()

    fun craft(input: CraftingInventory): ItemStack? {
        val first = input.getStack(0)
        val second = input.getStack(1)

        if (first == null || second == null) return null

        val recipe = findRecipe(input) ?: return null

        val newStack = (ItemStack(first.item, 1, first.damage) as IItemStackMixin)

        newStack.`beta_mod$setEnhancement`(recipe.enhancement)
        newStack.`beta_mod$setEnhancementTier`(recipe.enhancementTier)

        return newStack as ItemStack
    }


    fun findRecipe(input: CraftingInventory) = recipes.find {
        input.getStack(0).item::class.java == it.toolType.clazz && input.getStack(1).containsOther(it.ingredients)
    }

    private fun ItemStack.containsOther(other: ItemStack) = this.item.id == other.item.id && this.count >= other.count && this.damage == other.damage
}