package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {
    // SEASONAL ascii
    @Unique
    private final long SEASONAL_FOREST_NOISE_SALT = 0x534541534F4E414CL;

    @Unique
    private static final int SEASONAL_BURNT_ORANGE = 0xB85F2E;
    @Unique
    private static final int SEASONAL_ORANGE       = 0xD97A2F;
    @Unique
    private static final int SEASONAL_GOLD         = 0xD9A23A;

    // cant think of a good way to work world seed in here so ill cope with non world seeded
    @Unique
    private final OctavePerlinNoiseSampler seasonalForestNoise = new OctavePerlinNoiseSampler(new Random(SEASONAL_FOREST_NOISE_SALT), 1);

    @Inject(method = "getColorMultiplier", at=@At("HEAD"), cancellable = true)
    void getColorMultiplier(BlockView blockView, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        if ((blockView.getBlockMeta(x, y, z) & 4) == 0) return;

        double regionX = x / 24.0;
        double regionZ = z / 24.0;

        double noise = seasonalForestNoise.sample(regionX, regionZ);

        if (noise < -0.25) {
            cir.setReturnValue(SEASONAL_BURNT_ORANGE);
        } else if (noise < 0.30) {
            cir.setReturnValue(SEASONAL_ORANGE);
        } else {
            cir.setReturnValue(SEASONAL_GOLD);
        }
    }

}
