package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.extendEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.world.World;

/**
 * 扩展玩家数据
 */
@Mixin(LivingEntity.class)
public abstract class ModPlayerExtendMixin extends Entity implements extendEntity{
    @Shadow protected void applyDamage(DamageSource source, float amount){};

    protected ModPlayerExtendMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void uesDamage(Entity attacker, float amount) {
        this.applyDamage(new EntityDamageSource("player", attacker), amount);
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"))
    public final float getMaxHealth(CallbackInfoReturnable<CallbackInfo> info) {
        return (float) (info.getReturnValueF() + ((IEntityDataSaver) this).getCilentCultivationData().getCultivationLevel().getBaseHealth());
    }

    @Inject(method = "onAttacking", at = @At("HEAD"))
    public void onAttacking(Entity target, CallbackInfo info) {
        if(target instanceof LivingEntity){
            ((extendEntity) target).uesDamage(this, ((IEntityDataSaver) this).getCilentCultivationData().getCultivationLevel().getBaseAttack());
        }
    }
}
