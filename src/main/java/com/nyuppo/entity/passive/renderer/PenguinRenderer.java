package com.nyuppo.entity.passive.renderer;

import com.nyuppo.entity.passive.CrabEntity;
import com.nyuppo.entity.passive.PenguinEntity;
import com.nyuppo.entity.passive.model.PenguinModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PenguinModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderLayer getRenderType(PenguinEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
