package fengliu.invincible.structure;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.api.Ui_Block.Entity;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.entity.block.InjectionReikiStandsEntity;
import fengliu.invincible.screen.handler.JuLingZhenLv1ScreenHandler;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.CheckStructure.Structure;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;

public class ZhenFuLv1 implements ZhenFus {
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

    private static final ZhenFuSettings[] zhenFuSettings = {
        /**
         * 一阶聚灵阵
         * 
         * 结构大小 x5 * 5y * z5 = 125, 有效范围一区块 (0 * 0) 有效高度 10y, 玉石需求 4 块
         * 修炼灵气基础加成 10倍
         * 注灵台效率基础加成 2.5倍
         */
        new ZhenFuSettings(
            new Structure(juLingZhen, juLingZhenMaterial, 2, 5, 2)
                .setChunkScope(0, 0)
        ){

            public void effect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
                // 修炼灵气基础加成 10倍
                float gain = 10 * ((float) getAllJadeEffect(world, pos) / 100);
                if(gain == 0){
                    gain = 1;
                }

                ((IEntityDataSaver) player).getServerCultivationData().setGain(gain);
            }

            public void endEffect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
                ((IEntityDataSaver) player).getServerCultivationData().setGain(1);
            }

            @Override
            public void usable(World world, BlockPos pos, BlockState state, Entity be) {
                for(BlockEntity blockEntity: getInAllBlockEntity(ModBlocks.INJECTION_REIKI_STANDS, world, pos)){
                    // 注灵台效率基础加成 2.5倍
                    ((InjectionReikiStandsEntity) blockEntity).gain = 2.5f * ((float) getAllJadeEffect(world, pos) / 100);
                }   
            }

            @Override
            public void unusable(World world, BlockPos pos, BlockState state, Entity be) {
                for(BlockEntity blockEntity: getInAllBlockEntity(ModBlocks.INJECTION_REIKI_STANDS, world, pos)){
                    ((InjectionReikiStandsEntity) blockEntity).gain = 1;
                }   
            }
        }
            .setName(new TranslatableText("zhen_fu.invincible.ju_ling_zhen.1"))
            .setScreenHandler(JuLingZhenLv1ScreenHandler.class)
            .setNeedJadeNumber(4)
            .setHighly(10)
        
    };

    @Override
    public ZhenFuSettings[] getZhenFuSettings() {
        return zhenFuSettings;
    }
}
