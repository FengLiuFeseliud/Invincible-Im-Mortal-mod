package fengliu.invincible.util;

import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class LianDanServerData extends LianDanCilentData {
    
    private final IEntityDataSaver EntityData;
    
    public LianDanServerData(IEntityDataSaver EntityData) {
        super(EntityData);
        this.EntityData = EntityData;
    }

    @Override
    public int getLianDanExp(){
        NbtCompound nbt = EntityData.getPersistentData();
        int cultivation_exp = nbt.getInt("lian_dan_exp");
        if(cultivation_exp < 0){
            nbt.putInt("lian_dan_exp", 0);
            syncData();
            return 0;
        }
        return cultivation_exp;
    }

    @Override
    public LianDanLevel getLianDanLevel(){
        NbtCompound nbt = EntityData.getPersistentData();
        int level = nbt.getInt("lian_dan_level");

        if(level < 0){
            nbt.putInt("lian_dan_level", 0);
            syncData();
            return LianDanLevelAll[0];
        }

        if(level >= LianDanLevelAll.length - 1){
            nbt.putInt("lian_dan_level", LianDanLevelAll.length - 1);
            syncData();
            return LianDanLevelAll[LianDanLevelAll.length - 1];
        }

        return LianDanLevelAll[level];
    }

    /**
     * 增加指定量的经验, 并返回可升级到的境界的需增加的索引
     * @param addExp 增加经验
     * @return 可升级到的境界需增加的索引, 不能升级为 0
     */
    public int addLianDanExpInUpLevel(DanYanItem danYan){
        int new_lianDanExp = danYan.getNewLianDanExp(getLianDanExp()) ;
        EntityData.getPersistentData().putInt("lian_dan_exp", new_lianDanExp);
        syncData();

        return getUpLevelIndex(new_lianDanExp);
    }

    /**
     * 向客户端同步数据
     */
    public void syncData(){
        ServerPlayNetworking.send((ServerPlayerEntity) EntityData, ModMessage.LIAN_DAN_SYNC_DATA, 
            PacketByteBufs.create().writeNbt((EntityData.getPersistentData())));
    }
}
