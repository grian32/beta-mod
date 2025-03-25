package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.item.Items
import net.modificationstation.stationapi.api.recipe.CraftingRegistry
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry

class SmeltingRecipeBuilder {
    private lateinit var output: ItemStack
    private lateinit var input: ItemStack

    fun input(item: Item) {
        input(ItemStack(item, 1))
    }

    fun input(block: Block) {
        input(ItemStack(block, 1))
    }

    fun input(itemStack: ItemStack) {
        input = itemStack
    }

    fun output(item: Item) {
        output(ItemStack(item, 1))
    }

    fun output(block: Block) {
        output(ItemStack(block, 1))
    }

    fun output(itemStack: ItemStack) {
        output = itemStack
    }

    fun registerRecipe() {
        SmeltingRegistry.addSmeltingRecipe(
            input,
            output
        )
    }
}