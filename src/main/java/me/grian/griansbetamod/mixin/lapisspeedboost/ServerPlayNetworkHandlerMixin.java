package me.grian.griansbetamod.mixin.lapisspeedboost;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.glasslauncher.mods.networking.GlassNetworkHandler;
import net.glasslauncher.mods.networking.GlassNetworking;
import net.glasslauncher.mods.networking.GlassPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow private ServerPlayerEntity player;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (((GlassNetworkHandler) this).glass_Networking$hasGlassNetworking() && ConfigScreen.config.lapisSpeedBoost) {
            IPlayerEntityMixin playerEntity = (IPlayerEntityMixin) player;

            if (playerEntity.beta_mod$getSpeedBoostTicks() != 0) {
                int newValue = Math.max(playerEntity.beta_mod$getSpeedBoostTicks() - 1, 0);
                playerEntity.beta_mod$setSpeedBoostTicks(newValue);
                NbtCompound nbt = new NbtCompound();
                nbt.putInt("speedTicks", newValue);
                GlassNetworking.sendToPlayer(player, new GlassPacket("griansbetamod", "speedticks", nbt));
            }
        }
    }
}
