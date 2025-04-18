package me.grian.griansbetamod.mixin.icystone;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
    @Unique
    private Biome biomeMod;

    @Inject(method = "generate", at = @At("HEAD"))
    void buildTerrain(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigScreen.config.icyStone) {
            biomeMod = world.method_1781().getBiome(x, z);
        }
    }
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;id:I", opcode = Opcodes.GETFIELD))
    private int injected(Block instance) {
        if (ConfigScreen.config.icyStone && (biomeMod == Biome.TAIGA || biomeMod == Biome.TUNDRA)) {
            return BetaMod.icyStone.id;
        }

        return Block.STONE.id;
    }
}
