package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/*
 * 保存玩家修为数据
 */

@Mixin(PlayerEntity.class)
public abstract class ModPlayerDataSaveMixin extends LivingEntity {

    protected ModPlayerDataSaveMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCultivationDataToNbt(NbtCompound nbt, CallbackInfo info) {
        IEntityDataSaver data = (IEntityDataSaver) this;
        NbtCompound cultivationNbtData = data.getPersistentData();

        nbt.put("invincible.player_cultivation", cultivationNbtData);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCultivationDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        NbtCompound data = nbt.getCompound("invincible.player_cultivation");
        invincibleMod.LOGGER.info(data.toString());

        ((IEntityDataSaver) this).writePersistentData(nbt);
    }
}
