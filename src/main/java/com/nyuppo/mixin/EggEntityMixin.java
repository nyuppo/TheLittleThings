package com.nyuppo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EggEntity.class)
public class EggEntityMixin {
    @Inject(method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V", at = @At("HEAD"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onEntityHitMixin(EntityHitResult entityHitResult, CallbackInfo info) {
        Entity entity = entityHitResult.getEntity();
        if (entity.isPlayer()) {
            ThrownEntity projectile = ((ThrownItemEntity)(Object)this);
            LivingEntity livingEntity = (LivingEntity) entity;
            Entity self = ((Entity)(Object)this);

            DamageSource source = DamageSource.thrownProjectile(projectile, projectile.getOwner());
            if (!livingEntity.blockedByShield(source)) {
                livingEntity.damage(source, -1f);
                livingEntity.takeKnockback(0, self.getX() - livingEntity.getX(), self.getZ() - livingEntity.getZ());
            }
        }
    }
}
