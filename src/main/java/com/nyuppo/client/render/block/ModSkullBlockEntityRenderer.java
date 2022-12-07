package com.nyuppo.client.render.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.nyuppo.block.ModAbstractSkullBlock;
import com.nyuppo.block.ModSkullBlock;
import com.nyuppo.block.ModWallSkullBlock;
import com.nyuppo.block.entity.ModSkullBlockEntity;
import com.nyuppo.client.render.entity.ModEntityModelLayers;
import com.nyuppo.client.render.entity.model.PiglinHeadEntityModel;
import com.nyuppo.util.RotationPropertyHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModSkullBlockEntityRenderer implements BlockEntityRenderer<ModSkullBlockEntity> {
    private final Map<ModSkullBlock.ModSkullType, ModSkullBlockEntityModel> MODELS;
    private static final Map<ModSkullBlock.ModSkullType, Identifier> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(ModSkullBlock.Type.PIGLIN, new Identifier("textures/entity/piglin/piglin.png"));
        map.put(ModSkullBlock.Type.ENDERMAN, new Identifier("textures/entity/enderman/enderman.png"));
    });

    public static Map<ModSkullBlock.ModSkullType, ModSkullBlockEntityModel> getModels(EntityModelLoader modelLoader) {
        ImmutableMap.Builder<ModSkullBlock.ModSkullType, ModSkullBlockEntityModel> builder = ImmutableMap.builder();
        builder.put(ModSkullBlock.Type.PIGLIN, new PiglinHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.PIGLIN_HEAD)));
        return builder.build();
    }

    public ModSkullBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.MODELS = ModSkullBlockEntityRenderer.getModels(ctx.getLayerRenderDispatcher());
    }

    @Override
    public void render(ModSkullBlockEntity skullBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        float g = skullBlockEntity.getPoweredTicks(f);
        BlockState blockState = skullBlockEntity.getCachedState();
        boolean bl = blockState.getBlock() instanceof ModWallSkullBlock;
        Direction direction = bl ? blockState.get(ModWallSkullBlock.FACING) : null;
        int k = bl ? RotationPropertyHelper.fromDirection(direction) : blockState.get(ModSkullBlock.ROTATION);
        float h = RotationPropertyHelper.toDegrees(k);
        ModSkullBlock.ModSkullType skullType = ((ModAbstractSkullBlock)blockState.getBlock()).getSkullType();
        ModSkullBlockEntityModel model = this.MODELS.get(skullType);
        RenderLayer renderLayer = ModSkullBlockEntityRenderer.getRenderLayer(skullType);
        ModSkullBlockEntityRenderer.renderSkull(direction, h, g, matrixStack, vertexConsumerProvider, i, model, renderLayer);
    }

    public static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ModSkullBlockEntityModel model, RenderLayer renderLayer) {
        matrices.push();
        if (direction == null) {
            matrices.translate(0.5f, 0.0f, 0.5f);
        } else {
            float f = 0.25f;
            matrices.translate(0.5f - (float)direction.getOffsetX() * 0.25f, 0.25f, 0.5f - (float)direction.getOffsetZ() * 0.25f);
        }
        matrices.scale(-1.0f, -1.0f, 1.0f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        model.setHeadRotation(animationProgress, yaw, 0.0f);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
    }

    public static RenderLayer getRenderLayer(ModSkullBlock.ModSkullType type) {
        return RenderLayer.getEntityCutoutNoCullZOffset(TEXTURES.get(type));
    }
}
