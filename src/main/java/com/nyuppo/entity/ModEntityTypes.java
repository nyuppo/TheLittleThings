package com.nyuppo.entity;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.passive.CamelEntity;
import com.nyuppo.entity.vehicle.ChestRaftEntity;
import com.nyuppo.entity.vehicle.RaftEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class ModEntityTypes {
    public static EntityType<RaftEntity> RAFT;
    public static EntityType<ChestRaftEntity> CHEST_RAFT;
    public static EntityType<CamelEntity> CAMEL;

    public static void registerEntityTypes() {
        RAFT = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("raft"), FabricEntityTypeBuilder.<RaftEntity>create(SpawnGroup.MISC, RaftEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build());
        CHEST_RAFT = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("chest_raft"), FabricEntityTypeBuilder.<ChestRaftEntity>create(SpawnGroup.MISC, ChestRaftEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build());

        CAMEL = Registry.register(Registry.ENTITY_TYPE, TheLittleThings.ID("camel"), FabricEntityTypeBuilder.<CamelEntity>create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7F, 2.375F)).trackRangeChunks(10).build());
        FabricDefaultAttributeRegistry.register(CAMEL, CamelEntity.createCamelAttributes());
        SpawnRestriction.register(CAMEL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CamelEntity::canSpawn);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT), SpawnGroup.CREATURE, CAMEL, 1, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT), SpawnGroup.CREATURE, EntityType.RABBIT, 8, 2, 3);
    }

    public static EntityType<CamelEntity> getCamel() {
        return CAMEL;
    }
}
