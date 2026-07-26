package me.grian.griansbetamod.mixin.biomes.rainforest;

import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @ModifyConstant(
        method = "decorate",
        constant = @Constant(intValue = 5),
        slice = @Slice(
            from = @At(
                value = "FIELD",
                target = "Lnet/minecraft/world/biome/Biome;RAINFOREST:Lnet/minecraft/world/biome/Biome;",
                opcode = Opcodes.GETSTATIC
            ),
            to = @At(
                value = "FIELD",
                target = "Lnet/minecraft/world/biome/Biome;SEASONAL_FOREST:Lnet/minecraft/world/biome/Biome;",
                opcode = Opcodes.GETSTATIC
            )
        )
    )
    int increaseRainforestTrees(int constant) {
        return 10;
    }
}
