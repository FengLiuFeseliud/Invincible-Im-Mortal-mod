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

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostHitManaSkillSettings postHitManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        PostHitManaSkillSettings = postHitManaSkillSettings;
    }

    public ManaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostHitManaSkillSettings postHitManaSkillSettings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        PostHitManaSkillSettings = postHitManaSkillSettings;
        ManaSkillSettings = manaSkillsSettings;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }
        ManaSkillSettings uesSkill = ManaSkillSettings[getUesSkill(user, ManaSkillSettings)];

        if(!tryUesSkill(uesSkill, this, user)){
            return super.use(world, user, hand);
        }
        uesSkill.Skill.function(world, user, hand);
        return super.use(world, user, hand);
    }

    public void setManaSkillSettings(PlayerEntity user){
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

        PostHitManaSkillSettings.PostHitSkill.function(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
