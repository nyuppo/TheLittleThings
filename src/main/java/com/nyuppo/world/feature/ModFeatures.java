package com.nyuppo.world.feature;

import com.google.common.collect.ImmutableList;
import com.nyuppo.TheLittleThings;
import com.nyuppo.registration.ModBlocks;
import com.nyuppo.util.WeightedRandomBag;
import com.nyuppo.world.feature.natural.BlobReplacerFeature;
import com.nyuppo.world.feature.natural.FallenTreeFeature;
import com.nyuppo.world.feature.natural.WildFlowerFeature;
import com.nyuppo.world.gen.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Arrays;
import java.util.List;

public class ModFeatures {
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> SAKURA_TREE;
    public static final RegistryEntry<PlacedFeature> SAKURA_TREE_PLACED;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> SMALL_SAKURA_TREE;
    public static final RegistryEntry<PlacedFeature> SMALL_SAKURA_TREE_PLACED;
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> SAKURA_TREES;
    public static final RegistryEntry<PlacedFeature> SAKURA_TREES_PLACED;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> FLOWER_SAKURA;
    public static final RegistryEntry<PlacedFeature> FLOWER_SAKURA_PLACED;

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> WILLOW_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> WILLOW_TREE_SAPLING;
    public static final RegistryEntry<PlacedFeature> WILLOW_TREE_PLACED;
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> WILLOW_TREES;
    public static final RegistryEntry<PlacedFeature> WILLOW_TREES_PLACED;

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> BAOBAB_TREE;
    public static final RegistryEntry<PlacedFeature> BAOBAB_TREE_PLACED;
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BAOBAB_TREES;
    public static final RegistryEntry<PlacedFeature> BAOBAB_TREES_PLACED;

    public static final RegistryEntry<PlacedFeature> SPARSE_FOREST_ROCK;

    public static final Feature<DefaultFeatureConfig> FALLEN_OAK_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_OAK_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_OAK_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_BIRCH_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_BIRCH_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_SPRUCE_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_SPRUCE_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_SPRUCE_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_ACACIA_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_ACACIA_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_ACACIA_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_JUNGLE_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_JUNGLE_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_JUNGLE_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_SAKURA_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_SAKURA_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_SAKURA_TREE_PLACED;
    public static final Feature<DefaultFeatureConfig> FALLEN_WILLOW_TREE;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_WILLOW_TREE_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> FALLEN_WILLOW_TREE_PLACED;

    public static final Feature<DefaultFeatureConfig> COARSE_DIRT_BLOB;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> COARSE_DIRT_BLOB_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> COARSE_DIRT_BLOB_PLACED;

