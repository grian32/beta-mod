package me.grian.griansbetamod.mixin.biomes.swampland;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block {
    protected SugarCaneBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getColorMultiplier(BlockView blockView, int x, int y, int z) {
        return blockView.method_1781().getBiome(x, z) == Biome.SWAMPLAND ?
            0x36422C :
            super.getColorMultiplier(blockView, x, y, z);
    }
}
