package fengliu.invincible.block.entity;

import java.util.Optional;

import javax.annotation.Nullable;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.DanLu;
import fengliu.invincible.item.DanYanItem;
import fengliu.invincible.recipe.SmeltRecipe;
import fengliu.invincible.screen.handler.DanLuScreenHandler;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DanLuEntity extends Ui_Block.Entity implements SmeltingBlockEntity {
    private Optional<SmeltRecipe> match = Optional.empty();
    private PlayerEntity uesPlayer;
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

    @Override
    public Optional<SmeltRecipe> getMatch() {
        return match;
    }

    @Override
    public PlayerEntity getPlayer() {
        return uesPlayer;
    }

    @Override
    public int getPlayerExp(){
        return ((IEntityDataSaver) uesPlayer).getLianDanServerData().getLianDanExp();
    }
    
    @Override
    public void setUesPlayer(PlayerEntity player){
        uesPlayer = player;
    }

    public static void tick(World world, BlockPos pos, BlockState state, DanLuEntity be){
        if(world.isClient){
            return;
        }

        PlayerEntity uesPlayer = be.getUesPlayer(world);
        if(uesPlayer == null){
            return;
        }
        
        BlockState downBlockState = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
        if(!(downBlockState.getBlock() instanceof AbstractFireBlock)){
            be.fire_in = 0;
            return;
        }
        be.fire_in = 1;

        be.match = be.getRecipeMatch(world);
        if(!be.match.isPresent() || !be.canSmelting()){
            be.tick_count = 0;
            return;
        }
        
        be.tick_count++;
        if(be.tick_count != 200){
            return;
        }
        be.tick_count = 0;
        be.consumeRecipeItems();

        Item outSmeltItem = be.getMatchOutSmeltItem();
        if(!(outSmeltItem instanceof DanYanItem)){
            return;
        }
        
        ((IEntityDataSaver) uesPlayer).getLianDanServerData().addLianDanExpInUpLevel((DanYanItem) outSmeltItem);
        be.setOutSmeltItem();

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
