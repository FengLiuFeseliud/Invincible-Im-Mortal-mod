package fengliu.invincible.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.screen.handler.DanLuScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DanLuScreen extends HandledScreen<DanLuScreenHandler>{
    private static final Identifier TEXTTURE = new Identifier(invincibleMod.MOD_ID, "textures/gui/dan_lu.png");

    public DanLuScreen(DanLuScreenHandler handler, PlayerInventory inventory, Text title) {
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

        if(this.handler.isFireIn()){
            this.drawTexture(matrices, this.x + 126, this.y + 59, 176, 0, 14, 14);
        }

        int tick_draw_width = this.handler.getTickCount() * 24 / 200;
        this.drawTexture(matrices, this.x + 89, 70, 176, 14, tick_draw_width, 17);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
