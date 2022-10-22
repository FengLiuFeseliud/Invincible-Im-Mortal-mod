package fengliu.invincible.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallback;
import net.minecraft.world.World;

public class ModTimer {
    /**
     * 封装 mc 计时器
     * @param world 世界
     * @param name 定时任务名
     * @param tick 多少 tick 后执行
     * @param callback 回调
     */
    public static void timer(World world, String name, long tick, TimerCallback<MinecraftServer> callback){
        if(world.isClient){
            return;
        }

        MinecraftServer server = world.getServer();
        Timer<MinecraftServer> stimer = world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents();
        stimer.setEvent(name + "_" + stimer.getEventNames().size(), server.getOverworld().getTime() + tick, callback);
    }

    public static void timer(MinecraftServer server, String name, long tick, TimerCallback<MinecraftServer> callback){
        Timer<MinecraftServer> stimer = server.getSaveProperties().getMainWorldProperties().getScheduledEvents();
        stimer.setEvent(name + "_" + stimer.getEventNames().size(), server.getOverworld().getTime() + tick, callback);
    }
    
}
