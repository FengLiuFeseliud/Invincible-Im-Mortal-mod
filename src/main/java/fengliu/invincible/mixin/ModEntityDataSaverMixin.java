package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import fengliu.invincible.util.CultivationCilentData;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.KungFuCilentData;
import fengliu.invincible.util.KungFuServerData;
import fengliu.invincible.util.LianDanServerData;

/*
 * 为每个实体添加修为数据
 */

@Mixin(Entity.class)
public class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    public NbtCompound getPersistentData() {
        if(this.persistentData != null){
            return persistentData;
        }
        NbtCompound persistent_data = new NbtCompound();
        persistent_data.putInt("cultivation_level", 0);
        persistent_data.putInt("cultivation_exp", 0);
        persistent_data.putInt("lian_dan_level", 0);
        persistent_data.putInt("lian_dan_exp", 0);
        persistent_data.putInt("mana", 0);
        persistent_data.putInt("kung_fu_ues_in", 0);
        persistent_data.putInt("kung_fu_group_ues_in", 0);
        persistent_data.put("combo_in", new NbtCompound());
        persistent_data.put("can_ues_kung_fu", new NbtList());
        this.persistentData = persistent_data;
        return this.persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<CallbackInfo> info){
        NbtCompound persistent_data = getPersistentData();
        if(persistent_data != null){
            return;
        }
        nbt.put("invincible.cultivation", persistent_data);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadNbt(NbtCompound nbt, CallbackInfo info){
        if(nbt.contains("invincible.cultivation", 10)){
            return;
        }
        persistentData = nbt.getCompound("invincible.cultivation");
    }

    @Override
    public void writePersistentData(NbtCompound nbt) {
        if(!nbt.contains("invincible.cultivation", 10)){
            return;
        }
        persistentData = nbt.getCompound("invincible.cultivation");
    }

    @Override
    public CultivationCilentData getCilentCultivationData() {
        return new CultivationCilentData(this);
    }

    @Override
    public CultivationServerData getServerCultivationData() {
        return new CultivationServerData(this);
    }

    @Override
    public KungFuCilentData getKungFuCilentData() {
        return new KungFuCilentData(this);
    }

    @Override
    public KungFuServerData getKungFuServerData() {
        return new KungFuServerData(this);
    }

    @Override
    public LianDanServerData getLianDanServerData() {
        return new LianDanServerData(this);
    }
}
