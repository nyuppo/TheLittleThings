package com.nyuppo.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class ModFenceGateBlock extends HorizontalFacingBlock {
    public static final BooleanProperty OPEN;
    public static final BooleanProperty POWERED;
    public static final BooleanProperty IN_WALL;
    protected static final VoxelShape Z_AXIS_SHAPE;
    protected static final VoxelShape X_AXIS_SHAPE;
    protected static final VoxelShape IN_WALL_Z_AXIS_SHAPE;
    protected static final VoxelShape IN_WALL_X_AXIS_SHAPE;
    protected static final VoxelShape Z_AXIS_COLLISION_SHAPE;
    protected static final VoxelShape X_AXIS_COLLISION_SHAPE;
    protected static final VoxelShape Z_AXIS_CULL_SHAPE;
    protected static final VoxelShape X_AXIS_CULL_SHAPE;
    protected static final VoxelShape IN_WALL_Z_AXIS_CULL_SHAPE;
    protected static final VoxelShape IN_WALL_X_AXIS_CULL_SHAPE;
    private final SoundEvent closeSound;
    private final SoundEvent openSound;

    public ModFenceGateBlock(Settings settings, SoundEvent closeSound, SoundEvent openSound) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(OPEN, false)).with(POWERED, false)).with(IN_WALL, false));
        this.closeSound = closeSound;
        this.openSound = openSound;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if ((Boolean)state.get(IN_WALL)) {
            return ((Direction)state.get(FACING)).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_SHAPE : IN_WALL_Z_AXIS_SHAPE;
        } else {
            return ((Direction)state.get(FACING)).getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = direction.getAxis();
        if (((Direction)state.get(FACING)).rotateYClockwise().getAxis() != axis) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            boolean bl = this.isWall(neighborState) || this.isWall(world.getBlockState(pos.offset(direction.getOpposite())));
            return (BlockState)state.with(IN_WALL, bl);
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if ((Boolean)state.get(OPEN)) {
            return VoxelShapes.empty();
        } else {
            return ((Direction)state.get(FACING)).getAxis() == Direction.Axis.Z ? Z_AXIS_COLLISION_SHAPE : X_AXIS_COLLISION_SHAPE;
        }
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        if ((Boolean)state.get(IN_WALL)) {
            return ((Direction)state.get(FACING)).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_CULL_SHAPE : IN_WALL_Z_AXIS_CULL_SHAPE;
        } else {
            return ((Direction)state.get(FACING)).getAxis() == Direction.Axis.X ? X_AXIS_CULL_SHAPE : Z_AXIS_CULL_SHAPE;
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch(type) {
            case LAND:
                return (Boolean)state.get(OPEN);
            case WATER:
                return false;
            case AIR:
                return (Boolean)state.get(OPEN);
            default:
                return false;
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        boolean bl = world.isReceivingRedstonePower(blockPos);
        Direction direction = ctx.getPlayerFacing();
        Direction.Axis axis = direction.getAxis();
        boolean bl2 = axis == Direction.Axis.Z && (this.isWall(world.getBlockState(blockPos.west())) || this.isWall(world.getBlockState(blockPos.east()))) || axis == Direction.Axis.X && (this.isWall(world.getBlockState(blockPos.north())) || this.isWall(world.getBlockState(blockPos.south())));
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, direction)).with(OPEN, bl)).with(POWERED, bl)).with(IN_WALL, bl2);
    }

    private boolean isWall(BlockState state) {
        return state.isIn(BlockTags.WALLS);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if ((Boolean)state.get(OPEN)) {
            state = (BlockState)state.with(OPEN, false);
            world.setBlockState(pos, state, 10);
        } else {
            Direction direction = player.getHorizontalFacing();
            if (state.get(FACING) == direction.getOpposite()) {
                state = (BlockState)state.with(FACING, direction);
            }

            state = (BlockState)state.with(OPEN, true);
            world.setBlockState(pos, state, 10);
        }

        boolean bl = (Boolean)state.get(OPEN);
        world.playSound(null, pos, bl ? this.openSound : this.closeSound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.playSound(null, pos, bl ? SoundEvents.BLOCK_FENCE_GATE_OPEN : SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 0.35F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = world.isReceivingRedstonePower(pos);
            if ((Boolean)state.get(POWERED) != bl) {
                world.setBlockState(pos, (BlockState)((BlockState)state.with(POWERED, bl)).with(OPEN, bl), 2);
                if ((Boolean)state.get(OPEN) != bl) {
                    world.playSound(null, pos, bl ? this.openSound : this.closeSound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
                    world.playSound(null, pos, bl ? SoundEvents.BLOCK_FENCE_GATE_OPEN : SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 0.35F, world.getRandom().nextFloat() * 0.1F + 0.9F);
                    world.emitGameEvent((Entity)null, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
            }

        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, OPEN, POWERED, IN_WALL});
    }

    public static boolean canWallConnect(BlockState state, Direction side) {
        return ((Direction)state.get(FACING)).getAxis() == side.rotateYClockwise().getAxis();
    }

    static {
        OPEN = Properties.OPEN;
        POWERED = Properties.POWERED;
        IN_WALL = Properties.IN_WALL;
        Z_AXIS_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
        X_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
        IN_WALL_Z_AXIS_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 13.0D, 10.0D);
        IN_WALL_X_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 13.0D, 16.0D);
        Z_AXIS_COLLISION_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 24.0D, 10.0D);
        X_AXIS_COLLISION_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 24.0D, 16.0D);
        Z_AXIS_CULL_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 5.0D, 7.0D, 2.0D, 16.0D, 9.0D), Block.createCuboidShape(14.0D, 5.0D, 7.0D, 16.0D, 16.0D, 9.0D));
        X_AXIS_CULL_SHAPE = VoxelShapes.union(Block.createCuboidShape(7.0D, 5.0D, 0.0D, 9.0D, 16.0D, 2.0D), Block.createCuboidShape(7.0D, 5.0D, 14.0D, 9.0D, 16.0D, 16.0D));
        IN_WALL_Z_AXIS_CULL_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 2.0D, 7.0D, 2.0D, 13.0D, 9.0D), Block.createCuboidShape(14.0D, 2.0D, 7.0D, 16.0D, 13.0D, 9.0D));
        IN_WALL_X_AXIS_CULL_SHAPE = VoxelShapes.union(Block.createCuboidShape(7.0D, 2.0D, 0.0D, 9.0D, 13.0D, 2.0D), Block.createCuboidShape(7.0D, 2.0D, 14.0D, 9.0D, 13.0D, 16.0D));
    }
}
