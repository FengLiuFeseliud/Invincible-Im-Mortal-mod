package fengliu.invincible.item;

import java.util.List;
import java.util.Random;

import fengliu.invincible.invincibleMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Jade extends Item {

    private static final String[] JADE_LV_TOOLTIP_KEY = {
        "item.invincible.jade.Lv0.tooltip",
        "item.invincible.jade.Lv1.tooltip",
        "item.invincible.jade.Lv2.tooltip",
        "item.invincible.jade.Lv3.tooltip",
        "item.invincible.jade.Lv4.tooltip",
        "item.invincible.jade.Lv5.tooltip",
        "item.invincible.jade.Lv6.tooltip",
        "item.invincible.jade.Lv7.tooltip",
        "item.invincible.jade.Lv8.tooltip",
        "item.invincible.jade.Lv9.tooltip"
    };

    public static final int[] JADE_LV = {
        0,
        35,
        50,
        75,
        100,
        125,
        150,
        175,
        200,
        300
    };

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack jadeItemStack = user.getStackInHand(hand);
        if(jadeItemStack.hasNbt()){
            return super.use(world, user, hand);
        }
        jadeItemStack.setCount(jadeItemStack.getCount() - 1);
        
        int random_jade_lv = new Random().nextInt(1, JADE_LV_TOOLTIP_KEY.length);
        NbtCompound jade_nbt= new NbtCompound();
        jade_nbt.putInt("invincible.jade_lv", random_jade_lv);

        jadeItemStack = new ItemStack(invincibleMod.JADE, 1);
        jadeItemStack.setNbt(jade_nbt);

        world.spawnEntity(new ItemEntity(
            world, user.getX(), user.getY(), user.getZ(), jadeItemStack
        ));

        return super.use(world, user, hand);
    }

    public Jade(Settings settings) {
        super(settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.jade.tooltip"));

        if(!stack.hasNbt()){
            tooltip.add(new TranslatableText(JADE_LV_TOOLTIP_KEY[0]));
            return;
        }
        
        int jadeLv = stack.getNbt().getInt("invincible.jade_lv");
        if(jadeLv < 0 || jadeLv > JADE_LV_TOOLTIP_KEY.length){
            stack.setNbt(new NbtCompound());
            return;
        }
        
        tooltip.add(new TranslatableText(JADE_LV_TOOLTIP_KEY[jadeLv]));
    }
}
