package fengliu.invincible.screen.handler;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class NotZhenFu_ScreenHandler extends BaseScreenHandler{
    public Inventory inventory;

    public NotZhenFu_ScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(0));
    }

    public NotZhenFu_ScreenHandler(int syncId, PlayerInventory playerInventory, SimpleInventory inventory) {
        super(ModScreenHandlers.NOT_ZHEN_FU_SCREENHANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 0);

        this.playerSlot(playerInventory);
    }
}
