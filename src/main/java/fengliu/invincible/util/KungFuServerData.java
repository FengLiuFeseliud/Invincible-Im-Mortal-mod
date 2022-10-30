package fengliu.invincible.util;

import fengliu.invincible.item.KungFuItem;
import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        syncData();
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
        syncData();
        return 0;
    }

    @Override
    public int getKungFuUesIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("kung_fu_ues_in")){
            return nbt.getInt("kung_fu_ues_in");
        }

        nbt.putInt("kung_fu_ues_in", 0);
        syncData();
        return 0;
    }

    @Override
    public int getKungFuProficiency(){
        NbtCompound nbt = getUesKungFu();
        if(!nbt.contains("proficiency")){
            nbt.putInt("proficiency", 0);
            return 0;
        }

        return nbt.getInt("proficiency");
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
            syncData();
            return;
        }

        nbt.putInt("kung_fu_ues_in", 0);
        syncData();
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
            syncData();
            return;
        }

        nbt.putInt("kung_fu_group_ues_in", 0);
        syncData();
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
     * 获取功法当前功法层的效果接口
     * @return
     */
    public KungFuTiek ues(){
        KungFuSettings settings = getUesKungFuSettings();
        if(settings == null){
            return null;
        }
        return settings.getKungFuTiek(getUesKungFu().getInt("tieks")).kungFuTiek;
    }

    /**
     * 连击使用功法, 下一次使用功法下一功法层, 如果为最后一功法层, 返回至第一功法层
     */
    public void comboUes(){
        NbtCompound uesKungFu = getUesKungFu();
        
        uesKungFu.putInt("combo_in", uesKungFu.getInt("combo_end"));
        uesKungFu.putInt("tieks", getUesKungFuSettings().getNextTiekIndex(uesKungFu.getInt("tieks"), (NbtList) uesKungFu.get("can_ues_tieks")));
        setComboIn();
        syncData();
    }

    /**
     * 判断功法是否可以学习 (是否存在)
     */
    public boolean isKungFuInLearn(KungFuSettings settings){
        NbtList nbtList = getCanUesKungFu();
        for(int kungFuGroupIndex = 0; kungFuGroupIndex < nbtList.size(); kungFuGroupIndex++){
            NbtList kungFuGroup = nbtList.getList(kungFuGroupIndex);
            for(int kungFuIndex = 0; kungFuIndex < kungFuGroup.size(); kungFuIndex++){
                NbtCompound nbt = kungFuGroup.getCompound(kungFuIndex);
                if(nbt.getString("name") != settings.Name.getKey()){
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 学习功法 初始化功法 nbt
     * @param stack 功法
     */
    public boolean learnKungFu(ItemStack stack){
        Item kungFu = stack.getItem();
        if(!(kungFu instanceof KungFuItem)){
            return false;
        }

        KungFuSettings settings = ((KungFuItem) kungFu).getKungFuSettings();
        PlayerEntity player = (PlayerEntity) EntityData;
        if(isKungFuInLearn(settings)){
            player.sendMessage(new TranslatableText("info.invincible.kung_fu_in_learn",
                settings.Name.getString()
            ), false);
            return false;
        }

        settings.initialNbt(EntityData);
        syncData();

        player.sendMessage(new TranslatableText("info.invincible.kung_fu_learn",
            settings.Name.getString()
        ), false);
        return true;
    }

    /**
     * 增加功法熟练度
     * @return 可以升级返回 true
     */
    public boolean addKungFuProficiency(){
        int proficiency = getKungFuProficiency();
        KungFuTiekSettings KungFuTiekSettings = getKungFuNotUesTiek();
        if(KungFuTiekSettings == null){
            return false;
        }
        
        proficiency++;
        getUesKungFu().putInt("proficiency", proficiency);

        syncData();
        if(proficiency >= KungFuTiekSettings.Proficiency){
            return true;
        }

        return false;
    }

    /**
     * 增加功法物品的功法领悟度
     * @param stack 功法
     * @return 可以学习返回 true
     */
    public boolean addKungFuProficiencyFromItemStack(ItemStack stack){
        Item kungFu = stack.getItem();
        if(!(kungFu instanceof KungFuItem)){
            return false;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        KungFuTiekSettings KungFuTiekSettings =  ((KungFuItem) kungFu).getKungFuSettings().getKungFuTiek(0);

        int proficiency = getKungFuItemProficiency(stack);
        if(proficiency >= KungFuTiekSettings.Proficiency){
            return true;
        }

        proficiency++;
        nbt.putInt("proficiency", proficiency);
        return proficiency >= KungFuTiekSettings.Proficiency;
    }

    /**
     * 增加当前功法格的功法可用层
     */
    public void upKungFuTiek(){
        NbtCompound nbt = getUesKungFu();
        NbtList nbtList = (NbtList) nbt.get("can_ues_tieks");
        for(int index = 0; index < nbtList.size(); index++){
            NbtCompound nbtTiek = (NbtCompound) nbtList.get(index);
            if(nbtTiek.getBoolean(index+"")){
                continue;
            }

            nbtTiek.putBoolean(index+"", true);
            nbtList.set(index, nbtTiek);
            syncData();

            ((PlayerEntity) EntityData).sendMessage(new TranslatableText("info.invincible.kung_fu_up_level",
                getUesKungFuSettings().Name.getString()
            ), false);
            return;
        }
    }

    /**
     * 向客户端同步修为数据
     */
    public void syncData(){
        ServerPlayNetworking.send((ServerPlayerEntity) EntityData, ModMessage.SYNC_DATA, 
            PacketByteBufs.create().writeNbt((EntityData.getPersistentData())));
    }
}
