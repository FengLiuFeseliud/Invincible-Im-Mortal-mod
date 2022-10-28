package fengliu.invincible.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import fengliu.invincible.item.ManaPickaxe;
import fengliu.invincible.item.ManaSword;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.KungFuServerData;


public class PlayerServerPackets {

    public static void setUesSkill(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ItemStack stack = player.getStackInHand(player.getActiveHand());
        Item item = stack.getItem();

        if(item instanceof ManaSword){
            ((ManaSword) item).setManaSkillSettings(player);
            return;
        }

        if(item instanceof ManaPickaxe){
            ((ManaPickaxe) item).setManaSkillSettings(player);
            return;
        }

    }

    public static void setUesKungFu(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ((IEntityDataSaver) player).getKungFuServerData().setNextUesKungFu();
    }

    public static void setUesKungFuGroup(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ((IEntityDataSaver) player).getKungFuServerData().setNextUesKungFuGroup();
    }

    public static void uesKungFu(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        KungFuServerData kungFuServerData = ((IEntityDataSaver) player).getKungFuServerData();
        if(!kungFuServerData.consume()){
            return;
        }

        kungFuServerData.ues().function(server, player);
        kungFuServerData.comboUes();
        kungFuServerData.getUesKungFuSettings().comboToNext(server, player);
        if(kungFuServerData.addKungFuProficiency()){
            kungFuServerData.upKungFuTiek();

        }
    }
}
