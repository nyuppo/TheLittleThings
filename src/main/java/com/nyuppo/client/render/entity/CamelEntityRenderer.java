package com.nyuppo.client.render.entity;

import com.nyuppo.TheLittleThings;
import com.nyuppo.client.render.entity.model.CamelEntityModel;
import com.nyuppo.entity.passive.CamelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CamelEntityRenderer extends MobEntityRenderer<CamelEntity, CamelEntityModel<CamelEntity>> {
    private static final Identifier TEXTURE = TheLittleThings.ID("textures/entity/camel/camel.png");

    public CamelEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, new CamelEntityModel(ctx.getPart(layer)), 0.7f);
    }

    @Override
    public Identifier getTexture(CamelEntity camelEntity) {
        return TEXTURE;
    }
}
