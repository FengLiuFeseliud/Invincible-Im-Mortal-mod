package fengliu.invincible.block.entity;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.DanLu;
import fengliu.invincible.recipe.SmeltRecipe;
import fengliu.invincible.screen.handler.DanLuScreenHandler;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DanLuEntity extends Ui_Block.Entity {
    public int tick_count;
    public int fire_in = 0;
    
    protected PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            if(index == 0){
                return fire_in;
            }

            if(index == 1){
                return tick_count;
            }

            return 0;
        }

        @Override
        public void set(int index, int value) {
            if(index == 0){
                fire_in = value;
            }

            if(index == 1){
                tick_count = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
        
    };

    public DanLuEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitys.DAN_LU_ENTITY, blockPos, blockState);

        this.setMaxItemStack(10);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DanLuEntity be){
        if(world.isClient){
            return;
        }
        
        BlockState downBlockState = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
        if(!(downBlockState.getBlock() instanceof AbstractFireBlock)){
            be.fire_in = 0;
            return;
        }
        be.fire_in = 1;

        SimpleInventory inventory = new SimpleInventory(9);
        for(int index = 0; index < 9; index++){
            inventory.addStack(be.inventory.get(index));
        }
        Optional<SmeltRecipe> match = world.getRecipeManager().getFirstMatch(SmeltRecipe.Type.INSTANCE, inventory, world);
        
        ItemStack outDanLuStack = be.inventory.get(9);
        if(!match.isPresent()){
            be.tick_count = 0;
            return;
        }

        Item outItem = match.get().getOutput().getItem();
        if((!outDanLuStack.isOf(outItem) && !outDanLuStack.isEmpty()) || outDanLuStack.getCount() == outItem.getMaxCount()){
            be.tick_count = 0;
            return;
        }
        
        be.tick_count++;
        if(be.tick_count != 200){
            return;
        }
        be.tick_count = 0;

        List<ItemStack> clearItems = match.get().getInventory().clearToList();
        int clearItemsSize = clearItems.size();
        for(int index = 0; index < 9; index++){
            if(index > clearItemsSize - 1){
                be.inventory.set(index, new ItemStack(Items.AIR));
                continue;
            }
            be.inventory.set(index, clearItems.get(index));
        }
        
        int outItemCount = match.get().getCount();
        if(outDanLuStack.isEmpty()){
            be.inventory.set(9, new ItemStack(outItem, outItemCount));
        }else{
            outDanLuStack.increment(outItemCount);
        }

    };

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        // 漏斗只能从背后输入至第9个格子
        if(side == Direction.UP || side == Direction.DOWN || slot == 9) {
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
        if(side == Direction.DOWN && slot == 9) {
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
