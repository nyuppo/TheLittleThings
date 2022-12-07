package com.nyuppo.mixin;

import com.nyuppo.registration.ModTexturedRenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "addDefaultTextures", at = @At("RETURN"))
    private static void onAddDefaultTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci) {
        ModTexturedRenderLayers.HANGING_WOOD_TYPE_TEXTURES.values().forEach(adder);
    }
}
