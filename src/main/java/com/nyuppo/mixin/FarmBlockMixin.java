package com.nyuppo.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public abstract class FarmBlockMixin extends Block {
    public FarmBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method="onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V", at=@At("HEAD"), cancellable=true)
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float distance, CallbackInfo ci) {
        if (entity instanceof LivingEntity && EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, (LivingEntity)entity) >= 1) {
            super.onLandedUpon(world, state, pos, entity, distance);
            ci.cancel();
        }
    }
}
