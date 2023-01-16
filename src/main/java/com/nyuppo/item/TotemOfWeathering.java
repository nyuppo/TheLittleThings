package com.nyuppo.item;

import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TotemOfWeathering extends Item {
    public TotemOfWeathering(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld)world;
            if (world.isRaining() || world.isThundering()) {
                serverWorld.setWeather(6000, 0, false, false);
            } else {
                serverWorld.setWeather(0, 6000, true, serverWorld.random.nextInt(5) == 0);
            }

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeItemStack(itemStack.copy());

            ServerPlayerEntity serverUser = (ServerPlayerEntity)user;
            ServerPlayNetworking.send(serverUser, TheLittleThingsNetworkingConstants.getUseTotemOfWeatheringPacketId(), buf);
        }

        world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0f, 1.0f, false);

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, 100);

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
