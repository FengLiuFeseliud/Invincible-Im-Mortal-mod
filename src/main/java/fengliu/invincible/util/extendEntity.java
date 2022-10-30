package fengliu.invincible.util;

import net.minecraft.entity.Entity;

public interface extendEntity {
    /**
     *  跳过原版检查直接造成伤害, 原版 damage 有时候没效果, 不知道什么原因
     */
    void uesDamage(Entity attacker, float amount);
}
