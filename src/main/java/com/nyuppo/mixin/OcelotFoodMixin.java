package com.nyuppo.mixin;

import com.nyuppo.registration.ModItems;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OcelotEntity.class)
public class OcelotFoodMixin {
    @Shadow
    private TemptGoal temptGoal;

    private static final Ingredient MOD_TAMING_INGREDIENT = Ingredient.ofItems(
            Items.COD,
            Items.SALMON,
            ModItems.CARP,
            ModItems.ZANDER,
            ModItems.MACKAREL,
            ModItems.ANCHOVY,
            ModItems.SARDINE,
            ModItems.TUNA,
            ModItems.ROACH,
            ModItems.BREAM,
            ModItems.LARGEMOUTH_BASS,
            ModItems.TILAPIA,
            ModItems.ALBACORE,
            ModItems.POINKER,
            ModItems.BLOWFISH,
            ModItems.TREVALLY,
            ModItems.BLUE_BETTA,
            ModItems.RED_BETTA);

    @Inject(method = "initGoals", at = @At("RETURN"))
    private void onInitGoals(CallbackInfo ci) {
        this.temptGoal = new OcelotEntity.OcelotTemptGoal(((OcelotEntity)(Object)this), 0.6D, MOD_TAMING_INGREDIENT, true);
    }

    @Inject(method = "isBreedingItem", at = @At("RETURN"), cancellable = true)
    private void onIsBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        if (MOD_TAMING_INGREDIENT.test(stack)) {
            ci.setReturnValue(true);
        }
    }
}
