package fengliu.invincible.item.danyan;

import java.util.List;

import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HuiQiDan extends DanYanItem {

    public HuiQiDan(Settings settings, DanYanFunction function, int gain, int cd) {
        super(settings, function, gain, cd);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.hui_qi_dan.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    
    public static void HuiQiDanFunction(World world, PlayerEntity user, Hand hand, int gain){
        ((IEntityDataSaver) user).getServerCultivationData().recoverMana(100 * gain);
    }
}
