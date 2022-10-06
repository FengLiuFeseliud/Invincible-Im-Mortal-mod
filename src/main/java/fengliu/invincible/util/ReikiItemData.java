package fengliu.invincible.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ReikiItemData {

    public static int getMaxReiki(ItemStack stack){
        if(!stack.hasNbt()){
            int maxReiki = ((ReikiItem) stack.getItem()).getMaxReiki();
            NbtCompound nbt = new NbtCompound();
            nbt.putInt("invincible.max_reiki", maxReiki);

            stack.setNbt(nbt);
            return maxReiki;
        }

        return stack.getNbt().getInt("invincible.max_reiki");
    }

    public static int getReiki(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if(!nbt.contains("invincible.reiki")){
            int reiki = getMaxReiki(stack);
            nbt.putInt("invincible.reiki", reiki);
            
            return reiki;
        }
        
        return nbt.getInt("invincible.reiki");
    }

    public static boolean isConsumeReiki(ItemStack stack){
        if(getMaxReiki(stack) == 0 && getReiki(stack) == 0){
            return false;
        }
        return true;
    }
    
    public static boolean consume(int consume, ItemStack stack){
        if(!stack.hasNbt()){
            return false;
        }

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
}
