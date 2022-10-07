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

public abstract class ManaSword extends SwordItem implements ManaSkillsItem {
    private ManaSkillSettings[] ManaSkillSettings;

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ManaSkillSettings = manaSkillsSettings;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }

        CultivationServerData cultivationData = ((IEntityDataSaver) user).getServerCultivationData();
        
        if(!tryUesSkill(ManaSkillSettings[0], user, cultivationData.getMana())){
            return super.use(world, user, hand);
        }

        if(!ManaSkillSettings[0].Skill.function(world, user, hand, cultivationData)){
            return super.use(world, user, hand);
        }
        
        cultivationData.consumeMana(ManaSkillSettings[0].Consume);
        return super.use(world, user, hand);
    }
}
