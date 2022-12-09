package com.nyuppo.world.feature.natural;

import com.mojang.serialization.Codec;
import com.nyuppo.util.WeightedRandomBag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FairyRingFeature extends Feature<DefaultFeatureConfig> {
    WeightedRandomBag<Block> flowers = new WeightedRandomBag<>();

    public FairyRingFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);

        flowers.addEntry(Blocks.OXEYE_DAISY, 30.0);
        flowers.addEntry(Blocks.POPPY, 20.0);
        flowers.addEntry(Blocks.DANDELION, 20.0);
        flowers.addEntry(Blocks.AZURE_BLUET, 20.0);
        flowers.addEntry(Blocks.LILY_OF_THE_VALLEY, 5.0);
        flowers.addEntry(Blocks.ALLIUM, 5.0);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        return generate(context.getWorld(),context.getRandom(),context.getOrigin());
    }

    private boolean generate(StructureWorldAccess world, Random random, BlockPos pos) {
        BlockPos center = new BlockPos(pos.getX(), 128, pos.getY());

        if (world.getRandom().nextDouble() < 0.5d) {
            BlockPos temp = center;
            BlockState state = world.getBlockState(pos);

            while (!state.isIn(BlockTags.DIRT)  && pos.getY() > 30) {
                pos = pos.down();
                state = world.getBlockState(pos);
            }

            if (state.isIn(BlockTags.DIRT) ) {
                generateFairyRing(world, pos.down());
                return true;
            }
        }
        return false;


    }

    private void generateFairyRing(StructureWorldAccess world, BlockPos pos) {
        BlockState flowerState = flowers.getRandom().getDefaultState();

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                float dist = (i * i) + (j * j);
                if (dist < 7 || dist > 10) {
                    for (int k = 6; k > -3; k--) {
                        BlockPos fpos = pos.add(i, k, j);
                        BlockState state = world.getBlockState(fpos);
                        if (state.isIn(BlockTags.SMALL_FLOWERS)) {
                            world.setBlockState(fpos, Blocks.AIR.getDefaultState(), 2);
                            break;
                        }
                    }
                } else {
                    for (int k = 5; k > -4; k--) {
                        BlockPos fpos = pos.add(i, k, j);
                        BlockPos fposUp = fpos.up();
                        BlockState state = world.getBlockState(fpos);
                        if (state.isIn(BlockTags.DIRT) && world.getBlockState(fposUp).getMaterial().isReplaceable()) {
                            world.setBlockState(fposUp, flowerState, 2);
                            break;
                        }
                    }
                }
            }
        }

        BlockPos orePos = pos.down(world.getRandom().nextInt(10) + 25);
        BlockState stoneState = world.getBlockState(orePos);
        int down = 0;

        while (!stoneState.isIn(BlockTags.BASE_STONE_OVERWORLD) && down < 10) {
            orePos = orePos.down();
            stoneState = world.getBlockState(orePos);
            down++;
        }

        if (stoneState.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            BlockState ore = world.getRandom().nextBoolean() ? Blocks.DIAMOND_ORE.getDefaultState() : Blocks.EMERALD_ORE.getDefaultState();
            world.setBlockState(orePos, ore, 2);
            for (Direction dir : Direction.values()) {
                if (world.getRandom().nextBoolean()) {
                    world.setBlockState(orePos.offset(dir), ore, 2);
                }
            }
        }
    }
}
