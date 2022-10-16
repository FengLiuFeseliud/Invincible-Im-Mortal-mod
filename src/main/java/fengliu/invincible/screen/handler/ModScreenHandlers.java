package fengliu.invincible.screen.handler;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers{
    private static final String MOD_ID = invincibleMod.MOD_ID;
    public static ScreenHandlerType<Angle_Grinder_ScreenHandler> ANGLE_GRINDER_SCREENHANDLER;
    public static ScreenHandlerType<NotZhenFu_ScreenHandler> NOT_ZHEN_FU_SCREENHANDLER;
	public static ScreenHandlerType<JuLingZhen_Lv1_ScreenHandler> JU_LING_ZHEN_LV1_SCREENHANDLER;

    public static void registerAllScreenHandlers(){
        // 角磨机 UI
		ANGLE_GRINDER_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_angle_grinder_1"), Angle_Grinder_ScreenHandler::new);
        // 错误阵法 UI
		NOT_ZHEN_FU_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "not_zhen_fu"), NotZhenFu_ScreenHandler::new);
        // 一阶聚灵阵 UI
		JU_LING_ZHEN_LV1_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ju_ling_zhen_lv1"), JuLingZhen_Lv1_ScreenHandler::new);
    }
}
