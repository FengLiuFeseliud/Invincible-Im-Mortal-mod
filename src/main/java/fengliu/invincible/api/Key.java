package fengliu.invincible.api;

import net.minecraft.client.MinecraftClient;

import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class Key {

    private static int spawn_reiki_sleep = 0;

    public static void cultivation_up(MinecraftClient client){
        ClientPlayNetworking.send(ModMessage.CULTIVATION_UP, PacketByteBufs.create());
    }

    public static void reiki_practice(MinecraftClient client){
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
        ClientPlayNetworking.send(ModMessage.CLEAR_MANA, PacketByteBufs.create());
    }
    
}
