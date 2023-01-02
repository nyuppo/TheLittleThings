package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.block.WildFlowerBlock;
import com.nyuppo.block.enums.WildFlowerState;
import com.nyuppo.item.GardeningHat;
import com.nyuppo.registration.HatItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class GardeningHatOverlayMixin {
    @Final
    @Mutable
    @Shadow
    private MinecraftClient client;

    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;

    @Shadow protected abstract void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color);

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderCrosshair(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!this.client.options.hudHidden) {
            if (this.client.player != null && this.client.player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof GardeningHat) {
                HitResult blockHit = this.client.player.raycast(this.client.interactionManager.getReachDistance(), 0.0f, false);
                if (blockHit.getType() == HitResult.Type.BLOCK) {
                    if (this.client.world != null) {
                        BlockState state = this.client.world.getBlockState(((BlockHitResult)blockHit).getBlockPos());
                        if (state.getBlock() instanceof WildFlowerBlock) {
                            WildFlowerState flowerState = state.get(WildFlowerBlock.FLOWER_STATE);

                            matrices.push();

                            matrices.translate(this.scaledWidth / 2f, this.scaledHeight / 2f, 0.0);
                            matrices.translate(0f, 10f, 0f);

                            TextRenderer textRenderer = ((InGameHud)(Object)this).getTextRenderer();
                            Text text = Text.translatable("block.wild_flower." + flowerState.toString());
                            this.drawTextBackground(matrices, textRenderer, -10, textRenderer.getWidth(text), 16777215);
                            textRenderer.drawWithShadow(matrices, text, (float)(-textRenderer.getWidth(text) / 2f), -4.0f, 16777215);

                            matrices.pop();
                        }
                    }
                }
            }

        }
    }
}
