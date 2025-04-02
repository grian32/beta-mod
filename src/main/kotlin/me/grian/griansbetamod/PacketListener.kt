package me.grian.griansbetamod

import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.mixin.ClientNetworkHandlerAccessor
import me.grians.griansbetamod.mixininterfaces.IPlayerEntityMixin
import net.modificationstation.stationapi.api.util.Namespace
import net.glasslauncher.mods.networking.GlassPacketListener

object PacketListener : GlassPacketListener {
    private val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    override fun registerGlassPackets() {
        if (ConfigScreen.config.lapisSpeedBoost) {
            registerGlassPacket("griansbetamod:speedticks", { glassPacket, networkHandler ->
                val player = ((networkHandler as ClientNetworkHandlerAccessor).minecraft.player as IPlayerEntityMixin)
                player.`beta_mod$setSpeedBoostTicks`(glassPacket.nbt.getInt("speedTicks"))
            }, true, false)
        }
    }
}