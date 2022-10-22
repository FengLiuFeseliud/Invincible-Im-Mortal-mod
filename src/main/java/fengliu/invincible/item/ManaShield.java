package fengliu.invincible.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ManaShield extends ShieldItem implements ManaSkillsItem {
    private ManaSkillSettings[] ManaSkillSettings;

    public ManaShield(Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(settings);
        ManaSkillSettings = manaSkillsSettings;
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient || ManaSkillSettings.length == 0){
            return super.use(world, user, hand);
        }
        ManaSkillSettings uesSkill = ManaSkillSettings[getUesSkill(user, ManaSkillSettings)];

        if(!tryUesSkill(uesSkill, this, user)){
            return super.use(world, user, hand);
        }

        if(uesSkill.Skill.function(world, user, hand)){
            consumeMana(uesSkill, user);
        }
        return super.use(world, user, hand);
    }
}
