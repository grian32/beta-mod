package me.grian.griansbetamod.mixin.icystone;

import me.grian.griansbetamod.BetaMod;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(CaveWorldCarver.class)
public class CaveWorldCarverMixin {
    @Unique
    private World world;

    /**
     * @author Telvarost
     * @source <a href="https://github.com/telvarost/TelsTerrain-StationAPI/blob/997b7a33dc3b627750c6e124b67ed0cc576e0fbd/src/main/java/com/github/telvarost/telsterrain/mixin/OverworldChunkGeneratorMixin.java#L35">...</a>
     */
    @Inject(method = "place(Lnet/minecraft/world/World;IIII[B)V", at = @At("HEAD"))
    void buildTerrain(World world, int startChunkX, int startChunkZ, int chunkX, int chunkZ, byte[] blocks, CallbackInfo ci) {
        if (ConfigScreen.config.icyStone) {
            this.world = world;
//            biomeMod = this.world.method_1781().getBiome(var4, var5);
        }
    }

    @Redirect(method = "placeTunnels", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;id:I", opcode = Opcodes.GETFIELD))
    private int injected(Block instance) {
        // this mixins very strangely
        if (instance == Block.STONE && ConfigScreen.config.icyStone) {
            // gets lenghty if done in one if lol
            Biome biome = world.method_1781().getBiome((int) Math.floor(instance.maxX), (int) Math.floor(instance.maxZ));
            if(biome == Biome.TAIGA || biome == Biome.TUNDRA) {
                return BetaMod.icyStone.id;
            }
        }

        return instance.id;
    }

}
