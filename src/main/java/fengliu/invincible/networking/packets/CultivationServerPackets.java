package fengliu.invincible.networking.packets;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.CultivationCilentData.CultivationLevel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class CultivationServerPackets {
    private static boolean CAN_SEND_CAN_UP_LEVEL_MSG = false;
    private static int OLD_LEVEL = 0;
    
    public static void reiki_practice(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        
        int up_level = cultivationData.addCultivationExpInUpLevel(1000);
        if(up_level > OLD_LEVEL){
            OLD_LEVEL = up_level;
            CAN_SEND_CAN_UP_LEVEL_MSG = true;
        }

        if(!CAN_SEND_CAN_UP_LEVEL_MSG){
            return;
        }

        CultivationLevel is_level = cultivationData.getCultivationLevel();

        int is_level_int = is_level.getLevel();
        int can_up_level = cultivationData.getUpLevel().getLevel() - 1;

        player.sendMessage(new TranslatableText("info.invincible.can_up_level", 
            CultivationServerData.getAllCultivationLevel()[
                cultivationData.getCultivationLevel().getLevel() + up_level
            ].getLevelName().getString(),
            up_level
        ), false);

        for(CultivationLevel level: CultivationServerData.getAllCultivationLevel()){
            int in_level = level.getLevel();
            if(in_level <= is_level_int){
                continue;
            }

            if(in_level > can_up_level){
                break;
            }
            player.sendMessage(
                new LiteralText(level.getLevelName().getString() + " " + (float) level.getUpLevelRate()), 
            false);
        }

        CAN_SEND_CAN_UP_LEVEL_MSG = false;
    }

    public static void cultivation_up(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        if(cultivationData.addCultivationExpInUpLevel(0) == 0){
            player.sendMessage(new TranslatableText("info.invincible.cannot_up_level",
                cultivationData.getUpLevel().getLevelName().getString(),
                cultivationData.getCultivationLevel().getUpLevelExp() - cultivationData.getCultivationExp()
            ), false);

            return;
        }

        if(cultivationData.upLevel()){
            int onUesExp = cultivationData.getCultivationLevel().getUpLevelExp() - cultivationData.getCultivationExp();
            if(onUesExp < 0){
                onUesExp = 0;
            }
            player.sendMessage(new TranslatableText("info.invincible.successful_up_level",
                cultivationData.getCultivationLevel().getLevelName().getString(),
                cultivationData.getUpLevel().getLevelName().getString(),
                onUesExp
            ), false);

            OLD_LEVEL -= 1;
            return;
        }

        player.sendMessage(new TranslatableText("info.invincible.unsuccessful_up_level",
            cultivationData.getCultivationLevel().getLevelName().getString(),
            cultivationData.getCultivationExp()
        ), false);
    }

    public static void cultivation_info (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        CultivationLevel cultivationLevel = cultivationData.getCultivationLevel();
        int mana = ((IEntityDataSaver) player).getPersistentData().getInt("mana");
        player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " mana " + mana), false);
    }

    public static void clear_mana(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();

        if(cultivationData.consumeMana(10)){
            player.sendMessage(new LiteralText("成功消耗灵力"), false);
        }else{
            player.sendMessage(new LiteralText("灵力不足"), false);
        }
    }
}
