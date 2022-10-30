package fengliu.invincible.util;

import net.minecraft.text.TranslatableText;

public class LianDanCilentData {

    protected static final LianDanLevel[] LianDanLevelAll = {
        new LianDanLevel(0, "", 0, 1, 100),
        new LianDanLevel(1, "", 1, 100, 80),
        new LianDanLevel(2, "", 100, 20000, 60),
        new LianDanLevel(3, "", 20000, 400000, 50),
        new LianDanLevel(4, "", 400000, 8000000, 40),
        new LianDanLevel(5, "", 8000000, 1000000, 30),
        new LianDanLevel(6, "", 1000000, 1300000, 20),
        new LianDanLevel(7, "", 1300000, 1600000, 10),
        new LianDanLevel(8, "", 1600000, 2000000, 5),
        new LianDanLevel(9, "", 2000000, 2500000, 1),
    };

    private final IEntityDataSaver EntityData;
    
    public LianDanCilentData(IEntityDataSaver EntityData) {
        this.EntityData = EntityData;
    }

    /**
     * 获取当前经验值
     * @return 当前经验值
     */
    public int getLianDanExp(){
        return EntityData.getPersistentData().getInt("lian_dan_exp");
    }

    /**
     * 获取当前境界
     * @return 当前境界
     */
    public LianDanLevel getLianDanLevel(){
        return LianDanLevelAll[EntityData.getPersistentData().getInt("lian_dan_level")];
    }

    /**
     * 获取可升级到的境界的需增加的索引
     * @param maxLianDanExp 最大经验值
     * @return 可升级到的境界需增加的索引
     */
    public int getUpLevelIndex(int maxLianDanExp){
        LianDanLevel in_level = getLianDanLevel();
        if(in_level.getLevel() >= LianDanLevelAll.length - 1){
            return 0;
        }

        if(maxLianDanExp >= LianDanLevelAll[LianDanLevelAll.length - 1].getCultivationExp()){
            return LianDanLevelAll[LianDanLevelAll.length - 1].getLevel() - in_level.getLevel();
        }

        int upInUpLevel = 0;
        for(LianDanLevel level: LianDanLevelAll){
            if(level.canUpLevel(maxLianDanExp)){
                continue;
            }

            upInUpLevel = level.getLevel();
            break;
        }

        return upInUpLevel - getLianDanLevel().getLevel();
    }

    public static class LianDanLevel {
        private int Level;
        private String LevelName;
        private int LianDanExp;
        private int UpLevelExp;
        private double UpLevelRate;
        
        /**
         * 境界数值
         * @param Level 境界等级
         * @param LevelName 境界名称 (TranslatableText)
         * @param LianDanExp 境界初始修为
         * @param UpLevelExp 突破下一个境界初始修为
         * @param UpLevelRate 突破下一个境界的成功率
         */
        public LianDanLevel(int Level, String LevelName, int LianDanExp, int UpLevelExp, double UpLevelRate){
            this.Level = Level;
            this.LevelName = LevelName;
            this.LianDanExp = LianDanExp;
            this.UpLevelExp = UpLevelExp;
            this.UpLevelRate = UpLevelRate;
        }

        public int getLevel(){
            return Level;
        }

        public TranslatableText getLevelName(){
            return new TranslatableText(LevelName);
        }

        public int getCultivationExp(){
            return LianDanExp;
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

        /**
         * 计算升级所需经验
         * @return 升级所需经验
         */
        public int getNeedCultivationExp(){
            return UpLevelExp - LianDanExp;
        }

        /**
         * 判断指定经验是否可以升级这个境界
         * @param lianDanExp 经验
         * @return 可以时为 true
         */
        public boolean canUpLevel(int lianDanExp){
            return lianDanExp >= UpLevelExp;
        }

    }
}
