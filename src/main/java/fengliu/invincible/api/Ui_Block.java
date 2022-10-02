package fengliu.invincible.api;

import fengliu.invincible.invincibleMod;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
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

        protected Class<? extends LootableContainerBlockEntity> BLOCK_ENTITY;

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

            BlockEntity entity = world.getBlockEntity(pos);
            openHandledScreen(entity, player);
            return super.onUse(state, world, pos, player, hand, hit);
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
    }

    public static abstract class Model_Block extends Ui_Block.Block {

        public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

        public Model_Block(Settings settings) {
            super(settings);
        }

        @Override
        public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

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
    
    public static abstract class Entity extends LootableContainerBlockEntity {
        
        public DefaultedList<ItemStack> inv;

        public Entity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
            super(blockEntityType, blockPos, blockState);
        }

        protected void setMaxItemStack(int maxItemStack){
            inv = DefaultedList.ofSize(maxItemStack, ItemStack.EMPTY);
        }

        @Override
        protected abstract Text getContainerName();
        @Override
        protected abstract ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory);;

        @Override
        protected DefaultedList<ItemStack> getInvStackList() {
            return this.inv;
        }
    
        @Override
        protected void setInvStackList(DefaultedList<ItemStack> list) {
            this.inv = list;
        }
        
        @Override
        public int size() {
            return this.inv.size();
        }
    
        @Override
        protected void writeNbt(NbtCompound nbt) {
            super.writeNbt(nbt);
            Inventories.writeNbt(nbt, inv);
        }
    
        @Override
        public void readNbt(NbtCompound nbt) {
            super.readNbt(nbt);
            Inventories.readNbt(nbt, inv);
        }
    }

}
