package fengliu.invincible.item.kungfu;

import java.util.List;

import fengliu.invincible.item.KungFuItem;
import fengliu.invincible.util.KungFuCilentData.KungFuSettings;
import fengliu.invincible.util.KungFuCilentData.KungFuTiekSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class HuanHuoKung extends KungFuItem {

    public HuanHuoKung(Settings settings, KungFuSettings kungFuSettings) {
        super(settings, kungFuSettings);
    }

    @Override
    public TranslatableText[] addKungFuText() {
        TranslatableText[] data = {
            
        };

        return data;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.huan_huo_kung.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    
    public static class HuanHuoKungSettings extends KungFuSettings{
        public HuanHuoKungSettings(KungFuTiekSettings... kungFuTiekSettings){
            super(kungFuTiekSettings);
        }

        @Override
        public void comboStart(MinecraftServer server, ServerPlayerEntity player) {
            
        }

        @Override
        public void comboToNext(MinecraftServer server, ServerPlayerEntity player) {
            
        }

        @Override
        public void comboEnd(MinecraftServer server, ServerPlayerEntity player) {
            
        }

        public static void tiek1(MinecraftServer server, ServerPlayerEntity player) {
            
        }

        public static void tiek2(MinecraftServer server, ServerPlayerEntity player) {

        }

        public static void tiek3(MinecraftServer server, ServerPlayerEntity player) {

        }
    }
}
