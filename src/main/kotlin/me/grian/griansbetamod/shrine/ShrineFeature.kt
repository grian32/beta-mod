package me.grian.griansbetamod.shrine

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*
import kotlin.math.abs

class ShrineFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        for (dx in -2..2) {
            for (dz in -2..2) {
                // take out corners
                if (abs(dx) == 2 && abs(dz) == 2) continue
                if (dx == 0 && dz == 0) {
                    // replace w center moon block l8r
                    world.setBlock(x + dx, y, z + dz, BetaMod.shrineCenter.id)
                } else {
                    world.setBlock(x + dx, y, z + dz, getBaseBlock(random))
                }
            }
        }

        // cardinal pillars
        for (dy in 1..3) {
            val block = if (dy == 3) {
                BetaMod.goldStone.id
            } else {
                getBaseBlock(random)
            }
            world.setBlock(x - 2, y + dy, z, block)
            world.setBlock(x + 2, y + dy, z, block)
            world.setBlock(x, y + dy, z - 2, block)
            world.setBlock(x, y + dy, z + 2, block)
        }

        return true
    }

    private fun getBaseBlock(random: Random): Int =
        if (random.nextInt(3) == 0) Block.COBBLESTONE.id else Block.STONE.id

}
