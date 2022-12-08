package com.nyuppo;

import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.block.entity.ModBlockEntityType;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import com.nyuppo.registration.*;
import com.nyuppo.world.ModBiomes;
import com.nyuppo.world.ModWorldGen;
import com.nyuppo.world.feature.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TheLittleThings implements ModInitializer {
    public static final String MOD_ID = "thelittlethings";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("It's the little things that matter.");

        ModBlocks.registerBlocks();
        ModEntityTypes.registerEntityTypes();
        ModItems.registerItems();
        HatItems.registerHats();
        ModSoundEvents.registerSoundEvents();
        ModBlockEntityType.registerBlockEntityTypes();
        ModFeatures.init();
        ModWorldGen.init();
        ModBiomes.registerBiomes();
        SwapAzaleaWood.swapAzaleaWood();

        FallibleItemDispenserBehavior dispenserBehavior2 = new FallibleItemDispenserBehavior(){
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
                return stack;
            }
        };
        DispenserBlock.registerBehavior(ModItems.PIGLIN_HEAD, dispenserBehavior2);

        ServerPlayNetworking.registerGlobalReceiver(TheLittleThingsNetworkingConstants.getUpdateHangingSignPacketId(), ((server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                list.add(buf.readString());
            }

            player.updateLastActionTime();
            if (player instanceof ServerPlayerEntity) {
                if (player.world.isChunkLoaded(pos)) {
                    server.execute(() -> {
                        BlockState blockState = player.world.getBlockState(pos);
                        BlockEntity blockEntity = player.world.getBlockEntity(pos);
                        if (!(blockEntity instanceof HangingSignBlockEntity)) {
                            return;
                        }

                        HangingSignBlockEntity hangingSignBlockEntity = (HangingSignBlockEntity)blockEntity;
                        for (int i = 0; i < list.size(); ++i) {
                            hangingSignBlockEntity.setTextOnRow(i, Text.literal(list.get(i)));
                        }
                        hangingSignBlockEntity.markDirty();
                        player.world.updateListeners(pos, blockState, blockState, Block.NOTIFY_ALL);
                    });

                }
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(TheLittleThingsNetworkingConstants.getPincerPullPacketId(), ((server, player, handler, buf, responseSender) -> {
            UUID uuid = buf.readUuid();
            double d = buf.readDouble();
            double e = buf.readDouble();
            double f = buf.readDouble();

            Entity entity = server.getOverworld().getEntity(uuid);
            entity.setVelocity(new Vec3d(d, e + 2.0D, f).multiply(0.15D));
        }));
    }

    public static Identifier ID(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static String asStringID(String path) {
        return MOD_ID + ":" + path;
    }
}
