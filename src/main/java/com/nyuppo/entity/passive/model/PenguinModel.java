package com.nyuppo.entity.passive.model;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.passive.PenguinEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PenguinModel extends AnimatedGeoModel<PenguinEntity> {
    private String penguinName;
    private Identifier NORMAL_TEXTURE = TheLittleThings.ID("textures/entity/penguin/penguin.png");
    private Identifier BABY_TEXTURE = TheLittleThings.ID("textures/entity/penguin/penguin_baby.png");
    private Identifier FANCY_TEXTURE = TheLittleThings.ID("textures/entity/penguin/penguin_fancy.png");
    private Identifier SCRUFFY_TEXTURE = TheLittleThings.ID("textures/entity/penguin/penguin_scruffy.png");
    private Identifier currentTexture = NORMAL_TEXTURE;

    @Override
    public Identifier getModelResource(PenguinEntity object) {
        return TheLittleThings.ID("geo/penguin.geo.json");
    }

    @Override
    public Identifier getTextureResource(PenguinEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationResource(PenguinEntity object) {
        return TheLittleThings.ID("animations/penguin.animation.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        if (entity.isBaby()) {
            IBone full = this.getAnimationProcessor().getBone("base");
            if (full != null) {
                full.setScaleX(0.5f);
                full.setScaleY(0.5f);
                full.setScaleZ(0.5f);
            }
            currentTexture = BABY_TEXTURE;
        } else {
            penguinName = Formatting.strip(entity.getName().getString());
            currentTexture = NORMAL_TEXTURE;

            if (penguinName != null) {
                if ("Popper".equalsIgnoreCase(penguinName)) {
                    currentTexture = FANCY_TEXTURE;
                } else if ("Rico".equalsIgnoreCase(penguinName)) {
                    currentTexture = SCRUFFY_TEXTURE;
                }
            }
        }

        IBone neck = this.getAnimationProcessor().getBone("neck");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (neck != null && !entity.getEntityWorld().getBlockState(entity.getBlockPos().down()).isIn(BlockTags.ICE) && !entity.isTouchingWater() && !entity.isShaking()) {
            neck.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            neck.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
