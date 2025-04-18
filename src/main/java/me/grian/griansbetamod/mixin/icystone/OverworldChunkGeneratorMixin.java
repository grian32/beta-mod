package me.grian.griansbetamod.mixin.icystone;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.grian.griansbetamod.mixinutils.IcyOresKt.convertOresToIcy;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
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
        if(ConfigScreen.config.icyStone && value == BetaMod.icyStone.id){
            return Block.STONE.id;
        }
        return value;
    }

    @Redirect(method = "decorate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;id:I", opcode = Opcodes.GETFIELD))
    private int injected(Block instance) {
        return convertOresToIcy(instance.id, biomeMod);
    }
}
