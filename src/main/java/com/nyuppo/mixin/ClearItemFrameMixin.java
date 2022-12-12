package com.nyuppo.mixin;

import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public class ClearItemFrameMixin {
    @Inject(
            method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!((ItemFrameEntity)(Object)this).world.isClient) {
            if (itemStack.isOf(Items.GLASS_PANE) && player.isSneaking() && !((ItemFrameEntity)(Object)this).getHeldItemStack().isEmpty()) {
                if (((ItemFrameEntity)(Object)this).isInvisible()) {
                    ((ItemFrameEntity)(Object)this).setInvisible(false);
                } else {
                    ((ItemFrameEntity)(Object)this).setInvisible(true);
                }
                ci.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    @Inject(
            method = "removeFromFrame(Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD")
    )
    private void onRemoveFromFrame(ItemStack map, CallbackInfo ci) {
        if (((ItemFrameEntity)(Object)this).isInvisible()) {
            ((ItemFrameEntity)(Object)this).setInvisible(false);
        }
    }

}
