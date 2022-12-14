package fengliu.invincible.item.tool;

import java.util.List;

import fengliu.invincible.item.ManaSword;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class ReikiIronDagger extends ManaSword {

    public ReikiIronDagger(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostHitManaSkillSettings postHitManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, postHitManaSkillSettings);
    }

    public static boolean postHitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker){
        attacker.getMainHandStack().getNbt().putFloat("invincible.player_attack_damage", 2);
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
