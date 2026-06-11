package me.grian.griansbetamod.mixin.climate;

import lombok.extern.slf4j.Slf4j;
import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Slf4j
@Mixin(SaplingBlock.class)
public class SaplingBlockMixin extends PlantBlock {
    public SaplingBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        Biome biome = world.method_1781().getBiome(x, z);
        if (biome == Biome.TUNDRA || biome == Biome.TAIGA) {
            world.setBlockMeta(x, y, z, 1);
            return;
        } else if (biome == Biome.DESERT) {
            if (checkSurroundingBlocks(world, x, y-1, z, Block.WATER, 3)) {
                if (checkSurroundingBlocks(world, x, y, z, BetaMod.bluePeony, 3)) {
                    world.setBlockMeta(x, y, z, 2);
                } else {
                    world.setBlockMeta(x, y, z, 0);
                }
                return;
            }

            world.setBlock(x, y, z, Block.DEAD_BUSH.id);
            // to be sure
            world.setBlockMeta(x, y, z, 0);
            return;

            // TODO: dupe check? maybe opt
        } else if (checkSurroundingBlocks(world, x, y, z, BetaMod.bluePeony, 3)) {
            world.setBlockMeta(x, y, z, 2);
            return;
        }

        world.setBlockMeta(x, y, z, 0);
    }

    @Inject(method = "getDroppedItemMeta", at = @At("HEAD"), cancellable = true)
    void getDroppedItemMeta(int blockMeta, CallbackInfoReturnable<Integer> cir) {
        if (ConfigScreen.config.climate) {
            cir.setReturnValue(0);
        }
    }

    @Unique
    private boolean checkSurroundingBlocks(World world, int x, int y, int z, Block wantedBlock, int amountWanted) {
        int minX = x - 2;
        int minZ = z - 2;
        int maxX = x + 2;
        int maxZ = z + 2;

        int blockCount = 0;

        for (int cx = minX; cx <= maxX; cx++) {
            for (int cz = minZ; cz <= maxZ; cz++) {
                if (world.getBlockId(cx, y, cz) == wantedBlock.id) {
                    blockCount++;
                    if (blockCount >= amountWanted) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
