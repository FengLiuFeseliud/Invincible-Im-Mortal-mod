package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.entity.InjectionReikiStandsEntity;
import fengliu.invincible.block.entity.ModBlockEntitys;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InjectionReikiStands extends Ui_Block.Block{

    public InjectionReikiStands(Settings settings) {
        super(settings);
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntitys.INJECTION_REIKI_STANDS_ENTITY,  InjectionReikiStandsEntity::tick);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InjectionReikiStandsEntity(pos, state);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player){
        if(entity instanceof InjectionReikiStandsEntity){
            player.openHandledScreen((InjectionReikiStandsEntity) entity);
        }
    }

    @Override
    public void updateComparators(BlockEntity entity, BlockPos pos, World world){
        if(entity instanceof InjectionReikiStandsEntity){
            ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
            world.updateComparators(pos, this);
        }
    }
    
}
