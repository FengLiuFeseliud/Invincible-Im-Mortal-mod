package fengliu.invincible.item;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReikiIronPickaxe extends ManaPickaxe {

    public ReikiIronPickaxe(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostMineManaSkillSettings postMineManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, postMineManaSkillSettings);
    }
    
    public static boolean activeSkill(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner){
        
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
