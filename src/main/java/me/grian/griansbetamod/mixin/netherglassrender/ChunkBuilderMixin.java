package me.grian.griansbetamod.mixin.netherglassrender;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkBuilder.class)
public class ChunkBuilderMixin {
    /** TODO:
     * ChunkBuilder:
     * update renderlayerempty to [3]
     * loop in 113/123/hasnogeom to < 3
     * loop in reset < 3 @ 190
     *
     * worldrenderer:
     * chunkgllist = generate display to * 3
     * reload - n3 += 4
     *
     * loop in renderframe gamerenderer
     * to <3 @ 440
     * ensure colormask + renderlastchunks
     *
     * make sure to call worldrenderer ..., 2... in gamerender
     */
}
