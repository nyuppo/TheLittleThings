package com.nyuppo.registration;

import com.nyuppo.mixin.TreeConfiguredFeaturesAccessor;
import com.nyuppo.mixin.TreeFeatureConfigAccessor;
import com.nyuppo.world.gen.ModSimpleBlockStateProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class SwapAzaleaWood {
    public static void swapAzaleaWood() {
        //ConfiguredFeature<?, ?> feature = TreeConfiguredFeaturesAccessor.getAzaleaTree();
        RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> registryEntry = TreeConfiguredFeaturesAccessor.getAzaleaTree();
        TreeFeatureConfig config = (TreeFeatureConfig)registryEntry.value().config();
        ((TreeFeatureConfigAccessor)config).setTrunkProvider(new ModSimpleBlockStateProvider(ModBlocks.AZALEA_LOG.getDefaultState()));

        //TreeFeatureConfig config = (TreeFeatureConfig)feature.config;
        //((TreeFeatureConfigAccessor)config).setTrunkProvider(new ModSimpleBlockStateProvider(Caboodle.AZALEA_LOG.getDefaultState()));
    }
}
