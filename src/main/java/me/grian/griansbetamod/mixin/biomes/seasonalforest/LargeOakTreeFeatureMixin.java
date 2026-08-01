package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LargeOakTreeFeature.class)
public class LargeOakTreeFeatureMixin {
    @Unique
    private boolean seasonalTree;

    @Inject(method = "generate", at = @At("HEAD"))
    void captureSeasonalTree(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.method_1781().getBiome(x, z) == Biome.SEASONAL_FOREST) {
            seasonalTree = true;
        }
    }

    @WrapOperation(
        method = "placeCluster",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockWithoutNotifyingNeighbors(IIII)Z"
        )
    )
    boolean setMeta(World instance, int x, int y, int z, int blockId, Operation<Boolean> original) {
        if (blockId == Block.LEAVES.id && seasonalTree) {
            // meta bit 4
            return instance.setBlockWithoutNotifyingNeighbors(x, y, z, blockId, 4);
        }

        return original.call(instance, x, y, z, blockId);
    }
}
