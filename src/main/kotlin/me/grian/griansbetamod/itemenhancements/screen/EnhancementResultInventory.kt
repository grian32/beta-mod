package me.grian.griansbetamod.itemenhancements.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

class EnhancementResultInventory : Inventory {
    private val stacks = arrayOfNulls<ItemStack>(1)

    override fun size(): Int = 1

    override fun getStack(slot: Int): ItemStack? = stacks[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack? {
        if (stacks[slot] != null) {
            val stack = stacks[slot]!!
            stacks[slot] = null
            return stack
        }

        return null
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        stacks[slot] = stack
    }

    override fun getName(): String = "Result"

    override fun getMaxCountPerStack(): Int = 1

    override fun markDirty() {
    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean = true
}