package fengliu.invincible.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class CheckStructure {
    
    /**
     * 获取指定中心点范围方块
     * 
     * 例 x 为 2, y 为 5, z 为 2
     * 则范围为 x5 * y5 * z5 = 125 格
     * 范围第一层中心点则为指定中心点
     * @param world 世界
     * @param pos 中心点方块坐标
     * @param x 从中心点开始 x 方向半径
     * @param y 范围层数
     * @param z 从中心点开始 z 方向半径
     * @return x为方块列表为一排 z为多个x列表的列表为一层 返回列表为多个z列表的列表为整个范围
     */
    public static List<List<List<BlockState>>> getScopeAllBlock(World world, BlockPos pos, int x, int y, int z){
        List<List<List<BlockState>>> allBlocks = new ArrayList<>();
        for(int yindex = y - 1; 0 <= yindex; --yindex){
            List<List<BlockState>> zBlocks = new ArrayList<>();

            for(int zindex = z * 2 + 1; 0 < zindex; --zindex){
                List<BlockState> xBlocks = new ArrayList<>();

                for(int xindex = x * 2 + 1; 0 < xindex; --xindex){
                    Vec3i block_pos = new Vec3i(
                        pos.getX() - (x - xindex + 1), 
                        pos.getY() + yindex, 
                        pos.getZ() - (z - zindex + 1)
                    );
                    xBlocks.add(world.getBlockState(new BlockPos(block_pos)));
                }
                zBlocks.add(xBlocks);
            }
            allBlocks.add(zBlocks);
        }
        return allBlocks;
    }

    /**
     * 获取指定中心点的指定范围区块对角两区块
     * @param world 世界
     * @param pos 中心点方块坐标
     * @param x 从中心点区块开始 x 方向半径
     * @param z 从中心点区块开始 z 方向半径
     * @return 指定范围区块对角两区块
     */
    public static WorldChunk[] getAllChunkScope(World world, BlockPos pos, int x, int z){
        ChunkPos chunkPos = world.getChunk(pos).getPos();
        WorldChunk[] chunks = {
            world.getChunk(chunkPos.x - x, chunkPos.z - z),
            world.getChunk(chunkPos.x + x, chunkPos.z + z)
        };
        return chunks;
    }

    /**
     * 自定义结构
     */
    public static class Structure {
        /**
         * 使用 Material 方块下标志方块
         * " " 空格代表 空气 (air)
         * "*" 星号代表可以跳过检查
         */
        public final String[][] StructureList;
        /**
         * 方块材料
         */
        public final Block[] Material;
        public final int x;
        public final int y;
        public final int z;
        public int chunkScopeX = 0;
        public int chunkScopeZ = 0;

        /**
         * 自定义结构
         * @param structure 结构 层 -> 排
         * @param material 材料
         * @param x 从中心点开始 x 方向半径
         * @param y 范围层数
         * @param z 从中心点开始 z 方向半径
         */
        public Structure(String[][] structure, Block[] material, int x, int y, int z){
            StructureList = structure;
            Material = material;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Structure setChunkScope(int chunkScopeX, int chunkScopeZ){
            this.chunkScopeX = chunkScopeX;
            this.chunkScopeZ = chunkScopeZ;
            return this;
        }

        /**
         * 将多于层拆分
         * @param scopeAllBlock 范围结构
         * @return 于结构层数相同的范围
         */
        private List<List<List<BlockState>>> splitScope(List<List<List<BlockState>>> scopeAllBlock){
            List<List<List<BlockState>>> scope = new ArrayList<>();
            for(int layer = 0; layer < scopeAllBlock.size(); layer++){
                if(layer < scopeAllBlock.size() - StructureList.length){
                    continue;
                }
                scope.add(scopeAllBlock.get(layer));
            }

            return scope;
        }

        /**
         * 检查结构上方范围是为空
         * @param scopeAllBlock 范围结构
         * @return 空为 true
         */
        public boolean isAllAir(List<List<List<BlockState>>> scopeAllBlock){
            for(int layer = 0; layer < scopeAllBlock.size(); layer++){
                if(layer == scopeAllBlock.size() - StructureList.length){
                    return true;
                }
                
                for(List<BlockState> check_row: scopeAllBlock.get(layer)){
                    for(BlockState block: check_row){
                        if(block.isAir()){
                            continue;
                        }

                        return false;
                    }
                }
            }

            return true;
        }

        /**
         * 检查结构是否一致
         * @param world 世界
         * @param pos 中心点方块坐标
         * @return 一致为 true
         */
        public boolean checkStructure(World world, BlockPos pos){
            List<List<List<BlockState>>> scopeAllBlock = getScopeAllBlock(world, pos, x, y, z);
            if(!isAllAir(scopeAllBlock)){
                return false;
            }

            List<List<List<BlockState>>> scopeBlock = splitScope(scopeAllBlock);
            for(int check_layer = 0; check_layer < StructureList.length; check_layer++){
                for(int check_row = 0; check_row < StructureList[check_layer].length; check_row++){
                    String[] check_block_list = StructureList[check_layer][check_row].split("");

                    for(int check_block = 0; check_block < check_block_list.length; check_block++){
                        BlockState block = scopeBlock.get(check_layer).get(check_row).get(check_block);
                        String blockIndex = check_block_list[check_block];
                        
                        if(!block.isAir() && blockIndex.equals(" ")){
                            return false;
                        }

                        if(blockIndex.equals("*") || blockIndex.equals(" ")){
                            continue;
                        }

                        if(!block.isOf(Material[Integer.parseInt(blockIndex)])){
                            return false;
                        }
                    }
                }
            }

            return true;
        }

        /**
         * 获取结构盒子
         * @param pos 中心点方块坐标
         * @return 结构盒子
         */
        public Box getStructureBox(BlockPos pos){
            return new Box(
                pos.getX() - x, pos.getY(), pos.getZ() + z + 1, 
                pos.getX() + x, pos.getY() + y, pos.getZ() - z + 1
            );
        }

        /**
         * 获取范围区块盒子
         * @param world 世界
         * @param pos 中心点方块坐标
         * @param highly 盒子高度
         * @return 结构盒子
         */
        public Box getChunkScopeBox(World world, BlockPos pos, int highly){
            WorldChunk[] chunks = getAllChunkScope(world, pos, chunkScopeX, chunkScopeZ);

            return new Box(
                chunks[0].getPos().getStartX(), pos.getY(), chunks[0].getPos().getStartZ(), 
                chunks[1].getPos().getEndX() + 1, pos.getY() + highly, chunks[1].getPos().getEndZ() + 1
            );
        }

        /**
         * 获取范围区块中的玩家
         * @param world 世界
         * @param pos 中心点方块坐标
         * @param highly 盒子高度
         * @return 玩家列表
         */
        public List<PlayerEntity> getChunkScopeAllPlayer(World world, BlockPos pos, int highly){
            return world.getEntitiesByClass(PlayerEntity.class, getChunkScopeBox(world, pos, highly), EntityPredicates.VALID_ENTITY);
        }
    }
}
