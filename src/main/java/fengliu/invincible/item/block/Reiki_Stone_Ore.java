package fengliu.invincible.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class Reiki_Stone_Ore {
    
    public static class Lv1 extends BlockItem{

        public Lv1(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("block.invincible.reiki_stone_ore_1.tooltip"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv2 extends BlockItem{

        public Lv2(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("block.invincible.reiki_stone_ore_2.tooltip"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv3 extends BlockItem{

        public Lv3(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("block.invincible.reiki_stone_ore_3.tooltip"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv4 extends BlockItem{

        public Lv4(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("block.invincible.reiki_stone_ore_4.tooltip"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

}
