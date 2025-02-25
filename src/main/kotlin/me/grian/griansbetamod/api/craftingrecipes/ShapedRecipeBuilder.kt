package me.grian.griansbetamod.api.craftingrecipes

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.recipe.CraftingRegistry

class ShapedRecipeBuilder {
    private val pattern: Array<String> = Array(3) { "" }
    private lateinit var output: ItemStack
    private val keys: MutableMap<Char, ItemStack> = mutableMapOf()
    private val elements = ArrayDeque(('a'..'z').toList())
//
//    fun key(c: Char, item: ItemStack) {
//        keys[c] = item
//    }
//
//    fun top(str: String) {
//        checkLength(str)
//        pattern[0] = str
//    }
//
//    fun middle(str: String) {
//        checkLength(str)
//        pattern[1] = str
//    }
//
//    fun bottom(str: String) {
//        checkLength(str)
//        pattern[2] = str
//    }

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
        if (itemStack == null) {
            return ' '
        } else if (keys.values.contains(itemStack)) {
            return keys.entries.filter { it.value == itemStack }[0].key
        } else {
            val key = elements.removeFirst()
            keys[key] = itemStack
            return key
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
}