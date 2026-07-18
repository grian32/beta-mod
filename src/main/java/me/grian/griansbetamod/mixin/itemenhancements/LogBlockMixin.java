package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.getExtraLogs;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.resinDropped;

@Mixin(LogBlock.class)
public class LogBlockMixin extends Block {
    @Unique
    private static final BooleanProperty PLACED = BooleanProperty.of("placed");

    public LogBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(int id, CallbackInfo ci) {
        if (ConfigScreen.config.enhancementSystem) {
            setDefaultState(getDefaultState().with(PLACED, false));
        }
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        if (ConfigScreen.config.enhancementSystem) {
            builder.add(PLACED);
            super.appendProperties(builder);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        if (ConfigScreen.config.enhancementSystem) {
            return getDefaultState().with(PLACED, true);
        }

        return super.getPlacementState(context);
    }


    // afaik this needs to be in after break because the player can switch tools.
    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, BlockState brokenState, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        if (selectedSlot != null && ConfigScreen.config.enhancementSystem) {
            Enhancement enhancement = getEnhancement(selectedSlot);
            int tier = getEnhancementTier(selectedSlot);

            if (enhancement == Enhancement.EXTRA_LOGS && !brokenState.get(PLACED)) {
                int extraAmount = getExtraLogs(tier, world.random);
                if (extraAmount > 0) {
                    this.dropStack(world, x, y, z, new ItemStack(Block.LOG, extraAmount, meta));
                }
            } else if (enhancement == Enhancement.RESIN && resinDropped(tier, world.random)) {
                this.dropStack(world, x, y, z, new ItemStack(BetaMod.resin, 1));
            }
        }

        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}
