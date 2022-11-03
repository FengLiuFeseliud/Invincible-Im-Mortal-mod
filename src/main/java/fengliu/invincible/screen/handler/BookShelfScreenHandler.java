package fengliu.invincible.screen.handler;

import fengliu.invincible.item.KungFuItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BookShelfScreenHandler extends BaseScreenHandler {

    public BookShelfScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(14));
    }

    public BookShelfScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.BOOK_SHELF_SCREENHANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 14);

        for(int row = 0; row < 2; row++){
            for(int index = 0; index < 7; index++){
                this.addSlot(new Slot(this.inventory, index + 7 * row, 26 + 18 * index, 25 + 18 * row){
                    @Override
                    public boolean canInsert(ItemStack stack){
                        return stack.getItem() instanceof KungFuItem;
                    }
                });
            }
        }
        
        this.playerSlot(playerInventory);
    }
    
}
