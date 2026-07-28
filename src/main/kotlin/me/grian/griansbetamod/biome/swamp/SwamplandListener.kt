package me.grian.griansbetamod.biome.swamp

import me.grian.griansbetamod.BuildFeatures
import me.grian.griansbetamod.lilyofthelake.LilyOfTheLakePatchFeature
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.feature.GrassPatchFeature
import net.minecraft.world.gen.feature.SugarCanePatchFeature
import net.modificationstation.stationapi.api.block.BlockState
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent
import net.modificationstation.stationapi.api.worldgen.feature.HeightScatterFeature
import net.modificationstation.stationapi.api.worldgen.surface.SurfaceBuilder
import net.modificationstation.stationapi.api.worldgen.surface.condition.SurfaceCondition
import java.util.Random
import kotlin.math.abs

object SwamplandListener {
    @EventListener
    fun configureSwampland(event: BiomeRegisterEvent) {
        if (!BuildFeatures.SWAMP_BIOME_CHANGES) return

        val swamp = Biome.SWAMPLAND;
        swamp.setFixedGrassColorProvider(0x3F5A32)
        swamp.setLeavesColor(0x2F4A28)

        val terrainSurfaceCache = TerrainSurfaceCache()
        val channel = ChannelSurfaceCondition(terrainSurfaceCache)
        val surface = SurfaceOffsetCondition(terrainSurfaceCache, 0)
        val subsurface = SurfaceOffsetCondition(terrainSurfaceCache, 1)

        swamp.addSurfaceRule(
            SurfaceBuilder.start(Block.WATER)
                .condition(channel, 0)
                .replace(Block.STONE)
                .build()
        )

        swamp.addSurfaceRule(
            SurfaceBuilder.start(Block.GRASS_BLOCK)
                .condition(surface, 0)
                .replace(Block.STONE)
                .build()
        )

        swamp.addSurfaceRule(
            SurfaceBuilder.start(Block.DIRT)
                .condition(subsurface, 0)
                .replace(Block.STONE)
                .build()
        )

        swamp.addFeature(
           HeightScatterFeature(SwamplandLilyOfTheLakePatchFeature(), 1),
        )

        swamp.addFeature(
            HeightScatterFeature(SwamplandUnderwaterPatchFeature(), 5)
        )

        swamp.addFeature(
            HeightScatterFeature(CrookedOakTreeFeature(), 64)
        )

        swamp.addFeature(
            HeightScatterFeature(SugarCanePatchFeature(), 6)
        )

        swamp.addFeature(
            HeightScatterFeature(GrassPatchFeature(Block.GRASS.id, 1), 4)
        )

        swamp.addFeature(
            HeightScatterFeature(GrassPatchFeature(Block.GRASS.id, 2), 3)
        )
    }

    private class ChannelSurfaceCondition(
        private val terrainSurfaceCache: TerrainSurfaceCache
    ) : SurfaceCondition {
        private var seed = Long.MIN_VALUE
        private var noise: OctavePerlinNoiseSampler? = null
        private var detailNoise: OctavePerlinNoiseSampler? = null
        private var channelNoise: OctavePerlinNoiseSampler? = null
        private val CHANNEL_SALT = 0x4348414E4E454CL
        private val WETLAND_SALT = 0x5745544C414E44L
        private val STREAM_SALT = 0x53545245414DL

        private val DIRECTIONS = listOf(
            -1 to 0,
            -1 to 1,
            -1 to -1,
            0 to 1,
            0 to -1,
            1 to 0,
            1 to 1,
            1 to -1,
        )

        override fun canApply(
            world: World,
            x: Int,
            y: Int,
            z: Int,
            state: BlockState
        ): Boolean {
            if (noise == null || seed != world.seed) {
                seed = world.seed
                noise = OctavePerlinNoiseSampler(
                    Random(seed xor CHANNEL_SALT),
                    1
                )
                detailNoise = OctavePerlinNoiseSampler(
                    Random(seed xor WETLAND_SALT),
                    1
                )
                channelNoise = OctavePerlinNoiseSampler(
                    Random(seed xor STREAM_SALT),
                    1
                )
            }

            val surfaceY = terrainSurfaceCache.get(world, x, z)
            if (y != surfaceY) return false

            for (dir in DIRECTIONS) {
                if (world.isAir(x + dir.first, y, z+dir.second)) return false
            }

            // return y == surfaceY && abs(noise!!.sample(x / 4.0, z / 4.0)) < 0.18

            val pondBase = noise!!.sample(x / 10.0, z / 10.0)
            val pondEdge = detailNoise!!.sample(x / 3.5, z / 3.5)
            val pondShape = pondBase * 0.82 + pondEdge * 0.18

            val pond = pondShape < -0.08
            val channel = abs(channelNoise!!.sample(x / 18.0, z / 18.0)) < 0.05 &&
                pondShape < 0.0

            return pond || channel
        }

    }

    private class SurfaceOffsetCondition(
        private val terrainSurfaceCache: TerrainSurfaceCache,
        private val offset: Int
    ) : SurfaceCondition {
        override fun canApply(
            world: World,
            x: Int,
            y: Int,
            z: Int,
            state: BlockState
        ): Boolean {
            return y == terrainSurfaceCache.get(world, x, z) - offset
        }
    }

    private class TerrainSurfaceCache {
        private var world: World? = null
        private var x = 0
        private var z = 0
        private var surfaceY = 0

        fun get(world: World, x: Int, z: Int): Int {
            if (this.world !== world || this.x != x || this.z != z) {
                this.world = world
                this.x = x
                this.z = z
                surfaceY = findTerrainSurfaceY(world, x, z)
            }

            return surfaceY
        }

        private fun findTerrainSurfaceY(world: World, x: Int, z: Int): Int {
            val chunk: Chunk = world.getChunkFromPos(x, z)
            val localX = x and 15
            val localZ = z and 15
            var y = chunk.getHeight(localX, localZ) - 1

            while (y > world.bottomY) {
                val blockId = chunk.getBlockId(localX, y, localZ)
                if (
                    blockId != 0 &&
                    blockId != Block.LEAVES.id &&
                    blockId != Block.LOG.id
                ) {
                    return y
                }
                y--
            }

            return world.bottomY
        }
    }
}
