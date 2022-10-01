package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.Zhen_Yan_Entity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Zhen_Yan{

    public static class Lv1 extends Ui_Block.Block {

        public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
        protected static VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 5, 16);

        public Lv1(Settings settings) {
            super(settings);
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
        public BlockState getPlacementState(ItemPlacementContext ctx) {
            return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
        }

        @Override
        public BlockState rotate(BlockState state, BlockRotation rotation) {
            return state.with(FACING, rotation.rotate(state.get(FACING)));
        }

        @Override
        public BlockState mirror(BlockState state, BlockMirror mirror) {
            return state.rotate(mirror.getRotation(state.get(FACING)));
        }

        @Override
        protected void appendProperties(Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }
        
        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return SHAPE;
        }
    }
    
}
