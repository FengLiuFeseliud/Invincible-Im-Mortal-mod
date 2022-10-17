package fengliu.invincible.util;

import fengliu.invincible.api.Probability_Random;
import fengliu.invincible.api.Probability_Random.Random_Item;
import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 *  服务端修为数据 (可以修改)
 */
public class CultivationServerData extends CultivationCilentData {
    
    private final IEntityDataSaver EntityData;
    
    public CultivationServerData(IEntityDataSaver EntityData) {
        super(EntityData);
        this.EntityData = EntityData;
    }

    @Override
    public int getCultivationExp(){
        NbtCompound nbt = EntityData.getPersistentData();
        int cultivation_exp = nbt.getInt("cultivation_exp");
        if(cultivation_exp < 0){
            nbt.putInt("cultivation_exp", 0);
            syncData(EntityData);
            return 0;
        }
        return cultivation_exp;
    }

    @Override
    public CultivationLevel getCultivationLevel(){
        NbtCompound nbt = EntityData.getPersistentData();
        int level = nbt.getInt("cultivation_level");

        if(level < 0){
            nbt.putInt("cultivation_level", 0);
            syncData(EntityData);
            return CultivationLevelAll[0];
        }

        if(level >= CultivationLevelAll.length - 1){
            nbt.putInt("cultivation_level", CultivationLevelAll.length - 1);
            syncData(EntityData);
            return CultivationLevelAll[CultivationLevelAll.length - 1];
        }

        return CultivationLevelAll[level];
    }

    @Override
    public int getMana(){
        NbtCompound nbt = EntityData.getPersistentData();
        int mana = nbt.getInt("mana");

        if(mana < 0){
            nbt.putInt("mana", 0);
            syncData(EntityData);
            return 0;
        }

        CultivationLevel level = getCultivationLevel();
        if(mana > level.getBaseMana()){
            nbt.putInt("mana", level.getBaseMana());
            syncData(EntityData);
            return level.getBaseMana();
        }
        
        return mana;
    }

    @Override
    public float getGain() {
        NbtCompound nbt = EntityData.getPersistentData();
        float gain = 1.0f;

        if(nbt.contains("gain")){
            gain = nbt.getFloat("gain");
        }

        if(gain < 0){
            nbt.putFloat("gain", 1.0f);
            syncData(EntityData);
            return 1.0f;
        }
        return gain;
    }

    public void setGain(float gain) {
        EntityData.getPersistentData().putFloat("gain", gain);
    }

    /**
     * 增加指定量的修为, 并返回可升级到的境界的需增加的索引
     * @param addExp 增加修为
     * @return 可升级到的境界需增加的索引, 不能升级为 0
     */
    public int addCultivationExpInUpLevel(int addExp){
        int cultivationExp = getCultivationExp();
        int new_cultivationExp = cultivationExp + addExp;
        EntityData.getPersistentData().putInt("cultivation_exp", new_cultivationExp);
        syncData(EntityData);

        return getUpLevelIndex(new_cultivationExp);
    }

    /**
     * 进行突破
     * @return 成功突破为 true
     */
    public boolean upLevel(){
        CultivationLevel level = getCultivationLevel();
        if(level.getLevel() >= CultivationLevelAll.length - 1){
            return false;
        }

        if(!level.canUpLevel(getCultivationExp())){
            return false;
        }

        /*
         * 进行概率随机
         * 第一个随机项目为成功 概率 突破成功率 内容 true 
         * 第二个随机项目为失败 概率 突破失败率 内容 false 
         */
        Random_Item[] random = {
            new Random_Item(level.getUpLevelRate(), true),
            new Random_Item(level.getUpLevelFailureRate(), false)
        };
        // 进行随机并获取内容
        boolean random_result = (boolean) Probability_Random.random(random).getItem();

        if(!random_result){
            return random_result;
        }

        EntityData.getPersistentData().putInt("cultivation_level", level.getLevel() + 1);
        syncData(EntityData);
        return random_result;
    }

    /**
     * 恢复指定量的真元
     * @param mana 恢复真元
     */
    public void recoverMana(int mana){
        NbtCompound nbt = EntityData.getPersistentData();
        CultivationLevel level = getCultivationLevel();

        int recover_mana = nbt.getInt("mana") + mana;
        if(recover_mana > level.getBaseMana()){
            recover_mana = level.getBaseMana();
        }

        EntityData.getPersistentData().putInt("mana", recover_mana);
        syncData(EntityData);
    }

    /**
     * 消耗指定量的真元
     * @param mana 消耗真元
     * @return 成功消耗为 true
     */
    public boolean consumeMana(int mana){
        NbtCompound nbt = EntityData.getPersistentData();

        int consume_mana = nbt.getInt("mana") - mana;
        if(consume_mana < 0 || getCultivationLevel().getBaseMana() == 0){
            return false;
        }
        nbt.putInt("mana", consume_mana);
        syncData(EntityData);
        return true;
    }

    /**
     * 向客户端同步修为数据
     * @param player 当前同步玩家
     */
    public void syncData(IEntityDataSaver player){
        ServerPlayNetworking.send((ServerPlayerEntity) EntityData, ModMessage.SYNC_DATA, 
            PacketByteBufs.create().writeNbt((player.getPersistentData())));
    }
}
