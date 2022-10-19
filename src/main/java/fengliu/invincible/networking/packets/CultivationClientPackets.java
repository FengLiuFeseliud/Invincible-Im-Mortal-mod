package fengliu.invincible.networking.packets;

import net.minecraft.client.MinecraftClient;
import fengliu.invincible.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class CultivationClientPackets {
    
    public static void syncData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if(client.player == null){
            return;
        }
        
        NbtCompound nbt = buf.readNbt();
        ((IEntityDataSaver) client.player).getPersistentData().putInt("cultivation_level", nbt.getInt("cultivation_level"));
        ((IEntityDataSaver) client.player).getPersistentData().putInt("cultivation_exp", nbt.getInt("cultivation_exp"));
        ((IEntityDataSaver) client.player).getPersistentData().putInt("mana", nbt.getInt("mana"));
        ((IEntityDataSaver) client.player).getPersistentData().putInt("kung_fu_ues_in", nbt.getInt("kung_fu_ues_in"));
        ((IEntityDataSaver) client.player).getPersistentData().putInt("kung_fu_group_ues_in", nbt.getInt("kung_fu_group_ues_in"));
        ((IEntityDataSaver) client.player).getPersistentData().put("can_ues_kung_fu", nbt.get("can_ues_kung_fu"));
    }
}
