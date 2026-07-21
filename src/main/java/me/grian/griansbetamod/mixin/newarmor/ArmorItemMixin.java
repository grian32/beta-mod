package me.grian.griansbetamod.mixin.newarmor;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Mutable
    @Shadow
    @Final
    public int maxProtection;
    @Shadow
    @Final
    public int type;
    @Shadow
    @Final
    public int equipmentSlot;
    @Shadow
    @Final
    public int textureIndex;
    // tiers: idx 0 = leather, idx 1 = chain, idx 2 = iron, idx 3 = dia
    // slots: idx 0 = helmet, idx 1 = chest, idx 2 = legs, idx 3 = boots
    @Unique
    private static final int[][] PROTECTION_BY_SLOT_TIER = new int[][]{
            {
                1, 3, 2, 1
            },
            {
                2, 5, 4, 1
            },
            {
                2, 6, 5, 2
            },
            {
                3, 8, 6, 3
            }
    };

    @Unique
    private static final int[] GOLD_PROTECTION = new int[] {
        2, 5, 3, 1
    };

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructorInject(CallbackInfo ci) {
        if (this.textureIndex == 4) {
            this.maxProtection = GOLD_PROTECTION[this.equipmentSlot];
        } else {
            this.maxProtection = PROTECTION_BY_SLOT_TIER[this.type][this.equipmentSlot];
        }
    }
}
