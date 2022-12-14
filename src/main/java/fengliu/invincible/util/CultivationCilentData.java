package fengliu.invincible.util;

import net.minecraft.text.TranslatableText;

/**
 *  客户端修为数据 (只可获取)
 */
public class CultivationCilentData {

    /**
     * 可用的所有境界
     */
    protected static final CultivationLevel[] CultivationLevelAll = {
        new CultivationLevel(0, "invincible.cultivation_level.0", 0, 10000, 100, 0, 0, 0),
        // 练气
        new CultivationLevel(1, "invincible.cultivation_level.1", 10000, 20000, 90, 1, 1, 100),
        new CultivationLevel(2, "invincible.cultivation_level.2", 20000, 31000, 85, 1.1, 1, 110),
        new CultivationLevel(3, "invincible.cultivation_level.3", 31000, 43000, 80, 1.2, 1, 120),
        new CultivationLevel(4, "invincible.cultivation_level.4", 43000, 56000, 75, 1.5, 2, 130),
        new CultivationLevel(5, "invincible.cultivation_level.5", 56000, 70000, 70, 1.6, 2, 140),
        new CultivationLevel(6, "invincible.cultivation_level.6", 70000, 85000, 70, 1.7, 2, 150),
        new CultivationLevel(7, "invincible.cultivation_level.7", 85000, 100000, 70, 2, 3, 160),
        new CultivationLevel(8, "invincible.cultivation_level.8", 100000, 116000, 65, 2.1, 3, 170),
        new CultivationLevel(9, "invincible.cultivation_level.9", 116000, 133000, 60, 2.2, 3, 180),
        new CultivationLevel(10, "invincible.cultivation_level.10", 133000, 180000, 60, 2.5, 5, 190),
        // 筑基
        new CultivationLevel(11, "invincible.cultivation_level.11", 180000, 200000, 90, 3, 6, 250),
        new CultivationLevel(12, "invincible.cultivation_level.12", 200000, 221000, 85, 3.2, 6, 260),
        new CultivationLevel(13, "invincible.cultivation_level.13", 221000, 242000, 80, 3.4, 6, 270),
        new CultivationLevel(14, "invincible.cultivation_level.14", 242000, 264000, 75, 3.6, 6, 280),
        new CultivationLevel(15, "invincible.cultivation_level.15", 264000, 288000, 70, 4, 7, 290),
        new CultivationLevel(16, "invincible.cultivation_level.16", 288000, 313000, 65, 4.2, 7, 300),
        new CultivationLevel(17, "invincible.cultivation_level.17", 313000, 339000, 60, 4.4, 7, 310),
        new CultivationLevel(18, "invincible.cultivation_level.18", 339000, 366000, 55, 4.6, 7, 320),
        new CultivationLevel(19, "invincible.cultivation_level.19", 366000, 396000, 50, 5, 8, 330),
        new CultivationLevel(20, "invincible.cultivation_level.20", 396000, 425000, 50, 5.2, 8, 340),
        new CultivationLevel(21, "invincible.cultivation_level.21", 425000, 451000, 45, 5.4, 8, 350),
        new CultivationLevel(22, "invincible.cultivation_level.22", 451000, 483000, 40, 5.6, 8, 360),
        new CultivationLevel(23, "invincible.cultivation_level.23", 483000, 500000, 40, 7, 10, 370),
    };

    private final IEntityDataSaver EntityData;

    public CultivationCilentData(IEntityDataSaver EntityData){
        this.EntityData = EntityData;
    }

    /**
     * 获取所有境界
     * @return CultivationLevel 列表
     */
    public static CultivationLevel[] getAllCultivationLevel(){
        return CultivationLevelAll;
    }

    /**
     * 获取当前修为值
     * @return 当前修为值
     */
    public int getCultivationExp(){
        return EntityData.getPersistentData().getInt("cultivation_exp");
    }

    /**
     * 获取当前境界
     * @return 当前境界
     */
    public CultivationLevel getCultivationLevel(){
        return CultivationLevelAll[EntityData.getPersistentData().getInt("cultivation_level")];
    }

    /**
     * 获取当前真元值
     * @return 当前真元值
     */
    public int getMana(){
        return EntityData.getPersistentData().getInt("mana");
    }

    public float getGain(){
        if(!EntityData.getPersistentData().contains("gain")){
            return 1.0f;
        }
        
        return EntityData.getPersistentData().getInt("gain");
    }

    /**
     * 获取可升级到的境界的需增加的索引
     * @param maxCultivationExp 最大修为值
     * @return 可升级到的境界需增加的索引
     */
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

    /**
     * 获取下个升级到的境界
     * @return 下个升级到的境界 CultivationLevel
     */
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

    /**
     * 获取升级到下一个境界的所需修为
     * @return 升级所需修为
     */
    public int getNeedCultivationExp(){
        int index = getUpLevelIndex(getCultivationExp());

        if(index == 0){
            return getCultivationLevel().getNeedCultivationExp();
        }

        if(index >= CultivationLevelAll.length || index < 0){
            return 0;
        }

        return CultivationLevelAll[index].getNeedCultivationExp();
    }
    
    /**
     * 境界对象
     */
    public static class CultivationLevel {
        private int Level;
        private String LevelName;
        private int CultivationExp;
        private int UpLevelExp;
        private double UpLevelRate;
        private float BaseAttack;
        private float BaseHealth;
        private int BaseMana;
        
        /**
         * 境界数值
         * @param Level 境界等级
         * @param LevelName 境界名称 (TranslatableText)
         * @param CultivationExp 境界初始修为
         * @param UpLevelExp 突破下一个境界初始修为
         * @param UpLevelRate 突破下一个境界的成功率
         * @param BaseAttack 境界的基础攻击力
         * @param BaseHealth 境界的基础血量
         * @param BaseMana 境界的基础真元
         */
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

        /**
         * 计算突破失败率
         * @return 突破失败率
         */
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

        /**
         * 计算升级所需修为
         * @return 升级所需修为
         */
        public int getNeedCultivationExp(){
            return UpLevelExp - CultivationExp;
        }

        /**
         * 判断指定修为是否可以升级这个境界
         * @param cultivationExp 修为
         * @return 可以时为 true
         */
        public boolean canUpLevel(int cultivationExp){
            return cultivationExp >= UpLevelExp;
        }

        /**
         * 判断指定修为是否可以下降这个境界
         * @param cultivationExp 修为
         * @return 可以时为 true
         */
        public boolean canDownLevel(int cultivationExp){
            return cultivationExp - CultivationExp < 0;
        }


    }
}
