package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapedRecipeBuilder {
    private val pattern: Array<String> = Array(3) { "" }
    private lateinit var output: ItemStack
    private val keys: MutableMap<Char, ItemStack> = mutableMapOf()
    private val charsForItems = ('a'..'j').toMutableList()

    fun output(block: Block) {
        output(block.asItem())
    }

    fun output(item: Item) {
        output(ItemStack(item, 1))
    }

    fun output(itemStack: ItemStack) {
        output = itemStack
    }

    fun top(itemStack1: ItemStack?, itemStack2: ItemStack?, itemStack3: ItemStack?) {
        handleItemStackLine(itemStack1, itemStack2, itemStack3, 0)
    }

    fun middle(itemStack1: ItemStack?, itemStack2: ItemStack?, itemStack3: ItemStack?) {
        handleItemStackLine(itemStack1, itemStack2, itemStack3, 1)
    }

    fun bottom(itemStack1: ItemStack?, itemStack2: ItemStack?, itemStack3: ItemStack?) {
        handleItemStackLine(itemStack1, itemStack2, itemStack3, 2)
    }

    fun top(item1: Item?, item2: Item?, item3: Item?) = top(item1.toStack(), item2.toStack(), item3.toStack())

    fun middle(item1: Item?, item2: Item?, item3: Item?) = middle(item1.toStack(), item2.toStack(), item3.toStack())

    fun bottom(item1: Item?, item2: Item?, item3: Item?) = bottom(item1.toStack(), item2.toStack(), item3.toStack())

    fun top(block1: Block, block2: Block, block3: Block) = top(block1.toItem(), block2.toItem(), block3.toItem())

    fun middle(block1: Block, block2: Block, block3: Block) = middle(block1.toItem(), block2.toItem(), block3.toItem())

    fun bottom(block1: Block, block2: Block, block3: Block) = bottom(block1.toItem(), block2.toItem(), block3.toItem())

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

    private fun processCharForItemStack(itemStack: ItemStack?): Char {
        return when {
            itemStack == null -> ' '
            keys.values.contains(itemStack) -> keys.entries.first { it.value == itemStack }.key
            else -> {
                val key = charsForItems.removeFirst()
                keys[key] = itemStack
                return key
            }
        }
    }

    private fun handleItemStackLine(itemStack1: ItemStack?, itemStack2: ItemStack?, itemStack3: ItemStack?, idx: Int) {
        var str = ""

        str += processCharForItemStack(itemStack1)
        str += processCharForItemStack(itemStack2)
        str += processCharForItemStack(itemStack3)

        pattern[idx] = str
    }

    private fun Item?.toStack(): ItemStack? = if (this == null) null else ItemStack(this, 1)
    
    private fun Block?.toItem(): Item? = this?.asItem()
}