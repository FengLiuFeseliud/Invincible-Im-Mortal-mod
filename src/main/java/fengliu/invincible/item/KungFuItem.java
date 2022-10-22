package fengliu.invincible.item;

import java.util.List;

import fengliu.invincible.util.KungFuCilentData;
import fengliu.invincible.util.KungFuCilentData.KungFuSettings;
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
    private final KungFuSettings kungFuSettings;

    public KungFuItem(Settings settings, KungFuSettings kungFuSettings) {
        super(settings);
        this.kungFuSettings = kungFuSettings;
    }

    public KungFuSettings getKungFuSettings(){
        return kungFuSettings;
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
        tooltip.add(new TranslatableText("info.invincible.kung_fu_learn_residue", 
            KungFuCilentData.getKungFuItemProficiency(stack), kungFuSettings.getKungFuTiek(0).Proficiency
        ));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }
}
