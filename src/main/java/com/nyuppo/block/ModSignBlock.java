package com.nyuppo.block;

import com.nyuppo.TheLittleThings;
import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class ModSignBlock extends SignBlock {
    public ModSignBlock(Settings settings, SignType type) {
        super(settings, type);
    }

    @Override
    public final Identifier getLootTableId() {
        Identifier correctedLootTableId = TheLittleThings.ID("blocks/" + this.getSignType().getName() + "_sign");
        if (this.lootTableId != correctedLootTableId) {
            this.lootTableId = correctedLootTableId;
        }

        return this.lootTableId;
    }
}
