package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.debug.DevTeleportScreen
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class StoneBricksBlock(identifier: Identifier) : TemplateBlock(identifier, Material.STONE) {
    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity): Boolean {
        val loader = FabricLoader.getInstance()
        if (!loader.isDevelopmentEnvironment) return false
        if (loader.environmentType != EnvType.CLIENT || world.isRemote) return false

        val minecraft = loader.gameInstance as Minecraft
        minecraft.setScreen(DevTeleportScreen(player))
        return true
    }
}
