package com.nyuppo.entity.passive.model;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.passive.CrabEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrabModel extends AnimatedGeoModel<CrabEntity> {
    private Identifier RED_TEXTURE = TheLittleThings.ID("textures/entity/crab/crab_red.png");
    private Identifier BLUE_TEXTURE = TheLittleThings.ID("textures/entity/crab/crab_blue.png");
    private Identifier GREEN_TEXTURE = TheLittleThings.ID("textures/entity/crab/crab_green.png");
    private Identifier PURPLE_TEXTURE = TheLittleThings.ID("textures/entity/crab/crab_purple.png");
    private Identifier currentTexture = RED_TEXTURE;

    @Override
    public Identifier getModelResource(CrabEntity object) {
        return TheLittleThings.ID("geo/crab.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrabEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationResource(CrabEntity object) {
        return TheLittleThings.ID("animations/crab.animation.json");
    }

    @Override
    public void setCustomAnimations(CrabEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        if (entity.isBaby()) {
            IBone root = this.getAnimationProcessor().getBone("root");
            if (root != null) {
                root.setScaleX(0.5f);
                root.setScaleY(0.5f);
                root.setScaleZ(0.5f);
            }
        }

        int i = entity.getCrabType();
        switch (i) {
            case 1:
                currentTexture = BLUE_TEXTURE;
                break;
            case 2:
                currentTexture = GREEN_TEXTURE;
                break;
            case 3:
                currentTexture = PURPLE_TEXTURE;
                break;
            case 0:
            default:
                currentTexture = RED_TEXTURE;
                break;
        }
    }
}
