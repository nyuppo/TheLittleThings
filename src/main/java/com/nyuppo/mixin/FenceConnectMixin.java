package com.nyuppo.mixin;

import com.nyuppo.block.ModFenceGateBlock;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FenceBlock.class)
public class FenceConnectMixin {
    @Inject(method = "canConnect", at = @At("RETURN"), cancellable = true)
    private void onCanConnect(BlockState state, boolean neighborIsFullSquare, Direction dir, CallbackInfoReturnable<Boolean> ci) {
        Block block = state.getBlock();
        boolean bl = canConnectToFence(state);
        boolean bl2 = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, dir);
        boolean bl3 = block instanceof ModFenceGateBlock && ModFenceGateBlock.canWallConnect(state, dir);
        ci.setReturnValue(!Block.cannotConnect(state) && neighborIsFullSquare || bl || bl2 || bl3);
    }

    private boolean canConnectToFence(BlockState state) {
        return state.isIn(BlockTags.FENCES) && state.isIn(BlockTags.WOODEN_FENCES) == ((FenceBlock)(Object)this).getDefaultState().isIn(BlockTags.WOODEN_FENCES);
    }
}
