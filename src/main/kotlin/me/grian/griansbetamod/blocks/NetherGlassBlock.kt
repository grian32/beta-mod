package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.shrine.ShrineActivatedSender
import me.grian.griansbetamod.shrine.ShrineState
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
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

    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity): Boolean {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment) return false

        // mp client
        if (world.isRemote) return true

        // sp client
        val state = ShrineState.get(world)
        state.setShrineState(!state.shrineActivated)

        // mp server
        if (FabricLoader.getInstance().environmentType == EnvType.SERVER) {
            // looks nonsensical but really i gotta keep serverplayerentity out the imports otherwise itll crash cuz it cant load that on server :S!!!
            ShrineActivatedSender.send(player, state.shrineActivated)
        }

        return true
    }
}
