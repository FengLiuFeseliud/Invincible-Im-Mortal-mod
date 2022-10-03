package fengliu.invincible.screen.handler;

import fengliu.invincible.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class Angle_Grinder_ScreenHandler extends ScreenHandler {

    public Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public Angle_Grinder_ScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(1));
    }

    public Angle_Grinder_ScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.ANGLE_GRINDER_SCREENHANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 2);
        checkDataCount(propertyDelegate, 1);

        this.addSlot(new Slot(this.inventory, 0, 81, 10){
            @Override
            public boolean canInsert(ItemStack stack){
                return stack.isOf(ModItems.JADE_ROUGH_STONE);
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 81, 60){
            @Override
            public boolean canInsert(ItemStack stack){
                return false;
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

    public int getTickCount(){
        return this.propertyDelegate.get(0);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
