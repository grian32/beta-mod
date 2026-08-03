package me.grian.griansbetamod.mixin.hell.limbo;

import me.grian.griansbetamod.hell.limbo.LimboDimension;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private World world;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void disableLimboSky(float tickDelta, CallbackInfo ci) {
        if (world.dimension instanceof LimboDimension) ci.cancel();
    }
}
