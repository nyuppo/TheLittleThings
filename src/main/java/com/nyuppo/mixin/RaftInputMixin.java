package com.nyuppo.mixin;

import com.nyuppo.entity.vehicle.RaftEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class RaftInputMixin {
    @Inject(method = "tickRiding", at = @At("TAIL"))
    private void onTickRiding(CallbackInfo ci) {
        ClientPlayerEntity player = ((ClientPlayerEntity)(Object)this);
        if (player.getVehicle() instanceof RaftEntity) {
            RaftEntity raftEntity = (RaftEntity)(player.getVehicle());
            raftEntity.setInputs(player.input.pressingLeft, player.input.pressingRight, player.input.pressingForward, player.input.pressingBack);
            ((ClientPlayerRidingAccessor)((ClientPlayerEntity)(Object)this)).setRiding(player.input.pressingLeft || player.input.pressingRight || player.input.pressingForward || player.input.pressingBack);
        }
    }
}
