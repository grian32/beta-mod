package me.grian.griansbetamod.mixin.croptrampling;

import me.grian.griansbetamod.config.ConfigScreen;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Objects;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method="onSteppedOn(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void onSteppedOn(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        boolean isPlayer = entity instanceof PlayerEntity;
        PlayerEntity player = null;
        boolean hasArmor = false;
        boolean hasLeatherBoots = false;

        if (isPlayer) {
            player = (PlayerEntity) entity;
            hasArmor = !Arrays.stream(player.inventory.armor).allMatch(Objects::isNull);
        }

        if (isPlayer && hasArmor) {
            hasLeatherBoots = Arrays.stream(player.inventory.armor).anyMatch(
                    i -> i.getItem().id == Item.LEATHER_BOOTS.id
            );
        }

        if (ConfigScreen.config.leatherBootsTrampleCrops && isPlayer && hasLeatherBoots) {
            ci.cancel();
        }
    }
}
