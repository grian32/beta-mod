package me.grian.griansbetamod.biome.swamp

import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.Feature
import java.util.Random
import kotlin.math.abs

class CrookedOakTreeFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        if (world.method_1781().getBiome(x, z) != Biome.SWAMPLAND) return false

        val height = random.nextInt(5) + 7
        if (y < 1 || y + height + 1 > 128) return false

        val ground = world.getBlockId(x, y - 1, z)
        if (ground != Block.GRASS_BLOCK.id && ground != Block.DIRT.id) return false

        val direction = DIRECTIONS[random.nextInt(DIRECTIONS.size)]
        val bendLevels = mutableSetOf(3 + random.nextInt(2))
        if (random.nextInt(4) == 0) {
            bendLevels += (bendLevels.first() + 2).coerceAtMost(height - 2)
        }

        val trunk = mutableListOf<Position>()
        var trunkX = x
        var trunkZ = z

        for (level in 0 until height) {
            trunk += Position(trunkX, y + level, trunkZ)

            if (level in bendLevels) {
                trunkX += direction.first
                trunkZ += direction.second
                trunk += Position(trunkX, y + level, trunkZ)
            }
        }

        val canopyY = y + height - 1
        if (!hasRoom(world, trunk, trunkX, canopyY, trunkZ)) return false

        world.setBlockWithoutNotifyingNeighbors(x, y - 1, z, Block.DIRT.id)

        for (leafY in canopyY - 2..canopyY + 1) {
            val topLayer = leafY == canopyY + 1
            val radius = if (topLayer) 1 else 2

            for (dx in -radius..radius) {
                for (dz in -radius..radius) {
                    val corner = abs(dx) == radius && abs(dz) == radius
                    if (corner && (topLayer || random.nextInt(3) == 0)) continue

                    val leafX = trunkX + dx
                    val leafZ = trunkZ + dz
                    if (!Block.BLOCKS_OPAQUE[world.getBlockId(leafX, leafY, leafZ)]) {
                        world.setBlockWithoutNotifyingNeighbors(
                            leafX,
                            leafY,
                            leafZ,
                            Block.LEAVES.id
                        )
                    }
                }
            }
        }

        trunk.forEach { position ->
            world.setBlockWithoutNotifyingNeighbors(
                position.x,
                position.y,
                position.z,
                Block.LOG.id
            )
        }

        return true
    }

    private fun hasRoom(
        world: World,
        trunk: List<Position>,
        canopyX: Int,
        canopyY: Int,
        canopyZ: Int
    ): Boolean {
        if (trunk.any { !isReplaceable(world.getBlockId(it.x, it.y, it.z)) }) {
            return false
        }

        for (leafY in canopyY - 2..canopyY + 1) {
            val radius = if (leafY == canopyY + 1) 1 else 2
            for (dx in -radius..radius) {
                for (dz in -radius..radius) {
                    if (!isReplaceable(world.getBlockId(canopyX + dx, leafY, canopyZ + dz))) {
                        return false
                    }
                }
            }
        }

        return true
    }

    private fun isReplaceable(blockId: Int): Boolean {
        return blockId == 0
    }

    private data class Position(
        val x: Int,
        val y: Int,
        val z: Int
    )

    companion object {
        private val DIRECTIONS = arrayOf(
            -1 to 0,
            1 to 0,
            0 to -1,
            0 to 1
        )
    }
}
