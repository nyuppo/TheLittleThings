package com.nyuppo.block;

import com.nyuppo.TheLittleThings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableBlock extends Block implements Waterloggable {
    // 0 = NW, 1 = NE, 2 = SW, 3 = SE
    public static final BooleanProperty LEG_0;
    public static final BooleanProperty LEG_1;
    public static final BooleanProperty LEG_2;
    public static final BooleanProperty LEG_3;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape LEG_0_SHAPE = Block.createCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 13.0D, 2.0D);
    protected static final VoxelShape LEG_1_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 13.0D, 2.0D);
    protected static final VoxelShape LEG_2_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 14.0D, 2.0D, 13.0D, 16.0D);
    protected static final VoxelShape LEG_3_SHAPE = Block.createCuboidShape(14.0D, 0.0D, 14.0D, 16.0D, 13.0D, 16.0D);

    private static Map<BooleanProperty, Pair<Direction, Direction>> LEG_DIRECTIONS;

    public TableBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(LEG_0, true).with(LEG_1, true).with(LEG_2, true).with(LEG_3, true));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LEG_0, LEG_1, LEG_2, LEG_3);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        List<VoxelShape> shapesList = new ArrayList<VoxelShape>();

        if (state.get(LEG_0)) {
            shapesList.add(LEG_0_SHAPE);
        }
        if (state.get(LEG_1)) {
            shapesList.add(LEG_1_SHAPE);
        }
        if (state.get(LEG_2)) {
            shapesList.add(LEG_2_SHAPE);
        }
        if (state.get(LEG_3)) {
            shapesList.add(LEG_3_SHAPE);
        }
        VoxelShape shape = TOP_SHAPE;

        for (VoxelShape legShape : shapesList) {
            shape = VoxelShapes.union(shape, legShape);
        }

        return shape;
    }

    private BlockState getLegState(WorldAccess world, BlockState state, BlockPos pos) {
        BlockState newState = state;
        for (BooleanProperty leg : LEG_DIRECTIONS.keySet()) {
            Direction dir1 = LEG_DIRECTIONS.get(leg).getLeft();
            Direction dir2 = LEG_DIRECTIONS.get(leg).getRight();

            Block block1 = world.getBlockState(pos.offset(dir1)).getBlock();
            Block block2 = world.getBlockState(pos.offset(dir2)).getBlock();

            if (block1 instanceof TableBlock && block2 instanceof TableBlock) {
                if (world.getBlockState(pos.offset(dir1).offset(dir2)).getBlock() instanceof TableBlock) {
                    newState = newState.with(leg, false);
                } else {
                    newState = newState.with(leg, true);
                }

            } else if ((block1 instanceof TableBlock) != (block2 instanceof TableBlock)) {
                newState = newState.with(leg, false);
            } else {
                newState = newState.with(leg, true);
            }
        }

        return newState;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return this.getLegState(world, state, pos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();

        for (BooleanProperty leg : LEG_DIRECTIONS.keySet()) {
            Direction dir1 = LEG_DIRECTIONS.get(leg).getLeft();
            Direction dir2 = LEG_DIRECTIONS.get(leg).getRight();

            if (world.getBlockState(pos.offset(dir1).offset(dir2)).getBlock() instanceof TableBlock &&
                    world.getBlockState(pos.offset(dir1)).getBlock() instanceof TableBlock &&
                    world.getBlockState(pos.offset(dir2)).getBlock() instanceof TableBlock) {
                world.setBlockState(pos.offset(dir1).offset(dir2), world.getBlockState(pos.offset(dir1).offset(dir2)).with(getOppositeLeg(leg), false), Block.NOTIFY_ALL);
            }
        }

        return this.getLegState(world, state, pos);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
       super.onStateReplaced(state, world, pos, newState, moved);

        for (BooleanProperty leg : LEG_DIRECTIONS.keySet()) {
            Direction dir1 = LEG_DIRECTIONS.get(leg).getLeft();
            Direction dir2 = LEG_DIRECTIONS.get(leg).getRight();

            if (world.getBlockState(pos.offset(dir1).offset(dir2)).getBlock() instanceof TableBlock) {
                if (!(newState.getBlock() instanceof TableBlock)) {
                    if (world.getBlockState(pos.offset(Direction.NORTH)).getBlock() instanceof TableBlock &&
                            world.getBlockState(pos.offset(Direction.EAST)).getBlock() instanceof TableBlock &&
                            world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() instanceof TableBlock &&
                            world.getBlockState(pos.offset(Direction.WEST)).getBlock() instanceof TableBlock) {
                        world.setBlockState(pos.offset(dir1).offset(dir2), world.getBlockState(pos.offset(dir1).offset(dir2)).with(getOppositeLeg(leg), true), Block.NOTIFY_ALL);
                    }
                } else {
                    if (world.getBlockState(pos.offset(dir1)).getBlock() instanceof TableBlock &&
                            world.getBlockState(pos.offset(dir2)).getBlock() instanceof TableBlock) {
                        world.setBlockState(pos.offset(dir1).offset(dir2), world.getBlockState(pos.offset(dir1).offset(dir2)).with(getOppositeLeg(leg), false), Block.NOTIFY_ALL);
                    }
                }
            }
        }
    }

    private static BooleanProperty getOppositeLeg(BooleanProperty leg) {
        if (leg == LEG_0) {
            return LEG_2;
        } else if (leg == LEG_1) {
            return LEG_3;
        } else if (leg == LEG_2) {
            return LEG_0;
        } else if (leg == LEG_3) {
            return LEG_1;
        }
        return LEG_0;
    }

    static {
        LEG_0 = BooleanProperty.of("leg_0");
        LEG_1 = BooleanProperty.of("leg_1");
        LEG_2 = BooleanProperty.of("leg_2");
        LEG_3 = BooleanProperty.of("leg_3");
        LEG_DIRECTIONS = Map.of(
                LEG_0, new Pair<>(Direction.NORTH, Direction.EAST),
                LEG_1, new Pair<>(Direction.NORTH, Direction.WEST),
                LEG_2, new Pair<>(Direction.SOUTH, Direction.WEST),
                LEG_3, new Pair<>(Direction.SOUTH, Direction.EAST)
        );
    }
}
