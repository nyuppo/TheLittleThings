package com.nyuppo.world.gen;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyuppo.TheLittleThings;
import com.nyuppo.mixin.TrunkPlacerTypeInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public static final Codec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> BaobabTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, BaobabTrunkPlacer::new));

    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = TrunkPlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("baobab_trunk_placer"), BaobabTrunkPlacer.CODEC);

    public BaobabTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return BAOBAB_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ArrayList<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = startPos.getX();
        int y = startPos.getY();
        int z = startPos.getZ();

        int sides = height - 2;
        for (int i = -2; i < sides; i++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z), config);
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).north(), config);
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).south(), config);
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).east(), config);
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).west(), config);
        }
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - 2, z), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - 1, z), config);
        list.add(new FoliagePlacer.TreeNode(new BlockPos(x, y + height - 1, z), 1, false));

        int cornerHeight = 2 + random.nextInt(2);
        for (int i = -1; i < cornerHeight; i++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).north().east(), config);
        }

        cornerHeight = 2 + random.nextInt(2);
        for (int i = -1; i < cornerHeight; i++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).north().west(), config);
        }

        cornerHeight = 2 + random.nextInt(2);
        for (int i = -1; i < cornerHeight; i++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).south().east(), config);
        }

        cornerHeight = 2 + random.nextInt(2);
        for (int i = -1; i < cornerHeight; i++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, y + i, z).south().west(), config);
        }

        int branchOffset = 3 + random.nextInt(2);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).north(2), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).north(3), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).north(4), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).north(4).up(), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).north(4).up(2), config);
        list.add(new FoliagePlacer.TreeNode(new BlockPos(x, y + height - branchOffset, z).north(4).up(2), 0, false));

        branchOffset = 3 + random.nextInt(2);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).south(2), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).south(3), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).south(4), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).south(4).up(), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).south(4).up(2), config);
        list.add(new FoliagePlacer.TreeNode(new BlockPos(x, y + height - branchOffset, z).south(4).up(2), 0, false));

        branchOffset = 3 + random.nextInt(2);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).east(2), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).east(3), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).east(4), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).east(4).up(), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).east(4).up(2), config);
        list.add(new FoliagePlacer.TreeNode(new BlockPos(x, y + height - branchOffset, z).east(4).up(2), 0, false));

        branchOffset = 3 + random.nextInt(2);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).west(2), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).west(3), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).west(4), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).west(4).up(), config);
        this.getAndSetState(world, replacer, random, mutable.set(x, y + height - branchOffset, z).west(4).up(2), config);
        list.add(new FoliagePlacer.TreeNode(new BlockPos(x, y + height - branchOffset, z).west(4).up(2), 0, false));

        return list;
    }

}
