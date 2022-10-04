package fengliu.invincible.networking.packets;

import net.minecraft.client.MinecraftClient;
import fengliu.invincible.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class CultivationClientPackets {
    
    public static void syncData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        NbtCompound nbt = buf.readNbt();
        ((IEntityDataSaver) client.player).getPersistentData().putInt("cultivation_level", nbt.getInt("cultivation_level"));
        ((IEntityDataSaver) client.player).getPersistentData().putInt("mana", nbt.getInt("mana"));
    }
}
