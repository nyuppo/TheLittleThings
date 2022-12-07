package com.nyuppo.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.util.RotationPropertyHelper;
import com.nyuppo.util.tags.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.Optional;

public class HangingSignBlock extends AbstractHangingSignBlock {
    public static final IntProperty ROTATION;
    public static final BooleanProperty ATTACHED;
    protected static final float field_40302 = 5.0F;
    protected static final VoxelShape DEFAULT_SHAPE;
    private static final Map<Integer, VoxelShape> SHAPES_FOR_ROTATION;

    public HangingSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(ROTATION, 0)).with(ATTACHED, false)).with(WATERLOGGED, false));
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

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isSideSolid(world, pos.up(), Direction.DOWN, SideShapeType.CENTER);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        FluidState fluidState = world.getFluidState(ctx.getBlockPos());
        BlockPos blockPos = ctx.getBlockPos().up();
        BlockState blockState = world.getBlockState(blockPos);
        boolean bl = blockState.isIn(ModBlockTags.ALL_HANGING_SIGNS);
        Direction direction = Direction.fromRotation((double)ctx.getPlayerYaw());
        boolean bl2 = !Block.isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.DOWN) || ctx.shouldCancelInteraction();
        if (bl && !ctx.shouldCancelInteraction()) {
            if (blockState.contains(WallHangingSignBlock.FACING)) {
                Direction direction2 = (Direction)blockState.get(WallHangingSignBlock.FACING);
                if (direction2.getAxis().test(direction)) {
                    bl2 = false;
                }
            } else if (blockState.contains(ROTATION)) {
                Optional<Direction> optional = RotationPropertyHelper.toDirection((Integer)blockState.get(ROTATION));
                if (optional.isPresent() && ((Direction)optional.get()).getAxis().test(direction)) {
                    bl2 = false;
                }
            }
        }

        int i = !bl2 ? RotationPropertyHelper.fromDirection(direction) : RotationPropertyHelper.fromYaw(ctx.getPlayerYaw());
        return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(ATTACHED, bl2)).with(ROTATION, i)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape voxelShape = (VoxelShape)SHAPES_FOR_ROTATION.get(state.get(ROTATION));
        return voxelShape == null ? DEFAULT_SHAPE : voxelShape;
    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return this.getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(ROTATION, rotation.rotate((Integer)state.get(ROTATION), 16));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(ROTATION, mirror.mirror((Integer)state.get(ROTATION), 16));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{ROTATION, ATTACHED, WATERLOGGED});
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignBlockEntity(pos, state);
    }

    static {
        ROTATION = Properties.ROTATION;
        ATTACHED = Properties.ATTACHED;
        DEFAULT_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        SHAPES_FOR_ROTATION = Maps.newHashMap(ImmutableMap.of(0, Block.createCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D), 4, Block.createCuboidShape(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D), 8, Block.createCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D), 12, Block.createCuboidShape(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D)));
    }
}
