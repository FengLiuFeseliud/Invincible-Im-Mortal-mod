package fengliu.invincible.item;

import fengliu.invincible.util.CultivationServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ManaItem{
    public boolean activeSkill(World world, PlayerEntity user, Hand hand, CultivationServerData cultivationData);
}