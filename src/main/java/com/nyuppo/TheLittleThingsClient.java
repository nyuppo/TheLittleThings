package com.nyuppo;

import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.block.entity.ModBlockEntityType;
import com.nyuppo.client.gui.HangingSignEditScreen;
import com.nyuppo.client.render.block.DisplayCaseBlockRenderer;
import com.nyuppo.client.render.block.HangingSignBlockEntityRenderer;
import com.nyuppo.client.render.block.ModSkullBlockEntityRenderer;
import com.nyuppo.client.render.entity.CamelEntityRenderer;
import com.nyuppo.client.render.entity.ModEntityModelLayers;
import com.nyuppo.client.render.entity.RaftEntityRenderer;
import com.nyuppo.client.render.entity.model.CamelEntityModel;
import com.nyuppo.client.render.entity.model.ChestRaftEntityModel;
import com.nyuppo.client.render.entity.model.PiglinHeadEntityModel;
import com.nyuppo.client.render.entity.model.RaftEntityModel;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.entity.passive.renderer.CrabRenderer;
import com.nyuppo.entity.passive.renderer.PenguinRenderer;
import com.nyuppo.entity.vehicle.RaftEntity;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import com.nyuppo.registration.ModBlocks;
import com.nyuppo.registration.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class TheLittleThingsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(TheLittleThings.MOD_ID + "client");

    @Override
    public void onInitializeClient() {
        for (RaftEntity.Type type : RaftEntity.Type.values()) {
            EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.createRaft(type), RaftEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.createChestRaft(type), ChestRaftEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PIGLIN_HEAD, PiglinHeadEntityModel::getTexturedModelData);
        }

        for (SignType type : SignType.stream().toList()) {
            EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.createHangingSign(type), HangingSignBlockEntityRenderer::getTexturedModelData);
        }

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CAMEL, CamelEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityTypes.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.CRAB, CrabRenderer::new);

        EntityRendererRegistry.register(ModEntityTypes.RAFT, (context) -> new RaftEntityRenderer(context, false));
        EntityRendererRegistry.register(ModEntityTypes.CHEST_RAFT, (context) -> new RaftEntityRenderer(context, true));
        EntityRendererRegistry.register(ModEntityTypes.CAMEL, (context) -> new CamelEntityRenderer(context, ModEntityModelLayers.CAMEL));

        BlockEntityRendererRegistry.register(ModBlockEntityType.HANGING_SIGN, HangingSignBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SKULL, ModSkullBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.DISPLAY_CASE, DisplayCaseBlockRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                ModBlocks.BAMBOO_DOOR, ModBlocks.BAMBOO_TRAPDOOR,
                ModBlocks.SAKURA_DOOR, ModBlocks.SAKURA_TRAPDOOR, ModBlocks.SAKURA_LEAVES, ModBlocks.SAKURA_SAPLING, ModBlocks.POTTED_SAKURA_SAPLING,
                ModBlocks.AZALEA_DOOR, ModBlocks.AZALEA_TRAPDOOR,
                ModBlocks.WILLOW_DOOR, ModBlocks.WILLOW_TRAPDOOR, ModBlocks.WILLOW_LEAVES, ModBlocks.WILLOW_SAPLING, ModBlocks.POTTED_WILLOW_SAPLING,
                ModBlocks.BAOBAB_DOOR, ModBlocks.BAOBAB_TRAPDOOR, ModBlocks.BAOBAB_LEAVES, ModBlocks.BAOBAB_SAPLING, ModBlocks.POTTED_BAOBAB_SAPLING,
                ModBlocks.WHITE_WILD_FLOWER, ModBlocks.ORANGE_WILD_FLOWER, ModBlocks.MAGENTA_WILD_FLOWER, ModBlocks.LIGHT_BLUE_WILD_FLOWER, ModBlocks.YELLOW_WILD_FLOWER, ModBlocks.LIME_WILD_FLOWER, ModBlocks.PINK_WILD_FLOWER, ModBlocks.CYAN_WILD_FLOWER, ModBlocks.PURPLE_WILD_FLOWER, ModBlocks.BLUE_WILD_FLOWER, ModBlocks.GREEN_WILD_FLOWER, ModBlocks.RED_WILD_FLOWER, ModBlocks.BLACK_WILD_FLOWER, ModBlocks.FIRE_WILD_FLOWER, ModBlocks.ICE_WILD_FLOWER, ModBlocks.GOLDEN_WILD_FLOWER, ModBlocks.WILD_WEEDS,
                ModBlocks.POTTED_WHITE_WILD_FLOWER, ModBlocks.POTTED_ORANGE_WILD_FLOWER, ModBlocks.POTTED_MAGENTA_WILD_FLOWER, ModBlocks.POTTED_LIGHT_BLUE_WILD_FLOWER, ModBlocks.POTTED_YELLOW_WILD_FLOWER, ModBlocks.POTTED_LIME_WILD_FLOWER, ModBlocks.POTTED_PINK_WILD_FLOWER, ModBlocks.POTTED_CYAN_WILD_FLOWER, ModBlocks.POTTED_PURPLE_WILD_FLOWER, ModBlocks.POTTED_BLUE_WILD_FLOWER, ModBlocks.POTTED_GREEN_WILD_FLOWER, ModBlocks.POTTED_RED_WILD_FLOWER, ModBlocks.POTTED_BLACK_WILD_FLOWER, ModBlocks.POTTED_FIRE_WILD_FLOWER, ModBlocks.POTTED_ICE_WILD_FLOWER, ModBlocks.POTTED_GOLDEN_WILD_FLOWER, ModBlocks.POTTED_WILD_WEEDS,
                ModBlocks.OAK_HEDGE, ModBlocks.BIRCH_HEDGE, ModBlocks.SPRUCE_HEDGE, ModBlocks.JUNGLE_HEDGE, ModBlocks.ACACIA_HEDGE, ModBlocks.DARK_OAK_HEDGE, ModBlocks.AZALEA_HEDGE, ModBlocks.FLOWERING_AZALEA_HEDGE, ModBlocks.SAKURA_HEDGE, ModBlocks.WILLOW_HEDGE, ModBlocks.BAOBAB_HEDGE);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.DISPLAY_CASE, ModBlocks.GLASS_DISPLAY_CASE);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return FoliageColors.getDefaultColor();
            }
            return BiomeColors.getFoliageColor(world, pos);
        }, ModBlocks.WILLOW_LEAVES, ModBlocks.BAOBAB_LEAVES,
                ModBlocks.OAK_HEDGE, ModBlocks.BIRCH_HEDGE, ModBlocks.SPRUCE_HEDGE, ModBlocks.JUNGLE_HEDGE, ModBlocks.ACACIA_HEDGE, ModBlocks.DARK_OAK_HEDGE, ModBlocks.MANGROVE_HEDGE, ModBlocks.WILLOW_HEDGE, ModBlocks.BAOBAB_HEDGE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            //BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            //return BlockColors.create().getColor(blockState, null, null, tintIndex);
            return FoliageColors.getDefaultColor();
        }, ModItems.WILLOW_LEAVES, ModItems.BAOBAB_LEAVES,
                ModItems.OAK_HEDGE, ModItems.BIRCH_HEDGE, ModItems.SPRUCE_HEDGE, ModItems.JUNGLE_HEDGE, ModItems.ACACIA_HEDGE, ModItems.DARK_OAK_HEDGE, ModItems.MANGROVE_HEDGE, ModItems.WILLOW_HEDGE, ModItems.BAOBAB_HEDGE);

        ClientPlayNetworking.registerGlobalReceiver(TheLittleThingsNetworkingConstants.getHangingSignScreenPacketId(), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            BlockEntity blockEntity = client.world.getBlockEntity(pos);
            if (!(blockEntity instanceof HangingSignBlockEntity)) {
                BlockState blockState = client.world.getBlockState(pos);
                blockEntity = new SignBlockEntity(pos, blockState);
                blockEntity.setWorld(client.world);
            }

            BlockEntity finalBlockEntity = blockEntity;
            client.execute(() -> {
                client.setScreen(new HangingSignEditScreen((HangingSignBlockEntity) finalBlockEntity, client.shouldFilterText()));
            });

            //((PlayerEntitySignScreenAccess)client.player).editHangingSignScreen((HangingSignBlockEntity) blockEntity);
            //setScreen(new SignEditScreen(sign, this.client.shouldFilterText()));

        });

        ClientPlayNetworking.registerGlobalReceiver(TheLittleThingsNetworkingConstants.getPincerAttackPacketId(), (client, handler, buf, responseSender) -> {
            double d = buf.readDouble();
            double e = buf.readDouble();
            double f = buf.readDouble();

            if (client.player != null) {
                client.player.setVelocity(new Vec3d(d, e + 2.0D, f).multiply(0.15D));
            }
        });
    }
}
