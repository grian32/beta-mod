package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import me.grian.griansbetamod.mixininterfaces.IItemStackMixin;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IItemStackMixin {
    @Unique
    private Enhancement enhancement;

    @Unique
    private int enhancementTier;

    @Unique
    public Enhancement beta_mod$getEnhancement() {
        return this.enhancement;
    }

    @Unique
    public void beta_mod$setEnhancement(Enhancement enhancement) {
        this.enhancement = enhancement;
    }

    @Unique
    public int beta_mod$getEnhancementTier() {
        return enhancementTier;
    }

    @Unique
    public void beta_mod$setEnhancementTier(int enhancementTier) {
        this.enhancementTier = enhancementTier;
    }

}
