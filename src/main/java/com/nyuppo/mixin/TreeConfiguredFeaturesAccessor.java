package com.nyuppo.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TreeConfiguredFeatures.class)
public interface TreeConfiguredFeaturesAccessor {
    /*
    @Accessor("AZALEA_TREE")
    static ConfiguredFeature<?, ?> getAzaleaTree() {
        throw new IllegalStateException();
    }
     */

    @Accessor("AZALEA_TREE")
    static RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> getAzaleaTree() {
        throw new IllegalStateException();
    }
}
