package fengliu.invincible.networking.packets;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.item.ManaSword;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
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
import net.minecraft.entity.LivingEntity;


public class CultivationServerPackets {
    
    public static void reiki_practice(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        
        cultivationData.addCultivationExpInUpLevel(10 + ReikiItemData.absord(1000, itemStack));
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
        player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " BaseAttack " + cultivationLevel.getBaseAttack() + " mana " + mana), false);
    }

    public static void consumeMana(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        cultivationData.consumeMana(buf.readInt());
        invincibleMod.LOGGER.info("" + ((LivingEntity) player).getMaxHealth());
        ((LivingEntity) player).setHealth(100);
    }

    public static void setUesSkill(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler hendler, PacketByteBuf buf, PacketSender respsonSender){
        ItemStack stack = player.getStackInHand(player.getActiveHand());
        Item item = stack.getItem();

        if(item instanceof ManaSword){
            ((ManaSword) item).setManaSkillSettings(player);
            return;
        }

    }
}
