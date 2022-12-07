package com.nyuppo.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModButtonBlock extends WallMountedBlock {
    private final SoundEvent offSound;
    private final SoundEvent onSound;
    public static final BooleanProperty POWERED;
    private static final int field_31040 = 1;
    private static final int field_31041 = 2;
    protected static final int field_31042 = 2;
    protected static final int field_31043 = 3;
    protected static final VoxelShape CEILING_X_SHAPE;
    protected static final VoxelShape CEILING_Z_SHAPE;
    protected static final VoxelShape FLOOR_X_SHAPE;
    protected static final VoxelShape FLOOR_Z_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape CEILING_X_PRESSED_SHAPE;
    protected static final VoxelShape CEILING_Z_PRESSED_SHAPE;
    protected static final VoxelShape FLOOR_X_PRESSED_SHAPE;
    protected static final VoxelShape FLOOR_Z_PRESSED_SHAPE;
    protected static final VoxelShape NORTH_PRESSED_SHAPE;
    protected static final VoxelShape SOUTH_PRESSED_SHAPE;
    protected static final VoxelShape WEST_PRESSED_SHAPE;
    protected static final VoxelShape EAST_PRESSED_SHAPE;
    private final boolean wooden;

    public ModButtonBlock(boolean wooden, Settings settings, SoundEvent offSound, SoundEvent onSound) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(POWERED, false)).with(FACE, WallMountLocation.WALL));
        this.wooden = wooden;
        this.offSound = offSound;
        this.onSound = onSound;
    }

    private int getPressTicks() {
        return this.wooden ? 30 : 20;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction)state.get(FACING);
        boolean bl = (Boolean)state.get(POWERED);
        switch((WallMountLocation)state.get(FACE)) {
            case FLOOR:
                if (direction.getAxis() == Direction.Axis.X) {
                    return bl ? FLOOR_X_PRESSED_SHAPE : FLOOR_X_SHAPE;
                }

                return bl ? FLOOR_Z_PRESSED_SHAPE : FLOOR_Z_SHAPE;
            case WALL:
                switch(direction) {
                    case EAST:
                        return bl ? EAST_PRESSED_SHAPE : EAST_SHAPE;
                    case WEST:
                        return bl ? WEST_PRESSED_SHAPE : WEST_SHAPE;
                    case SOUTH:
                        return bl ? SOUTH_PRESSED_SHAPE : SOUTH_SHAPE;
                    case NORTH:
                    default:
                        return bl ? NORTH_PRESSED_SHAPE : NORTH_SHAPE;
                }
            case CEILING:
            default:
                if (direction.getAxis() == Direction.Axis.X) {
                    return bl ? CEILING_X_PRESSED_SHAPE : CEILING_X_SHAPE;
                } else {
                    return bl ? CEILING_Z_PRESSED_SHAPE : CEILING_Z_SHAPE;
                }
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if ((Boolean)state.get(POWERED)) {
            return ActionResult.CONSUME;
        } else {
            this.powerOn(state, world, pos);
            this.playClickSound(player, world, pos, true);
            world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
            return ActionResult.success(world.isClient);
        }
    }

    public void powerOn(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, (BlockState)state.with(POWERED, true), 3);
        this.updateNeighbors(state, world, pos);
        world.createAndScheduleBlockTick(pos, this, this.getPressTicks());
    }

    protected void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered) {
        world.playSound(powered ? player : null, pos, this.getClickSound(powered), SoundCategory.BLOCKS, 0.3F, powered ? 0.7F : 0.5F);
        world.playSound(powered ? player : null, pos, powered ? (this.wooden ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON) : (this.wooden ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF), SoundCategory.BLOCKS, 0.2F, powered ? 0.6F : 0.5F);
    }

    protected SoundEvent getClickSound(boolean powered) {
        return powered ? this.onSound : this.offSound;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            if ((Boolean)state.get(POWERED)) {
                this.updateNeighbors(state, world, pos);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (Boolean)state.get(POWERED) ? 15 : 0;
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (Boolean)state.get(POWERED) && getDirection(state) == direction ? 15 : 0;
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(POWERED)) {
            if (this.wooden) {
                this.tryPowerWithProjectiles(state, world, pos);
            } else {
                world.setBlockState(pos, (BlockState)state.with(POWERED, false), 3);
                this.updateNeighbors(state, world, pos);
                this.playClickSound((PlayerEntity)null, world, pos, false);
                world.emitGameEvent((Entity)null, GameEvent.BLOCK_DEACTIVATE, pos);
            }

        }
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && this.wooden && !(Boolean)state.get(POWERED)) {
            this.tryPowerWithProjectiles(state, world, pos);
        }
    }

    private void tryPowerWithProjectiles(BlockState state, World world, BlockPos pos) {
        List<? extends Entity> list = world.getNonSpectatingEntities(PersistentProjectileEntity.class, state.getOutlineShape(world, pos).getBoundingBox().offset(pos));
        boolean bl = !list.isEmpty();
        boolean bl2 = (Boolean)state.get(POWERED);
        if (bl != bl2) {
            world.setBlockState(pos, (BlockState)state.with(POWERED, bl), 3);
            this.updateNeighbors(state, world, pos);
            this.playClickSound((PlayerEntity)null, world, pos, bl);
            world.emitGameEvent((Entity)list.stream().findFirst().orElse(null), bl ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        }

        if (bl) {
            world.createAndScheduleBlockTick(new BlockPos(pos), this, this.getPressTicks());
        }

    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED, FACE});
    }

    static {
        POWERED = Properties.POWERED;
        CEILING_X_SHAPE = Block.createCuboidShape(6.0D, 14.0D, 5.0D, 10.0D, 16.0D, 11.0D);
        CEILING_Z_SHAPE = Block.createCuboidShape(5.0D, 14.0D, 6.0D, 11.0D, 16.0D, 10.0D);
        FLOOR_X_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 5.0D, 10.0D, 2.0D, 11.0D);
        FLOOR_Z_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 6.0D, 11.0D, 2.0D, 10.0D);
        NORTH_SHAPE = Block.createCuboidShape(5.0D, 6.0D, 14.0D, 11.0D, 10.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 2.0D);
        WEST_SHAPE = Block.createCuboidShape(14.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
        EAST_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 5.0D, 2.0D, 10.0D, 11.0D);
        CEILING_X_PRESSED_SHAPE = Block.createCuboidShape(6.0D, 15.0D, 5.0D, 10.0D, 16.0D, 11.0D);
        CEILING_Z_PRESSED_SHAPE = Block.createCuboidShape(5.0D, 15.0D, 6.0D, 11.0D, 16.0D, 10.0D);
        FLOOR_X_PRESSED_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 5.0D, 10.0D, 1.0D, 11.0D);
        FLOOR_Z_PRESSED_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 6.0D, 11.0D, 1.0D, 10.0D);
        NORTH_PRESSED_SHAPE = Block.createCuboidShape(5.0D, 6.0D, 15.0D, 11.0D, 10.0D, 16.0D);
        SOUTH_PRESSED_SHAPE = Block.createCuboidShape(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 1.0D);
        WEST_PRESSED_SHAPE = Block.createCuboidShape(15.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
        EAST_PRESSED_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 5.0D, 1.0D, 10.0D, 11.0D);
    }
}
