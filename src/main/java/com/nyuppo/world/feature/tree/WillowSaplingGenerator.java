package com.nyuppo.world.feature.tree;

import com.nyuppo.world.feature.ModFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class WillowSaplingGenerator extends SaplingGenerator {
    @Override
    @Nullable
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModFeatures.WILLOW_TREE_SAPLING;
    }
}
