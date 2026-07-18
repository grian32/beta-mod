package me.grian.griansbetamod.mixin.carpetedstairs;

import me.grian.griansbetamod.mixininterfaces.SlabBlockMixinShared;
import me.grian.griansbetamod.util.BetaSide;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(SlabBlock.class)
public class SlabBlockClientMixin extends  Block {
    public SlabBlockClientMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public int getTextureId(BlockView blockView, int x, int y, int z, int side) {
        if (blockView.getBlockMeta(x, y, z) != SlabBlockMixinShared.WOOD_SLAB_META || side != BetaSide.TOP.getValue()) {
            return super.getTextureId(blockView, x, y, z, side);
        }
        if (blockView instanceof BlockStateView bsv) {
            int woolMeta = bsv.getBlockState(x, y, z).get(SlabBlockMixinShared.WOOL_META);
            if (woolMeta == 16) {
                return super.getTextureId(blockView, x, y, z, side);
            }

            int woolTexture = Block.WOOL.textureId;
            if (woolMeta != 0) {
                // insane stuff from orig impl, nfc..
                int shiftedWoolMeta = ~(woolMeta & 0xF);
                woolTexture = 113 + ((shiftedWoolMeta & 8) >> 3) + (shiftedWoolMeta & 7) * 16;
            }

            return woolTexture;
        } else {
            System.out.println("block state is not bsv");
        }
        return super.getTextureId(blockView, x, y, z, side);
    }
}
