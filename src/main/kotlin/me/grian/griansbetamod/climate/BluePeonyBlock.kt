package me.grian.griansbetamod.climate

import it.unimi.dsi.fastutil.floats.Float2IntArrayMap
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.BlockItem
import net.minecraft.util.math.Box
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock
import net.modificationstation.stationapi.api.template.item.TemplateBlockItem
import net.modificationstation.stationapi.api.util.Identifier

class BluePeonyBlock(identifier: Identifier) : TemplateBlock(identifier, Material.PLANT) {
    override fun getRenderType(): Int {
        return 1
    }

    override fun isOpaque(): Boolean {
        return false
    }

    override fun isFullCube(): Boolean {
        return false
    }

    override fun getCollisionShape(world: World?, x: Int, y: Int, z: Int): Box? {
        return null
    }

    override fun canPlaceAt(world: World, x: Int, y: Int, z: Int): Boolean {
        val blockBelow = world.getBlockId(x, y - 1, z);
        return blockBelow == GRASS_BLOCK.id || blockBelow == DIRT.id || blockBelow == FARMLAND.id
    }
}