package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.AngleGrinderEntity;
import fengliu.invincible.entity.block.ModBlockEntitys;
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

public class AngleGrinder extends Ui_Block.Model_Block{

    public AngleGrinder(Settings settings) {
        super(settings);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntitys.ANGLE_GRINDER_ENTITY, AngleGrinderEntity::tick);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AngleGrinderEntity(pos, state);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player){
        if(entity instanceof AngleGrinderEntity){
            player.openHandledScreen((AngleGrinderEntity) entity);
        }
    }

    @Override
    public void updateComparators(BlockEntity entity, BlockPos pos, World world){
        if(entity instanceof AngleGrinderEntity){
            ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
            world.updateComparators(pos, this);
        }
    }

    // 这东西元素一多巨卡
    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0.8125, 1, 0.3125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.1875, 0.3125, 0.1875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0.8125, 0.1875, 0.3125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0, 1, 0.3125, 0.1875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.3125, 0, 1, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.4375, 0, 1, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.4375, 0, 0.125, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.9375, 0, 0.875, 1, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.9375, 0.875, 1, 1));

        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }
    
}