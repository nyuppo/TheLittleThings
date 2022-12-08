package com.nyuppo.entity.passive.renderer;

import com.nyuppo.entity.passive.CrabEntity;
import com.nyuppo.entity.passive.model.CrabModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CrabRenderer extends GeoMobRenderer<CrabEntity> {
    public CrabRenderer(EntityRendererFactory.Context ctx)
    {
        super(ctx, new CrabModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderLayer getRenderType(CrabEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
