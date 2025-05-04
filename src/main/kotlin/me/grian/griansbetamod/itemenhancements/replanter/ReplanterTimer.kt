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
        for (block in blocks) {
            if (block.value.first <= 1) {
                val world = block.value.second
                world.setBlock(block.key.x, block.key.y, block.key.z, Block.WHEAT.id, 0)
                world.addParticle(
                    "reddust",
                    block.key.x + 0.5,
                    block.key.y + 0.5,
                    block.key.z + 0.5,
                    -1.0,
                    1.0,
                    0.0
                )
                blocks.remove(block.key)
            } else {
                block.setValue(block.value.first - 1 to block.value.second)
            }
        }
    }

    @JvmStatic
    fun registerTimer(pos: BlockPos, world: World) {
        blocks[pos] = 3 to world
    }
}
