package fengliu.invincible.item;

import java.util.List;

import fengliu.invincible.api.Probability_Random;
import fengliu.invincible.api.Probability_Random.Random_Item;
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

    private static class Jade_Lv{
        private String TOOLTIP_KEY;
        private int EFFECT;

        public Jade_Lv(int effect, String tooltipKey) {
            TOOLTIP_KEY = tooltipKey;
            EFFECT = effect;
        }


        public String getTooltipKey(){
            return TOOLTIP_KEY;
        }

        public int getEffect(){
            return EFFECT;
        }
    }

    /**
     * 玉石等级和概率
     */
    public static final Random_Item[] JADE_LV_LIST = {
        new Random_Item(0, new Jade_Lv(0, "item.invincible.jade.Lv0.tooltip")),
        // 95% Lv1 > Lv4
        new Random_Item(30, new Jade_Lv(35, "item.invincible.jade.Lv1.tooltip")),
        new Random_Item(45, new Jade_Lv(50, "item.invincible.jade.Lv2.tooltip")),
        new Random_Item(20, new Jade_Lv(75, "item.invincible.jade.Lv3.tooltip")),
        new Random_Item(10, new Jade_Lv(100, "item.invincible.jade.Lv4.tooltip")),
        // 5% Lv5 > Lv9
        new Random_Item(2, new Jade_Lv(125, "item.invincible.jade.Lv5.tooltip")),
        new Random_Item(1.5, new Jade_Lv(150, "item.invincible.jade.Lv6.tooltip")),
        new Random_Item(0.75, new Jade_Lv(175, "item.invincible.jade.Lv7.tooltip")),
        new Random_Item(0.5, new Jade_Lv(200, "item.invincible.jade.Lv8.tooltip")),
        new Random_Item(0.25, new Jade_Lv(300, "item.invincible.jade.Lv9.tooltip")),
    };

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack jadeItemStack = user.getStackInHand(hand);
        if(jadeItemStack.hasNbt()){
            return super.use(world, user, hand);
        }
        jadeItemStack.decrement(1);
        
        // 随机玉石等级
        Random_Item random_jade_lv = Probability_Random.random(JADE_LV_LIST);
        NbtCompound jade_nbt = new NbtCompound();
        for(int index = 0; index < JADE_LV_LIST.length; index++){
            if(random_jade_lv != JADE_LV_LIST[index]){
                continue;
            }

            jade_nbt.putInt("invincible.jade_lv", index);
            jade_nbt.putInt("invincible.jade_effect", ((Jade_Lv) random_jade_lv.getItem()).getEffect());
        }

        jadeItemStack = new ItemStack(ModItems.JADE, 1);
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
            tooltip.add(new TranslatableText(((Jade_Lv) JADE_LV_LIST[0].getItem()).getTooltipKey()));
            return;
        }
        
        int jadeLv = stack.getNbt().getInt("invincible.jade_lv");
        if(jadeLv < 0 || jadeLv > JADE_LV_LIST.length){
            stack.setNbt(new NbtCompound());
            return;
        }
        
        tooltip.add(new TranslatableText(((Jade_Lv) JADE_LV_LIST[jadeLv].getItem()).getTooltipKey()));
    }
}
