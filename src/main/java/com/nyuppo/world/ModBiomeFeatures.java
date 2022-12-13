package com.nyuppo.world;

import com.nyuppo.world.feature.ModFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ModBiomeFeatures {
    public static void addSakuraTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.SAKURA_TREES_PLACED);
    }

    public static void addSakuraFlowers(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_SAKURA_PLACED);
    }

    public static void addFallenSakuraTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FALLEN_SAKURA_TREE_PLACED);
    }

    public static void addSparseMossyRocks(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ModFeatures.SPARSE_FOREST_ROCK);
    }

    public static void addCoarseDirtBlobs(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ModFeatures.COARSE_DIRT_BLOB_PLACED);
    }

    public static void addBaobabTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.BAOBAB_TREES_PLACED);
    }

    public static void addYuccaFlowers(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModFeatures.FLOWER_YUCCA_PLACED);
    }
}
