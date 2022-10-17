package fengliu.invincible.screen.handler;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers{
    private static final String MOD_ID = invincibleMod.MOD_ID;
    public static ScreenHandlerType<AngleGrinderScreenHandler> ANGLE_GRINDER_SCREENHANDLER;
    public static ScreenHandlerType<InjectionReikiStandsScreenHandler> INJECTION_REIKI_STANDS_SCREENHANDLER;
    public static ScreenHandlerType<NotZhenFuScreenHandler> NOT_ZHEN_FU_SCREENHANDLER;
	public static ScreenHandlerType<JuLingZhenLv1ScreenHandler> JU_LING_ZHEN_LV1_SCREENHANDLER;

    public static void registerAllScreenHandlers(){
        // 角磨机 UI
		ANGLE_GRINDER_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_angle_grinder_1"), AngleGrinderScreenHandler::new);
        // 注灵台 UI
        INJECTION_REIKI_STANDS_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_injection_reiki_stands"), InjectionReikiStandsScreenHandler::new);
        // 错误阵法 UI
		NOT_ZHEN_FU_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "not_zhen_fu"), NotZhenFuScreenHandler::new);
        // 一阶聚灵阵 UI
		JU_LING_ZHEN_LV1_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ju_ling_zhen_lv1"), JuLingZhenLv1ScreenHandler::new);
    }
}
