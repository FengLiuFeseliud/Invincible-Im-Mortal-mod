package fengliu.invincible.item;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fengliu.invincible.invincibleMod;

public class ManaSettings {
    private int Consume;
    private float SkillCd;
    private boolean CanUse = false;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public ManaSettings(int consume, float skillCd){
        Consume = consume;
        SkillCd = skillCd;
    }

    public int getConsume(){
        return Consume;
    }

    public float getSkillCd(){
        return SkillCd;
    }

    private void setCdIn(){
        invincibleMod.LOGGER.info("run3..");
        CanUse = true;
    }

    public boolean canUesSkill(int manaAll){
        if(Consume > manaAll && CanUse){
            return false;
        }
        CanUse = false;

        scheduledExecutorService.schedule(() ->  setCdIn(), (long) SkillCd, TimeUnit.SECONDS);
        return true;
    }
}
