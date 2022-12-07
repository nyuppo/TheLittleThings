package com.nyuppo.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class GravestoneBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE_NS = Block.createCuboidShape(3.0, 0.0, 6.0, 13.0, 14.0, 10.0);
    private static final VoxelShape SHAPE_WE = Block.createCuboidShape(6.0, 0.0, 3.0, 10.0, 14.0, 13.0);

    public GravestoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        if (dir == Direction.WEST || dir == Direction.EAST) {
            return SHAPE_WE;
        }
        return SHAPE_NS;
    }

    /*
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GravestoneBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (player.isSneaking()) {
            if (blockEntity instanceof GravestoneBlockEntity) {
                GravestoneBlockEntity gravestoneBlockEntity = (GravestoneBlockEntity)blockEntity;
                DefaultedList<ItemStack> temp = DefaultedList.ofSize(36 + 4 + 1, ItemStack.EMPTY);
                for (int i = 0; i < player.getInventory().main.size(); i++) {
                    temp.set(i, player.getInventory().main.get(i));
                }
                for (int i = 0; i < player.getInventory().armor.size(); i++) {
                    temp.set(i, player.getInventory().armor.get(i + 36));
                }
                for (int i = 0; i < player.getInventory().offHand.size(); i++) {
                    temp.set(i, player.getInventory().offHand.get(i + 40));
                }
                gravestoneBlockEntity.setItems(temp);
            }
        } else {
            player.openHandledScreen((GravestoneBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }
    */

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

}
