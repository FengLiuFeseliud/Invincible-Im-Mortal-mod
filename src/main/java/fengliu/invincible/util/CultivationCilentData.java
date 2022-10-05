package fengliu.invincible.util;

import net.minecraft.text.TranslatableText;

public class CultivationCilentData {

    protected static CultivationLevel[] CultivationLevelAll = {
        new CultivationLevel(0, "invincible.cultivation_level.0", 0, 10000, 100, 0, 0, 0),
        // 练气
        new CultivationLevel(1, "invincible.cultivation_level.1", 10000, 20000, 100, 0, 0, 100),
        new CultivationLevel(2, "invincible.cultivation_level.2", 20000, 31000, 99.9, 0, 0, 110),
        new CultivationLevel(3, "invincible.cultivation_level.3", 31000, 43000, 99.85, 0, 0, 120),
        new CultivationLevel(4, "invincible.cultivation_level.4", 43000, 56000, 99.8, 0, 0, 130),
        new CultivationLevel(5, "invincible.cultivation_level.5", 56000, 70000, 99.5, 0, 0, 140),
        new CultivationLevel(6, "invincible.cultivation_level.6", 70000, 85000, 99.35, 0, 0, 150),
        new CultivationLevel(7, "invincible.cultivation_level.7", 85000, 100000, 99.1, 0, 0, 160),
        new CultivationLevel(8, "invincible.cultivation_level.8", 100000, 116000, 99, 0, 0, 170),
        new CultivationLevel(9, "invincible.cultivation_level.9", 116000, 133000, 98.5, 0, 0, 180),
        new CultivationLevel(10, "invincible.cultivation_level.10", 133000, 180000, 98, 0, 0, 190),
        // 筑基
        new CultivationLevel(11, "invincible.cultivation_level.11", 180000, 200000, 97.5, 0, 0, 250),
        new CultivationLevel(12, "invincible.cultivation_level.12", 200000, 221000, 97, 0, 0, 260),
        new CultivationLevel(13, "invincible.cultivation_level.13", 221000, 242000, 96.5, 0, 0, 270),
        new CultivationLevel(14, "invincible.cultivation_level.14", 242000, 264000, 96, 0, 0, 280),
        new CultivationLevel(15, "invincible.cultivation_level.15", 264000, 288000, 95, 0, 0, 290),
        new CultivationLevel(16, "invincible.cultivation_level.16", 288000, 313000, 94, 0, 0, 300),
        new CultivationLevel(17, "invincible.cultivation_level.17", 313000, 339000, 93, 0, 0, 310),
        new CultivationLevel(18, "invincible.cultivation_level.18", 339000, 366000, 94, 0, 0, 320),
        new CultivationLevel(19, "invincible.cultivation_level.19", 366000, 396000, 95, 0, 0, 330),
        new CultivationLevel(20, "invincible.cultivation_level.20", 396000, 425000, 94.5, 0, 0, 340),
        new CultivationLevel(21, "invincible.cultivation_level.21", 425000, 451000, 94, 0, 0, 350),
        new CultivationLevel(22, "invincible.cultivation_level.22", 451000, 483000, 93, 0, 0, 360),
        new CultivationLevel(23, "invincible.cultivation_level.23", 483000, 500000, 90, 0, 0, 370),
    };

    private final IEntityDataSaver EntityData;

    public CultivationCilentData(IEntityDataSaver EntityData){
        this.EntityData = EntityData;
    }

    public static CultivationLevel[] getAllCultivationLevel(){
        return CultivationLevelAll;
    }

    public int getCultivationExp(){
        return EntityData.getPersistentData().getInt("cultivation_exp");
    }

    public CultivationLevel getCultivationLevel(){
        return CultivationLevelAll[EntityData.getPersistentData().getInt("cultivation_level")];
    }

    public int getMana(){
        return EntityData.getPersistentData().getInt("mana");
    }

    public int getUpLevelIndex(int maxCultivationExp){
        CultivationLevel in_level = getCultivationLevel();
        if(in_level.getLevel() >= CultivationLevelAll.length - 1){
            return 0;
        }

        if(maxCultivationExp >= CultivationLevelAll[CultivationLevelAll.length - 1].getCultivationExp()){
            return CultivationLevelAll[CultivationLevelAll.length - 1].getLevel() - in_level.getLevel();
        }

        int upInUpLevel = 0;
        for(CultivationLevel level: CultivationLevelAll){
            if(level.canUpLevel(maxCultivationExp)){
                continue;
            }

            upInUpLevel = level.getLevel();
            break;
        }

        return upInUpLevel - getCultivationLevel().getLevel();
    }

    public CultivationLevel getUpLevel(){
        CultivationLevel in_level = getCultivationLevel();
        if(in_level.getLevel() >= CultivationLevelAll.length -1){
            return in_level;
        }

        int upIndex = in_level.getLevel() + getUpLevelIndex(getCultivationExp()) + 1;
        if(upIndex >= CultivationLevelAll.length -1){
            return CultivationLevelAll[CultivationLevelAll.length -1];
        }

        return CultivationLevelAll[upIndex];
    }

    public int getNeedCultivationExp(){
        int index = getUpLevelIndex(getCultivationExp());

        if(index == 0){
            return getCultivationLevel().getNeedCultivationExp();
        }

        if(index >= CultivationLevelAll.length){
            return 0;
        }

        return CultivationLevelAll[index].getNeedCultivationExp();
    }
    
    public static class CultivationLevel {
        private int Level;
        private String LevelName;
        private int CultivationExp;
        private int UpLevelExp;
        private double UpLevelRate;
        private float BaseAttack;
        private float BaseHealth;
        private int BaseMana;

        public CultivationLevel(int Level, String LevelName, int CultivationExp, int UpLevelExp, double UpLevelRate, 
                float BaseAttack, float BaseHealth, int BaseMana){
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

        public int getBaseMana(){
            return BaseMana;
        }

        public int getNeedCultivationExp(){
            return UpLevelExp - CultivationExp;
        }

        public boolean canUpLevel(int cultivationExp){
            return cultivationExp >= UpLevelExp;
        }

        public boolean canDownLevel(int cultivationExp){
            return CultivationExp - cultivationExp < 0;
        }


    }
}
