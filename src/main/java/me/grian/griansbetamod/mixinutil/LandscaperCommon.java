package me.grian.griansbetamod.mixinutil;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;

public final class LandscaperCommon {
    private LandscaperCommon() {

    }

    static public ItemStack getDrop(PlayerEntity playerEntity, Block block) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();
        if (selectedSlot == null || getEnhancement(selectedSlot) != Enhancement.LANDSCAPER || getEnhancementTier(selectedSlot) <= 0) {
            return null;
        }

        return new ItemStack(block, 1);
    }
}
