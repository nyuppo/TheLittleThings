package com.nyuppo.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrunkPlacerType.class)
public interface TrunkPlacerTypeInvoker<P extends TrunkPlacer> {
    @Invoker("register")
    static <P extends TrunkPlacer> TrunkPlacerType<P> invokeRegister(String id, Codec<P> codec) {
        throw new AssertionError();
    }
}
