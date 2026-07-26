package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PlantPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PlantPatchFeature.class)
public class MushroomFeatureMixin {
    @Shadow
    private int plantBlockId;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    void genMushrooms(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        boolean mushroom = plantBlockId == Block.BROWN_MUSHROOM.id || plantBlockId == Block.RED_MUSHROOM.id;
        boolean seasonal = world.method_1781().getBiome(x, z) == Biome.SEASONAL_FOREST;

        if (!mushroom || !seasonal) return;

        for(int var6 = 0; var6 < 64; ++var6) {
            int var7 = x + random.nextInt(8) - random.nextInt(8);
            int var8 = world.getTopY(x, z);
            int var9 = z + random.nextInt(8) - random.nextInt(8);
            if (world.isAir(var7, var8, var9) && ((PlantBlock)Block.BLOCKS[this.plantBlockId]).canGrow(world, var7, var8, var9)) {
                world.setBlockWithoutNotifyingNeighbors(var7, var8, var9, this.plantBlockId);
            }
        }

        cir.setReturnValue(true);
    }
}
