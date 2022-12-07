package com.nyuppo.world.region;

import com.mojang.datafixers.util.Pair;
import com.nyuppo.world.ModBiomes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class ModRegion extends Region {
    public ModRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        //this.addBiomeSimilar(mapper, BiomeKeys.BIRCH_FOREST, ModBiomes.SAKURA_FOREST);
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(BiomeKeys.BIRCH_FOREST, ModBiomes.SAKURA_FOREST);

            builder.replaceBiome(BiomeKeys.DESERT, ModBiomes.ARID_DUNES);
        });
    }
}
