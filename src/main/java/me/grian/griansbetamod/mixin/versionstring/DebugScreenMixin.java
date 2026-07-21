package me.grian.griansbetamod.mixin.versionstring;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InGameHud.class)
public class DebugScreenMixin {
    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Ljava/lang/String;III)V"
            ),
            index = 0
    )
    String versionString(String oldVersionString) {
        if (ConfigScreen.config.modifyVersionString) {
            int index = oldVersionString.indexOf(" 1.7.3");
            if (index == -1) {
                return oldVersionString;
            }

            int insertIndex = index + 6;
            return oldVersionString.substring(0, insertIndex) + "+PBE " + BetaMod.getVersionString() + oldVersionString.substring(insertIndex);
        }

        return oldVersionString;
    }
}
