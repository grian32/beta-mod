package me.grian.griansbetamod.shrine

import me.grian.griansbetamod.network.ShrineActivatedPacket
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.server.event.network.PlayerLoginEvent

object LoginListener {
    @EventListener
    fun onPlayerLogin(event: PlayerLoginEvent) {
        val activated = ShrineState.get(event.player.world).shrineActivated
        event.player.networkHandler.sendPacket(
            ShrineActivatedPacket(activated)
        )
    }
}