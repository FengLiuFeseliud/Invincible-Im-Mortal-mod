package fengliu.invincible.structure;


import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        new ZhenFuSettings(
            new Structure(juLingZhen, juLingZhenMaterial, 2, 5, 2)
                .setChunkScope(1, 1)
        ){
            @Override
            public boolean skill(World world, BlockPos pos, BlockState state, Ui_Block.Entity be, PlayerEntity player) {
                // player.sendMessage(new TranslatableText("zhen_fu.invincible.ju_ling_zhen.1"), false);
                return false;
            }
        }
            .setName(new TranslatableText("zhen_fu.invincible.ju_ling_zhen.1"))
            .setNeedJadeNumber(4)
            .setHighly(10)
    };

    @Override
    public ZhenFuSettings[] getZhenFuSettings() {
        return zhenFuSettings;
    }
}
