package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import net.minecraft.world.gen.feature.OakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getRandomTreeFeature", at = @At("HEAD"), cancellable = true)
    void makeLargeOaksCommon(Random random, CallbackInfoReturnable<Feature> cir) {
        if ((Object) this != Biome.SEASONAL_FOREST) return;

        cir.setReturnValue(
            random.nextInt(3) == 0 ?
                new LargeOakTreeFeature() :
                new OakTreeFeature()
        );
    }
}
