package me.grian.griansbetamod.mixin.lapisspeedboost;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerEntityMixin {
    @Shadow
    public abstract float getEyeHeight();

    @Unique
    int speedBoostTicks = 0;

    public PlayerEntityMixin(World world) {
        super(world);
    }


    @Inject(method = "tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (ConfigScreen.config.lapisSpeedBoost) {
            // ripped from b1.9-pre3 - public void updateVelocity(float sideways, float forwards, float scale)
            float scale;

            if (beta_mod$getSpeedBoostTicks() != 0) {
                scale = 0.035f;
            } else {
                scale = 0.0f;
            }

            if (!this.isSwimming() && scale > 0.0f) {
                float movementMagnitude = MathHelper.sqrt(this.sidewaysSpeed * this.sidewaysSpeed + this.forwardSpeed * this.forwardSpeed);
                if (movementMagnitude < 0.01f) {
                    // basically standing still
                    return;
                }
                if (movementMagnitude < 1.0f) {
                    // set minimum as moving too slow
                    movementMagnitude = 1.0f;
                }

                // adjust for scale
                movementMagnitude = scale / movementMagnitude;

                float yawSin = MathHelper.sin(this.yaw * (float) Math.PI / 180.0f);
                float yawCos = MathHelper.cos(this.yaw * (float) Math.PI / 180.0f);

                this.velocityX += (this.sidewaysSpeed *= movementMagnitude) * yawCos - (this.forwardSpeed *= movementMagnitude) * yawSin;
                this.velocityZ += this.forwardSpeed * yawCos + this.sidewaysSpeed * yawSin;
            }
        }
    }

    @Override
    public int beta_mod$getSpeedBoostTicks() {
        return speedBoostTicks;
    }

    @Override
    public void beta_mod$setSpeedBoostTicks(int value) {
        speedBoostTicks = value;
    }

    @Unique
    private boolean isSwimming() {
        int playerX = MathHelper.floor(this.x);
        int playerY = MathHelper.floor(this.y);
        int playerZ = MathHelper.floor(this.z);

        int currentBlockId = this.world.getBlockId(playerX, playerY, playerZ);
        // this covers jumping in >1 block deep water
        int blockBelowId = this.world.getBlockId(playerX, playerY - 1, playerZ);
        // this covers jumping in 1 block deep water
        int blockTwiceBelowId = this.world.getBlockId(playerX, playerY - 2, playerZ);

        return isWater(currentBlockId) || isWater(blockBelowId) || isWater(blockTwiceBelowId);
    }

    @Unique
    private boolean isWater(int id) {
        return id == Block.WATER.id || id == Block.FLOWING_WATER.id;
    }
}
