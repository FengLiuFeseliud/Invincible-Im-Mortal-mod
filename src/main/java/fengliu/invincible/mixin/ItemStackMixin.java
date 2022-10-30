package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.item.DanPing;
import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.util.ReikiItemData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

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

    @Inject(method = "onStackClicked", at = @At("HEAD"))
    public void onStackClicked(Slot slot, ClickType clickType, PlayerEntity player, CallbackInfoReturnable<CallbackInfo> info){
        ItemStack cursorStack = player.currentScreenHandler.getCursorStack();
        ItemStack stack = slot.getStack();
        Item stackItem = stack.getItem();
        
        // 丹瓶使用
        if(stackItem instanceof DanYanItem && cursorStack.isOf(ModItems.DAN_PING)){
            DanPing.add(cursorStack, stack);
        }
    }
}
