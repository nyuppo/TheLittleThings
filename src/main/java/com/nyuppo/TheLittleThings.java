package com.nyuppo;

import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.block.entity.ModBlockEntityType;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.mixin.ArmorStandEntityAccessor;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import com.nyuppo.registration.*;
import com.nyuppo.world.ModBiomes;
import com.nyuppo.world.ModWorldGen;
import com.nyuppo.world.feature.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;

import java.util.ArrayList;
import java.util.Iterator;
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
        ModStats.registerStats();

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

        UseBlockCallback.EVENT.register(this::onUseBlock);
    }

    public static Identifier ID(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static String asStringID(String path) {
        return MOD_ID + ":" + path;
    }

    private ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult result) {
        ItemStack stack = player.getStackInHand(hand);
        BlockPos pos = result.getBlockPos();

        // Stair chairs
        boolean sitSuccess = sit(player, world, hand, result);
        if (sitSuccess) {
            return ActionResult.SUCCESS; // SUCCESS
        }

        return ActionResult.PASS; // PASS
    }

    private boolean sit(PlayerEntity player, World world, Hand hand, BlockHitResult result) {
        if (hand == Hand.OFF_HAND) {
            return false;
        }

        if (!player.getStackInHand(hand).isEmpty()) {
            return false;
        }

        if (world.isClient) {
            return false;
        }

        if (player.getVehicle() != null) {
            return false;
        }

        if (player.isSneaking()) {
            return false;
        }

        if (player.isSpectator()) {
            return false;
        }

        BlockPos pos = result.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Direction dir = result.getSide();

        Box box = new Box(pos);
        List<ArmorStandEntity> standList = world.getNonSpectatingEntities(ArmorStandEntity.class, box);
        if (!standList.isEmpty()) {
            Iterator<ArmorStandEntity> iterator = standList.iterator();
            ArmorStandEntity tempArmorStandEntity;
            while (iterator.hasNext()) {
                tempArmorStandEntity = iterator.next();
                if (tempArmorStandEntity.hasCustomName() && tempArmorStandEntity.getCustomName().getString() == "_seat") {
                    return false;
                }
            }
        }

        // If we right click bottom of block or if block isn't stairs, return false
        if (dir == Direction.DOWN || !(state.getBlock() instanceof StairsBlock) || (world.getBlockState(pos.up()).isOpaque())) { // ||  // !(world.getBlockState(pos.up()).isAir())
            return false;
        }

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            ArmorStandEntity armorStandEntity = (ArmorStandEntity) EntityType.ARMOR_STAND.create(serverWorld, (NbtCompound)null, (Text)null, player, pos, SpawnReason.COMMAND, true, true);
            if (armorStandEntity == null) {
                return false;
            }

            if (state.get(StairsBlock.SHAPE) != StairShape.STRAIGHT || state.get(StairsBlock.HALF) != BlockHalf.BOTTOM) {
                return false;
            }

            float yaw = 0F;
            Direction stairsDir = state.get(StairsBlock.FACING);
            if (stairsDir == Direction.SOUTH) {
                yaw = 180F;
            } else if (stairsDir == Direction.WEST) {
                yaw = -90F;
            } else if (stairsDir == Direction.EAST) {
                yaw = 90F;
            }

            armorStandEntity.setNoGravity(true);
            armorStandEntity.refreshPositionAndAngles(armorStandEntity.getX(), armorStandEntity.getY() - 1.5D, armorStandEntity.getZ(), yaw, 0.0F);
            player.setYaw(yaw);
            ((ArmorStandEntityAccessor)armorStandEntity).cSetSmall(true);
            armorStandEntity.setInvulnerable(true);
            armorStandEntity.setInvisible(true);
            armorStandEntity.setCustomNameVisible(false);
            armorStandEntity.setCustomName(Text.literal("_seat"));
            serverWorld.spawnEntity(armorStandEntity);
            player.startRiding(armorStandEntity, true);
            return true;
        } else {
            return false;
        }
    }
}
