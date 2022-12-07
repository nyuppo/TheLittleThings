package com.nyuppo.registration;

import net.minecraft.sound.BlockSoundGroup;

public class ModBlockSoundGroup {
    public static final BlockSoundGroup CHISELED_BOOKSHELF;
    public static final BlockSoundGroup BAMBOO_WOOD;

    static {
        CHISELED_BOOKSHELF = new BlockSoundGroup(1.0F, 1.0F, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_BREAK, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_STEP, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PLACE, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_HIT, ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_FALL);
        BAMBOO_WOOD = new BlockSoundGroup(1.0F, 1.0F, ModSoundEvents.BLOCK_BAMBOO_WOOD_BREAK, ModSoundEvents.BLOCK_BAMBOO_WOOD_STEP, ModSoundEvents.BLOCK_BAMBOO_WOOD_PLACE, ModSoundEvents.BLOCK_BAMBOO_WOOD_HIT, ModSoundEvents.BLOCK_BAMBOO_WOOD_FALL);
    }
}
