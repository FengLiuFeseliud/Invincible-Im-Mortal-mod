package fengliu.invincible.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * 基础真元技能剑
 */
public abstract class ManaSword extends SwordItem implements ManaSkillsItem {
    private ManaSkillSettings[] ManaSkillSettings;
    private PostHitManaSkillSettings PostHitManaSkillSettings;

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ManaSkillSettings = manaSkillsSettings;
    }

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostHitManaSkillSettings postHitManaSkillSettings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        PostHitManaSkillSettings = postHitManaSkillSettings;
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

    public void setManaSkillSettings(PlayerEntity user){
        if(ManaSkillSettings == null){
            return;
        }
        
        setUesSkill(user, ManaSkillSettings, getUesSkill(user, ManaSkillSettings) + 1);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(PostHitManaSkillSettings == null){
            return super.postHit(stack, target, attacker);
        }
        
        if(!tryUesSkill(PostHitManaSkillSettings, this, (PlayerEntity) attacker)){
            attacker.getStackInHand(attacker.getActiveHand()).getNbt().remove("invincible.player_attack_damage");
            return super.postHit(stack, target, attacker);
        }

        if(PostHitManaSkillSettings.PostHitSkill.function(stack, target, attacker)){
            consumeMana(PostHitManaSkillSettings, (PlayerEntity) attacker);
        }
        return super.postHit(stack, target, attacker);
    }
}
