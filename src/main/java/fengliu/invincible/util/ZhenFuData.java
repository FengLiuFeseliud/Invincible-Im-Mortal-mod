package fengliu.invincible.util;

import java.util.List;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.util.CheckStructure.Structure;
import net.minecraft.block.BlockState;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
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
     * 阵法设置
     */
    public static abstract class ZhenFuSettings {
        private final Structure Structure;
        public TranslatableText Name;
        public int Highly = 0;
        public int NeedJadeNumber = 0;

        public ZhenFuSettings(Structure structure){
            Structure = structure;
            Highly = structure.y;
        }

        public ZhenFuSettings setName(TranslatableText name){
            Name = name;
            return this;
        }

        public ZhenFuSettings setHighly(int highly){
            Highly = highly;
            return this;
        }

        public ZhenFuSettings setNeedJadeNumber(int needJadeNumber){
            NeedJadeNumber = needJadeNumber;
            return this;
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
         * 执行阵法效果 skill
         * @param world 世界
         * @param pos 阵眼方块坐标 (中心点方块坐标)
         * @param state 阵眼方块状态
         * @param be 阵眼方块实体
         * @return 玩家超过 0 返回 ture
         */
        public boolean onPlayerUes(World world, BlockPos pos, BlockState state, Ui_Block.Entity be){
            List<PlayerEntity> players = getInAllPlayer(world, pos);
            for(PlayerEntity player: players){
                skill(world, pos, state, be, player);
            }
            
            if(players.size() == 0){
                return false;
            }else{
                return true;
            }
        }

        public abstract boolean skill(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player);
    }

    public interface ZhenFus {
        public ZhenFuSettings[] getZhenFuSettings();
    }
}
