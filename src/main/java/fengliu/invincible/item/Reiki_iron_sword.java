package fengliu.invincible.item;

import fengliu.invincible.util.CultivationServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Reiki_iron_sword extends ManaSword{

    public Reiki_iron_sword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings,
            ManaSettings manaSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, manaSettings);
    }

    @Override
    public boolean activeSkill(World world, PlayerEntity user, Hand hand, CultivationServerData cultivationData) {
        user.sendMessage(getName(), isDamageable());
        return true;
    }

}
