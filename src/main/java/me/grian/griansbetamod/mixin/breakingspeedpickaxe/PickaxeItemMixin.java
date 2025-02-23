package me.grian.griansbetamod.mixin.breakingspeedpickaxe;

import com.google.common.collect.ObjectArrays;
import net.minecraft.block.Block;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Debug(export = true)
@Mixin(PickaxeItem.class)
public class PickaxeItemMixin {
    @Shadow
    private static Block[] pickaxeEffectiveBlocks;

    static {
        Block[] newBlocks = new Block[]{
                Block.REDSTONE_ORE,
                Block.LIT_REDSTONE_ORE,
                Block.COBBLESTONE_STAIRS
        };

        pickaxeEffectiveBlocks = ObjectArrays.concat(pickaxeEffectiveBlocks, newBlocks, Block.class);
    }
}
