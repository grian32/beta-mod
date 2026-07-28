package me.grian.griansbetamod.biome.swamp

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
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
        var generated = false

        repeat(12) {
            val featureX = x + random.nextInt(33) - 16
            val featureZ = z + random.nextInt(33) - 16
            val featureY = world.getTopY(featureX, featureZ)
            val blockId = world.getBlockId(featureX, featureY - 1, featureZ)
            val isWater = blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id

            if (
                isWater &&
                world.isAir(featureX, featureY, featureZ) &&
                world.setBlockWithoutNotifyingNeighbors(
                    featureX,
                    featureY,
                    featureZ,
                    BetaMod.lilyOfTheLake.id
                )
            ) {
                world.updateLight(LightType.BLOCK, featureX, featureY, featureZ, 0)
                generated = true
            }
        }

        if (generated) {
            world.doLightingUpdates()
        }

        return generated
    }
}
