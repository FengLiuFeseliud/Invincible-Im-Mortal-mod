package fengliu.invincible.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class ZhenYanItem {
    
    public static class Lv1 extends BlockItem {

        public Lv1(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("block.invincible.zhen_yan_1.tooltip"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }
}
