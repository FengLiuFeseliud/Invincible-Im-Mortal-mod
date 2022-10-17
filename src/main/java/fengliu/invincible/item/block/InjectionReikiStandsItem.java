package fengliu.invincible.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class InjectionReikiStandsItem extends BlockItem {

    public InjectionReikiStandsItem(Block block, Settings settings) {
        super(block, settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("block.invincible.injection_reiki_stands.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
