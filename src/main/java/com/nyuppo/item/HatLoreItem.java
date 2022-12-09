package com.nyuppo.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class HatLoreItem extends HatItem {
    private final MutableText lore;

    public HatLoreItem(Settings settings, MutableText lore) {
        super(settings);
        this.lore = lore;
    }

    public HatLoreItem(Settings settings, SoundEvent equipSound, MutableText lore) {
        super(settings, equipSound);
        this.lore = lore;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.lore);
    }
}
