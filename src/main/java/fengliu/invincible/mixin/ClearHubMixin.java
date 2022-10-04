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
import net.minecraft.client.font.TextRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.CultivationData;
import fengliu.invincible.util.IEntityDataSaver;


@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class ClearHubMixin {
    private int maxAbsorption = 0;

    private int oldAbsorption = 0;
    private int oldAbsorption_show = 0;
    private boolean oldAbsorption_show_end = false;

    private int oldHealth = 0;
    private int oldHealth_show = 0;
    private boolean oldHealth_show_end = false;

    private int oldMana = 0;
    private int oldMana_show = 0;
    private boolean oldMana_show_end = false;

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

        // 渲染空生命值条
        DrawableHelper.drawTexture(matrices, x, y, 0, 27, 81, 9, 81, 71);

        // 渲染生命值条
        int healthBarWidth = Math.round((81 / (float) maxHealth) * lastHealth);
        if(healthBarWidth > 20){
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, healthBarWidth, 9, 81, 71);
        }else{
            // 渲染 1/4 数值告紧
            DrawableHelper.drawTexture(matrices, x, y, 0, 36, healthBarWidth, 9, 81, 71);
            DrawableHelper.drawTexture(matrices, x - 5, y, 0, 52, 4, 8, 81, 71);
        }
        
        // 渲染空伤害吸收值条
        DrawableHelper.drawTexture(matrices, x, y - 10, 0, 27, 81, 9, 81,71);
        /*
         * 如果最大伤害吸收值为 0 设置 maxAbsorption 为 absorption
         * 如果最大伤害吸收值小于更新的 absorption 设置 maxAbsorption 为新的 absorption
         */
        if(maxAbsorption == 0 || absorption >= maxAbsorption){
            maxAbsorption = absorption;
        }

        if(absorption > 0){
            int absorptionBarWidth = Math.round((81 / (float) maxAbsorption) * absorption);
            // 渲染伤害吸收值条
            if(absorptionBarWidth > 20){
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 18, absorptionBarWidth, 9, 81, 71);
            }else{
                // 渲染 1/4 数值告紧
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 45, absorptionBarWidth, 9, 81, 71);
                DrawableHelper.drawTexture(matrices, x - 5, y - 10, 0, 54, 4, 8, 81, 71);
            }
        }else{
            // 没有伤害吸收值时重置最大伤害吸收值
            maxAbsorption = 0;
        }

        CultivationData cultivationData = ((IEntityDataSaver) player).getCultivationData();
        int mana = cultivationData.getMana();
        int maxMana = cultivationData.getCultivationLevel().getBaseMana();

        // 渲染空灵力值条
        DrawableHelper.drawTexture(matrices, x + 101, y, 0, 27, 81, 9, 81, 71);

        // 渲染灵力值条
        int manaBarWidth = Math.round((81 / (float) maxMana) * mana);
        if(manaBarWidth > 20){
            DrawableHelper.drawTexture(matrices, x + 101, y, 0, 9, manaBarWidth, 9, 81, 71);
        }else{
            // 渲染 1/4 数值告紧
            DrawableHelper.drawTexture(matrices, x + 101, y, 0, 62, manaBarWidth, 9, 81, 71);
            DrawableHelper.drawTexture(matrices, x + 186, y, 0, 54, 4, 8, 81, 71);
        }

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        
        if(absorption > oldAbsorption){
            oldAbsorption = absorption;
        }

        if(absorption != oldAbsorption && oldAbsorption_show_end){
            oldAbsorption_show_end = false;
            oldAbsorption_show = 100;
        }else if(oldAbsorption_show > 0){
            oldAbsorption_show -= 1;
        }else{
            oldAbsorption_show_end = true;
            oldAbsorption = absorption;
        }

        // 渲染伤害吸收减少数字
        if(!oldAbsorption_show_end){
            renderer.draw(matrices, "-" + (oldAbsorption - absorption), x + 81 - 15, y - 9, 0xCC9500);
        }else{
            renderer.draw(matrices, "     " , x + 81 - 15, y - 9, 0xCC9500);
        }


        if(lastHealth > oldHealth){
            oldHealth = lastHealth;
        }

        if(lastHealth != oldHealth && oldHealth_show_end){
            oldHealth_show_end = false;
            oldHealth_show = 100;
        }else if(oldHealth_show > 0){
            oldHealth_show -= 1;
        }else{
            oldHealth_show_end = true;
            oldHealth = lastHealth;
        }

        // 渲染生命值减少数字
        if(!oldHealth_show_end){
            renderer.draw(matrices, "-" + (oldHealth - lastHealth), x + 81 - 15, y + 1, 0xCC0000);
        }else{
            renderer.draw(matrices, "     " , x + 81 - 15, y + 1, 0xCC0000);
        }


        if(mana > oldMana){
            oldMana = mana;
        }

        if(mana != oldMana && oldMana_show_end){
            oldMana_show_end = false;
            oldMana_show = 100;
        }else if(oldMana_show > 0){
            oldMana_show -= 1;
        }else{
            oldMana_show_end = true;
            oldMana = mana;
        }

        // 渲染灵力值减少数字
        if(!oldMana_show_end){
            renderer.draw(matrices, "-" + (oldMana - mana), x + 182 - 15, y + 1, 0x009FCC);
        }else{
            renderer.draw(matrices, "     " , x + 182 - 15, y + 1, 0x009FCC);
        }

    }

    @Overwrite
    private int getHeartCount(LivingEntity entity) {
        // 清空饥饿条 护甲条是被写死了改不了只能盖住, 我服
        return 1;
    }
    
}
