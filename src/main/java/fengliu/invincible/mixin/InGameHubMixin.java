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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.client.font.TextRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.systems.RenderSystem;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.CultivationCilentData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.KungFuCilentData;
import fengliu.invincible.util.CultivationCilentData.CultivationLevel;
import fengliu.invincible.util.KungFuCilentData.KungFuSettings;
import fengliu.invincible.util.KungFuCilentData.KungFuTiekSettings;

/*
 * 重写原版状态条 以更好显示上千血量
 */

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHubMixin {
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

    private int cultivationExp_show = 0;

    private static final Identifier BARS_TEXTURE = new Identifier(
        invincibleMod.MOD_ID, "textures/gui/bars.png"
    );

    private static final int TEXTURE_Y = 81;
    private static final int TEXTURE_X = 182;

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
        DrawableHelper.drawTexture(matrices, x, y, 0, 27, 81, 9, TEXTURE_X, TEXTURE_Y);

        // 渲染生命值条
        int healthBarWidth = Math.round((81 / (float) maxHealth) * lastHealth);
        if(healthBarWidth > 20){
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, healthBarWidth, 9, TEXTURE_X, TEXTURE_Y);
        }else{
            // 渲染 1/4 数值告紧
            DrawableHelper.drawTexture(matrices, x, y, 0, 36, healthBarWidth, 9, TEXTURE_X, TEXTURE_Y);
            DrawableHelper.drawTexture(matrices, x - 5, y, 0, 54, 4, 8, TEXTURE_X, TEXTURE_Y);
        }
        
        // 渲染空伤害吸收值条
        DrawableHelper.drawTexture(matrices, x, y - 10, 0, 27, 81, 9, TEXTURE_X, TEXTURE_Y);
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
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 18, absorptionBarWidth, 9, TEXTURE_X, TEXTURE_Y);
            }else{
                // 渲染 1/4 数值告紧
                DrawableHelper.drawTexture(matrices, x, y - 10, 0, 45, absorptionBarWidth, 9, TEXTURE_X, TEXTURE_Y);
                DrawableHelper.drawTexture(matrices, x - 5, y - 10, 0, 54, 4, 8, TEXTURE_X, TEXTURE_Y);
            }
        }else{
            // 没有伤害吸收值时重置最大伤害吸收值
            maxAbsorption = 0;
        }

        CultivationCilentData cultivationData = ((IEntityDataSaver) player).getCilentCultivationData();
        int mana = cultivationData.getMana();
        int maxMana = cultivationData.getCultivationLevel().getBaseMana();

        // 渲染空真元值条
        DrawableHelper.drawTexture(matrices, x + 101, y, 0, 27, 81, 9, TEXTURE_X, TEXTURE_Y);

        // 渲染真元值条
        int manaBarWidth = Math.round((81 / (float) maxMana) * mana);
        if(manaBarWidth > 20){
            DrawableHelper.drawTexture(matrices, x + 101, y, 0, 9, manaBarWidth, 9, TEXTURE_X, TEXTURE_Y);
        }else{
            // 渲染 1/4 数值告紧
            DrawableHelper.drawTexture(matrices, x + 101, y, 0, 62, manaBarWidth, 9, TEXTURE_X, TEXTURE_Y);
            DrawableHelper.drawTexture(matrices, x + 186, y, 0, 54, 4, 8, TEXTURE_X, TEXTURE_Y);
        }

        int cultivationExp = cultivationData.getCultivationExp();
        int maxNeedCultivationExp = cultivationData.getNeedCultivationExp();

        // 渲染空修为值条
        DrawableHelper.drawTexture(matrices, x, 4, 0, 71, 182, 5, TEXTURE_X, TEXTURE_Y);
        
        // 如果当前修为大于当前境界最大修为, 按当前能够到达的境界渲染
        int cultivationExpBarWidth = 0;
        if(cultivationExp > maxNeedCultivationExp && cultivationExp > cultivationData.getUpLevel().getUpLevelExp()){
            // 超过最大境界渲染条
            cultivationExpBarWidth = 182;
        }else{
            // 计算渲染所需修为
            CultivationLevel level = cultivationData.getCultivationLevel();
            if(level.canUpLevel(cultivationExp)){
                level = cultivationData.getUpLevel();
                maxNeedCultivationExp = level.getNeedCultivationExp();
                cultivationExp_show = maxNeedCultivationExp + cultivationExp - level.getCultivationExp();
            }else{
                maxNeedCultivationExp = level.getNeedCultivationExp();
                cultivationExp_show = cultivationExp - level.getCultivationExp();
            }
            cultivationExpBarWidth = Math.round((182 / (float) maxNeedCultivationExp) * cultivationExp_show);
        }

        if(cultivationExpBarWidth > 182){
            cultivationExpBarWidth = 182;
        }
        // 渲染修为值条
        DrawableHelper.drawTexture(matrices, x, 4, 0, 76, cultivationExpBarWidth, 5, TEXTURE_X, TEXTURE_Y);

        MinecraftClient client = MinecraftClient.getInstance();
        if(client == null){
            return;
        }
        TextRenderer renderer = client.textRenderer;

        /**
         * 功法格
         */
        KungFuCilentData kungFuCilentData = ((IEntityDataSaver) player).getKungFuCilentData();
        // 渲染空功法格
        DrawableHelper.drawTexture(matrices, 5,  y - 5, 81, 0, 37, 37, TEXTURE_X, TEXTURE_Y);
        // 渲染空功法 combo 条
        DrawableHelper.drawTexture(matrices, 47,  y + 22, 118, 0, 60, 5, TEXTURE_X, TEXTURE_Y);

        int comboBarWidth;
        NbtCompound uesKungFu = kungFuCilentData.getUesKungFu();

        // 计算功法 combo 条
        if(uesKungFu == null){
            comboBarWidth = 0;
        }else{
            comboBarWidth = Math.round((60 / (float) uesKungFu.getInt("combo_end")) * uesKungFu.getInt("combo_in"));
        }
        KungFuSettings settings = kungFuCilentData.getUesKungFuSettings();

        if(settings != null){
            if(uesKungFu != null){
                KungFuTiekSettings tiek = kungFuCilentData.getKungFuNotUesTiek();
                if(tiek == null){
                    tiek = settings.getKungFuTiek(settings.getKungFuTiekIndex());
                }
                int proficiencyMax = tiek.Proficiency;
                int proficiencyBarWidth = Math.round((60 / (float) proficiencyMax) * kungFuCilentData.getKungFuProficiency());
                if(proficiencyBarWidth > 60){
                    proficiencyBarWidth = 60;
                }

                DrawableHelper.drawTexture(matrices, 47,  y + 29, 118, 0, 60, 5, TEXTURE_X, TEXTURE_Y);

                DrawableHelper.drawTexture(matrices, 47,  y + 29, 118, 10, proficiencyBarWidth, 5, TEXTURE_X, TEXTURE_Y);
            }

            // 渲染功法 combo 条
            DrawableHelper.drawTexture(matrices, 47,  y + 22, 118, 5, comboBarWidth, 5, TEXTURE_X, TEXTURE_Y);

            RenderSystem.setShaderTexture(0, settings.Texture);
            // 渲染功法图标
            DrawableHelper.drawTexture(matrices, 8,  y - 5, 0, 0, 32, 32, 32, 32);
            // 渲染功法名称
            renderer.draw(matrices, settings.Name.getString(), 47, y,  0xffffff);
            renderer.draw(matrices, kungFuCilentData.getUesKungFuTiekName(), 47, y + 10,  0xffffff);
        }

        
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

        // 渲染伤害吸收值
        String absorption_str = "" + absorption;
        renderer.draw(matrices, absorption_str, x + 41 - (float) (absorption_str.length() / 2 * 2.5), y - 9,  0xCC9500);

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

        // 渲染生命值
        String lastHealth_str = "" + lastHealth;
        renderer.draw(matrices, lastHealth_str, x + 41 - (float) (lastHealth_str.length() / 2 * 2.5), y + 1,  0xCC0000);

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

        String mana_str = "" + mana;
        // 渲染真元值
        renderer.draw(matrices, mana_str, x + 142 - (float) (mana_str.length() / 2 * 2.5), y + 1,  0x009FCC);

        // 渲染真元值减少数字
        if(!oldMana_show_end){
            renderer.draw(matrices, "-" + (oldMana - mana), x + 167, y + 1, 0x009FCC);
        }else{
            renderer.draw(matrices, "     " , x + 167, y + 1, 0x009FCC);
        }

        CultivationLevel level = cultivationData.getCultivationLevel();
        String levelName = level.getLevelName().getString();
        levelName = levelName + " " + cultivationExp + "/" + level.getUpLevelExp();
        // 渲染修为境界
        renderer.draw(matrices, levelName, x + 20, 2, 0xffffff);


    }

    @Overwrite
    private int getHeartCount(LivingEntity entity) {
        // 清空饥饿条 护甲条是被写死了改不了只能盖住, 我服
        return 1;
    }
    
}
