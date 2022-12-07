package com.nyuppo.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class WallPiglinHeadBlock extends ModWallSkullBlock {
    private static final Map<Direction, VoxelShape> SHAPES;

    public WallPiglinHeadBlock(Settings settings) {
        super(ModSkullBlock.Type.PIGLIN, settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)SHAPES.get(state.get(FACING));
    }

    static {
        SHAPES = Maps.immutableEnumMap(Map.of(Direction.NORTH, Block.createCuboidShape(3.0D, 4.0D, 8.0D, 13.0D, 12.0D, 16.0D), Direction.SOUTH, Block.createCuboidShape(3.0D, 4.0D, 0.0D, 13.0D, 12.0D, 8.0D), Direction.EAST, Block.createCuboidShape(0.0D, 4.0D, 3.0D, 8.0D, 12.0D, 13.0D), Direction.WEST, Block.createCuboidShape(8.0D, 4.0D, 3.0D, 16.0D, 12.0D, 13.0D)));
    }
}
