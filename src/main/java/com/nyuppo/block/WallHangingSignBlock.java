package com.nyuppo.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.nyuppo.TheLittleThings;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.util.tags.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallHangingSignBlock extends AbstractHangingSignBlock {
    public static final DirectionProperty FACING;
    public static final VoxelShape NORTH_SOUTH_COLLISION_SHAPE;
    public static final VoxelShape EAST_WEST_COLLISION_SHAPE;
    public static final VoxelShape NORTH_SOUTH_SHAPE;
    public static final VoxelShape EAST_WEST_SHAPE;
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES;

    public WallHangingSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(WATERLOGGED, false));
    }

    /*
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity var8 = world.getBlockEntity(pos);
        if (var8 instanceof SignBlockEntity) {
            SignBlockEntity signBlockEntity = (SignBlockEntity)var8;
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() instanceof BlockItem) { // !signBlockEntity.shouldRunCommand(player) &&
                return ActionResult.PASS;
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

     */

    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)OUTLINE_SHAPES.get(state.get(FACING));
    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return this.getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch((Direction)state.get(FACING)) {
            case EAST:
            case WEST:
                return EAST_WEST_COLLISION_SHAPE;
            default:
                return NORTH_SOUTH_COLLISION_SHAPE;
        }
    }

    public boolean canAttachAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = ((Direction)state.get(FACING)).rotateYClockwise();
        Direction direction2 = ((Direction)state.get(FACING)).rotateYCounterclockwise();
        return this.canAttachTo(world, state, pos.offset(direction), direction2) || this.canAttachTo(world, state, pos.offset(direction2), direction);
    }

    public boolean canAttachTo(WorldView world, BlockState state, BlockPos toPos, Direction direction) {
        BlockState blockState = world.getBlockState(toPos);
        return blockState.isIn(ModBlockTags.WALL_HANGING_SIGNS) ? (blockState.get(FACING)).getAxis().test(state.get(FACING)) : blockState.isSideSolid(world, toPos, direction, SideShapeType.FULL);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction[] var6 = ctx.getPlacementDirections();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            if (direction.getAxis().isHorizontal() && !direction.getAxis().test(ctx.getSide())) {
                Direction direction2 = direction.getOpposite();
                blockState = (BlockState)blockState.with(FACING, direction2);
                if (blockState.canPlaceAt(worldView, blockPos) && this.canAttachAt(blockState, worldView, blockPos)) {
                    return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getAxis() == ((Direction)state.get(FACING)).rotateYClockwise().getAxis() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, WATERLOGGED});
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignBlockEntity(pos, state);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public final Identifier getLootTableId() {
        Identifier correctedLootTableId = TheLittleThings.ID("blocks/" + this.getSignType().getName() + "_hanging_sign");
        if (this.lootTableId != correctedLootTableId) {
            this.lootTableId = correctedLootTableId;
        }

        return this.lootTableId;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        NORTH_SOUTH_COLLISION_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 6.0D, 16.0D, 16.0D, 10.0D);
        EAST_WEST_COLLISION_SHAPE = Block.createCuboidShape(6.0D, 14.0D, 0.0D, 10.0D, 16.0D, 16.0D);
        NORTH_SOUTH_SHAPE = VoxelShapes.union(NORTH_SOUTH_COLLISION_SHAPE, Block.createCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D));
        EAST_WEST_SHAPE = VoxelShapes.union(EAST_WEST_COLLISION_SHAPE, Block.createCuboidShape(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D));
        OUTLINE_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH_SOUTH_SHAPE, Direction.SOUTH, NORTH_SOUTH_SHAPE, Direction.EAST, EAST_WEST_SHAPE, Direction.WEST, EAST_WEST_SHAPE));
    }
}
