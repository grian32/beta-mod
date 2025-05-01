package me.grian.griansbetamod.mixinutils

import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe

fun stationRecipeToList(ssr: StationShapedRecipe): List<ItemStack?> = ssr.grid.map { it?.right()?.orElseThrow() }

fun normalizeRecipe(input: Inventory, recipe: List<ItemStack?>): List<ItemStack?> {
    val normalizedRecipe = MutableList<ItemStack?>(input.size()) { null }
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
