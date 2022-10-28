package fengliu.invincible.item.tool;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class ReikiIronEmpty extends Item {

    public ReikiIronEmpty(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_empty.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    
}
