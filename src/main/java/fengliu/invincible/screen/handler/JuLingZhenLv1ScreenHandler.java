package fengliu.invincible.screen.handler;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class JuLingZhenLv1ScreenHandler extends BaseScreenHandler{

    public Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public JuLingZhenLv1ScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(2));
    }

    public JuLingZhenLv1ScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.JU_LING_ZHEN_LV1_SCREENHANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 1);
        checkDataCount(propertyDelegate, 2);

        this.addSlot(new Slot(this.inventory, 0, 79, 35){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.playerSlot(playerInventory);
    }

    public int getItemReiki(){
        return this.propertyDelegate.get(0);
    }

    public int getItemMaxReiki(){
        return this.propertyDelegate.get(1);
    }
}
