package me.grian.griansbetamod.hell

import net.minecraft.block.Block
import net.minecraft.util.math.Vec3i
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.Random
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class DescentGateFeature : Feature() {
    override fun generate(world: World, random: Random, x: Int, y: Int, z: Int): Boolean {
        val chunkX = Math.floorDiv(x, 16)
        val chunkZ = Math.floorDiv(z, 16)

        val gateCoords = gateForChunk(world.seed, chunkX, chunkZ) ?: return false
        val gateX = gateCoords.x
        val gateY = gateCoords.y
        val gateZ = gateCoords.z

        for (offsetY in 0 until 42) {
            val wallRadius = if (offsetY < 12) {
                6.0
            } else {
                getUpperRadius(offsetY)
            }
            val innerRadius = wallRadius - 3.0

            for (offsetX in -24..24) {
                for (offsetZ in -24..24) {
                    val radius = sqrt((offsetX * offsetX + offsetZ * offsetZ).toDouble())

                    if (offsetY == 0) {
                        if (radius <= wallRadius) {
                            world.setBlock(gateX + offsetX, gateY, gateZ + offsetZ, Block.BEDROCK.id)
                        }
                        continue
                    }

                    val removedUpperSector = offsetY >= 12 && abs(offsetX) >= abs(offsetZ)
                    when {
                        radius < innerRadius -> {
                            val blockId = if (offsetY == 1) Block.GOLD_BLOCK.id else 0
                            world.setBlock(gateX + offsetX, gateY + offsetY, gateZ + offsetZ, blockId)
                        }
                        removedUpperSector && radius <= wallRadius -> {
                            world.setBlock(gateX + offsetX, gateY + offsetY, gateZ + offsetZ, 0)
                        }
                        radius <= wallRadius -> {
                            world.setBlock(gateX + offsetX, gateY + offsetY, gateZ + offsetZ, Block.BEDROCK.id)
                        }
                    }
                }
            }
        }

        generateDroopingTips(world, gateX, gateY, gateZ)
        println("genned descent gate @ $gateX $gateY $gateZ")

        return true
    }

    private fun getUpperRadius(offsetY: Int): Double {
        val progress = (offsetY - 12).toDouble() / (41 - 12)
        return 6.0 + (20.0 - 6.0) * progress.pow(2.25)
    }

    private fun generateDroopingTips(world: World, centerX: Int, bottomY: Int, centerZ: Int) {
        for (extension in 1..4) {
            val progress = extension.toDouble() / 4
            val radius = 20 + extension
            val offsetY = 41 - (4 * progress * progress).roundToInt()

            for (verticalThickness in 0 until 3) {
                for (offsetX in -24..24) {
                    for (offsetZ in -24..24) {
                        if (abs(offsetX) >= abs(offsetZ)) continue

                        val blockRadius = sqrt((offsetX * offsetX + offsetZ * offsetZ).toDouble())
                        if (blockRadius in (radius - 3.0)..radius.toDouble()) {
                            world.setBlock(
                                centerX + offsetX,
                                bottomY + offsetY - verticalThickness,
                                centerZ + offsetZ,
                                Block.BEDROCK.id
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        // DESCENT
        private const val SALT = 0x44455343454E54L

        fun gateForRegion(seed: Long, regionX: Int, regionZ: Int): Vec3i {
            val regionSeed = seed xor
                    (regionX.toLong() * 341873128712L) xor
                    (regionZ.toLong() * 132897987541L) xor
                    SALT

            val random = Random(regionSeed)

            val chunkX = regionX * 17 + 8 + random.nextInt(5) - 2
            val chunkZ = regionZ * 17 + 8 + random.nextInt(5) - 2

            return Vec3i(chunkX * 16 + 8, 0, chunkZ * 16 + 8)
        }

        fun gateForChunk(seed: Long, chunkX: Int, chunkZ: Int): Vec3i? {
            val regionX = Math.floorDiv(chunkX, 17)
            val regionZ = Math.floorDiv(chunkZ, 17)

            val gate = gateForRegion(seed, regionX, regionZ)
            val gateChunkX = Math.floorDiv(gate.x, 16)
            val gateChunkZ = Math.floorDiv(gate.z, 16)

            return if (gateChunkX == chunkX && gateChunkZ == chunkZ) {
                gate
            } else {
                null
            }
        }

        @JvmStatic
        fun nearestGate(seed: Long, x: Double, z: Double): Vec3i {
            val chunkX = floor(x / 16.0).toInt()
            val chunkZ = floor(z / 16.0).toInt()
            val regionCenterX = Math.floorDiv(chunkX, 17) * 17 + 8
            val regionCenterZ = Math.floorDiv(chunkZ, 17) * 17 + 8

            for (ccx in regionCenterX - 2..regionCenterX + 2) {
                for (ccz in regionCenterZ - 2..regionCenterZ + 2) {
                    val gate = gateForChunk(seed, ccx, ccz)
                    if (gate != null) return gate
                }
            }

            error("No descent gate found in region")
        }
    }
}
