package com.nyuppo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractHorseEntity.class)
public abstract class HorseEntitySpeedMixin extends Entity {
    public HorseEntitySpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 1))
    private Vec3d onSetVelocity(Vec3d velocity) {
        return new Vec3d(
                this.getX() - this.lastRenderX,
                this.getY() - this.lastRenderY,
                this.getZ() - this.lastRenderZ);
    }
}
