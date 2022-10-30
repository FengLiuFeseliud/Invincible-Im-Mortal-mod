package fengliu.invincible.item.danyan;

import java.util.List;
import java.util.Map;

import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HuiQiDan extends DanYanItem {

    public HuiQiDan(Settings settings, int gain, int cd) {
        super(settings, 0, 1, HuiQiDan::HuiQiDanFunction, gain, cd);
    }

    public static void HuiQiDanFunction(World world, PlayerEntity user, Hand hand, int gain){
        ((IEntityDataSaver) user).getServerCultivationData().recoverMana(100 * gain);
    }

    @Override
    public void setQuality(Map<Integer, Item> danYanQuality) {
        danYanQuality.put(0, ModItems.HUI_QI_DAN);
        danYanQuality.put(10, ModItems.HUI_QI_DAN_1);
        danYanQuality.put(100, ModItems.HUI_QI_DAN_2);
        danYanQuality.put(250, ModItems.HUI_QI_DAN_3);
        danYanQuality.put(600, ModItems.HUI_QI_DAN_4);
        danYanQuality.put(1200, ModItems.HUI_QI_DAN_5);
        danYanQuality.put(2400, ModItems.HUI_QI_DAN_6);
        danYanQuality.put(5800, ModItems.HUI_QI_DAN_7);
        danYanQuality.put(11600, ModItems.HUI_QI_DAN_8);
        danYanQuality.put(23200, ModItems.HUI_QI_DAN_9);

        super.setQuality(danYanQuality);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.hui_qi_dan.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
