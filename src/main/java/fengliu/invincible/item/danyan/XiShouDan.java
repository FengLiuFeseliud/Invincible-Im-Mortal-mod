package fengliu.invincible.item.danyan;

import java.util.List;
import java.util.Map;

import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class XiShouDan extends DanYanItem {

    public XiShouDan(Settings settings, int gain, int cd) {
        super(settings, 0, 1, XiShouDan::XiShouDanFunction, gain, cd);
    }

    @Override
    public void setQuality(Map<Integer, Item> danYanQuality) {
        danYanQuality.put(0, ModItems.XI_SHOU_DAN);
        danYanQuality.put(10, ModItems.XI_SHOU_DAN_1);
        danYanQuality.put(100, ModItems.XI_SHOU_DAN_2);
        danYanQuality.put(250, ModItems.XI_SHOU_DAN_3);
        danYanQuality.put(600, ModItems.XI_SHOU_DAN_4);
        danYanQuality.put(1200, ModItems.XI_SHOU_DAN_5);
        danYanQuality.put(2400, ModItems.XI_SHOU_DAN_6);
        danYanQuality.put(5800, ModItems.XI_SHOU_DAN_7);
        danYanQuality.put(11600, ModItems.XI_SHOU_DAN_8);
        danYanQuality.put(23200, ModItems.XI_SHOU_DAN_9);

        super.setQuality(danYanQuality);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.xi_shou_dan.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static void XiShouDanFunction(World world, PlayerEntity user, Hand hand, int gain){
        if(user.getAbsorptionAmount() >= 50){
            return;
        }

        float amount = user.getAbsorptionAmount() + 4 * gain;
        if(amount > 50){
            amount = 50;
        }

        user.setAbsorptionAmount(amount);
    }
}
