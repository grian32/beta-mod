package me.grian.griansbetamod.mixin;

import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayerEntity.class)
public interface ServerPlayerEntityAccessor {
    @Invoker("incrementScreenHandlerSyncId")
    void incrementScreenHanderSyncId();

    @Accessor
    int getScreenHandlerSyncId();
}
