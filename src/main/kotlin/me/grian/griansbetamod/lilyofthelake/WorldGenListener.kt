package me.grian.griansbetamod.lilyofthelake

import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent

object WorldGenListener {
    @EventListener
    fun worldGen(event: WorldGenEvent.ChunkDecoration) {
        // mimics vanilla behaviour more or less
        val x = event.x
        val z = event.z
        val random = event.random

        val featureX = x + random.nextInt(16) + 8
        val featureZ = z + random.nextInt(16) + 8

        LilyOfTheLakePatchFeature().generate(event.world, random, featureX, 63, featureZ)
    }
}