package me.grian.griansbetamod.mixin.itemenhancements;

import net.minecraft.block.LogBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;

@Mixin(LogBlock.class)
public class LogBlockMixin {
    @Inject(method = "afterBreak", at = @At("HEAD"))
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta, CallbackInfo ci) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();
        if (selectedSlot != null) {
            System.out.println(getEnhancement(selectedSlot));
            System.out.println(getEnhancementTier(selectedSlot));
        }
    }
}
