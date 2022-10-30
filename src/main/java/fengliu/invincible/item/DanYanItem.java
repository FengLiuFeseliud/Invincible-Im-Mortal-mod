package fengliu.invincible.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DanYanItem extends Item implements QualityItem {
    private Map<Integer, Item> DanYanQualityItem;
    private final int LianDanLevel;
    private final int LianDanExp;
    private final DanYanFunction Function;
    private final int Gain;
    private final int Cd;

    public DanYanItem(Settings settings, int lianDanLevel, int lianDanExp, DanYanFunction function, int gain, int cd) {
        super(settings);
        LianDanLevel = lianDanLevel;
        LianDanExp = lianDanExp;
        Function = function;
        Gain = gain;
        Cd = cd;
    }

    @Override
    public int getLevel() {
        return LianDanLevel;
    }

    @Override
    public int getExp() {
        return LianDanExp;
    }

    @Override
    public void setQuality(Map<Integer, Item> danYanQualityItem) {
        DanYanQualityItem = danYanQualityItem;
    }

    @Override
    public Map<Integer, Item> getQualityItems() {
        return DanYanQualityItem;
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }

        if(DanYanQualityItem == null){
            setQuality(new HashMap<>());
        }

        user.getStackInHand(hand).decrement(1);
        Function.function(world, user, hand, Gain);
        useCd(user);
        return super.use(world, user, hand);
    }

    /**
     * 设置所有该丹药品阶进入cd, 防止短时间使用该丹药的不同品阶
     */
    public void useCd(PlayerEntity user){
        DanYanQualityItem.values().forEach((danYan) -> {
            ItemCooldownManager cooldown = user.getItemCooldownManager();
            if(!cooldown.isCoolingDown(danYan)){
                cooldown.set(danYan, Cd * 20);
            }
        });
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
