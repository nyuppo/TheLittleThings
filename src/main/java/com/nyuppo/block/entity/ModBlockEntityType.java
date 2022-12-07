package com.nyuppo.block.entity;

import com.nyuppo.TheLittleThings;
import com.nyuppo.registration.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntityType<T extends BlockEntity> {
    public static final BlockEntityType<ChiseledBookshelfBlockEntity> CHISELED_BOOKSHELF;
    public static final BlockEntityType<HangingSignBlockEntity> HANGING_SIGN;
    public static final BlockEntityType<ModSkullBlockEntity> SKULL;

    public static void registerBlockEntityTypes() {

    }

    static {
        CHISELED_BOOKSHELF = Registry.register(Registry.BLOCK_ENTITY_TYPE, TheLittleThings.ID("chiseled_bookshelf"), FabricBlockEntityTypeBuilder.create(ChiseledBookshelfBlockEntity::new, ModBlocks.CHISELED_BOOKSHELF).build());
        HANGING_SIGN = Registry.register(Registry.BLOCK_ENTITY_TYPE, TheLittleThings.ID("hanging_sign"), FabricBlockEntityTypeBuilder.create(HangingSignBlockEntity::new,
                ModBlocks.OAK_HANGING_SIGN, ModBlocks.OAK_WALL_HANGING_SIGN,
                ModBlocks.SPRUCE_HANGING_SIGN, ModBlocks.SPRUCE_WALL_HANGING_SIGN,
                ModBlocks.BIRCH_HANGING_SIGN, ModBlocks.BIRCH_WALL_HANGING_SIGN,
                ModBlocks.JUNGLE_HANGING_SIGN, ModBlocks.JUNGLE_WALL_HANGING_SIGN,
                ModBlocks.ACACIA_HANGING_SIGN, ModBlocks.ACACIA_WALL_HANGING_SIGN,
                ModBlocks.DARK_OAK_HANGING_SIGN, ModBlocks.DARK_OAK_WALL_HANGING_SIGN,
                ModBlocks.MANGROVE_HANGING_SIGN, ModBlocks.MANGROVE_WALL_HANGING_SIGN,
                ModBlocks.CRIMSON_HANGING_SIGN, ModBlocks.CRIMSON_WALL_HANGING_SIGN,
                ModBlocks.WARPED_HANGING_SIGN, ModBlocks.WARPED_WALL_HANGING_SIGN,
                ModBlocks.BAMBOO_HANGING_SIGN, ModBlocks.BAMBOO_WALL_HANGING_SIGN,
                ModBlocks.SAKURA_HANGING_SIGN, ModBlocks.SAKURA_WALL_HANGING_SIGN,
                ModBlocks.AZALEA_HANGING_SIGN, ModBlocks.AZALEA_WALL_HANGING_SIGN,
                ModBlocks.WILLOW_HANGING_SIGN, ModBlocks.WILLOW_WALL_HANGING_SIGN,
                ModBlocks.BAOBAB_HANGING_SIGN, ModBlocks.BAOBAB_WALL_HANGING_SIGN,
                ModBlocks.PAPER_HANGING_SIGN, ModBlocks.PAPER_WALL_HANGING_SIGN).build());
        SKULL = Registry.register(Registry.BLOCK_ENTITY_TYPE, TheLittleThings.ID("skull"), FabricBlockEntityTypeBuilder.create(ModSkullBlockEntity::new, ModBlocks.PIGLIN_HEAD, ModBlocks.PIGLIN_HEAD_WALL).build());

    }
}
