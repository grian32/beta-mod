package me.grian.griansbetamod.mixin.breakingspeedwood;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Shadow
    private static Block[] axeEffectiveBlocks;

    static {
        axeEffectiveBlocks = new Block[]{
                Block.PLANKS,
                Block.BOOKSHELF,
                Block.LOG,
                Block.CHEST,
                Block.CRAFTING_TABLE,
                Block.WOODEN_STAIRS
        };
    }
}
