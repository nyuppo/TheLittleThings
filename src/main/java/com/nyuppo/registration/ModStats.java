package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStats {
    public static final Identifier INTERACT_DISPLAY_CASE = TheLittleThings.ID("interact_display_case");
    public static final Identifier INTERACT_CHISELED_BOOKSHELF = TheLittleThings.ID("interact_chiseled_bookshelf");

    public static void registerStats() {
        Registry.register(Registry.CUSTOM_STAT, "interact_display_case", INTERACT_DISPLAY_CASE);
        Stats.CUSTOM.getOrCreateStat(INTERACT_DISPLAY_CASE, StatFormatter.DEFAULT);
        Registry.register(Registry.CUSTOM_STAT, "interact_chiseled_bookshelf", INTERACT_CHISELED_BOOKSHELF);
        Stats.CUSTOM.getOrCreateStat(INTERACT_CHISELED_BOOKSHELF, StatFormatter.DEFAULT);
    }
}
