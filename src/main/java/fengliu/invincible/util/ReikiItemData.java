package fengliu.invincible.util;

import java.util.Optional;

import fengliu.invincible.entity.block.ImplementedInventory;
import fengliu.invincible.recipe.InjectionReikiRecipe;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * 灵气物品
 */
public class ReikiItemData {

    public static int getMaxReiki(ItemStack stack){
        return ((ReikiItem) stack.getItem()).getMaxReiki();
    }

    public static int getInitialReiki(ItemStack stack){
        return ((ReikiItem) stack.getItem()).getInitialReiki();
    }

    public static int getTargetReiki(ItemStack stack){
        return ((ReikiItem) stack.getItem()).getTargetReiki();
    }

    public static int getMaxInjectionReiki(ItemStack stack){
        return ((ReikiItem) stack.getItem()).getMaxInjectionReiki();
    }

    public static boolean canInjectionReiki(ItemStack stack){
        return ((ReikiItem) stack.getItem()).canInjectionReiki();
    }

    /**
     * 获取物品格的物品灵气
     * @param stack 物品格 (物品栏的一个格子)
     * @return 物品灵气
     */
    public static int getReiki(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains("invincible.reiki")){
            return nbt.getInt("invincible.reiki");
        }
        int reiki = getInitialReiki(stack);
        nbt.putInt("invincible.reiki", reiki);
        
        stack.setNbt(nbt);
        return reiki;
    }

    /**
     * 获取物品格的物品注入灵气
     * @param stack 物品格 (物品栏的一个格子)
     * @return 物品注入多少灵气
     */
    public static int getInjectionReiki(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains("invincible.injection_reiki")){
            return nbt.getInt("invincible.injection_reiki");
        }

        int injectionReiki = getReiki(stack);
        nbt.putInt("invincible.reiki", injectionReiki);
        
        stack.setNbt(nbt);
        return injectionReiki;
    }

    public static int getTargetNeedReiki(ItemStack stack){
        if(!canInjectionReiki(stack)){
            return 0;
        }
        return getTargetReiki(stack) - getInitialReiki(stack);
    }

    /**
     * 判断物品是否还存在灵气
     * @param stack 物品格 (物品栏的一个格子)
     * @return 不存在灵气为 true
     */
    public static boolean isConsumeReiki(ItemStack stack){
        if(getMaxReiki(stack) == 0 && getReiki(stack) == 0){
            return false;
        }
        return true;
    }

    /**
     * 判断物品注入灵气是否超过当前物品注入灵气目标
     * @param stack 物品格 (物品栏的一个格子)
     * @return 超过当前注入灵气目标为 true
     */
    public static boolean isExceedTargetReiki(ItemStack stack){
        if(getReiki(stack) < getTargetReiki(stack)){
            return false;
        }
        return true;
    }

    /**
     * 判断物品注入灵气是否超过当前物品可注入灵气的最大值
     * @param stack 物品格 (物品栏的一个格子)
     * @return 超过可注入灵气最大值为 true
     */
    public static boolean isMaxInjectionReiki(ItemStack stack){
        if(getReiki(stack) < getMaxInjectionReiki(stack)){
            return false;
        }
        return true;
    }
    
    /**
     * 消耗物品指定灵气量
     * @param consume 灵气量
     * @param stack 物品格 (物品栏的一个格子)
     * @return 成功消耗为 true
     */
    public static boolean consume(int consume, ItemStack stack){
        int maxReiki = getMaxReiki(stack);
        if(consume > maxReiki){
            return false;
        }

        int reiki = getReiki(stack);
        if(consume >  reiki){
            return false;
        }

        stack.getNbt().putInt("invincible.reiki", reiki - consume);
        return true;
    }

    /**
     * 吸收物品指定灵气量
     * 消耗物品指定灵气, 物品不存在灵气时减少这个物品格一个物品并重置灵气量
     * @param absord 灵气量
     * @param stack 物品格 (物品栏的一个格子)
     * @return 吸收成功多少灵气, 没有吸收到灵气为 0, 指定灵气量超过物品灵气剩余返回 物品灵气剩余量
     */
    public static int absord(int absord, ItemStack stack){
        if(!isConsumeReiki(stack)){
            return 0;
        }
        
        if(consume(absord, stack)){
            return absord;
        }

        int residue = getReiki(stack);
        stack.decrement(1);
        if(!stack.isEmpty()){
            stack.getNbt().putInt("invincible.reiki", getMaxReiki(stack));
        }

        return residue;
    }

    /**
     * 注入物品指定灵气量
     * @param injection 灵气量
     * @param stack 物品格 (物品栏的一个格子)
     */
    public static void injection(int injection, ItemStack stack){
        if(!canInjectionReiki(stack)){
            return;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("invincible.reiki", injection + getReiki(stack));

        stack.setNbt(nbt);
    }

    /**
     * 尝试注入物品指定灵气量, 不注入灵气返回可注入灵气量
     * @param injection 灵气量
     * @param exceedTargetInjection 是否允许超过灵气注入目标
     * @param stack 物品格 (物品栏的一个格子)
     * @return 可注入成功多少灵气, 无法注入返回 0, 指定灵气量超过最大可注入灵气返回 可再注入多少灵气
     */
    public static int tryInjection(int injection, boolean exceedTargetInjection, ItemStack stack){
        if(!canInjectionReiki(stack)){
            return 0;
        }
        if(isMaxInjectionReiki(stack)){
            return 0;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        if(!nbt.contains("invincible.reach_target") && isExceedTargetReiki(stack)){
            nbt.putInt("invincible.reach_target", 1);
            stack.setNbt(nbt);
            if(!exceedTargetInjection){
                return 0;
            }
        }

        int new_reiki = getReiki(stack) + injection;
        if(new_reiki > getMaxInjectionReiki(stack)){
            return new_reiki - getMaxInjectionReiki(stack);
        }

        return injection;
    }

    /**
     * 注入物品指定灵气量, 完成目标进行配方合成并将物品移至新物品格 (物品栏的一个格子)
     * @param world 世界
     * @param injection 灵气量
     * @param fromStack 从这个物品格移动至新物品格
     * @param toSlot 移动至的物品格序号
     * @param be 块存储实体
     * @return 成功合成为 true
     */
    public static boolean injectionToNewItemStack(World world, int injection,  ItemStack fromStack, int toSlot, ImplementedInventory be){
        ItemStack toStack = be.getStack(toSlot);
        if(toStack.getCount() == toStack.getMaxCount() || fromStack.isEmpty()){
            return false;
        }

        if(!isExceedTargetReiki(fromStack)){
            injection(tryInjection(injection, false, fromStack), fromStack);
            return false;
        }

        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0, fromStack);
        Optional<InjectionReikiRecipe> match = world.getRecipeManager().getFirstMatch(InjectionReikiRecipe.Type.INSTANCE, inventory, world);

        if(!match.isPresent()){
            return false;
        }

        ItemStack matchStack  = match.get().getOutput().copy();
        if(toStack.isEmpty()){
            be.setStack(toSlot, matchStack);
        }else if(!toStack.isOf(matchStack.getItem())){
            return false;
        }else{
            be.getStack(toSlot).increment(match.get().getCount());
        }
        fromStack.decrement(1);

        NbtCompound nbt = fromStack.getOrCreateNbt();
        nbt.remove("invincible.reach_target");
        nbt.putInt("invincible.reiki", getInitialReiki(fromStack));
        return true;
    }
}
