package com.nyuppo.client.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public abstract class ModSkullBlockEntityModel extends Model {
    public ModSkullBlockEntityModel() {
        super(RenderLayer::getEntityTranslucent);
    }

    public abstract void setHeadRotation(float var1, float var2, float var3);
}
