package fengliu.invincible.util;

import fengliu.invincible.api.Probability_Random;
import fengliu.invincible.api.Probability_Random.Random_Item;
import fengliu.invincible.networking.ModMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

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
        
        return EntityData.getPersistentData().getInt("mana");
    }

    public int addCultivationExpInUpLevel(int addExp){
        int cultivationExp = getCultivationExp();
        int new_cultivationExp = cultivationExp + addExp;
        EntityData.getPersistentData().putInt("cultivation_exp", new_cultivationExp);
        syncData(EntityData);

        return getUpLevelIndex(new_cultivationExp);
    }

    public boolean upLevel(){
        CultivationLevel level = getCultivationLevel();
        if(level.getLevel() >= CultivationLevelAll.length - 1){
            return false;
        }

        if(!level.canUpLevel(getCultivationExp())){
            return false;
        }

        Random_Item[] random = {
            new Random_Item(level.getUpLevelRate(), true),
            new Random_Item(level.getUpLevelFailureRate(), false)
        };
        boolean random_result = (boolean) Probability_Random.random(random).getItem();

        if(!random_result){
            return random_result;
        }

        EntityData.getPersistentData().putInt("cultivation_level", level.getLevel() + 1);
        syncData(EntityData);
        return random_result;
    }

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

    public void syncData(IEntityDataSaver player){
        ServerPlayNetworking.send((ServerPlayerEntity) EntityData, ModMessage.SYNC_DATA, 
            PacketByteBufs.create().writeNbt((player.getPersistentData())));
    }
}
