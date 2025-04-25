package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.itemenhancements.screen.EnhancementScreen
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class EnhancementTableBlock(identifier: Identifier) : TemplateBlock(identifier, Material.STONE) {
    @Suppress("DEPRECATION") // only applies in newer versions
    override fun onUse(world: World?, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        val minecraft = FabricLoader.getInstance().gameInstance as Minecraft;

        minecraft.setScreen(EnhancementScreen(player!!.inventory, world!!, x, y, z))

        return true
    }
}