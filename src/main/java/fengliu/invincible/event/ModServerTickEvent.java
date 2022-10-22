package fengliu.invincible.event;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.KungFuServerData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModServerTickEvent{

    private static int playerTick_count = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            recoverPlayerMana(server);
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            comboKungFu(server);
        });
    }

    /**
     * 每 20 tick 恢复玩家真元
     */
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

    /**
     * 每 tick 计算玩家功法 combo 
     */
    public static void comboKungFu(MinecraftServer server){
        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){
            IEntityDataSaver iplayer = (IEntityDataSaver) player;
            KungFuServerData KungFuServerData = iplayer.getKungFuServerData();
            NbtCompound combo_in = KungFuServerData.getComboIn();
            if(combo_in == null){
                continue;
            }

            NbtCompound nbt = KungFuServerData.getUesKungFuFromComboIn();
            if(nbt == null){
                continue;
            }

            int comboIn = nbt.getInt("combo_in");
            if(0 < comboIn){
                nbt.putInt("combo_in", --comboIn);
                KungFuServerData.syncData(iplayer);
                continue;
            }
            
            KungFuServerData.getUesKungFuSettings().comboEnd(server, player);
            nbt.putInt("tieks", 0);
            nbt.putInt("combo_in", nbt.getInt("combo_end"));
            iplayer.getPersistentData().remove("combo_in");

            KungFuServerData.syncData(iplayer);
        }
        
    }
}
