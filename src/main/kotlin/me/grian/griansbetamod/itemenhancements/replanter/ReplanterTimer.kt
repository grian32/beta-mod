package me.grian.griansbetamod.itemenhancements.replanter

import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.modificationstation.stationapi.api.event.tick.GameTickEvent

object ReplanterTimer {
    // pos, timer, world
    val blocks: MutableMap<BlockPos, Pair<Int, World>> = mutableMapOf()

    @EventListener
    fun worldTick(event: GameTickEvent.End) {
        // TODO: more particles
        if (blocks.isEmpty()) return

        val blocksCopy = blocks.entries.toList()

        for (block in blocksCopy) {
            if (block.value.first <= 1) {
                val world = block.value.second

                world.addGrowthParticle(block.key, 0.25, 0.5)
                world.addGrowthParticle(block.key, 0.75, 0.5)
                world.addGrowthParticle(block.key, 0.5, 0.25)
                world.addGrowthParticle(block.key, 0.5, 0.75)

                world.setBlock(block.key.x, block.key.y, block.key.z, Block.WHEAT.id, 0)
                blocks.remove(block.key)
            } else {
                blocks[block.key] = block.value.first - 1 to block.value.second
            }
        }
    }

    @JvmStatic
    fun registerTimer(pos: BlockPos, world: World) {
        blocks[pos] = 5 to world
    }

    private fun World.addGrowthParticle(pos: BlockPos, modifierX: Double, modifierZ: Double) {
        addParticle(
            "reddust",
            pos.x + modifierX,
            pos.y + 0.75,
            pos.z + modifierZ,
            -1.0, // red - 1.0 default so have to negate it
            1.0, // green
            0.0 // blue
        )
    }
}
