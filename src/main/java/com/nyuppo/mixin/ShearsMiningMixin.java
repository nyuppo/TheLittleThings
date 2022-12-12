package com.nyuppo.mixin;

import com.nyuppo.util.tags.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsMiningMixin {
    @Inject(method = "postMine", at = @At("TAIL"), cancellable = true)
    private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> ci) {
        if (state.isIn(ModBlockTags.HEDGES)) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("TAIL"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> ci) {
        if (state.isIn(ModBlockTags.HEDGES)) {
            ci.setReturnValue(15.0f);
        }
    }
}