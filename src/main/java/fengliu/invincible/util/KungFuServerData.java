package fengliu.invincible.util;

import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class KungFuServerData extends KungFuCilentData{
    private final IEntityDataSaver EntityData;

    public KungFuServerData(IEntityDataSaver player){
        super(player);
        EntityData = player;
    }

    @Override
    public NbtList getCanUesKungFu(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("can_ues_kung_fu")){
            return (NbtList) nbt.get("can_ues_kung_fu");
        }

        NbtList nbtList = new NbtList();
        nbt.put("can_ues_kung_fu", nbtList);
        syncData(EntityData);
        return nbtList;
    }

    @Override
    public NbtList getCanUesKungFuGroup(){
        NbtList nbtListCanUesKungFu = (NbtList) getCanUesKungFu();

        if(nbtListCanUesKungFu.size() == 0){
            nbtListCanUesKungFu.add(new NbtList());
        }
        NbtList nbtList = (NbtList) nbtListCanUesKungFu.get(getKungFuGroupUesIn());
        if(nbtList.size() == 0){
            return nbtList;
        }
        return nbtList;
    }

    @Override
    public int getKungFuGroupUesIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("kung_fu_group_ues_in")){
            return nbt.getInt("kung_fu_group_ues_in");
        }

        nbt.putInt("kung_fu_group_ues_in", 0);
        syncData(EntityData);
        return 0;
    }

    @Override
    public int getKungFuUesIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("kung_fu_ues_in")){
            return nbt.getInt("kung_fu_ues_in");
        }

        nbt.putInt("kung_fu_ues_in", 0);
        syncData(EntityData);
        return 0;
    }
    
    /**
     * 设置功法格至下一本功法, 如果为最后一本功法, 返回至第一本
     */
    public void setNextUesKungFu(){
        NbtList canUesGroup = getCanUesKungFuGroup();
        if(canUesGroup.size() == 0){
            return;
        }

        NbtCompound nbt = EntityData.getPersistentData();
        int kungFuUesIn = getKungFuUesIn();
        if(kungFuUesIn < canUesGroup.size() - 1){
            nbt.putInt("kung_fu_ues_in", ++kungFuUesIn);
            syncData(EntityData);
            return;
        }

        nbt.putInt("kung_fu_ues_in", 0);
        syncData(EntityData);
    }

    /**
     * 设置功法组至下一功法组, 如果为最后一功法组, 返回至第一功法组
     */
    public void setNextUesKungFuGroup(){
        NbtList canUes = getCanUesKungFu();
        if(canUes.size() == 0){
            return;
        }

        NbtCompound nbt = EntityData.getPersistentData();
        int kungFuGroupUesIn = getKungFuGroupUesIn();
        if(kungFuGroupUesIn < canUes.size() - 1){
            nbt.putInt("kung_fu_group_ues_in", ++kungFuGroupUesIn);
            syncData(EntityData);
            return;
        }

        nbt.putInt("kung_fu_group_ues_in", 0);
        syncData(EntityData);
    }

    /**
     * 设置当前 combo 功法标记
     */
    public void setComboIn(){
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("group_ues_in", getKungFuGroupUesIn());
        nbt.putInt("ues_in", getKungFuUesIn());

        EntityData.getPersistentData().put("combo_in", nbt);
    }

    /**
     * 消耗当前功法需要真元
     * @return 成功消耗 true
     */
    public boolean consume(){
        KungFuSettings settings = getUesKungFuSettings();
        if(settings == null){
            return false;
        }

        CultivationServerData cultivationData = EntityData.getServerCultivationData();
        if(!cultivationData.consumeMana(settings.Consume * (getUesKungFu().getInt("tieks") + 1)) && settings.Consume != 0){
            ((PlayerEntity) EntityData).sendMessage(
                new TranslatableText("info.invincible.mana_insufficient", settings.Name.getString()), true);
            return false;
        }
        return true;
    }

    /**
     * 获取功法当前重天的效果接口
     * @return
     */
    public KungFuTiek ues(){
        KungFuSettings settings = getUesKungFuSettings();
        if(settings == null){
            return null;
        }
        return settings.getKungFuTiek(getUesKungFu().getInt("tieks"));
    }

    /**
     * 连击使用功法, 下一次使用功法下一重天, 如果为最后一重天, 返回至第一重天
     */
    public void comboUes(){
        NbtCompound uesKungFu = getUesKungFu();
        
        uesKungFu.putInt("combo_in", uesKungFu.getInt("combo_end"));
        uesKungFu.putInt("tieks", getUesKungFuSettings().getNextTiekIndex(uesKungFu.getInt("tieks"), (NbtList) uesKungFu.get("can_ues_tieks")));
        setComboIn();
        syncData(EntityData);
    }

    /**
     * 
     * @return
     */
    public boolean upKungFuTiek(){
        kungFuSettings[0].initialNbt(EntityData);

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
