package me.grian.griansbetamod.climate

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.BirchTreeFeature
import java.util.*

class FloweryBirchTreeFeature : BirchTreeFeature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        if (super.generate(world, random, x, y, z)) {
            var flowerAmount = random.nextInt(3, 6)
            var attemptAmount = flowerAmount * 12

            while (flowerAmount > 0 && attemptAmount > 0) {
                attemptAmount--

                val flowerX = x + random.nextInt(-3, 4)
                val flowerZ = z + random.nextInt(-3, 4)
                val flowerY = world.getTopY(flowerX, flowerZ)
                val blockId = world.getBlockId(flowerX, flowerY, flowerZ)
                if (
                    (blockId == 0 || blockId == Block.GRASS.id) &&
                    // block dandelion. can place was blocking me since it was checking for air and i wanna replace grass aswell
                    canPlantOnTop(world, flowerX, flowerY-1, flowerZ)
                ) {
                    if (random.nextInt(2) == 0) {
                        world.setBlockWithoutNotifyingNeighbors(flowerX, flowerY, flowerZ, Block.DANDELION.id)
                    } else {
                        world.setBlockWithoutNotifyingNeighbors(flowerX, flowerY, flowerZ, BetaMod.bluePeony.id)
                    }
                    flowerAmount--
                }
            }

            return true
        }
        return false
    }

    fun canPlantOnTop(world: World, x: Int, y: Int, z: Int): Boolean {
        val id = world.getBlockId(x, y, z)
        return id == Block.GRASS_BLOCK.id || id == Block.DIRT.id || id == Block.FARMLAND.id
    }
}
