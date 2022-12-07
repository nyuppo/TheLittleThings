package com.nyuppo.mixin;

import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TreeFeatureConfig.class)
public interface TreeFeatureConfigAccessor {
    @Mutable
    @Accessor("trunkProvider")
    void setTrunkProvider(BlockStateProvider provider);
}
