package me.grian.griansbetamod.mixininterfaces;

import me.grian.griansbetamod.itemenhancements.Enhancement;

public interface IItemStackMixin {
    int beta_mod$getEnhancementTier();
    void beta_mod$setEnhancementTier(int enhancementTier);

    Enhancement beta_mod$getEnhancement();
    void beta_mod$setEnhancement(Enhancement enhancement);
}
