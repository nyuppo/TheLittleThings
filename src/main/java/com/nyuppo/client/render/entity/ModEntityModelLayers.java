package com.nyuppo.client.render.entity;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.vehicle.RaftEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.SignType;

public class ModEntityModelLayers {
    public static final EntityModelLayer CAMEL = create("camel", "main");
    public static final EntityModelLayer PIGLIN_HEAD = create("piglin_head", "main");

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(TheLittleThings.ID(id), layer);
    }

    public static EntityModelLayer createRaft(RaftEntity.Type type) {
        return create("raft/" + type.getName(), "main");
    }

    public static EntityModelLayer createChestRaft(RaftEntity.Type type) {
        return create("chest_raft/" + type.getName(), "main");
    }

    public static EntityModelLayer createHangingSign(SignType type) {
        return create("hanging_sign/" + type.getName(), "main");
    }
}
