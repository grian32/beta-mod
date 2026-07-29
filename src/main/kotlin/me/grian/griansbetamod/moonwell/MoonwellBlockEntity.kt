package me.grian.griansbetamod.moonwell

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import me.grian.griansbetamod.shrine.ShrineState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

class MoonwellBlockEntity : BlockEntity() {
    private var item: ItemStack? = null
    private var tickCounter: Int = 0

    fun getStack(): ItemStack? = item

    fun setItemStack(v: ItemStack?, checkEnhancement: Boolean): Boolean {
        if (v == null) {
            item = v
            return true
        }

        if (!checkEnhancement || (v.getEnhancement() != Enhancement.NONE && v.getEnhancementTier() > 0)) {
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

    override fun tick() {
        if (world.isRemote) return
        if (item == null) return
        if (item!!.damage == 0) return
        val dayTime = world.time % 24000L
        val isNight = dayTime in 13000L..<23000L

        if (isNight) {
            if (ShrineState.get(world).shrineActivated) {
                tickCounter += 2
            } else {
                tickCounter += 1
            }
        }

        if (tickCounter >= 12) {
            item!!.damage = (item!!.damage - 1).coerceAtLeast(0)
            tickCounter = 0
            markDirty()
        }
    }
}