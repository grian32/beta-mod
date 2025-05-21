package me.grian.griansbetamod.blocks;

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class LilyOfTheLakeBlock(identifier: Identifier) : TemplateBlock(identifier, Material.PLANT) {
    override fun canPlaceAt(world: World?, x: Int, y: Int, z: Int, side: Int): Boolean {
        val blockId = world!!.getBlockId(x, y - 1, z)
        return blockId == Block.WATER.id
    }

    override fun getRenderType() = 1 // cross
    override fun isOpaque() = false
    override fun isFullCube() = false
    override fun getCollisionShape(world: World?, x: Int, y: Int, z: Int) = null
}