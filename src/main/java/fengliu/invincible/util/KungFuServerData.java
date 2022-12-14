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
     * ?????????????????????????????????, ???????????????????????????, ??????????????????
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
     * ?????????????????????????????????, ???????????????????????????, ????????????????????????
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
     * ???????????? combo ????????????
     */
    public void setComboIn(){
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("group_ues_in", getKungFuGroupUesIn());
        nbt.putInt("ues_in", getKungFuUesIn());

        EntityData.getPersistentData().put("combo_in", nbt);
    }

    /**
     * ??????????????????????????????
     * @return ???????????? true
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
     * ??????????????????????????????????????????
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
     * ??????????????????, ????????????????????????????????????, ???????????????????????????, ????????????????????????
     */
    public void comboUes(){
        NbtCompound uesKungFu = getUesKungFu();
        
        uesKungFu.putInt("combo_in", uesKungFu.getInt("combo_end"));
        uesKungFu.putInt("tieks", getUesKungFuSettings().getNextTiekIndex(uesKungFu.getInt("tieks"), (NbtList) uesKungFu.get("can_ues_tieks")));
        setComboIn();
        syncData();
    }

    /**
     * ?????????????????????????????? (????????????)
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
     * ???????????? ??????????????? nbt
     * @param stack ??????
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
     * ?????????????????????
     * @return ?????????????????? true
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
     * ????????????????????????????????????
     * @param stack ??????
     * @return ?????????????????? true
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
     * ???????????????????????????????????????
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
     * ??????????????????????????????
     */
    public void syncData(){
        ServerPlayNetworking.send((ServerPlayerEntity) EntityData, ModMessage.SYNC_DATA, 
            PacketByteBufs.create().writeNbt((EntityData.getPersistentData())));
    }
}
