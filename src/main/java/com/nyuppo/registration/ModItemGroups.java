package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup HAT_GROUP;

    static {
        HAT_GROUP = FabricItemGroupBuilder.build(
                TheLittleThings.ID("hats"), () -> new ItemStack(HatItems.ARMORER_HAT)
        );
    }
}
