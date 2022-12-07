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

@Mixin(WallBlock.class)
public class WallConnectMixin {
    @Inject(method = "shouldConnectTo", at = @At("RETURN"), cancellable = true)
    private void onShouldConnectTo(BlockState state, boolean faceFullSquare, Direction side, CallbackInfoReturnable<Boolean> ci) {
        Block block = state.getBlock();
        boolean bl = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, side);
        boolean bl2 = block instanceof ModFenceGateBlock && ModFenceGateBlock.canWallConnect(state, side);
        ci.setReturnValue(state.isIn(BlockTags.WALLS) || !Block.cannotConnect(state) && faceFullSquare || block instanceof PaneBlock || bl || bl2);
    }
}
