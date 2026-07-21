package me.grian.griansbetamod.mixin.fenceplacement;

import net.minecraft.block.FenceBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceBlock.class)
public class FenceBlockMixin {
    @Inject(method = "canPlaceAt", at=@At("HEAD"), cancellable = true)
    void canPlaceAt(World x, int y, int z, int par4, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
