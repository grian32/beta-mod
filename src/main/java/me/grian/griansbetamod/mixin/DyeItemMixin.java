package me.grian.griansbetamod.mixin;

import me.grian.griansbetamod.ConfigScreen;
import me.grians.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Unique
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (ConfigScreen.config.lapisSpeedBoost) {
            // lapis
//        if (stack.getDamage() == 4) {
            --stack.count;
            ((IPlayerEntityMixin) (Object) user).beta_mod$setSpeedBoostTicks(1200);
//        }

            return stack;
        }
        return stack;
    }
}
