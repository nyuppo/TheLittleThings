package com.nyuppo.entity;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.passive.CamelEntity;
import com.nyuppo.entity.passive.CrabEntity;
import com.nyuppo.entity.passive.PenguinEntity;
import com.nyuppo.entity.vehicle.ChestRaftEntity;
import com.nyuppo.entity.vehicle.RaftEntity;
import com.nyuppo.world.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class ModEntityTypes {
    public static EntityType<RaftEntity> RAFT;
    public static EntityType<ChestRaftEntity> CHEST_RAFT;
    public static EntityType<CamelEntity> CAMEL;
    public static EntityType<PenguinEntity> PENGUIN;
    public static EntityType<CrabEntity> CRAB;

    public static void registerEntityTypes() {
        RAFT = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("raft"), FabricEntityTypeBuilder.<RaftEntity>create(SpawnGroup.MISC, RaftEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build());
        CHEST_RAFT = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("chest_raft"), FabricEntityTypeBuilder.<ChestRaftEntity>create(SpawnGroup.MISC, ChestRaftEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build());

        CAMEL = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("camel"), FabricEntityTypeBuilder.<CamelEntity>create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7F, 2.375F)).trackRangeChunks(10).build());
        FabricDefaultAttributeRegistry.register(CAMEL, CamelEntity.createCamelAttributes());
        SpawnRestriction.register(CAMEL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CamelEntity::canSpawn);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT), SpawnGroup.CREATURE, CAMEL, 1, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT), SpawnGroup.CREATURE, EntityType.RABBIT, 8, 2, 3);

        PENGUIN = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("penguin"), FabricEntityTypeBuilder.<PenguinEntity>create(SpawnGroup.CREATURE, PenguinEntity::new).dimensions(EntityDimensions.fixed(0.4f, 1.0f)).trackRangeChunks(10).build());
        FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.getDefaultAttributes());
        SpawnRestriction.register(PENGUIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_BEACH, BiomeKeys.SNOWY_SLOPES, BiomeKeys.SNOWY_TAIGA, BiomeKeys.ICE_SPIKES, BiomeKeys.GROVE, BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.FROZEN_RIVER), SpawnGroup.CREATURE, PENGUIN, 10, 2, 6);

        CRAB = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("crab"), FabricEntityTypeBuilder.<CrabEntity>create(SpawnGroup.CREATURE, CrabEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeChunks(10).build());
        FabricDefaultAttributeRegistry.register(CRAB, CrabEntity.getDefaultAttributes());
        SpawnRestriction.register(CRAB, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, ModBiomes.ARID_DUNES), SpawnGroup.CREATURE, CRAB, 4, 2, 4);
    }

    public static EntityType<CamelEntity> getCamel() {
        return CAMEL;
    }
}
