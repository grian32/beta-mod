package me.grian.griansbetamod.mixin.debugbiome;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawContext {
    @Shadow private Minecraft minecraft;

    @Inject(
        method = "render",
        // invoke assign didnt wanna work but this achieves the same thing by shifting :shrug:
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
            ordinal = 5,
            shift = At.Shift.BY,
            by = 2
        )
    )
    void render(float screenOpen, boolean mouseX, int mouseY, int par4, CallbackInfo ci) {
        int x = this.minecraft.player.chunkX * 16;
        int z = this.minecraft.player.chunkZ * 16;
        Biome biome = this.minecraft.world.method_1781().getBiome(x, z);

        this.drawTextWithShadow(this.minecraft.textRenderer, "biome:" + biome.name, 2, 96, 14737632);
    }
}
