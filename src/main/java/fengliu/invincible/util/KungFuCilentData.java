package fengliu.invincible.util;

import fengliu.invincible.item.KungFuItem;
import fengliu.invincible.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class KungFuCilentData {
    private final IEntityDataSaver EntityData;

    protected static final KungFuSettings[] kungFuSettings = {
        ModItems.JU_QI_KUNG.getKungFuSettings()
    };

    public KungFuCilentData(IEntityDataSaver player){
        EntityData = player;
    }

    /**
     * 获取所有可用功法 (至少已入门)
     * @return 所有可用功法 (nbt list)
     */
    public NbtList getCanUesKungFu(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("can_ues_kung_fu")){
            return (NbtList) nbt.get("can_ues_kung_fu");
        }
        return new NbtList();
    }

    /**
     * 获取当前功法组
     * @return 功法组 (nbt list)
     */
    public NbtList getCanUesKungFuGroup(){
        NbtList nbtListCanUesKungFu = (NbtList) getCanUesKungFu();

        if(nbtListCanUesKungFu.size() == 0){
            return new NbtList();
        }
        NbtList nbtList = (NbtList) nbtListCanUesKungFu.get(getKungFuGroupUesIn());
        if(nbtList.size() == 0){
            return nbtList;
        }
        return nbtList;
    }
    
    /**
     * 获取当前功法组下标
     * @return 功法组下标
     */
    public int getKungFuGroupUesIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("kung_fu_group_ues_in")){
            return nbt.getInt("kung_fu_group_ues_in");
        }
        return 0;
    }

    /**
     * 获取现在在功法格的功法 can_ues_kung_fu 下标
     * @return nbt list 下标
     */
    public int getKungFuUesIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("kung_fu_ues_in")){
            return nbt.getInt("kung_fu_ues_in");
        }
        return 0;
    }

    /**
     * 获取现在在功法格的功法 nbt
     * @return 功法 nbt
     */
    public NbtCompound getUesKungFu(){
        NbtList canUes = getCanUesKungFu();
        if(canUes.isEmpty()){
            return null;
        }

        NbtList canUesGroup = (NbtList) canUes.get(getKungFuGroupUesIn());
        if(canUesGroup.isEmpty()){
            return null;
        }
        return canUesGroup.getCompound(getKungFuUesIn());
    }

    /**
     * 通过 combo_in nbt 获取正在 combo 的功法 nbt
     * @return 功法 nbt
     */
    public NbtCompound getUesKungFuFromComboIn(){
        NbtCompound nbt = EntityData.getPersistentData().getCompound("combo_in");

        NbtList canUes = getCanUesKungFu();
        if(canUes.isEmpty()){
            return null;
        }

        NbtList canUesGroup = (NbtList) canUes.get(nbt.getInt("group_ues_in"));
        if(canUesGroup.isEmpty()){
            return null;
        }
        return canUesGroup.getCompound(nbt.getInt("ues_in"));
    }

    /**
     * 获取现在在功法格的功法 
     * @return 功法设置 (KungFuSettings)
     */
    public KungFuSettings getUesKungFuSettings(){
        NbtCompound nbt = getUesKungFu();
        if(nbt == null){
            return null;
        }

        return kungFuSettings[nbt.getInt("index")];
    }

    /**
     * 获取现在 combo 中的功法下标组 (功法组下标, 功法下标)
     * @return nbt list
     */
    public NbtCompound getComboIn(){
        NbtCompound nbt = EntityData.getPersistentData();
        if(nbt.contains("combo_in")){
            return nbt.getCompound("combo_in");
        }
        return null;
    }

    /**
     * 获取现在 combo 中的功法层名
     * @return
     */
    public String getUesKungFuTiekName(){
        if(getComboIn() == null){
            return "";
        }

        NbtCompound nbt = getUesKungFu();
        if(nbt == null){
            return "";
        }

        int tiek = nbt.getInt("tieks") - 1;
        if(tiek < 0){
            return getUesKungFuSettings().getCanMaxUesTiek((NbtList) nbt.get("can_ues_tieks")).Name.getString();
        }

        return getUesKungFuSettings().getKungFuTiek(tiek).Name.getString();
    }

    /**
     * 获取现在功法格的功法第一个不能使用的功法层
     * @return 返回功法层, 全部可用返回 null
     */
    public KungFuTiekSettings getKungFuNotUesTiek(){
        NbtCompound nbt = getUesKungFu();
        NbtList nbtList = (NbtList) nbt.get("can_ues_tieks");
        for(int index = 0; index < nbtList.size(); index++){
            if(((NbtCompound) nbtList.get(index)).getBoolean(index+"")){
                continue;
            }
            return getUesKungFuSettings().getKungFuTiek(index);
        }
        return null;
    }

    /**
     * 获取现在功法格的功法的功法熟练
     */
    public int getKungFuProficiency(){
        NbtCompound nbt = getUesKungFu();
        if(!nbt.contains("proficiency")){
            return 0;
        }

        return nbt.getInt("proficiency");
    }

    /**
     * 获取功法物品的功法领悟度
     * @param stack 物品格 (物品栏的一个格子)
     */
    public static int getKungFuItemProficiency(ItemStack stack){
        if(!(stack.getItem() instanceof KungFuItem)){
            return 0;
        }
        
        NbtCompound nbt = stack.getOrCreateNbt();
        if(!nbt.contains("proficiency")){
            nbt.putInt("proficiency", 0);
            return 0;
        }

        return nbt.getInt("proficiency");
    }
    
    /**
     * 功法设置
     */
    public abstract static class KungFuSettings{
        private KungFuTiekSettings[] kungFuTiekSettings;
        public Identifier Texture;
        public int Consume = 0;
        public int ComboTime = 0;
        public TranslatableText Name;

        public KungFuSettings(KungFuTiekSettings ...kungFuTiekSettings){
            this.kungFuTiekSettings = kungFuTiekSettings;
        }

        public KungFuSettings setName(TranslatableText name){
            Name = name;
            return this;
        }

        /**
         * 功法图标
         * @param texture 纹理路径
         */
        public KungFuSettings setTexture(Identifier texture){
            Texture = texture;
            return this;
        }

        public KungFuSettings setConsume(int consume){
            Consume = consume;
            return this;
        }

        /**
         * 功法 combo 有效时间
         * @param comboTime tick
         */
        public KungFuSettings setComboTime(int comboTime){
            ComboTime = comboTime;
            return this;
        }

        /**
         * 初始化第一次入门功法 nbt
         * @param EntityData player
         */
        public void initialNbt(IEntityDataSaver EntityData){
            NbtCompound nbt = new NbtCompound();
            NbtList nbtList = new NbtList();

            nbt.putBoolean("" + 0, true);
            nbtList.add(nbt);

            for(int index = 1; index < kungFuTiekSettings.length; index++){
                nbt = new NbtCompound();
                nbt.putBoolean("" + index, false);

                nbtList.add(nbt);
            }

            nbt = new NbtCompound();
            nbt.putInt("combo_end", ComboTime);
            nbt.putInt("combo_in", ComboTime);
            nbt.putInt("tieks", 0);
            nbt.putInt("proficiency", 0);
            nbt.putString("name", Name.getKey());
            for(int index = 0; index < kungFuSettings.length; index++){
                if(kungFuSettings[index].Name.getKey().equals(Name.getKey())){
                    nbt.putInt("index", index); 
                    break;
                }
            }
            nbt.put("can_ues_tieks", nbtList);
            EntityData.getKungFuServerData().getCanUesKungFuGroup().add(nbt);
        }

        public KungFuTiekSettings getKungFuTiek(int index){
            return kungFuTiekSettings[index];
        }

        public int getKungFuTiekIndex(){
            return kungFuTiekSettings.length -1;
        }

        /**
         * 获取功法下一功法层下标, 如果超出最大功法层 回到第一功法层
         * @param index 当前一功法层下标
         * @param canUesTieks 可以使用的功法层 (can_ues_kung_fu 下的功法 nbt list can_ues_tieks)
         * @return 下一功法层下标
         */
        public int getNextTiekIndex(int index, NbtList canUesTieks){
            int nextIndex = index + 1;
            if(nextIndex < kungFuTiekSettings.length && canUesTieks.getCompound(nextIndex).getBoolean(""+nextIndex)){
                return nextIndex;
            }

            return 0;
        }

        /**
         * 可以使用的最大功法层
         * @param canUesTieks 可以使用的功法层 (can_ues_kung_fu 下的功法 nbt list can_ues_tieks)
         * @return 功法层
         */
        public KungFuTiekSettings getCanMaxUesTiek(NbtList canUesTieks){
            for(int index = 0; index < canUesTieks.size(); index++){
                if(((NbtCompound) canUesTieks.get(index)).getBoolean(index+"")){
                    continue;
                }

                if(index > 0){
                    return kungFuTiekSettings[index - 1];
                }

                return kungFuTiekSettings[0];
            }

            return kungFuTiekSettings[kungFuTiekSettings.length - 1];
        }

        public abstract void comboStart(MinecraftServer server, ServerPlayerEntity player);
        public abstract void comboToNext(MinecraftServer server, ServerPlayerEntity player);
        public abstract void comboEnd(MinecraftServer server, ServerPlayerEntity player);
    }

    /**
     * 功法层设置
     */
    public static class KungFuTiekSettings{
        public final KungFuTiek kungFuTiek;
        public TranslatableText Name;
        public int Proficiency;

        public KungFuTiekSettings(KungFuTiek kungFuTiek){
            this.kungFuTiek = kungFuTiek;
        }

        public KungFuTiekSettings setName(TranslatableText name){
            Name = name;
            return this;
        }

        /**
         * 设置功法层可用所需熟练度
         * 
         * 第一层设置将是功法物品的功法领悟度
         * 之后每一层都是功法熟练度
         * @param proficiency 熟练度
         */
        public KungFuTiekSettings setProficiency(int proficiency){
            Proficiency = proficiency;
            return this;
        }
    }

    public interface KungFuTiek{
        void function(MinecraftServer server, ServerPlayerEntity player);
    }
}
