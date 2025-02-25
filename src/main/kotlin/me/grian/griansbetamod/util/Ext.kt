package me.grian.griansbetamod.util

import net.minecraft.item.ItemStack

fun Array<Any?>.isAllNull(): Boolean {
    for (i in this) {
        if (i != null) {
            return false
        }
    }

    return true
}

fun Array<ItemStack?>.toItemIds(): Array<Int?> {
    val arr: Array<Int?> = arrayOfNulls(this.size)

    for (i in this.indices) {
        arr[i] = if (this[i] == null) null else this[i]!!.itemId;
    }

    return arr
}