package com.nyuppo.client.render.block;

import com.google.common.collect.ImmutableMap;
import com.nyuppo.TheLittleThings;
import com.nyuppo.block.AbstractHangingSignBlock;
import com.nyuppo.block.HangingSignBlock;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.client.render.entity.ModEntityModelLayers;
import com.nyuppo.registration.ModBlocks;
import com.nyuppo.util.RotationPropertyHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class HangingSignBlockEntityRenderer implements BlockEntityRenderer<HangingSignBlockEntity> {
    private static final String STICK = "stick";
    private static final int GLOWING_BLACK_COLOR = -988212;
    private static final int RENDER_DISTANCE = MathHelper.square(16);
    private final TextRenderer textRenderer;
    private static final String PLANK = "plank";
    private static final String V_CHAINS = "vChains";
    public static final String NORMAL_CHAINS = "normalChains";
    public static final String CHAIN_L1 = "chainL1";
    public static final String CHAIN_L2 = "chainL2";
    public static final String CHAIN_R1 = "chainR1";
    public static final String CHAIN_R2 = "chainR2";
    public static final String BOARD = "board";
    private final Map<SignType, HangingSignBlockEntityRenderer.HangingSignModel> MODELS;

    public HangingSignBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.MODELS = (Map)SignType.stream().collect(ImmutableMap.toImmutableMap((signType) -> {
            return signType;
        }, (type) -> {
            return new HangingSignModel(context.getLayerModelPart(ModEntityModelLayers.createHangingSign(type)));
        }));
        this.textRenderer = context.getTextRenderer();
    }

    public void render(HangingSignBlockEntity hangingSignBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        BlockState blockState = hangingSignBlockEntity.getCachedState();
        matrixStack.push();
        SignType signType = getSignType(blockState.getBlock());
        HangingSignModel hangingSignModel = this.MODELS.get(signType);
        boolean bl = !(blockState.getBlock() instanceof HangingSignBlock);
        boolean bl2 = blockState.contains(Properties.ATTACHED) && (Boolean)blockState.get(Properties.ATTACHED);
        matrixStack.translate(0.5D, 0.9375D, 0.5D);
        float g;
        if (bl2) {
            g = -RotationPropertyHelper.toDegrees((Integer)blockState.get(HangingSignBlock.ROTATION));
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
        } else {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(this.getRotationDegrees(blockState, bl)));
        }

        matrixStack.translate(0.0F, -0.3125F, 0.0F);
        hangingSignModel.updateVisibleParts(blockState);
        g = 1.0F;

        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        //SpriteIdentifier spriteIdentifier = this.getTextureId(signType);
        Identifier textureId = getTextureId(signType);
        //Objects.requireNonNull(hangingSignModel);
        //VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, hangingSignModel::getLayer);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(textureId));
        //VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(textureId));
        this.renderSignModel(matrixStack, i, j, hangingSignModel, vertexConsumer);
        matrixStack.pop();

        //this.renderText(signBlockEntity, matrixStack, vertexConsumerProvider, i, 1.0F);
        float h = 0.015625F;
        Vec3d vec3d = this.getTextOffset(1.0F);
        matrixStack.translate(vec3d.x, vec3d.y, vec3d.z);
        matrixStack.scale(h, -h, h);
        int k = getColor(hangingSignBlockEntity);
        int l = 4 * 9 / 2;
        OrderedText[] orderedTexts = hangingSignBlockEntity.updateSign(MinecraftClient.getInstance().shouldFilterText(), (text) -> {
            List<OrderedText> list = this.textRenderer.wrapLines(text, 50);
            return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
        });
        int m;
        boolean bl3;
        int n;
        if (hangingSignBlockEntity.isGlowingText()) {
            m = hangingSignBlockEntity.getTextColor().getSignColor();
            bl3 = shouldRender(hangingSignBlockEntity, m);
            n = 15728880;
        } else {
            m = k;
            bl3 = false;
            n = i;
        }

        for (int o = 0; o < 4; ++o) {
            OrderedText orderedText = orderedTexts[o];
            float p = (float)(-this.textRenderer.getWidth(orderedText) / 2);
            if (bl3) {
                this.textRenderer.drawWithOutline(orderedText, p, (float)(o * 9 - l), m, k, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, n);
            } else {
                this.textRenderer.draw(orderedText, p, (float)(o * 9 - l), m, false, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, false, 0, n);
            }
        }

        matrixStack.pop();
    }

    private static int getColor(HangingSignBlockEntity sign) {
        int i = sign.getTextColor().getSignColor();
        double d = 0.4D;
        int j = (int)((double) NativeImage.getRed(i) * 0.4D);
        int k = (int)((double)NativeImage.getGreen(i) * 0.4D);
        int l = (int)((double)NativeImage.getBlue(i) * 0.4D);
        return i == DyeColor.BLACK.getSignColor() && sign.isGlowingText() ? -988212 : NativeImage.packColor(0, l, k, j);
    }

    static boolean shouldRender(HangingSignBlockEntity sign, int signColor) {
        if (signColor == DyeColor.BLACK.getSignColor()) {
            return true;
        } else {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
            if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
                return true;
            } else {
                Entity entity = minecraftClient.getCameraEntity();
                return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(sign.getPos())) < (double) MathHelper.square(16);
            }
        }
    }

    public static SignType getSignType(Block block) {
        SignType signType;
        if (block instanceof AbstractHangingSignBlock) {
            signType = ((AbstractHangingSignBlock)block).getSignType();
        } else {
            signType = SignType.OAK;
        }

        return signType;
    }

    private float getRotationDegrees(BlockState state, boolean wall) {
        return wall ? -((Direction)state.get(WallSignBlock.FACING)).asRotation() : -((float)((Integer)state.get(HangingSignBlock.ROTATION) * 360) / 16.0F);
    }

    public static Identifier getTextureId(SignType signType) {
        if (SignType.OAK.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/oak.png");
        } else if (SignType.BIRCH.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/birch.png");
        } else if (SignType.SPRUCE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/spruce.png");
        } else if (SignType.JUNGLE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/jungle.png");
        } else if (SignType.ACACIA.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/acacia.png");
        } else if (SignType.DARK_OAK.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/dark_oak.png");
        } else if (SignType.MANGROVE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/mangrove.png");
        } else if (SignType.CRIMSON.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/crimson.png");
        } else if (SignType.WARPED.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/warped.png");
        } else if (ModBlocks.BAMBOO_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/bamboo.png");
        } else if (ModBlocks.SAKURA_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/sakura.png");
        } else if (ModBlocks.AZALEA_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/azalea.png");
        } else if (ModBlocks.WILLOW_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/willow.png");
        } else if (ModBlocks.BAOBAB_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/baobab.png");
        } else if (ModBlocks.PAPER_SIGN_TYPE.equals(signType)) {
            return TheLittleThings.ID("textures/entity/signs/hanging/paper.png");
        }

        return TheLittleThings.ID("textures/entity/signs/hanging/oak.png");
        //return TexturedRenderLayers.getHangingSignTextureId(signType);
    }

    void renderSignModel(MatrixStack matrices, int light, int overlay, Model model, VertexConsumer vertices) {
        HangingSignModel hangingSignModel = (HangingSignModel)model;
        hangingSignModel.root.render(matrices, vertices, light, overlay);
    }

    Vec3d getTextOffset(float scale) {
        return new Vec3d(0.0D, (double)(-0.32F * scale), (double)(0.063F * scale));
    }

    public static HangingSignModel createHangingSignModel(EntityModelLoader entityModelLoader, SignType type) {
        return new HangingSignModel(entityModelLoader.getModelPart(ModEntityModelLayers.createHangingSign(type)));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("board", ModelPartBuilder.create().uv(0, 12).cuboid(-7.0F, 0.0F, -1.0F, 14.0F, 10.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild("plank", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -6.0F, -2.0F, 16.0F, 2.0F, 4.0F), ModelTransform.NONE);
        ModelPartData modelPartData2 = modelPartData.addChild("normalChains", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData2.addChild("chainL1", ModelPartBuilder.create().uv(0, 6).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), ModelTransform.of(-5.0F, -6.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
        modelPartData2.addChild("chainL2", ModelPartBuilder.create().uv(6, 6).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), ModelTransform.of(-5.0F, -6.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        modelPartData2.addChild("chainR1", ModelPartBuilder.create().uv(0, 6).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), ModelTransform.of(5.0F, -6.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
        modelPartData2.addChild("chainR2", ModelPartBuilder.create().uv(6, 6).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), ModelTransform.of(5.0F, -6.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        modelPartData.addChild("vChains", ModelPartBuilder.create().uv(14, 6).cuboid(-6.0F, -6.0F, 0.0F, 12.0F, 6.0F, 0.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Environment(EnvType.CLIENT)
    public static final class HangingSignModel extends Model {
        public final ModelPart root;
        public final ModelPart plank;
        public final ModelPart vChains;
        public final ModelPart normalChains;

        public HangingSignModel(ModelPart root) {
            super(RenderLayer::getEntityCutoutNoCull);
            this.root = root;
            this.plank = root.getChild("plank");
            this.normalChains = root.getChild("normalChains");
            this.vChains = root.getChild("vChains");
        }

        public void updateVisibleParts(BlockState state) {
            boolean bl = !(state.getBlock() instanceof HangingSignBlock);
            this.plank.visible = bl;
            this.vChains.visible = false;
            this.normalChains.visible = true;
            if (!bl) {
                boolean bl2 = (Boolean)state.get(Properties.ATTACHED);
                this.normalChains.visible = !bl2;
                this.vChains.visible = bl2;
            }

        }

        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }
}
