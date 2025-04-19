package me.grian.griansbetamod.mixin.lapisspeedboost;

import me.grian.griansbetamod.config.ConfigScreen;
import me.grians.griansbetamod.mixininterfaces.IPlayerEntityMixin;
import net.minecraft.block.Block;
import net.minecraft.block.LiquidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.Sys;
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
            /** ripped from b1.9-pre3 - MobEntity.moveRelative
             public void updateVelocity(float sideways, float forwards, float scale) {
             float f = MathHelper.sqrt(sideways * sideways + forwards * forwards);
             if (f < 0.01f) {
             return;
             }
             if (f < 1.0f) {
             f = 1.0f;
             }
             f = scale / f;
             float f2 = MathHelper.sin(this.yaw * (float)Math.PI / 180.0f);
             float f3 = MathHelper.cos(this.yaw * (float)Math.PI / 180.0f);
             this.velocityX += (double)((sideways *= f) * f3 - (forwards *= f) * f2);
             this.velocityZ += (double)(forwards * f3 + sideways * f2);
             }*/
            float scale;

            if (beta_mod$getSpeedBoostTicks() != 0) {
                scale = 0.035f;
            } else {
                scale = 0.0f;
            }

            if (!this.isSwimming() && scale > 0.0f) {
                float f = MathHelper.sqrt(this.sidewaysSpeed * this.sidewaysSpeed + this.forwardSpeed * this.forwardSpeed);
                if (f < 0.01f) {
                    return;
                }
                if (f < 1.0f) {
                    f = 1.0f;
                }

                f = scale / f;
                float f2 = MathHelper.sin(this.yaw * (float) Math.PI / 180.0f);
                float f3 = MathHelper.cos(this.yaw * (float) Math.PI / 180.0f);
                this.velocityX += (this.sidewaysSpeed *= f) * f3 - (this.forwardSpeed *= f) * f2;
                this.velocityZ += this.forwardSpeed * f3 + this.sidewaysSpeed * f2;
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

    /**
     * based on net.minecraft.entity.Entity#isInFluid(net.minecraft.block.material.Material)
     */
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
