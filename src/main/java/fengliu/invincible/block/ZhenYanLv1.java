package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.ModBlockEntitys;
import fengliu.invincible.entity.block.ZhenYanLv1Entity;
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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ZhenYanLv1 extends Ui_Block.Model_Block{
    public ZhenYanLv1(Settings settings) {
        super(settings);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntitys.ZHEN_YAN_1_ENTITY,  ZhenYanLv1Entity::tick);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ZhenYanLv1Entity(pos, state);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player){
        if(entity instanceof ZhenYanLv1Entity){
            player.openHandledScreen((ZhenYanLv1Entity) entity);
        }
    }

    @Override
    public void updateComparators(BlockEntity entity, BlockPos pos, World world){
        if(entity instanceof ZhenYanLv1Entity){
            ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
            world.updateComparators(pos, this);
        }
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.16874999999999996, 0.1875, 0.19375, 0.8125, 0.25, 0.81875));
    
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }
}
