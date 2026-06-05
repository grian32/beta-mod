package me.grian.griansbetamod.mixin.climate;

import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.LeavesBlock;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Debug(export = true)
@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {
    @ModifyArg(
            method = "breakLeaves",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/LeavesBlock;dropStacks(Lnet/minecraft/world/World;IIII)V"
            ),
            index = 4
    )
    int modifyMetaBreakLeaves(int oldMeta) {
        if (ConfigScreen.config.climate) {
            return 0;
        }

        return oldMeta;
    }

    @ModifyArg(
            method = "afterBreak",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/TransparentBlock;afterBreak(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;IIII)V"
            ),
            index=5
    )
    int modifyMetaAfterBreak(int oldMeta) {
        if (ConfigScreen.config.climate) {
            return 0;
        }

        return oldMeta;
    }
}
