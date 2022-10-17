package fengliu.invincible.screen.handler;


import fengliu.invincible.util.ReikiItemData;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class InjectionReikiStandsScreenHandler extends BaseScreenHandler {

    public Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public InjectionReikiStandsScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(2));
    }

    public InjectionReikiStandsScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.INJECTION_REIKI_STANDS_SCREENHANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 2);
        checkDataCount(propertyDelegate, 1);

        this.addSlot(new Slot(this.inventory, 0, 80, 31){
            @Override
            public boolean canInsert(ItemStack stack){
                return ReikiItemData.canInjectionReiki(stack);
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 80, 58){
            @Override
            public boolean canInsert(ItemStack stack){
                return false;
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
