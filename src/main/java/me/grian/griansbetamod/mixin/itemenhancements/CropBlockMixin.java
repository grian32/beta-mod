package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import me.grian.griansbetamod.itemenhancements.replanter.ReplanterTimer;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.shouldDropExtraBountiful;

@Mixin(CropBlock.class)
public class CropBlockMixin extends Block {
    public CropBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, BlockState bs, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        if (selectedSlot != null && meta == 7) {
            if (
                getEnhancement(selectedSlot) == Enhancement.REPLANTER &&
                getEnhancementTier(selectedSlot) > 0
            ) {
                selectedSlot.damage(1, playerEntity);
                // i have to do this so it doesnt get an extra durability where it shows as full durability, not sure why this happens as it should work correctly but :shrug:
                if (selectedSlot.count <= 0) {
                    selectedSlot.onRemoved(playerEntity);
                    playerEntity.clearStackInHand();
                }
                ReplanterTimer.registerTimer(new BlockPos(x, y, z), world, this.id);
            } else if (
                getEnhancement(selectedSlot) == Enhancement.BOUNTIFUL &&
                getEnhancementTier(selectedSlot) > 0
            ) {
                selectedSlot.damage(1, playerEntity);
                if (selectedSlot.count <= 0) {
                    selectedSlot.onRemoved(playerEntity);
                    playerEntity.clearStackInHand();
                }
                // little awkward call but whatever
                if (shouldDropExtraBountiful(getEnhancementTier(selectedSlot), world.random)) {
                    this.dropStack(world, x, y, z, new ItemStack(Item.WHEAT, world.random.nextInt(1, 3)));
                }
            }
        }


        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}