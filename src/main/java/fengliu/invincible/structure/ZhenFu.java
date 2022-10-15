package fengliu.invincible.structure;

import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.util.CheckStructure.Structure;
import net.minecraft.block.Block;
import net.minecraft.text.TranslatableText;

public class ZhenFu {
    public class Lv1 {
        private static final String[][] test = {
            {
                "0   0",
                "     ",
                "     ",
                "     ",
                "0   0"
            },
            {
                "00 00",
                "0   0",
                "  *  ",
                "0   0",
                "00 00"
            }
        };
        private static final Block[] testMaterial = {
            ModBlocks.REIKI_STONE_BRICKS
        };

        public static final ZhenFuSettings[] zhenFuSettings = {
            new ZhenFuSettings(new Structure(test, testMaterial, 2, 5, 2))
        };
    }

    public static class ZhenFuSettings {
        public final Structure Structure;
        public TranslatableText Name;

        public ZhenFuSettings(Structure structure){
            Structure = structure;
        }

        public ZhenFuSettings setName(TranslatableText name){
            Name = name;
            return this;
        }
    }
}
