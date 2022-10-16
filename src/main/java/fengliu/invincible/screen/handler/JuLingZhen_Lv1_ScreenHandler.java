package fengliu.invincible.screen.handler;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class JuLingZhen_Lv1_ScreenHandler extends BaseScreenHandler{
    public Inventory inventory;

    public JuLingZhen_Lv1_ScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6));
    }

    public JuLingZhen_Lv1_ScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.JU_LING_ZHEN_LV1_SCREENHANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 6);

        this.addSlot(new Slot(this.inventory, 0, 80, 17){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 38, 40){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.addSlot(new Slot(this.inventory, 2, 59, 40){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.addSlot(new Slot(this.inventory, 3, 80, 40){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.addSlot(new Slot(this.inventory, 4, 101, 40){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.addSlot(new Slot(this.inventory, 5, 122, 40){
            @Override
            public boolean canInsert(ItemStack stack){
                return true;
            }
        });

        this.playerSlot(playerInventory);
    }
}
