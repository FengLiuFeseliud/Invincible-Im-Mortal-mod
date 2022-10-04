package fengliu.invincible.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;

import fengliu.invincible.api.Probability_Random;
import fengliu.invincible.api.Probability_Random.Random_Item;

public class CultivationData {

    private static CultivationLevel[] CultivationLevelAll = {
        new CultivationLevel(0, "invincible.cultivation_level.0", 0, 10000, 100, 0, 0, 0),

        new CultivationLevel(1, "invincible.cultivation_level.1", 10000, 20000, 100, 0, 0, 0),
        new CultivationLevel(2, "invincible.cultivation_level.2", 20000, 31000, 99.9, 0, 0, 0),
        new CultivationLevel(3, "invincible.cultivation_level.3", 31000, 43000, 99.85, 0, 0, 0),
        new CultivationLevel(4, "invincible.cultivation_level.4", 43000, 56000, 99.8, 0, 0, 0),
        new CultivationLevel(5, "invincible.cultivation_level.5", 56000, 70000, 99.5, 0, 0, 0),
        new CultivationLevel(6, "invincible.cultivation_level.6", 70000, 85000, 99.35, 0, 0, 0),
        new CultivationLevel(7, "invincible.cultivation_level.7", 85000, 100000, 99.1, 0, 0, 0),
        new CultivationLevel(8, "invincible.cultivation_level.8", 100000, 116000, 99, 0, 0, 0),
        new CultivationLevel(9, "invincible.cultivation_level.9", 116000, 133000, 98.5, 0, 0, 0),
        new CultivationLevel(10, "invincible.cultivation_level.10", 133000, 180000, 98, 0, 0, 0),
        new CultivationLevel(11, "invincible.cultivation_level.11", 180000, 200000, 97.5, 0, 0, 0),
        new CultivationLevel(12, "invincible.cultivation_level.12", 200000, 221000, 97, 0, 0, 0),
        new CultivationLevel(13, "invincible.cultivation_level.13", 221000, 242000, 96.5, 0, 0, 0),
        new CultivationLevel(14, "invincible.cultivation_level.14", 242000, 264000, 96, 0, 0, 0),
        new CultivationLevel(15, "invincible.cultivation_level.15", 264000, 288000, 95, 0, 0, 0),
        new CultivationLevel(16, "invincible.cultivation_level.16", 288000, 313000, 94, 0, 0, 0),
        new CultivationLevel(17, "invincible.cultivation_level.17", 313000, 339000, 93, 0, 0, 0),
        new CultivationLevel(18, "invincible.cultivation_level.18", 339000, 366000, 94, 0, 0, 0),
        new CultivationLevel(19, "invincible.cultivation_level.19", 366000, 396000, 95, 0, 0, 0),
        new CultivationLevel(20, "invincible.cultivation_level.20", 396000, 425000, 94.5, 0, 0, 0),
        new CultivationLevel(21, "invincible.cultivation_level.21", 425000, 451000, 94, 0, 0, 0),
        new CultivationLevel(22, "invincible.cultivation_level.22", 451000, 483000, 93, 0, 0, 0),
        new CultivationLevel(23, "invincible.cultivation_level.23", 483000, 500000, 90, 0, 0, 0),
    };

    private final IEntityDataSaver EntityData;

    public CultivationData(IEntityDataSaver EntityData){
        this.EntityData = EntityData;
    }

    public static CultivationLevel[] getAllCultivationLevel(){
        return CultivationLevelAll;
    }

    public int getCultivationExp(){
        NbtCompound nbt = EntityData.getPersistentData();
        int cultivation_exp = nbt.getInt("cultivation_exp");
        if(cultivation_exp < 0){
            nbt.putInt("cultivation_exp", 0);
            return 0;
        }
        return cultivation_exp;
    }

    public CultivationLevel getCultivationLevel(){
        NbtCompound nbt = EntityData.getPersistentData();
        int level = nbt.getInt("cultivation_level");

        if(level < 0){
            nbt.putInt("cultivation_level", 0);
            return CultivationLevelAll[0];
        }

        if(level >= CultivationLevelAll.length - 1){
            nbt.putInt("cultivation_level", CultivationLevelAll.length - 1);
            return CultivationLevelAll[CultivationLevelAll.length - 1];
        }

        return CultivationLevelAll[level];
    }

    public int addCultivationExpInUpLevel(int addExp){
        int cultivationExp = getCultivationExp();
        int new_cultivationExp = cultivationExp + addExp;
        EntityData.getPersistentData().putInt("cultivation_exp", new_cultivationExp);

        CultivationLevel in_level = getCultivationLevel();
        if(in_level.getLevel() >= CultivationLevelAll.length - 1){
            return 0;
        }

        int upInUpLevel = 0;
        for(CultivationLevel level: CultivationLevelAll){
            if(level.canUpLevel(new_cultivationExp)){
                continue;
            }

            upInUpLevel = level.getLevel();
            break;
        }

        return upInUpLevel - getCultivationLevel().getLevel();
    }

    public boolean upLevel(){
        CultivationLevel level = getCultivationLevel();
        if(level.getLevel() >= CultivationLevelAll.length - 1){
            return false;
        }

        if(!level.canUpLevel(getCultivationExp())){
            return false;
        }

        Random_Item[] random = {
            new Random_Item(level.getUpLevelRate(), true),
            new Random_Item(level.getUpLevelFailureRate(), false)
        };
        boolean random_result = (boolean) Probability_Random.random(random).getItem();

        if(!random_result){
            return random_result;
        }

        EntityData.getPersistentData().putInt("cultivation_level", level.getLevel() + 1);
        return random_result;
    }

    public CultivationLevel getUpLevel(){
        CultivationLevel in_level = getCultivationLevel();
        if(in_level.getLevel() >= CultivationLevelAll.length -1){
            return in_level;
        }
        return CultivationLevelAll[in_level.getLevel() + addCultivationExpInUpLevel(0) + 1];
    }
    
    public static class CultivationLevel {
        private int Level;
        private String LevelName;
        private int CultivationExp;
        private int UpLevelExp;
        private double UpLevelRate;
        private float BaseAttack;
        private float BaseHealth;
        private float BaseMana;

        public CultivationLevel(int Level, String LevelName, int CultivationExp, int UpLevelExp, double UpLevelRate, 
                float BaseAttack, float BaseHealth, float BaseMana){
            this.Level = Level;
            this.LevelName = LevelName;
            this.CultivationExp = CultivationExp;
            this.UpLevelExp = UpLevelExp;
            this.UpLevelRate = UpLevelRate;
            this.BaseAttack = BaseAttack;
            this.BaseHealth = BaseHealth;
            this.BaseMana = BaseMana;
        }

        public int getLevel(){
            return Level;
        }

        public TranslatableText getLevelName(){
            return new TranslatableText(LevelName);
        }

        public int getCultivationExp(){
            return CultivationExp;
        }

        public int getUpLevelExp(){
            return UpLevelExp;
        }

        public double getUpLevelRate(){
            return UpLevelRate;
        }

        public double getUpLevelFailureRate(){
            return 100 - UpLevelRate;
        }

        public float getBaseAttack(){
            return BaseAttack;
        }

        public float getBaseHealth(){
            return BaseHealth;
        }

        public float getBaseMana(){
            return BaseMana;
        }

        public boolean canUpLevel(int cultivationExp){
            return cultivationExp >= UpLevelExp;
        }

        public boolean canDownLevel(int cultivationExp){
            return CultivationExp - cultivationExp < 0;
        }


    }
}
