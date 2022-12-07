package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.registration.BoatItems;
import com.nyuppo.registration.ModItems;
import com.nyuppo.util.ModBoatType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public class BoatDropsMixin {
    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItemMixin(CallbackInfoReturnable<Item> ci) {
        BoatEntity.Type type = ((BoatEntity)(Object)this).getBoatType();
        if (type == ModBoatType.SAKURA) {
            ci.setReturnValue(BoatItems.SAKURA_BOAT);
        } else if (type == ModBoatType.AZALEA) {
            ci.setReturnValue(BoatItems.AZALEA_BOAT);
        } else if (type == ModBoatType.WILLOW) {
            ci.setReturnValue(BoatItems.WILLOW_BOAT);
        } else if (type == ModBoatType.BAOBAB) {
            ci.setReturnValue(BoatItems.BAOBAB_BOAT);
        }
    }
}
