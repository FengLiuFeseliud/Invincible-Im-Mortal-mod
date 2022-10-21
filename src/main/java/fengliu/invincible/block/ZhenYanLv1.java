package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.entity.ModBlockEntitys;
import fengliu.invincible.block.entity.ZhenYanEntity;
import fengliu.invincible.block.entity.ZhenYanLv1Entity;
import fengliu.invincible.util.ZhenFuData;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;
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
import net.minecraft.world.explosion.Explosion;

public class ZhenYanLv1 extends Ui_Block.Model_Block{
    public ZhenYanLv1(Settings settings) {
        super(settings);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntitys.ZHEN_YAN_1_ENTITY,  ZhenYanLv1Entity::tick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(world.isClient){
            super.onBreak(world, pos, state, player);
            return;
        }

        ZhenYanEntity entity = (ZhenYanEntity) world.getBlockEntity(pos);
        ZhenFuSettings settings = entity.settings;
        if(settings == null){
            super.onBreak(world, pos, state, player);
            return;
        }
        // 阵眼被破坏
        ZhenFuData.onZhenFuEnd(entity.settings, world, pos, state, entity);
        entity.settings.onBreak(world, pos, state, entity);
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if(world.isClient){
            super.onDestroyedByExplosion(world, pos, explosion);
            return;
        }
        // ZhenYanEntity entity = (ZhenYanEntity) world.getBlockEntity(pos);
        // ZhenFuSettings settings = entity.settings;
        // if(settings == null){
        //     super.onDestroyedByExplosion(world, pos, explosion);
        //     return;
        // }

        // BlockState state = world.getBlockState(pos);
        // // 阵眼被破坏
        // ZhenFuData.onZhenFuEnd(entity.settings, world, pos, state, entity);
        // entity.settings.onBreak(world, pos, state, entity);
        super.onDestroyedByExplosion(world, pos, explosion);
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
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1));
        
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }
}
