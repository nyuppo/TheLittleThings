package com.nyuppo.util.tags;

import com.nyuppo.TheLittleThings;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemTags {
    public static final TagKey<Item> BOOKSHELF_BOOKS = of("bookshelf_books");
    public static final TagKey<Item> HATS = of("hats");

    private static TagKey<Item> of(String id) { return TagKey.of(Registry.ITEM.getKey(), new Identifier(TheLittleThings.MOD_ID, id));}
}
