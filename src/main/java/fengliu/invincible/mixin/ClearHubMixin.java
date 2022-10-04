package fengliu.invincible.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.font.FontType;
import net.minecraft.client.font.TextRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;


@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class ClearHubMixin {
    private int maxAbsorption = 0;

    private int oldAbsorption = 0;
    private int oldAbsorption_show = 0;
    private int showAbsorption = 0;
    private boolean oldAbsorption_show_end = false;

    private int oldHealth = 0;
    private int oldHealth_show = 0;
    private int showHealth = 0;
    private boolean oldHealth_show_end = false;

    private static final Identifier BARS_TEXTURE = new Identifier(
        invincibleMod.MOD_ID, "textures/gui/bars.png"
    );

    @Shadow
    @Final
    private MinecraftClient client;

    @Overwrite
    private void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking){
        /*
         * 重写渲染血条
         * 
         * maxHealth 最大生命值
         * lastHealth 最新生命值
         * health 之前的生命值
         * absorption 伤害吸收值
         */
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BARS_TEXTURE);

        invincibleMod.LOGGER.info(oldAbsorption_show + " " + oldAbsorption + " " + absorption + " " + oldAbsorption_show_end);
        // 渲染空生命值条
        DrawableHelper.drawTexture(matrices, x, y, 0, 27, 81, 9, 81, 62);

        // 渲染生命值条
        int healthBarWidth = Math.round((81 / maxHealth) * lastHealth);
        
        if(healthBarWidth > 20){
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, healthBarWidth, 9, 81, 62);
        }else{
            DrawableHelper.drawTexture(matrices, x, y, 0, 36, healthBarWidth, 9, 81, 62);
            DrawableHelper.drawTexture(matrices, x - 5, y, 0, 52, 4, 8, 81, 60);
        }
        
        // 渲染空伤害吸收值条
        DrawableHelper.drawTexture(matrices, x, y - 10, 0, 27, 81, 9, 81, 62);
        /*
         * 如果最大伤害吸收值为 0 设置 maxAbsorption 为 absorption
         * 如果最大伤害吸收值小于更新的 absorption 设置 maxAbsorption 为新的 absorption
         */
        if(maxAbsorption == 0 || absorption > maxAbsorption){
            maxAbsorption = absorption;
        }

        if(absorption != 0){
            // 渲染伤害吸收值条
            int absorptionBarWidth = (int) (81 / maxAbsorption) * absorption;
            if(absorptionBarWidth > 20){
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 18, absorptionBarWidth, 9, 81, 62);
            }else{
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 45, absorptionBarWidth, 9, 81, 62);
                DrawableHelper.drawTexture(matrices, x - 5, y - 10, 0, 54, 4, 8, 81, 62);
            }
        }else{
            // 没有伤害吸收值时重置最大伤害吸收值
            maxAbsorption = 0;
        }

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        if(absorption > oldAbsorption){
            oldAbsorption = absorption;
        }

        if(absorption != oldAbsorption && oldAbsorption_show_end){
            oldAbsorption_show_end = false;
            oldAbsorption_show = 100;
            showAbsorption = oldAbsorption - absorption;
        }else if(oldAbsorption_show > 0){
            oldAbsorption_show -= 1;
        }else{
            oldAbsorption_show_end = true;
            oldAbsorption = absorption;
        }

        if(!oldAbsorption_show_end){
            renderer.draw(matrices, "-" + showAbsorption, x + 81 + 5, y - 10, 0xffffff);
        }else{
            renderer.draw(matrices, "     " , x + 81 + 5, y, 0xffffff);
        }

        if(lastHealth > oldHealth){
            oldHealth = lastHealth;
        }

        if(lastHealth != oldHealth && oldHealth_show_end){
            oldHealth_show_end = false;
            oldHealth_show = 100;
            showHealth = oldHealth - lastHealth;
        }else if(oldHealth_show > 0){
            oldHealth_show -= 1;
        }else{
            oldHealth_show_end = true;
            oldHealth = lastHealth;
        }

        if(!oldHealth_show_end){
            renderer.draw(matrices, "-" + showHealth, x + 81 + 5, y, 0xffffff);
        }else{
            renderer.draw(matrices, "     " , x + 81 + 5, y, 0xffffff);
        }

    }

    @Overwrite
    private int getHeartCount(LivingEntity entity) {
        // 清空饥饿条 护甲条是被写死了改不了只能盖住, 我服
        return 1;
    }
    
}
