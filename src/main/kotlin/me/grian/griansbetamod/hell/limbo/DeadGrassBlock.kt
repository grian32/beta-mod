package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.mixinutil.LandscaperCommon
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.stat.Stats
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class DeadGrassBlock(identifier: Identifier) : TemplateBlock(identifier, Material.SOLID_ORGANIC) {
    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return BetaMod.deadDirt.asItem().id
    }

    override fun afterBreak(world: World, playerEntity: PlayerEntity, x: Int, y: Int, z: Int, meta: Int) {
        val drop = LandscaperCommon.getDrop(playerEntity, this)

        if (drop != null) {
            dropStack(world, x, y, z, drop)
            playerEntity.increaseStat(Stats.MINE_BLOCK[id], 1)
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta)
        }
    }
}