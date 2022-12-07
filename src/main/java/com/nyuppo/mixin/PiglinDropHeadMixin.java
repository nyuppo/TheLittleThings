package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.registration.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinEntity.class)
public class PiglinDropHeadMixin {
    @Inject(method = "dropEquipment", at = @At("RETURN"))
    private void onDropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
        //if (((LivingEntity)(Object)this) instanceof PiglinEntity) {
            if (source.getAttacker() instanceof CreeperEntity && ((CreeperEntity) source.getAttacker()).shouldDropHead()) {
                ItemStack itemStack = new ItemStack(ModItems.PIGLIN_HEAD);
                ((CreeperEntity) source.getAttacker()).onHeadDropped();
                ((PiglinEntity)(Object)this).dropStack(itemStack);
            }
        //}
    }
}
