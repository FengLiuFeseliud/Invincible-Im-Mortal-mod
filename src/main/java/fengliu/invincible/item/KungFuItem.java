package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class KungFuItem extends WrittenBookItem{

    public KungFuItem(Settings settings) {
        super(settings);
    }

    /**
     * 列表每多一项增加一页
     * @return
     */
    public TranslatableText[] addKungFuText(){
        return null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtCompound nbt = itemStack.getOrCreateNbt();

        NbtList nbtList = new NbtList();
        TranslatableText[] kungFuText = addKungFuText();
        if(kungFuText == null){
            nbt.put(PAGES_KEY, nbtList);
            return super.use(world, user, hand);
        }

        for(TranslatableText page: kungFuText){
            nbtList.add(NbtString.of(page.getString()));
        }
        nbt.put(PAGES_KEY, nbtList);
        return super.use(world, user, hand);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }
}
