package me.grian.griansbetamod.mixin.stackcrafting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Shadow public abstract void onSlotUpdate(Inventory inventory);

    @Inject(method = "onSlotClick", at = @At("RETURN"))
    public void onSlotClick(int index, int button, boolean shift, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        // fixes an issue where if you modify the amount in a slot then the slot update doesn't get processed, so this means if you add 1 log to a recipe
        // that requires 48 logs but has 47 logs in the slot, then it will not update.
        onSlotUpdate(player.inventory);
    }
}
