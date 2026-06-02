package me.grian.griansbetamod.mixin.carpetedstairs;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.util.BetaSide;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlabBlock.class)
public class SlabBlockMixin extends Block {
    @Unique
    private static final IntProperty WOOL_META = IntProperty.of("wool_meta", 0, 16);

    @Unique
    private static final int WOOD_SLAB_META = 2;

    @Shadow
    private boolean doubleSlab;

    public SlabBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(int id, boolean doubleSlab, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(WOOL_META, 16));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WOOL_META);
        super.appendProperties(builder);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (
                ConfigScreen.config.carpetedStairsAndSlabs &&
                !doubleSlab &&
                world.getBlockMeta(x, y, z) == WOOD_SLAB_META &&
                player.getHand() != null &&
                player.getHand().itemId == Block.WOOL.id &&
                world.getBlockState(x, y, z).get(WOOL_META) == 16
        ) {
            BlockState state = world.getBlockState(x, y, z);
            world.setBlockState(x, y, z, state.with(WOOL_META, player.getHand().getDamage()), world.getBlockMeta(x, y, z));
            player.getHand().count -= 1;
            return true;
        }
        return super.onUse(world, x, y, z, player);
    }

    @Override
    public int getTextureId(BlockView blockView, int x, int y, int z, int side) {
        if (!ConfigScreen.config.carpetedStairsAndSlabs || blockView.getBlockMeta(x, y, z) != WOOD_SLAB_META || side != BetaSide.TOP.getValue()) {
            return super.getTextureId(blockView, x, y, z, side);
        }
        if (blockView instanceof BlockStateView bsv) {
            int woolMeta = bsv.getBlockState(x, y, z).get(WOOL_META);
            if (woolMeta == 16) {
                return super.getTextureId(blockView, x, y, z, side);
            }

            int woolTexture = Block.WOOL.textureId;
            if (woolMeta != 0) {
                int shiftedWoolMeta = ~(woolMeta & 0xF);
                woolTexture = 113 + ((shiftedWoolMeta & 8) >> 3) + (shiftedWoolMeta & 7) * 16;
            }

            return woolTexture;
        } else {
            System.out.println("block state is not bsv");
        }
        return super.getTextureId(blockView, x, y, z, side);
    }

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
