package com.nyuppo.mixin;

import com.nyuppo.entity.passive.CamelEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class CamelJumpMixin {
    @Inject(method = "hasJumpingMount", at = @At("RETURN"), cancellable = true)
    private void onHasJumpingMount(CallbackInfoReturnable<Boolean> ci) {
        Entity entity = ((ClientPlayerEntity)(Object)this).getVehicle();
        if (entity instanceof CamelEntity) {
            if (!((CamelEntity)entity).canJump(((ClientPlayerEntity)(Object)this))) {
                ci.setReturnValue(false);
            }
        }
    }
}
