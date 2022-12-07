package com.nyuppo.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.nyuppo.block.HangingSignBlock;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.client.render.block.HangingSignBlockEntityRenderer;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import com.nyuppo.registration.ModTexturedRenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.*;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Matrix4f;

import java.util.Objects;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
public class HangingSignEditScreen extends Screen {
    private final HangingSignBlockEntity sign;
    private int ticksSinceOpened;
    private int currentRow;
    private SelectionManager selectionManager;
    private SignType signType;
    private HangingSignBlockEntityRenderer.HangingSignModel model;
    private final String[] text;

    public HangingSignEditScreen(HangingSignBlockEntity sign, boolean filtered) {
        super(Text.translatable("hanging_sign.edit"));
        this.text = (String[]) IntStream.range(0, 4).mapToObj((row) -> {
            return sign.getTextOnRow(row, filtered);
        }).map(Text::getString).toArray((i) -> {
            return new String[i];
        });
        this.sign = sign;
    }

    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, ScreenTexts.DONE, (button) -> {
            this.finishEditing();
        }));
        this.sign.setEditable(false);
        this.selectionManager = new SelectionManager(() -> {
            return this.text[this.currentRow];
        }, (text) -> {
            this.text[this.currentRow] = text;
            this.sign.setTextOnRow(this.currentRow, Text.literal(text));
        }, SelectionManager.makeClipboardGetter(this.client), SelectionManager.makeClipboardSetter(this.client), (text) -> {
            return this.client.textRenderer.getWidth(text) <= 50;
        });
        BlockState blockState = this.sign.getCachedState();
        //this.signType = SignBlockEntityRenderer.getSignType(blockState.getBlock());
        this.signType = HangingSignBlockEntityRenderer.getSignType(blockState.getBlock());
        //this.model = SignBlockEntityRenderer.createSignModel(this.client.getEntityModelLoader(), this.signType);
        this.model = HangingSignBlockEntityRenderer.createHangingSignModel(this.client.getEntityModelLoader(), this.signType);
    }

    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(this.sign.getPos());
            buf.writeString(this.text[0]);
            buf.writeString(this.text[1]);
            buf.writeString(this.text[2]);
            buf.writeString(this.text[3]);
            ClientPlayNetworking.send(TheLittleThingsNetworkingConstants.getUpdateHangingSignPacketId(), buf);
        }

        this.sign.setEditable(true);
    }

    public void tick() {
        ++this.ticksSinceOpened;
        if (!this.sign.getType().supports(this.sign.getCachedState())) {
            this.finishEditing();
        }

    }

    private void finishEditing() {
        this.sign.markDirty();
        this.client.setScreen((Screen)null);
    }

    public boolean charTyped(char chr, int modifiers) {
        this.selectionManager.insert(chr);
        return true;
    }

    public void close() {
        this.finishEditing();
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) {
            this.currentRow = this.currentRow - 1 & 3;
            this.selectionManager.putCursorAtEnd();
            return true;
        } else if (keyCode != 264 && keyCode != 257 && keyCode != 335) {
            return this.selectionManager.handleSpecialKey(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            this.currentRow = this.currentRow + 1 & 3;
            this.selectionManager.putCursorAtEnd();
            return true;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DiffuseLighting.disableGuiDepthLighting();
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 16777215);
        matrices.push();
        matrices.translate((double)(this.width / 2), 0.0D, 50.0D);
        float f = 93.75F;
        matrices.scale(93.75F, -93.75F, 93.75F);
        matrices.translate(0.0D, -1.3125D, 0.0D);
        BlockState blockState = this.sign.getCachedState();
        boolean bl = blockState.getBlock() instanceof HangingSignBlock;
        boolean bl3 = bl ? blockState.get(Properties.ATTACHED) : false;
        /*
        if (!bl) {
            matrices.translate(0.0D, -0.3125D, 0.0D);
        }
         */
        matrices.translate(0.0D, -0.5D, 0.0D);

        boolean bl2 = this.ticksSinceOpened / 6 % 2 == 0;
        float g = 0.6666667F;
        VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
        matrices.scale(1.5F, 1.5F, 1.0F);


        matrices.push();
        matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
        //matrices.translate(this.width / 2.0F, 125.0F, 50.0F);
        matrices.translate(0.0D, -0.85D, 0.0D);
        //SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getSignTextureId(this.signType);
        SpriteIdentifier spriteIdentifier = ModTexturedRenderLayers.getHangingSignTextureId(this.signType);
        HangingSignBlockEntityRenderer.HangingSignModel var10002 = this.model;
        Objects.requireNonNull(var10002);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(immediate, var10002::getLayer);
        this.model.vChains.visible = bl3;
        this.model.normalChains.visible = !bl3;
        this.model.plank.visible = !bl;
        this.model.root.visible = true;

        this.model.root.render(matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
        matrices.pop();

        /*
        matrices.push();
        matrices.scale(2.0F, 2.0F, 2.0F);
        Identifier textureId = HangingSignBlockEntityRenderer.getTextureId(this.signType);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, textureId);
        drawTexture(matrices, this.width / 2, this.height / 2, 0, 0, 16, 16, 64, 32);
        drawCenteredText(matrices, this.textRenderer, Text.literal("haiiii"), this.width, this.height, 16777215);
        matrices.pop();
        */

        float h = 0.010416667F;
        matrices.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
        matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.sign.getTextColor().getSignColor();
        int j = this.selectionManager.getSelectionStart();
        int k = this.selectionManager.getSelectionEnd();
        int l = this.currentRow * 9 - this.text.length * 5;
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();

        int m;
        String string;
        int o;
        int p;
        for(m = 0; m < this.text.length; ++m) {
            string = this.text[m];
            if (string != null) {
                if (this.textRenderer.isRightToLeft()) {
                    string = this.textRenderer.mirror(string);
                }

                float n = (float)(-this.client.textRenderer.getWidth(string) / 2);
                //                                                 m * 10
                this.client.textRenderer.draw(string, n, (float)(m * 9 - this.text.length * 5), i, false, matrix4f, immediate, false, 0, 15728880, false);
                if (m == this.currentRow && j >= 0 && bl2) {
                    o = this.client.textRenderer.getWidth(string.substring(0, Math.max(Math.min(j, string.length()), 0)));
                    p = o - this.client.textRenderer.getWidth(string) / 2;
                    if (j >= string.length()) {
                        this.client.textRenderer.draw("_", (float)p, (float)l, i, false, matrix4f, immediate, false, 0, 15728880, false);
                    }
                }
            }
        }

        immediate.draw();

        for(m = 0; m < this.text.length; ++m) {
            string = this.text[m];
            if (string != null && m == this.currentRow && j >= 0) {
                int q = this.client.textRenderer.getWidth(string.substring(0, Math.max(Math.min(j, string.length()), 0)));
                o = q - this.client.textRenderer.getWidth(string) / 2;
                if (bl2 && j < string.length()) {
                    int var31 = l - 1;
                    int var10003 = o + 1;
                    Objects.requireNonNull(this.client.textRenderer);
                    fill(matrices, o, var31, var10003, l + 9, -16777216 | i);
                }

                if (k != j) {
                    p = Math.min(j, k);
                    int r = Math.max(j, k);
                    int s = this.client.textRenderer.getWidth(string.substring(0, p)) - this.client.textRenderer.getWidth(string) / 2;
                    int t = this.client.textRenderer.getWidth(string.substring(0, r)) - this.client.textRenderer.getWidth(string) / 2;
                    int u = Math.min(s, t);
                    int v = Math.max(s, t);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.getBuffer();
                    RenderSystem.setShader(GameRenderer::getPositionColorShader);
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                    float var32 = (float)u;
                    Objects.requireNonNull(this.client.textRenderer);
                    bufferBuilder.vertex(matrix4f, var32, (float)(l + 9), 0.0F).color(0, 0, 255, 255).next();
                    var32 = (float)v;
                    Objects.requireNonNull(this.client.textRenderer);
                    bufferBuilder.vertex(matrix4f, var32, (float)(l + 9), 0.0F).color(0, 0, 255, 255).next();
                    bufferBuilder.vertex(matrix4f, (float)v, (float)l, 0.0F).color(0, 0, 255, 255).next();
                    bufferBuilder.vertex(matrix4f, (float)u, (float)l, 0.0F).color(0, 0, 255, 255).next();
                    BufferRenderer.drawWithShader(bufferBuilder.end());
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        matrices.pop();
        DiffuseLighting.enableGuiDepthLighting();
        super.render(matrices, mouseX, mouseY, delta);
    }
}
