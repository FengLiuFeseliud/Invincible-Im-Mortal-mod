package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

/*
 * 允许 nbt 控制装备属性
 */

@Mixin(EnchantmentHelper.class)
public class EnchantmentMixin{

    /*
     *  nbt 控制攻击
     */
    @Inject(method = "getAttackDamage", at = @At("RETURN"))
    private static float getAttackDamage(ItemStack stack, EntityGroup group, CallbackInfoReturnable<CallbackInfo> info) {
        NbtCompound nbt = stack.getOrCreateNbt();

        float player_attack_damage = 0;
        if(nbt.contains("invincible.player_attack_damage")){
            player_attack_damage += nbt.getFloat("invincible.player_attack_damage");
        }

        return info.getReturnValueF() + player_attack_damage;
    }
}
