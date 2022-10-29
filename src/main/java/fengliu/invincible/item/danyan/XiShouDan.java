package fengliu.invincible.item.danyan;

import java.util.List;

import fengliu.invincible.item.DanYanItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class XiShouDan extends DanYanItem {

    public XiShouDan(Settings settings, DanYanFunction function, int gain, int cd) {
        super(settings, function, gain, cd);
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
