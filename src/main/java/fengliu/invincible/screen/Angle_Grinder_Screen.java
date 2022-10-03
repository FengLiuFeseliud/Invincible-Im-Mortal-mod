package fengliu.invincible.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.screen.handler.Angle_Grinder_ScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Angle_Grinder_Screen extends HandledScreen<Angle_Grinder_ScreenHandler>{

    private static final Identifier TEXTTURE = new Identifier(invincibleMod.MOD_ID, "textures/gui/angle_grinder.png");

    public Angle_Grinder_Screen(Angle_Grinder_ScreenHandler handler, PlayerInventory inventory, Text title) {
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
        
        int tick_draw_height = this.handler.getTickCount() * 27 / 200;
        if(tick_draw_height != 0){
            // 进度条不为空亮绿灯
            this.drawTexture(matrices, this.x + 72, this.y + 47, 176, 8, 8, 8);
        }else{
            // 进度条为空亮红灯
            this.drawTexture(matrices, this.x + 72, this.y + 31, 176, 0, 8, 8);
        }
        this.drawTexture(matrices, this.x + 84, this.y + 30, 176, 16, 7, tick_draw_height);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

}
