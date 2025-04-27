package me.grian.griansbetamod.blocks

import net.minecraft.block.material.Material
import net.minecraft.util.math.Box
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class PileOfLogsBlock(identifier: Identifier) : TemplateBlock(identifier, Material.WOOD) {
    override fun isFullCube(): Boolean = false
    override fun isOpaque(): Boolean = false

    override fun getCollisionShape(world: World?, x: Int, y: Int, z: Int): Box = getBox(x, y, z)
    override fun getBoundingBox(world: World?, x: Int, y: Int, z: Int): Box = getBox(x, y, z)

    private fun getBox(x: Int, y: Int, z: Int): Box =
        Box.createCached(
            x.toDouble(),
            y.toDouble(),
            z.toDouble(),
            x.toDouble() + 1,
            y.toDouble() + 0.75,
            z.toDouble() + 1
        )
}