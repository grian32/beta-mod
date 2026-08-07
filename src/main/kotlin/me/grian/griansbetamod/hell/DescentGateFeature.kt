package me.grian.griansbetamod.hell

import net.minecraft.block.Block
import net.minecraft.world.World
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object DescentGateFeature {
    fun generate(world: World, centerX: Int, bottomY: Int, centerZ: Int) {
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
                            world.setBlock(centerX + offsetX, bottomY + offsetY, centerZ + offsetZ, Block.BEDROCK.id)
                        }
                        continue
                    }

                    val removedUpperSector = offsetY >= 12 && abs(offsetX) >= abs(offsetZ)
                    when {
                        radius < innerRadius -> {
                            val blockId = if (offsetY == 1) Block.GOLD_BLOCK.id else 0
                            world.setBlock(centerX + offsetX, bottomY + offsetY, centerZ + offsetZ, blockId)
                        }
                        removedUpperSector && radius <= wallRadius -> {
                            world.setBlock(centerX + offsetX, bottomY + offsetY, centerZ + offsetZ, 0)
                        }
                        radius <= wallRadius -> {
                            world.setBlock(centerX + offsetX, bottomY + offsetY, centerZ + offsetZ, Block.BEDROCK.id)
                        }
                    }
                }
            }
        }

        generateDroopingTips(world, centerX, bottomY, centerZ)
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
}
