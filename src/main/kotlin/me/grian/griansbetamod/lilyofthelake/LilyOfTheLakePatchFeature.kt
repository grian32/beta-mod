package me.grian.griansbetamod.lilyofthelake

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class LilyOfTheLakePatchFeature : Feature() {
    override fun generate(world: World?, random: Random?, x: Int, y: Int, z: Int): Boolean {
        // FIXME: generates underground, possible fix: just leave it or check if y level > 50 i wager is ok enough
        // TODO: make this generate in actual lakes and  make it very often, by actual lakes = solid blocks in 10 blocks of atleast 3 directions
        repeat(20) {
            val testX = x + random!!.nextInt(5) - random.nextInt(5)
            val testZ = z + random.nextInt(5) - random.nextInt(5)

            if (world!!.isAir(testX, y, testZ) && isOnShore(world, testX, y, testZ)) {
                val genX = testX + random.nextInt(2, 5)
                val genZ = testX + random.nextInt(2, 5)

                if (world.getMaterial(genX, y, genZ) == Material.WATER && world.isAir(genX, y + 5, genZ) && world.isAir(genX, y + 1, genZ)) {
                    world.setBlockWithoutNotifyingNeighbors(genX, y + 1, genZ, BetaMod.lilyOfTheLake.id)
                    println("we generated @ $genX, ${y+1}, $genZ")
                }
            }
        }

        return true
    }

    private fun isOnShore(world: World, x: Int, y: Int, z: Int): Boolean {
        // Check if any adjacent block is water (north, south, east, west)
        return world.getBlockId(x + 1, y, z) == Block.WATER.id ||
               world.getBlockId(x - 1, y, z) == Block.WATER.id ||
               world.getBlockId(x, y, z + 1) == Block.WATER.id ||
               world.getBlockId(x, y, z - 1) == Block.WATER.id
    }
}
