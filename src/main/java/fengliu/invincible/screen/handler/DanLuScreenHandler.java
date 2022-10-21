package fengliu.invincible.screen.handler;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class DanLuScreenHandler extends BaseScreenHandler {
    
    public Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public DanLuScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(10), new ArrayPropertyDelegate(0));
    }

    public DanLuScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.DAN_LU_STANDS_SCREENHANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 10);
        checkDataCount(propertyDelegate, 0);

        this.addSlot(new Slot(inventory, 0, 30, 17){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 1, 48, 17){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 2, 66, 17){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 3, 30, 35){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 4, 48, 35){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 5, 66, 35){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 6, 30, 53){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 7, 48, 53){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 8, 66, 53){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        this.addSlot(new Slot(inventory, 9, 124, 35){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        this.playerSlot(playerInventory);
    }



}
