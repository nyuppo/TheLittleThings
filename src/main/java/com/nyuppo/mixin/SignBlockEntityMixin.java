package com.nyuppo.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {
    @Inject(method = "onActivate(Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At("HEAD"))
    public void useOnBlock(ServerPlayerEntity serverPlayer, CallbackInfoReturnable<Boolean> callback){
        ItemStack itemStack = serverPlayer.getActiveItem();
        Item item = itemStack.getItem();

        boolean isDye = item instanceof DyeItem;
        boolean isGlowInkSack = itemStack.isOf(Items.GLOW_INK_SAC);
        boolean isInkSack = itemStack.isOf(Items.INK_SAC);

        if (isDye || isGlowInkSack || isInkSack){
            return;
        }

        if (serverPlayer.isSneaking() && serverPlayer.getAbilities().allowModifyWorld) {
            SignBlockEntity sign = (SignBlockEntity) (Object) this;
            sign.setEditable(true);
            serverPlayer.openEditSignScreen(sign);
        }
    }
}
