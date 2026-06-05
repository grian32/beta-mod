package me.grian.griansbetamod.mixin.climate;

import me.grian.griansbetamod.climate.FloweryBirchTreeFeature;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.world.biome.ForestBiome;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ForestBiome.class)
public class ForestBiomeMixin {
    @Inject(method = "getRandomTreeFeature", at=@At(value = "RETURN", ordinal = 0), cancellable = true)
    void getRandomTreeFeature(Random random, CallbackInfoReturnable<Feature> cir) {
        if (ConfigScreen.config.climate) {
            cir.setReturnValue(new FloweryBirchTreeFeature());
        }
    }
}
