package me.grian.griansbetamod.mixinutils

import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe

// tag ingredients (left) always consume 1, item stack ingredients (right) consume their count
fun stationRecipeToCounts(ssr: StationShapedRecipe): List<Int?> = ssr.grid.map { it?.map({ 1 }, { stack -> stack.count }) }

fun normalizeRecipe(input: Inventory, recipe: List<Int?>): List<Int?> {
    val normalizedRecipe = MutableList<Int?>(input.size()) { null }
    val recipeItems = ArrayDeque(recipe.filterNotNull())

    // unfortunately cant use input.indices as its not a collection
    for (i in 0..<input.size()) {
        if (input.getStack(i) == null) continue

        normalizedRecipe[i] = recipeItems.removeFirst()
    }

    return normalizedRecipe
}

fun findRecipe(recipes: List<Any>, input: Inventory): StationShapedRecipe? =
    recipes.find { it is StationShapedRecipe && it.matches(input as CraftingInventory) } as StationShapedRecipe?
