package fengliu.invincible.block.entity;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.BookShelfScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class BookShelfEntity extends Ui_Block.Entity {

    public BookShelfEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitys.BOOK_SHELF_ENTITY, blockPos, blockState);
        
        this.setMaxItemStack(14);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("block.invincible.book_shelf.name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BookShelfScreenHandler(syncId, inv, this);
    }
    
}
