package me.grian.griansbetamod.shrine

import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.OverworldDimension
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent

object WorldGenListener {
    @EventListener
    fun listener(event: WorldGenEvent.ChunkDecoration) {
        if (event.world.dimension !is OverworldDimension) return
        if (event.random.nextInt(300) != 0) return

        val featureX = event.x + event.random.nextInt(16) + 8
        val featureZ = event.z + event.random.nextInt(16) + 8
        val featureY = event.world.getTopY(featureX, featureZ)

        if (!isAreaClear(event.world, featureX, featureY, featureZ)) return

        ShrineFeature().generate(event.world, event.random, featureX, featureY, featureZ)
        println("Generated shrine @ $featureX $featureY $featureZ")
    }

    private fun isAreaClear(world: World, x: Int, y: Int, z: Int): Boolean {
        for (dx in -2..2) {
            for (dz in -2..2) {
                val blockBelow = world.getBlockId(x+dx, y-1, z+dz)
                // FIXME: this kinda sucks
                if (blockBelow == Block.ICE.id || blockBelow == Block.WATER.id || blockBelow == Block.FLOWING_WATER.id || blockBelow == Block.LEAVES.id || blockBelow == 0) {
                    return false
                }

                for (dy in 0..4) {
                    if (!world.isAir(x+dx, y+dy, z+dz)) return false
                }
            }
        }

        return true
    }
}
