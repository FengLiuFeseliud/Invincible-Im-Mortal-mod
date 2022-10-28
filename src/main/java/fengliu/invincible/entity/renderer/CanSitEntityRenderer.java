package fengliu.invincible.entity.renderer;

import fengliu.invincible.entity.CanSitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CanSitEntityRenderer extends EntityRenderer<CanSitEntity> {
    private static final Identifier TEXTURE = new Identifier("minecraft","textures/entity/minecart.png");

    public CanSitEntityRenderer(Context entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.7F;
    }

    public void render(CanSitEntity abstractMinecartEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(abstractMinecartEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(CanSitEntity abstractMinecartEntity) {
        return TEXTURE;
    }
}
