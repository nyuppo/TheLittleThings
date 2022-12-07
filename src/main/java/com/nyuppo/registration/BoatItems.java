package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import com.nyuppo.util.ModBoatType;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BoatItems {
    public static final Item SAKURA_BOAT = new BoatItem(false, ModBoatType.SAKURA, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item SAKURA_CHEST_BOAT = new BoatItem(true, ModBoatType.SAKURA, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item AZALEA_BOAT = new BoatItem(false, ModBoatType.AZALEA, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item AZALEA_CHEST_BOAT = new BoatItem(true, ModBoatType.AZALEA, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item WILLOW_BOAT = new BoatItem(false, ModBoatType.WILLOW, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item WILLOW_CHEST_BOAT = new BoatItem(true, ModBoatType.WILLOW, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_BOAT = new BoatItem(false, ModBoatType.BAOBAB, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_CHEST_BOAT = new BoatItem(true, ModBoatType.BAOBAB, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));

    public static void registerBoats() {
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_boat"), SAKURA_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_chest_boat"), SAKURA_CHEST_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_boat"), AZALEA_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_chest_boat"), AZALEA_CHEST_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_boat"), WILLOW_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_chest_boat"), WILLOW_CHEST_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_boat"), BAOBAB_BOAT);
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_chest_boat"), BAOBAB_CHEST_BOAT);
    }
}
