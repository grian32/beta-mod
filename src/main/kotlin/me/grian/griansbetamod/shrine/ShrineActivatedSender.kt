package me.grian.griansbetamod.shrine

import me.grian.griansbetamod.network.ShrineActivatedPacket
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity

object ShrineActivatedSender {
    @JvmStatic
    fun send(player: PlayerEntity, activated: Boolean, playSound: Boolean = false) {
        val serverPlayer = player as? ServerPlayerEntity ?: return
        serverPlayer.server.playerManager.sendToAll(
            ShrineActivatedPacket(activated, playSound)
        )
    }
}
