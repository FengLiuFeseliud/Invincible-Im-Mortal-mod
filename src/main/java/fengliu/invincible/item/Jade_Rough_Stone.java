package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class Jade_Rough_Stone extends Item {

    public Jade_Rough_Stone(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.jade_rough_stone.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    
}
