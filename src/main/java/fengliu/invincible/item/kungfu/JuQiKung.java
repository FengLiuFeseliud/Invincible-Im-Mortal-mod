package fengliu.invincible.item.kungfu;

import fengliu.invincible.util.KungFuCilentData.KungFuSettings;
import fengliu.invincible.util.KungFuCilentData.KungFuTiek;

import java.util.List;

import fengliu.invincible.item.KungFuItem;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class JuQiKung extends KungFuItem{
    
    public JuQiKung(Settings settings) {
        super(settings);
    }

    @Override
    public TranslatableText[] addKungFuText() {
        TranslatableText[] data = {
            new TranslatableText("item.invincible.ju_qi_kung.text"),
            new TranslatableText("item.invincible.ju_qi_kung.text_2"),
            new TranslatableText("item.invincible.ju_qi_kung.text_3"),
            new TranslatableText("item.invincible.ju_qi_kung.text_4"),
            new TranslatableText("item.invincible.ju_qi_kung.text_5"),
        };

        return data;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.ju_qi_kung.tooltip"));
    }

    public static class JuQiKungSettings extends KungFuSettings {
        public JuQiKungSettings(KungFuTiek... kungFuTieks){
            super(kungFuTieks);
        }
    
        @Override
        public void comboStart(MinecraftServer server, ServerPlayerEntity player) {
            
        }
    
        @Override
        public void comboToNext(MinecraftServer server, ServerPlayerEntity player) {
            
        }
    
        @Override
        public void comboEnd(MinecraftServer server, ServerPlayerEntity player) {
            ((IEntityDataSaver) player).getServerCultivationData().minusGain("ju_qi_kung");
        }
    
        public static void tiek1(MinecraftServer server, ServerPlayerEntity player){
            ((IEntityDataSaver) player).getServerCultivationData().setGain("ju_qi_kung", 5.0f);
        }
        
        public static void tiek2(MinecraftServer server, ServerPlayerEntity player){
            ((IEntityDataSaver) player).getServerCultivationData().addGain("ju_qi_kung", 5.0f);
        }
    
        public static void tiek3(MinecraftServer server, ServerPlayerEntity player){
            ((IEntityDataSaver) player).getServerCultivationData().addGain("ju_qi_kung", 5.0f);
        }
    }
}
