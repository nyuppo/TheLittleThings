package com.nyuppo.mixin;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public class ArmorStandEntityMixin {
    @Inject(at = @At("HEAD"), method = "interactAt", cancellable = true)
    private void interactAt(final PlayerEntity player, final Vec3d hitPos, final Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ItemStack heldStack = player.getStackInHand(hand);
        Item heldStackItem = heldStack.getItem();

        if (player.isSneaking()) {
            if (heldStackItem == Items.STICK || heldStackItem == Items.SMOOTH_STONE_SLAB || heldStackItem instanceof AxeItem) {
                boolean hasItems = false;
                if (heldStackItem == Items.STICK) {
                    for (int i = 0; i < ((ArmorStandEntityAccessor)this).cHeldItems().size(); i++) {
                        if (!((ItemStack)((ArmorStandEntityAccessor)this).cHeldItems().get(i)).isEmpty()) {
                            hasItems = true;
                            break;
                        }
                    }

                    if (!hasItems) {
                        ((ArmorStandEntityAccessor)this).cSetShowArms(!((ArmorStandEntityAccessor)this).cShouldShowArms());
                    }
                } else if (heldStackItem == Items.SMOOTH_STONE_SLAB) {
                    ((ArmorStandEntityAccessor)this).cSetHideBasePlate(!((ArmorStandEntityAccessor)this).cShouldHideBasePlate());
                } else if (heldStackItem instanceof AxeItem) {
                    ((ArmorStandEntityAccessor)this).cSetSmall(!((ArmorStandEntityAccessor)this).cIsSmall());
                }

                if (!hasItems) {
                    ((EntityAccessor)this).cWorld().playSound((PlayerEntity)null, ((EntityAccessor)this).cGetX(), ((EntityAccessor)this).cGetY(), ((EntityAccessor)this).cGetZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    info.setReturnValue(ActionResult.SUCCESS); // SUCCESS
                } else {
                    info.setReturnValue(ActionResult.FAIL); // FAIL
                }
            }
        }

    }
}