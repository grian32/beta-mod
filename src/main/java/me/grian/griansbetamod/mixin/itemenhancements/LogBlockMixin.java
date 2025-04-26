package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;

@Mixin(LogBlock.class)
public class LogBlockMixin extends Block {
    @Unique
    private static final BooleanProperty PLACED = BooleanProperty.of("placed");

    @Unique
    public boolean enhanced = false;

    @Unique
    public int tier = -1;

    @Unique
    public boolean isPlaced = false;

    public LogBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(int id, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PLACED, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PLACED);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(PLACED, true);
    }

    @Override
    public void onBlockBreakStart(World world, int x, int y, int z, PlayerEntity player) {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.get(PLACED)) isPlaced = true;

        super.onBlockBreakStart(world, x, y, z, player);
    }

    @Inject(method = "getDroppedItemCount", at = @At("HEAD"), cancellable = true)
    public void getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (enhanced && this.tier == 1) {
            cir.setReturnValue(1 + (random.nextInt(5) == 0 ? 1 : 0));

            isPlaced = false;
            enhanced = false;
            this.tier = -1;
        }
    }

    // afaik this needs to be in after break because the player can switch tools.
    @Inject(method = "afterBreak", at = @At("HEAD"))
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta, CallbackInfo ci) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        if (selectedSlot != null && isPlaced) {
            Enhancement enhancement = getEnhancement(selectedSlot);
            int tier = getEnhancementTier(selectedSlot);

            if (enhancement == Enhancement.EXTRA_LOGS) {
                enhanced = true;
                this.tier = tier;
            }
        }
    }
}
