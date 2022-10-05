package fengliu.invincible.event;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModServerTickEvent{

    private static int playerTick_count = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            recoverPlayerMana(server);
        });
    }

    public static void recoverPlayerMana(MinecraftServer server){
        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){
            if(playerTick_count < 20){
                playerTick_count += 1;
                return;
            }

            CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
            cultivationData.recoverMana(10);
            cultivationData.syncData((IEntityDataSaver) player);

            playerTick_count = 0;
        }
    }
}
