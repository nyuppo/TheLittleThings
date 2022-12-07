package com.nyuppo.mixin;

import com.nyuppo.block.ModAbstractSkullBlock;
import com.nyuppo.block.ModSkullBlock;
import com.nyuppo.client.render.block.ModSkullBlockEntityModel;
import com.nyuppo.client.render.block.ModSkullBlockEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BuiltinModelItemRenderer.class)
public class SkullItemRendererMixin {
    @Final
    @Mutable
    @Shadow
    private EntityModelLoader entityModelLoader;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            BlockEntity blockEntity;
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof ModAbstractSkullBlock) {
                Map<ModSkullBlock.ModSkullType, ModSkullBlockEntityModel> models = ModSkullBlockEntityRenderer.getModels(this.entityModelLoader);

                ModSkullBlock.ModSkullType type = ((ModAbstractSkullBlock)block).getSkullType();
                ModSkullBlockEntityModel model = models.get(type);
                RenderLayer layer = ModSkullBlockEntityRenderer.getRenderLayer(type);
                ModSkullBlockEntityRenderer.renderSkull(null, 180.0F, 0.0F, matrices, vertexConsumers, light, model, layer);
                ci.cancel();
            }
        }
    }
}
