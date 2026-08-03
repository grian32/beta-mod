package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.hell.DimensionListener
import me.grian.griansbetamod.hell.limbo.LimboForcer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper

class RedstoneBlock(identifier: Identifier) : TemplateBlock(identifier, Material.METAL) {
    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity): Boolean {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment) return false

        DimensionHelper.switchDimension(
            player,
            DimensionListener.LIMBO_ID,
            0.125,
            LimboForcer()
        )
        return true
    }
}