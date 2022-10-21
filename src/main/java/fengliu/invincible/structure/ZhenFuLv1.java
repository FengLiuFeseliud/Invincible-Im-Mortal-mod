package fengliu.invincible.structure;

import fengliu.invincible.screen.handler.JuLingZhenLv1ScreenHandler;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.text.TranslatableText;
import fengliu.invincible.structure.zhenfu.juLingZhen;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;

public class ZhenFuLv1 implements ZhenFus {
    private static final ZhenFuSettings[] zhenFuSettings = {
        new juLingZhen()
            .setName(new TranslatableText("zhen_fu.invincible.ju_ling_zhen.1"))
            .setScreenHandler(JuLingZhenLv1ScreenHandler.class)
            .setNeedJadeNumber(4)
            .setHighly(10)
        
    };

    @Override
    public ZhenFuSettings[] getZhenFuSettings() {
        return zhenFuSettings;
    }
}
