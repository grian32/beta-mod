package me.grian.griansbetamod.mixin.newarmor;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow
    public ItemStack[] armor;

    @Inject(method = "getTotalArmorDurability", at = @At("HEAD"), cancellable = true)
    void getTotalArmorDurability(CallbackInfoReturnable<Integer> cir) {
        int prot = 0;
        for (ItemStack stack : this.armor) {
            if (stack != null && stack.getItem() instanceof ArmorItem armor) {
                prot += armor.maxProtection;
            }
        }

        cir.setReturnValue(prot);
    }
}
