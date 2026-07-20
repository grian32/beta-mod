package me.grian.griansbetamod.itemenhancements.quarryman

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.PickaxeItem
import net.modificationstation.stationapi.api.event.entity.player.PlayerStrengthOnBlockEvent

object MiningStrengthListener {
    @EventListener
    fun playerStrengthOnBlock(event: PlayerStrengthOnBlockEvent) {
        val selectedSlot = event.player.inventory.selectedItem ?: return

        if (selectedSlot.item !is PickaxeItem) return
        if (selectedSlot.getEnhancement() != Enhancement.QUARRYMAN || selectedSlot.getEnhancementTier() <= 0) return
        val pos = event.blockPos
        if (event.blockView.getBlockId(pos.x, pos.y, pos.z) !in AFFECTED_BLOCKS) return

        val previous = event.resultProvider
        val multiplier = getMultiplierForTier(selectedSlot.getEnhancementTier())

        println("nonmultiplied: ${previous.asFloat}; multiplier: $multiplier; multipliedAmount:${previous.asFloat * multiplier}; tier: ${selectedSlot.getEnhancementTier()}")
        event.resultProvider = PlayerStrengthOnBlockEvent.ResultProvider {
            previous.asFloat * multiplier
        }
    }

    private val AFFECTED_BLOCKS by lazy {
        arrayOf(
            Block.COBBLESTONE.id,
            Block.STONE.id,
            Block.MOSSY_COBBLESTONE.id,
            BetaMod.icyStone,
            BetaMod.icyCobblestone
        )
    }

    private fun getMultiplierForTier(tier: Int): Float =
        when (tier) {
            1 -> 1.25f
            2 -> 1.5f
            3 -> 2.0f
            else -> 1.0f
        }
}