package fengliu.invincible.util;

import fengliu.invincible.invincibleMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class KungFuCilentData {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    private final IEntityDataSaver EntityData;

    public static void text(MinecraftServer server, ServerPlayerEntity player){
        player.sendMessage(new LiteralText("text1"), false);
    }

    public static void text1(MinecraftServer server, ServerPlayerEntity player){
        player.sendMessage(new LiteralText("text11"), false);
    }

    public static void text2(MinecraftServer server, ServerPlayerEntity player){
        player.sendMessage(new LiteralText("text2"), false);
    }

    protected static final KungFuSettings[] kungFuSettings = {
        new KungFuSettings(
            KungFuServerData::text,
            KungFuServerData::text1,
            KungFuServerData::text2
        )
            .setName(new TranslatableText("item.invincible.kung_fu"))
            .setTexture(new Identifier(MOD_ID, "textures/kung_fu/test.png"))
            .setConsume(0),
        new KungFuSettings(
            KungFuServerData::text,
            KungFuServerData::text1,
            KungFuServerData::text2
        )
            .setName(new TranslatableText("kung_fu.invincible.ju_ling_zhen.1"))
            .setTexture(new Identifier(MOD_ID, "textures/kung_fu/test2.png"))
            .setConsume(5),
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
    
    public static class KungFuSettings{
        private KungFuTiek[] kungFuTieks;
        public Identifier Texture;
        public int Consume;
        public TranslatableText Name;

        public KungFuSettings(KungFuTiek ...kungFuTieks){
            this.kungFuTieks = kungFuTieks;
        }

        public KungFuSettings setName(TranslatableText name){
            Name = name;
            return this;
        }

        public KungFuSettings setTexture(Identifier texture){
            Texture = texture;
            return this;
        }

        public KungFuSettings setConsume(int consume){
            Consume = consume;
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

            for(int index = 1; index < kungFuTieks.length; index++){
                nbt = new NbtCompound();
                nbt.putBoolean("" + index, true);

                nbtList.add(nbt);
            }

            nbt = new NbtCompound();
            nbt.putInt("combo_end", 25);
            nbt.putInt("combo_in", 25);
            nbt.putInt("tieks", 0);
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

        public KungFuTiek getKungFuTiek(int index){
            return kungFuTieks[index];
        }

        /**
         * 获取功法下一重天下标, 如果超出最大重天, 回到第一重天
         * @param index 当前一重天下标
         * @param canUesTieks 可以使用的重天 (can_ues_kung_fu 下的功法 nbt list can_ues_tieks)
         * @return 下一重天下标
         */
        public int getNextTiekIndex(int index, NbtList canUesTieks){
            int nextIndex = index + 1;
            if(nextIndex < kungFuTieks.length && canUesTieks.getCompound(nextIndex).getBoolean(""+nextIndex)){
                return nextIndex;
            }

            return 0;
        }

    }

    public interface KungFuTiek{
        void function(MinecraftServer server, ServerPlayerEntity player);
    }
}
