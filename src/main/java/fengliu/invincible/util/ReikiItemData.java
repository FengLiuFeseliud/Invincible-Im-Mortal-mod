package fengliu.invincible.util;

import fengliu.invincible.invincibleMod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

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

    public static boolean isConsumeReiki(ItemStack stack){
        if(getMaxReiki(stack) == 0 && getReiki(stack) == 0){
            return false;
        }
        return true;
    }

    public static boolean isExceedTargetReiki(ItemStack stack){
        if(getReiki(stack) < getTargetReiki(stack)){
            return false;
        }
        return true;
    }

    public static boolean isMaxInjectionReiki(ItemStack stack){
        if(getReiki(stack) < getMaxInjectionReiki(stack)){
            return false;
        }
        return true;
    }
    
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

    public static void injection(int injection, ItemStack stack){
        if(!canInjectionReiki(stack)){
            return;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("invincible.reiki", injection + getReiki(stack));

        stack.setNbt(nbt);
    }

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
}
