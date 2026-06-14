package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.BetaMod
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.Random

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

        val minX = x - 3
        val maxX = x + 3
        val minZ = z - 3
        val maxZ = z + 3

        for (cy in 0..5) {
            setBlock(minX, cy+y, minZ, getGenBlock(rand, cy))
            setBlock(minX, cy+y, maxZ, getGenBlock(rand, cy))
            setBlock(maxX, cy+y, minZ, getGenBlock(rand, cy))
            setBlock(maxX, cy+y, maxZ, getGenBlock(rand, cy))
        }

        genFlat(x, y+6, z, rand, true)
    }

    private fun World.genXRoom(x: Int, y: Int, z: Int, rand: Random, positive: Boolean) {
        var centerX = x
        if (positive) {
            centerX += 7
        } else {
            centerX -= 7
        }
        genFlat(centerX, y, z, rand, false)
        genFlat(centerX, y+6, z, rand, true)

        val minX = centerX - 3
        val maxX = centerX + 3

        // neg z wall -- left
        for (dy in 0..5) {
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

        for (dy in 0..5) {
            for (cz in minZ..maxZ) {
                setBlock(cx, dy + y, cz, getGenBlock(rand, dy))
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
        genFlat(x, y+6, centerZ, rand, true)

        val minZ = centerZ - 3
        val maxZ = centerZ + 3

        // neg z wall -- left
        for (dy in 0..5) {
            for (cz in minZ..maxZ) {
                setBlock(x - 3, dy + y, cz, getGenBlock(rand, dy))
            }
        }

        // pos z wall -- right
        for (dy in 0..5) {
            for (cz in minZ..maxZ) {
                setBlock(x+3, dy + y, cz, getGenBlock(rand, dy))
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

        for (dy in 0..5) {
            for (cx in minX..maxX) {
                setBlock(cx, dy + y, cz, getGenBlock(rand, dy))
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