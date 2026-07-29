package me.grian.griansbetamod.moonwell

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

class MoonwellBlockEntity : BlockEntity() {
    private var item: ItemStack? = null

    fun getStack(): ItemStack? = item

    fun setItemStack(v: ItemStack?): Boolean {
        if (v == null) {
            item = v
            return true
        }

        if (v.getEnhancement() != Enhancement.NONE && v.getEnhancementTier() > 0) {
            item = v
            return true
        }

        return false
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        if (item != null) {
            val itemNbt = NbtCompound()
            item!!.writeNbt(itemNbt)
            nbt.put("Item", itemNbt)
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        item = if (nbt.contains("Item")) {
            ItemStack(nbt.getCompound("Item"))
        } else {
            null
        }
    }

}