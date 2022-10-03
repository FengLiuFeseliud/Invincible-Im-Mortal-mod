package fengliu.invincible.screen.handler;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers{
    private static final String MOD_ID = invincibleMod.MOD_ID;
    public static ScreenHandlerType<Angle_Grinder_ScreenHandler> ANGLE_GRINDER_SCREENHANDLER;
	public static ScreenHandlerType<Zhen_Yan_ScreenHandler.Lv1> ZHEN_YAN_1_SCREENHANDLER;

    public static void registerAllScreenHandlers(){
        // 角磨机 UI
		ANGLE_GRINDER_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_angle_grinder_1"), Angle_Grinder_ScreenHandler::new);
		// 一阶阵眼 UI
		ZHEN_YAN_1_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_zhen_yan_1"), Zhen_Yan_ScreenHandler.Lv1::new);
    }
}
