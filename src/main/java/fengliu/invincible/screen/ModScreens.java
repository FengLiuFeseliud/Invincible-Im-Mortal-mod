package fengliu.invincible.screen;

import fengliu.invincible.screen.handler.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {
    
    public static void registerAllScreen(){
        // 角磨机 UI
		HandledScreens.register(ModScreenHandlers.ANGLE_GRINDER_SCREENHANDLER, AngleGrinderScreen::new);
		// 注灵台 UI
		HandledScreens.register(ModScreenHandlers.INJECTION_REIKI_STANDS_SCREENHANDLER, InjectionReikiStandsScreen::new);
		HandledScreens.register(ModScreenHandlers.DAN_LU_STANDS_SCREENHANDLER, DanLuScreen::new);
		// 错误阵法 UI
		HandledScreens.register(ModScreenHandlers.NOT_ZHEN_FU_SCREENHANDLER, NotZhenFuScreen::new);
		// 一阶聚灵阵 UI
		HandledScreens.register(ModScreenHandlers.JU_LING_ZHEN_LV1_SCREENHANDLER, JuLingZhenLv1Screen::new);
    }
}
