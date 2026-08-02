package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.lilyofthelake.LilyOfTheLakePatchFeature
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.world.biome.Biome
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent

object WorldGenListener {
    @EventListener
    fun worldGen(event: WorldGenEvent.ChunkDecoration) {
        if (event.random.nextInt(160) != 0 || (event.biome != Biome.TUNDRA && event.biome != Biome.TAIGA)) {
            return
        }

        val featureX = event.x + event.random.nextInt(16) + 8
        val featureZ = event.z + event.random.nextInt(16) + 8
        val featureY = event.world.getTopY(featureX, featureZ) - 14

        for (dx in -10..10) {
            for (dz in -10..10) {
                val surfaceY = event.world.getTopY(
                    featureX + dx,
                    featureZ + dz
                )

                if (surfaceY <= featureY + 7) {
                    return
                }
            }
        }

        IcyDungeonFeature().generate(event.world, event.random, featureX, featureY, featureZ)
    }
}
