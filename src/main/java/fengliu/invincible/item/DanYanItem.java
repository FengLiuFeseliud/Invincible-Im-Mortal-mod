package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DanYanItem extends Item {
    private final DanYanFunction Function;
    private final int Gain;
    private final int Cd;

    public DanYanItem(Settings settings, DanYanFunction function, int gain, int cd) {
        super(settings);
        Function = function;
        Gain = gain;
        Cd = cd;
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }
        user.getStackInHand(hand).decrement(1);
        user.getItemCooldownManager().set(this, Cd * 20);
        Function.function(world, user, hand, Gain);
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("info.invincible.dan_yan_cd", Cd));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if(Gain  < 2){
            return super.hasGlint(stack);
        }
        return true;
    }

    public interface DanYanFunction{
        void function(World world, PlayerEntity user, Hand hand, int gain);
    }
}
