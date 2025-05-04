package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import me.grian.griansbetamod.itemenhancements.replanter.ReplanterTimer;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;

@Mixin(CropBlock.class)
public class CropBlockMixin extends Block {
    public CropBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, BlockState bs, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        if (
            getEnhancement(selectedSlot) == Enhancement.REPLANTER &&
            getEnhancementTier(selectedSlot) > 0 &&
            meta == 7 // fully grown
        ) {
            ReplanterTimer.registerTimer(new BlockPos(x, y, z), world);
        }

        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}