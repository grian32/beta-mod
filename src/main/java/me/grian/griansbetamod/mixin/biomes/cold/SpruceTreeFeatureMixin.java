package me.grian.griansbetamod.mixin.biomes.cold;

import net.minecraft.world.gen.feature.SpruceTreeFeature;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(SpruceTreeFeature.class)
public class SpruceTreeFeatureMixin {
    @ModifyVariable(
        method = "generate",
        at = @At("STORE"),
        index = 6
    )
    private int modifyTreeHeight(int height) {
        return height + 4;
    }
}
