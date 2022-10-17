package fengliu.invincible.api;

import fengliu.invincible.entity.block.ImplementedInventory;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Ui_Block {

    public static abstract class Block extends BlockWithEntity {

        public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

        public Block(Settings settings) {
            super(settings);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return null;
        }

        public void openHandledScreen(BlockEntity entity, PlayerEntity player){
            if(entity.getClass().isAssignableFrom(null)){
                player.openHandledScreen((NamedScreenHandlerFactory) entity);
            }
        }

        public void updateComparators(BlockEntity entity, BlockPos pos, World world){
            if(entity.getClass().isAssignableFrom(null)){
                ItemScatterer.spawn(world, pos, (Inventory)((Object) entity));
                world.updateComparators(pos, this);
            }
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

            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
            return ActionResult.SUCCESS;
        }

        @Override
        public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
            if(state.isOf(newState.getBlock())){
                return;
            }

            BlockEntity entity = world.getBlockEntity(pos);
            updateComparators(entity, pos, world);
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
        protected void appendProperties(Builder<net.minecraft.block.Block, BlockState> builder) {
            builder.add(FACING);
        }

    }

    public static abstract class Model_Block extends Ui_Block.Block {

        public Model_Block(Settings settings) {
            super(settings);
        }

        @Override
        public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);
    }
    
    public static abstract class Entity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{
        
        public DefaultedList<ItemStack> inventory;

        public Entity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
            super(blockEntityType, blockPos, blockState);
        }

        protected void setMaxItemStack(int maxItemStack){
            inventory = DefaultedList.ofSize(maxItemStack, ItemStack.EMPTY);
        }

        @Override
        public abstract Text getDisplayName();
        @Override
        public abstract ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player);
    
        @Override
        public DefaultedList<ItemStack> getItems() {
            return this.inventory;
        }

        @Override
        protected void writeNbt(NbtCompound nbt) {
            super.writeNbt(nbt);
            Inventories.writeNbt(nbt, inventory);
        }
    
        @Override
        public void readNbt(NbtCompound nbt) {
            super.readNbt(nbt);
            Inventories.readNbt(nbt, inventory);
        }
    }

}
