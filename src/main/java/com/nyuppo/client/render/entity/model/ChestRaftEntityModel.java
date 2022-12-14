package com.nyuppo.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public class ChestRaftEntityModel extends RaftEntityModel {
    private static final String CHEST_BOTTOM = "chest_bottom";
    private static final String CHEST_LID = "chest_lid";
    private static final String CHEST_LOCK = "chest_lock";

    public ChestRaftEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    protected ImmutableList.Builder<ModelPart> getParts(ModelPart root) {
        ImmutableList.Builder<ModelPart> builder = super.getParts(root);
        builder.add(root.getChild("chest_bottom"));
        builder.add(root.getChild("chest_lid"));
        builder.add(root.getChild("chest_lock"));
        return builder;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        RaftEntityModel.addParts(modelPartData);
        modelPartData.addChild("chest_bottom", ModelPartBuilder.create().uv(0, 76).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F), ModelTransform.of(-2.0F, -11.0F, -6.0F, 0.0F, -1.5707964F, 0.0F));
        modelPartData.addChild("chest_lid", ModelPartBuilder.create().uv(0, 59).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F), ModelTransform.of(-2.0F, -15.0F, -6.0F, 0.0F, -1.5707964F, 0.0F));
        modelPartData.addChild("chest_lock", ModelPartBuilder.create().uv(0, 59).cuboid(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F), ModelTransform.of(-1.0F, -12.0F, -1.0F, 0.0F, -1.5707964F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
