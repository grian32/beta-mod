package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.lilyofthelake.LilyOfTheLakePatchFeature
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.world.biome.Biome
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent

object WorldGenListener {
    @EventListener
    fun worldGen(event: WorldGenEvent.ChunkDecoration) {
        if (event.random.nextInt(200) != 0 || (event.biome != Biome.TUNDRA && event.biome != Biome.TAIGA)) {
            return
        }

        val featureX = event.x + event.random.nextInt(16) + 8
        val featureZ = event.z + event.random.nextInt(16) + 8

        IcyDungeonFeature().generate(event.world, event.random, featureX, event.world.getTopY(featureX, featureZ) - 14, featureZ)
    }
}
