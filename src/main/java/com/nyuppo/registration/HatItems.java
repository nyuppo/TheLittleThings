package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import com.nyuppo.item.HatItem;
import com.nyuppo.item.HatLoreItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class HatItems {
    public static final Item PINK_AXOLOTL_HAT;
    public static final Item BROWN_AXOLOTL_HAT;
    public static final Item GOLD_AXOLOTL_HAT;
    public static final Item CYAN_AXOLOTL_HAT;
    public static final Item BLUE_AXOLOTL_HAT;

    public static final Item BLACK_AXOLOTL_HAT;
    public static final Item GREEN_AXOLOTL_HAT;
    public static final Item PURPLE_AXOLOTL_HAT;
    public static final Item SCULK_AXOLOTL_HAT;

    public static final Item STRAW_HAT;
    public static final Item LIBRARIAN_HAT;
    public static final Item ARMORER_HAT;
    public static final Item WINTER_HAT;
    public static final Item SWAMP_HAT;
    public static final Item LEAF_CROWN;
    public static final Item FLOWER_CROWN;

    public static final Item WITCH_HAT;
    public static final Item RED_WITCH_HAT;

    public static final Item SQUID_HAT;
    public static final Item GLOW_SQUID_HAT;

    public static final Item GREEN_FROGGY_HAT;
    public static final Item ORANGE_FROGGY_HAT;
    public static final Item BEIGE_FROGGY_HAT;

    public static final Item BLUE_FROGGY_HAT;
    public static final Item PURPLE_FROGGY_HAT;

    public static final Item SANTA_HAT;
    public static final Item ELF_HAT;
    public static final Item NEW_YEARS_HAT;

    public static final Item RED_VIKING_HAT;
    public static final Item BLUE_VIKING_HAT;
    public static final Item DARK_VIKING_HAT;
    public static final Item GOLD_VIKING_HAT;

    public static final Item PLAGUE_DOCTOR_MASK;
    public static final Item COWBOY_HAT;
    public static final Item COW_COWBOY_HAT;
    public static final Item SOMBRERO;
    public static final Item TV_HAT;
    public static final Item CHEFS_HAT;
    public static final Item TALL_CHEFS_HAT;

    public static final Item RUBY_CROWN;
    public static final Item DIAMOND_CROWN;
    public static final Item EMERALD_CROWN;

    public static final Item BLACK_TOP_HAT;
    public static final Item BLACK_TOP_HAT_RED;
    public static final Item BLACK_TOP_HAT_BLUE;
    public static final Item BLACK_TOP_HAT_GREEN;
    public static final Item WHITE_TOP_HAT;
    public static final Item WHITE_TOP_HAT_RED;
    public static final Item WHITE_TOP_HAT_BLUE;
    public static final Item WHITE_TOP_HAT_GREEN;

    public static final Item PROPELLER_CAP;
    public static final Item PINK_PROPELLER_CAP;
    public static final Item BLUE_PROPELLER_CAP;

    public static final Item CARDBOARD_BOX;
    public static final Item CARDBOARD_BOX_HAPPY;
    public static final Item CARDBOARD_BOX_SAD;
    public static final Item CARDBOARD_BOX_SURPRISED;
    public static final Item CARDBOARD_BOX_TONGUE;
    public static final Item CARDBOARD_BOX_TINY;
    public static final Item CARDBOARD_BOX_CREEPER;

    public static void registerHats() {

    }

    private static Item register(String id) {
        return Registry.register(Registry.ITEM, TheLittleThings.ID(id), new HatItem(new Item.Settings().maxCount(1).group(ModItemGroups.HAT_GROUP).rarity(Rarity.UNCOMMON)));
    }

    private static Item register(String id, Rarity rarity) {
        return Registry.register(Registry.ITEM, TheLittleThings.ID(id), new HatItem(new Item.Settings().maxCount(1).group(ModItemGroups.HAT_GROUP).rarity(rarity)));
    }

    private static Item register(String id, Formatting formatting) {
        return Registry.register(Registry.ITEM, TheLittleThings.ID(id), new HatLoreItem(new Item.Settings().maxCount(1).group(ModItemGroups.HAT_GROUP).rarity(Rarity.UNCOMMON), Text.translatable("item.thelittlethings." + id + ".tooltip").formatted(formatting)));
    }

    private static Item register(String id, Rarity rarity, Formatting formatting) {
        return Registry.register(Registry.ITEM, TheLittleThings.ID(id), new HatLoreItem(new Item.Settings().maxCount(1).group(ModItemGroups.HAT_GROUP).rarity(rarity), Text.translatable("item.thelittlethings." + id + ".tooltip").formatted(formatting)));
    }

    static {
        PINK_AXOLOTL_HAT = register("pink_axolotl_hat", Formatting.GRAY);
        BROWN_AXOLOTL_HAT = register("brown_axolotl_hat", Formatting.GRAY);
        GOLD_AXOLOTL_HAT = register("gold_axolotl_hat", Formatting.GRAY);
        CYAN_AXOLOTL_HAT = register("cyan_axolotl_hat", Formatting.GRAY);
        BLUE_AXOLOTL_HAT = register("blue_axolotl_hat", Formatting.GRAY);

        BLACK_AXOLOTL_HAT = register("black_axolotl_hat", Rarity.RARE, Formatting.GRAY);
        GREEN_AXOLOTL_HAT = register("green_axolotl_hat", Rarity.RARE, Formatting.GRAY);
        PURPLE_AXOLOTL_HAT = register("purple_axolotl_hat", Rarity.RARE, Formatting.GRAY);
        SCULK_AXOLOTL_HAT = register("sculk_axolotl_hat", Rarity.RARE, Formatting.GRAY);

        STRAW_HAT = register("straw_hat");
        LIBRARIAN_HAT = register("librarian_hat");
        ARMORER_HAT = register("armorer_hat");
        WINTER_HAT = register("winter_hat");
        SWAMP_HAT = register("swamp_hat");
        LEAF_CROWN = register("leaf_crown");
        FLOWER_CROWN = register("flower_crown");

        WITCH_HAT = register("witch_hat");
        RED_WITCH_HAT = register("red_witch_hat");

        SQUID_HAT = register("squid_hat");
        GLOW_SQUID_HAT = register("glow_squid_hat");

        GREEN_FROGGY_HAT = register("green_froggy_hat", Formatting.GRAY);
        ORANGE_FROGGY_HAT = register("orange_froggy_hat", Formatting.GRAY);
        BEIGE_FROGGY_HAT = register("beige_froggy_hat", Formatting.GRAY);

        BLUE_FROGGY_HAT = register("blue_froggy_hat", Rarity.RARE, Formatting.GRAY);
        PURPLE_FROGGY_HAT = register("purple_froggy_hat", Rarity.RARE, Formatting.GRAY);

        SANTA_HAT = register("santa_hat", Rarity.EPIC, Formatting.AQUA);
        ELF_HAT = register("elf_hat", Rarity.EPIC, Formatting.AQUA);
        NEW_YEARS_HAT = register("new_years_hat", Rarity.EPIC, Formatting.AQUA);

        RED_VIKING_HAT = register("red_viking_hat", Formatting.GRAY);
        BLUE_VIKING_HAT = register("blue_viking_hat", Formatting.GRAY);
        DARK_VIKING_HAT = register("dark_viking_hat", Formatting.GRAY);
        GOLD_VIKING_HAT = register("gold_viking_hat", Formatting.GRAY);

        PLAGUE_DOCTOR_MASK = register("plague_doctor_mask");
        COWBOY_HAT = register("cowboy_hat");
        COW_COWBOY_HAT = register("cow_cowboy_hat");
        SOMBRERO = register("sombrero");
        TV_HAT = register("tv_hat");
        CHEFS_HAT = register("chefs_hat");
        TALL_CHEFS_HAT = register("tall_chefs_hat");

        RUBY_CROWN = register("ruby_crown");
        DIAMOND_CROWN = register("diamond_crown");
        EMERALD_CROWN = register("emerald_crown");

        BLACK_TOP_HAT = register("black_top_hat");
        BLACK_TOP_HAT_RED = register("black_top_hat_red", Formatting.GRAY);
        BLACK_TOP_HAT_BLUE = register("black_top_hat_blue", Formatting.GRAY);
        BLACK_TOP_HAT_GREEN = register("black_top_hat_green", Formatting.GRAY);
        WHITE_TOP_HAT = register("white_top_hat");
        WHITE_TOP_HAT_RED = register("white_top_hat_red", Formatting.GRAY);
        WHITE_TOP_HAT_BLUE = register("white_top_hat_blue", Formatting.GRAY);
        WHITE_TOP_HAT_GREEN = register("white_top_hat_green", Formatting.GRAY);

        PROPELLER_CAP = register("propeller_cap");
        PINK_PROPELLER_CAP = register("pink_propeller_cap", Formatting.GRAY);
        BLUE_PROPELLER_CAP = register("blue_propeller_cap", Formatting.GRAY);

        CARDBOARD_BOX = register("cardboard_box");
        CARDBOARD_BOX_HAPPY = register("cardboard_box_happy", Formatting.GRAY);
        CARDBOARD_BOX_SAD = register("cardboard_box_sad", Formatting.GRAY);
        CARDBOARD_BOX_SURPRISED = register("cardboard_box_surprised", Formatting.GRAY);
        CARDBOARD_BOX_TONGUE = register("cardboard_box_tongue", Formatting.GRAY);
        CARDBOARD_BOX_TINY = register("cardboard_box_tiny", Formatting.GRAY);
        CARDBOARD_BOX_CREEPER = register("cardboard_box_creeper", Formatting.GRAY);
    }
}
