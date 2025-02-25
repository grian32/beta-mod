package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapedRecipeBuilder {
    private val pattern: Array<String> = Array(3) { "" }
    lateinit var output: ItemStack
    private val keys: MutableMap<Char, ItemStack> = mutableMapOf()

    fun key(c: Char, item: ItemStack) {
        keys[c] = item
    }

    fun top(str: String) {
        checkLength(str)
        pattern[0] = str
    }

    fun middle(str: String) {
        checkLength(str)
        pattern[1] = str
    }

    fun bottom(str: String) {
        checkLength(str)
        pattern[2] = str
    }

    fun registerRecipe() {
        val spreadArr: MutableList<Any> = mutableListOf()

        pattern.forEach {
            spreadArr.add(it)
        }

        keys.forEach { (c, itemStack) ->
            spreadArr.add(c)
            spreadArr.add(itemStack)
        }


        CraftingRegistry.addShapedRecipe(
            output,
            *spreadArr.toTypedArray()
        )
    }

    private fun checkLength(str: String) {
        if (str.length != 3) throw Exception("All recipes lines must be 3 characters long.")
    }
}