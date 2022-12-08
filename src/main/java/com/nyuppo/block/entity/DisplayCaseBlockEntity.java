package com.nyuppo.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class DisplayCaseBlockEntity extends BlockEntity {
    private ItemStack stack;

    public DisplayCaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DISPLAY_CASE, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getItem() {
        return this.stack;
    }

    public boolean setItem(ItemStack stack, boolean shouldDecrement) {
        if (stack.isEmpty()) {
            return false;
        }

        this.stack = stack.copy();
        this.stack.setCount(1);
        if (shouldDecrement) {
            stack.decrement(1);
        }

        this.updateListeners();

        return true;
    }

    public void dropItem() {
        ItemScatterer.spawn(this.world, (double)this.getPos().getX() + 0.5D, (double)this.getPos().getY() + 0.5D, (double)this.pos.getZ() + 0.5D, this.stack);
        this.stack = ItemStack.EMPTY;
        this.updateListeners();
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.stack.isEmpty()) {
            nbt.put("Item", this.stack.writeNbt(new NbtCompound()));
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.stack = ItemStack.fromNbt(nbt.getCompound("Item"));
    }

    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Item", this.stack.writeNbt(new NbtCompound()));
        return nbtCompound;
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


}
