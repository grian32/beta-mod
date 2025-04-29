package me.grian.griansbetamod.util

import me.grian.griansbetamod.mixin.ClientNetworkHandlerAccessor
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.network.NetworkHandler

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

fun Block.asItemStack(): ItemStack = ItemStack(this, 1)

fun NetworkHandler?.toClientAccessor() = this as ClientNetworkHandlerAccessor