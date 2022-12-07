package com.nyuppo.world.surfacerule;

import com.google.common.collect.ImmutableList;
import com.nyuppo.registration.ModBlocks;
import com.nyuppo.world.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;

public class ModSurfaceRuleData {
    private static final MaterialRules.MaterialRule ARID_SAND = makeMaterialRule(ModBlocks.ARID_SAND);
    private static final MaterialRules.MaterialRule DUNESTONE = makeMaterialRule(ModBlocks.DUNESTONE);

    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeMaterialRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule DIRT = makeMaterialRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule AIR = makeMaterialRule(Blocks.AIR);
    private static final MaterialRules.MaterialRule BEDROCK = makeMaterialRule(Blocks.BEDROCK);
    private static final MaterialRules.MaterialRule WHITE_TERRACOTTA = makeMaterialRule(Blocks.WHITE_TERRACOTTA);
    private static final MaterialRules.MaterialRule ORANGE_TERRACOTTA = makeMaterialRule(Blocks.ORANGE_TERRACOTTA);
    private static final MaterialRules.MaterialRule TERRACOTTA = makeMaterialRule(Blocks.TERRACOTTA);
    private static final MaterialRules.MaterialRule RED_SAND = makeMaterialRule(Blocks.RED_SAND);
    private static final MaterialRules.MaterialRule RED_SANDSTONE = makeMaterialRule(Blocks.RED_SANDSTONE);
    private static final MaterialRules.MaterialRule STONE = makeMaterialRule(Blocks.STONE);
    private static final MaterialRules.MaterialRule DEEPSLATE = makeMaterialRule(Blocks.DEEPSLATE);
    private static final MaterialRules.MaterialRule PODZOL = makeMaterialRule(Blocks.PODZOL);
    private static final MaterialRules.MaterialRule COARSE_DIRT = makeMaterialRule(Blocks.COARSE_DIRT);
    private static final MaterialRules.MaterialRule MYCELIUM = makeMaterialRule(Blocks.MYCELIUM);
    private static final MaterialRules.MaterialRule CALCITE = makeMaterialRule(Blocks.CALCITE);
    private static final MaterialRules.MaterialRule GRAVEL = makeMaterialRule(Blocks.GRAVEL);
    private static final MaterialRules.MaterialRule SAND = makeMaterialRule(Blocks.SAND);
    private static final MaterialRules.MaterialRule SANDSTONE = makeMaterialRule(Blocks.SANDSTONE);
    private static final MaterialRules.MaterialRule PACKED_ICE = makeMaterialRule(Blocks.PACKED_ICE);
    private static final MaterialRules.MaterialRule SNOW_BLOCK = makeMaterialRule(Blocks.SNOW_BLOCK);
    private static final MaterialRules.MaterialRule MUD = makeMaterialRule(Blocks.MUD);
    private static final MaterialRules.MaterialRule POWDER_SNOW = makeMaterialRule(Blocks.POWDER_SNOW);
    private static final MaterialRules.MaterialRule ICE = makeMaterialRule(Blocks.ICE);
    private static final MaterialRules.MaterialRule WATER = makeMaterialRule(Blocks.WATER);

