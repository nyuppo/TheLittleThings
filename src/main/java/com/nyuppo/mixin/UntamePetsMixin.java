package com.nyuppo.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class UntamePetsMixin {
    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        ItemStack itemStack = player.getStackInHand(hand);
        AnimalEntity animalEntity = (AnimalEntity)(Object)this;
        if (animalEntity instanceof TameableEntity) {
            TameableEntity tameableEntity = (TameableEntity)animalEntity;
            if (tameableEntity.isTamed() && tameableEntity.getOwnerUuid().compareTo(player.getUuid()) == 0) {
                if (itemStack.isOf(Items.PUFFERFISH) && player.isSneaking()) {
                    tameableEntity.setTamed(false);
                    tameableEntity.setOwnerUuid(null);
                    tameableEntity.setSitting(false);
                    tameableEntity.world.sendEntityStatus(tameableEntity, (byte)6);
                    if (tameableEntity instanceof WolfEntity) {
                        ((WolfEntity)tameableEntity).setCollarColor(DyeColor.RED);
                    } else if (tameableEntity instanceof CatEntity) {
                        ((CatEntity)tameableEntity).setCollarColor(DyeColor.RED);
                        ((CatEntity)tameableEntity).setInSittingPose(false);
                        ((CatEntity)tameableEntity).setInSleepingPose(false);
                    }

                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    ci.setReturnValue(ActionResult.success(tameableEntity.world.isClient));
                }
            }
        }
    }
}