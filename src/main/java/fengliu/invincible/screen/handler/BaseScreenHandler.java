package fengliu.invincible.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public abstract class BaseScreenHandler extends ScreenHandler{
    public Inventory inventory;

    protected BaseScreenHandler(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    /**
     * 绘制物品栏与背包
     */
    public void playerSlot(PlayerInventory playerInventory){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if(!(slot != null && slot.hasStack())){
            return itemStack;
        }

        ItemStack itemStack2 = slot.getStack().copy();
        ItemStack itemStack3 = this.inventory.getStack(0);
        ItemStack itemStack4 = this.inventory.getStack(1);
        if(index == 2){
            if(!this.insertItem(itemStack2, 3, 39, true)){
                return ItemStack.EMPTY;
            }
            slot.onQuickTransfer(itemStack2, itemStack);
        }else if(index == 0 || index == 1 ? !this.insertItem(itemStack2, 3, 39, false) : (itemStack3.isEmpty() || itemStack4.isEmpty() ? !this.insertItem(itemStack3, 3, 39, false) : !this.insertItem(itemStack4, 3, 39, false))){
            return ItemStack.EMPTY;
        }

        if(itemStack2.isEmpty()){
            slot.setStack(ItemStack.EMPTY);
        }else{
            slot.markDirty();
        }

        if(itemStack2.getCount() == itemStack.getCount()){
            return ItemStack.EMPTY;
        }
        slot.onTakeItem(player, itemStack2);

        return super.transferSlot(player, index);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
