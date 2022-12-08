package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import com.nyuppo.block.*;
import com.nyuppo.block.enums.WildFlowerColour;
import com.nyuppo.mixin.SignTypeAccessor;
import com.nyuppo.util.tags.ModBlockTags;
import com.nyuppo.world.feature.tree.BaobabSaplingGenerator;
import com.nyuppo.world.feature.tree.SakuraSaplingGenerator;
import com.nyuppo.world.feature.tree.WillowSaplingGenerator;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

import static com.nyuppo.TheLittleThings.MOD_ID;

public class ModBlocks {
    // Bamboo Wood
    public static final Block BAMBOO_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> { return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.YELLOW : MapColor.DARK_GREEN; }).strength(2.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block STRIPPED_BAMBOO_BLOCK = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> { return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.YELLOW : MapColor.YELLOW; }).strength(2.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.YELLOW).strength(2.0F, 3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_MOSAIC = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.YELLOW).strength(2.0F, 3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final SignType BAMBOO_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("bamboo"));
    public static final Block BAMBOO_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), BAMBOO_SIGN_TYPE);
    public static final Block BAMBOO_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), BAMBOO_SIGN_TYPE);
    public static final Block BAMBOO_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_TRAPDOOR = new ModTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.YELLOW).strength(3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD).nonOpaque().allowsSpawning(ModBlocks::never), ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN);
    public static final Block BAMBOO_BUTTON = new ModButtonBlock(true, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5f).sounds(ModBlockSoundGroup.BAMBOO_WOOD), ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
    public static final Block BAMBOO_STAIRS = new StairsBlock(BAMBOO_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(BAMBOO_PLANKS));
    public static final Block BAMBOO_MOSAIC_STAIRS = new StairsBlock(BAMBOO_MOSAIC.getDefaultState(), AbstractBlock.Settings.copy(BAMBOO_MOSAIC));
    public static final Block BAMBOO_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.YELLOW).strength(2.0F, 3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_MOSAIC_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.YELLOW).strength(2.0F, 3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_FENCE_GATE = new ModFenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN);
    public static final Block BAMBOO_FENCE = new FenceBlock(AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).strength(3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD));
    public static final Block BAMBOO_DOOR = new ModDoorBlock(AbstractBlock.Settings.of(Material.WOOD, BAMBOO_PLANKS.getDefaultMapColor()).strength(3.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD).nonOpaque(), ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN);

    // Sakura Wood
    public static final Block SAKURA_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block SAKURA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SAKURA_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SAKURA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block SAKURA_LEAVES = new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((state, world, pos, entitytype) -> false).suffocates((state, world, pos) -> false).blockVision((state, world, pos) -> false));
    public static final Block SAKURA_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block SAKURA_SLAB = new SlabBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS));
    //public static final Block SAKURA_VERTICAL_SLAB = new VerticalSlabBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS));
    public static final Block SAKURA_STAIRS = new StairsBlock(SAKURA_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(SAKURA_PLANKS));
    public static final Block SAKURA_FENCE = new FenceBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS));
    public static final Block SAKURA_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS));
    public static final Block SAKURA_DOOR = new DoorBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS).nonOpaque());
    public static final Block SAKURA_TRAPDOOR = new TrapdoorBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS).nonOpaque().allowsSpawning(ModBlocks::never));
    public static final Block SAKURA_BUTTON = new WoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block SAKURA_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final SignType SAKURA_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("sakura"));
    public static final Block SAKURA_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SAKURA_SIGN_TYPE);
    public static final Block SAKURA_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.LIGHT_GRAY).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(SAKURA_SIGN), SAKURA_SIGN_TYPE);
    public static final Block SAKURA_SAPLING = new SaplingBlock(new SakuraSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_SAKURA_SAPLING = new FlowerPotBlock(SAKURA_SAPLING, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    // Azalea Wood
    public static final Block AZALEA_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block AZALEA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_AZALEA_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_AZALEA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block AZALEA_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block AZALEA_SLAB = new SlabBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS));
    //public static final Block AZALEA_VERTICAL_SLAB = new VerticalSlabBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS));
    public static final Block AZALEA_STAIRS = new StairsBlock(AZALEA_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(AZALEA_PLANKS));
    public static final Block AZALEA_FENCE = new FenceBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS));
    public static final Block AZALEA_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS));
    public static final Block AZALEA_DOOR = new DoorBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS).nonOpaque());
    public static final Block AZALEA_TRAPDOOR = new TrapdoorBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS).nonOpaque().allowsSpawning(ModBlocks::never));
    public static final Block AZALEA_BUTTON = new WoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block AZALEA_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final SignType AZALEA_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("azalea"));
    public static final Block AZALEA_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), AZALEA_SIGN_TYPE);
    public static final Block AZALEA_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DULL_PINK).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(AZALEA_SIGN), AZALEA_SIGN_TYPE);

    // Willow Wood
    public static final Block WILLOW_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block WILLOW_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_WILLOW_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_WILLOW_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block WILLOW_LEAVES = new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((state, world, pos, entitytype) -> false).suffocates((state, world, pos) -> false).blockVision((state, world, pos) -> false));
    public static final Block WILLOW_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block WILLOW_SLAB = new SlabBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS));
    //public static final Block WILLOW_VERTICAL_SLAB = new VerticalSlabBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS));
    public static final Block WILLOW_STAIRS = new StairsBlock(WILLOW_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(WILLOW_PLANKS));
    public static final Block WILLOW_FENCE = new FenceBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS));
    public static final Block WILLOW_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS));
    public static final Block WILLOW_DOOR = new DoorBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS).nonOpaque());
    public static final Block WILLOW_TRAPDOOR = new TrapdoorBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS).nonOpaque().allowsSpawning(ModBlocks::never));
    public static final Block WILLOW_BUTTON = new WoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block WILLOW_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final SignType WILLOW_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("willow"));
    public static final Block WILLOW_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), WILLOW_SIGN_TYPE);
    public static final Block WILLOW_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_GREEN).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(WILLOW_SIGN), WILLOW_SIGN_TYPE);
    public static final Block WILLOW_SAPLING = new SaplingBlock(new WillowSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_WILLOW_SAPLING = new FlowerPotBlock(WILLOW_SAPLING, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    // Baobab Wood
    public static final Block BAOBAB_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BAOBAB_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BAOBAB_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_LEAVES = new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((state, world, pos, entitytype) -> false).suffocates((state, world, pos) -> false).blockVision((state, world, pos) -> false));
    public static final Block BAOBAB_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_SLAB = new SlabBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS));
    //public static final Block BAOBAB_VERTICAL_SLAB = new VerticalSlabBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS));
    public static final Block BAOBAB_STAIRS = new StairsBlock(BAOBAB_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(BAOBAB_PLANKS));
    public static final Block BAOBAB_FENCE = new FenceBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS));
    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS));
    public static final Block BAOBAB_DOOR = new DoorBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS).nonOpaque());
    public static final Block BAOBAB_TRAPDOOR = new TrapdoorBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS).nonOpaque().allowsSpawning(ModBlocks::never));
    public static final Block BAOBAB_BUTTON = new WoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final SignType BAOBAB_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("baobab"));
    public static final Block BAOBAB_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(BAOBAB_SIGN), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_SAPLING = new SandSaplingBlock(new BaobabSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_BAOBAB_SAPLING = new FlowerPotBlock(BAOBAB_SAPLING, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    // Arid Dunes
    public static final Block ARID_SAND = new SandBlock(11503706, AbstractBlock.Settings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).strength(0.5f).sounds(BlockSoundGroup.SAND));
    public static final Block DUNESTONE = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(0.8f).sounds(BlockSoundGroup.MUD_BRICKS));
    public static final Block POLISHED_DUNESTONE = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(0.8f).sounds(BlockSoundGroup.MUD_BRICKS));
    public static final Block POLISHED_DUNESTONE_SLAB = new SlabBlock(AbstractBlock.Settings.copy(POLISHED_DUNESTONE));
    public static final Block POLISHED_DUNESTONE_STAIRS = new StairsBlock(POLISHED_DUNESTONE.getDefaultState(), AbstractBlock.Settings.copy(POLISHED_DUNESTONE));
    public static final Block POLISHED_DUNESTONE_WALL = new WallBlock(AbstractBlock.Settings.copy(POLISHED_DUNESTONE));
    public static final Block SMOOTH_DUNESTONE = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(0.8f).sounds(BlockSoundGroup.MUD_BRICKS));
    public static final Block SMOOTH_DUNESTONE_SLAB = new SlabBlock(AbstractBlock.Settings.copy(SMOOTH_DUNESTONE));
    public static final Block SMOOTH_DUNESTONE_STAIRS = new StairsBlock(SMOOTH_DUNESTONE.getDefaultState(), AbstractBlock.Settings.copy(SMOOTH_DUNESTONE));
    public static final Block SMOOTH_DUNESTONE_WALL = new WallBlock(AbstractBlock.Settings.copy(SMOOTH_DUNESTONE));
    public static final Block DUNESTONE_BRICKS = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(0.8f).sounds(BlockSoundGroup.MUD_BRICKS));
    public static final Block DUNESTONE_BRICK_SLAB = new SlabBlock(AbstractBlock.Settings.copy(DUNESTONE_BRICKS));
    public static final Block DUNESTONE_BRICK_STAIRS = new StairsBlock(DUNESTONE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(DUNESTONE_BRICKS));
    public static final Block DUNESTONE_BRICK_WALL = new WallBlock(AbstractBlock.Settings.copy(DUNESTONE_BRICKS));

    // Trimmed Planks
    public static final Block OAK_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block SPRUCE_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS));
    public static final Block BIRCH_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS));
    public static final Block JUNGLE_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS));
    public static final Block ACACIA_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS));
    public static final Block DARK_OAK_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS));
    public static final Block MANGROVE_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS));
    public static final Block CRIMSON_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS));
    public static final Block WARPED_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS));
    public static final Block BAMBOO_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(BAMBOO_PLANKS));
    public static final Block SAKURA_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(SAKURA_PLANKS));
    public static final Block AZALEA_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(AZALEA_PLANKS));
    public static final Block WILLOW_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(WILLOW_PLANKS));
    public static final Block BAOBAB_TRIMMED_PLANKS = new PillarBlock(AbstractBlock.Settings.copy(BAOBAB_PLANKS));

    // Hedges
    public static final Block OAK_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block SPRUCE_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block BIRCH_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block JUNGLE_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block ACACIA_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block DARK_OAK_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block AZALEA_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque());
    public static final Block FLOWERING_AZALEA_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque());
    public static final Block MANGROVE_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block SAKURA_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block WILLOW_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block BAOBAB_HEDGE = new HedgeBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque());

    public static final Block CHISELED_BOOKSHELF = new ChiseledBookshelfBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(ModBlockSoundGroup.CHISELED_BOOKSHELF));

    // Oriental
    public static final Block PAPER_WALL = new PaneBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).nonOpaque());
    public static final Block FULL_PAPER_WALL = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.WHITE).strength(0.5F).sounds(BlockSoundGroup.BAMBOO));
    public static final Block PAPER_DOOR = new ModDoorBlock(AbstractBlock.Settings.copy(FULL_PAPER_WALL), ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN);
    public static final Block PAPER_TRAPDOOR = new ModTrapdoorBlock(AbstractBlock.Settings.copy(FULL_PAPER_WALL).nonOpaque().allowsSpawning(ModBlocks::never), ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN);
    public static final SignType PAPER_SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("paper"));
    public static final Block PAPER_SIGN = new ModSignBlock(AbstractBlock.Settings.of(Material.WOOD, FULL_PAPER_WALL.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), PAPER_SIGN_TYPE);
    public static final Block PAPER_WALL_SIGN = new ModWallSignBlock(AbstractBlock.Settings.of(Material.WOOD, FULL_PAPER_WALL.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), PAPER_SIGN_TYPE);
    public static final Block PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block ORANGE_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block MAGENTA_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block LIGHT_BLUE_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block YELLOW_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block LIME_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block PINK_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block GRAY_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block LIGHT_GRAY_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block CYAN_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block PURPLE_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block BLUE_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block BROWN_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block GREEN_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block RED_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block BLACK_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block SOUL_PAPER_LANTERN = new PaperLanternBlock(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.BAMBOO).luminance((state) -> {
        return 10;
    }).nonOpaque());

    // Hanging Signs
    public static final Block OAK_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.OAK_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.OAK);
    public static final Block OAK_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.OAK_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.OAK); // .dropsLike(OAK_HANGING_SIGN)
    public static final Block SPRUCE_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.SPRUCE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.SPRUCE);
    public static final Block SPRUCE_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.SPRUCE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.SPRUCE);
    public static final Block BIRCH_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.BIRCH_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.BIRCH);
    public static final Block BIRCH_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.BIRCH_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.BIRCH);
    public static final Block JUNGLE_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.JUNGLE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.JUNGLE);
    public static final Block JUNGLE_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.JUNGLE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.JUNGLE);
    public static final Block ACACIA_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.ACACIA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.ACACIA);
    public static final Block ACACIA_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.ACACIA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.ACACIA);
    public static final Block DARK_OAK_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.DARK_OAK_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.DARK_OAK);
    public static final Block DARK_OAK_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.DARK_OAK_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.DARK_OAK);
    public static final Block MANGROVE_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.MANGROVE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.MANGROVE);
    public static final Block MANGROVE_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.MANGROVE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.MANGROVE);
    public static final Block CRIMSON_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.CRIMSON_STEM.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.CRIMSON);
    public static final Block CRIMSON_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.CRIMSON_STEM.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.CRIMSON);
    public static final Block WARPED_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.WARPED_STEM.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.WARPED);
    public static final Block WARPED_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, Blocks.WARPED_STEM.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SignType.WARPED);
    public static final Block BAMBOO_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_BAMBOO_BLOCK.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), BAMBOO_SIGN_TYPE);
    public static final Block BAMBOO_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_BAMBOO_BLOCK.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), BAMBOO_SIGN_TYPE);
    public static final Block SAKURA_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_SAKURA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SAKURA_SIGN_TYPE);
    public static final Block SAKURA_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_SAKURA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SAKURA_SIGN_TYPE);
    public static final Block AZALEA_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_AZALEA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), AZALEA_SIGN_TYPE);
    public static final Block AZALEA_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_AZALEA_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), AZALEA_SIGN_TYPE);
    public static final Block WILLOW_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_WILLOW_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), WILLOW_SIGN_TYPE);
    public static final Block WILLOW_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_WILLOW_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), WILLOW_SIGN_TYPE);
    public static final Block BAOBAB_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, STRIPPED_BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), BAOBAB_SIGN_TYPE);
    public static final Block PAPER_HANGING_SIGN = new HangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, FULL_PAPER_WALL.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), PAPER_SIGN_TYPE);
    public static final Block PAPER_WALL_HANGING_SIGN = new WallHangingSignBlock(AbstractBlock.Settings.of(Material.WOOD, FULL_PAPER_WALL.getDefaultMapColor()).noCollision().strength(1.0F).sounds(ModBlockSoundGroup.BAMBOO_WOOD), PAPER_SIGN_TYPE);

    // Heads
    public static final Block PIGLIN_HEAD = new ModSkullBlock(ModSkullBlock.Type.PIGLIN, AbstractBlock.Settings.of(Material.DECORATION).strength(1.0F));
    public static final Block PIGLIN_HEAD_WALL = new WallPiglinHeadBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(1.0F));

    // Wild Flowers
    public static final Block WHITE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.WHITE, StatusEffects.REGENERATION, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_WHITE_WILD_FLOWER = new FlowerPotBlock(WHITE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block ORANGE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.ORANGE, StatusEffects.NIGHT_VISION, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_ORANGE_WILD_FLOWER = new FlowerPotBlock(ORANGE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block MAGENTA_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.MAGENTA, StatusEffects.WEAKNESS, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_MAGENTA_WILD_FLOWER = new FlowerPotBlock(MAGENTA_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block LIGHT_BLUE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.LIGHT_BLUE, StatusEffects.SPEED, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_LIGHT_BLUE_WILD_FLOWER = new FlowerPotBlock(LIGHT_BLUE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block YELLOW_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.YELLOW, StatusEffects.SATURATION, 5, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_YELLOW_WILD_FLOWER = new FlowerPotBlock(YELLOW_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block LIME_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.LIME, StatusEffects.POISON, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_LIME_WILD_FLOWER = new FlowerPotBlock(LIME_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block PINK_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.PINK, StatusEffects.WEAKNESS, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_PINK_WILD_FLOWER = new FlowerPotBlock(PINK_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block CYAN_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.CYAN, StatusEffects.BLINDNESS, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_CYAN_WILD_FLOWER = new FlowerPotBlock(CYAN_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block PURPLE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.PURPLE, StatusEffects.WEAKNESS, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_PURPLE_WILD_FLOWER = new FlowerPotBlock(PURPLE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block BLUE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.BLUE, StatusEffects.JUMP_BOOST, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_BLUE_WILD_FLOWER = new FlowerPotBlock(BLUE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block GREEN_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.GREEN, StatusEffects.REGENERATION, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_GREEN_WILD_FLOWER = new FlowerPotBlock(GREEN_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block RED_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.RED, StatusEffects.NIGHT_VISION, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_RED_WILD_FLOWER = new FlowerPotBlock(RED_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block BLACK_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.BLACK, StatusEffects.WITHER, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_BLACK_WILD_FLOWER = new FlowerPotBlock(BLACK_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block FIRE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.FIRE, StatusEffects.FIRE_RESISTANCE, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_FIRE_WILD_FLOWER = new FlowerPotBlock(FIRE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block ICE_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.ICE, StatusEffects.SLOWNESS, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_ICE_WILD_FLOWER = new FlowerPotBlock(ICE_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block GOLDEN_WILD_FLOWER = new WildFlowerBlock(WildFlowerColour.GOLDEN, StatusEffects.SLOW_FALLING, 10, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_GOLDEN_WILD_FLOWER = new FlowerPotBlock(GOLDEN_WILD_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block WILD_WEEDS = new WildFlowerBlock(WildFlowerColour.WEED, StatusEffects.POISON, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_WILD_WEEDS = new FlowerPotBlock(WILD_WEEDS, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static void registerBlocks() {
        // Bamboo Wood
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_block"), BAMBOO_BLOCK);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_BLOCK, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_bamboo_block"), STRIPPED_BAMBOO_BLOCK);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_BAMBOO_BLOCK, 5, 5);
        StrippableBlockRegistry.register(BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_planks"), BAMBOO_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_mosaic"), BAMBOO_MOSAIC);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_MOSAIC, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_slab"), BAMBOO_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_mosaic_slab"), BAMBOO_MOSAIC_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_MOSAIC_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_fence_gate"), BAMBOO_FENCE_GATE);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_FENCE_GATE, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_sign"), BAMBOO_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_wall_sign"), BAMBOO_WALL_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_pressure_plate"), BAMBOO_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_trapdoor"), BAMBOO_TRAPDOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_button"), BAMBOO_BUTTON);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_stairs"), BAMBOO_STAIRS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_mosaic_stairs"), BAMBOO_MOSAIC_STAIRS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_fence"), BAMBOO_FENCE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_door"), BAMBOO_DOOR);

        // Sakura Wood
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_log"), SAKURA_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_LOG, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_sakura_log"), STRIPPED_SAKURA_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_SAKURA_LOG, 5, 5);
        StrippableBlockRegistry.register(SAKURA_LOG, STRIPPED_SAKURA_LOG);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_wood"), SAKURA_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_WOOD, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_sakura_wood"), STRIPPED_SAKURA_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_SAKURA_WOOD, 5, 5);
        StrippableBlockRegistry.register(SAKURA_WOOD, STRIPPED_SAKURA_WOOD);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_leaves"), SAKURA_LEAVES);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_LEAVES, 30, 60);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_planks"), SAKURA_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_slab"), SAKURA_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_SLAB, 5, 20);
        //Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_vertical_slab"), SAKURA_VERTICAL_SLAB);
        //Registry.register(Registry.ITEM, TheLittleThings.ID("sakura_vertical_slab"), new BlockItem(SAKURA_VERTICAL_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //FlammableBlockRegistry.getDefaultInstance().add(SAKURA_VERTICAL_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_stairs"), SAKURA_STAIRS);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_STAIRS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_sapling"), SAKURA_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("potted_sakura_sapling"), POTTED_SAKURA_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_sign"), SAKURA_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_wall_sign"), SAKURA_WALL_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_button"), SAKURA_BUTTON);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_pressure_plate"), SAKURA_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_door"), SAKURA_DOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_trapdoor"), SAKURA_TRAPDOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_fence_gate"), SAKURA_FENCE_GATE);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_FENCE_GATE, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_fence"), SAKURA_FENCE);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_FENCE, 5, 20);

        // Azalea Wood
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_log"), AZALEA_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_LOG, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_azalea_log"), STRIPPED_AZALEA_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_AZALEA_LOG, 5, 5);
        StrippableBlockRegistry.register(AZALEA_LOG, STRIPPED_AZALEA_LOG);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_wood"), AZALEA_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_WOOD, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_azalea_wood"), STRIPPED_AZALEA_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_AZALEA_WOOD, 5, 5);
        StrippableBlockRegistry.register(AZALEA_WOOD, STRIPPED_AZALEA_WOOD);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_planks"), AZALEA_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_slab"), AZALEA_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_SLAB, 5, 20);
        //Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_vertical_slab"), AZALEA_VERTICAL_SLAB);
        //Registry.register(Registry.ITEM, TheLittleThings.ID("azalea_vertical_slab"), new BlockItem(AZALEA_VERTICAL_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //FlammableBlockRegistry.getDefaultInstance().add(AZALEA_VERTICAL_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_stairs"), AZALEA_STAIRS);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_STAIRS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_sign"), AZALEA_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_wall_sign"), AZALEA_WALL_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_button"), AZALEA_BUTTON);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_pressure_plate"), AZALEA_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_door"), AZALEA_DOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_trapdoor"), AZALEA_TRAPDOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_fence_gate"), AZALEA_FENCE_GATE);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_FENCE_GATE, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_fence"), AZALEA_FENCE);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_FENCE, 5, 20);

        // Willow Wood
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_log"), WILLOW_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_LOG, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_willow_log"), STRIPPED_WILLOW_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_WILLOW_LOG, 5, 5);
        StrippableBlockRegistry.register(WILLOW_LOG, STRIPPED_WILLOW_LOG);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_wood"), WILLOW_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_WOOD, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_willow_wood"), STRIPPED_WILLOW_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_WILLOW_WOOD, 5, 5);
        StrippableBlockRegistry.register(WILLOW_WOOD, STRIPPED_WILLOW_WOOD);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_leaves"), WILLOW_LEAVES);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_LEAVES, 30, 60);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_planks"), WILLOW_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_slab"), WILLOW_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_SLAB, 5, 20);
        //Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_vertical_slab"), WILLOW_VERTICAL_SLAB);
        //Registry.register(Registry.ITEM, TheLittleThings.ID("willow_vertical_slab"), new BlockItem(WILLOW_VERTICAL_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //FlammableBlockRegistry.getDefaultInstance().add(WILLOW_VERTICAL_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_stairs"), WILLOW_STAIRS);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_STAIRS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_sapling"), WILLOW_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("potted_willow_sapling"), POTTED_WILLOW_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_sign"), WILLOW_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_wall_sign"), WILLOW_WALL_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_button"), WILLOW_BUTTON);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_pressure_plate"), WILLOW_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_door"), WILLOW_DOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_trapdoor"), WILLOW_TRAPDOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_fence_gate"), WILLOW_FENCE_GATE);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_FENCE_GATE, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_fence"), WILLOW_FENCE);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_FENCE, 5, 20);

        // Boabab Wood
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_log"), BAOBAB_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_LOG, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_baobab_log"), STRIPPED_BAOBAB_LOG);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_BAOBAB_LOG, 5, 5);
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_wood"), BAOBAB_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_WOOD, 5, 5);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("stripped_baobab_wood"), STRIPPED_BAOBAB_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_BAOBAB_WOOD, 5, 5);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_leaves"), BAOBAB_LEAVES);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_LEAVES, 30, 60);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_planks"), BAOBAB_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_slab"), BAOBAB_SLAB);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_SLAB, 5, 20);
        //Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_vertical_slab"), BAOBAB_VERTICAL_SLAB);
        //Registry.register(Registry.ITEM, TheLittleThings.ID("baobab_vertical_slab"), new BlockItem(BAOBAB_VERTICAL_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        //FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_VERTICAL_SLAB, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_stairs"), BAOBAB_STAIRS);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_STAIRS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_sapling"), BAOBAB_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("potted_baobab_sapling"), POTTED_BAOBAB_SAPLING);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_sign"), BAOBAB_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_wall_sign"), BAOBAB_WALL_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_button"), BAOBAB_BUTTON);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_pressure_plate"), BAOBAB_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_door"), BAOBAB_DOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_trapdoor"), BAOBAB_TRAPDOOR);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_fence_gate"), BAOBAB_FENCE_GATE);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_FENCE_GATE, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_fence"), BAOBAB_FENCE);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_FENCE, 5, 20);

        // Arid Dunes
        Registry.register(Registry.BLOCK, TheLittleThings.ID("arid_sand"), ARID_SAND);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dunestone"), DUNESTONE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("polished_dunestone"), POLISHED_DUNESTONE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("polished_dunestone_slab"), POLISHED_DUNESTONE_SLAB);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("polished_dunestone_stairs"), POLISHED_DUNESTONE_STAIRS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("polished_dunestone_wall"), POLISHED_DUNESTONE_WALL);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("smooth_dunestone"), SMOOTH_DUNESTONE);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("smooth_dunestone_slab"), SMOOTH_DUNESTONE_SLAB);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("smooth_dunestone_stairs"), SMOOTH_DUNESTONE_STAIRS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("smooth_dunestone_wall"), SMOOTH_DUNESTONE_WALL);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dunestone_bricks"), DUNESTONE_BRICKS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dunestone_brick_slab"), DUNESTONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dunestone_brick_stairs"), DUNESTONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dunestone_brick_wall"), DUNESTONE_BRICK_WALL);

        // Trimmed Planks
        Registry.register(Registry.BLOCK, TheLittleThings.ID("oak_trimmed_planks"), OAK_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("spruce_trimmed_planks"), SPRUCE_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("birch_trimmed_planks"), BIRCH_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("jungle_trimmed_planks"), JUNGLE_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(JUNGLE_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("acacia_trimmed_planks"), ACACIA_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(ACACIA_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dark_oak_trimmed_planks"), DARK_OAK_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(DARK_OAK_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("mangrove_trimmed_planks"), MANGROVE_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(MANGROVE_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("crimson_trimmed_planks"), CRIMSON_TRIMMED_PLANKS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("warped_trimmed_planks"), WARPED_TRIMMED_PLANKS);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_trimmed_planks"), BAMBOO_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(BAMBOO_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_trimmed_planks"), SAKURA_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(SAKURA_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_trimmed_planks"), AZALEA_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(AZALEA_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_trimmed_planks"), WILLOW_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(WILLOW_TRIMMED_PLANKS, 5, 20);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_trimmed_planks"), BAOBAB_TRIMMED_PLANKS);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_TRIMMED_PLANKS, 5, 20);

        register("oak_hedge", OAK_HEDGE);
        register("birch_hedge", BIRCH_HEDGE);
        register("spruce_hedge", SPRUCE_HEDGE);
        register("jungle_hedge", JUNGLE_HEDGE);
        register("acacia_hedge", ACACIA_HEDGE);
        register("dark_oak_hedge", DARK_OAK_HEDGE);
        register("azalea_hedge", AZALEA_HEDGE);
        register("flowering_azalea_hedge", FLOWERING_AZALEA_HEDGE);
        register("mangrove_hedge", MANGROVE_HEDGE);
        register("sakura_hedge", SAKURA_HEDGE);
        register("willow_hedge", WILLOW_HEDGE);
        register("baobab_hedge", BAOBAB_HEDGE);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlockTags.HEDGES, 30, 60);

        Registry.register(Registry.BLOCK, TheLittleThings.ID("chiseled_bookshelf"), CHISELED_BOOKSHELF);

        // Paper Blocks
        register("paper_wall", PAPER_WALL);
        register("full_paper_wall", FULL_PAPER_WALL);
        FlammableBlockRegistry.getDefaultInstance().add(FULL_PAPER_WALL, 5, 20);
        register("paper_door", PAPER_DOOR);
        register("paper_trapdoor", PAPER_TRAPDOOR);
        register("paper_sign", PAPER_SIGN);
        register("paper_wall_sign", PAPER_WALL_SIGN);
        register("paper_lantern", PAPER_LANTERN);
        register("orange_paper_lantern", ORANGE_PAPER_LANTERN);
        register("magenta_paper_lantern", MAGENTA_PAPER_LANTERN);
        register("light_blue_paper_lantern", LIGHT_BLUE_PAPER_LANTERN);
        register("yellow_paper_lantern", YELLOW_PAPER_LANTERN);
        register("lime_paper_lantern", LIME_PAPER_LANTERN);
        register("pink_paper_lantern", PINK_PAPER_LANTERN);
        register("gray_paper_lantern", GRAY_PAPER_LANTERN);
        register("light_gray_paper_lantern", LIGHT_GRAY_PAPER_LANTERN);
        register("cyan_paper_lantern", CYAN_PAPER_LANTERN);
        register("purple_paper_lantern", PURPLE_PAPER_LANTERN);
        register("blue_paper_lantern", BLUE_PAPER_LANTERN);
        register("brown_paper_lantern", BROWN_PAPER_LANTERN);
        register("green_paper_lantern", GREEN_PAPER_LANTERN);
        register("red_paper_lantern", RED_PAPER_LANTERN);
        register("black_paper_lantern", BLACK_PAPER_LANTERN);
        register("soul_paper_lantern", SOUL_PAPER_LANTERN);

        // Hanging Signs
        Registry.register(Registry.BLOCK, TheLittleThings.ID("oak_hanging_sign"), OAK_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("oak_wall_hanging_sign"), OAK_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("spruce_hanging_sign"), SPRUCE_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("spruce_wall_hanging_sign"), SPRUCE_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("birch_hanging_sign"), BIRCH_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("birch_wall_hanging_sign"), BIRCH_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("jungle_hanging_sign"), JUNGLE_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("jungle_wall_hanging_sign"), JUNGLE_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("acacia_hanging_sign"), ACACIA_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("acacia_wall_hanging_sign"), ACACIA_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dark_oak_hanging_sign"), DARK_OAK_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("dark_oak_wall_hanging_sign"), DARK_OAK_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("mangrove_hanging_sign"), MANGROVE_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("mangrove_wall_hanging_sign"), MANGROVE_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("crimson_hanging_sign"), CRIMSON_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("crimson_wall_hanging_sign"), CRIMSON_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("warped_hanging_sign"), WARPED_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("warped_wall_hanging_sign"), WARPED_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_hanging_sign"), BAMBOO_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("bamboo_wall_hanging_sign"), BAMBOO_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_hanging_sign"), SAKURA_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("sakura_wall_hanging_sign"), SAKURA_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_hanging_sign"), AZALEA_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("azalea_wall_hanging_sign"), AZALEA_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_hanging_sign"), WILLOW_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("willow_wall_hanging_sign"), WILLOW_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_hanging_sign"), BAOBAB_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("baobab_wall_hanging_sign"), BAOBAB_WALL_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("paper_hanging_sign"), PAPER_HANGING_SIGN);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("paper_wall_hanging_sign"), PAPER_WALL_HANGING_SIGN);

        Registry.register(Registry.BLOCK, TheLittleThings.ID("piglin_head"), PIGLIN_HEAD);
        Registry.register(Registry.BLOCK, TheLittleThings.ID("piglin_head_wall"), PIGLIN_HEAD_WALL);

        // Wild Flowers
        register("white_wild_flower", WHITE_WILD_FLOWER);
        register("potted_white_wild_flower", POTTED_WHITE_WILD_FLOWER);
        register("orange_wild_flower", ORANGE_WILD_FLOWER);
        register("potted_orange_wild_flower", POTTED_ORANGE_WILD_FLOWER);
        register("magenta_wild_flower", MAGENTA_WILD_FLOWER);
        register("potted_magenta_wild_flower", POTTED_MAGENTA_WILD_FLOWER);
        register("light_blue_wild_flower", LIGHT_BLUE_WILD_FLOWER);
        register("potted_light_blue_wild_flower", POTTED_LIGHT_BLUE_WILD_FLOWER);
        register("yellow_wild_flower", YELLOW_WILD_FLOWER);
        register("potted_yellow_wild_flower", POTTED_YELLOW_WILD_FLOWER);
        register("lime_wild_flower", LIME_WILD_FLOWER);
        register("potted_lime_wild_flower", POTTED_LIME_WILD_FLOWER);
        register("pink_wild_flower", PINK_WILD_FLOWER);
        register("potted_pink_wild_flower", POTTED_PINK_WILD_FLOWER);
        register("cyan_wild_flower", CYAN_WILD_FLOWER);
        register("potted_cyan_wild_flower", POTTED_CYAN_WILD_FLOWER);
        register("purple_wild_flower", PURPLE_WILD_FLOWER);
        register("potted_purple_wild_flower", POTTED_PURPLE_WILD_FLOWER);
        register("blue_wild_flower", BLUE_WILD_FLOWER);
        register("potted_blue_wild_flower", POTTED_BLUE_WILD_FLOWER);
        register("green_wild_flower", GREEN_WILD_FLOWER);
        register("potted_green_wild_flower", POTTED_GREEN_WILD_FLOWER);
        register("red_wild_flower", RED_WILD_FLOWER);
        register("potted_red_wild_flower", POTTED_RED_WILD_FLOWER);
        register("black_wild_flower", BLACK_WILD_FLOWER);
        register("potted_black_wild_flower", POTTED_BLACK_WILD_FLOWER);
        register("fire_wild_flower", FIRE_WILD_FLOWER);
        register("potted_fire_wild_flower", POTTED_FIRE_WILD_FLOWER);
        register("ice_wild_flower", ICE_WILD_FLOWER);
        register("potted_ice_wild_flower", POTTED_ICE_WILD_FLOWER);
        register("golden_wild_flower", GOLDEN_WILD_FLOWER);
        register("potted_golden_wild_flower", POTTED_GOLDEN_WILD_FLOWER);
        register("wild_weeds", WILD_WEEDS);
        register("potted_wild_weeds", POTTED_WILD_WEEDS);
    }

    private static Block register(String id, Block entry) {
        return Registry.register(Registry.BLOCK, TheLittleThings.ID(id), entry);
    }

    private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }
}
