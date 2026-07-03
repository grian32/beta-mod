package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class IcyDungeonFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        //center room
        world.genCenterRoom(x, y, z, random)
        world.genXRoom(x, y, z, random, false)
        world.genXRoom(x, y, z, random, true)
        world.genZRoom(x, y, z, random, false)
        world.genZRoom(x, y, z, random, true)
        return true
    }

    private fun World.genCenterRoom(x: Int, y: Int, z: Int, rand: Random) {
        genFlat(x, y, z, rand, false)
        genFlat(x, y + 6, z, rand, true)
        // TODO: move entrances here since theyre part of the entrance
        // TODO: carve center hole 

        val minX = x - 3
        val maxX = x + 3
        val minZ = z - 3
        val maxZ = z + 3

        for (cy in 1..5) {
            for (dx in minX..maxX) {
                for (dz in minZ..maxZ) {
                    setBlock(dx, cy + y, dz, 0)
                }
            }
        }

        for (cy in 1..5) {
            setBlock(minX, cy + y, minZ, getGenBlock(rand, cy))
            setBlock(minX, cy + y, maxZ, getGenBlock(rand, cy))
            setBlock(maxX, cy + y, minZ, getGenBlock(rand, cy))
            setBlock(maxX, cy + y, maxZ, getGenBlock(rand, cy))
        }


        repeat(2) { i ->
            // entrance x axis
            var entranceX = x
            if (i == 0) {
                entranceX -= 3
            } else {
                entranceX += 3
            }

            for (dy in 1..5) {
                setBlock(entranceX, dy + y, minZ + 1, getGenBlock(rand, dy))
                setBlock(entranceX, dy + y, maxZ - 1, getGenBlock(rand, dy))
            }

            for (cz in minZ - 1..<maxZ) {
                // AT dy = 5 getgenblock always returns icystone, so no need..
                setBlock(entranceX, y + 5, cz, BetaMod.icyStone.id)
            }

            // corners around entrance
            setBlock(entranceX, y + 4, z + 1, getGenBlock(rand, 4))
            setBlock(entranceX, y + 4, z - 1, getGenBlock(rand, 4))

            // z entrances
            var entranceZ = z
            if (i == 0) {
                entranceZ -= 3
            } else {
                entranceZ += 3
            }

            for (dy in 1..5) {
                setBlock(minX + 1, dy + y, entranceZ, getGenBlock(rand, dy))
                setBlock(maxX - 1, dy + y, entranceZ, getGenBlock(rand, dy))
            }

            for (cx in minX - 1..<maxX) {
                // AT dy = 5 getgenblock always returns icystone, so no need..
                setBlock(cx, y + 5, entranceZ, BetaMod.icyStone.id)
            }

            // corners around entrance
            setBlock(x + 1, y + 4, entranceZ, getGenBlock(rand, 4))
            setBlock(x - 1, y + 4, entranceZ, getGenBlock(rand, 4))
        }
    }

    private fun World.genXRoom(x: Int, y: Int, z: Int, rand: Random, positive: Boolean) {
        var centerX = x
        if (positive) {
            centerX += 7
        } else {
            centerX -= 7
        }
        genFlat(centerX, y, z, rand, false)
        genFlat(centerX, y + 6, z, rand, true)

        val minX = centerX - 3
        val maxX = centerX + 3

        // neg z wall -- left
        for (dy in 1..5) {
            for (cx in minX..maxX) {
                setBlock(cx, dy + y, z - 3, getGenBlock(rand, dy))
            }
        }

        // pos z wall -- right
        for (dy in 0..5) {
            for (cx in minX..maxX) {
                setBlock(cx, dy + y, z + 3, getGenBlock(rand, dy))
            }
        }

        // neg x wall -- front
        val minZ = z - 2
        val maxZ = z + 2
        var cx = centerX
        if (positive) {
            cx += 3
        } else {
            cx -= 3
        }

        for (dy in 1..5) {
            for (cz in minZ..maxZ) {
                setBlock(cx, dy + y, cz, getGenBlock(rand, dy))
            }
        }

        // clean out w/ air
        val clearMinX = if (positive) centerX - 3 else centerX - 2
        val clearMaxX = if (positive) centerX + 2 else centerX + 3
        for (dy in 1..5) {
            for (cx in clearMinX..clearMaxX) {
                for (cz in z - 2..z + 2) {
                    setBlock(cx, dy + y, cz, 0)
                }
            }
        }

        genRubble(x, y, z, rand, positive)
    }

    private fun World.genRubble(x: Int, y: Int, z: Int, rand: Random, positive: Boolean) {
        var maxX = x
        var minX = x
        if (positive) {
            maxX += 9
            minX += 7
        } else {
            maxX -= 7
            minX -= 9
        }

        val minZ = z - 2
        val maxZ = z + 2

        for (cx in minX..maxX) {
            for (cz in minZ..maxZ) {
                val rubbleBlock = if (rand.nextInt(2) == 0)  {
                    Block.GRAVEL.id
                } else {
                    Block.ICE.id
                }

                setBlock(cx, y + 1, cz, rubbleBlock)

                if (rand.nextInt(3) == 0) {
                    setBlock(cx, y + 2, cz, rubbleBlock)
                } else {
                    setBlock(cx, y + 2, cz, Block.SNOW.id)
                }
            }
        }

        val frontX = if (positive) {
            minX - 1
        } else {
            maxX + 1
        }

        // random gen ahead of the already genned so it doesnt cutoff randomly
        for (dx in 0..1) {
            for (cz in minZ..maxZ) {
                if (rand.nextInt(2) == 0) {
                    setBlock(frontX+dx, y + 1, cz, Block.GRAVEL.id)
                }
            }
        }
    }

    private fun World.genZRoom(x: Int, y: Int, z: Int, rand: Random, positive: Boolean) {
        var centerZ = z
        if (positive) {
            centerZ += 7
        } else {
            centerZ -= 7
        }
        genFlat(x, y, centerZ, rand, false)
        genFlat(x, y + 6, centerZ, rand, true)

        val minZ = centerZ - 3
        val maxZ = centerZ + 3

        // neg z wall -- left
        for (dy in 1..5) {
            for (cz in minZ..maxZ) {
                setBlock(x - 3, dy + y, cz, getGenBlock(rand, dy))
            }
        }

        // pos z wall -- right
        for (dy in 1..5) {
            for (cz in minZ..maxZ) {
                setBlock(x + 3, dy + y, cz, getGenBlock(rand, dy))
            }
        }

        // neg x wall -- front
        val minX = x - 2
        val maxX = x + 2
        var cz = centerZ
        if (positive) {
            cz += 3
        } else {
            cz -= 3
        }

        for (dy in 1..5) {
            for (cx in minX..maxX) {
                setBlock(cx, dy + y, cz, getGenBlock(rand, dy))
            }
        }

        val clearMinZ = if (positive) centerZ - 3 else centerZ - 2
        val clearMaxZ = if (positive) centerZ + 2 else centerZ + 3
        for (dy in 1..5) {
            for (cz in clearMinZ..clearMaxZ) {
                for (cx in x - 2..x + 2) {
                    setBlock(cx, dy + y, cz, 0)
                }
            }
        }
    }

    private fun World.genFlat(x: Int, y: Int, z: Int, random: Random, ceiling: Boolean) {
        val minX = x - 3;
        val maxX = x + 3;

        val minZ = z - 3;
        val maxZ = z + 3;

        val blockId = if (ceiling) {
            BetaMod.icyStone.id
        } else {
            BetaMod.icyCobblestone.id
        }

        for (cx in minX..maxX) {
            for (cz in minZ..maxZ) {
                // deffo want icy cobblestone on the floor so hardcoding dy
                setBlock(cx, y, cz, blockId)
            }
        }
    }

    private fun getGenBlock(random: Random, dy: Int): Int {
        if (dy < 4) {
            if (random.nextFloat(1.5f) >= 0.5f) {
                return BetaMod.icyCobblestone.id
            }
        } else if (dy == 4) {
            if (random.nextFloat(1.5f) >= 1.0f) {
                return BetaMod.icyCobblestone.id
            }
        }

        return BetaMod.icyStone.id
    }
}
