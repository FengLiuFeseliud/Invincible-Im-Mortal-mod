package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/*
 * 保存玩家修为数据
 */

@Mixin(LivingEntity.class)
public abstract class ModPlayerDataSaveMixin extends Entity {

    protected ModPlayerDataSaveMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCultivationDataToNbt(NbtCompound nbt, CallbackInfo info) {
        IEntityDataSaver data = (IEntityDataSaver) this;
        NbtCompound cultivationNbtData = data.getPersistentData();

        nbt.put("invincible.cultivation", cultivationNbtData);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCultivationDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        NbtCompound data = nbt.getCompound("invincible.cultivation");
        invincibleMod.LOGGER.info(data.toString());

        ((IEntityDataSaver) this).writePersistentData(nbt);
    }

    /**
     * 向主手物品添加境界攻击加成 Nbt
     */
    @Inject(method = "getMainHandStack", at = @At("RETURN"))
    public ItemStack getMainHandStack(CallbackInfoReturnable<ItemStack> info) {
        ItemStack mainHandStack = info.getReturnValue();
        mainHandStack.getOrCreateNbt().putFloat("invincible.player_base_attack_damage", ((IEntityDataSaver) this).getCilentCultivationData().getCultivationLevel().getBaseAttack());
        
        return mainHandStack;
    }

}
