package fengliu.invincible.api;

import fengliu.invincible.entity.ModEntitys;
import fengliu.invincible.entity.Reiki;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;

public class Key {

    private static int spawn_reiki_sleep = 0;

    public static void reiki_practice(ClientWorld clientWorld, ServerWorld serverWorld, ClientPlayerEntity player){
        if(spawn_reiki_sleep != 0){
            spawn_reiki_sleep -= 1;
            return;
        }

        Reiki reiki = new Reiki(ModEntitys.REIKI, serverWorld);
        reiki.setPosition(player.getPos());
        if(serverWorld.spawnEntity(reiki)){
            spawn_reiki_sleep = 5;
        }
    }
    
}
