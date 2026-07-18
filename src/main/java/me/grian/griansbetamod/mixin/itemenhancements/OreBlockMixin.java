package me.grian.griansbetamod.mixin.itemenhancements;

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


    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(int textureId, int par2, CallbackInfo ci) {
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
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, BlockState brokenState, int meta) {
        ItemStack selectedSlot = playerEntity.inventory.getSelectedItem();

        int tier = getEnhancementTier(selectedSlot);

        if (
            getEnhancement(selectedSlot) == Enhancement.STEADY_HAND &&
            tier > 0 &&
            !brokenState.get(PLACED) &&
            shouldSaveOre(tier, world.random)
        ) {
            world.playSound(x, y, z, "random.fizz", 0.3f, 0.6f);
            world.setBlock(x, y, z, this.id);
        }

        super.afterBreak(world, playerEntity, x, y, z, meta);
    }
}
