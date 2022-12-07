package com.nyuppo.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface ClientPlayerRidingAccessor {
    @Accessor("riding")
    boolean getRiding();

    @Accessor("riding")
    void setRiding(boolean shouldRiding);
}
