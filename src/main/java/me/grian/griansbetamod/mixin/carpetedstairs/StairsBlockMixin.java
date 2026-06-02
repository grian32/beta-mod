package me.grian.griansbetamod.mixin.carpetedstairs;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.util.BetaSide;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(StairsBlock.class)
public class StairsBlockMixin extends Block {
    @Unique
    private static final IntProperty WOOL_META = IntProperty.of("wool_meta", 0, 16);

    @Shadow
    private Block baseBlock;

    public StairsBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "<init>", at=@At("TAIL"))
    private void injectedConstructor(int id, Block baseBlock, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(WOOL_META, 16));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WOOL_META);
        super.appendProperties(builder);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    void onUse(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (
                ConfigScreen.config.carpetedStairsAndSlabs &&
                baseBlock == Block.PLANKS &&
                player.getHand() != null &&
                player.getHand().itemId == Block.WOOL.id &&
                world.getBlockState(x, y, z).get(WOOL_META) == 16
        ) {
            BlockState state = world.getBlockState(x, y, z);
            int blockMeta = player.getHand().getDamage();
            world.setBlockState(x, y, z, state.with(WOOL_META, blockMeta), world.getBlockMeta(x, y, z));
            player.getHand().count -= 1;
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    void getTextureId(BlockView blockView, int x, int y, int z, int side, CallbackInfoReturnable<Integer> cir) {
        if (!ConfigScreen.config.carpetedStairsAndSlabs || baseBlock != Block.PLANKS) {
            return;
        }
        if (blockView instanceof BlockStateView bsv) {
            int woolMeta = bsv.getBlockState(x, y, z).get(WOOL_META);
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

    // feels real dodgy but onbreak gets called after block is broken lol
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState) {
        if (state.getBlock() == newState.getBlock() || !ConfigScreen.config.carpetedStairsAndSlabs) {
            return;
        }
        int woolMeta = state.get(WOOL_META);
        if (woolMeta != 16) {
            this.dropStack(world, pos.x, pos.y, pos.z, new ItemStack(WOOL.id, 1, woolMeta));
        }

        super.onStateReplaced(state, world, pos, newState);
    }
}
