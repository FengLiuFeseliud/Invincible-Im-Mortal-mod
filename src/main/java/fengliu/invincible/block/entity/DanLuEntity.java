package fengliu.invincible.block.entity;

import javax.annotation.Nullable;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.DanLu;
import fengliu.invincible.screen.handler.DanLuScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DanLuEntity extends Ui_Block.Entity {
    
    protected PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public void set(int index, int value) {
            
        }

        @Override
        public int size() {
            return 0;
        }
        
    };

    public DanLuEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitys.DAN_LU_ENTITY, blockPos, blockState);

        this.setMaxItemStack(10);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        // 漏斗只能从背后输入至第一个格子
        if(side == Direction.UP || side == Direction.DOWN || slot == 1) {
            return false;
        }

        return switch (this.getWorld().getBlockState(this.pos).get(DanLu.FACING)){
            case EAST ->
                side == Direction.WEST;
            case WEST ->
                side == Direction.EAST;
            case NORTH ->
                side == Direction.SOUTH;
            case SOUTH ->
                side == Direction.NORTH;
            default ->
                false;
            
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        // 漏斗只能从产出格并且在下面才能输出
        if(side == Direction.DOWN && slot == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("block.invincible.dan_lu.name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DanLuScreenHandler(syncId, inventory, this, propertyDelegate);
    }
}
