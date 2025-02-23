package me.grian.griansbetamod.mixin.stairsdrop;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(StairsBlock.class)
public abstract class StairsBlockMixin extends Block {
    @Shadow
    private Block baseBlock;

    public StairsBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "dropStacks(Lnet/minecraft/world/World;IIIIF)V", at = @At("HEAD"), cancellable = true)
    public void dropStacks(World world, int x, int y, int z, int meta, float f, CallbackInfo ci) {
        // mc code would call dropped item count with world.random, probably because some items like ores can drop more
        // based on rng but in this case stairsBlock just calls the base method which always returns 1, so i just
        // hard code it for perf reasons

        // saw another impl which called getDroppedItemMeta which in stairs case always returns 0, it prob went off of
        // orig mc code, but as stairs don't have meta in this version, it's not required so we can just hardcode
        // it as 0

        if (baseBlock == COBBLESTONE) {
            this.dropStack(world, x, y, z, new ItemStack(COBBLESTONE_STAIRS.id, 1, 0));
        } else if (baseBlock == PLANKS) {
            this.dropStack(world, x, y, z, new ItemStack(WOODEN_STAIRS.id, 1, 0));
        }
        ci.cancel();
    }
}
