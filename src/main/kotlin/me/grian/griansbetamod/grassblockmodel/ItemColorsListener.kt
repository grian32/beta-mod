package me.grian.griansbetamod.grassblockmodel

import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent

object ItemColorsListener {
    @EventListener
    fun register(event: ItemColorsRegisterEvent) {
        event.itemColors.register({_, tintIndex ->
            if (tintIndex == 0) 0x7CBD6B else 0xFFFFFF
        }, Block.GRASS_BLOCK)
    }
}
