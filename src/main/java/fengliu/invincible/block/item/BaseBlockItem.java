package fengliu.invincible.block.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class BaseBlockItem extends BlockItem {
    private final String TooltipKey;

    public BaseBlockItem(Block block, Settings settings) {
        super(block, settings);
        this.TooltipKey = "";
    }

    public BaseBlockItem(String tooltipKey, Block block, Settings settings) {
        super(block, settings);
        this.TooltipKey = tooltipKey;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText(TooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
