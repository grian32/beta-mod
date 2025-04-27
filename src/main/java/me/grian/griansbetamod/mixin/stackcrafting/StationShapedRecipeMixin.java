package me.grian.griansbetamod.mixin.stackcrafting;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Debug(export = true)
@Mixin(StationShapedRecipe.class)
public class StationShapedRecipeMixin {
    @Inject(
            method = "matches(Lnet/minecraft/inventory/CraftingInventory;IIZ)Z",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/item/ItemStack;isItemEqual(Lnet/minecraft/item/ItemStack;)Z"
            ),
            cancellable = true
    )
    private void injected(CraftingInventory grid, int startX, int startY, boolean mirror, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) boolean ignoreDamage, @Local(ordinal = 0) ItemStack itemToTest, @Local(ordinal = 1) Optional<ItemStack> itemOpt) {
        @SuppressWarnings("OptionalGetWithoutIsPresent") ItemStack item = itemOpt.get();
        // had to dupe this since i mixin after boolean equals etc and if i return early then it doesn't get called, makes no diff otherwise, idk why it doesnt let me mixin after setDamage() but yeah
        if (ignoreDamage) item.setDamage(-1);
        if (itemToTest.count < item.count) cir.setReturnValue(false);
    }
}
