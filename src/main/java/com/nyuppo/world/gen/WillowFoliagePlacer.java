package com.nyuppo.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyuppo.TheLittleThings;
import com.nyuppo.mixin.FoliagePlacerTypeInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.function.BiConsumer;

public class WillowFoliagePlacer extends FoliagePlacer {
    public static final Codec<WillowFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> WillowFoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, WillowFoliagePlacer::new));

    public static final FoliagePlacerType<WillowFoliagePlacer> WILLOW_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("willow_foliage_placer"), WillowFoliagePlacer.CODEC);

    public WillowFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WILLOW_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter();

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up().north());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up().south());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up().east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up().west());

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(2));

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).west());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west(2));

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).down());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).down(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east().down());

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).down());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).down(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west().down());

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(2).down());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(2).down(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(2).south().down());

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(2).down());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(2).down(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(2).north().down());

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down(2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down().north());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down().south());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down().east());
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.down().west());
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (y == 0) {
            return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
        }
        return dx == radius && dz == radius && radius > 0;
    }
}
