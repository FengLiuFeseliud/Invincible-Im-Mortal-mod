package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.ModBlockEntitys;
import fengliu.invincible.entity.block.Zhen_Yan_Entity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Zhen_Yan{

    public static class Lv1 extends Ui_Block.Model_Block {

        public Lv1(Settings settings) {
            super(settings);
        }

        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                BlockEntityType<T> type) {
            return checkType(type, ModBlockEntitys.ZHEN_YAN_1_ENTITY, 
                (world1, pos, state1, be) -> Zhen_Yan_Entity.Lv1.tick(world1, pos, state1, be)
            );
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new Zhen_Yan_Entity.Lv1(pos, state);
        }

        @Override
        public void openHandledScreen(BlockEntity entity, PlayerEntity player){
            if(entity instanceof Zhen_Yan_Entity.Lv1){
                player.openHandledScreen((Zhen_Yan_Entity.Lv1) entity);
            }
        }

        @Override
        public void updateComparators(BlockEntity entity, BlockPos pos, World world){
            if(entity instanceof Zhen_Yan_Entity.Lv1){
                ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
                world.updateComparators(pos, this);
            }
        }

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return Block.createCuboidShape(0, 0, 0, 16, 5, 16);
        }

    }
    
}
