package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.network.ShrineActivatedPacket
import me.grian.griansbetamod.shrine.ShrineState
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class NetherGlassBlock(identifier: Identifier) : TemplateTranslucentBlock(identifier, 0,Material.GLASS, false) {
    override fun getRenderLayer(): Int {
        return 0
//        return 2
    }

    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return 0
    }

    override fun getDroppedItemCount(random: Random?): Int {
        return 0
    }

    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment) return false
        if (world.isRemote) return true

        val ss = ShrineState.get(world)
        ss.setShrineState(!ss.shrineActivated)

        if (player is ServerPlayerEntity) {
            player.server.playerManager.sendToAll(
                ShrineActivatedPacket(ss.shrineActivated)
            )
        }
        return true;
    }
}