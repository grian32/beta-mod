package me.grian.griansbetamod.lilyofthelake;

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class LilyOfTheLakeBlock(identifier: Identifier) : TemplateBlock(identifier, Material.PLANT) {
    override fun canPlaceAt(world: World?, x: Int, y: Int, z: Int, side: Int): Boolean {
        val blockId = world!!.getBlockId(x, y - 1, z)
        return blockId == Block.WATER.id
    }

    override fun isOpaque() = false
    override fun isFullCube() = false
    override fun getCollisionShape(world: World?, x: Int, y: Int, z: Int) = null

    override fun getLuminance(
        blockView: BlockView,
        x: Int,
        y: Int,
        z: Int
    ): Float {
        return if (
            blockView.method_1781().getBiome(x, z) == Biome.SWAMPLAND
        ) {
            blockView.getNaturalBrightness(x, y, z, 15)
        } else {
            super.getLuminance(blockView, x, y, z)
        }
    }
}