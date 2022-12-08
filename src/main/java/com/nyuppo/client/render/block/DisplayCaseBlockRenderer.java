package com.nyuppo.client.render.block;

import com.nyuppo.TheLittleThingsClient;
import com.nyuppo.block.DisplayCaseBlock;
import com.nyuppo.block.entity.DisplayCaseBlockEntity;
import com.nyuppo.item.HatItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class DisplayCaseBlockRenderer implements BlockEntityRenderer<DisplayCaseBlockEntity> {
    private static final float SCALE = 0.6f;
    private static final float BLOCK_SCALE = 1.2f;
    private static final float HAT_SCALE = 1.0f;

    private final ItemRenderer itemRenderer;
    private final ItemStack tempStack = new ItemStack(Items.APPLE);

    public DisplayCaseBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(DisplayCaseBlockEntity displayCaseBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Direction direction = displayCaseBlockEntity.getCachedState().get(DisplayCaseBlock.FACING);
        ItemStack stack = displayCaseBlockEntity.getItem();
        /*
        matrixStack.push();
        matrixStack.translate(0.5, 2.0, 0.5);
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        this.itemRenderer.renderItem(tempStack, ModelTransformation.Mode.GROUND, i, j, matrixStack, vertexConsumerProvider, 0);
        matrixStack.pop();
         */

        if (stack != null && !stack.isEmpty()) {
            matrixStack.push();
            matrixStack.translate(0.5, 0.4, 0.5);
            Direction dir2 = Direction.fromHorizontal(direction.getHorizontal());
            float g = dir2.asRotation();
            if (dir2 == Direction.NORTH || dir2 == Direction.SOUTH) {
                g += 180;
            }

            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
            matrixStack.push();
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(15f));
            matrixStack.translate(0.0, 0.0, -0.05);

            /*
            if (stack.getItem() instanceof BlockItem) {
                matrixStack.scale(BLOCK_SCALE, BLOCK_SCALE, BLOCK_SCALE);
            } else if (stack.getItem() instanceof HatItem) {
                matrixStack.scale(HAT_SCALE, HAT_SCALE, HAT_SCALE);
            } else {
                matrixStack.scale(SCALE, SCALE, SCALE);
            }
            */

            if (stack.getItem() instanceof HatItem) {
                matrixStack.scale(HAT_SCALE, HAT_SCALE, HAT_SCALE);
                matrixStack.translate(0.0, 0.2, 0.0);
            } else {
                matrixStack.scale(SCALE, SCALE, SCALE);
            }

            this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, 0);
            matrixStack.pop();
            matrixStack.pop();
        }



        /*
        if (!stack.isEmpty()) {
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            Direction dir2 = Direction.fromHorizontal(direction.getHorizontal());
            float g = -dir2.asRotation();

            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));

            matrixStack.scale(SCALE, SCALE, SCALE);
            this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k);
            matrixStack.pop();
        }
        */
    }
}
