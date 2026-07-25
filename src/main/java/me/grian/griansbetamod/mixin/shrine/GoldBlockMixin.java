package me.grian.griansbetamod.mixin.shrine;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.shrine.ShrineActivatedSender;
import me.grian.griansbetamod.shrine.ShrineState;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.OreStorageBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OreStorageBlock.class)
public class GoldBlockMixin extends Block {
    public GoldBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Unique
    private static final int GOLD_BLOCK_TEX_ID = 23;

    @Unique
    private static final int[][] SHRINE_CENTER_OFFSETS = {
            {-2, 0},
            {2, 0},
            {0, -2},
            {0, 2}
    };

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity player) {
        if (
            this.textureId != GOLD_BLOCK_TEX_ID ||
            world.getBlockId(x, y - 1, z) != BetaMod.goldStone.id
        ) {
            return;
        }

        BlockPos shrineCenter = findShrineCenter(world, x, y, z);
        if (shrineCenter == null) {
            return;
        }

        if (allPillarsHaveGold(world, shrineCenter)) {
            PlayerEntity pe = (PlayerEntity) player;

            if (world.isRemote) return;

            ShrineState state = ShrineState.get(world);
            if (state.getShrineActivated()) return;

            state.activateShrine();


            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
                ShrineActivatedSender.send(pe, state.getShrineActivated(), true);
            }
        }
    }

    @Unique
    private static BlockPos findShrineCenter(World world, int x, int y, int z) {
        int centerY = y - 4;

        for (int[] offset : SHRINE_CENTER_OFFSETS) {
            int centerX = x + offset[0];
            int centerZ = z + offset[1];

            if (world.getBlockId(centerX, centerY, centerZ) == BetaMod.shrineCenter.id) {
                return new BlockPos(centerX, centerY, centerZ);
            }
        }

        return null;
    }

    @Unique
    private static boolean allPillarsHaveGold(World world, BlockPos shrineCenter) {
        int pillarTopY = shrineCenter.y + 4;

        for (int[] offset : SHRINE_CENTER_OFFSETS) {
            int pillarX = shrineCenter.x + offset[0];
            int pillarZ = shrineCenter.z + offset[1];

            if (world.getBlockId(pillarX, pillarTopY, pillarZ) != Block.GOLD_BLOCK.id) {
                return false;
            }
        }

        return true;
    }
}
