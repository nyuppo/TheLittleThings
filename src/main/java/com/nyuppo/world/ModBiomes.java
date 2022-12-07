package com.nyuppo.world;

import com.nyuppo.TheLittleThings;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class ModBiomes {
    public static final RegistryKey<Biome> SAKURA_FOREST = register("sakura_forest");
    public static final RegistryKey<Biome> ARID_DUNES = register("arid_dunes");

    public static void registerBiomes() {
        register(SAKURA_FOREST, ModOverworldBiomes.createSakuraForest());
        register(ARID_DUNES, ModOverworldBiomes.createAridDunes());
    }

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, TheLittleThings.ID(name));
    }

    private static void register(RegistryKey<Biome> key, Biome biome) {
        BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }
}
