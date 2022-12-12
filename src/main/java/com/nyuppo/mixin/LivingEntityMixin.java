package com.nyuppo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "stopRiding()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onStopRiding(CallbackInfo ci, Entity entity) {
        if (entity != null && entity instanceof ArmorStandEntity && entity.hasCustomName() && entity.getCustomName().getString() == "_seat") {
            entity.remove(Entity.RemovalReason.KILLED);
            ((LivingEntity)(Object)this).refreshPositionAfterTeleport(((LivingEntity)(Object)this).getPos().add(0.0D, 1.0D, 0.0D));
        }
    }
}
