package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.util.ReikiItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(ItemStack.class)
public class ItemStackMixin{
    
    /**
     * 重置物品格灵气 nbt
     */
    @Inject(method = "split", at = @At("RETURN"))
    public ItemStack split(int amount, CallbackInfoReturnable<ItemStack> info) {
        ItemStack itemStack = info.getReturnValue();
        NbtCompound nbt = itemStack.getOrCreateNbt();
        if(nbt.contains("invincible.reiki")){
            nbt.putInt("invincible.reiki", ReikiItemData.getInitialReiki(itemStack));
        }
        return itemStack;
    }
}
