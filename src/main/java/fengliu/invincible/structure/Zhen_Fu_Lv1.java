package fengliu.invincible.structure;


import fengliu.invincible.invincibleMod;
import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.screen.handler.JuLingZhen_Lv1_ScreenHandler;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import fengliu.invincible.util.IEntityDataSaver;
import fengliu.invincible.util.CheckStructure.Structure;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;

public class Zhen_Fu_Lv1 implements ZhenFus {
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
        // 一阶聚灵阵
        new ZhenFuSettings(
            new Structure(juLingZhen, juLingZhenMaterial, 2, 5, 2)
                .setChunkScope(0, 0)
        ){

            public void effect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
                // 一阶聚灵阵 基础加成 10
                float gain = 10 * ((float) getAllJadeEffect(world, pos) / 100);
                if(gain == 0){
                    gain = 1;
                }

                ((IEntityDataSaver) player).getServerCultivationData().setGain(gain);
            }

            public void endEffect(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
                ((IEntityDataSaver) player).getServerCultivationData().setGain(1);
            };
        }
            .setName(new TranslatableText("zhen_fu.invincible.ju_ling_zhen.1"))
            .setScreenHandler(JuLingZhen_Lv1_ScreenHandler.class)
            .setNeedJadeNumber(4)
            .setHighly(10)
        
    };

    @Override
    public ZhenFuSettings[] getZhenFuSettings() {
        return zhenFuSettings;
    }
}
