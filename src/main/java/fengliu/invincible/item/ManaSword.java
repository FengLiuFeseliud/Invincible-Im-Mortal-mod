package fengliu.invincible.item;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class ManaSword extends SwordItem implements ManaItem {
    protected ManaSettings manaSettings;

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSettings manaSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.manaSettings = manaSettings;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }

        CultivationServerData cultivationData = ((IEntityDataSaver) user).getServerCultivationData();

        if(!manaSettings.canUesSkill(cultivationData.getMana())){
            return super.use(world, user, hand);
        }

        if(!activeSkill(world, user, hand, cultivationData)){
            return super.use(world, user, hand);
        }
        
        cultivationData.consumeMana(manaSettings.getConsume());
        return super.use(world, user, hand);
    }

    @Override
    public abstract boolean activeSkill(World world, PlayerEntity user, Hand hand, CultivationServerData cultivationData);
}
