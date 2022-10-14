package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class Reiki_iron_dagger extends ManaSword {

    public Reiki_iron_dagger(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings,
            fengliu.invincible.item.ManaSkillsItem.PostHitManaSkillSettings postHitManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, postHitManaSkillSettings);
    }

    public static boolean activeSkill(ItemStack stack, LivingEntity target, LivingEntity attacker){
        attacker.getStackInHand(attacker.getActiveHand()).getNbt().putFloat("invincible.player_attack_damage", 2);
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_dagger.tooltip_2"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_dagger.tooltip_3"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
