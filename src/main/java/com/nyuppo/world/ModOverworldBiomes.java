package com.nyuppo.world;

import com.nyuppo.entity.ModEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.jetbrains.annotations.Nullable;

public class ModOverworldBiomes {
    protected static final int DEFAULT_WATER_COLOR = 4159204;
    protected static final int DEFAULT_WATER_FOG_COLOR = 329011;
    private static final int DEFAULT_FOG_COLOR = 12638463;
    @Nullable
    private static final MusicSound DEFAULT_MUSIC = null;

    protected static int getSkyColor(float temperature) {
        float f = temperature;
        f /= 3.0f;
        f = MathHelper.clamp(f, -1.0f, 1.0f);
        return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
    }

    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
        return createBiome(precipitation, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
    }

    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
        return new Biome.Builder().precipitation(precipitation).temperature(temperature).downfall(downfall).effects(new BiomeEffects.Builder().waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).music(music).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    private static void addBasicFeatures(GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    public static Biome createSakuraForest() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CAT, 2, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 4, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));

        DefaultBiomeFeatures.addCaveMobs(builder);
        DefaultBiomeFeatures.addMonsters(builder, 100, 25, 100, false);

        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        addBasicFeatures(builder2);
        //DefaultBiomeFeatures.addMossyRocks(builder2);
        ModBiomeFeatures.addSparseMossyRocks(builder2);
        //DefaultBiomeFeatures.addLargeFerns(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        ModBiomeFeatures.addCoarseDirtBlobs(builder2);
        DefaultBiomeFeatures.addBamboo(builder2);
        //builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, spruce ? VegetationPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA : VegetationPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
        ModBiomeFeatures.addSakuraTrees(builder2);
        ModBiomeFeatures.addFallenSakuraTrees(builder2);
        DefaultBiomeFeatures.addDefaultFlowers(builder2);
        ModBiomeFeatures.addSakuraFlowers(builder2);
        DefaultBiomeFeatures.addForestGrass(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addDefaultVegetation(builder2);
        //DefaultBiomeFeatures.addSweetBerryBushes(builder2);
        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_OLD_GROWTH_TAIGA);
        return createBiome(Biome.Precipitation.RAIN, 0.6f, 0.6f, builder, builder2, musicSound);
    }

    public static Biome createAridDunes() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addCaveMobs(builder);
        DefaultBiomeFeatures.addMonsters(builder, 19, 1, 100, false);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.HUSK, 80, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntityTypes.CAMEL, 2, 1, 3));

        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        DefaultBiomeFeatures.addFossils(builder2);
        addBasicFeatures(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        ModBiomeFeatures.addBaobabTrees(builder2);
        DefaultBiomeFeatures.addDefaultFlowers(builder2);
        DefaultBiomeFeatures.addDefaultGrass(builder2);
        DefaultBiomeFeatures.addDesertDeadBushes(builder2);
        ModBiomeFeatures.addYuccaFlowers(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addDesertVegetation(builder2);
        DefaultBiomeFeatures.addDesertFeatures(builder2);
        return createBiome(Biome.Precipitation.NONE, 2.0f, 0.0f, builder, builder2, DEFAULT_MUSIC);

    }
}
