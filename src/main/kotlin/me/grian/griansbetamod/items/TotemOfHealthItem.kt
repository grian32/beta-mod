package me.grian.griansbetamod.items;

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Identifier

class TotemOfHealthItem(identifier: Identifier) : TemplateItem(identifier) {
    override fun inventoryTick(stack: ItemStack?, world: World?, entity: Entity?, slot: Int, selected: Boolean) {
        if (entity !is PlayerEntity) return
        if (stack == null) return

        val stationNbt = stack.stationNbt
        val ticksRemaining = stationNbt.getTicksRemaining()
        if (ticksRemaining == 0) {
            entity.heal(1)
            // 400 ticks = 10 seconds
            stationNbt.setTicksRemaining(400)
        } else if (ticksRemaining > 0) {
            stationNbt.setTicksRemaining((ticksRemaining - 1).coerceAtLeast(0))
        }
    }

    private fun NbtCompound.setTicksRemaining(ticks: Int) = putInt(TICKS_REMAINING, ticks)
    private fun NbtCompound.getTicksRemaining() = getInt(TICKS_REMAINING)

    companion object {
        const val TICKS_REMAINING = "ticksRemaining"
    }
}