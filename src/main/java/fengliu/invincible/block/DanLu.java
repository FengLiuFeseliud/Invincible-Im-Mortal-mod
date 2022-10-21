package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.entity.DanLuEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DanLu extends Ui_Block.Model_Block {

    public DanLu(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DanLuEntity(pos, state);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player){
        if(entity instanceof DanLuEntity){
            player.openHandledScreen((DanLuEntity) entity);
        }
    }

    @Override
    public void updateComparators(BlockEntity entity, BlockPos pos, World world){
        if(entity instanceof DanLuEntity){
            ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
            world.updateComparators(pos, this);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.2, 0, 0.2, 0.8, 0.5, 0.8));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.32, 0.5, 0.32, 0.68, 0.7, 0.68));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.38, 0.7, 0.38, 0.63, 0.95, 0.63));
        return shape;
    }
    
}