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

public class SakuraFoliagePlacer extends FoliagePlacer {
    public static final Codec<SakuraFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> SakuraFoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, SakuraFoliagePlacer::new));

    public static final FoliagePlacerType<SakuraFoliagePlacer> SAKURA_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("sakura_foliage_placer"), SakuraFoliagePlacer.CODEC);

    public SakuraFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return SAKURA_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        boolean bl = treeNode.isGiantTrunk();
        BlockPos blockPos = treeNode.getCenter().up(offset);
        this.generateCircle(world, replacer, random, config, blockPos, radius + treeNode.getFoliageRadius(), -foliageHeight - 1, bl);
        this.generateCircle(world, replacer, random, config, blockPos, radius - 1, -foliageHeight, bl);
        this.generateCircle(world, replacer, random, config, blockPos, radius + treeNode.getFoliageRadius() - 1, 0, bl);

        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.NORTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.SOUTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.EAST, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2));
        FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.WEST, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2));
        if (treeNode.getFoliageRadius() > 0) {
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.NORTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2).south().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.NORTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2).south().east(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.SOUTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2).north().west(2));
            FoliagePlacer.placeFoliageBlock(world, replacer, random, config, blockPos.offset(Direction.SOUTH, radius + treeNode.getFoliageRadius()).down(foliageHeight + 2).north().east(2));
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