    private static MaterialRules.MaterialRule makeMaterialRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }

    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.sequence(
                createModdedOverworldSurfaceRule()
        );
    }

    public static MaterialRules.MaterialRule createModdedOverworldSurfaceRule() {
        return createModdedRules(true, false, true);
    }

    public static MaterialRules.MaterialRule createModdedRules(boolean surface, boolean bedrockRoof, boolean bedrockFloor) {
        MaterialRules.MaterialCondition materialCondition = MaterialRules.aboveY(YOffset.fixed(97), 2);
        MaterialRules.MaterialCondition materialCondition2 = MaterialRules.aboveY(YOffset.fixed(256), 0);
        MaterialRules.MaterialCondition materialCondition3 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(63), -1);
        MaterialRules.MaterialCondition materialCondition4 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(74), 1);
        MaterialRules.MaterialCondition materialCondition5 = MaterialRules.aboveY(YOffset.fixed(60), 0);
        MaterialRules.MaterialCondition materialCondition6 = MaterialRules.aboveY(YOffset.fixed(62), 0);
        MaterialRules.MaterialCondition materialCondition7 = MaterialRules.aboveY(YOffset.fixed(63), 0);
        MaterialRules.MaterialCondition materialCondition8 = MaterialRules.water(-1, 0);
        MaterialRules.MaterialCondition materialCondition9 = MaterialRules.water(0, 0);
        MaterialRules.MaterialCondition materialCondition10 = MaterialRules.waterWithStoneDepth(-6, -1);
        MaterialRules.MaterialCondition materialCondition11 = MaterialRules.hole();
        MaterialRules.MaterialCondition materialCondition12 = MaterialRules.biome(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN);
        MaterialRules.MaterialCondition materialCondition13 = MaterialRules.steepSlope();
        MaterialRules.MaterialRule materialRule = MaterialRules.sequence(MaterialRules.condition(materialCondition9, GRASS_BLOCK), DIRT);
        MaterialRules.MaterialRule materialRule2 = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, SANDSTONE), SAND);
        MaterialRules.MaterialRule materialRuleAridDunes = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, DUNESTONE), ARID_SAND);
        MaterialRules.MaterialRule materialRule3 = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), GRAVEL);
        MaterialRules.MaterialCondition materialCondition14 = MaterialRules.biome(BiomeKeys.WARM_OCEAN, BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH);
        MaterialRules.MaterialCondition materialCondition15 = MaterialRules.biome(BiomeKeys.DESERT);
        MaterialRules.MaterialCondition materialConditionAridDunes = MaterialRules.biome(ModBiomes.ARID_DUNES);
        MaterialRules.MaterialRule materialRule4 = MaterialRules.sequence(
                MaterialRules.condition
                        (MaterialRules.biome(BiomeKeys.STONY_PEAKS), MaterialRules.sequence(
                            MaterialRules.condition(
                                MaterialRules.noiseThreshold(NoiseParametersKeys.CALCITE, -0.0125, 0.0125), CALCITE), STONE)),
                MaterialRules.condition(
                        MaterialRules.biome(BiomeKeys.STONY_SHORE), MaterialRules.sequence(
                                MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.GRAVEL, -0.05, 0.05), materialRule3), STONE)),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WINDSWEPT_HILLS),
                        MaterialRules.condition(
                                surfaceNoiseThreshold(1.0), STONE)),
                MaterialRules.condition(materialCondition14, materialRule2),
                MaterialRules.condition(materialCondition15, materialRule2),
                MaterialRules.condition(materialConditionAridDunes, materialRuleAridDunes),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.DRIPSTONE_CAVES), STONE));
        MaterialRules.MaterialRule materialRule5 = MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.POWDER_SNOW, 0.45, 0.58), MaterialRules.condition(materialCondition9, POWDER_SNOW));
        MaterialRules.MaterialRule materialRule6 = MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.POWDER_SNOW, 0.35, 0.6), MaterialRules.condition(materialCondition9, POWDER_SNOW));
        MaterialRules.MaterialRule materialRule7 = MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.biome(BiomeKeys.FROZEN_PEAKS), MaterialRules.sequence(
                                MaterialRules.condition(materialCondition13, PACKED_ICE),
                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.PACKED_ICE, -0.5, 0.2), PACKED_ICE),
                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.ICE, -0.0625, 0.025), ICE),
                                MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.SNOWY_SLOPES),
                        MaterialRules.sequence(
                                MaterialRules.condition(materialCondition13, STONE),
                                materialRule5,
                                MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.JAGGED_PEAKS), STONE),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.GROVE),
                        MaterialRules.sequence(materialRule5, DIRT)),
                materialRule4,
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WINDSWEPT_SAVANNA),
                        MaterialRules.condition(surfaceNoiseThreshold(1.75), STONE)),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS),
                        MaterialRules.sequence(
                                MaterialRules.condition(surfaceNoiseThreshold(2.0), materialRule3),
                                MaterialRules.condition(surfaceNoiseThreshold(1.0), STONE),
                                MaterialRules.condition(surfaceNoiseThreshold(-1.0), DIRT), materialRule3)),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.MANGROVE_SWAMP), MUD), DIRT);
        MaterialRules.MaterialRule materialRule8 = MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.FROZEN_PEAKS),
                        MaterialRules.sequence(MaterialRules.condition(materialCondition13, PACKED_ICE),
                                MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.PACKED_ICE, 0.0, 0.2), PACKED_ICE),
                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.ICE, 0.0, 0.025), ICE),
                                MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.SNOWY_SLOPES), MaterialRules.sequence(
                        MaterialRules.condition(materialCondition13, STONE),
                        materialRule6,
                        MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.JAGGED_PEAKS),
                        MaterialRules.sequence(
                                MaterialRules.condition(materialCondition13, STONE),
                                MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.GROVE),
                        MaterialRules.sequence(materialRule6,
                                MaterialRules.condition(materialCondition9, SNOW_BLOCK))),
                materialRule4,
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WINDSWEPT_SAVANNA),
                        MaterialRules.sequence(MaterialRules.condition(surfaceNoiseThreshold(1.75), STONE),
                                MaterialRules.condition(surfaceNoiseThreshold(-0.5), COARSE_DIRT))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS),
                        MaterialRules.sequence(
                                MaterialRules.condition(surfaceNoiseThreshold(2.0), materialRule3),
                                MaterialRules.condition(surfaceNoiseThreshold(1.0), STONE),
                                MaterialRules.condition(surfaceNoiseThreshold(-1.0), materialRule), materialRule3)),
                MaterialRules.condition(
                        MaterialRules.biome(BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                        MaterialRules.sequence(
                                MaterialRules.condition(surfaceNoiseThreshold(1.75), COARSE_DIRT),
                                MaterialRules.condition(surfaceNoiseThreshold(-0.95), PODZOL))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.ICE_SPIKES),
                        MaterialRules.condition(materialCondition9, SNOW_BLOCK)),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.MANGROVE_SWAMP), MUD),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.MUSHROOM_FIELDS), MYCELIUM), materialRule);
        MaterialRules.MaterialCondition materialCondition16 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.909, -0.5454);
        MaterialRules.MaterialCondition materialCondition17 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.1818, 0.1818);
        MaterialRules.MaterialCondition materialCondition18 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 0.5454, 0.909);
        MaterialRules.MaterialRule materialRule9 = MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(BiomeKeys.WOODED_BADLANDS),
                                MaterialRules.condition(materialCondition,
                                        MaterialRules.sequence(
                                                MaterialRules.condition(materialCondition16, COARSE_DIRT),
                                                MaterialRules.condition(materialCondition17, COARSE_DIRT),
                                                MaterialRules.condition(materialCondition18, COARSE_DIRT), materialRule))),
                        MaterialRules.condition(MaterialRules.biome(BiomeKeys.SWAMP),
                                MaterialRules.condition(materialCondition6, MaterialRules.condition(
                                        MaterialRules.not(materialCondition7),
                                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER)))),
                        MaterialRules.condition(MaterialRules.biome(BiomeKeys.MANGROVE_SWAMP),
                                MaterialRules.condition(materialCondition5, MaterialRules.condition(
                                        MaterialRules.not(materialCondition7),
                                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER)))))),
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS),
                        MaterialRules.sequence(
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                                        MaterialRules.condition(materialCondition2, ORANGE_TERRACOTTA),
                                        MaterialRules.condition(materialCondition4,
                                                MaterialRules.sequence(
                                                        MaterialRules.condition(materialCondition16, TERRACOTTA),
                                                        MaterialRules.condition(materialCondition17, TERRACOTTA),
                                                        MaterialRules.condition(materialCondition18, TERRACOTTA),
                                                        MaterialRules.terracottaBands())),
                                        MaterialRules.condition(materialCondition8, MaterialRules.sequence(
                                                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, RED_SANDSTONE), RED_SAND)),
                                        MaterialRules.condition(MaterialRules.not(materialCondition11), ORANGE_TERRACOTTA),
                                        MaterialRules.condition(materialCondition10, WHITE_TERRACOTTA), materialRule3)),
                                MaterialRules.condition(materialCondition3, MaterialRules.sequence(
                                        MaterialRules.condition(materialCondition7, MaterialRules.condition(
                                                MaterialRules.not(materialCondition4), ORANGE_TERRACOTTA)),
                                        MaterialRules.terracottaBands())),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                        MaterialRules.condition(materialCondition10, WHITE_TERRACOTTA)))),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
                        MaterialRules.condition(materialCondition8, MaterialRules.sequence(
                                MaterialRules.condition(materialCondition12,
                                        MaterialRules.condition(materialCondition11, MaterialRules.sequence(
                                                MaterialRules.condition(materialCondition9, AIR),
                                                MaterialRules.condition(MaterialRules.temperature(), ICE), WATER))), materialRule8))),
                MaterialRules.condition(materialCondition10, MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
                                MaterialRules.condition(materialCondition12, MaterialRules.condition(materialCondition11, WATER))),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, materialRule7),
                        MaterialRules.condition(materialCondition14, MaterialRules.condition(
                                MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, SANDSTONE)),
                        MaterialRules.condition(materialCondition15, MaterialRules.condition(
                                MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30, SANDSTONE)),
                        MaterialRules.condition(materialConditionAridDunes, MaterialRules.condition(
                                MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30, DUNESTONE)))),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
                        MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS), STONE),
                                MaterialRules.condition(MaterialRules.biome(BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), materialRule2), materialRule3)));
        ImmutableList.Builder builder = ImmutableList.builder();
        if (bedrockRoof) {
            builder.add(MaterialRules.condition(MaterialRules.not(MaterialRules.verticalGradient("bedrock_roof", YOffset.belowTop(5), YOffset.getTop())), BEDROCK));
        }
        if (bedrockFloor) {
            builder.add(MaterialRules.condition(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)), BEDROCK));
        }
        MaterialRules.MaterialRule materialRule10 = MaterialRules.condition(MaterialRules.surface(), materialRule9);
        builder.add(surface ? materialRule10 : materialRule9);
        builder.add(MaterialRules.condition(MaterialRules.verticalGradient("deepslate", YOffset.fixed(0), YOffset.fixed(8)), DEEPSLATE));
        return MaterialRules.sequence((MaterialRules.MaterialRule[])builder.build().toArray(MaterialRules.MaterialRule[]::new));
    }

    private static MaterialRules.MaterialCondition surfaceNoiseThreshold(double min) {
        return MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, min / 8.25, Double.MAX_VALUE);
    }
}
