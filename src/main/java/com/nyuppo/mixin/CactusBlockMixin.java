package com.nyuppo.mixin;

import com.nyuppo.registration.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    @Inject(method = "canPlaceAt", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void onCanPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        BlockState blockState2 = world.getBlockState(pos.down());
        if (blockState2.isOf(ModBlocks.ARID_SAND)) {
            ci.setReturnValue(true);
        }
    }
}
