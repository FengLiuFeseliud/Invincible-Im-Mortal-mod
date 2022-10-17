package fengliu.invincible.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.screen.handler.JuLingZhenLv1ScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JuLingZhenLv1Screen extends HandledScreen<JuLingZhenLv1ScreenHandler>{
    private static final Identifier TEXTTURE = new Identifier(invincibleMod.MOD_ID, "textures/gui/ju_ling_zhen_1.png");

    public JuLingZhenLv1Screen(JuLingZhenLv1ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = 167;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTTURE);
        
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int reiki_draw_height = 52;
        if(this.handler.getItemMaxReiki() != 0){
            reiki_draw_height = 52 - (this.handler.getItemReiki() * 52 / this.handler.getItemMaxReiki());
            this.drawTexture(matrices, this.x + 58, this.y + 15, 176, 0, 59, 56);
        }
        
        this.drawTexture(matrices, this.x + 144, this.y + 18, 176, 56, 11, reiki_draw_height);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
