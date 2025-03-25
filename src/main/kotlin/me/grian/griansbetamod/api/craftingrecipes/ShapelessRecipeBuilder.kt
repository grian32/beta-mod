package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapelessRecipeBuilder {
    private lateinit var output: ItemStack
    private val ingredients: MutableList<Any> = mutableListOf()

    fun ingredient(item: Any) {
        ingredients.add(item)
    }

    fun output(item: Item) {
        output(ItemStack(item, 1))
    }

    fun output(itemStack: ItemStack) {
        output = itemStack
    }

    fun registerRecipe() {
        CraftingRegistry.addShapelessRecipe(
            output,
            *ingredients.toTypedArray()
        )
    }
}