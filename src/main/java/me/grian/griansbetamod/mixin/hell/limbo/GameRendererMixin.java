package me.grian.griansbetamod.mixin.hell.limbo;

import me.grian.griansbetamod.hell.limbo.LimboDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private Minecraft client;

    @Inject(method = "applyFog", at = @At("RETURN"))
    void limboFog(int mode, float tickDelta, CallbackInfo ci) {
        if (!(client.world.dimension instanceof LimboDimension)) return;

        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
        GL11.glFogf(GL11.GL_FOG_START, 1.0f);
        GL11.glFogf(GL11.GL_FOG_END, 30.0f);
    }
}
