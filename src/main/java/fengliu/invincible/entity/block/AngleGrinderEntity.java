package fengliu.invincible.entity.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.screen.handler.AngleGrinderScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AngleGrinderEntity extends Ui_Block.Entity {

    private int tick_count;

    protected PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            return index == 0 ? tick_count : 0;
        }

        @Override
        public void set(int index, int value) {
            if(index == 0){
                tick_count = value;
            }
        }

        @Override
        public int size() {
            return 1;
        }
        
    };

    public AngleGrinderEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitys.ANGLE_GRINDER_ENTITY, blockPos, blockState);

        this.setMaxItemStack(2);
    }

    // 10秒 (200 tick) 生成一个玉石
    public static void tick(World world, BlockPos pos, BlockState state, AngleGrinderEntity be){
        if(world.isClient){
            return;
        }

        ItemStack stack1 = be.getStack(0);
        if(stack1.isEmpty()){
            be.tick_count = 0;
            return;
        }
        
        ItemStack stack2 = be.getStack(1);
        if(stack2.getMaxCount() == stack2.getCount()){
            return;
        }

        be.tick_count++;
        if(be.tick_count != 20 * 10){
            return;
        }

        if(stack2.isEmpty()){
            be.setStack(1, new ItemStack(ModItems.JADE, 1));
        }else{
            stack2.increment(1);
        }
        stack1.decrement(1);
        be.tick_count = 0;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("angle_grinder.tick_count", tick_count);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        tick_count = nbt.getInt("angle_grinder.tick_count");
        super.readNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("block.invincible.angle_grinder.name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new AngleGrinderScreenHandler(syncId, inventory, this, propertyDelegate);
    }
    
}
