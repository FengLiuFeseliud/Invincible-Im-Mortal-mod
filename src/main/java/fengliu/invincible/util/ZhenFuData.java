package fengliu.invincible.util;

import java.util.List;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.entity.block.ZhenYanEntity;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.screen.handler.NotZhenFuScreenHandler;
import fengliu.invincible.util.CheckStructure.Structure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZhenFuData {
    /**
     * 从指定阵法级别检查阵法是否正确
     * @param world 世界
     * @param pos 阵眼方块坐标 (中心点方块坐标)
     * @param zhenFus 阵法级别
     * @return 有效的阵法, 没有为 null
     */
    public static ZhenFuSettings checkAllZhenFu(World world, BlockPos pos, ZhenFus zhenFus){
        for(ZhenFuSettings settings: zhenFus.getZhenFuSettings()){
            if(settings.Structure.checkStructure(world, pos)){
                return settings;
            }
        }
        return null;
    }

    /**
     * 执行阵法效果 skill
     * @param settings 阵法设置
     * @param world 世界
     * @param pos 阵眼方块坐标 (中心点方块坐标)
     * @param state 阵眼方块状态
     * @param be 阵眼方块实体
     * @return 在阵法中的玩家列表
     */
    public static List<PlayerEntity> onPlayerUes(ZhenFuSettings settings, World world, BlockPos pos, BlockState state, Ui_Block.Entity be){
        List<PlayerEntity> players = settings.getInAllPlayer(world, pos);
        for(PlayerEntity player: players){
            settings.effect(world, pos, state, be, player);
        }
        return players;
    }

    public static void onZhenFuEnd(ZhenFuSettings settings, World world, BlockPos pos, BlockState state, ZhenYanEntity be) {
        for(PlayerEntity player: settings.getInAllPlayer(world, pos)){
            settings.endEffect(world, pos, state, be, player);
        }
        be.oldOnPlayerUes.clear();
    }

    /**
     * 检查玩家是否从阵法中出去并执行阵法离开效果 endSkill
     * @param settings 阵法设置
     * @param oldOnPlayerUes 上一 tick 在阵法中的玩家列表
     * @param newOnPlayerUes 当前 tick 在阵法中的玩家列表
     * @param world 世界
     * @param pos 阵眼方块坐标 (中心点方块坐标)
     * @param state 阵眼方块状态
     * @param be 阵眼方块实体
     */
    public static void onPlayerEndUes(ZhenFuSettings settings, List<PlayerEntity> newOnPlayerUes, World world, BlockPos pos, BlockState state, ZhenYanEntity be) {
        for(PlayerEntity player: be.oldOnPlayerUes){
            if(newOnPlayerUes.contains(player)){
                continue;
            }
            settings.endEffect(world, pos, state, be, player);
        }
        be.oldOnPlayerUes = newOnPlayerUes;
    }

    /**
     * 阵法设置
     */
    public static abstract class ZhenFuSettings {
        private final Structure Structure;
        public Class<? extends ScreenHandler> screenHandler;
        public TranslatableText Name;
        public int Highly = 0;
        public int Consume = 10;
        public int NeedJadeNumber = 0;

        public ZhenFuSettings(Structure structure){
            Structure = structure;
            Highly = structure.y;
        }

        public ZhenFuSettings setScreenHandler(Class<? extends ScreenHandler> screenHandler){
            this.screenHandler = screenHandler;
            return this;
        }

        public ZhenFuSettings setName(TranslatableText name){
            Name = name;
            return this;
        }

        public ZhenFuSettings setHighly(int highly){
            Highly = highly;
            return this;
        }

        public ZhenFuSettings setConsume(int consume){
            Consume = consume;
            return this;
        }

        public ZhenFuSettings setNeedJadeNumber(int needJadeNumber){
            NeedJadeNumber = needJadeNumber;
            return this;
        }

        /**
         * 动态实例化 ScreenHandler
         */
        public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory){
            try {
                return screenHandler.getConstructor(int.class, PlayerInventory.class, Inventory.class).newInstance(syncId, playerInventory, inventory);
            } catch (Exception e) {
                return new NotZhenFuScreenHandler(syncId, playerInventory, inventory);
            }
        }

        public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate){
            try {
                return screenHandler.getConstructor(int.class, PlayerInventory.class, Inventory.class, PropertyDelegate.class).newInstance(syncId, playerInventory, inventory, propertyDelegate);
            } catch (Exception e) {
                return new NotZhenFuScreenHandler(syncId, playerInventory, inventory);
            }
        }

        public boolean checkZhenFu(World world, BlockPos pos){
            return Structure.checkStructure(world, pos);
        }

        /**
         * 获取在阵法中的玩家
         * @param world 世界
         * @param pos 阵眼方块坐标 (中心点方块坐标)
         * @return 玩家列表
         */
        public List<PlayerEntity> getInAllPlayer(World world, BlockPos pos){
            return Structure.getChunkScopeAllPlayer(world, pos, Highly);
        }

        public List<BlockEntity> getInAllBlockEntity(Block block, World world, BlockPos pos){
            return Structure.getChunkScopeBlockEntity(block, world, pos, Highly);
        }

        /**
         * 获取阵法玉石加成
         * 
         * 玉石少于 NeedJadeNumber 则永久为 0
         * @param world 世界
         * @param pos 阵眼方块坐标 (中心点方块坐标)
         * @return 加成百分比
         */
        public int getAllJadeEffect(World world, BlockPos pos){
            int jadeEffect = 0;
            int jadeAll = 0;
            for(ItemFrameEntity itemFrame: world.getEntitiesByClass(
                ItemFrameEntity.class, Structure.getStructureBox(pos), EntityPredicates.VALID_ENTITY
            )){
                ItemStack stack = itemFrame.getHeldItemStack();
                if(!stack.isOf(ModItems.JADE)){
                    continue;
                }

                NbtCompound nbt = stack.getOrCreateNbt();
                if(!nbt.contains("invincible.jade_effect")){
                    continue;
                }
                jadeAll += 1;
                jadeEffect += nbt.getInt("invincible.jade_effect");
            }

            if(jadeAll != NeedJadeNumber){
                return 0;
            }

            return jadeEffect / jadeAll;
        }

        /**
         * 将在每 tick 玩家存在于阵法中执行
         */
        public abstract void effect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player);
        /**
         * 将在玩家从阵法中出去时执行
         */
        public abstract void endEffect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player);
        /**
         * 将在每 tick 阵法可用时执行
         */
        public abstract void usable(World world, BlockPos pos, BlockState state, Ui_Block.Entity be);
        /**
         * 将在阵法不可用时执行
         */
        public abstract void unusable(World world, BlockPos pos, BlockState state, Ui_Block.Entity be);
    }

    public interface ZhenFus {
        public ZhenFuSettings[] getZhenFuSettings();
    }
}
