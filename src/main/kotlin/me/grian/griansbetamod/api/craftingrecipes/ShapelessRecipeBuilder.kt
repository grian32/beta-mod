package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapelessRecipeBuilder {
    lateinit var output: ItemStack
    private val ingredients: MutableList<Any> = mutableListOf()

    fun ingredient(item: Any) {
        ingredients.add(item)
    }

    fun registerRecipe() {
        CraftingRegistry.addShapelessRecipe(
            output,
            *ingredients.toTypedArray()
        )
    }
}