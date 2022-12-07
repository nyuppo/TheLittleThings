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
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class WillowTrunkPlacer extends TrunkPlacer {
    public static final Codec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> WillowTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, WillowTrunkPlacer::new));

    public static final TrunkPlacerType<WillowTrunkPlacer> WILLOW_TRUNK_PLACER = TrunkPlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("willow_trunk_placer"), WillowTrunkPlacer.CODEC);

    public WillowTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return WILLOW_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        WillowTrunkPlacer.setToDirt(world, replacer, random, startPos.down(), config);

        ArrayList<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = startPos.getX();
        int z = startPos.getZ();

        int i = 5 + random.nextInt(4);

        for (int y = 0; y < i; y++) {
            this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY() + y, z), config);
        }
        this.getAndSetState(world, replacer, random, mutable.set(startPos).north(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).south(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).east(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).west(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).north().down(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).south().down(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).east().down(), config);
        this.getAndSetState(world, replacer, random, mutable.set(startPos).west().down(), config);

        int rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).north().up(), config);
        }
        rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).south().up(), config);
        }
        rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).east().up(), config);
        }
        rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).west().up(), config);
        }

        this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i).east(), config);
        rand = random.nextInt(3);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).east(2), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).east(2)), 2, false));
        } else if (rand == 1) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).east(2).north(), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).east(2).north()), 2, false));
        } else {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).east(2).south(), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).east(2).south()), 2, false));
        }

        this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i).west().north(), config);
        rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).west().north(2), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).west().north(2)), 2, false));
        } else {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).west(2).north(2), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).west(2).north(2)), 2, false));
        }

        this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i).west().south(), config);
        rand = random.nextInt(2);
        if (rand == 0) {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).west().south(2), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).west().south(2)), 2, false));
        } else {
            this.getAndSetState(world, replacer, random, mutable.set(startPos).up(i + 1).west(2).south(2), config);
            list.add(new FoliagePlacer.TreeNode(new BlockPos(startPos.up(i + 1).west(2).south(2)), 2, false));
        }

        return list;
    }

}
