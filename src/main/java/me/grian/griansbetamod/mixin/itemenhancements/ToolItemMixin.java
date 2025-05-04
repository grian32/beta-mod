package me.grian.griansbetamod.mixin.itemenhancements;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.shouldSaveDurability;

@Mixin(ToolItem.class)
public class ToolItemMixin {
    // can't just cir.setReturnValue(shouldSaveDurability()) since that would return early if true
    @Inject(method = "postHit", at = @At("HEAD"), cancellable = true)
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        if (shouldSaveDurability(stack, attacker.world.random)) cir.setReturnValue(true);
    }

    @Inject(method = "postMine", at = @At("HEAD"), cancellable = true)
    public void postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (shouldSaveDurability(stack, miner.world.random)) cir.setReturnValue(true);
    }
}