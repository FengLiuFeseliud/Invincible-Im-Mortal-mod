package fengliu.invincible.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.Angle_Grinder_Entity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Angle_Grinder extends Ui_Block.Model_Block{

    public Angle_Grinder(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Angle_Grinder_Entity(pos, state);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player){
        if(entity instanceof Angle_Grinder_Entity){
            player.openHandledScreen((Angle_Grinder_Entity) entity);
        }
    }

    @Override
    public void updateComparators(BlockEntity entity, BlockPos pos, World world){
        if(entity instanceof Angle_Grinder_Entity){
            ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
            world.updateComparators(pos, this);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    }
    
}