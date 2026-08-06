package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateSlabBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class DeadPlankSlabBlock(
    identifier: Identifier,
    private val doubleSlab: Boolean
) : TemplateSlabBlock(identifier, doubleSlab) {
    override fun getTexture(side: Int): Int = textureId

    override fun getTexture(side: Int, meta: Int): Int = textureId

    override fun onPlaced(world: World, x: Int, y: Int, z: Int) {
        if (doubleSlab) return

        val meta = world.getBlockMeta(x, y, z)
        if (
            world.getBlockId(x, y - 1, z) == BetaMod.deadPlankSlab.id &&
            world.getBlockMeta(x, y - 1, z) == meta
        ) {
            world.setBlock(x, y, z, 0)
            world.setBlock(x, y - 1, z, BetaMod.deadPlankDoubleSlab.id, meta)
        }
    }

    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int =
        BetaMod.deadPlankSlab.asItem().id

    override fun getDroppedItemCount(random: Random?): Int =
        if (doubleSlab) 2 else 1

}
