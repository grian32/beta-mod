package me.grian.griansbetamod.mixin.biomes.cold;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @Shadow
    private World world;

    // SNOWDRIFT ascii lol
    @Unique
    private static final long SNOW_NOISE_SALT = 0x534E4F5744524654L;

    @Unique
    private OctavePerlinNoiseSampler snowNoise;

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    void ctorInject(World world, long seed, CallbackInfo ci) {
        snowNoise = new OctavePerlinNoiseSampler(new Random(seed ^ SNOW_NOISE_SALT), 1);
    }


    @WrapOperation(
        method = "decorate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlock(IIII)Z"
        )
    )
    boolean snowMeta(World instance, int x, int y, int z, int blockId, Operation<Boolean> original) {
        if (instance.getBlockId(x, y-1, z) == Block.LEAVES.id) {
            original.call(instance, x, y, z, blockId);
        } else {
            double regionX = x / 8.0;
            double regionZ = z / 8.0;
            instance.setBlock(x, y, z, blockId, getSnowMeta(regionX, regionZ));
        }

        return true;
    }

    @Unique
    private int getSnowMeta(double regionX, double regionZ) {
        double value = snowNoise.sample(regionX, regionZ);
        if (value < -0.15) return 1;
        if (value < 0.15) return 2;

        return 3;
    }
}
