package me.grian.griansbetamod.mixin;

import me.grians.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Debug(export = true)
@Mixin(World.class)
public class WorldMixin {
    @Shadow public List players;

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        for (Object p : players) {
            IPlayerEntityMixin player = ((IPlayerEntityMixin) p);
            if (player.beta_mod$getSpeedBoostTicks() != 0) {
                player.beta_mod$setSpeedBoostTicks(player.beta_mod$getSpeedBoostTicks() - 1);
            }
        }
    }
}
