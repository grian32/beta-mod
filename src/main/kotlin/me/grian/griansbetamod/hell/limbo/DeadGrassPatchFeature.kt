package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.Random

class DeadGrassPatchFeature : Feature() {
    // ref GrassPatchFeature
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        repeat(random.nextInt(6)) {
            val plantX = x + random.nextInt(8) - random.nextInt(8)
            val plantZ = z + random.nextInt(8) - random.nextInt(8)
            val plantY = world.getTopY(plantX, plantZ)

            if (world.isAir(plantX, plantY, plantZ) &&
                BetaMod.deadGrassPlant.canGrow(world, plantX, plantY, plantZ)
            ) {
                world.setBlockWithoutNotifyingNeighbors(
                    plantX,
                    plantY,
                    plantZ,
                    BetaMod.deadGrassPlant.id,
                    0
                )
            }
        }

        return true
    }
}
