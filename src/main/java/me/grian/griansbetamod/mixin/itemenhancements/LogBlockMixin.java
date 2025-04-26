package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.LogBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;

@Mixin(LogBlock.class)
public class LogBlockMixin {
    @Unique
    public boolean enhanced = false;

    @Unique
    public int tier = -1;

    @Inject(method = "getDroppedItemCount", at = @At("HEAD"), cancellable = true)
    public void getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (enhanced && this.tier == 1) {
            cir.setReturnValue(1 + (random.nextInt(5) == 0 ? 1 : 0));
        }
    }

    @Inject(method = "afterBreak", at = @At("HEAD"))
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta, CallbackInfo ci) {
        // TODO: make sure block hasn' been placed by player before applying enhancement
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();
        if (selectedSlot != null) {
            Enhancement enhancement = getEnhancement(selectedSlot);
            int tier = getEnhancementTier(selectedSlot);
            if (enhancement != Enhancement.NONE) {
                enhanced = true;
                this.tier = tier;
            }
        }
    }

}
