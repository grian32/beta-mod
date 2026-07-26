package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PumpkinPatchFeature.class)
public class PumpkinPatchFeatureMixin {
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    void generate(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.method_1781().getBiome(x, z) != Biome.SEASONAL_FOREST) return;

        for (int var6 = 0; var6 < 64; ++var6) {
            int var7 = x + random.nextInt(8) - random.nextInt(8);
            int var8 = world.getTopY(x, z);
            int var9 = z + random.nextInt(8) - random.nextInt(8);
            if (world.isAir(var7, var8, var9) && world.getBlockId(var7, var8 - 1, var9) == Block.GRASS_BLOCK.id && Block.PUMPKIN.canPlaceAt(world, var7, var8, var9)) {
                world.setBlockWithoutNotifyingNeighbors(var7, var8, var9, Block.PUMPKIN.id, random.nextInt(4));
            }
        }

        cir.setReturnValue(true);
    }
}
