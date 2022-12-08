package com.nyuppo.mixin;

import com.nyuppo.block.HedgeBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(RenderLayers.class)
public class LeavesRenderLayerMixin {
    @Shadow
    private static boolean fancyGraphicsOrBetter;

    @Inject(method = "getBlockLayer(Lnet/minecraft/block/BlockState;)Lnet/minecraft/client/render/RenderLayer;", at = @At("HEAD"), cancellable = true)
    private static void getBlockLayer(BlockState state, CallbackInfoReturnable<RenderLayer> ci) {
        Block block = state.getBlock();
        if (block instanceof HedgeBlock) {
            ci.setReturnValue(fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid());
        }
    }

    @Inject(method = "getMovingBlockLayer(Lnet/minecraft/block/BlockState;)Lnet/minecraft/client/render/RenderLayer;", at = @At("HEAD"), cancellable = true)
    private static void getMovingBlockLayer(BlockState state, CallbackInfoReturnable<RenderLayer> ci) {
        Block block = state.getBlock();
        if (block instanceof HedgeBlock) {
            ci.setReturnValue(fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid());
        }
    }
}
