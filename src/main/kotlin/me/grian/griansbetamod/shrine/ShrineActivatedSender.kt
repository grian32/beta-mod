package me.grian.griansbetamod.shrine

import me.grian.griansbetamod.network.ShrineActivatedPacket
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity

@Environment(EnvType.SERVER)
object ShrineActivatedSender {
    fun send(player: PlayerEntity, activated: Boolean) {
        val serverPlayer = player as? ServerPlayerEntity ?: return
        serverPlayer.server.playerManager.sendToAll(
            ShrineActivatedPacket(activated)
        )
    }
}
