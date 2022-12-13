package com.nyuppo.world;

import com.nyuppo.TheLittleThings;
import com.nyuppo.world.feature.ModFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.function.Predicate;

public class ModWorldGen {
    private static final Predicate<BiomeSelectionContext> OAK_FALLEN_TREE_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.FOREST,BiomeKeys.FLOWER_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.WINDSWEPT_FOREST);
    private static final Predicate<BiomeSelectionContext> BIRCH_FALLEN_TREE_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.FOREST,BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
    private static final Predicate<BiomeSelectionContext> SPRUCE_FALLEN_TREE_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.TAIGA,BiomeKeys.WINDSWEPT_FOREST,BiomeKeys.TAIGA);
    private static final Predicate<BiomeSelectionContext> ACACIA_FALLEN_TREE_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA);
    private static final Predicate<BiomeSelectionContext> JUNGLE_FALLEN_TREE_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE);
    private static final Predicate<BiomeSelectionContext> SWAMP_BIOME = BiomeSelectors.includeByKey(BiomeKeys.SWAMP);
    private static final Predicate<BiomeSelectionContext> FAIRY_RING_BIOMES = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.PLAINS, BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DARK_FOREST, ModBiomes.SAKURA_FOREST);

    private static void doModifications() {
        addSakuraTrees();
        addFallenTrees();
        modifySwamp();
        addFairyRings();
        addWildFlowers();
        addFlowers();
    }

    private static void addSakuraTrees() {
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModConfiguredFeatures.SAKURA_TREES_PLACED.getKey().get());
    }

    private static void addFallenTrees() {
        BiomeModifications.create(TheLittleThings.ID("fallen_trees_oak")).add(ModificationPhase.ADDITIONS, OAK_FALLEN_TREE_BIOMES, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_OAK_TREE_PLACED.getKey().get());
        });

        BiomeModifications.create(TheLittleThings.ID("fallen_trees_birch")).add(ModificationPhase.ADDITIONS, BIRCH_FALLEN_TREE_BIOMES, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_BIRCH_TREE_PLACED.getKey().get());
        });

        BiomeModifications.create(TheLittleThings.ID("fallen_trees_spruce")).add(ModificationPhase.ADDITIONS, SPRUCE_FALLEN_TREE_BIOMES, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_SPRUCE_TREE_PLACED.getKey().get());
        });

        BiomeModifications.create(TheLittleThings.ID("fallen_trees_acacia")).add(ModificationPhase.ADDITIONS, ACACIA_FALLEN_TREE_BIOMES, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_ACACIA_TREE_PLACED.getKey().get());
        });

        BiomeModifications.create(TheLittleThings.ID("fallen_trees_jungle")).add(ModificationPhase.ADDITIONS, JUNGLE_FALLEN_TREE_BIOMES, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_JUNGLE_TREE_PLACED.getKey().get());
        });
    }

    private static void modifySwamp() {
        BiomeModifications.create(TheLittleThings.ID("remove_vanilla_swamp_trees"))
                .add(ModificationPhase.REPLACEMENTS, BiomeSelectors.includeByKey(BiomeKeys.SWAMP), (c) -> {
                    c.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.TREES_SWAMP.value());
                });
        BiomeModifications.create(TheLittleThings.ID("willow_trees")).add(ModificationPhase.ADDITIONS, SWAMP_BIOME, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.WILLOW_TREES_PLACED.getKey().get());
        });
        BiomeModifications.create(TheLittleThings.ID("fallen_trees_willow")).add(ModificationPhase.ADDITIONS, SWAMP_BIOME, biomeModificationContext -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_WILLOW_TREE_PLACED.getKey().get());
        });
    }

    private static void addWildFlowers() {
        BiomeModifications.create(TheLittleThings.ID("wild_flowers_tall_birch_forest"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.TALL_BIRCH_WILD_FLOWERS_PLACED.getKey().get());
                });
        BiomeModifications.create(TheLittleThings.ID("wild_flowers_dark_oak_forest"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.DARK_OAK_WILD_FLOWERS_PLACED.getKey().get());
                });
    }

    private static void addFairyRings() {
        BiomeModifications.create(TheLittleThings.ID("fairy_rings"))
                .add(ModificationPhase.ADDITIONS, FAIRY_RING_BIOMES, (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FAIRY_RING_PLACED.getKey().get());
                });
    }

    private static void addFlowers() {
        BiomeModifications.create(TheLittleThings.ID("carnations"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, ModBiomes.SAKURA_FOREST), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_PINK_CARNATION_PLACED.getKey().get());
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_RED_CARNATION_PLACED.getKey().get());
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_WHITE_CARNATION_PLACED.getKey().get());
                });

        BiomeModifications.create(TheLittleThings.ID("crocus"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_CROCUS_PLACED.getKey().get());
                });

        BiomeModifications.create(TheLittleThings.ID("heliconia"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_HELICONIA_PLACED.getKey().get());
                });

        /*
        BiomeModifications.create(TheLittleThings.ID("yucca"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(ModBiomes.ARID_DUNES), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_YUCCA_PLACED.getKey().get());
                });
        */

        BiomeModifications.create(TheLittleThings.ID("crillea"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_CRILLEA_PLACED.getKey().get());
                });

        BiomeModifications.create(TheLittleThings.ID("eko"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), (c) -> {
                    c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_EKO_PLACED.getKey().get());
                });
    }

    public static void init() {
        doModifications();
    }
}
