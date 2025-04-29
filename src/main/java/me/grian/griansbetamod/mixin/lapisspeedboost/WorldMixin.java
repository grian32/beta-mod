package me.grian.griansbetamod.mixin.lapisspeedboost;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import me.grian.griansbetamod.network.SpeedTicksPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
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
        if (ConfigScreen.config.lapisSpeedBoost) {
            for (Object p : players) {
                IPlayerEntityMixin player = ((IPlayerEntityMixin) p);

                int newTicks = Math.max(player.beta_mod$getSpeedBoostTicks() - 1, 0);

                if (player.beta_mod$getSpeedBoostTicks() != 0) {
                    player.beta_mod$setSpeedBoostTicks(newTicks);
                    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
                        PacketHelper.sendTo((PlayerEntity) p, new SpeedTicksPacket(newTicks));
                    }
                }
            }
        }
    }
}
