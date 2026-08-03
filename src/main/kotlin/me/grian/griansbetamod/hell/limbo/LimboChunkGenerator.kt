package me.grian.griansbetamod.hell.limbo

import net.minecraft.block.Block
import net.minecraft.client.gui.screen.LoadingDisplay
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.chunk.ChunkSource
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk

class LimboChunkGenerator(private val world: World, private val seed: Long) : ChunkSource {
    override fun getChunk(chunkX: Int, chunkZ: Int): Chunk {
        val chunk = FlattenedChunk(world, chunkX, chunkZ)
        val section = chunk.getOrCreateSection(0, false)
        val bedrock = Block.BEDROCK.defaultState

        for (x in 0..15) {
            for (z in 0..15) {
                section.setBlockState(
                    x,
                    0,
                    z,
                    bedrock
                )
            }
        }

        chunk.populateHeightMap()
        return chunk
    }

    override fun loadChunk(chunkX: Int, chunkZ: Int): Chunk = getChunk(chunkX, chunkZ)

    override fun decorate(source: ChunkSource?, x: Int, z: Int) {

    }

    override fun isChunkLoaded(x: Int, z: Int): Boolean = true
    override fun save(saveEntities: Boolean, display: LoadingDisplay?): Boolean = true
    override fun tick(): Boolean = false
    override fun canSave(): Boolean = true
    override fun getDebugInfo(): String = "LimboChunkGenerator"
}