package me.grian.griansbetamod.biome.swamp

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class SwamplandUnderwaterPatchFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        if (world.getMaterial(x, y - 1, z) != Material.WATER) return false

        val width = random.nextInt(2) + 1
        val height = random.nextInt(2) + 1

        val block = if (random.nextInt(3) == 0) {
            Block.CLAY.id
        } else {
            Block.SAND.id
        }

        for (dx in -width..width) {
            for (dz in -height..height) {
                if (world.getMaterial(x + dx, y - 1, z + dz) != Material.WATER) continue
                world.setBlockWithoutNotifyingNeighbors(x + dx, y - 2, z + dz, block);
                if (random.nextInt(2) == 0) {
                    // extra x
                    if (world.getMaterial(x + dx + 1, y - 1, z + dz) != Material.WATER) continue
                    world.setBlockWithoutNotifyingNeighbors(x + dx + 1, y - 2, z + dz, block);
                } else {
                    // extra z
                    if (world.getMaterial(x + dx, y - 1, z + dz + 1) != Material.WATER) continue
                    world.setBlockWithoutNotifyingNeighbors(x + dx, y - 2, z + dz + 1, block);
                }
            }
        }

        return true
    }
}
