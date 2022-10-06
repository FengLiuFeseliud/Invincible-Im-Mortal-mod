package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import fengliu.invincible.util.ReikiItem;
import fengliu.invincible.util.ReikiItemData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/*
 * 为每个物品添加灵气属性
 */

@Mixin(Item.class)
public class ItemMixin implements ReikiItem{
    private int Reiki = 0;
    private int MaxReiki = 0;


    public int getReiki() {
        return Reiki;
    }

    public int getMaxReiki() {
        return MaxReiki;
    }

    public void setMaxReiki(int reiki) {
        MaxReiki = reiki;
    }

    @Inject(method = "appendTooltip", at = @At("RETURN"))
    public  void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo info) {
        tooltip.add(new TranslatableText("info.invincible.item_reiki_residue", ReikiItemData.getReiki(stack), ReikiItemData.getMaxReiki(stack)));
    }
}