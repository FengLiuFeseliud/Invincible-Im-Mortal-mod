package fengliu.invincible.util;

import net.minecraft.text.TranslatableText;

public class CultivationCilentData {

    // 境界
    protected static CultivationLevel[] CultivationLevelAll = {
        new CultivationLevel(0, "invincible.cultivation_level.0", 0, 10000, 100, 0, 0, 0),
        // 练气
        new CultivationLevel(1, "invincible.cultivation_level.1", 10000, 20000, 99, 1, 0, 100),
        new CultivationLevel(2, "invincible.cultivation_level.2", 20000, 31000, 99, 1.1, 0, 110),
        new CultivationLevel(3, "invincible.cultivation_level.3", 31000, 43000, 99, 1.2, 0, 120),
        new CultivationLevel(4, "invincible.cultivation_level.4", 43000, 56000, 98, 1.5, 0, 130),
        new CultivationLevel(5, "invincible.cultivation_level.5", 56000, 70000, 97, 1.6, 0, 140),
        new CultivationLevel(6, "invincible.cultivation_level.6", 70000, 85000, 96, 1.7, 0, 150),
        new CultivationLevel(7, "invincible.cultivation_level.7", 85000, 100000, 94, 2, 0, 160),
        new CultivationLevel(8, "invincible.cultivation_level.8", 100000, 116000, 93, 2.1, 0, 170),
        new CultivationLevel(9, "invincible.cultivation_level.9", 116000, 133000, 92, 2.2, 0, 180),
        new CultivationLevel(10, "invincible.cultivation_level.10", 133000, 180000, 90, 2.5, 0, 190),
        // 筑基
        new CultivationLevel(11, "invincible.cultivation_level.11", 180000, 200000, 89, 3, 0, 250),
        new CultivationLevel(12, "invincible.cultivation_level.12", 200000, 221000, 88, 3.2, 0, 260),
        new CultivationLevel(13, "invincible.cultivation_level.13", 221000, 242000, 87, 3.4, 0, 270),
        new CultivationLevel(14, "invincible.cultivation_level.14", 242000, 264000, 85, 3.6, 0, 280),
        new CultivationLevel(15, "invincible.cultivation_level.15", 264000, 288000, 84, 4, 0, 290),
        new CultivationLevel(16, "invincible.cultivation_level.16", 288000, 313000, 83, 4.2, 0, 300),
        new CultivationLevel(17, "invincible.cultivation_level.17", 313000, 339000, 82, 4.4, 0, 310),
        new CultivationLevel(18, "invincible.cultivation_level.18", 339000, 366000, 80, 4.6, 0, 320),
        new CultivationLevel(19, "invincible.cultivation_level.19", 366000, 396000, 79, 5, 0, 330),
        new CultivationLevel(20, "invincible.cultivation_level.20", 396000, 425000, 78, 5.2, 0, 340),
        new CultivationLevel(21, "invincible.cultivation_level.21", 425000, 451000, 75, 5.4, 0, 350),
        new CultivationLevel(22, "invincible.cultivation_level.22", 451000, 483000, 73, 5.6, 0, 360),
        new CultivationLevel(23, "invincible.cultivation_level.23", 483000, 500000, 70, 6, 0, 370),
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
                double BaseAttack, double BaseHealth, int BaseMana){
            this.Level = Level;
            this.LevelName = LevelName;
            this.CultivationExp = CultivationExp;
            this.UpLevelExp = UpLevelExp;
            this.UpLevelRate = UpLevelRate;
            this.BaseAttack = (float) BaseAttack;
            this.BaseHealth = (float) BaseHealth;
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
