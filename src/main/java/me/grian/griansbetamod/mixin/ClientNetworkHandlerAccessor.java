package me.grian.griansbetamod.mixin;

import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientNetworkHandler.class)
public interface ClientNetworkHandlerAccessor {
    @Accessor("minecraft")
    public Minecraft getMinecraft();
}
