package fengliu.invincible.screen.handler;

import fengliu.invincible.invincibleMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class Zhen_Yan_ScreenHandler {

    public static class Lv1 extends ScreenHandler {

        public Inventory inventory;

        public Lv1(int syncId, PlayerInventory playerInventory) {
            this(syncId, playerInventory, new SimpleInventory(6));
        }

        public Lv1(int syncId, PlayerInventory playerInventory, SimpleInventory inventory) {
            super(invincibleMod.ZHEN_YAN_1_SCREENHANDLER, syncId);
            this.inventory = inventory;
            checkSize(inventory, 6);

            this.addSlot(new Slot(this.inventory, 0, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            this.addSlot(new Slot(this.inventory, 1, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            this.addSlot(new Slot(this.inventory, 2, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            this.addSlot(new Slot(this.inventory, 3, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            this.addSlot(new Slot(this.inventory, 4, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            this.addSlot(new Slot(this.inventory, 5, 0, 0){
                @Override
                public boolean canInsert(ItemStack stack){
                    return true;
                }
            });

            for(int row_index = 0; row_index < 3; ++row_index){
                for(int line_index = 0; line_index < 9; ++line_index){
                    this.addSlot(new Slot(playerInventory, row_index + line_index * 9 + 9, 8 + line_index * 18, 84 + row_index * 18));
                }
            }
            for(int line_index = 0; line_index < 9; ++line_index){
                this.addSlot(new Slot(playerInventory, line_index, 8 + line_index * 18, 142));
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
}
