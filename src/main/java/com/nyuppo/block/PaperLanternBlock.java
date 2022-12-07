package com.nyuppo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PaperLanternBlock extends LanternBlock {
    protected static final VoxelShape STANDING_SHAPE;
    protected static final VoxelShape HANGING_SHAPE;

    public PaperLanternBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (Boolean)state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    static {
        STANDING_SHAPE = VoxelShapes.union(Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 9.0D, 13.0D), Block.createCuboidShape(5.0D, 9.0D, 5.0D, 11.0D, 11.0D, 11.0D));
        HANGING_SHAPE = VoxelShapes.union(Block.createCuboidShape(3.0D, 3.0D, 3.0D, 13.0D, 12.0D, 13.0D), Block.createCuboidShape(5.0D, 12.0D, 5.0D, 11.0D, 14.0D, 11.0D));
    }
}
