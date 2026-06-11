package me.grian.griansbetamod

import me.grian.griansbetamod.network.EnhancementTableScreenPacket
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry
import net.modificationstation.stationapi.api.registry.Registry

object PacketListener {
    @EventListener
    fun registerPacket(event: PacketRegisterEvent) {
        Registry.register(PacketTypeRegistry.INSTANCE, EnhancementTableScreenPacket.ID, EnhancementTableScreenPacket.TYPE)
    }
}