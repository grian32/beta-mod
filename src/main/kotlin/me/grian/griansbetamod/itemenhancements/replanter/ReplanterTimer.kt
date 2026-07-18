package me.grian.griansbetamod.itemenhancements.replanter

import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.modificationstation.stationapi.api.event.tick.GameTickEvent

private data class Location(
    val world: World,
    val pos: BlockPos
)

private data class PendingReplant(
    val cropId: Int,
    var ticksRemaining: Int = 5
)

object ReplanterTimer {
    private val replants: MutableMap<Location, PendingReplant> = mutableMapOf()

    @EventListener
    fun worldTick(event: GameTickEvent.End) {
        if (replants.isEmpty()) return

        val iterator = replants.iterator()

        while (iterator.hasNext()) {
            val entry = iterator.next()

            if (entry.value.ticksRemaining - 1 <= 0) {
                val world = entry.key.world
                val pos = entry.key.pos
                if (world.isAir(pos.x, pos.y, pos.z)) {
                    world.addGrowthParticle(pos, 0.25, 0.5)
                    world.addGrowthParticle(pos, 0.75, 0.5)
                    world.addGrowthParticle(pos, 0.5, 0.25)
                    world.addGrowthParticle(pos, 0.5, 0.75)

                    world.setBlock(pos.x, pos.y, pos.z, entry.value.cropId, 0)
                }
                iterator.remove()
            } else {
                entry.value.ticksRemaining--
            }
        }
    }

    @JvmStatic
    fun registerTimer(pos: BlockPos, world: World, cropBlockId: Int) {
        replants[Location(world, pos)] = PendingReplant(cropBlockId)
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
