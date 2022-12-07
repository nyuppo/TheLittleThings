package com.nyuppo.util;

import com.nyuppo.block.enums.WildFlowerColour;
import com.nyuppo.registration.ModBlocks;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class WildFlowerUtil {
    public static final Map<WildFlowerColour, Block> COLOUR_TO_BLOCK_MAP;

    static {
        COLOUR_TO_BLOCK_MAP = new HashMap<>();
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.WHITE, ModBlocks.WHITE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.ORANGE, ModBlocks.ORANGE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.MAGENTA, ModBlocks.MAGENTA_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.LIGHT_BLUE, ModBlocks.LIGHT_BLUE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.YELLOW, ModBlocks.YELLOW_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.LIME, ModBlocks.LIME_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.PINK, ModBlocks.PINK_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.CYAN, ModBlocks.CYAN_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.PURPLE, ModBlocks.PURPLE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.BLUE, ModBlocks.BLUE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.GREEN, ModBlocks.GREEN_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.RED, ModBlocks.RED_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.BLACK, ModBlocks.BLACK_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.FIRE, ModBlocks.FIRE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.ICE, ModBlocks.ICE_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.GOLDEN, ModBlocks.GOLDEN_WILD_FLOWER);
        COLOUR_TO_BLOCK_MAP.put(WildFlowerColour.WEED, ModBlocks.WILD_WEEDS);
    }
}
