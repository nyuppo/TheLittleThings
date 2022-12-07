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
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.function.BiConsumer;

public class BaobabFoliagePlacer extends FoliagePlacer {
    public static final Codec<BaobabFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> BaobabFoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, BaobabFoliagePlacer::new));

    public static final FoliagePlacerType<BaobabFoliagePlacer> BAOBAB_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("baobab_foliage_placer"), BaobabFoliagePlacer.CODEC);

    public BaobabFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return BAOBAB_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter();

        if (treeNode.getFoliageRadius() == 0) {
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
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west());

            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west().up());
        } else {
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west(3));

            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(3).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(3).west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(3).east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(3).west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().east(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().west(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).east(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north(2).west(3));

            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(3).east());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(3).west());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(3).east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(3).west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().east(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().west(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).east(3));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south(2).west(3));

            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.north().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.south().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.east().up());
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.west().up());
        }
    }

    private void generateCircle(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
        int i = giantTrunk ? 1 : 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int j = -radius; j <= radius + i; ++j) {
            for (int k = -radius; k <= radius + i; ++k) {
                if (j*j + k*k <= radius*radius) {
                    if (this.isPositionInvalid(random, j, y, k, radius, giantTrunk)) continue;
                    mutable.set(centerPos, j, y, k);
                    FoliagePlacer.placeFoliageBlock(world, replacer, random, config, mutable);
                }
            }
        }
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
