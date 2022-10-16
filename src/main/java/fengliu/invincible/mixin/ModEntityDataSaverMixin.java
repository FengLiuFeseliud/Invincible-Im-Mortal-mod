package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import fengliu.invincible.util.CultivationCilentData;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;

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
        persistent_data.putInt("mana", 0);
        persistent_data.putFloat("gain", 1f);
        this.persistentData = persistent_data;
        return this.persistentData;
    }

    @Override
    public CultivationCilentData getCilentCultivationData() {
        return new CultivationCilentData(this);
    }

    @Override
    public CultivationServerData getServerCultivationData() {
        return new CultivationServerData(this);
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<CallbackInfo> info){
        NbtCompound persistent_data = getPersistentData();
        if(persistent_data != null){
            return;
        }
        nbt.put("invincible.player_cultivation", persistent_data);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadNbt(NbtCompound nbt, CallbackInfo info){
        if(nbt.contains("invincible.player_cultivation", 10)){
            return;
        }
        persistentData = nbt.getCompound("invincible.player_cultivation");
    }

    @Override
    public void writePersistentData(NbtCompound nbt) {
        if(!nbt.contains("invincible.player_cultivation", 10)){
            return;
        }
        persistentData = nbt.getCompound("invincible.player_cultivation");
    }

}
