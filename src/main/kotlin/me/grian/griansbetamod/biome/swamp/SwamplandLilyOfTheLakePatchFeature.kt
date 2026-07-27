package me.grian.griansbetamod.biome.swamp

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.LightType
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.Random

class SwamplandLilyOfTheLakePatchFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        val waterY = y - 1;

        val blockId = world.getBlockId(x, waterY, z);
        val isWater = blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id

        if (!isWater || !world.isAir(x, y, z)) return false

        if (world.setBlockWithoutNotifyingNeighbors(x, y, z, BetaMod.lilyOfTheLake.id)) {
           world.updateLight(LightType.BLOCK, x, y, z, 0)
           world.doLightingUpdates()
        }

        return true
    }

}