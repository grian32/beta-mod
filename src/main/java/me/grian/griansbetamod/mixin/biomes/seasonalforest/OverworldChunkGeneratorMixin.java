package me.grian.griansbetamod.mixin.biomes.seasonalforest;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @Shadow
    private World world;
    @Unique
    private boolean seasonalForestDecoration;

    @Inject(method = "decorate", at = @At("HEAD"))
    void setSeasonalForestDecoration(ChunkSource chunkSource, int x, int z, CallbackInfo ci) {
        seasonalForestDecoration = world.method_1781().getBiome(x * 16 + 16, z * 16 + 16) == Biome.SEASONAL_FOREST;
    }

    @ModifyConstant(
        method = "decorate",
        constant = @Constant(intValue = 4),
        slice = @Slice(
            from = @At(
                value = "FIELD",
                target = "Lnet/minecraft/block/Block;ROSE:Lnet/minecraft/block/PlantBlock;",
                opcode = Opcodes.GETSTATIC
            ),
            to = @At(
                value = "FIELD",
                target = "Lnet/minecraft/block/Block;BROWN_MUSHROOM:Lnet/minecraft/block/PlantBlock;",
                opcode = Opcodes.GETSTATIC
            )
        )
    )
    int changeBrownMushroomOdds(int constant) {
        return seasonalForestDecoration ? 1 : constant;
    }

    @ModifyConstant(
        method = "decorate",
        constant = @Constant(intValue = 8, ordinal = 0),
        slice = @Slice(
            from = @At(
                value = "FIELD",
                target = "Lnet/minecraft/block/Block;BROWN_MUSHROOM:Lnet/minecraft/block/PlantBlock;",
                opcode = Opcodes.GETSTATIC
            ),
            to = @At(
                value = "FIELD",
                target = "Lnet/minecraft/block/Block;RED_MUSHROOM:Lnet/minecraft/block/PlantBlock;",
                opcode = Opcodes.GETSTATIC
            )
        )
    )
    int changeRedMushroomOdds(int constant) {
        return seasonalForestDecoration ? 2 : constant;
    }
}
