package me.grian.griansbetamod.mixin.shrine;

import me.grian.griansbetamod.mixin.MinecraftAccessor;
import me.grian.griansbetamod.shrine.ShrineState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @ModifyConstant(
            method = "renderSky",
            constant = @Constant(stringValue = "/terrain/moon.png")
    )
    String changeMoonTexture(String constant) {
        Minecraft mc = MinecraftAccessor.getInstance();
        World world = mc.world;
        if (world == null) return constant;
        long dayTime = world.getTime() % 24000L;
        boolean isNight = dayTime >= 13000L && dayTime < 23000L;
        boolean shrineActivated = ShrineState.get(world).getShrineActivated();
        if (isNight && shrineActivated) {
            return "/assets/griansbetamod/terrain/broken_moon.png";
        }

        return constant;
    }
}
