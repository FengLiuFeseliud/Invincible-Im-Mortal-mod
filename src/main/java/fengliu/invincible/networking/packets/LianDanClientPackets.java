package fengliu.invincible.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class LianDanClientPackets {
    
    public static void syncData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if(client.player == null){
            return;
        }
    }
}
