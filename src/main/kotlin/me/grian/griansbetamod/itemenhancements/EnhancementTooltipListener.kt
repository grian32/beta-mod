package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent

object EnhancementTooltipListener {
    @EventListener
    fun buildTooltip(event: TooltipBuildEvent) {
        if (ConfigScreen.config.enhancementSystem) {
            val stack = event.itemStack

            val enhancement = stack.getEnhancement()
            val enhancementTier = stack.getEnhancementTier().toTierString()

            if (enhancement == Enhancement.NONE) return

            val enhancementTooltip = buildString {
                append(enhancement.guiName)
                if (enhancement.hasTiers) append(" $enhancementTier")
            }

            event.add(enhancement.color.toString() + enhancementTooltip)
        }
    }

    private fun Int.toTierString(): String =
        when (this) {
            1 -> "I"
            2 -> "II"
            3 -> "III"
            4 -> "IV"
            5 -> "v"
            else -> ""
        }

}