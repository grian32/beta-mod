package me.grian.griansbetamod.mixin.breakingspeedwood;

import com.google.common.collect.ObjectArrays;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Debug(export = true)
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Shadow
    private static Block[] axeEffectiveBlocks;

    static {
        Block[] newBlocks = new Block[]{
                Block.CRAFTING_TABLE,
                Block.WOODEN_STAIRS
        };

        axeEffectiveBlocks = ObjectArrays.concat(axeEffectiveBlocks, newBlocks, Block.class);
    }
}
