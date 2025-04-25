package me.grian.griansbetamod.mixin.lapisspeedboost;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(World.class)
public class WorldMixin {
    @Shadow public List players;

    @Shadow public boolean isRemote;

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (isRemote) return;
        if (ConfigScreen.config.lapisSpeedBoost) {
            for (Object p : players) {
                IPlayerEntityMixin player = ((IPlayerEntityMixin) p);
                if (player.beta_mod$getSpeedBoostTicks() != 0) {
                    player.beta_mod$setSpeedBoostTicks(player.beta_mod$getSpeedBoostTicks() - 1);
                }
            }
        }
    }
}
