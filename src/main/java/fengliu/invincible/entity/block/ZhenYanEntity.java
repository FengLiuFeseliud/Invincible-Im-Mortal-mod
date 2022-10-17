package fengliu.invincible.entity.block;

import java.util.ArrayList;
import java.util.List;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.NotZhenFuScreenHandler;
import fengliu.invincible.util.ReikiItemData;
import fengliu.invincible.util.ZhenFuData;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ZhenYanEntity extends Ui_Block.Entity{
    public ZhenFuSettings settings;
    public List<PlayerEntity> oldOnPlayerUes = new ArrayList<>();
    public int item_max_reiki = 0;
    public int item_reiki = 0;

    protected PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            if(index == 0){
                return item_reiki;
            }

            if(index == 1){
                return item_max_reiki;
            }

            return 0;
        }

        @Override
        public void set(int index, int value) {
            if(index == 0){
                item_reiki = value;
            }

            if(index == 1){
                item_max_reiki = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
        
    };

    public ZhenYanEntity(BlockEntityType<?> be, BlockPos pos, BlockState state) {
        super(be, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ZhenYanEntity be) {
        if(world.isClient){
            return;
        }

        ZhenFuSettings settings = be.settings;
        if(settings == null){
            be.settings = ZhenFuData.checkAllZhenFu(world, pos, be.getZhenFus());
            return;
        }

        if(!settings.checkZhenFu(world, pos)){
            be.settings = null;
            return;
        }

        ItemStack stack1 = be.getStack(0);
        be.item_max_reiki = ReikiItemData.getMaxReiki(stack1);
        be.item_reiki = ReikiItemData.getReiki(stack1);
        if(stack1.isEmpty()){
            if(be.oldOnPlayerUes.size() != 0){
                ZhenFuData.onZhenFuEnd(settings, world, pos, state, be);
            }
            settings.unusable(world, pos, state, be);
            return;
        }
        ReikiItemData.absord(be.settings.Consume, stack1);
        settings.usable(world, pos, state, be);

        List<PlayerEntity> newOnPlayerUes = ZhenFuData.onPlayerUes(settings, world, pos, state, be);
        ZhenFuData.onPlayerEndUes(settings, newOnPlayerUes, world, pos, state, be);
        return;
    }

    public abstract ZhenFus getZhenFus();
    public abstract TranslatableText getNotZhenFuDisplayName();

    @Override
    public Text getDisplayName() {
        if(settings == null){
            return getNotZhenFuDisplayName();
        }
        return settings.Name;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        if(settings == null){
            return new NotZhenFuScreenHandler(syncId, inventory, this);
        }
        return settings.getScreenHandler(syncId, inventory, this, propertyDelegate);
    }
}
