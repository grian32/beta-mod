package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.api.tooltip.buildTooltip
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent
import net.modificationstation.stationapi.api.util.Formatting

object TooltipListener {
    @EventListener
    fun register(event: TooltipBuildEvent) {
        if (event.itemStack.itemId == BetaMod.frostRootSeeds.id) {
            event.add(buildTooltip {
                aqua("Frost-touched")
                darkGray("seeds that thrive")
            })

            event.add(buildTooltip {
                darkGray("only in")
                aqua("cold biomes.")
            })
        }
    }
}
