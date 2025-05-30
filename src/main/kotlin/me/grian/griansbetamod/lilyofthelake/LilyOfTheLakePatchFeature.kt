package me.grian.griansbetamod.lilyofthelake

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class LilyOfTheLakePatchFeature : Feature() {
    override fun generate(world: World?, random: Random?, x: Int, y: Int, z: Int): Boolean {
        if (random!!.nextInt(4) != 0) return false


        if (isOnShore(world!!, x, z)) {
            // TODO: maybe tone down scale?
            repeat(25) {
                val genX = x + random.nextInt(10)
                val genZ = z + random.nextInt(10)

                if (world.getMaterial(genX, y, genZ) == Material.WATER) {
                    world.setBlockWithoutNotifyingNeighbors(genX, y + 1, genZ, BetaMod.lilyOfTheLake.id)
                    println("we generated @ $genX, ${y + 1}, $genZ")
                }
            }
        }
        return true
    }

    private fun isOnShore(world: World, x: Int, z: Int): Boolean {
        // Check if any adjacent block is water (north, south, east, west)
        return world.getBlockId(x + 1, 63, z) != Block.WATER.id ||
                world.getBlockId(x - 1, 63, z) != Block.WATER.id ||
                world.getBlockId(x, 63, z + 1) != Block.WATER.id ||
                world.getBlockId(x, 63, z - 1) != Block.WATER.id
    }
}
