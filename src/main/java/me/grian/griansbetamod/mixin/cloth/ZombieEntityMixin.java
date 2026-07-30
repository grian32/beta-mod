package me.grian.griansbetamod.mixin.cloth;

import me.grian.griansbetamod.BetaMod;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends MonsterEntity {
    public ZombieEntityMixin(World world) {
        super(world);
    }

    @Override
    protected void dropItems() {
        if (this.random.nextBoolean()) {
            this.dropItem(
                new ItemStack(BetaMod.cloth, 1),
                0.0F
            );
        }
    }
}
