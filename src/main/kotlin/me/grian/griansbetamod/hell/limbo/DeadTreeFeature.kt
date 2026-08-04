package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class DeadTreeFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        if (world.getBlockId(x, y - 1, z) != BetaMod.deadGrass.id) return false
        if (random.nextInt(7) != 0) return false

        val trunkHeight = 6 + random.nextInt(3)

        for (dy in 0..trunkHeight) {
            world.setBlock(x, y + dy, z, Block.LOG.id, 0)
        }

        repeat(5) {
            val dx = if (random.nextInt(2) == 0) -1 else 1
            val dz = if (random.nextInt(2) == 0) -1 else 1
            val dy = random.nextInt(trunkHeight - 2, trunkHeight)

            world.setBlock(x + dx, y + dy, z + dz, Block.LOG.id, 0)
            if (random.nextBoolean()) {
                world.setBlock(x + dx, (y + dy - 1).coerceAtLeast(trunkHeight - 2), z + dz, Block.LOG.id, 0)
            }
        }

        return true
    }
}
