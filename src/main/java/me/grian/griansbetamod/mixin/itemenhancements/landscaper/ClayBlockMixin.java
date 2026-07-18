package me.grian.griansbetamod.mixin.itemenhancements.landscaper;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.block.ClayBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;

@Mixin(ClayBlock.class)
public class ClayBlockMixin extends Block {
    public ClayBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        if (
            selectedSlot != null &&
            getEnhancement(selectedSlot) == Enhancement.LANDSCAPER
        ) {
            this.dropStack(world, x, y, z, new ItemStack(Block.CLAY, 1));
        }

        playerEntity.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        // i dont super cuz all it does is increase stat and dropstacks which im modifying here
    }
}
