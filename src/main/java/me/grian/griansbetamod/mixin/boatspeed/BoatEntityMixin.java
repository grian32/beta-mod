package me.grian.griansbetamod.mixin.boatspeed;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
    public BoatEntityMixin(World world) {
        super(world);
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.4))
    private double changeMaximumSpeed(double original) {
        if (this.passenger != null) {
            return 1.0;
        }

        return original;
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.2))
    private double changeAcceleration(double original) {
        if (this.passenger != null) {
            return 1.2;
        }

        return original;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;horizontalCollision:Z", opcode = Opcodes.GETFIELD))
    private boolean disableCollisionBreaking(BoatEntity boat) {
        return false;
    }

    @ModifyConstant(method = "damage", constant = @Constant(intValue = 3))
    private int disablePlankDrop(int constant) {
        return 0;
    }

    @ModifyConstant(method = "damage", constant = @Constant(intValue = 2))
    private int disableStickDrop(int constant) {
        return 0;
    }

    @SuppressWarnings("DiscouragedShift")
    @Inject(
        method = "damage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/vehicle/BoatEntity;markDead()V",
            shift = At.Shift.BEFORE
        )
    )
    private void dropBoat(Entity amount, int par2, CallbackInfoReturnable<Boolean> cir) {
        this.dropItem(Item.BOAT.id, 1, 0.0F);
    }

    // im going for tighter steering here, also the constant is super dumb cuz 0.99F exists then is casted into a double which compiles to like 0.990000......9.. so on
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue= (double)0.99F))
    private double increaseHorizontalDrag(double constant) {
        return 0.94;
    }
}
