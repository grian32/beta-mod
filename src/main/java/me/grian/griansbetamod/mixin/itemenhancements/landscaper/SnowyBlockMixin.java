package me.grian.griansbetamod.mixin.itemenhancements.landscaper;

import me.grian.griansbetamod.mixinutil.LandscaperCommon;
import net.minecraft.block.Block;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowBlock.class)
public class SnowyBlockMixin extends Block {
    public SnowyBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        ItemStack drop = LandscaperCommon.getDrop(playerEntity, this);

        if (drop != null) {
            this.dropStack(world, x, y, z, drop);
            playerEntity.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }
}
