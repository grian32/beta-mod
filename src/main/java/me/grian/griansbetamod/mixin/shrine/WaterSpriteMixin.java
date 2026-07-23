package me.grian.griansbetamod.mixin.shrine;

import me.grian.griansbetamod.mixin.MinecraftAccessor;
import me.grian.griansbetamod.shrine.ShrineState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.binder.StationTextureBinder;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.binder.ArsenicFlowingWater;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.binder.ArsenicStillWater;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(value = {ArsenicStillWater.class, ArsenicFlowingWater.class}, remap = false)
public abstract class WaterSpriteMixin extends StationTextureBinder {
    public WaterSpriteMixin(Atlas.Sprite staticReference) {
        super(staticReference);
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void colorWater(CallbackInfo ci) {
        Minecraft mc = MinecraftAccessor.getInstance();
        World world = mc.world;

        if (world == null) return;
        long dayTime = world.getTime() % 24000L;
        boolean isNight = dayTime >= 13000L && dayTime < 23000L;
        boolean shrineActivated = ShrineState.get(world).getShrineActivated();

        if (!isNight || !shrineActivated) return;

        for (int i = 0; i < pixels.length; i += 4) {
            int alpha = pixels[i+3] & 0xFF;
            // since mc has alpha following the intensity of the anim we convert it to 0.0-1.0 range
            float blend = Math.max(
                    0.0F, Math.min(1.0F, (alpha-146)/50.0F)
            );

            int shadowRed = 0x24;
            int shadowGreen = 0x12;
            int shadowBlue = 0x3D;

            int highlightRed = 0x17;
            int highlightGreen = 0x61;
            int highlightBlue = 0x5d;

            pixels[i] = (byte) (shadowRed+(highlightRed-shadowRed) * blend);
            pixels[i+1] = (byte) (shadowGreen+(highlightGreen-shadowGreen) * blend);
            pixels[i+2] = (byte) (shadowBlue+(highlightBlue-shadowBlue) * blend);
        }
    }
}
