package me.grian.griansbetamod.mixin.biomes.rainforest;

import net.minecraft.world.biome.RainforestBiome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RainforestBiome.class)
public class RainforestBiomeMixin {
    @Inject(method = "getRandomTreeFeature", at = @At("HEAD"), cancellable = true)
    void alwaysSpawnLargeTrees(Random random, CallbackInfoReturnable<Feature> cir) {
        cir.setReturnValue(new LargeOakTreeFeature());
    }
}
