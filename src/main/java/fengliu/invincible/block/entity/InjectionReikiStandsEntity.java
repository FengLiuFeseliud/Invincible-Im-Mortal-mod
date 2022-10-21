package fengliu.invincible.block.entity;

import org.jetbrains.annotations.Nullable;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.InjectionReikiStandsScreenHandler;
import fengliu.invincible.util.ReikiItemData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.Direction;

public class InjectionReikiStandsEntity extends Ui_Block.Entity {
    public float gain = 1.0f;
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

    public InjectionReikiStandsEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitys.INJECTION_REIKI_STANDS_ENTITY, blockPos, blockState);
        
        this.setMaxItemStack(2);
    }

    public static void tick(World world, BlockPos pos, BlockState state, InjectionReikiStandsEntity be){
        if(world.isClient){
            return;
        }
        
        ItemStack fromStack = be.getStack(0);
        be.item_reiki = ReikiItemData.getReiki(fromStack) - ReikiItemData.getInitialReiki(fromStack);
        be.item_max_reiki = ReikiItemData.getTargetNeedReiki(fromStack);

        ReikiItemData.injectionToNewItemStack(world, Math.round(10 * be.gain), fromStack, 1, be);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        // 漏斗除了上下面和产出格都可以输入至第一个格子
        if(side == Direction.UP || side == Direction.DOWN || slot == 1) {
            return false;
        }
        return true;
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
        return new TranslatableText("block.invincible.injection_reiki_stands.name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new InjectionReikiStandsScreenHandler(syncId, inv, this, propertyDelegate);
    }
    
}
