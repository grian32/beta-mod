package me.grian.griansbetamod.mixin.shrine;

import me.grian.griansbetamod.mixin.WorldRegionAccessor;
import me.grian.griansbetamod.shrine.ShrineState;
import net.minecraft.block.Block;
import net.minecraft.block.LiquidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin extends Block {
    public LiquidBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "getColorMultiplier", at=@At("HEAD"), cancellable = true)
    void getColorMultiplier(BlockView blockView, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        if (this.material != Material.WATER) return;
        World world = ((WorldRegionAccessor) blockView).getWorld();
        if (world == null) return;
        long dayTime = world.getTime() % 24000L;
        boolean isNight = dayTime >= 13000L && dayTime < 23000L;
        boolean shrineActivated = ShrineState.get(world).getShrineActivated();

        if (shrineActivated && isNight) {
            cir.setReturnValue(0xFFFFFF);
        } else {
            cir.setReturnValue(0x3F7690);
        }
    }
}
