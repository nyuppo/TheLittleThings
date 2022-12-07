package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModTexturedRenderLayers {
    public static final Identifier SIGNS_ATLAS_TEXTURE = new Identifier("textures/atlas/hanging_signs.png");
    public static final Map<SignType, SpriteIdentifier> HANGING_WOOD_TYPE_TEXTURES = SignType.stream().collect(Collectors.toMap(Function.identity(), ModTexturedRenderLayers::createHangingSignTextureId));

    private static SpriteIdentifier createHangingSignTextureId(SignType type) {
        return new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, TheLittleThings.ID("entity/signs/hanging/" + type.getName()));
    }

    public static SpriteIdentifier getHangingSignTextureId(SignType signType) {
        return HANGING_WOOD_TYPE_TEXTURES.get(signType);
    }
}