    public static final Feature<DefaultFeatureConfig> TALL_BIRCH_WILD_FLOWERS;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> TALL_BIRCH_WILD_FLOWERS_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> TALL_BIRCH_WILD_FLOWERS_PLACED;
    public static final Feature<DefaultFeatureConfig> DARK_OAK_WILD_FLOWERS;
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> DARK_OAK_WILD_FLOWERS_CONFIGURED;
    public static final RegistryEntry<PlacedFeature> DARK_OAK_WILD_FLOWERS_PLACED;

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<?, ?>> register(String id, F feature, FC config) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, TheLittleThings.ID(id), new ConfiguredFeature<FC, F>(feature, config));
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, TheLittleThings.ID(name), feature);
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    public static void init() {}

    static {
        SAKURA_TREE = ConfiguredFeatures.register(TheLittleThings.asStringID("sakura_tree"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.SAKURA_LOG), new SakuraTrunkPlacer(6, 2, 2), BlockStateProvider.of(ModBlocks.SAKURA_LEAVES), new SakuraFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        SMALL_SAKURA_TREE = ConfiguredFeatures.register(TheLittleThings.asStringID("small_sakura_tree"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.SAKURA_WOOD), new SakuraTrunkPlacer(1, 1, 0), BlockStateProvider.of(ModBlocks.SAKURA_LEAVES), new SakuraFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        SAKURA_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("sakura_tree_placed"), SAKURA_TREE, PlacedFeatures.wouldSurvive(ModBlocks.SAKURA_SAPLING));
        SMALL_SAKURA_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("small_sakura_tree_placed"), SMALL_SAKURA_TREE, PlacedFeatures.wouldSurvive(ModBlocks.SAKURA_SAPLING));
        SAKURA_TREES = ConfiguredFeatures.register(TheLittleThings.asStringID("sakura_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(SAKURA_TREE_PLACED, 0f)), SAKURA_TREE_PLACED));
        SAKURA_TREES_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("sakura_trees_placed"), SAKURA_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(3, 0.2f, 1)));
        FLOWER_SAKURA = ConfiguredFeatures.register(TheLittleThings.asStringID("flower_sakura"), Feature.FLOWER, createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.PINK_TULIP.getDefaultState(), 2).add(Blocks.POPPY.getDefaultState(), 1).add(Blocks.LILAC.getDefaultState(), 1).build()), 64));
        FLOWER_SAKURA_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("flower_sakura"), FLOWER_SAKURA, RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        WILLOW_TREE = ConfiguredFeatures.register(TheLittleThings.asStringID("willow_tree"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.WILLOW_LOG), new WillowTrunkPlacer(4, 6, 2), BlockStateProvider.of(ModBlocks.WILLOW_LEAVES), new WillowFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 1)).decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25f))).build());
        WILLOW_TREE_SAPLING = ConfiguredFeatures.register(TheLittleThings.asStringID("willow_tree_sapling"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.WILLOW_LOG), new WillowTrunkPlacer(4, 6, 2), BlockStateProvider.of(ModBlocks.WILLOW_LEAVES), new WillowFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        WILLOW_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("willow_tree_placed"), WILLOW_TREE, PlacedFeatures.wouldSurvive(ModBlocks.WILLOW_SAPLING));
        WILLOW_TREES = ConfiguredFeatures.register(TheLittleThings.asStringID("willow_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(WILLOW_TREE_PLACED, 0f)), WILLOW_TREE_PLACED));
        WILLOW_TREES_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("willow_trees_placed"), WILLOW_TREES, PlacedFeatures.createCountExtraModifier(2, 0.1F, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());

        BAOBAB_TREE = ConfiguredFeatures.register(TheLittleThings.asStringID("baobab_tree"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(6, 3, 2), BlockStateProvider.of(ModBlocks.BAOBAB_LEAVES), new BaobabFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        BAOBAB_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("baobab_tree_placed"), BAOBAB_TREE, PlacedFeatures.wouldSurvive(ModBlocks.BAOBAB_SAPLING));
        BAOBAB_TREES = ConfiguredFeatures.register(TheLittleThings.asStringID("baobab_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(BAOBAB_TREE_PLACED, 0f)), BAOBAB_TREE_PLACED));
        BAOBAB_TREES_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("baobab_trees_placed"), BAOBAB_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1)));

        SPARSE_FOREST_ROCK = PlacedFeatures.register(TheLittleThings.asStringID("sparse_mossy_rocks"), MiscConfiguredFeatures.FOREST_ROCK, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        FALLEN_OAK_TREE = register("fallen_oak_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, Blocks.OAK_LOG));
        FALLEN_OAK_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_oak_tree"), FALLEN_OAK_TREE, FeatureConfig.DEFAULT);
        FALLEN_OAK_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_oak_tree"), FALLEN_OAK_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.02f, 1), Blocks.OAK_SAPLING));
        FALLEN_SPRUCE_TREE = register("fallen_spruce_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, Blocks.SPRUCE_LOG));
        FALLEN_SPRUCE_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_spruce_tree"), FALLEN_SPRUCE_TREE, FeatureConfig.DEFAULT);
        FALLEN_SPRUCE_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_spruce_tree"), FALLEN_SPRUCE_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.05f, 1), Blocks.SPRUCE_SAPLING));
        FALLEN_BIRCH_TREE = register("fallen_birch_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, Blocks.BIRCH_LOG));
        FALLEN_BIRCH_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_birch_tree"), FALLEN_BIRCH_TREE, FeatureConfig.DEFAULT);
        FALLEN_BIRCH_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_birch_tree"), FALLEN_BIRCH_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.02f, 1), Blocks.BIRCH_SAPLING));
        FALLEN_ACACIA_TREE = register("fallen_acacia_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, Blocks.ACACIA_LOG));
        FALLEN_ACACIA_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_acacia_tree"), FALLEN_ACACIA_TREE, FeatureConfig.DEFAULT);
        FALLEN_ACACIA_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_acacia_tree"), FALLEN_ACACIA_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.02f, 1), Blocks.ACACIA_SAPLING));
        FALLEN_JUNGLE_TREE = register("fallen_jungle_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, Blocks.JUNGLE_LOG));
        FALLEN_JUNGLE_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_jungle_tree"), FALLEN_JUNGLE_TREE, FeatureConfig.DEFAULT);
        FALLEN_JUNGLE_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_jungle_tree"), FALLEN_JUNGLE_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.02f, 1),Blocks.JUNGLE_SAPLING));
        FALLEN_SAKURA_TREE = register("fallen_sakura_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, ModBlocks.SAKURA_LOG));
        FALLEN_SAKURA_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_sakura_tree"), FALLEN_SAKURA_TREE, FeatureConfig.DEFAULT);
        FALLEN_SAKURA_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_sakura_tree"), FALLEN_SAKURA_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ModBlocks.SAKURA_SAPLING));
        FALLEN_WILLOW_TREE = register("fallen_willow_tree", new FallenTreeFeature(DefaultFeatureConfig.CODEC, ModBlocks.WILLOW_LOG));
        FALLEN_WILLOW_TREE_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("fallen_willow_tree"), FALLEN_WILLOW_TREE, FeatureConfig.DEFAULT);
        FALLEN_WILLOW_TREE_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("fallen_willow_tree"), FALLEN_WILLOW_TREE_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ModBlocks.SAKURA_SAPLING));

        COARSE_DIRT_BLOB = register("coarse_dirt_blob", new BlobReplacerFeature(DefaultFeatureConfig.CODEC, UniformIntProvider.create(2, 6), UniformIntProvider.create(2, 3), Blocks.COARSE_DIRT.getDefaultState(), Blocks.DIRT, Blocks.GRASS_BLOCK));
        COARSE_DIRT_BLOB_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("coarse_dirt_blob"), COARSE_DIRT_BLOB, FeatureConfig.DEFAULT);
        COARSE_DIRT_BLOB_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("coarse_dirt_blob"), COARSE_DIRT_BLOB_CONFIGURED, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

        TALL_BIRCH_WILD_FLOWERS = register("tall_birch_wild_flowers", new WildFlowerFeature(DefaultFeatureConfig.CODEC, new WeightedRandomBag<>(Arrays.asList(
                new Pair<>(ModBlocks.WHITE_WILD_FLOWER, 50.0D),
                new Pair<>(ModBlocks.PURPLE_WILD_FLOWER, 40.0D),
                new Pair<>(ModBlocks.LIGHT_BLUE_WILD_FLOWER, 10.0D)
        )), 8, 0.2d));
        TALL_BIRCH_WILD_FLOWERS_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("tall_birch_wild_flowers"), TALL_BIRCH_WILD_FLOWERS, FeatureConfig.DEFAULT);
        TALL_BIRCH_WILD_FLOWERS_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("tall_birch_wild_flowers"), TALL_BIRCH_WILD_FLOWERS_CONFIGURED, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        DARK_OAK_WILD_FLOWERS = register("dark_oak_wild_flowers", new WildFlowerFeature(DefaultFeatureConfig.CODEC, new WeightedRandomBag<>(Arrays.asList(
                new Pair<>(ModBlocks.RED_WILD_FLOWER, 50.0D),
                new Pair<>(ModBlocks.YELLOW_WILD_FLOWER, 45.0D),
                new Pair<>(ModBlocks.MAGENTA_WILD_FLOWER, 5.0D)
        )), 8, 0.2d));
        DARK_OAK_WILD_FLOWERS_CONFIGURED = ConfiguredFeatures.register(TheLittleThings.asStringID("dark_oak_wild_flowers"), DARK_OAK_WILD_FLOWERS, FeatureConfig.DEFAULT);
        DARK_OAK_WILD_FLOWERS_PLACED = PlacedFeatures.register(TheLittleThings.asStringID("dark_oak_wild_flowers"), DARK_OAK_WILD_FLOWERS_CONFIGURED, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    }
}
