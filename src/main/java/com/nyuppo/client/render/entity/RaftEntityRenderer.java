package com.nyuppo.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.nyuppo.TheLittleThings;
import com.nyuppo.client.render.entity.model.ChestRaftEntityModel;
import com.nyuppo.client.render.entity.model.RaftEntityModel;
import com.nyuppo.entity.vehicle.RaftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.Map;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class RaftEntityRenderer extends EntityRenderer<RaftEntity> {
    private final Map<RaftEntity.Type, Pair<Identifier, CompositeEntityModel<RaftEntity>>> texturesAndModels;

    public RaftEntityRenderer(EntityRendererFactory.Context ctx, boolean chest) {
        super(ctx);
        this.shadowRadius = 0.8F;
        this.texturesAndModels = (Map) Stream.of(RaftEntity.Type.values()).collect(ImmutableMap.toImmutableMap((type) -> {
            return type;
        }, (type) -> {
            return Pair.of(TheLittleThings.ID(getTexture(type, chest)), this.createModel(ctx, type, chest));
        }));
    }

    private RaftEntityModel createModel(EntityRendererFactory.Context ctx, RaftEntity.Type type, boolean chest) {
        EntityModelLayer entityModelLayer = chest ? ModEntityModelLayers.createChestRaft(type) : ModEntityModelLayers.createRaft(type);
        return chest ? new ChestRaftEntityModel(ctx.getPart(entityModelLayer)) : new RaftEntityModel(ctx.getPart(entityModelLayer));
    }

    private static String getTexture(RaftEntity.Type type, boolean chest) {
        return chest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
    }

    public void render(RaftEntity raftEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
        float h = (float)raftEntity.getDamageWobbleTicks() - g;
        float j = raftEntity.getDamageWobbleStrength() - g;
        if (j < 0.0F) {
            j = 0.0F;
        }

        if (h > 0.0F) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * (float)raftEntity.getDamageWobbleSide()));
        }

        float k = raftEntity.interpolateBubbleWobble(g);
        if (!MathHelper.approximatelyEquals(k, 0.0F)) {
            matrixStack.multiply(new Quaternion(new Vec3f(1.0F, 0.0F, 1.0F), raftEntity.interpolateBubbleWobble(g), true));
        }

        Pair<Identifier, CompositeEntityModel<RaftEntity>> pair = (Pair)this.texturesAndModels.get(raftEntity.getRaftType());
        Identifier identifier = (Identifier)pair.getFirst();
        CompositeEntityModel compositeEntityModel = pair.getSecond();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        compositeEntityModel.setAngles(raftEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(compositeEntityModel.getLayer(identifier));
        compositeEntityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!raftEntity.isSubmergedInWater()) {
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
        }

        matrixStack.pop();
        super.render(raftEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(RaftEntity raftEntity) {
        return (Identifier)((Pair)this.texturesAndModels.get(raftEntity.getRaftType())).getFirst();
    }
}
