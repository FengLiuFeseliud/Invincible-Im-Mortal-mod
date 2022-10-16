package fengliu.invincible.screen.handler;

import fengliu.invincible.item.ModItems;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class Angle_Grinder_ScreenHandler extends BaseScreenHandler {

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

        this.addSlot(new Slot(this.inventory, 0, 80, 10){
            @Override
            public boolean canInsert(ItemStack stack){
                return stack.isOf(ModItems.JADE_ROUGH_STONE);
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 80, 60){
            @Override
            public boolean canInsert(ItemStack stack){
                return false;
            }
        });

        this.playerSlot(playerInventory);
    }

    public int getTickCount(){
        return this.propertyDelegate.get(0);
    }
}
