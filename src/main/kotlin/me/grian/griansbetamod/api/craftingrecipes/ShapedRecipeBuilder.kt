package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapedRecipeBuilder {
    private val pattern: Array<String> = Array(3) { "" }
    lateinit var output: ItemStack
    private val keys: MutableMap<Char, ItemStack> = mutableMapOf()

    fun pattern(str: String) {
        if (str.trimIndent().replace("\n", "").length != 9) {
            throw Exception("Recipe pattern does not contain the full pattern and is therefore invalid.")
        }

        if (str.trimIndent().count { it == '\n' } != 2) {
            throw Exception("Recipe pattern does not have the required amount of lines (3) and is therefore invalid.")
        }

        val splitStr = str.trimIndent().split("\n")

        // should always be valid :S
        pattern[0] = splitStr[0]
        pattern[1] = splitStr[1]
        pattern[2] = splitStr[2]
    }

    fun registerRecipe() {
        //            CraftingRegistry.addShapedRecipe(
//                ItemStack(redstoneBlock), // output
//                "rrr", // pattern
//                "rrr",
//                "rrr",
//                'r', // key
//                ItemStack(Item.REDSTONE) // value
//            )
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

    infix fun Char.key(item: ItemStack) {
        keys[this] = item
    }
}