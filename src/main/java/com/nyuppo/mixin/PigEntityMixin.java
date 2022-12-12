package com.nyuppo.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {
    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract boolean isSaddled();

    @Shadow @Final
    private static TrackedData<Boolean> SADDLED;

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void removeSaddle(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.isSneaking() && player.getStackInHand(hand).isOf(Items.SHEARS) && isSaddled() && !hasPassengers()) {
            dropStack(new ItemStack(Items.SADDLE));
            dataTracker.set(SADDLED, false);
            player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1.0f, 1.0f);
            cir.setReturnValue(ActionResult.SUCCESS); // SUCCESS
        }
    }
}