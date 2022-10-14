package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.invincibleMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;

/*
 * 允许 nbt 控制玩家属性
 */

@Mixin(LivingEntity.class)
public abstract class ModPlayerMixin extends LivingEntity{
    @Shadow(aliases = "HEALTH") private static final TrackedData<Float> HEALTH = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    protected ModPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // @Overwrite
    // public float getMaxHealth(CallbackInfoReturnable<CallbackInfo> info) {

    //     float old_health = info.getReturnValueF() + 10.0f;

    //     // this.dataTracker.set(HEALTH, old_health);
    //     invincibleMod.LOGGER.info("old_health" + old_health);
    //     return old_health + 9999;
    // }

}
