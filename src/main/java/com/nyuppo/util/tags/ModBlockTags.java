package com.nyuppo.util.tags;

import com.nyuppo.TheLittleThings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockTags {
    public static final TagKey<Block> CEILING_HANGING_SIGNS = of("ceiling_hanging_signs");
    public static final TagKey<Block> WALL_HANGING_SIGNS = of("wall_hanging_signs");
    public static final TagKey<Block> ALL_HANGING_SIGNS = of("all_hanging_signs");
    public static final TagKey<Block> CAMELS_SPAWNABLE_ON = of("camels_spawnable_on");
    public static final TagKey<Block> CRABS_SPAWNABLE_ON = of("crabs_spawnable_on");
    public static final TagKey<Block> HEDGES = of("hedges");

    private static TagKey<Block> of(String id) { return TagKey.of(Registry.BLOCK.getKey(), new Identifier(TheLittleThings.MOD_ID, id));}
}
