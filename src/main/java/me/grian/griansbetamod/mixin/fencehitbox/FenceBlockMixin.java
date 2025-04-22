package me.grian.griansbetamod.mixin.fencehitbox;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends Block{
    public FenceBlockMixin(int id, Material material) {
        super(id, material);
    }

    /**
     * @author Grian
     * @reason No point leaving the original code in & would be incompatible with other mods either which way
     */
    @Overwrite
    public Box getCollisionShape(World world, int x, int y, int z) {
        boolean bl = this.shouldConnectTo(world, x, y, z - 1);
        boolean bl2 = this.shouldConnectTo(world, x, y, z + 1);
        boolean bl3 = this.shouldConnectTo(world, x - 1, y, z);
        boolean bl4 = this.shouldConnectTo(world, x + 1, y, z);
        float f = 0.375f;
        float f2 = 0.625f;
        float f3 = 0.375f;
        float f4 = 0.625f;
        if (bl) {
            f3 = 0.0f;
        }
        if (bl2) {
            f4 = 1.0f;
        }
        if (bl3) {
            f = 0.0f;
        }
        if (bl4) {
            f2 = 1.0f;
        }
        return Box.createCached((float)x + f, y, (float)z + f3, (float)x + f2, (float)y + 1.5f, (float)z + f4);
    }

    @Unique
    public boolean shouldConnectTo(World world, int x, int y, int z) {
        int n = world.getBlockId(x, y, z);
        if (n == this.id) {
            return true;
        }
        Block block = Block.BLOCKS[n];
        if (block != null && block.material.isSolid() && block.isFullCube()) {
            return block.material != Material.PUMPKIN;
        }
        return false;
    }

}
