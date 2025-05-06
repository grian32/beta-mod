package me.grian.griansbetamod.mixin.itemenhancements;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.itemenhancements.Enhancement;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
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

import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancement;
import static me.grian.griansbetamod.itemenhancements.UtilKt.getEnhancementTier;
import static me.grian.griansbetamod.mixinutils.ItemEnhancementsKt.shouldSaveOre;

@Mixin(OreBlock.class)
public class OreBlockMixin extends Block {
    public OreBlockMixin(int id, Material material) {
        super(id, material);
    }
    @Unique
    private static final BooleanProperty PLACED = BooleanProperty.of("placed");

    @Unique
    private boolean placed = true;

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
        // so im like 90% sure i can get away with not having an intermediary property for this one but apparently
        // onBreak gets called during all of world gen for god knows what reason
        // and that seems like it could get wonky perf wise
        if (ConfigScreen.config.enhancementSystem) {
            BlockState state = world.getBlockState(x, y, z);
            placed = state.get(PLACED);

            super.onBlockBreakStart(world, x, y, z, player);
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        int tier = getEnhancementTier(selectedSlot);

        if (
            getEnhancement(selectedSlot) == Enhancement.STEADY_HAND &&
            tier > 0 &&
            !placed &&
            shouldSaveOre(tier, world.random)
        ) {
            world.playSound(x, y, z, "random.fizz", 0.3f, 0.6f);
            world.setBlock(x, y, z, this.id);
        }

        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}