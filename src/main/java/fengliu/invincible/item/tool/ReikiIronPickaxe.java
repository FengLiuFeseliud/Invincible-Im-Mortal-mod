package fengliu.invincible.item.tool;

import java.util.List;

import fengliu.invincible.item.ManaPickaxe;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ReikiIronPickaxe extends ManaPickaxe {

    public ReikiIronPickaxe(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, ManaSkillSettings);
    }
    
    public static boolean activeSkill(World world, PlayerEntity player, Hand hand){
        
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        // tooltip.add(new TranslatableText("item.invincible.reiki_iron_pickaxe.tooltip_2"));
        // tooltip.add(new TranslatableText("item.invincible.reiki_iron_pickaxe.tooltip_3"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
