package me.grian.griansbetamod.mixin.versionstring;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @ModifyArg(
            method = "render(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
            ),
            index = 1
    )
    String versionString(String oldVersionString) {
        if (ConfigScreen.config.modifyVersionString) {
            return oldVersionString + "+GBE " + BetaMod.getVersionString();
        }
        return oldVersionString;
    }
}
