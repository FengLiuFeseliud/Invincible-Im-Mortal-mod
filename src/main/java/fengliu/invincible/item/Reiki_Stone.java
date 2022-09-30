package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class Reiki_Stone{

    public static class Lv0 extends Item {

        public Lv0(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_0.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_0.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv1 extends Item {

        public Lv1(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_1.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_1.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv2 extends Item {

        public Lv2(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_2.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_2.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv3 extends Item {

        public Lv3(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_3.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_3.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv4 extends Item {

        public Lv4(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_4.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_4.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }

    public static class Lv5 extends Item {

        public Lv5(Settings settings) {
            super(settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_5.tooltip"));
            tooltip.add(new TranslatableText("item.invincible.reiki_stone_5.tooltip_2"));
            super.appendTooltip(stack, world, tooltip, context);
        }

    }
}
