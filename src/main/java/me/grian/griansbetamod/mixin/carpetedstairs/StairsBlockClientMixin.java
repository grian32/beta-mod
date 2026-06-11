package me.grian.griansbetamod.mixin.carpetedstairs;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.mixininterfaces.StairsBlockMixinShared;
import me.grian.griansbetamod.util.BetaSide;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StairsBlock.class)
public class StairsBlockClientMixin {
    @Shadow
    private Block baseBlock;

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    void getTextureId(BlockView blockView, int x, int y, int z, int side, CallbackInfoReturnable<Integer> cir) {
        if (!ConfigScreen.config.carpetedStairsAndSlabs || baseBlock != Block.PLANKS) {
            return;
        }
        if (blockView instanceof BlockStateView bsv) {
            int woolMeta = bsv.getBlockState(x, y, z).get(StairsBlockMixinShared.WOOL_META);
            if (woolMeta == 16) {
                return;
            }

            int woolTexture = Block.WOOL.textureId;
            if (woolMeta != 0) {
                int shiftedWoolMeta = ~(woolMeta & 0xF);
                woolTexture = 113 + ((shiftedWoolMeta & 8) >> 3) + (shiftedWoolMeta & 7) * 16;
            }

            int riserSide = BetaSide.getStairsDirection(blockView.getBlockMeta(x, y, z)).getValue();
            if (side == BetaSide.TOP.getValue() || side == riserSide) {
                cir.setReturnValue(woolTexture);
            }
        } else {
            System.out.println("block state is not bsv");
        }
    }
}
