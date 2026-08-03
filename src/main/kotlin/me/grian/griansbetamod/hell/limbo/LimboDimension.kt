package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.hell.DimensionListener
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.FixedBiomeSource
import net.minecraft.world.chunk.ChunkSource
import net.minecraft.world.dimension.Dimension

class LimboDimension(legacyId: Int): Dimension() {
    init {
        id = legacyId
    }

    override fun initBiomeSource() {
        biomeSource = FixedBiomeSource(
            DimensionListener.limboBiome,
            0.5,
            0.0
        )
    }

    override fun createChunkGenerator(): ChunkSource {
        return LimboChunkGenerator(world, world.seed)
    }

    override fun getBiomes(): Collection<Biome> = listOf(DimensionListener.limboBiome)

    override fun isValidSpawnPoint(x: Int, z: Int): Boolean = true
    override fun hasWorldSpawn(): Boolean = true
}