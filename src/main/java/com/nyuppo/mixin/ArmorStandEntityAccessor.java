package com.nyuppo.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArmorStandEntity.class)
public interface ArmorStandEntityAccessor {
    @Accessor("heldItems")
    DefaultedList<ItemStack> cHeldItems();

    @Accessor("armorItems")
    DefaultedList<ItemStack> cArmorItems();

    @Invoker("setShowArms")
    void cSetShowArms(boolean showArms);

    @Invoker("setHideBasePlate")
    void cSetHideBasePlate(boolean hideBasePlate);

    @Invoker("setSmall")
    void cSetSmall(boolean small);

    @Invoker("shouldShowArms")
    boolean cShouldShowArms();

    @Invoker("shouldHideBasePlate")
    boolean cShouldHideBasePlate();

    @Invoker("isSmall")
    boolean cIsSmall();

    @Invoker("equipStack")
    void cEquipStack(EquipmentSlot slot, ItemStack stack);
}