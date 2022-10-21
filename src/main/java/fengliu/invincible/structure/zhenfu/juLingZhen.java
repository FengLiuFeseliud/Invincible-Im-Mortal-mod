package fengliu.invincible.structure.zhenfu;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.api.Ui_Block.Entity;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.block.entity.InjectionReikiStandsEntity;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;
import fengliu.invincible.util.CheckStructure.Structure;
import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 一阶聚灵阵
 * 
 * 结构大小 x5 * 5y * z5 = 125, 有效范围一区块 (0 * 0) 有效高度 10y, 玉石需求 4 块
 * 修炼灵气基础加成 10倍
 * 注灵台效率基础加成 2.5倍
 */
public class juLingZhen extends ZhenFuSettings {

    private static final String[][] juLingZhen = {
        {
            "0   0",
            "     ",
            "     ",
            "     ",
            "0   0"
        },
        {
            "00 00",
            "0* *0",
            "  *  ",
            "0* *0",
            "00 00"
        }
    };
    private static final Block[] juLingZhenMaterial = {
        ModBlocks.REIKI_STONE_BRICKS
    };

    public juLingZhen() {
        super(
            new Structure(juLingZhen, juLingZhenMaterial, 2, 5, 2)
                .setChunkScope(0, 0)
        );
    }

    public void effect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
        CultivationServerData cultivation = ((IEntityDataSaver) player).getServerCultivationData();
        if(cultivation.inGain("ju_ling_zhen_lv1")){
            return;
        }
        // 修炼灵气基础加成 10倍
        float gain = 10 * ((float) getAllJadeEffect(world, pos) / 100);
        if(gain == 0){
            gain = 1;
        }

        cultivation.addGain("ju_ling_zhen_lv1", gain);
    }

    public void endEffect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
        ((IEntityDataSaver) player).getServerCultivationData().minusGain("ju_ling_zhen_lv1");
    }

    @Override
    public void usable(World world, BlockPos pos, BlockState state, Ui_Block.Entity be) {
        for(BlockEntity blockEntity: getInAllBlockEntity(ModBlocks.INJECTION_REIKI_STANDS, world, pos)){
            // 注灵台效率基础加成 2.5倍
            ((InjectionReikiStandsEntity) blockEntity).gain = 2.5f * ((float) getAllJadeEffect(world, pos) / 100);
        }   
    }

    @Override
    public void unusable(World world, BlockPos pos, BlockState state, Ui_Block.Entity be) {
        for(BlockEntity blockEntity: getInAllBlockEntity(ModBlocks.INJECTION_REIKI_STANDS, world, pos)){
            ((InjectionReikiStandsEntity) blockEntity).gain = 1;
        }   
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, Entity be) {
        for(BlockEntity blockEntity: getInAllBlockEntity(ModBlocks.INJECTION_REIKI_STANDS, world, pos)){
            ((InjectionReikiStandsEntity) blockEntity).gain = 1;
        } 
    }
}
