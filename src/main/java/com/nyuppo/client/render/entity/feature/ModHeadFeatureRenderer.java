package com.nyuppo.client.render.entity.feature;

import com.mojang.authlib.GameProfile;
import com.nyuppo.block.ModAbstractSkullBlock;
import com.nyuppo.block.ModSkullBlock;
import com.nyuppo.client.render.block.ModSkullBlockEntityModel;
import com.nyuppo.client.render.block.ModSkullBlockEntityRenderer;
import com.nyuppo.client.render.entity.model.PiglinHeadEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModHeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final float scaleX;
    private final float scaleY;
    private final float scaleZ;
    private final Map<ModSkullBlock.ModSkullType, ModSkullBlockEntityModel> models;
    private final HeldItemRenderer heldItemRenderer;

    public ModHeadFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, HeldItemRenderer heldItemRenderer) {
        this(context, loader, 1.0f, 1.0f, 1.0f, heldItemRenderer);
    }

    public ModHeadFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, float scaleX, float scaleY, float scaleZ, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.models = ModSkullBlockEntityRenderer.getModels(loader);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof ModAbstractSkullBlock)) {
            return;
        }
        Item item = itemStack.getItem();
        matrixStack.push();
        matrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
        boolean bl = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
        if ((livingEntity).isBaby() && !(livingEntity instanceof VillagerEntity)) {
            float n = 1.4f;
            matrixStack.translate(0.0f, 0.03125f, 0.0f);
            matrixStack.scale(0.7f, 0.7f, 0.7f);
            matrixStack.translate(0.0f, 1.0f, 0.0f);
        }
        ((ModelWithHead)this.getContextModel()).getHead().rotate(matrixStack);

        matrixStack.scale(1.1875f, -1.1875f, -1.1875f);
        if (bl) {
            matrixStack.translate(0.0f, 0.0625f, 0.0f);
        }

        matrixStack.translate(-0.5, 0.0, -0.5);
        ModSkullBlock.ModSkullType type = ((ModAbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType();
        ModSkullBlockEntityModel modSkullBlockEntityModel = this.models.get(type);
        RenderLayer renderLayer = ModSkullBlockEntityRenderer.getRenderLayer(type);
        ModSkullBlockEntityRenderer.renderSkull(null, 180.0f, f, matrixStack, vertexConsumerProvider, i, modSkullBlockEntityModel, renderLayer);
        matrixStack.pop();
    }

    public static void translate(MatrixStack matrices, boolean villager) {
        float f = 0.625f;
        matrices.translate(0.0f, -0.25f, 0.0f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        matrices.scale(0.625f, -0.625f, -0.625f);
        if (villager) {
            matrices.translate(0.0f, 0.1875f, 0.0f);
        }
    }
}
