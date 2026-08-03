package me.grian.griansbetamod.hell.limbo

import net.minecraft.block.Block
import net.minecraft.client.gui.screen.LoadingDisplay
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.chunk.ChunkSource
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk

class LimboChunkGenerator(private val world: World, private val seed: Long) : ChunkSource {
    override fun getChunk(chunkX: Int, chunkZ: Int): Chunk {
        val blocks = ByteArray(16*16*128)
        for (x in 0..15) {
            for (z in 0..15 ) {
                val index = (x * 16 + z) * 128
                blocks[index] = Block.BEDROCK.id.toByte()
            }
        }

        return FlattenedChunk(world, chunkX, chunkZ).also {
            it.fromLegacy(blocks)
            it.populateHeightMap()
        }
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