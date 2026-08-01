package me.grian.griansbetamod.mixin.biomes.swampland;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.grian.griansbetamod.BuildFeatures;
import net.minecraft.block.SandBlock;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.CactusPatchFeature;
import net.minecraft.world.gen.feature.ClayOreFeature;
import net.minecraft.world.gen.feature.DeadBushPatchFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassPatchFeature;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.PlantPatchFeature;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import net.minecraft.world.gen.feature.SugarCanePatchFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = OverworldChunkGenerator.class, priority = 500)
public class OverworldChunkGeneratorMixin {
    @Unique
    private static final int TERRAIN_GRID_SIDE = 5;
    @Unique
    private static final int TERRAIN_GRID_HEIGHT = 17;
    @Unique
    private static final int TERRAIN_GRID_HORIZONTAL_SCALE = 4;
    @Unique
    private static final double SWAMPLAND_TARGET_Y = 8.875;
    @Unique
    private static final double SWAMPLAND_COMPRESSION = 40.0;

    @Shadow
    private World world;
    @Shadow
    private double[] heightMap;

    @Unique
    private boolean swamplandDecoration;

    @Inject(
        method = "buildTerrain",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/gen/chunk/OverworldChunkGenerator;generateHeightMap([DIIIIII)[D",
            shift = Shift.AFTER
        )
    )
    private void flattenSwamplandTerrain(
        int chunkX,
        int chunkZ,
        byte[] blocks,
        Biome[] biomes,
        double[] temperatures,
        CallbackInfo ci
    ) {
        if (!BuildFeatures.SWAMP_BIOME_CHANGES) return;

        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        for (int gridX = 0; gridX < TERRAIN_GRID_SIDE; gridX++) {
            for (int gridZ = 0; gridZ < TERRAIN_GRID_SIDE; gridZ++) {
                int sampleX = worldX + gridX * TERRAIN_GRID_HORIZONTAL_SCALE;
                int sampleZ = worldZ + gridZ * TERRAIN_GRID_HORIZONTAL_SCALE;
                if (
                    world.method_1781().getBiome(sampleX, sampleZ) !=
                    Biome.SWAMPLAND
                ) continue;

                int column = (gridX * TERRAIN_GRID_SIDE + gridZ) * TERRAIN_GRID_HEIGHT;
                for (int y = 0; y < TERRAIN_GRID_HEIGHT; y++) {
                    heightMap[column + y] -=
                        (y - SWAMPLAND_TARGET_Y) * SWAMPLAND_COMPRESSION;
                }
            }
        }
    }

    @Inject(method = "decorate", at = @At("HEAD"))
    private void identifySwampland(ChunkSource source, int chunkX, int chunkZ, CallbackInfo ci) {
        swamplandDecoration =
            BuildFeatures.SWAMP_BIOME_CHANGES &&
            world.method_1781().getBiome(chunkX * 16 + 16, chunkZ * 16 + 16) == Biome.SWAMPLAND;
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/LakeFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeLakes(
        LakeFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/ClayOreFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeClayDeposits(
        ClayOreFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/Feature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeTrees(
        Feature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/PlantPatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removePlants(
        PlantPatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/GrassPatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeGrass(
        GrassPatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/DeadBushPatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeDeadBushes(
        DeadBushPatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/SugarCanePatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeSugarCane(
        SugarCanePatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/PumpkinPatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removePumpkins(
        PumpkinPatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/CactusPatchFeature;generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"
        )
    )
    private boolean removeCacti(
        CactusPatchFeature feature,
        World world,
        Random random,
        int x,
        int y,
        int z,
        Operation<Boolean> original
    ) {
        return !swamplandDecoration && original.call(feature, world, random, x, y, z);
    }

    @Inject(
        method = "decorate",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/gen/chunk/OverworldChunkGenerator;temperatures:[D",
            opcode = Opcodes.PUTFIELD
        ),
        cancellable = true
    )
    private void removeSnow(ChunkSource source, int chunkX, int chunkZ, CallbackInfo ci) {
        if (swamplandDecoration) {
            SandBlock.fallInstantly = false;
            ci.cancel();
        }
    }
}
