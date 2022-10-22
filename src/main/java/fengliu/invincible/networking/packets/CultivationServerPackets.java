package fengliu.invincible.networking.packets;

import fengliu.invincible.item.ManaPickaxe;
import fengliu.invincible.item.ManaSword;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.KungFuServerData;
import fengliu.invincible.util.ReikiItemData;
import fengliu.invincible.util.CultivationCilentData.CultivationLevel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;


public class CultivationServerPackets {
    
    public static void reiki_practice(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        KungFuServerData kungFuServerData = ((IEntityDataSaver) player).getKungFuServerData();

        ItemStack mainStack = player.getMainHandStack();
        ItemStack offStack = player.getOffHandStack();

        if(kungFuServerData.addKungFuProficiencyFromItemStack(mainStack)){
            kungFuServerData.learnKungFu(mainStack);
        }

        if(kungFuServerData.addKungFuProficiencyFromItemStack(offStack)){
            kungFuServerData.learnKungFu(offStack);
        }

        int absordReiki = 0;
        absordReiki = ReikiItemData.absord(500, mainStack);
        if(absordReiki == 0){
            absordReiki = ReikiItemData.absord(500, offStack);
        } 

        cultivationData.addCultivationExpInUpLevel(Math.round(10 * cultivationData.getGain()) + absordReiki);
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

            return;
        }

        player.sendMessage(new TranslatableText("info.invincible.unsuccessful_up_level",
            cultivationData.getCultivationLevel().getLevelName().getString(),
            cultivationData.getCultivationExp()
        ), false);
    }

    public static void cultivation_item (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        if(itemStack.isEmpty()){
            return;
        }
        
        if(!ReikiItemData.canInjectionReiki(itemStack)){
            player.sendMessage(new TranslatableText("info.invincible.not_can_injection_reiki_item", itemStack.getName().getString()), false);
            return;
        }

        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        if(!cultivationData.consumeMana(20)){
            player.sendMessage(new TranslatableText("info.invincible.mana_injection_insufficient", 20 - cultivationData.getMana()), false);
            return;
        }
        ReikiItemData.injectionToNewItemEntity(player.getWorld(), player, 100, itemStack);
    }

    public static void cultivation_info (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        CultivationLevel cultivationLevel = cultivationData.getCultivationLevel();
        int mana = cultivationData.getMana();
        player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " BaseAttack " + cultivationLevel.getBaseAttack() + " mana " + mana), false);
    }

    public static void consumeMana(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        // player.sendMessage(new LiteralText(KungFuData.test()), false);
        ((IEntityDataSaver) player).getKungFuServerData().upKungFuTiek();
    }

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
