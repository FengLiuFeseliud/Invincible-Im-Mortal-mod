package fengliu.invincible.client.block.entity.renderer;

import fengliu.invincible.block.entity.BookShelfEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BookShelfRenderer implements BlockEntityRenderer<BookShelfEntity> {

    public BookShelfRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(BookShelfEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        // matrices.translate(0.85, 0.595, 0.923);
        // matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(100));

        // int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        
        // ItemStack test = new ItemStack(ModItems.JU_QI_KUNG, 1);
        // MinecraftClient.getInstance().getItemRenderer().renderItem(test, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

        matrices.pop();
    }
}
