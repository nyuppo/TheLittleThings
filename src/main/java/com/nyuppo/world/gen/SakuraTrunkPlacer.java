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
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class SakuraTrunkPlacer extends TrunkPlacer {
    public static final Codec<SakuraTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> SakuraTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, SakuraTrunkPlacer::new));

    public static final TrunkPlacerType<SakuraTrunkPlacer> SAKURA_TRUNK_PLACER = TrunkPlacerTypeInvoker.invokeRegister(TheLittleThings.asStringID("sakura_trunk_placer"), SakuraTrunkPlacer.CODEC);

    public SakuraTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }


    @Override
    protected TrunkPlacerType<?> getType() {
        return SAKURA_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        SakuraTrunkPlacer.setToDirt(world, replacer, random, startPos.down(), config);

        ArrayList<FoliagePlacer.TreeNode> list = Lists.newArrayList();

        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int i = height - random.nextInt(4) - 1;
        int j = 3 - random.nextInt(3);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = startPos.getX();
        int z = startPos.getZ();

        OptionalInt optionalInt = OptionalInt.empty();

        for (Direction dir : Direction.Type.HORIZONTAL.stream().toList()) {
            if (random.nextBoolean()) {
                this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY(), z).move(dir), config);
                this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY() - 1, z).move(dir), config);

                Direction rotated = random.nextBoolean() ? dir.rotateYClockwise() : dir.rotateYCounterclockwise();
                this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY(), z).move(rotated), config);
                this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY() - 1, z).move(rotated), config);

                if (random.nextBoolean()) {
                    this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY() + 1, z).move(dir), config);
                }
            }
        }

        int n;
        for (int k = 0; k < height; k++) {
            n = startPos.getY() + k;
            if (k >= i && j > 0) {
                x += direction.getOffsetX();
                z += direction.getOffsetZ();
                j--;
            }
            if (!this.getAndSetState(world, replacer, random, mutable.set(x, n, z), config)) continue;
            optionalInt = OptionalInt.of(n + 1);
        }
        if (optionalInt.isPresent()) {
            list.add(new FoliagePlacer.TreeNode(new BlockPos(x, optionalInt.getAsInt(), z), 1, false));
        }

        x = startPos.getX();
        z = startPos.getZ();
        Direction direction2 = Direction.Type.HORIZONTAL.random(random);
        if (direction2 != direction) {
            n = i - random.nextInt(2) - 1;
            int o = 1 + random.nextInt(3);
            optionalInt = OptionalInt.empty();
            for (int p = n; p < height && o > 0; ++p, --o) {
                if (p < 1) continue;
                int q = startPos.getY() + p;
                if (!this.getAndSetState(world, replacer, random, mutable.set(x += direction2.getOffsetX(), q, z += direction2.getOffsetZ()), config)) continue;
                optionalInt = OptionalInt.of(q + 1);
            }
            if (optionalInt.isPresent()) {
                list.add(new FoliagePlacer.TreeNode(new BlockPos(x, optionalInt.getAsInt(), z), 0, false));
            }
        }
        return list;
    }


}
