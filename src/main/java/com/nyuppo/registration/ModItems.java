package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.entity.vehicle.RaftEntity;
import com.nyuppo.item.HangingSignItem;
import com.nyuppo.item.PincerItem;
import com.nyuppo.item.RaftItem;
import com.nyuppo.item.VerticallyAttachableBlockItem;
import com.nyuppo.util.ModBoatType;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item BAMBOO_RAFT;
    public static final Item BAMBOO_CHEST_RAFT;
    public static final Item PIGLIN_HEAD;
    public static final Item WILLOW_LEAVES;
    public static final Item BAOBAB_LEAVES;

    public static final Item OAK_HEDGE;
    public static final Item BIRCH_HEDGE;
    public static final Item SPRUCE_HEDGE;
    public static final Item JUNGLE_HEDGE;
    public static final Item ACACIA_HEDGE;
    public static final Item DARK_OAK_HEDGE;
    public static final Item AZALEA_HEDGE;
    public static final Item FLOWERING_AZALEA_HEDGE;
    public static final Item MANGROVE_HEDGE;
    public static final Item SAKURA_HEDGE;
    public static final Item WILLOW_HEDGE;
    public static final Item BAOBAB_HEDGE;

    public static final Item WHITE_WILD_FLOWER;
    public static final Item ORANGE_WILD_FLOWER;
    public static final Item MAGENTA_WILD_FLOWER;
    public static final Item LIGHT_BLUE_WILD_FLOWER;
    public static final Item YELLOW_WILD_FLOWER;
    public static final Item LIME_WILD_FLOWER;
    public static final Item PINK_WILD_FLOWER;
    public static final Item CYAN_WILD_FLOWER;
    public static final Item PURPLE_WILD_FLOWER;
    public static final Item BLUE_WILD_FLOWER;
    public static final Item GREEN_WILD_FLOWER;
    public static final Item RED_WILD_FLOWER;
    public static final Item BLACK_WILD_FLOWER;
    public static final Item FIRE_WILD_FLOWER;
    public static final Item ICE_WILD_FLOWER;
    public static final Item GOLDEN_WILD_FLOWER;
    public static final Item WILD_WEEDS;

    public static void registerItems() {
        // Bamboo Wood
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_planks"), new BlockItem(ModBlocks.BAMBOO_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_mosaic"), new BlockItem(ModBlocks.BAMBOO_MOSAIC, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_block"), new BlockItem(ModBlocks.BAMBOO_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_bamboo_block"), new BlockItem(ModBlocks.STRIPPED_BAMBOO_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_slab"), new BlockItem(ModBlocks.BAMBOO_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_mosaic_slab"), new BlockItem(ModBlocks.BAMBOO_MOSAIC_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_stairs"), new BlockItem(ModBlocks.BAMBOO_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_mosaic_stairs"), new BlockItem(ModBlocks.BAMBOO_MOSAIC_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_fence"), new BlockItem(ModBlocks.BAMBOO_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_sign"), new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.BAMBOO_SIGN, ModBlocks.BAMBOO_WALL_SIGN));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_button"), new BlockItem(ModBlocks.BAMBOO_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_pressure_plate"), new BlockItem(ModBlocks.BAMBOO_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_door"), new TallBlockItem(ModBlocks.BAMBOO_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_trapdoor"), new BlockItem(ModBlocks.BAMBOO_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_fence_gate"), new BlockItem(ModBlocks.BAMBOO_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        // Bamboo rafts registered in static init

        // Sakura Wood
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_log"), new BlockItem(ModBlocks.SAKURA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_sakura_log"), new BlockItem(ModBlocks.STRIPPED_SAKURA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_wood"), new BlockItem(ModBlocks.SAKURA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_sakura_wood"), new BlockItem(ModBlocks.STRIPPED_SAKURA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Item SAKURA_LEAVES = Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_leaves"), new BlockItem(ModBlocks.SAKURA_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(SAKURA_LEAVES, 0.3F);
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_planks"), new BlockItem(ModBlocks.SAKURA_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_slab"), new BlockItem(ModBlocks.SAKURA_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_stairs"), new BlockItem(ModBlocks.SAKURA_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_sign"), (Item)(new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.SAKURA_SIGN, ModBlocks.SAKURA_WALL_SIGN)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_button"), new BlockItem(ModBlocks.SAKURA_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_pressure_plate"), new BlockItem(ModBlocks.SAKURA_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_door"), (BlockItem)(new TallBlockItem(ModBlocks.SAKURA_DOOR, new Item.Settings().group(ItemGroup.REDSTONE))));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_trapdoor"), new BlockItem(ModBlocks.SAKURA_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_fence_gate"), new BlockItem(ModBlocks.SAKURA_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_fence"), new BlockItem(ModBlocks.SAKURA_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Item SAKURA_SAPLING = Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_sapling"), new BlockItem(ModBlocks.SAKURA_SAPLING, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(SAKURA_SAPLING, 0.3F);

        // Azalea Wood
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_log"), new BlockItem(ModBlocks.AZALEA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_azalea_log"), new BlockItem(ModBlocks.STRIPPED_AZALEA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_wood"), new BlockItem(ModBlocks.AZALEA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_azalea_wood"), new BlockItem(ModBlocks.STRIPPED_AZALEA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_planks"), new BlockItem(ModBlocks.AZALEA_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_slab"), new BlockItem(ModBlocks.AZALEA_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_stairs"), new BlockItem(ModBlocks.AZALEA_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_sign"), (Item)(new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.AZALEA_SIGN, ModBlocks.AZALEA_WALL_SIGN)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_button"), new BlockItem(ModBlocks.AZALEA_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_pressure_plate"), new BlockItem(ModBlocks.AZALEA_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_door"), (BlockItem)(new TallBlockItem(ModBlocks.AZALEA_DOOR, new Item.Settings().group(ItemGroup.REDSTONE))));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_trapdoor"), new BlockItem(ModBlocks.AZALEA_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_fence_gate"), new BlockItem(ModBlocks.AZALEA_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_fence"), new BlockItem(ModBlocks.AZALEA_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS)));

        // Willow Wood
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_log"), new BlockItem(ModBlocks.WILLOW_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_willow_log"), new BlockItem(ModBlocks.STRIPPED_WILLOW_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_wood"), new BlockItem(ModBlocks.WILLOW_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_willow_wood"), new BlockItem(ModBlocks.STRIPPED_WILLOW_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //WILLOW_LEAVES = Registry.register(Registry.ITEM, TheLittleThings.ID("willow_leaves"), new BlockItem(ModBlocks.WILLOW_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(WILLOW_LEAVES, 0.3F);
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_planks"), new BlockItem(ModBlocks.WILLOW_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_slab"), new BlockItem(ModBlocks.WILLOW_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_stairs"), new BlockItem(ModBlocks.WILLOW_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_sign"), (Item)(new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.WILLOW_SIGN, ModBlocks.WILLOW_WALL_SIGN)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_button"), new BlockItem(ModBlocks.WILLOW_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_pressure_plate"), new BlockItem(ModBlocks.WILLOW_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_door"), (BlockItem)(new TallBlockItem(ModBlocks.WILLOW_DOOR, new Item.Settings().group(ItemGroup.REDSTONE))));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_trapdoor"), new BlockItem(ModBlocks.WILLOW_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_fence_gate"), new BlockItem(ModBlocks.WILLOW_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_fence"), new BlockItem(ModBlocks.WILLOW_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Item WILLOW_SAPLING = Registry.register(Registry.ITEM, TheLittleThings.ID("willow_sapling"), new BlockItem(ModBlocks.WILLOW_SAPLING, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(WILLOW_SAPLING, 0.3F);

        // Baobab Wood
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_log"), new BlockItem(ModBlocks.BAOBAB_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_baobab_log"), new BlockItem(ModBlocks.STRIPPED_BAOBAB_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_wood"), new BlockItem(ModBlocks.BAOBAB_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("stripped_baobab_wood"), new BlockItem(ModBlocks.STRIPPED_BAOBAB_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //BAOBAB_LEAVES = Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_leaves"), new BlockItem(ModBlocks.BAOBAB_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_planks"), new BlockItem(ModBlocks.BAOBAB_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_slab"), new BlockItem(ModBlocks.BAOBAB_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_stairs"), new BlockItem(ModBlocks.BAOBAB_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_sign"), (Item)(new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.BAOBAB_SIGN, ModBlocks.BAOBAB_WALL_SIGN)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_button"), new BlockItem(ModBlocks.BAOBAB_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_pressure_plate"), new BlockItem(ModBlocks.BAOBAB_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_door"), (BlockItem)(new TallBlockItem(ModBlocks.BAOBAB_DOOR, new Item.Settings().group(ItemGroup.REDSTONE))));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_trapdoor"), new BlockItem(ModBlocks.BAOBAB_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_fence_gate"), new BlockItem(ModBlocks.BAOBAB_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_fence"), new BlockItem(ModBlocks.BAOBAB_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Item BAOBAB_SAPLING = Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_sapling"), new BlockItem(ModBlocks.BAOBAB_SAPLING, new Item.Settings().group(ItemGroup.DECORATIONS)));
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_SAPLING, 0.3F);

        // Arid Dunes
        Registry.register(Registry.ITEM, TheLittleThings.ID("arid_sand"), new BlockItem(ModBlocks.ARID_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dunestone"), new BlockItem(ModBlocks.DUNESTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("polished_dunestone"), new BlockItem(ModBlocks.POLISHED_DUNESTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("polished_dunestone_slab"), new BlockItem(ModBlocks.POLISHED_DUNESTONE_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("polished_dunestone_stairs"), new BlockItem(ModBlocks.POLISHED_DUNESTONE_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("polished_dunestone_wall"), new BlockItem(ModBlocks.POLISHED_DUNESTONE_WALL, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("smooth_dunestone"), new BlockItem(ModBlocks.SMOOTH_DUNESTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("smooth_dunestone_slab"), new BlockItem(ModBlocks.SMOOTH_DUNESTONE_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("smooth_dunestone_stairs"), new BlockItem(ModBlocks.SMOOTH_DUNESTONE_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("smooth_dunestone_wall"), new BlockItem(ModBlocks.SMOOTH_DUNESTONE_WALL, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dunestone_bricks"), new BlockItem(ModBlocks.DUNESTONE_BRICKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dunestone_brick_slab"), new BlockItem(ModBlocks.DUNESTONE_BRICK_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dunestone_brick_stairs"), new BlockItem(ModBlocks.DUNESTONE_BRICK_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dunestone_brick_wall"), new BlockItem(ModBlocks.DUNESTONE_BRICK_WALL, new Item.Settings().group(ItemGroup.DECORATIONS)));

        // Trimmed Planks
        Registry.register(Registry.ITEM, TheLittleThings.ID("oak_trimmed_planks"), new BlockItem(ModBlocks.OAK_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("spruce_trimmed_planks"), new BlockItem(ModBlocks.SPRUCE_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("birch_trimmed_planks"), new BlockItem(ModBlocks.BIRCH_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("jungle_trimmed_planks"), new BlockItem(ModBlocks.JUNGLE_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("acacia_trimmed_planks"), new BlockItem(ModBlocks.ACACIA_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dark_oak_trimmed_planks"), new BlockItem(ModBlocks.DARK_OAK_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("mangrove_trimmed_planks"), new BlockItem(ModBlocks.MANGROVE_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("crimson_trimmed_planks"), new BlockItem(ModBlocks.CRIMSON_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("warped_trimmed_planks"), new BlockItem(ModBlocks.WARPED_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_trimmed_planks"), new BlockItem(ModBlocks.BAMBOO_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_trimmed_planks"), new BlockItem(ModBlocks.SAKURA_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_trimmed_planks"), new BlockItem(ModBlocks.AZALEA_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_trimmed_planks"), new BlockItem(ModBlocks.WILLOW_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_trimmed_planks"), new BlockItem(ModBlocks.BAOBAB_TRIMMED_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, TheLittleThings.ID("chiseled_bookshelf"), new BlockItem(ModBlocks.CHISELED_BOOKSHELF, new Item.Settings().group(ItemGroup.DECORATIONS)));

        // Paper Blocks
        register("paper_wall", ModBlocks.PAPER_WALL, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("full_paper_wall", ModBlocks.FULL_PAPER_WALL, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("paper_door", ModBlocks.PAPER_DOOR, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("paper_trapdoor", ModBlocks.PAPER_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
        register("paper_sign", new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), ModBlocks.PAPER_SIGN, ModBlocks.PAPER_WALL_SIGN));
        register("paper_lantern", ModBlocks.PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("orange_paper_lantern", ModBlocks.ORANGE_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("magenta_paper_lantern", ModBlocks.MAGENTA_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("light_blue_paper_lantern", ModBlocks.LIGHT_BLUE_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("yellow_paper_lantern", ModBlocks.YELLOW_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("lime_paper_lantern", ModBlocks.LIME_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("pink_paper_lantern", ModBlocks.PINK_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("gray_paper_lantern", ModBlocks.GRAY_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("light_gray_paper_lantern", ModBlocks.LIGHT_GRAY_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("cyan_paper_lantern", ModBlocks.CYAN_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("purple_paper_lantern", ModBlocks.PURPLE_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("blue_paper_lantern", ModBlocks.BLUE_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("brown_paper_lantern", ModBlocks.BROWN_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("green_paper_lantern", ModBlocks.GREEN_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("red_paper_lantern", ModBlocks.RED_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("black_paper_lantern", ModBlocks.BLACK_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
        register("soul_paper_lantern", ModBlocks.SOUL_PAPER_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));

        Registry.register(Registry.ITEM, TheLittleThings.ID("oak_hanging_sign"), new HangingSignItem(ModBlocks.OAK_HANGING_SIGN, ModBlocks.OAK_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("spruce_hanging_sign"), new HangingSignItem(ModBlocks.SPRUCE_HANGING_SIGN, ModBlocks.SPRUCE_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("birch_hanging_sign"), new HangingSignItem(ModBlocks.BIRCH_HANGING_SIGN, ModBlocks.BIRCH_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("jungle_hanging_sign"), new HangingSignItem(ModBlocks.JUNGLE_HANGING_SIGN, ModBlocks.JUNGLE_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("acacia_hanging_sign"), new HangingSignItem(ModBlocks.ACACIA_HANGING_SIGN, ModBlocks.ACACIA_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("dark_oak_hanging_sign"), new HangingSignItem(ModBlocks.DARK_OAK_HANGING_SIGN, ModBlocks.DARK_OAK_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("mangrove_hanging_sign"), new HangingSignItem(ModBlocks.MANGROVE_HANGING_SIGN, ModBlocks.MANGROVE_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("crimson_hanging_sign"), new HangingSignItem(ModBlocks.CRIMSON_HANGING_SIGN, ModBlocks.CRIMSON_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("warped_hanging_sign"), new HangingSignItem(ModBlocks.WARPED_HANGING_SIGN, ModBlocks.WARPED_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_hanging_sign"), new HangingSignItem(ModBlocks.BAMBOO_HANGING_SIGN, ModBlocks.BAMBOO_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_hanging_sign"), new HangingSignItem(ModBlocks.SAKURA_HANGING_SIGN, ModBlocks.SAKURA_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_hanging_sign"), new HangingSignItem(ModBlocks.AZALEA_HANGING_SIGN, ModBlocks.AZALEA_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("willow_hanging_sign"), new HangingSignItem(ModBlocks.WILLOW_HANGING_SIGN, ModBlocks.WILLOW_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_hanging_sign"), new HangingSignItem(ModBlocks.BAOBAB_HANGING_SIGN, ModBlocks.BAOBAB_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));
        Registry.register(Registry.ITEM, TheLittleThings.ID("paper_hanging_sign"), new HangingSignItem(ModBlocks.PAPER_HANGING_SIGN, ModBlocks.PAPER_WALL_HANGING_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16)));

        Registry.register(Registry.ITEM, TheLittleThings.ID("camel_spawn_egg"), new SpawnEggItem(ModEntityTypes.getCamel(),16565097, 13341495, new Item.Settings().group(ItemGroup.MISC)));
        register("penguin_spawn_egg", new SpawnEggItem(ModEntityTypes.PENGUIN, 1776418, 15198183, new Item.Settings().group(ItemGroup.MISC)));
        register("crab_spawn_egg", new SpawnEggItem(ModEntityTypes.CRAB, 13846818, 15722172, new Item.Settings().group(ItemGroup.MISC)));

        register("crab_leg", new Item(new Item.Settings().group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 1), 0.25F).meat().build())));
        register("cooked_crab_leg", new Item(new Item.Settings().group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.5F).meat().build())));
        register("crab_claw", new Item(new Item.Settings().group(ItemGroup.MISC)));
        register("pincer", new PincerItem(ToolMaterials.IRON, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT).maxCount(1)));

        BoatItems.registerBoats();
    }

    private static Item register(String id, Item entry) {
        return Registry.register(Registry.ITEM, TheLittleThings.ID(id), entry);
    }

    private static Item register(String id, Block entry, Item.Settings settings) {
        return register(id, new BlockItem(entry, settings));
    }

    private static Item registerWildFlower(String id, Block entry) {
        return register(id, new BlockItem(entry, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    static {
        BAMBOO_RAFT = (Item)Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_raft"), new RaftItem(false, RaftEntity.Type.BAMBOO, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION)));
        BAMBOO_CHEST_RAFT = (Item)Registry.register(Registry.ITEM, TheLittleThings.ID("bamboo_chest_raft"), new RaftItem(true, RaftEntity.Type.BAMBOO, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION)));
        PIGLIN_HEAD = Registry.register(Registry.ITEM, TheLittleThings.ID("piglin_head"), new VerticallyAttachableBlockItem(ModBlocks.PIGLIN_HEAD, ModBlocks.PIGLIN_HEAD_WALL, new Item.Settings().rarity(Rarity.UNCOMMON).group(ItemGroup.DECORATIONS), Direction.DOWN));
        WILLOW_LEAVES = Registry.register(Registry.ITEM, TheLittleThings.ID("willow_leaves"), new BlockItem(ModBlocks.WILLOW_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));
        BAOBAB_LEAVES = Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_leaves"), new BlockItem(ModBlocks.BAOBAB_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));

        OAK_HEDGE = register("oak_hedge", ModBlocks.OAK_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        BIRCH_HEDGE = register("birch_hedge", ModBlocks.BIRCH_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        SPRUCE_HEDGE = register("spruce_hedge", ModBlocks.SPRUCE_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        JUNGLE_HEDGE = register("jungle_hedge", ModBlocks.JUNGLE_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        ACACIA_HEDGE = register("acacia_hedge", ModBlocks.ACACIA_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        DARK_OAK_HEDGE = register("dark_oak_hedge", ModBlocks.DARK_OAK_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        AZALEA_HEDGE = register("azalea_hedge", ModBlocks.AZALEA_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        FLOWERING_AZALEA_HEDGE = register("flowering_azalea_hedge", ModBlocks.FLOWERING_AZALEA_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        MANGROVE_HEDGE = register("mangrove_hedge", ModBlocks.MANGROVE_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        SAKURA_HEDGE = register("sakura_hedge", ModBlocks.SAKURA_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        WILLOW_HEDGE = register("willow_hedge", ModBlocks.WILLOW_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        BAOBAB_HEDGE = register("baobab_hedge", ModBlocks.BAOBAB_HEDGE, new Item.Settings().group(ItemGroup.DECORATIONS));
        CompostingChanceRegistry.INSTANCE.add(OAK_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BIRCH_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(SPRUCE_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(JUNGLE_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ACACIA_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(DARK_OAK_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(AZALEA_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_AZALEA_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(MANGROVE_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(SAKURA_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(WILLOW_HEDGE, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_HEDGE, 0.3F);

        WHITE_WILD_FLOWER = registerWildFlower("white_wild_flower", ModBlocks.WHITE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(WHITE_WILD_FLOWER, 0.65F);
        ORANGE_WILD_FLOWER = registerWildFlower("orange_wild_flower", ModBlocks.ORANGE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(ORANGE_WILD_FLOWER, 0.65F);
        MAGENTA_WILD_FLOWER = registerWildFlower("magenta_wild_flower", ModBlocks.MAGENTA_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(MAGENTA_WILD_FLOWER, 0.65F);
        LIGHT_BLUE_WILD_FLOWER = registerWildFlower("light_blue_wild_flower", ModBlocks.LIGHT_BLUE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(LIGHT_BLUE_WILD_FLOWER, 0.65F);
        YELLOW_WILD_FLOWER = registerWildFlower("yellow_wild_flower", ModBlocks.YELLOW_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(YELLOW_WILD_FLOWER, 0.65F);
        LIME_WILD_FLOWER = registerWildFlower("lime_wild_flower", ModBlocks.LIME_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(LIME_WILD_FLOWER, 0.65F);
        PINK_WILD_FLOWER = registerWildFlower("pink_wild_flower", ModBlocks.PINK_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(PINK_WILD_FLOWER, 0.65F);
        CYAN_WILD_FLOWER = registerWildFlower("cyan_wild_flower", ModBlocks.CYAN_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(CYAN_WILD_FLOWER, 0.65F);
        PURPLE_WILD_FLOWER = registerWildFlower("purple_wild_flower", ModBlocks.PURPLE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_WILD_FLOWER, 0.65F);
        BLUE_WILD_FLOWER = registerWildFlower("blue_wild_flower", ModBlocks.BLUE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(BLUE_WILD_FLOWER, 0.65F);
        GREEN_WILD_FLOWER = registerWildFlower("green_wild_flower", ModBlocks.GREEN_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(GREEN_WILD_FLOWER, 0.65F);
        RED_WILD_FLOWER = registerWildFlower("red_wild_flower", ModBlocks.RED_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(RED_WILD_FLOWER, 0.65F);
        BLACK_WILD_FLOWER = registerWildFlower("black_wild_flower", ModBlocks.BLACK_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(BLACK_WILD_FLOWER, 0.65F);
        FIRE_WILD_FLOWER = registerWildFlower("fire_wild_flower", ModBlocks.FIRE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(FIRE_WILD_FLOWER, 0.65F);
        ICE_WILD_FLOWER = registerWildFlower("ice_wild_flower", ModBlocks.ICE_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(ICE_WILD_FLOWER, 0.65F);
        GOLDEN_WILD_FLOWER = registerWildFlower("golden_wild_flower", ModBlocks.GOLDEN_WILD_FLOWER);
        CompostingChanceRegistry.INSTANCE.add(GOLDEN_WILD_FLOWER, 0.65F);
        WILD_WEEDS = registerWildFlower("wild_weeds", ModBlocks.WILD_WEEDS);
        CompostingChanceRegistry.INSTANCE.add(WILD_WEEDS, 0.65F);
    }
}
