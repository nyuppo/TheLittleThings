package com.nyuppo.world.feature.natural;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class BlobReplacerFeature extends Feature<DefaultFeatureConfig> {
    List<Block> toReplace;
    BlockState replacer;
    UniformIntProvider radiusProvider;
    UniformIntProvider heightProvider;

    public BlobReplacerFeature(Codec<DefaultFeatureConfig> codec, UniformIntProvider radiusProvider, UniformIntProvider heightProvider, BlockState replacer,  Block... toReplace) {
        super(codec);
        this.radiusProvider = radiusProvider;
        this.heightProvider = heightProvider;
        this.replacer = replacer;
        this.toReplace = List.of(toReplace);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos pos = context.getOrigin().down();

        int height = this.heightProvider.get(random);
        int halfHeight = (int)(height / 2);
        int radius = this.radiusProvider.get(random);

        if (height == 0 || radius == 0 || this.toReplace.isEmpty()) {
            return false;
        }

        for (int k = -halfHeight; k <= halfHeight; k++) {
            int tempRadius;
            if (k == 0) {
                tempRadius = radius;
            } else {
                tempRadius = (int)((1f - ((float)MathHelper.abs(k) / (float)height)) * (float)radius);
                if (tempRadius == 0) {
                    tempRadius = 1;
                }
            }

            for (int i = -tempRadius; i <= tempRadius; i++) {
                for (int j = -tempRadius; j <= tempRadius; j++) {
                    if (i*i + j*j <= tempRadius*tempRadius && this.toReplace.contains(world.getBlockState(pos.add(i, k, j)).getBlock())) {
                        world.setBlockState(pos.add(i, k, j), this.replacer, Block.NOTIFY_ALL);
                    }
                }
            }
        }

        return true;
    }
}
