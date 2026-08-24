package me.grian.griansbetamod.mixin.hell.limbo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.grian.griansbetamod.hell.DescentGateFeature;
import me.grian.griansbetamod.hell.limbo.LimboDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.binder.ArsenicCompass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArsenicCompass.class)
public class ArsenicCompassMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getSpawnPos()Lnet/minecraft/util/math/Vec3i;"
        )
    )
    Vec3i redirectCompass(World world, Operation<Vec3i> original) {
        if (world.dimension instanceof LimboDimension) {
            return DescentGateFeature.nearestGate(
                world.getSeed(),
                minecraft.player.x,
                minecraft.player.z
            );
        }

        return original.call(world);
    }
}
