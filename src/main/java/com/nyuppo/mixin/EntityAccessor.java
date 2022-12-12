package com.nyuppo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("world")
    World cWorld();

    @Invoker("getX")
    double cGetX();

    @Invoker("getY")
    double cGetY();

    @Invoker("getZ")
    double cGetZ();
}
