package com.nyuppo.block;

import com.nyuppo.TheLittleThings;
import com.nyuppo.block.entity.ChiseledBookshelfBlockEntity;
import com.nyuppo.registration.ModSoundEvents;
import com.nyuppo.util.tags.ModItemTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChiseledBookshelfBlock extends BlockWithEntity {
    private static final int MAX_BOOK_COUNT = 6;
    public static final int BOOK_HEIGHT = 3;
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES;
    public static final BooleanProperty SLOT_0_OCCUPIED;
    public static final BooleanProperty SLOT_1_OCCUPIED;
    public static final BooleanProperty SLOT_2_OCCUPIED;
    public static final BooleanProperty SLOT_3_OCCUPIED;
    public static final BooleanProperty SLOT_4_OCCUPIED;
    public static final BooleanProperty SLOT_5_OCCUPIED;
    public static final IntProperty LAST_INTERACTION_BOOK_SLOT;

    public ChiseledBookshelfBlock(Settings settings) {
        super(settings);
        BlockState blockState = (BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HorizontalFacingBlock.FACING, Direction.NORTH)).with(LAST_INTERACTION_BOOK_SLOT, 0);

        BooleanProperty booleanProperty;
        for(Iterator var3 = SLOT_OCCUPIED_PROPERTIES.iterator(); var3.hasNext(); blockState = (BlockState)blockState.with(booleanProperty, false)) {
            booleanProperty = (BooleanProperty)var3.next();
        }

        this.setDefaultState(blockState);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity var8 = world.getBlockEntity(pos);
        if (var8 instanceof ChiseledBookshelfBlockEntity) {
            ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity = (ChiseledBookshelfBlockEntity)var8;
            Optional optional = getHitPos(hit, (Direction)state.get(HorizontalFacingBlock.FACING));
            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else if (world.isClient()) {
                return ActionResult.SUCCESS;
            } else {
                int i = getSlotForHitPos((Vec2f)optional.get());
                ItemStack itemStack = player.getStackInHand(hand);
                return itemStack.isIn(ModItemTags.BOOKSHELF_BOOKS) ? tryAddBook(world, pos, player, chiseledBookshelfBlockEntity, itemStack, i) : tryRemoveBook(world, pos, player, chiseledBookshelfBlockEntity, i);
            }
        } else {
            return ActionResult.PASS;
        }
    }

    private static Optional<Vec2f> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getSide();
        if (facing != direction) {
            return Optional.empty();
        } else {
            BlockPos blockPos = hit.getBlockPos().offset(direction);
            Vec3d vec3d = hit.getPos().subtract((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
            double d = vec3d.getX();
            double e = vec3d.getY();
            double f = vec3d.getZ();
            Optional var10000;
            switch(direction) {
                case NORTH:
                    var10000 = Optional.of(new Vec2f((float)(1.0D - d), (float)e));
                    break;
                case SOUTH:
                    var10000 = Optional.of(new Vec2f((float)d, (float)e));
                    break;
                case WEST:
                    var10000 = Optional.of(new Vec2f((float)f, (float)e));
                    break;
                case EAST:
                    var10000 = Optional.of(new Vec2f((float)(1.0D - f), (float)e));
                    break;
                case DOWN:
                case UP:
                    var10000 = Optional.empty();
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return var10000;
        }
    }

    private static int getSlotForHitPos(Vec2f hitPos) {
        int i = hitPos.y >= 0.5F ? 0 : 1;
        int j = getColumn(hitPos.x);
        return j + i * 3;
    }

    private static int getColumn(float x) {
        float f = 0.0625F;
        float g = 0.375F;
        if (x < 0.375F) {
            return 0;
        } else {
            float h = 0.6875F;
            return x < 0.6875F ? 1 : 2;
        }
    }

    private static ActionResult tryAddBook(World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, ItemStack stack, int slot) {
        ActionResult actionResult = ActionResult.CONSUME;
        if (!blockEntity.getStack(slot).isEmpty()) {
            return actionResult;
        } else {
            SoundEvent soundEvent = stack.isOf(Items.ENCHANTED_BOOK) ? ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT_ENCHANTED : ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT;
            blockEntity.setStack(slot, stack.split(1));
            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (player.isCreative()) {
                stack.increment(1);
            }

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return actionResult;
        }
    }

    private static ActionResult tryRemoveBook(World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, int slot) {
        ActionResult actionResult = ActionResult.CONSUME;
        ItemStack itemStack = blockEntity.removeStack(slot, 1);
        if (itemStack.isEmpty()) {
            return actionResult;
        } else {
            SoundEvent soundEvent = itemStack.isOf(Items.ENCHANTED_BOOK) ? ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP_ENCHANTED : ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP;
            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().insertStack(itemStack)) {
                player.dropItem(itemStack, false);
            }

            return actionResult;
        }
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChiseledBookshelfBlockEntity(pos, state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LAST_INTERACTION_BOOK_SLOT}).add(new Property[]{HorizontalFacingBlock.FACING});
        List<BooleanProperty> occupieds = SLOT_OCCUPIED_PROPERTIES;
        Objects.requireNonNull(builder);
        occupieds.forEach((property) -> {
            builder.add(new Property[]{property});
        });
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChiseledBookshelfBlockEntity) {
                ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity = (ChiseledBookshelfBlockEntity)blockEntity;
                if (!chiseledBookshelfBlockEntity.isEmpty()) {
                    for(int i = 0; i < 6; ++i) {
                        ItemStack itemStack = chiseledBookshelfBlockEntity.getStack(i);
                        if (!itemStack.isEmpty()) {
                            ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
                        }
                    }

                    chiseledBookshelfBlockEntity.clear();
                    world.updateComparators(pos, this);
                }
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getPlayerFacing().getOpposite());
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return (Integer)state.get(LAST_INTERACTION_BOOK_SLOT);
    }

    static {
        LAST_INTERACTION_BOOK_SLOT = IntProperty.of("last_interaction_book_slot", 0, 6);
        SLOT_0_OCCUPIED = BooleanProperty.of("slot_0_occupied");
        SLOT_1_OCCUPIED = BooleanProperty.of("slot_1_occupied");
        SLOT_2_OCCUPIED = BooleanProperty.of("slot_2_occupied");
        SLOT_3_OCCUPIED = BooleanProperty.of("slot_3_occupied");
        SLOT_4_OCCUPIED = BooleanProperty.of("slot_4_occupied");
        SLOT_5_OCCUPIED = BooleanProperty.of("slot_5_occupied");
        SLOT_OCCUPIED_PROPERTIES = List.of(SLOT_0_OCCUPIED, SLOT_1_OCCUPIED, SLOT_2_OCCUPIED, SLOT_3_OCCUPIED, SLOT_4_OCCUPIED, SLOT_5_OCCUPIED);
    }
}
