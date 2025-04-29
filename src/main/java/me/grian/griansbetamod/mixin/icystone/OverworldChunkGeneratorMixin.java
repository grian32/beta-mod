package me.grian.griansbetamod.mixin.icystone;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.grian.griansbetamod.mixinutils.IcyOresKt.convertOresToIcy;

@Debug(export = true)
@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    /**
     * FIXME: if u spawn in a spruce/taiga biome then caves generate fine in those but not other biomes and vice versa where if i spawn in a forest or something then caves wont generate in a tundra/taiga
     * I think this issue might be due to buildTerrain being called once at the start of worldgen and the biome going off of that, but would require further rewriting
     * due to the redirect's not really letting me access functions param, would need to rewrite everything that uses it to check each time
     */

    /**
     * FIXME: weird bug where it thinks biome is null when passing to convertToIcyOres(...)
     */

    @Shadow
    private World world;

    @Unique
    private Biome biomeMod;

    /**
     * @author Telvarost
     * @source <a href="https://github.com/telvarost/TelsTerrain-StationAPI/blob/997b7a33dc3b627750c6e124b67ed0cc576e0fbd/src/main/java/com/github/telvarost/telsterrain/mixin/OverworldChunkGeneratorMixin.java#L35">...</a>
     */
    @Inject(method = "buildTerrain", at = @At("HEAD"))
    void buildTerrain(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes, double[] temperatures, CallbackInfo ci) {
        if (ConfigScreen.config.icyStone) {
            int var4 = chunkX * 16;
            int var5 = chunkZ * 16;
            biomeMod = this.world.method_1781().getBiome(var4, var5);
        }
    }

    // https://discord.com/channels/397834523028488203/782730916396924968/1344424528561963020
    @ModifyVariable(method = "buildTerrain", at = @At("STORE"), ordinal = 15)
    private int injected(int value, int chunkX, int chunkZ, byte[] blocks, Biome[] biomes, double[] temperatures) {
        if (ConfigScreen.config.icyStone && value == Block.STONE.id && (biomeMod == Biome.TAIGA || biomeMod == Biome.TUNDRA)) {
            return BetaMod.icyStone.id;
        }
        return value;
    }

    @ModifyVariable(method = "buildSurfaces", at = @At("STORE"), ordinal = 13)
    private int injected(int value, int chunkX, int chunkZ, byte[] blocks, Biome[] biomes) {
        if (ConfigScreen.config.icyStone && value == BetaMod.icyStone.id) {
            return Block.STONE.id;
        }
        return value;
    }

    @Redirect(method = "decorate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;id:I", opcode = Opcodes.GETFIELD))
    private int injectedDecorate(Block instance) {
        if (ConfigScreen.config.icyStone) {
            return convertOresToIcy(instance.id, biomeMod);
        }

        // required but never gets here since convert ores to icy default to base id by default
        return instance.id;
    }

    @Redirect(method = "buildSurfaces", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;id:I", opcode = Opcodes.GETFIELD, ordinal = 2))
    private int injectedBuildSurfaces(Block instance) {
        if (ConfigScreen.config.icyStone && instance.id == Block.STONE.id && (biomeMod == Biome.TAIGA || biomeMod == Biome.TUNDRA)) {
            return BetaMod.icyStone.id;
        }

        return instance.id;
    }
}
