package fengliu.invincible.item.tool;

import java.util.List;

import fengliu.invincible.item.ManaSword;
import fengliu.invincible.util.ModTimer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ReikiIronSword extends ManaSword{
    public ReikiIronSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, manaSkillsSettings);
    }

    public static boolean activeSkill(World world, PlayerEntity user, Hand hand) {
        ItemStack mainStack = user.getMainHandStack();
        mainStack.getNbt().putFloat("invincible.player_attack_damage", 10);

        ModTimer.timer(world, "reiki_iron_sword", 40, (tserver, stimer, long_time) -> {
            mainStack.getNbt().remove("invincible.player_attack_damage");
        });
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip_2"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip_3"));
        super.appendTooltip(stack, world, tooltip, context);
    }

}
