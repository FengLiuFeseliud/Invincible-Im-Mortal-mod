package fengliu.invincible.event;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModServerTickEvent{

    private static int playerTick_count = 0;
    private static int skillsTick_count = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            recoverPlayerMana(server);
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            recoverSkills(server);
        });
    }

    public static void recoverPlayerMana(MinecraftServer server){
        if(playerTick_count < 20){
            playerTick_count += 1;
            return;
        }

        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){

            CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
            cultivationData.recoverMana(10);
            cultivationData.syncData((IEntityDataSaver) player);
        }
        playerTick_count = 0;
        
    }

    public static void recoverSkills(MinecraftServer server){
        if(skillsTick_count < 20){
            skillsTick_count += 1;
            return;
        }

        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){

            player.getItemCooldownManager();
        }
        skillsTick_count = 0;
        
    }
}
