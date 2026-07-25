package me.grian.griansbetamod.mixin.biomes.cold;

import net.minecraft.block.SnowyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {
    @ModifyConstant(
            method = "getCollisionShape",
            constant = @Constant(intValue = 3)
    )
    int modifySnowCollision(int constant) {
        return 4;
    }
}
