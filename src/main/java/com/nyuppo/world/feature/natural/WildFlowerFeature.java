package com.nyuppo.world.feature.natural;

import com.mojang.serialization.Codec;
import com.nyuppo.block.WildFlowerBlock;
import com.nyuppo.block.enums.WildFlowerState;
import com.nyuppo.util.WeightedRandomBag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WildFlowerFeature extends Feature<DefaultFeatureConfig> {
    WeightedRandomBag<Block> flowers;
    int maxRadius;
    double density;

    public WildFlowerFeature(Codec<DefaultFeatureConfig> configCodec, WeightedRandomBag<Block> flowers, int maxRadius, double density) {
        super(configCodec);
        this.flowers = flowers;
        this.maxRadius = maxRadius;
        this.density = density;
    }

    public WildFlowerFeature(Codec<DefaultFeatureConfig> configCodec, Block flower, int maxRadius, double density) {
        super(configCodec);
        this.flowers = new WeightedRandomBag<>();
        this.flowers.addEntry(flower, 100.0);
        this.maxRadius = maxRadius;
        this.density = density;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos pos = context.getOrigin();

        int radius = random.nextInt(maxRadius) + 2;
        BlockPos centerPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
        if (!world.getBlockState(centerPos).getMaterial().isReplaceable() || !world.getBlockState(centerPos.down()).isIn(BlockTags.DIRT)) {
            return false;
        }

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i*i + j*j <= radius*radius && random.nextDouble() < this.density) {
                    generateFlower(world, random, centerPos.add(i, 0, j));
                }
            }
        }

        return true;
    }

    public void generateFlower(StructureWorldAccess world, Random random, BlockPos pos) {
        BlockPos tempPos = pos.up(3);

        for (int i = 0; i < 6; i++) {
            BlockPos fPos = tempPos.down(i);
            BlockState state = world.getBlockState(fPos);
            if (state.getMaterial().isReplaceable() && world.getBlockState(fPos.down()).isIn(BlockTags.DIRT)) {
                Block flower = this.flowers.getRandom();
                BlockState flowerState = flower.getDefaultState();

                if (flower instanceof WildFlowerBlock) {
                    flowerState = flower.getDefaultState().with(WildFlowerBlock.FLOWER_STATE, random.nextFloat() < 0.01F ? WildFlowerState.SPREADING : WildFlowerState.DEFAULT);
                }

                world.setBlockState(fPos, flowerState, Block.NOTIFY_ALL);
                break;
            }
        }
    }
}