package me.grian.griansbetamod.mixin.breakingspeedfixes;

import com.google.common.collect.ObjectArrays;
import net.minecraft.block.Block;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Shadow
    private static Block[] shovelEffectiveBlocks;

    static {
        Block[] newBlocks = new Block[]{
                Block.SOUL_SAND
        };

        shovelEffectiveBlocks = ObjectArrays.concat(shovelEffectiveBlocks, newBlocks, Block.class);
    }
}
