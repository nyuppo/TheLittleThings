package com.nyuppo.item;

import com.nyuppo.block.WallHangingSignBlock;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class HangingSignItem extends VerticallyAttachableBlockItem {
    public HangingSignItem(Block hangingSign, Block wallHangingSign, Settings settings) {
        super(hangingSign, wallHangingSign, settings, Direction.UP);
    }

    protected boolean canPlaceAt(WorldView world, BlockState state, BlockPos pos) {
        Block var5 = state.getBlock();
        if (var5 instanceof WallHangingSignBlock) {
            WallHangingSignBlock wallHangingSignBlock = (WallHangingSignBlock)var5;
            if (!wallHangingSignBlock.canAttachAt(state, world, pos)) {
                return false;
            }
        }

        return super.canPlaceAt(world, state, pos);
    }

    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean bl = super.postPlacement(pos, world, player, stack, state);
        if (!world.isClient && !bl && player != null) {
            BlockEntity var8 = world.getBlockEntity(pos);
            if (var8 instanceof HangingSignBlockEntity) {
                HangingSignBlockEntity signBlockEntity = (HangingSignBlockEntity)var8;

                ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(signBlockEntity.getPos());
                ServerPlayNetworking.send(serverPlayer, TheLittleThingsNetworkingConstants.getHangingSignScreenPacketId(), buf);
            }
        }

        return bl;
    }
}
