package fengliu.invincible.block;

import fengliu.invincible.entity.block.Zhen_Yan_Entity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Zhen_Yan{

    public static class Lv1 extends BlockWithEntity {

        public Lv1(Settings settings) {
            super(settings);
        }
    
        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new Zhen_Yan_Entity.Lv1(pos, state);
        }

        @Override
        public BlockRenderType getRenderType(BlockState state) {
            return BlockRenderType.MODEL;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if(world.isClient){
                return ActionResult.SUCCESS;
            }
            
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof Zhen_Yan_Entity.Lv1){
                player.openHandledScreen((Zhen_Yan_Entity.Lv1) entity);
            }
            return super.onUse(state, world, pos, player, hand, hit);
        }

        @Override
        public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
            if(state.isOf(newState.getBlock())){
                return;
            }

            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof Zhen_Yan_Entity.Lv1){
                ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

        @Override
        public boolean hasComparatorOutput(BlockState state) {
            return true;
        }

        @Override
        public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
            return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
        }

    }
    
}
