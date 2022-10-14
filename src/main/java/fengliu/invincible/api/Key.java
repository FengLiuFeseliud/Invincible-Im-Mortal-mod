package fengliu.invincible.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import fengliu.invincible.networking.ModMessage;
import fengliu.invincible.util.SpawnParticle;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class Key {

    private static int spawn_reiki_sleep = 0;

    public static void cultivation_up(MinecraftClient client){
        ClientPlayNetworking.send(ModMessage.CULTIVATION_UP, PacketByteBufs.create());
    }

    public static void reiki_practice(MinecraftClient client){
        SpawnParticle.absordRaiki(client.player);
        
        if(spawn_reiki_sleep != 0){
            spawn_reiki_sleep -= 1;
            return;
        }
        spawn_reiki_sleep = 5;
        ClientPlayNetworking.send(ModMessage.REIKI_PRACTICE, PacketByteBufs.create());
    }

    public static void cultivation_info(MinecraftClient client) {
        ClientPlayNetworking.send(ModMessage.CULTIVATION_INFO, PacketByteBufs.create());
    }

    public static void clear_mana(MinecraftClient client){
        PacketByteBuf pack = PacketByteBufs.create();
        pack.writeInt(10);
        
        ClientPlayNetworking.send(ModMessage.CONSUME_MANA, pack);
    }

    public static void cultivation_item(MinecraftClient client) {
        if(spawn_reiki_sleep != 0){
            spawn_reiki_sleep -= 1;
            return;
        }
        spawn_reiki_sleep = 5;
        ClientPlayNetworking.send(ModMessage.CULTIVATION_ITEM, PacketByteBufs.create());
    }
    
    public static void set_ues_skill(MinecraftClient client){
        ClientPlayNetworking.send(ModMessage.SET_SKILL, PacketByteBufs.create());
    }
}
