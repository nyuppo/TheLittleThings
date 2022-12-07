package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.registration.BoatItems;
import com.nyuppo.util.ModBoatType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public class ChestBoatDropsMixin {
    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItemMixin(CallbackInfoReturnable<Item> ci) {
        BoatEntity.Type type = ((BoatEntity)(Object)this).getBoatType();
        if (type == ModBoatType.SAKURA) {
            ci.setReturnValue(BoatItems.SAKURA_CHEST_BOAT);
        } else if (type == ModBoatType.AZALEA) {
            ci.setReturnValue(BoatItems.AZALEA_CHEST_BOAT);
        } else if (type == ModBoatType.WILLOW) {
            ci.setReturnValue(BoatItems.WILLOW_CHEST_BOAT);
        } else if (type == ModBoatType.BAOBAB) {
            ci.setReturnValue(BoatItems.BAOBAB_CHEST_BOAT);
        }
    }
}
