package fengliu.invincible.api;

import fengliu.invincible.util.CultivationData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.CultivationData.CultivationLevel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class Key {

    private static int spawn_reiki_sleep = 0;
    private static boolean CAN_SEND_CAN_UP_LEVEL_MSG = false;
    private static int OLD_LEVEL = 0;

    public static void cultivation_up(ClientWorld clientWorld, ServerWorld serverWorld, PlayerEntity player){
        CultivationData cultivationData = ((IEntityDataSaver) player).getCultivationData();
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

    public static void reiki_practice(ClientWorld clientWorld, ServerWorld serverWorld, PlayerEntity player){
        if(spawn_reiki_sleep != 0){
            spawn_reiki_sleep -= 1;
            return;
        }
        spawn_reiki_sleep = 5;
        CultivationData cultivationData = ((IEntityDataSaver) player).getCultivationData();
        
        int up_level = cultivationData.addCultivationExpInUpLevel(10000);
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
            CultivationData.getAllCultivationLevel()[
                cultivationData.getCultivationLevel().getLevel() + up_level
            ].getLevelName().getString(),
            up_level
        ), false);

        for(CultivationLevel level: CultivationData.getAllCultivationLevel()){
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

    public static void cultivation(ClientWorld world, ServerWorld serverWorld, PlayerEntity player) {
        CultivationData cultivationData = ((IEntityDataSaver) player).getCultivationData();
        CultivationLevel cultivationLevel = cultivationData.getCultivationLevel();
        // player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " Attack " + cultivationLevel.playerAttack(player) + " MaxHealih " + cultivationLevel.playereHealih(player)), false);
        player.sendMessage(new LiteralText("Lavel " + cultivationLevel.getLevel() + " LevelName " + cultivationLevel.getLevelName().getString() + " CultivationExp " + cultivationData.getCultivationExp() + " UpLevelExp " + cultivationLevel.getUpLevelExp() + " Attack "), false);
    }
    
}
