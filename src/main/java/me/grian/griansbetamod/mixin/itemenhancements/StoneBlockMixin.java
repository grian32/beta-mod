package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.block.StoneBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.lapisDropped;
@Mixin(StoneBlock.class)
public class StoneBlockMixin extends Block {
    @Unique
    private static final BooleanProperty PLACED = BooleanProperty.of("placed");

    @Unique
    private boolean isNotPlaced = true;

    public StoneBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(int textureId, int par2, CallbackInfo ci) {
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

    @Override
    public void onBlockBreakStart(World world, int x, int y, int z, PlayerEntity player) {
        if (ConfigScreen.config.enhancementSystem) {
            BlockState state = world.getBlockState(x, y, z);
            if (!state.get(PLACED)) isNotPlaced = true;

            super.onBlockBreakStart(world, x, y, z, player);
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        ItemStack selected = playerEntity.inventory.getSelectedItem();

        if (selected != null && isNotPlaced && ConfigScreen.config.enhancementSystem) {
            Enhancement enhancement = getEnhancement(selected);
            int tier = getEnhancementTier(selected);

            if (
                enhancement == Enhancement.LAPIS_MINER &&
                lapisDropped(tier, world.random)
            ) {
                this.dropStack(world, x, y, z, new ItemStack(Item.DYE, 1, 4)); // DMG 4 = LAPIS
                isNotPlaced = false;
            }
        }
        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}