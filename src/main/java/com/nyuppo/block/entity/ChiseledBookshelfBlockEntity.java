package com.nyuppo.block.entity;

import com.mojang.logging.LogUtils;
import com.nyuppo.block.ChiseledBookshelfBlock;
import com.nyuppo.util.tags.ModItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Predicate;

public class ChiseledBookshelfBlockEntity extends BlockEntity implements Inventory {
    public static final int MAX_BOOKS = 6;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final DefaultedList<ItemStack> inventory;

    public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.CHISELED_BOOKSHELF, pos, state);
        this.inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    }

    private void updateState(int interactedSlot) {
        if (interactedSlot >= 0 && interactedSlot < 6) {
            BlockState blockState = (BlockState)this.getCachedState().with(ChiseledBookshelfBlock.LAST_INTERACTION_BOOK_SLOT, interactedSlot + 1);

            for(int i = 0; i < ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
                boolean bl = !this.getStack(i).isEmpty();
                BooleanProperty booleanProperty = (BooleanProperty)ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockState = (BlockState)blockState.with(booleanProperty, bl);
            }

            ((World) Objects.requireNonNull(this.world)).setBlockState(this.pos, blockState, 3);
        } else {
            LOGGER.error("Expected slot 0-5, got {}", interactedSlot);
        }
    }

    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
    }

    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory, true);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        return nbtCompound;
    }

    public int getOpenSlotCount() {
        return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    public void clear() {
        this.inventory.clear();
    }

    public int size() {
        return 6;
    }

    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getStack(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = (ItemStack)Objects.requireNonNullElse((ItemStack)this.inventory.get(slot), ItemStack.EMPTY);
        this.inventory.set(slot, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.updateState(slot);
        }

        return itemStack;
    }

    public ItemStack removeStack(int slot) {
        return this.removeStack(slot, 1);
    }

    public void setStack(int slot, ItemStack stack) {
        if (stack.isIn(ModItemTags.BOOKSHELF_BOOKS)) {
            this.inventory.set(slot, stack);
            this.updateState(slot);
        }

    }

    public int getMaxCountPerStack() {
        return 1;
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world == null) {
            return false;
        } else if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    public boolean isValid(int slot, ItemStack stack) {
        return stack.isIn(ModItemTags.BOOKSHELF_BOOKS) && this.getStack(slot).isEmpty();
    }
}
