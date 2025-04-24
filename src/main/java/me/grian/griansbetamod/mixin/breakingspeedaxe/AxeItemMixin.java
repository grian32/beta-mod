package me.grian.griansbetamod.mixin.breakingspeedaxe;

import com.google.common.collect.ObjectArrays;
import me.grian.griansbetamod.config.Config;
import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Shadow
    private static Block[] axeEffectiveBlocks;

    static {
        if (ConfigScreen.config.axeEffectiveBlocks) {
            Block[] newBlocks = new Block[]{
                    Block.CRAFTING_TABLE,
                    Block.WOODEN_STAIRS,
                    Block.FENCE
            };

            axeEffectiveBlocks = ObjectArrays.concat(axeEffectiveBlocks, newBlocks, Block.class);
        }
    }
}
