package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.color.world.GrassColors
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

// 39 reg grass block id
class DeadGrassPlantBlock(identifier: Identifier) : TemplatePlantBlock(identifier, 39) {
    init {
        // ref TallPlantBlock
        this.setBoundingBox(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F)
    }

    override fun getColorMultiplier(blockView: BlockView, x: Int, y: Int, z: Int): Int {
        return 0x919191
    }

    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return 0
    }

    override fun canGrow(world: World, x: Int, y: Int, z: Int): Boolean =
        world.getBlockId(x, y-1, z) == BetaMod.deadGrass.id || world.getBlockId(x, y-1, z) == BetaMod.deadDirt.id
}