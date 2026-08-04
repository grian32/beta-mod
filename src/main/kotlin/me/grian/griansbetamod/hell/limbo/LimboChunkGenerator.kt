package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.block.SandBlock
import net.minecraft.client.gui.screen.LoadingDisplay
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.chunk.ChunkSource
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk
import net.modificationstation.stationapi.impl.worldgen.WorldDecoratorImpl
import java.util.Random

class LimboChunkGenerator(private val world: World, private val seed: Long) : ChunkSource {
    private val LIMBO_SALT = 0x4C494D42DFL
    private val noiseGen = OctavePerlinNoiseSampler(Random(seed xor LIMBO_SALT), 1)

    override fun getChunk(chunkX: Int, chunkZ: Int): Chunk {
        val chunk = FlattenedChunk(world, chunkX, chunkZ)
        val section = chunk.getOrCreateSection(0, false)
        val bedrock = Block.BEDROCK.defaultState

        // TODO: replace w/ dead dirt, grass blocks, & see if i can fit the grey netherrack thing

        for (x in 0..15) {
            for (z in 0..15) {
                section.setBlockState(x, 0, z, bedrock)

                val worldX = chunkX * 16.0 + x
                val worldZ = chunkZ * 16.0 + z
                val height = noiseGen.sample(worldX / 24.0, worldZ / 24.0)
                val grass = BetaMod.deadGrass.defaultState
                val grassY = if (height < -0.15) { 10 } else if (height < 0.15) { 11 } else { 12 }

                section.setBlockState(x, grassY, z, grass)

                for (y in 1..<grassY) {
                    val state = if (y >= grassY - 3) {
                        BetaMod.deadDirt.defaultState
                    } else {
                        Block.STONE.defaultState
                    }

                    section.setBlockState(x, y, z, state)
                }
            }
        }



        chunk.populateHeightMap()
        return chunk
    }

    override fun loadChunk(chunkX: Int, chunkZ: Int): Chunk = getChunk(chunkX, chunkZ)

    override fun decorate(source: ChunkSource, x: Int, z: Int) {
        try {
            WorldDecoratorImpl.decorate(world, x, z)
        } finally {
            SandBlock.fallInstantly = false
        }
    }

    override fun isChunkLoaded(x: Int, z: Int): Boolean = true
    override fun save(saveEntities: Boolean, display: LoadingDisplay?): Boolean = true
    override fun tick(): Boolean = false
    override fun canSave(): Boolean = true
    override fun getDebugInfo(): String = "LimboChunkGenerator"
}