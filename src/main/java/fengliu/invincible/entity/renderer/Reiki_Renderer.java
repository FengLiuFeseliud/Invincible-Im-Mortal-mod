package fengliu.invincible.entity.renderer;

import fengliu.invincible.invincibleMod;
import net.minecraft.client.render.entity.ExperienceOrbEntityRenderer;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.Identifier;

public class Reiki_Renderer extends ExperienceOrbEntityRenderer {

    private static final String MOD_ID = invincibleMod.MOD_ID;

    public Reiki_Renderer(net.minecraft.client.render.entity.EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(ExperienceOrbEntity experienceOrbEntity) {
        return new Identifier(MOD_ID, "textures/entity/reiki_stone_5.png");
    }

}
