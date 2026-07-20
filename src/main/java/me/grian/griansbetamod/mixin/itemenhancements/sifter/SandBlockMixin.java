package me.grian.griansbetamod.mixin.itemenhancements.sifter;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.block.SandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.shouldDropSifter;
import static me.grian.griansbetamod.util.UtilKt.hasAdjacent;

@Mixin(SandBlock.class)
public class SandBlockMixin extends Block {
    public SandBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();
        if (selectedSlot == null) {
            super.afterBreak(world, playerEntity, x, y, z, meta);
            return;
        }

        int tier = getEnhancementTier(selectedSlot);
        if (
            getEnhancement(selectedSlot) == Enhancement.SIFTER &&
            tier > 0 &&
            hasAdjacent(world, x, y, z, new Block[]{Block.WATER, Block.FLOWING_WATER}) &&
            shouldDropSifter(tier, world.random)
        ) {
            this.dropStack(world, x, y, z, new ItemStack(Item.CLAY, world.random.nextInt(2, 5)));
            playerEntity.increaseStat(Stats.MINE_BLOCK[this.id],1);
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }
}
