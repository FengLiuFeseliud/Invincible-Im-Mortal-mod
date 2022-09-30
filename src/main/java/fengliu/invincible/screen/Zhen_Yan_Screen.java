package fengliu.invincible.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.screen.handler.Zhen_Yan_ScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Zhen_Yan_Screen{

    private static final String MOD_ID = invincibleMod.MOD_ID;

    public static class Lv1 extends HandledScreen<Zhen_Yan_ScreenHandler.Lv1>{

        private static final Identifier TEXTTURE = new Identifier(MOD_ID, "textures/gui/zhen_yan_1.png");

        public Lv1(Zhen_Yan_ScreenHandler.Lv1 handler, PlayerInventory inventory, Text title) {
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
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            this.renderBackground(matrices);
            super.render(matrices, mouseX, mouseY, delta);

            this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        }

    }
    
}
