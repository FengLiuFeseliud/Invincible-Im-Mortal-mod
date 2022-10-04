package fengliu.invincible.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


import fengliu.invincible.invincibleMod;


@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class ClearHubMixin {
    private static final Identifier BARS_TEXTURE = new Identifier(
        invincibleMod.MOD_ID, "texture/gui/bars.png"
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
        // matrices.loadIdentity(BARS_TEXTURE);
        return;
    }

    // @Overwrite
    // private void renderMountJumpBar(MatrixStack matrices, int x) {
    //     return;
    // }
    
}
