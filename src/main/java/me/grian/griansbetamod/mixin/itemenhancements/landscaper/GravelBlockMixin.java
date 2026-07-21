package me.grian.griansbetamod.mixin.itemenhancements.landscaper;

import me.grian.griansbetamod.mixinutil.LandscaperCommon;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GravelBlock.class)
public class GravelBlockMixin extends SandBlock {
    public GravelBlockMixin(int id, int textureId) {
        super(id, textureId);
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
