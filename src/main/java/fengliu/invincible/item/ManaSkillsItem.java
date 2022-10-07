package fengliu.invincible.item;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fengliu.invincible.util.CultivationServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ManaSkillsItem{

    default void skillInCD(PlayerEntity player, ManaSkillSettings skillsSettings){
        player.sendMessage(new TranslatableText("info.invincible.skill_in_cd", skillsSettings.Name.getString()), true);
    }

    default void manaInsufficient(PlayerEntity player, ManaSkillSettings skillsSettings){
        player.sendMessage(new TranslatableText("info.invincible.mana_insufficient", skillsSettings.Name.getString()), true);
    }

    default boolean tryUesSkill(ManaSkillSettings skillsSettings, PlayerEntity player, int allMana){
        switch (skillsSettings.canUesSkill(allMana)) {
            case SKILL_UES:
                return true;
            case SKILL_IN_CD:
                skillInCD(player, skillsSettings);
                return false;
            case MANA_INSUFFICIENT:
                manaInsufficient(player, skillsSettings);
                return false;
            default:
                return false;
        }
    }

    public class ManaSkillSettings {
        public TranslatableText Name = new TranslatableText("info.skill_default_name");
        public int Consume = 0;
        public float SkillCD = 0;
        public boolean CanUse = true;
        public final Skill Skill;
        public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    
        public ManaSkillSettings(Skill skillFunction){
            Skill = skillFunction;
        }

        public ManaSkillSettings setName(TranslatableText name){
            Name = name;
            return this;
        }
        
        public ManaSkillSettings setConsume(int consume){
            Consume = consume;
            return this;
        }

        public ManaSkillSettings setSkillCd(int skillCD){
            SkillCD = skillCD;
            return this;
        }
    
        private void setCdIn(){
            CanUse = true;
        }
    
        public SkillState canUesSkill(int manaAll){
            if(!CanUse){
                return SkillState.SKILL_IN_CD;
            }

            if(Consume > manaAll){
                return SkillState.MANA_INSUFFICIENT;
            }

            if(SkillCD == 0){
                return SkillState.SKILL_UES;
            }
            CanUse = false;
    
            scheduledExecutorService.schedule(() ->  setCdIn(), (long) SkillCD, TimeUnit.SECONDS);
            return SkillState.SKILL_UES;
        }

        interface Skill {
            boolean function(World world, PlayerEntity player, Hand hand, CultivationServerData cultivationServerData);
        }

        enum SkillState {
            SKILL_UES, SKILL_IN_CD, MANA_INSUFFICIENT
        }
    }
}