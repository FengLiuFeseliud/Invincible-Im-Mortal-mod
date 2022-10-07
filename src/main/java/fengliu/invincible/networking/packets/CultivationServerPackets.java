package fengliu.invincible.networking.packets;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.ReikiItemData;
import fengliu.invincible.util.CultivationCilentData.CultivationLevel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
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
        
        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        
        int up_level = cultivationData.addCultivationExpInUpLevel(10 + ReikiItemData.absord(1000, itemStack));
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

    public static void cultivation_item (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        if(!ReikiItemData.canInjectionReiki(itemStack)){
            player.sendMessage(new TranslatableText("info.invincible.not_can_injection_reiki_item", itemStack.getName().getString()), false);
            return;
        }

        if(ReikiItemData.isExceedTargetReiki(itemStack)){
            player.sendMessage(new TranslatableText("info.invincible.is_exceed_injection_target", itemStack.getName().getString()), false);
            return;
        }

        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        int injectionReiki = ReikiItemData.tryInjection(100, false, itemStack);
        int consumeMana = Math.round(injectionReiki / 5);
        if(!cultivationData.consumeMana(consumeMana)){
            player.sendMessage(new TranslatableText("info.invincible.mana_injection_insufficient", consumeMana - cultivationData.getMana()), false);
            return;
        }

        ReikiItemData.injection(injectionReiki, itemStack);
    }

    public static void cultivation_info (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        CultivationLevel cultivationLevel = cultivationData.getCultivationLevel();
        int mana = cultivationData.getMana();
        player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " mana " + mana), false);
    }

    public static void consumeMana(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        cultivationData.consumeMana(buf.readInt());
    }
}
